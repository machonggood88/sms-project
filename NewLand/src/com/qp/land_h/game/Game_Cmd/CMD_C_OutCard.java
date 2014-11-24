package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;

/**
 * 用户出牌
 * 
 */
public class CMD_C_OutCard extends Cmd {

	public int nCardCount; // 出牌数目
	public int[] nCardData = new int[GDF.MAX_CARDCOUNT]; // 扑克数据

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = (byte) nCardCount;
		for (int i = 0; i < nCardCount; i++) {
			data[nIndex++] = (byte) nCardData[i];
		}
		return nIndex - pos;
	}

}
