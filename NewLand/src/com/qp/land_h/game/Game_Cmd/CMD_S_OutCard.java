package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * �û�����
 * 
 */
public class CMD_S_OutCard extends Cmd {

	public int nCardCount; // ������Ŀ
	public int nCurrentUser; // ��ǰ���
	public int nOutCardUser; // �������
	public int[] nCardData = new int[GDF.MAX_CARDCOUNT]; // �˿��б�

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		nCardCount = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nOutCardUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		for (int i = 0; i < nCardCount; i++) {
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
