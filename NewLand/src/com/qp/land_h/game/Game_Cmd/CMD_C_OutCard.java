package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;

/**
 * �û�����
 * 
 */
public class CMD_C_OutCard extends Cmd {

	public int nCardCount; // ������Ŀ
	public int[] nCardData = new int[GDF.MAX_CARDCOUNT]; // �˿�����

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
