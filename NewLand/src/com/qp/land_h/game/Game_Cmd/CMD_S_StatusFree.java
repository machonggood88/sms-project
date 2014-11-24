package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * ����״̬
 * 
 */
public class CMD_S_StatusFree extends Cmd {
	// ��Ϸ����
	public long lCellScore; // ��������
	// ʱ����Ϣ
	public byte cbTimeOutCard; // ����ʱ��
	public byte cbTimeCallScore; // �з�ʱ��
	public byte cbTimeStarGame; // ��ʼʱ��
	public byte cbTimeHeadOutCard; // �׳�ʱ��

	// ��ʷ����
	public long[] lTurnScore = new long[GDF.GAME_PLAYER]; // ������Ϣ
	public long[] lCollectScore = new long[GDF.GAME_PLAYER]; // ������Ϣ

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
