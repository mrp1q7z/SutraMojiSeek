package com.yojiokisoft.sutramojiseek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OpeningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opening);
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
}
