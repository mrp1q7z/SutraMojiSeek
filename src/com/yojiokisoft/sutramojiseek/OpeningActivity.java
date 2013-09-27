package com.yojiokisoft.sutramojiseek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpeningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// キャッチされない例外をキャッチするデフォルトのハンドラを設定する
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

		setContentView(R.layout.activity_opening);
	}

	/**
	 * 開始処理
	 */
	@Override
	protected void onStart() {
		super.onStart();
		MyUncaughtExceptionHandler.sendBugReport(this);
	}

	public void onStartButtonClicked(View view) {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void onSettingButtonClicked(View view) {
		Intent intent = new Intent(getApplicationContext(),
				SettingsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public void onUsageButtonClicked(View view) {
		Intent intent = new Intent(getApplicationContext(), UsageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public void onRankingButtonClicked(View view) {
		Intent intent = new Intent(getApplicationContext(),
				RankingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
