package com.yojiokisoft.sutramojiseek;

public class SutraDao {
	private static final SutraDao mInstance = new SutraDao();
	private static final String mSutra = "仏説摩訶般若波羅蜜多心経観自在菩薩行深般若波羅蜜多時照見五蘊皆空度一切苦厄舎利子色不異空空不異色色即是空空即是色受想行識亦復如是舎利子是諸法空相不生不滅不垢不浄不増不減是故空中無色無受想行識無眼耳鼻舌身意無色声香味触法無眼界乃至無意識界無無明亦無無明尽乃至無老死亦無老死尽無苦集滅道無智亦無得以無所得故菩提薩埵依般若波羅蜜多故心無罣礙無罣礙故無有恐怖遠離一切顛倒夢想究竟涅槃三世諸仏依般若波羅蜜多故得阿耨多羅三藐三菩提故知般若波羅蜜多是大神呪是大明呪是無上呪是無等等呪能除一切苦真実不虚故説般若波羅蜜多呪即説呪曰羯諦羯諦波羅羯諦波羅僧羯諦菩提薩婆訶般若心経";
	private static final String[] mKana = { "ぶっ", "せつ", "ま", "か", "はん", "にゃ",
			"は", "ら", "み", "た", "しん", "ぎょう", "かん", "じ", "ざい", "ぼ", "さつ", "ぎょう",
			"じん", "はん", "にゃ", "は", "ら", "みっ", "た", "じ", "しょう", "けん", "ご", "うん",
			"かい", "くう", "ど", "いっ", "さい", "く", "やく", "しゃ", "り", "し", "しき", "ふ",
			"い", "くう", "くう", "ふ", "い", "しき", "しき", "そく", "ぜ", "くう", "くう", "そく",
			"ぜ", "しき", "じゅ", "そう", "ぎょう", "しき", "やく", "ぶ", "にょ", "ぜ", "しゃ",
			"り", "し", "ぜ", "しょ", "ほう", "くう", "そう", "ふ", "しょう", "ふ", "めつ", "ふ",
			"く", "ふ", "じょう", "ふ", "ぞう", "ふ", "げん", "ぜ", "こ", "くう", "ちゅう", "む",
			"しき", "む", "じゅ", "そう", "ぎょう", "しき", "む", "げん", "に", "び", "ぜっ",
			"しん", "い", "む", "しき", "しょう", "こう", "み", "そく", "ほう", "む", "げん",
			"かい", "ない", "し", "む", "い", "しき", "かい", "む", "む", "みょう", "やく", "む",
			"む", "みょう", "じん", "ない", "し", "む", "ろう", "し", "やく", "む", "ろう", "し",
			"じん", "む", "く", "しゅう", "めつ", "どう", "む", "ち", "やく", "む", "とく", "い",
			"む", "しょ", "とく", "こ", "ぼ", "だい", "さっ", "た", "え", "はん", "にゃ", "は",
			"ら", "みっ", "た", "こ", "しん", "む", "けい", "げ", "む", "けい", "げ", "こ",
			"む", "う", "く", "ふ", "おん", "り", "いっ", "さい", "てん", "どう", "む", "そう",
			"く", "きょう", "ね", "はん", "さん", "ぜ", "しょ", "ぶつ", "え", "はん", "にゃ", "は",
			"ら", "みっ", "た", "こ", "とく", "あ", "のく", "た", "ら", "さん", "みゃく", "さん",
			"ぼ", "だい", "こ", "ち", "はん", "にゃ", "は", "ら", "みっ", "た", "ぜ", "だい",
			"じん", "しゅ", "ぜ", "だい", "みょう", "しゅ", "ぜ", "む", "じょう", "しゅ", "ぜ",
			"む", "とう", "どう", "しゅ", "のう", "じょ", "いっ", "さい", "く", "しん", "じつ",
			"ふ", "こ", "こ", "せつ", "はん", "にゃ", "は", "ら", "みっ", "た", "しゅ", "そく",
			"せつ", "しゅ", "わつ", "ぎゃ", "てい", "ぎゃ", "てい", "は", "ら", "ぎゃ", "てい",
			"は", "ら", "そう", "ぎゃ", "てい", "ぼ", "じ", "そ", "わ", "か", "はん", "にゃ",
			"しん", "ぎょう" };
	private int mIndex = 0;
	private boolean mEof = false;

	private SutraDao() {
	}

	public static SutraDao getInstance() {
		return mInstance;
	}

	public void seek(int pos) {
		if (pos < 0) {
			throw new RuntimeException("pos is less than 0");
		}
		if (pos >= mSutra.length()) {
			throw new RuntimeException("pos is over than length");
		}
		mIndex = pos;
		mEof = false;
	}

	public String read() {
		if (mEof) {
			return null;
		}

		String ret = mSutra.substring(mIndex, mIndex + 1);
		mIndex++;
		if (mIndex >= mSutra.length()) {
			mEof = true;
		}
		return ret;
	}

	public boolean eof() {
		return mEof;
	}

	public int getCurrentPos() {
		return mIndex;
	}

	public String[] randomRead(String omit, int len) {
		String[] ret = new String[len];
		int max = mSutra.length() - 1;
		int index;
		int cnt = 0;

		// 指定文字数の１０倍の間でランダムで読み込む
		for (int i = 0; i < len * 10; i++) {
			index = MyRandom.random(0, max);
			ret[cnt] = mSutra.substring(index, index + 1);
			// 除外する文字？
			if (ret[cnt].equals(omit)) {
				continue;
			}
			// 既に同じ文字がある？
			boolean continueFlag = false;
			for (int j = 0; j < cnt; j++) {
				if (ret[j].equals(ret[cnt])) {
					continueFlag = true;
					break;
				}
			}
			if (continueFlag) {
				continue;
			}
			// 指定文字数分読み込んだ？
			cnt++;
			if (cnt >= len) {
				break;
			}
		}
		// 指定文字数分に満たない部分を補う
		for (int i = cnt; i < len; i++) {
			ret[i] = "*";
		}

		return ret;
	}

	public String getKana() {
		int index = mIndex - 1;
		if (index < 0 || index > mKana.length) {
			return null;
		}
		return mKana[index];
	}

	public int getLength() {
		return mSutra.length();
	}
}
