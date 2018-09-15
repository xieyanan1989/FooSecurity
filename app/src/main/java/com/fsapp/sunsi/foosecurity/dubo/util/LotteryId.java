package com.fsapp.sunsi.foosecurity.dubo.util;
import com.fsapp.sunsi.foosecurity.R;

public class LotteryId {
	public static String INTENT_LID = "lid";

	/** 所有彩种 **/
	public static final String All = "";
	/** 双色球 **/
	public static final String SSQ = "100";
	/** 七乐彩 **/
	public static final String QLC = "101";
	/** 福彩3D **/
	public static final String D3 = "102";
	/** 快乐十分 **/
	public static final String KL10 = "103";
	/** 时时彩 **/
	public static final String SSC = "104";

	/** 大乐透 **/
	public static final String DLT = "200";
	/** 排列3 **/
	public static final String PL3 = "201";
	/** 排列五 **/
	public static final String PL5 = "202";
	/** 七星彩 **/
	public static final String QXC = "203";
	/** 11选5 **/
	public static final String SYXW = "204";
	/** 泳坛夺金 **/
	public static final String YTDJ = "212";

	/** 足彩 表示以下四个 **/
	public static final String CTZC = "205678";
	/** 足彩 14场 **/
	public static final String CTZC14 = "205";
	/** 足彩 9场 **/
	public static final String CTZC9 = "206";
	/** 足彩 6场半全场 **/
	public static final String CTZC6 = "207";
	/** 足彩 4场进球彩 **/
	public static final String CTZC4 = "208";
	/** 竞彩足球 **/
	public static final String JCZQ = "209";
	/** 竞彩篮球 **/
	public static final String JCLQ = "210";
	/** 北京单场 **/
	public static final String BJDC = "211";

	/** 玩法 01 **/
	public static final String PLAY_ID_01 = "01";
	/** 玩法 02 **/
	public static final String PLAY_ID_02 = "02";
	/** 玩法 03 **/
	public static final String PLAY_ID_03 = "03";
	/** 玩法 04 **/
	public static final String PLAY_ID_04 = "04";
	/** 玩法 05 **/
	public static final String PLAY_ID_05 = "05";
	/** 玩法 06 **/
	public static final String PLAY_ID_06 = "06";
	/** 玩法 07 **/
	public static final String PLAY_ID_07 = "07";
	/** 玩法 08 **/
	public static final String PLAY_ID_08 = "08";
	/** 玩法 09 **/
	public static final String PLAY_ID_09 = "09";
	/** 玩法 10 **/
	public static final String PLAY_ID_10 = "10";
	/** 玩法 11 **/
	public static final String PLAY_ID_11 = "11";
	/** 玩法 12 **/
	public static final String PLAY_ID_12 = "12";
	/** 玩法 13 **/
	public static final String PLAY_ID_13 = "13";
	/** 玩法 14 **/
	public static final String PLAY_ID_14 = "14";
	/** 玩法 15 **/
	public static final String PLAY_ID_15 = "15";
	/** 玩法 16 **/
	public static final String PLAY_ID_16 = "16";
	/** 玩法 17 **/
	public static final String PLAY_ID_17 = "17";
	/** 玩法 18 **/
	public static final String PLAY_ID_18 = "18";
	/** 玩法 19 **/
	public static final String PLAY_ID_19 = "19";

	/** 选号方式 01 **/
	public static final String POLL_ID_01 = "01";
	/** 选号方式 02 **/
	public static final String POLL_ID_02 = "02";
	/** 选号方式 03 **/
	public static final String POLL_ID_03 = "03";
	/** 选号方式 04 **/
	public static final String POLL_ID_04 = "04";
	/** 选号方式 05 **/
	public static final String POLL_ID_05 = "05";
	/** 选号方式 06 **/
	public static final String POLL_ID_06 = "06";
	/** 选号方式 07 **/
	public static final String POLL_ID_07 = "07";
	/** 选号方式 08 **/
	public static final String POLL_ID_08 = "08";
	/** 选号方式 09 **/
	public static final String POLL_ID_09 = "09";
	/** 选号方式 10 **/
	public static final String POLL_ID_10 = "10";
	/** 图表基本地址**/
//	public static final String URL = "http://tu.ez500.cn/SCS/html/lottery/v3/index.html?";
//	public static final String URL = "http://115.29.168.217/lottery/v3/lottery.html?";
	public static final String URL = "http://119.90.38.216:28084/lottery/v3/lottery.html?";

	/** 彩种name，根据彩种id查询彩种name **/
	public static String getLotteryName(String ltType) {
		return getLotteryName(ltType, "01");
	}

	public static String getLotteryName(String ltType, String playId) {
		if (SSQ.equals(ltType)) {
			return "双色球";
		} else if (D3.equals(ltType)) {
			return "福彩3D";
		} else if (KL10.equals(ltType)) {
			return "快乐十分";
		} else if (SSC.equals(ltType)) {
			return "时时彩";
		} else if (PL3.equals(ltType)) {
			return "排列三";
		} else if (DLT.equals(ltType)) {
			return "大乐透";
		} else if (QLC.equals(ltType)) {
			return "七乐彩";
		} else if (PL5.equals(ltType)) {
			return "排列五";
		} else if (QXC.equals(ltType)) {
			return "七星彩";
		} else if (SYXW.equals(ltType)) {
			return "11选5";
		} else if (CTZC.equals(ltType)) {
			return "足彩";
		} else if (CTZC14.equals(ltType)) {
			return "十四场";
		} else if (CTZC9.equals(ltType)) {
			return "任选九";
		} else if (CTZC6.equals(ltType)) {
			return "6场半全场";
		} else if (CTZC4.equals(ltType)) {
			return "4场进球彩";
		} else if (BJDC.equals(ltType)) {
			return "北京单场";
		} else if (JCZQ.equals(ltType)) {
			return "竞彩足球";
		} else if (JCLQ.equals(ltType)) {
			return "竞彩篮球";
		} else if (YTDJ.equals(ltType)) {
			return "游坛夺金";
		} else if (All.equals(ltType)) {
			return "全部";
		} else {
			return "未知彩种";
		}
	}

	/** 彩种id，根据彩种name查询彩种id **/
	public static String getLotteryLid(String ltType) {
		if ("双色球".equals(ltType)) {
			return SSQ;
		} else if ("福彩3D".equals(ltType)) {
			return D3;
		} else if ("快乐十分".equals(ltType)) {
			return KL10;
		} else if ("时时彩".equals(ltType)) {
			return SSC;
		} else if ("排列三".equals(ltType)) {
			return PL3;
		} else if ("大乐透".equals(ltType)) {
			return DLT;
		} else if ("七乐彩".equals(ltType)) {
			return QLC;
		} else if ("排列五".equals(ltType)) {
			return PL5;
		} else if ("七星彩".equals(ltType)) {
			return QXC;
		} else if ("11选5".equals(ltType)) {
			return SYXW;
		} else if ("十四场".equals(ltType)) {
			return CTZC14;
		} else if ("任选九".equals(ltType)) {
			return CTZC9;
		} else if ("6场半全场".equals(ltType)) {
			return CTZC6;
		} else if ("4场进球彩".equals(ltType)) {
			return CTZC4;
		} else if ("北京单场".equals(ltType)) {
			return BJDC;
		} else if ("竞彩足球".equals(ltType)) {
			return JCZQ;
		} else if ("竞彩篮球".equals(ltType)) {
			return JCLQ;
		} else if ("全部彩种".equals(ltType)) {
			return All;
		} else if ("全部".equals(ltType)) {
			return All;
		} else {
			return "---";
		}
	}

	public static String getPollType(String lid, String playId, String pollId) {
		if (lid.equals(D3) || lid.equals(PL3)) {
			if (playId.equals("01") && pollId.equals("01"))
				return "直选单式";
			if (playId.equals("01") && pollId.equals("02"))
				return "直选复式";
			if (playId.equals("02") && pollId.equals("01"))
				return "组三单式";
			if (playId.equals("02") && pollId.equals("02"))
				return "组三复式";
			if (playId.equals("03"))
				return "组六投注";
			if (playId.equals("01") && pollId.equals("13"))
				return "直选过滤";
			if (playId.equals("02") && pollId.equals("10"))
				return "组三直选过滤";
			if (playId.equals("02") && pollId.equals("11"))
				return "组三复选过滤";
			if (playId.equals("03") && pollId.equals("12"))
				return "组六过滤";
		}

		if (lid.equals(KL10)) {
			if (pollId.equals("03")) {
				if (playId.equals("03"))
					return "快乐二胆拖";
				if (playId.equals("04"))
					return "二连组胆拖";
				if (playId.equals("06"))
					return "快乐三胆拖";
				if (playId.equals("07"))
					return "前三组胆拖";
				if (playId.equals("09"))
					return "选四胆拖";
				if (playId.equals("10"))
					return "选五胆拖";
			} else {
				if (playId.equals("01"))
					return "首位数投";
				if (playId.equals("02"))
					return "首位红投";
				if (playId.equals("03"))
					return "快乐二";
				if (playId.equals("04"))
					return "二连组";
				if (playId.equals("05"))
					return "二连直";
				if (playId.equals("06"))
					return "快乐三";
				if (playId.equals("07"))
					return "前三组";
				if (playId.equals("08"))
					return "前三直";
				if (playId.equals("09"))
					return "选四投注";
				if (playId.equals("10"))
					return "选五投注";
			}
		}

		if (lid.equals(SSC)) {
			if (pollId.equals("03")) {
				if (playId.equals("07"))
					return "二星组选胆拖";
				if (playId.equals("08"))
					return "组三胆拖";
				if (playId.equals("09"))
					return "组六胆拖";

			} else {
				if (playId.equals("01"))
					return "一星直选";
				if (playId.equals("02"))
					return "二星直选";
				if (playId.equals("03"))
					return "三星直选";
				if (playId.equals("04"))
					return "四星直选";
				if (playId.equals("05"))
					return "五星直选";
				if (playId.equals("06"))
					return "五星通选";
				if (playId.equals("07"))
					return "二星组选";
				if (playId.equals("08") && pollId.equals("01"))
					return "组三单式";
				if (playId.equals("08") && pollId.equals("02"))
					return "组三复式";
				if (playId.equals("09"))
					return "三星组六";
				if (playId.equals("10"))
					return "任一";
				if (playId.equals("11"))
					return "任二";
				if (playId.equals("12"))
					return "任三";
				if (playId.equals("13"))
					return "大小单双";
				if (playId.equals("14"))
					return "趣味二星";
				if (playId.equals("15"))
					return "区间二星";
				if (playId.equals("03") && pollId.equals("13"))
					return "三星直选过滤";
				if (playId.equals("08") && pollId.equals("10"))
					return "三星组三单式过滤";
				if (playId.equals("08") && pollId.equals("11"))
					return "三星组三复式过滤";
				if (playId.equals("09") && pollId.equals("12"))
					return "三星组六过滤";
			}
		}

		if (lid.equals(SYXW)) {
			if (pollId.equals("03")) {
				if (playId.equals("01"))
					return "任二胆拖";
				if (playId.equals("02"))
					return "任三胆拖";
				if (playId.equals("03"))
					return "任四胆拖";
				if (playId.equals("04"))
					return "任五胆拖";
				if (playId.equals("05"))
					return "任六胆拖";
				if (playId.equals("06"))
					return "任七胆拖";
				if (playId.equals("07"))
					return "任八胆拖";
				if (playId.equals("11"))
					return "前二组选胆拖";
				if (playId.equals("12"))
					return "前三组选胆拖";
			} else {
				if (playId.equals("01"))
					return "任二";
				if (playId.equals("02"))
					return "任三";
				if (playId.equals("03"))
					return "任四";
				if (playId.equals("04"))
					return "任五";
				if (playId.equals("05"))
					return "任六";
				if (playId.equals("06"))
					return "任七";
				if (playId.equals("07"))
					return "任八";
				if (playId.equals("08"))
					return "前一直选";
				if (playId.equals("09"))
					return "前二直选";
				if (playId.equals("10"))
					return "前三直选";
				if (playId.equals("11"))
					return "前二组选";
				if (playId.equals("12"))
					return "前三组选";
			}
		}

		if (lid.equals(YTDJ)) {
			if (pollId.equals("03")) {
				if (playId.equals("05"))
					return "组选24胆拖";
			} else {
				if (playId.equals("01"))
					return "直选投注";
				if (playId.equals("02"))
					return "任选一";
				if (playId.equals("03"))
					return "任选二";
				if (playId.equals("04"))
					return "任选三";
				if (playId.equals("05"))
					return "组选24";
				if (playId.equals("06"))
					return "组选12";
				if (playId.equals("07"))
					return "组选6";
				if (playId.equals("08"))
					return "组选4";
			}
		}

		if (lid.equals(JCZQ)) {
			if (playId.equals("01"))
				return "胜平负";
			if (playId.equals("02"))
				return "让球胜平负";
			if (playId.equals("03"))
				return "总进球数";
			if (playId.equals("04"))
				return "半全场";
			if (playId.equals("05"))
				return "比分";
			if (playId.equals("06"))
				return "混合过关";
		}

		if (lid.equals(JCLQ)) {
			if (playId.equals("01"))
				return "胜负";
			if (playId.equals("02"))
				return "让分胜负";
			if (playId.equals("03"))
				return "胜分差";
			if (playId.equals("04"))
				return "大小分";
			if (playId.equals("05"))
				return "混合过关";
		}

		if (lid.equals(BJDC)) {
			if (playId.equals("01"))
				return "让球胜平负";
			if (playId.equals("02"))
				return "上下单双";
			if (playId.equals("03"))
				return "总进球";
			if (playId.equals("04"))
				return "半全场胜平负";
			if (playId.equals("05"))
				return "单场比分";
		}

		if (lid.equals(CTZC14) || lid.equals(CTZC4) || lid.equals(CTZC9)
				|| lid.equals(CTZC6)) {
			return "普通投注";
		}

		if (pollId.equals("01"))
			return "普通投注";
		if (pollId.equals("02"))
			return "复式投注";
		if (pollId.equals("03"))
			return "胆拖投注";
		return "";
	}


	public static String getUrl(String lid, String playId) {
		String url=null;
		 if (lid.equals(PL3)) {
//			if (playId.equals("01"))
				url=  URL+"id="+PL3+"&type="+1;
//			if (playId.equals("02"))
//				url=  URL+"id="+PL3+"&type="+2;
//			if (playId.equals("03"))
//				url=  URL+"id="+PL3+"&type="+3;
		} else if (lid.equals(KL10)) {
			if (playId.equals("01"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("02"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("03"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("04"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("05"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("06"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("07"))
				url=  URL+"id="+KL10+"&type="+8;
			if (playId.equals("08"))
				url=  URL+"id="+KL10+"&type="+8;
			if (playId.equals("09"))
				url=  URL+"id="+KL10+"&type="+1;
			if (playId.equals("10"))
				url=  URL+"id="+KL10+"&type="+1;
		} else if (lid.equals(SSC)) {
			if (playId.equals("01"))
				url=  URL+"id="+SSC+"&type="+1;
			if (playId.equals("02"))
				url=  URL+"id="+SSC+"&type="+2;
			if (playId.equals("03"))
				url=  URL+"id="+SSC+"&type="+3;
			if (playId.equals("04"))
				url=  URL+"id="+SSC+"&type="+4;
			if (playId.equals("05"))
				url=  URL+"id="+SSC+"&type="+5;
			if (playId.equals("06"))
				url=  URL+"id="+SSC+"&type="+6;
			if (playId.equals("07"))
				url=  URL+"id="+SSC+"&type="+7;
			if (playId.equals("08"))
				url=  URL+"id="+SSC+"&type="+8;
			if (playId.equals("09"))
				url=  URL+"id="+SSC+"&type="+9;
			if (playId.equals("10"))
				url=  URL+"id="+SSC+"&type="+10;
			if (playId.equals("11"))
				url=  URL+"id="+SSC+"&type="+11;
			if (playId.equals("12"))
				url=  URL+"id="+SSC+"&type="+12;
			if (playId.equals("13"))
				url=  URL+"id="+SSC+"&type="+13;
			if (playId.equals("14"))
				url=  URL+"id="+SSC+"&type="+14;
			if (playId.equals("15"))
				url=  URL+"id="+SSC+"&type="+15;
		} else if (lid.equals(YTDJ)) {
			if (playId.equals("01"))
				url=  URL+"id="+YTDJ+"&type="+1;
			if (playId.equals("02"))
				url=  URL+"id="+YTDJ+"&type="+2;
			if (playId.equals("03"))
				url=  URL+"id="+YTDJ+"&type="+3;
			if (playId.equals("04"))
				url=  URL+"id="+YTDJ+"&type="+4;
			if (playId.equals("05"))
				url=  URL+"id="+YTDJ+"&type="+5;
			if (playId.equals("06"))
				url=  URL+"id="+YTDJ+"&type="+6;
			if (playId.equals("07"))
				url=  URL+"id="+YTDJ+"&type="+7;
			if (playId.equals("08"))
				url=  URL+"id="+YTDJ+"&type="+8;
		} else if (lid.equals(SYXW)) {
			if (playId.equals("01"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("02"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("03"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("04"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("05"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("06"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("07"))
				url=  URL+"id="+SYXW+"&type="+1;
			if (playId.equals("08"))
				url=  URL+"id="+SYXW+"&type="+8;
			if (playId.equals("09"))
				url=  URL+"id="+SYXW+"&type="+11;
			if (playId.equals("10"))
				url=  URL+"id="+SYXW+"&type="+12;
			if (playId.equals("11"))
				url=  URL+"id="+SYXW+"&type="+11;
			if (playId.equals("12"))
				url=  URL+"id="+SYXW+"&type="+12;
		}else{
			url=URL+"id="+lid+"&type="+1;
		}
		return url;
	}

	public static String getRandomPly(String lotteryId, String mPlayId, String mPollId) {
		if(lotteryId.equals("200") || lotteryId.equals("101")){
			return "单式(机)";
		}
		
		if(lotteryId.equals("102")){
			if (mPlayId.equals("01") && mPollId.equals("01"))
				return "直选单式(机)";
			if (mPlayId.equals("02") && mPollId.equals("01"))
				return "组三单式(机)";
			if (mPlayId.equals("03") && mPollId.equals("01"))
				return "组六投注(机)";
		}
		
		if(lotteryId.equals("103")){
			if (mPlayId.equals("06") && mPollId.equals("01"))
				return "快乐三(机)";
			if (mPlayId.equals("07") && mPollId.equals("01"))
				return "前三组(机)";
			if (mPlayId.equals("08") && mPollId.equals("01"))
				return "前三直(机)";
			if (mPlayId.equals("09") && mPollId.equals("01"))
				return "任选四(机)";
			if (mPlayId.equals("10") && mPollId.equals("01"))
				return "任选五(机)";
		}
		
		if(lotteryId.equals("104")){
			if (mPlayId.equals("01"))
				return "一星直选(机)";
			if (mPlayId.equals("02"))
				return "二星直选(机)";
			if (mPlayId.equals("03"))
				return "三星直选(机)";
			if (mPlayId.equals("04"))
				return "四星直选(机)";
			if (mPlayId.equals("05"))
				return "五星直选(机)";
			if (mPlayId.equals("06"))
				return "五星通选(机)";
			if (mPlayId.equals("07"))
				return "二星组选(机)";
			if (mPlayId.equals("08"))
				return "三星组三(机)";
			if (mPlayId.equals("09"))
				return "三星组六(机)";
			if (mPlayId.equals("13"))
				return "大小单双(机)";
			if (mPlayId.equals("14"))
				return "趣味二星(机)";
			if (mPlayId.equals("15"))
				return "区间二星(机)";
		}
		return null;
	}
}
