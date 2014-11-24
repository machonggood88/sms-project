package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 放弃出牌
 * 
 */
public class CMD_S_PassCard extends Cmd {

	public int nTurnOver; // 一轮结束
	public int nCurrentUser; // 当前玩家
	public int nPassCardUser; // 放弃玩家

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		nTurnOver = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nPassCardUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
