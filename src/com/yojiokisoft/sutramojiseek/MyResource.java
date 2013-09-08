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
 * リソース関連のユーティリティ
 */
public class MyResource {
	/**
	 * dpiをpxに変換
	 * 
	 * @param dpi
	 * @return px
	 */
	public static int dpi2Px(int dpi) {
		// TODO Auto-generated method stub
		// float density =
		// App.getInstance().getResources().getDisplayMetrics().density;
		float density = 1.5f;
		int px = (int) (dpi * density + 0.5f);
		return px;
	}

	/**
	 * @return ステータスバーの高さ
	 */
	public static int getStatusBarHeight() {
		return dpi2Px(25);
	}
}
