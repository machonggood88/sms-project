package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * ��������
 * 
 */
public class CMD_S_PassCard extends Cmd {

	public int nTurnOver; // һ�ֽ���
	public int nCurrentUser; // ��ǰ���
	public int nPassCardUser; // �������

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
