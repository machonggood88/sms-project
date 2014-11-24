package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * �����˿�
 * 
 */
public class CMD_S_GameStart extends Cmd {
	public int nStartUser; // ��ʼ���
	public int nCurrentUser; // ��ǰ���
	public int nValidCardData; // �b���˿�
	public int nValidCardDIndex; // �b��λ��
	public int[] nCardData = new int[GDF.NORMAL_COUNT]; // �˿��б�

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		nStartUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nValidCardData = (data[nIndex] < 0
				? (data[nIndex] + 256)
				: data[nIndex]);
		nIndex++;
		nValidCardDIndex = (data[nIndex] < 0
				? (data[nIndex] + 256)
				: data[nIndex]);
		nIndex++;
		for (int i = 0; i < GDF.NORMAL_COUNT; i++) {
			nCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
