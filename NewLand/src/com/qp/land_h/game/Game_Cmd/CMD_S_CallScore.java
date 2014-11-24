package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * �û��з�
 * 
 * @author CHENPENG
 * 
 */
public class CMD_S_CallScore extends Cmd {
	public int nCurrentUser; // ��ǰ���
	public int nCallScoreUser; // �з����
	public int nCurrentScore; // ��ǰ�з�
	public int nUserCallScore; // �ϴνз�

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nCallScoreUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nCurrentScore = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nUserCallScore = (data[nIndex] < 0
				? (data[nIndex] + 256)
				: data[nIndex]);
		nIndex++;
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
