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
import java.util.List;

import com.j256.ormlite.dao.Dao;

/**
 * ランキングのデータアクセス
 */
public class RankingDao {
	private Dao<RankingEntity, Integer> mRankingDao = null;

	/**
	 * コンストラクタ.
	 * 
	 * @throws SQLException
	 */
	public RankingDao() {
		DatabaseHelper helper = DatabaseHelper.getInstance();
		try {
			mRankingDao = helper.getDao(RankingEntity.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 全データを検索.
	 * 
	 * @return カード一覧
	 * @throws SQLException
	 */
	public List<RankingEntity> queryForAll() {
		List<RankingEntity> list = null;
		try {
			if (mRankingDao == null) {
				throw new RuntimeException("mRankingDao is null");
			} else {
				list = mRankingDao.queryForAll();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * あれば更新、なければ追加.
	 * 
	 * @param data
	 * @return 変更、挿入または更新が実行されたかどうかの行数とステータスオブジェクト
	 * @throws SQLException
	 */
	public Dao.CreateOrUpdateStatus createOrUpdate(RankingEntity data) {
		Dao.CreateOrUpdateStatus ret = new Dao.CreateOrUpdateStatus(false,
				false, 0);
		try {
			if (mRankingDao == null) {
				throw new RuntimeException("mRankingDao is null");
			} else {
				ret = mRankingDao.createOrUpdate(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
