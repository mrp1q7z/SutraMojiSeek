package com.yojiokisoft.sutramojiseek;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {
	private Paint mPaint;
	private String mKana;

	public MyButton(Context context) {
		super(context);
		mPaint = new Paint();
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mKana == null || mKana.length() == 0) {
			return;
		}

		int textSize = MyResource.dpi2Px(10);
		int height = (canvas.getHeight() - MyResource.dpi2Px(50)) / 5;
		int width = canvas.getWidth() / 5;

		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setTextSize(textSize);

		int len = mKana.length();
		int x = width - textSize - MyResource.dpi2Px(2);
		int y = ((height - (textSize * len)) / 2) + textSize;
		for (int i = 0; i < len; i++) {
			String moji = String.valueOf(mKana.charAt(i));
			canvas.drawText(moji, x, y, mPaint);
			y += textSize;
		}
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		mKana = "";
	}

	public void setKana(String kana) {
		mKana = kana;
	}
}
