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
		list.add(getSound("se_syan"));
		list.add(getSound("se_pachi"));
		list.add(getSound("se_ton"));
		list.add(getSound("se_clock"));
		list.add(getSound("se_kan"));
		list.add(getSound("se_tuketaiko"));
		list.add(getSound("se_tuzumi"));

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
		} else if (resName.equals("se_syan")) {
			sound.title = mContext.getString(R.string.sound_syan);
			sound.description = mContext.getString(R.string.sound_syan_note);
			sound.resId = R.raw.se_syan;
			sound.checked = false;
		} else if (resName.equals("se_pachi")) {
			sound.title = mContext.getString(R.string.sound_pachi);
			sound.description = mContext.getString(R.string.sound_pachi_note);
			sound.resId = R.raw.se_pachi;
			sound.checked = false;
		} else if (resName.equals("se_ton")) {
			sound.title = mContext.getString(R.string.sound_ton);
			sound.description = mContext.getString(R.string.sound_ton_note);
			sound.resId = R.raw.se_ton;
			sound.checked = false;
		} else if (resName.equals("se_clock")) {
			sound.title = mContext.getString(R.string.sound_clock);
			sound.description = mContext.getString(R.string.sound_clock_note);
			sound.resId = R.raw.se_clock;
			sound.checked = false;
		} else if (resName.equals("se_kan")) {
			sound.title = mContext.getString(R.string.sound_kan);
			sound.description = mContext.getString(R.string.sound_kan_note);
			sound.resId = R.raw.se_kan;
			sound.checked = false;
		} else if (resName.equals("se_tuketaiko")) {
			sound.title = mContext.getString(R.string.sound_tuketaiko);
			sound.description = mContext.getString(R.string.sound_tuketaiko_note);
			sound.resId = R.raw.se_tuketaiko;
			sound.checked = false;
		} else if (resName.equals("se_tuzumi")) {
			sound.title = mContext.getString(R.string.sound_tuzumi);
			sound.description = mContext.getString(R.string.sound_tuzumi_note);
			sound.resId = R.raw.se_tuzumi;
			sound.checked = false;
		} else {
			Log.d("TAG", "resName=" + resName);
		}

		return sound;
	}
}
