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

/**
 * 定数クラス.
 */
public class MyConst {
	/** 設定キー：練習モード */
	public static final String PK_PMODE = "pmode";

	/** 設定キー：スピード */
	public static final String PK_SPEED = "speed";

	/** 設定キー：スピード（自分で） */
	public static final String PK_SPEED_MY_PACE = "1";

	/** 設定キー：スピード（遅い） */
	public static final String PK_SPEED_SLOW = "2";

	/** 設定キー：スピード（普通） */
	public static final String PK_SPEED_NORMAL = "3";

	/** 設定キー：スピード（速い） */
	public static final String PK_SPEED_FAST = "4";

	/** 設定キー：リズム音 */
	public static final String PK_RHYTHM_SOUND = "rhythm_sound";

	/** 設定キー：ボタン配置 */
	public static final String PK_BUTTON_POSITION = "button_position";

	/** 設定キー：ボタン配置（右） */
	public static final String PK_BUTTON_POSITIONS_RIGHT = "r";

	/** 設定キー：ボタン配置（左） */
	public static final String PK_BUTTON_POSITIONS_LEFT = "l";

	/** 設定キー：合いの手 */
	public static final String PK_AINOTE = "ainote";

	/** 設定キー：合いの手の音 */
	public static final String PK_AINOTE_SOUND = "ainote_sound";

	/** SQLiteのDB名 */
	public static final String DATABASE_FILE = "sutramojiseek.db";

	/** SQLiteのDB名のフルパス */
	public static String getDatabasePath() {
		return App.getInstance().getDatabasePath(DATABASE_FILE).toString();
	}

	/** バグファイル名(キャッチした) */
	public static final String BUG_CAUGHT_FILE = "bug_caught.txt";

	/** バグファイル名(キャッチされなかった) */
	public static final String BUG_UNCAUGHT_FILE = "bug_uncaught.txt";

	/** キャッチしたバグファイルのフルパス */
	public static String getCaughtBugFilePath() {
		return MyFile.pathCombine(App.getInstance().getAppDataPath(),
				BUG_CAUGHT_FILE);
	}

	/** キャッチされなかったバグファイルのフルパス */
	public static String getUncaughtBugFilePath() {
		return MyFile.pathCombine(App.getInstance().getAppDataPath(),
				BUG_UNCAUGHT_FILE);
	}
}