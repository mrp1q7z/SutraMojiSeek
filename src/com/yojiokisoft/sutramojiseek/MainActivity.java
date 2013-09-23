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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class MainActivity extends Activity implements AdListener {
	private AdView mAdViewBanner;
	private SoundPool mSound;
	private int mSoundId;
	private LinearLayout mPauseContainer;
	private LinearLayout mScoreContainer;
	private TextView mScore;
	private TableRow[] mTableRow;
	private Button[][] mButton;
	private SettingDao mSettings;
	private SutraDao mSutraDao;
	private int mCurrentLine;
	private State mState = new State();
	private Handler mHandler = new Handler();
	private long mMoveTime = 0;
	private ArrayList<Integer> mInterval = new ArrayList<Integer>();
	private ArrayList<Integer> mIntervalKeisoku = new ArrayList<Integer>();
	private int mOkCnt;
	private int mNgCnt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mSettings = SettingDao.getInstance();
		if (mSettings.getButtonPosition().equals(
				MyConst.PK_BUTTON_POSITIONS_LEFT)) {
			setContentView(R.layout.activity_main_left);
		} else {
			setContentView(R.layout.activity_main);
		}

		mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		int resId = MyResource.getResourceIdByName(mSettings.getRhythmSound(),
				"raw");
		if (resId == 0) {
			mSoundId = 0;
		} else {
			mSoundId = mSound.load(getApplicationContext(), resId, 0);
		}

		mSutraDao = SutraDao.getInstance();
		clearScore();

		mPauseContainer = (LinearLayout) findViewById(R.id.pause_container);
		mScoreContainer = (LinearLayout) findViewById(R.id.score_container);
		mScore = (TextView) findViewById(R.id.score);
		LinearLayout mokugyoContainer = (LinearLayout) findViewById(R.id.mokugyo_container);
		if (mSettings.getPMode()) {
			mokugyoContainer.setVisibility(View.VISIBLE);
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

		AdRequest adRequest = AdCatalogUtils.createAdRequest();
		mAdViewBanner = (AdView) findViewById(R.id.adViewBanner);
		mAdViewBanner.setAdListener(this);
		mAdViewBanner.loadAd(adRequest);
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
		if (mAdViewBanner != null) {
			mAdViewBanner.destroy();
		}
		MyResource.wakeLockRelease();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode != KeyEvent.KEYCODE_BACK) {
			return super.onKeyDown(keyCode, event);
		} else {
			// ポーズ中の戻るキーは画面を閉じずにポーズの解除を行う
			if (mState.getState() == State.S_PAUSE) {
				onResumButtonClicked(null);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
	}

	@Override
	public void onReceiveAd(Ad ad) {
		Log.d("Banners_class", "I received an ad");
	}

	@Override
	public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
		Log.d("Banners_class", "I failed to receive an ad");
	}

	@Override
	public void onPresentScreen(Ad ad) {
		Log.d("Banners_class", "Presenting screen");
	}

	@Override
	public void onDismissScreen(Ad ad) {
		Log.d("Banners_class", "Dismissing screen");
	}

	@Override
	public void onLeaveApplication(Ad ad) {
		Log.d("Banners_class", "Leaving application");
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

	private View.OnClickListener mOnButtonClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			boolean pause = false;
			int lineNumber = -1;

			switch (v.getId()) {
			case R.id.button_1a:
			case R.id.button_2a:
			case R.id.button_3a:
			case R.id.button_4a:
			case R.id.button_5a:
				pause = true;
				break;
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

			if (pause) {
				if (mState.getState() == State.S_PLAY) {
					mPauseContainer.setVisibility(View.VISIBLE);
					mState.setState(State.S_PAUSE);
				}
			} else if (lineNumber == mCurrentLine) {
				if (mState.getState() == State.S_INIT) {
					nextMoji(((Button) v).getText().toString());
					mState.setState(State.S_PLAY);
					MyResource.wakeLockAcquire();
				} else if (mState.getState() == State.S_PLAY) {
					nextMoji(((Button) v).getText().toString());
				}
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

	private RankingEntity setRanking(int okCnt, int ngCnt, int skipCnt,
			long playTime, int speed) {
		int score = okCnt * 1000 * speed;
		score -= ngCnt * 100;
		score -= skipCnt * 200;
		score += ((183001 * (5 - speed) - playTime) / 1000) * 100;

		String level;
		if (score > 1000000) {
			level = "A";
		} else if (score > 100000) {
			level = "B";
		} else if (score > 10000) {
			level = "C";
		} else if (score > 1000) {
			level = "D";
		} else if (score > 100) {
			level = "E";
		} else if (score > 10) {
			level = "F";
		} else if (score > 0) {
			level = "G";
		} else {
			level = "Z";
		}

		RankingEntity data = new RankingEntity();
		data.score = score;
		data.okCnt = mOkCnt;
		data.ngCnt = mNgCnt;
		data.skipCnt = skipCnt;
		data.playTime = playTime;
		data.level = level;
		data.lastUpdateTime = System.currentTimeMillis();
		return data;
	}

	private void showScore() {
		mState.setState(State.S_SCORE);

		long playTime = mState.getPlayTime();
		int speed = Integer.parseInt(mSettings.getSpeed());
		int skipCnt = mSutraDao.getLength() - mOkCnt - mNgCnt;
		RankingEntity data = setRanking(mOkCnt, mNgCnt, skipCnt, playTime,
				speed);
		RankingDao rankingDao = new RankingDao();
		rankingDao.createOrUpdate(data);

		String msg = "Score:" + data.score
				+ String.format("\n(○ : %1$d", mOkCnt)
				+ String.format(" × : %1$d", mNgCnt)
				+ String.format(" ◇ : %1$d", skipCnt)
				+ String.format(" T : %1$d)", playTime / 1000);
		mScore.setText(msg);
		mScoreContainer.setVisibility(View.VISIBLE);
	}

	public void onMokugyoButtonClicked(View view) {
		if (mState.getState() == State.S_INIT) {
			mState.setState(State.S_PLAY);
		}
		nextMoji(null);
	};

	public void onResumButtonClicked(View view) {
		int index = (Integer) mButton[mCurrentLine][0].getTag();
		setTimer(index - 1, 0);

		mPauseContainer.setVisibility(View.INVISIBLE);
		mState.setState(State.S_PLAY);
	}

	public void clearScore() {
		mOkCnt = 0;
		mNgCnt = 0;
		mCurrentLine = 0;
		mState.setState(State.S_INIT);
		mSutraDao.seek(0);
	}

	public void onReplayButtonClicked(View view) {
		mPauseContainer.setVisibility(View.INVISIBLE);
		mScoreContainer.setVisibility(View.INVISIBLE);

		clearScore();

		for (int i = 0; i < 5; i++) {
			mTableRow[i].setBackgroundResource(R.color.gray_scale);
			printSutra(i);
		}

		setCurrentLineBg();
	}

	public void onReturnOpeningButtonClicked(View view) {
		Intent intent = new Intent(getApplicationContext(),
				OpeningActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

	public void onExitButtonClicked(View view) {
		finish();
	}

	private void nextMoji(String clickMoji) {
		long startTime = System.currentTimeMillis();
		// debug >>>
		int time = (int) (startTime - mMoveTime);
		mIntervalKeisoku.add(time);
		// debug <<<

		if (mSoundId != 0) {
			mSound.stop(mSoundId);
			mSound.play(mSoundId, 1.0F, 1.0F, 0, 0, 1.0F);
		}

		if (clickMoji != null) {
			if (clickMoji.equals(mButton[mCurrentLine][0].getText())) {
				mOkCnt++;
			} else {
				mNgCnt++;
			}
		}

		int index = (Integer) mButton[mCurrentLine][0].getTag();
		if (index == -1) {
			showScore();
			MyResource.wakeLockRelease();
			return;
		}
		printSutra(mCurrentLine);
		mCurrentLine++;
		if (mCurrentLine >= mTableRow.length) {
			mCurrentLine = 0;
		}
		setCurrentLineBg();

		long procTime = (System.currentTimeMillis() - startTime);
		setTimer(index, procTime);

		mMoveTime = System.currentTimeMillis();
	}

	private void setTimer(int index, long procTime) {
		long interval;
		String speed = mSettings.getSpeed();
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
			if (procTime != 0) {
				interval -= procTime;
			}
			mHandler.removeCallbacks(mRhythmRunnable);
			mHandler.postDelayed(mRhythmRunnable, interval);
		}
	}

	private final Runnable mRhythmRunnable = new Runnable() {
		@Override
		public void run() {
			if (mState.getState() != State.S_PLAY) {
				return;
			}
			nextMoji(null);
		}
	};
}
