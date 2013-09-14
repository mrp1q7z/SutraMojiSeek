package com.yojiokisoft.sutramojiseek;

public class State {
	private int mState;

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
		mState = state;
	}
}
