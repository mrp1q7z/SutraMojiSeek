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

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * DBヘルパー
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHelper mInstance = null;

	/**
	 * コンストラクタは公開しない インスタンスを取得するときは、getInstanceを使用する.
	 * 
	 * @param context
	 */
	private DatabaseHelper(Context context) {
		super(context, MyConst.getDatabasePath(), null, DATABASE_VERSION);
	}

	/**
	 * インスタンスの取得.
	 * 
	 * @return DatabaseHelper
	 */
	public static DatabaseHelper getInstance() {
		if (mInstance == null) {
			Context context = App.getInstance().getAppContext();
			mInstance = new DatabaseHelper(context);
			createTable(context, mInstance.getConnectionSource());
		}
		return mInstance;
	}

	/**
	 * テーブルが存在しなければ、テーブルを作成する.
	 * 
	 * @param context
	 * @param conn
	 */
	public static void createTable(Context context, ConnectionSource conn) {
		try {
			TableUtils.createTableIfNotExists(conn, RankingEntity.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see DatabaseHelper#onCreate(SQLiteDatabase, ConnectionSource)
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource sonn) {
		;
	}

	/**
	 * @see DatabaseHelper#onUpgrade(SQLiteDatabase, ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource conn,
			int oldVersion, int newVersion) {
		;
	}
}
