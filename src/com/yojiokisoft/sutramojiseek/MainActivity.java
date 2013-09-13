package com.yojiokisoft.sutramojiseek;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SoundPool mSound;
	private int mSoundId;
	private String mCurrentSoundName;
	private LinearLayout mMokugyo;
	private LinearLayout mScoreContainer;
	private TableRow[] mTableRow;
	private Button[][] mButton;
	private SutraDao mSutraDao;
	private int mCurrentLine;
	private Handler mHandler = new Handler();
	private long mMoveTime = 0;
	private ArrayList<Integer> mInterval = new ArrayList<Integer>();
	private ArrayList<Integer> mIntervalKeisoku = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mCurrentSoundName = SettingDao.getInstance().getRhythmSound();
		int resId = MyResource.getResourceIdByName(mCurrentSoundName, "raw");
		if (resId == 0) {
			mSoundId = 0;
		} else {
			mSoundId = mSound.load(getApplicationContext(), resId, 0);
		}

		mCurrentLine = 0;
		mSutraDao = SutraDao.getInstance();
		mSutraDao.seek(0);

		mScoreContainer = (LinearLayout) findViewById(R.id.score_container);
		mMokugyo = (LinearLayout) findViewById(R.id.mokugyo_container);
		if (SettingDao.getInstance().getPMode()) {
			mMokugyo.setVisibility(View.VISIBLE);
		}

		mButton = new Button[5][5];
		mButton[0][0] = (Button) findViewById(R.id.button_1a);
		mButton[0][1] = (Button) findViewById(R.id.button_1b);
		mButton[0][2] = (Button) findViewById(R.id.button_1c);
		mButton[0][3] = (Button) findViewById(R.id.button_1d);
		mButton[0][4] = (Button) findViewById(R.id.button_1e);

		mButton[1][0] = (Button) findViewById(R.id.button_2a);
		mButton[1][1] = (Button) findViewById(R.id.button_2b);
		mButton[1][2] = (Button) findViewById(R.id.button_2c);
		mButton[1][3] = (Button) findViewById(R.id.button_2d);
		mButton[1][4] = (Button) findViewById(R.id.button_2e);

		mButton[2][0] = (Button) findViewById(R.id.button_3a);
		mButton[2][1] = (Button) findViewById(R.id.button_3b);
		mButton[2][2] = (Button) findViewById(R.id.button_3c);
		mButton[2][3] = (Button) findViewById(R.id.button_3d);
		mButton[2][4] = (Button) findViewById(R.id.button_3e);

		mButton[3][0] = (Button) findViewById(R.id.button_4a);
		mButton[3][1] = (Button) findViewById(R.id.button_4b);
		mButton[3][2] = (Button) findViewById(R.id.button_4c);
		mButton[3][3] = (Button) findViewById(R.id.button_4d);
		mButton[3][4] = (Button) findViewById(R.id.button_4e);

		mButton[4][0] = (Button) findViewById(R.id.button_5a);
		mButton[4][1] = (Button) findViewById(R.id.button_5b);
		mButton[4][2] = (Button) findViewById(R.id.button_5c);
		mButton[4][3] = (Button) findViewById(R.id.button_5d);
		mButton[4][4] = (Button) findViewById(R.id.button_5e);

		mTableRow = new TableRow[5];
		mTableRow[0] = (TableRow) findViewById(R.id.table_row1);
		mTableRow[1] = (TableRow) findViewById(R.id.table_row2);
		mTableRow[2] = (TableRow) findViewById(R.id.table_row3);
		mTableRow[3] = (TableRow) findViewById(R.id.table_row4);
		mTableRow[4] = (TableRow) findViewById(R.id.table_row5);

		for (int i = 0; i < 5; i++) {
			printSutra(i);
		}

		int iMax = mButton.length;
		int jMax = mButton[0].length;
		for (int i = 0; i < iMax; i++) {
			for (int j = 0; j < jMax; j++) {
				mButton[i][j].setOnClickListener(mOnButtonClicked);
			}
		}

		setCurrentLineBg();
		readInterval();
	}

	/**
	 * メニューが選択された
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(), SettingsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		}
		return false;
	}

	/**
	 * 前面に表示された
	 */
	@Override
	protected void onResume() {
		super.onResume();

		String newSoundName = SettingDao.getInstance().getRhythmSound();
		if (!mCurrentSoundName.equals(newSoundName)) {
			if (mSoundId != 0) {
				mSound.unload(mSoundId);
			}

			mCurrentSoundName = newSoundName;
			int resId = MyResource
					.getResourceIdByName(mCurrentSoundName, "raw");
			if (resId == 0) {
				mSoundId = 0;
			} else {
				mSoundId = mSound.load(getApplicationContext(), resId, 0);
			}
		}

		if (SettingDao.getInstance().getPMode()) {
			mMokugyo.setVisibility(View.VISIBLE);
		} else {
			mMokugyo.setVisibility(View.GONE);
		}
	}

	/**
	 * 終了処理
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		String filePath = Environment.getExternalStorageDirectory()
				+ "/sutra-interval.txt";
		File file = new File(filePath);
		file.getParentFile().mkdir();

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			for (int time : mIntervalKeisoku) {
				String str = "" + time + "\n";
				bw.write(str);
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mHandler.removeCallbacks(mRhythmRunnable);
		mSound.release();
	}

	private void readInterval() {
		AssetManager assetManager = getResources().getAssets();
		InputStream is;
		String line = "";
		try {
			is = assetManager.open("interval.txt");
			InputStreamReader input = new InputStreamReader(is);
			BufferedReader buffreader = new BufferedReader(input);
			while ((line = buffreader.readLine()) != null) {
				mInterval.add(Integer.parseInt(line));
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printSutra(int lineNumber) {
		int max = mButton[0].length;
		if (mSutraDao.eof()) {
			for (int i = 0; i < max; i++) {
				mButton[lineNumber][i].setText("");
			}
			return;
		}

		String sutra = mSutraDao.read();
		int rand = MyRandom.random(1, max - 1);
		String[] dummy = mSutraDao.randomRead(sutra, max - 2);

		mButton[lineNumber][0].setText(sutra);
		mButton[lineNumber][0].setTag(mSutraDao.eof() ? -1 : mSutraDao
				.getCurrentPos() - 1);
		((MyButton) mButton[lineNumber][0]).setKana(mSutraDao.getKana());
		int cnt = 0;
		for (int i = 1; i < max; i++) {
			if (i == rand) {
				mButton[lineNumber][i].setText(sutra);
			} else {
				mButton[lineNumber][i].setText(dummy[cnt]);
				cnt++;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private View.OnClickListener mOnButtonClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {

			int lineNumber = -1;

			switch (v.getId()) {
			case R.id.button_1b:
			case R.id.button_1c:
			case R.id.button_1d:
			case R.id.button_1e:
				lineNumber = 0;
				break;
			case R.id.button_2b:
			case R.id.button_2c:
			case R.id.button_2d:
			case R.id.button_2e:
				lineNumber = 1;
				break;
			case R.id.button_3b:
			case R.id.button_3c:
			case R.id.button_3d:
			case R.id.button_3e:
				lineNumber = 2;
				break;
			case R.id.button_4b:
			case R.id.button_4c:
			case R.id.button_4d:
			case R.id.button_4e:
				lineNumber = 3;
				break;
			case R.id.button_5b:
			case R.id.button_5c:
			case R.id.button_5d:
			case R.id.button_5e:
				lineNumber = 4;
				break;
			}

			if (lineNumber == mCurrentLine) {
				nextMoji();
			}
		}
	};

	private void setCurrentLineBg() {
		int old = mCurrentLine - 1;
		if (old < 0) {
			old = mTableRow.length - 1;
		}
		mTableRow[old].setBackgroundResource(R.color.gray_scale);
		mTableRow[mCurrentLine].setBackgroundColor(Color.WHITE);
	}

	private void showScore() {
		mScoreContainer.setVisibility(View.VISIBLE);
	}

	public void onMokugyoButtonClicked(View view) {
		nextMoji();
	};

	public void onReplayButtonClicked(View view) {
		mScoreContainer.setVisibility(View.INVISIBLE);

		mCurrentLine = 0;
		mSutraDao.seek(0);

		for (int i = 0; i < 5; i++) {
			printSutra(i);
		}

		setCurrentLineBg();
	}

	private void nextMoji() {
		// debug >>>
		long startTime = System.currentTimeMillis();
		int time = (int) (startTime - mMoveTime);
		mIntervalKeisoku.add(time);
		// debug <<<

		mMoveTime = System.currentTimeMillis();

		if (mSoundId != 0) {
			mSound.stop(mSoundId);
			mSound.play(mSoundId, 1.0F, 1.0F, 0, 0, 1.0F);
		}

		int index = (Integer) mButton[mCurrentLine][0].getTag();
		if (index == -1) {
			showScore();
			return;
		}
		printSutra(mCurrentLine);
		mCurrentLine++;
		if (mCurrentLine >= mTableRow.length) {
			mCurrentLine = 0;
		}
		setCurrentLineBg();

		long interval;
		String speed = SettingDao.getInstance().getSpeed();
		if (MyConst.PK_SPEED_SLOW.equals(speed)) {
			interval = mInterval.get(index + 1) * 3;
		} else if (MyConst.PK_SPEED_NORMAL.equals(speed)) {
			interval = mInterval.get(index + 1) * 2;
		} else if (MyConst.PK_SPEED_FAST.equals(speed)) {
			interval = mInterval.get(index + 1);
		} else {
			interval = 0;
		}
		if (interval != 0) {
			interval -= (System.currentTimeMillis() - mMoveTime);
			mHandler.removeCallbacks(mRhythmRunnable);
			mHandler.postDelayed(mRhythmRunnable, interval);
		}
	}

	private final Runnable mRhythmRunnable = new Runnable() {
		@Override
		public void run() {
			nextMoji();
		}
	};
}
