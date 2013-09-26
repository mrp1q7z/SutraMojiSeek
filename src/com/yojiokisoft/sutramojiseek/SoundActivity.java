/*
 * Copyright (C) 2013 YojiokiSoft
 * 
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this
 * program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.yojiokisoft.sutramojiseek;

import java.util.List;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * クリック音設定のアクティビティ
 */
public class SoundActivity extends Activity {
	private ListView mListView;
	private int mCheckedPosition = -1;
	private SoundPool mSound;
	private int[] mSoundId;
	private List<SoundEntity> mList;
	private String mTarget;
	private int mSoundPoolCompCnt;
	private RelativeLayout mProgressContainer;

	/**
	 * 初期処理
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);

		Bundle extra = getIntent().getExtras();
		String arg = null;
		if (extra != null) {
			if (extra.containsKey("arg")) {
				arg = extra.getString("arg");
			}
		}
		if ("ainote_sound".equals(arg)) {
			mTarget = "Ainote";
		} else {
			mTarget = "Rhythm";
		}

		mProgressContainer = (RelativeLayout) findViewById(R.id.progress_container);
		mListView = (ListView) findViewById(R.id.sound_list);
		String resName;
		if ("Ainote".equals(mTarget)) {
			mList = SoundDao.getInstance().getAinoteSoundList();
			resName = SettingDao.getInstance().getAinoteSound();
		} else {
			mList = SoundDao.getInstance().getRhythmSoundList();
			resName = SettingDao.getInstance().getRhythmSound();
		}

		int resId = MyResource.getResourceIdByName(resName, "raw");
		String[] items = new String[mList.size()];
		SoundEntity item;
		for (int i = 0; i < mList.size(); i++) {
			item = mList.get(i);
			items[i] = item.title + " (" + item.description + ")";
			if (resId == item.resId) {
				mCheckedPosition = i;
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice,
				android.R.id.text1, items);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(mSoundListItemClicked);
		mListView.setItemChecked(mCheckedPosition, true);

		mSound = new SoundPool(mList.size(), AudioManager.STREAM_MUSIC, 0);
		mSoundId = new int[mList.size()];
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).resId == 0) {
				mSoundId[i] = 0;
			} else {
				mSoundId[i] = mSound.load(getApplicationContext(),
						mList.get(i).resId, 0);
			}
		}

		mSoundPoolCompCnt = 0;
		mSound.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				if (0 == status) {
					mSoundPoolCompCnt++;
					if (mSoundPoolCompCnt >= mList.size() - 1) {
						mProgressContainer.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
	}

	/**
	 * データの一時保存
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("SAVE_POSITION", mCheckedPosition);
	}

	/**
	 * 一時保存されたデータを復元
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mCheckedPosition = savedInstanceState.getInt("SAVE_POSITION");
	}

	/**
	 * クリック音が選択された
	 */
	private OnItemClickListener mSoundListItemClicked = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (mCheckedPosition != -1) {
				if (mSoundId[mCheckedPosition] != 0) {
					mSound.stop(mSoundId[mCheckedPosition]);
				}
			}
			if (mSoundId[position] != 0) {
				mSound.play(mSoundId[position], 1.0F, 1.0F, 0, 0, 1.0F);
			}
			mCheckedPosition = position;
		}
	};

	/**
	 * OKボタンのクリック
	 * 
	 * @param view
	 */
	public void onOkButtonClicked(View view) {
		int resId = mList.get(mCheckedPosition).resId;
		String resName = "none";
		if (resId != 0) {
			resName = getResources().getResourceEntryName(resId);
		}
		if ("Ainote".equals(mTarget)) {
			SettingDao.getInstance().setAinoteSound(resName);
		} else {
			SettingDao.getInstance().setRhythmSound(resName);
		}
		finish();
	}

	public void onCancelButtonClicked(View view) {
		finish();
	}

	/**
	 * 終了処理
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSound.release();
	}
}
