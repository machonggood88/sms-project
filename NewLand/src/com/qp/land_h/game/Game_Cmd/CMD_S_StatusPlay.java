package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 游戏状态
 * 
 */
public class CMD_S_StatusPlay extends Cmd {

	// 时间信息
	public byte cbTimeOutCard; // 出牌时间
	public byte cbTimeCallScore; // 叫分时间
	public byte cbTimeStarGame; // 开始时间
	public byte cbTimeHeadOutCard; // 首出时间

	// 游戏变量
	public long lCellScore; // 单元积分
	public byte cbBombCount; // 炸弹次数
	public int nBankerUser; // 庄家用户
	public int nCurrentUser; // 当前玩家
	public byte cbBankerScore; // 庄家叫分

	// 出牌信息
	public int nTurnWiner; // 胜利玩家
	public int nTurnCardCount; // 出牌数目
	public int[] nTurnCardData = new int[GDF.MAX_CARDCOUNT]; // 出牌数据

	// 扑克信息
	public int[] nBankerCard = new int[3]; // 游戏底牌
	public int[] nHandCardData = new int[GDF.MAX_CARDCOUNT]; // 手上扑克
	public int[] nHandCardCount = new int[GDF.GAME_PLAYER]; // 扑克数目

	// 历史积分
	public long[] lTurnScore = new long[GDF.GAME_PLAYER]; // 积分信息
	public long[] lCollectScore = new long[GDF.GAME_PLAYER]; // 积分信息

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cbTimeOutCard = data[nIndex++];
		cbTimeCallScore = data[nIndex++];
		cbTimeStarGame = data[nIndex++];
		cbTimeHeadOutCard = data[nIndex++];

		lCellScore = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		cbBombCount = data[nIndex++];
		nBankerUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		cbBankerScore = data[nIndex++];

		nTurnWiner = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nTurnCardCount = (data[nIndex] < 0
				? (data[nIndex] + 256)
				: data[nIndex]);
		nIndex++;

		for (int i = 0; i < GDF.MAX_CARDCOUNT; i++) {
			nTurnCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < 3; i++) {
			nBankerCard[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.MAX_CARDCOUNT; i++) {
			nHandCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nHandCardCount[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			lTurnScore[i] = Utility.read8Byte(data, nIndex);
			nIndex += 8;
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			lCollectScore[i] = Utility.read8Byte(data, nIndex);
			nIndex += 8;
		}
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
