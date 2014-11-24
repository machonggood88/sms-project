package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 空闲状态
 * 
 */
public class CMD_S_StatusFree extends Cmd {
	// 游戏属性
	public long lCellScore; // 基础积分
	// 时间信息
	public byte cbTimeOutCard; // 出牌时间
	public byte cbTimeCallScore; // 叫分时间
	public byte cbTimeStarGame; // 开始时间
	public byte cbTimeHeadOutCard; // 首出时间

	// 历史积分
	public long[] lTurnScore = new long[GDF.GAME_PLAYER]; // 积分信息
	public long[] lCollectScore = new long[GDF.GAME_PLAYER]; // 积分信息

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lCellScore = Utility.read4Byte(data, nIndex);
		nIndex += 4;

		cbTimeOutCard = data[nIndex++];
		cbTimeCallScore = data[nIndex++];
		cbTimeStarGame = data[nIndex++];
		cbTimeHeadOutCard = data[nIndex++];

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
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
