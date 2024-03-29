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

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

/**
 * 使い方アクティビティ
 */
public class UsageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usage);

		WebView webView = (WebView) findViewById(R.id.webView);
		if (Locale.JAPAN.equals(Locale.getDefault())) {
			webView.loadUrl("file:///android_asset/usage-ja.html");
		} else {
			webView.loadUrl("file:///android_asset/usage.html");
		}
	}
}
