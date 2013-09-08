package com.yojiokisoft.sutramojiseek;

import java.util.Random;

public class MyRandom {
	private static Random mRandom = new Random();

	/**
	 * 整数の乱数を発生させる
	 * 
	 * @param start 開始（この値を含む）
	 * @param end 終了（この値を含む）
	 * @return 乱数
	 */
	static public final int random(int start, int end) {
		return start + mRandom.nextInt(end - start + 1);
	}
}
