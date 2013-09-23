package com.yojiokisoft.sutramojiseek;

public class State {
	private int mState = S_INIT;
	private long mStartTime;
	private long mEndTime;

	/** 初期状態 */
	public static final int S_INIT = 0;

	/** プレイ状態 */
	public static final int S_PLAY = 1;

	/** ポーズ状態 */
	public static final int S_PAUSE = 2;

	/** スコア状態 */
	public static final int S_SCORE = 3;

	public int getState() {
		return mState;
	}

	public void setState(int state) {
		if (mState == S_INIT && state == S_PLAY) {
			mStartTime = System.currentTimeMillis();
		}
		if (mState == S_PLAY && state == S_SCORE) {
			mEndTime = System.currentTimeMillis();
		}
		mState = state;
	}
	
	public long getPlayTime() {
		return (mEndTime - mStartTime);
	}
}
