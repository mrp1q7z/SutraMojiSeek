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

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * カード情報エンティティ
 */
@DatabaseTable(tableName = "ranking")
public class RankingEntity implements Serializable {
	/**
	 * シリアライズのリビジョン番号
	 */
	private static final long serialVersionUID = -4478603042434774346L;

	/** ID */
	@DatabaseField(generatedId = true)
	public int id;

	/** スコア */
	@DatabaseField
	public int score;

	/** 正解の数 */
	@DatabaseField
	public int okCnt;

	/** 誤りの数 */
	@DatabaseField
	public int ngCnt;

	/** スキップの数 */
	@DatabaseField
	public int skipCnt;

	/** 読み上げる速さ */
	@DatabaseField
	public int speed;

	/** プレイタイム */
	@DatabaseField
	public long playTime;

	/** レベル */
	@DatabaseField
	public String level;

	/** 最終更新日時 */
	@DatabaseField
	public long lastUpdateTime;
}
