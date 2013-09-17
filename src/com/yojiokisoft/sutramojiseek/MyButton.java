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
	private int mKanaLen;
	private int mTextSize;
	private int mKanaX;
	private int mKanaY;
	private int mHeight;
	private int mWidth;

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

		int y = mKanaY;
		for (int i = 0; i < mKanaLen; i++) {
			String moji = String.valueOf(mKana.charAt(i));
			canvas.drawText(moji, mKanaX, y, mPaint);
			y += mTextSize;
		}
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		mKana = "";
	}

	public void setKana(String kana) {
		mKana = kana;
		mTextSize = MyResource.dpi2Px(10);
		mKanaLen = mKana.length();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setTextSize(mTextSize);
		setXY();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mHeight = getHeight();
		mWidth = getWidth();
		if (mWidth >= 240) {
			mHeight = (mHeight - MyResource.dpi2Px(50)) / 5;
			mWidth /= 5;
		}
		setXY();
	}

	private void setXY() {
		mKanaX = mWidth - mTextSize - MyResource.dpi2Px(2);
		mKanaY = ((mHeight - (mTextSize * mKanaLen)) / 2) + mTextSize;
	}
}
