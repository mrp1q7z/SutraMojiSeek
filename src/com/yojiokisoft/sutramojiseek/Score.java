package com.yojiokisoft.sutramojiseek;

public class Score {
	static final String[] mLevel = { "釈迦如来", "阿閃如来", "大日如来", "阿弥陀如来", "薬師如来",
			"多宝如来", "宝生如来", "地蔵菩薩", "弥勒菩薩", "文殊菩薩", "観音菩薩", "千手観音", "勢至菩薩",
			"普賢菩薩", "日光菩薩", "月光菩薩", "虚空蔵菩薩", "不動明王", "降三世明王", "軍荼利明王",
			"大威徳夜叉明王", "金剛夜叉明王", "愛染明王", "烏瑟沙摩明王", "弁財天", "大黒天", "毘沙門天", "吉祥天",
			"韋駄天", "帝釈天", "摩利支天", "歓喜天", "梵天", "お地蔵さん", "人間" };

	private static int getScore(int okCnt, int ngCnt, int skipCnt,
			long playTime, int speed) {
		int score = okCnt * 1000 * speed;
		score -= ngCnt * 100;
		score -= skipCnt * 200;
		score += ((181650 * (5 - speed) - playTime) / 1000) * 100;

		return score;
	}

	private static String getLevel(int score) {
		String level;
		if (score >= 1000000) {
			level = mLevel[0];
		} else if (0 <= score && score < 50000) {
			level = mLevel[mLevel.length - 2];
		} else if (score < 0) {
			level = mLevel[mLevel.length - 1];
		} else {
			int index = (999999 - score) / 30000;
			if (index < 0 || index > mLevel.length) {
				level = "Error";
			} else {
				level = mLevel[index + 1];
			}
		}

		return level;
	}

	public static RankingEntity getRanking(int okCnt, int ngCnt, int skipCnt,
			long playTime, int speed) {
		int score = getScore(okCnt, ngCnt, skipCnt, playTime, speed);
		String level = getLevel(score);

		RankingEntity data = new RankingEntity();
		data.score = score;
		data.okCnt = okCnt;
		data.ngCnt = ngCnt;
		data.skipCnt = skipCnt;
		data.playTime = playTime;
		data.level = level;
		data.lastUpdateTime = System.currentTimeMillis();

		return data;
	}

	public static String getScoreDetail(RankingEntity ranking) {
		return "OK:" + ranking.okCnt + " NG:" + ranking.ngCnt + " SK:"
				+ ranking.skipCnt + " TM:" + ranking.playTime / 1000;
	}
}
