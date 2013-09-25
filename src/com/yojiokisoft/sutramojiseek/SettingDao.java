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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 設定情報のデータアクセス
 */
public class SettingDao {
	private static SettingDao mInstance = null;
	private static SharedPreferences mSharedPref = null;
	private static Context mContext;

	/**
	 * コンストラクタは公開しない インスタンスを取得する場合は、getInstanceを使用する.
	 */
	private SettingDao() {
	}

	/**
	 * インスタンスの取得.
	 * 
	 * @return SettingDao
	 */
	public static SettingDao getInstance() {
		if (mInstance == null) {
			mInstance = new SettingDao();
			mContext = App.getInstance().getAppContext();
			mSharedPref = PreferenceManager
					.getDefaultSharedPreferences(mContext);
		}
		return mInstance;
	}

	/**
	 * @return 練習モード
	 */
	public boolean getPMode() {
		String strDef = mContext.getString(R.string.pref_pmode_default);
		boolean def = (strDef.equals("true")) ? true : false;
		boolean val = mSharedPref.getBoolean(MyConst.PK_PMODE, def);
		return val;
	}

	/**
	 * @return スピード
	 */
	public String getSpeed() {
		String val = mSharedPref.getString(MyConst.PK_SPEED,
				mContext.getString(R.string.pref_speed_default));
		return val;
	}

	/**
	 * @return ボタン配置
	 */
	public String getButtonPosition() {
		String val = mSharedPref.getString(MyConst.PK_BUTTON_POSITION,
				mContext.getString(R.string.pref_button_position_default));
		return val;
	}

	/**
	 * @return 合いの手の音
	 */
	public String getAinoteSound() {
		String val = mSharedPref.getString(MyConst.PK_AINOTE_SOUND,
				mContext.getString(R.string.pref_ainote_sound_default));
		return val;
	}

	/**
	 * 合いの手の音のセット
	 * 
	 * @param resName
	 *            合いの手の音のリソース名
	 * @return true=正常終了 false=エラー
	 */
	public boolean setAinoteSound(String resName) {
		return mSharedPref.edit().putString(MyConst.PK_AINOTE_SOUND, resName)
				.commit();
	}

	/**
	 * @return リズム音
	 */
	public String getRhythmSound() {
		String val = mSharedPref.getString(MyConst.PK_RHYTHM_SOUND,
				mContext.getString(R.string.pref_rhythm_sound_default));
		return val;
	}

	/**
	 * リズム音のセット
	 * 
	 * @param resName
	 *            リズム音のリソース名
	 * @return true=正常終了 false=エラー
	 */
	public boolean setRhythmSound(String resName) {
		return mSharedPref.edit().putString(MyConst.PK_RHYTHM_SOUND, resName)
				.commit();
	}

	/**
	 * @return 合いの手を入れる
	 */
	public boolean getAinote() {
		String strDef = mContext.getString(R.string.pref_ainote_default);
		boolean def = (strDef.equals("true")) ? true : false;
		boolean val = mSharedPref.getBoolean(MyConst.PK_AINOTE, def);
		return val;
	}
}
