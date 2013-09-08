package com.yojiokisoft.sutramojiseek;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TableRow[] mTableRow;
	private Button[][] mButton;
	private SutraDao mSutraDao;
	private int mCurrentLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mCurrentLine = 0;
		mSutraDao = SutraDao.getInstance();
		mSutraDao.seek(0);

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
		mButton[lineNumber][0].setTag(mSutraDao.eof());
		((MyButton)mButton[lineNumber][0]).setKana(mSutraDao.getKana());
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
			// if (mSoundId != 0) {
			// mSound.stop(mSoundId);
			// mSound.play(mSoundId, 1.0F, 1.0F, 0, 0, 1.0F);
			// }

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
				boolean eof = (Boolean) mButton[mCurrentLine][0].getTag();
				if (eof) {
					showToast("おわり");
				}
				printSutra(mCurrentLine);
				mCurrentLine++;
				if (mCurrentLine >= mTableRow.length) {
					mCurrentLine = 0;
				}
				setCurrentLineBg();
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

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
