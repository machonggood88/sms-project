package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;

/**
 * 用户叫分
 * 
 */
public class CMD_C_CallScore extends Cmd {

	public int nCallScore; // 叫分数目

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = (byte) nCallScore;
		return nIndex - pos;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
