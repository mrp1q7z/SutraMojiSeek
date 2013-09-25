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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * クリック音設定のアクティビティ
 */
public class SoundDao {
	private static SoundDao mInstance = null;
	private static Context mContext;

	/**
	 * コンストラクタは公開しない インスタンスを取得する場合は、getInstanceを使用する.
	 */
	private SoundDao() {
	}

	/**
	 * インスタンスの取得.
	 * 
	 * @return SettingDao
	 */
	public static SoundDao getInstance() {
		if (mInstance == null) {
			mInstance = new SoundDao();
			mContext = App.getInstance().getAppContext();
		}
		return mInstance;
	}

	/**
	 * リズム音一覧の取得
	 * 
	 * @return
	 */
	public List<SoundEntity> getRhythmSoundList() {
		List<SoundEntity> list = new ArrayList<SoundEntity>();

		list.add(getSound("none"));
		list.add(getSound("mp_drop"));
		list.add(getSound("mp_hyu"));
		list.add(getSound("mp_poku"));
		list.add(getSound("mp_taiko"));

		return list;
	}

	/**
	 * 合いの手の音一覧の取得
	 * 
	 * @return
	 */
	public List<SoundEntity> getAinoteSoundList() {
		List<SoundEntity> list = new ArrayList<SoundEntity>();

		list.add(getSound("none"));
		list.add(getSound("mp_gooon"));
		list.add(getSound("mp_chiiin"));

		return list;
	}

	public SoundEntity getSound(String resName) {
		SoundEntity sound = new SoundEntity();

		if (resName.equals("none")) {
			sound.title = mContext.getString(R.string.sound_none);
			sound.description = mContext.getString(R.string.sound_none_note);
			sound.resId = 0;
			sound.checked = false;
		} else if (resName.equals("mp_gooon")) {
			sound.title = mContext.getString(R.string.sound_gooon);
			sound.description = mContext.getString(R.string.sound_gooon_note);
			sound.resId = R.raw.mp_gooon;
			sound.checked = false;
		} else if (resName.equals("mp_chiiin")) {
			sound.title = mContext.getString(R.string.sound_chiiin);
			sound.description = mContext.getString(R.string.sound_chiiin_note);
			sound.resId = R.raw.mp_chiiin;
			sound.checked = false;
		} else if (resName.equals("mp_drop")) {
			sound.title = mContext.getString(R.string.sound_drop);
			sound.description = mContext.getString(R.string.sound_drop_note);
			sound.resId = R.raw.mp_drop;
			sound.checked = false;
		} else if (resName.equals("mp_hyu")) {
			sound.title = mContext.getString(R.string.sound_hyu);
			sound.description = mContext.getString(R.string.sound_hyu_note);
			sound.resId = R.raw.mp_hyu;
			sound.checked = false;
		} else if (resName.equals("mp_poku")) {
			sound.title = mContext.getString(R.string.sound_poku);
			sound.description = mContext.getString(R.string.sound_poku_note);
			sound.resId = R.raw.mp_poku;
			sound.checked = false;
		} else if (resName.equals("mp_taiko")) {
			sound.title = mContext.getString(R.string.sound_taiko);
			sound.description = mContext.getString(R.string.sound_taiko_note);
			sound.resId = R.raw.mp_taiko;
			sound.checked = false;
		} else {
			Log.d("TAG", "resName=" + resName);
		}

		return sound;
	}
}
