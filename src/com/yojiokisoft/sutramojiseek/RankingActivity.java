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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ランキングアクティビティ
 */
public class RankingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);

		List<RankingEntity> list = null;
		RankingDao cardDao = new RankingDao();
		list = cardDao.queryForAll();
		if (list == null || list.size() <= 0) {
			Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
			return;
		}
		BaseAdapter adapter = new RankingAdapter(this, list);
		ListView listView = (ListView) findViewById(R.id.ranking);
		listView.setAdapter(adapter);
	}

	private class RankingAdapter extends BaseAdapter {
		private Activity mActivity;
		private List<RankingEntity> mItems;

		/**
		 * コンストラクタ.
		 * 
		 * @param activity
		 * @param items
		 */
		public RankingAdapter(Activity activity, List<RankingEntity> items) {
			super();
			mActivity = activity;
			mItems = items;
		}

		/**
		 * @see BaseAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return mItems.size();
		}

		/**
		 * @see BaseAdapter#getItem(int)
		 */
		@Override
		public Object getItem(int pos) {
			return mItems.get(pos);
		}

		/**
		 * @see BaseAdapter#getItemId(int)
		 */
		@Override
		public long getItemId(int pos) {
			return pos;
		}

		/**
		 * @see BaseAdapter#getView(int, View, ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewWrapper wrapper = null;

			if (view == null) {
				view = mActivity.getLayoutInflater().inflate(
						R.layout.row_ranking, null);
				wrapper = new ViewWrapper(view);
				view.setTag(wrapper);
			} else {
				wrapper = (ViewWrapper) view.getTag();
			}

			RankingEntity item = mItems.get(position);
			wrapper.no.setText("" + (position + 1));
			wrapper.score.setText("" + item.score);
			wrapper.cnt.setText(Score.getScoreDetail(item));
			wrapper.level.setText(item.level);
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy/MM/dd(E) HH:mm:ss", Locale.JAPANESE);
			wrapper.date.setText(sdf.format(item.lastUpdateTime));

			return view;
		}

		/**
		 * ビューを扱いやすくするためのラッパー.
		 */
		private class ViewWrapper {
			public final TextView no;
			public final TextView score;
			public final TextView cnt;
			public final TextView level;
			public final TextView date;

			ViewWrapper(View view) {
				this.no = (TextView) view.findViewById(R.id.no);
				this.score = (TextView) view.findViewById(R.id.score);
				this.cnt = (TextView) view.findViewById(R.id.cnt);
				this.level = (TextView) view.findViewById(R.id.level);
				this.date = (TextView) view.findViewById(R.id.date);
			}
		}
	}
}
