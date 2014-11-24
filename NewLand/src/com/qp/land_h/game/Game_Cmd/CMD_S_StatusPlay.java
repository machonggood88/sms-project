package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * ��Ϸ״̬
 * 
 */
public class CMD_S_StatusPlay extends Cmd {

	// ʱ����Ϣ
	public byte cbTimeOutCard; // ����ʱ��
	public byte cbTimeCallScore; // �з�ʱ��
	public byte cbTimeStarGame; // ��ʼʱ��
	public byte cbTimeHeadOutCard; // �׳�ʱ��

	// ��Ϸ����
	public long lCellScore; // ��Ԫ����
	public byte cbBombCount; // ը������
	public int nBankerUser; // ׯ���û�
	public int nCurrentUser; // ��ǰ���
	public byte cbBankerScore; // ׯ�ҽз�

	// ������Ϣ
	public int nTurnWiner; // ʤ�����
	public int nTurnCardCount; // ������Ŀ
	public int[] nTurnCardData = new int[GDF.MAX_CARDCOUNT]; // ��������

	// �˿���Ϣ
	public int[] nBankerCard = new int[3]; // ��Ϸ����
	public int[] nHandCardData = new int[GDF.MAX_CARDCOUNT]; // �����˿�
	public int[] nHandCardCount = new int[GDF.GAME_PLAYER]; // �˿���Ŀ

	// ��ʷ����
	public long[] lTurnScore = new long[GDF.GAME_PLAYER]; // ������Ϣ
	public long[] lCollectScore = new long[GDF.GAME_PLAYER]; // ������Ϣ

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cbTimeOutCard = data[nIndex++];
		cbTimeCallScore = data[nIndex++];
		cbTimeStarGame = data[nIndex++];
		cbTimeHeadOutCard = data[nIndex++];

		lCellScore = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		cbBombCount = data[nIndex++];
		nBankerUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		cbBankerScore = data[nIndex++];

		nTurnWiner = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nTurnCardCount = (data[nIndex] < 0
				? (data[nIndex] + 256)
				: data[nIndex]);
		nIndex++;

		for (int i = 0; i < GDF.MAX_CARDCOUNT; i++) {
			nTurnCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < 3; i++) {
			nBankerCard[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.MAX_CARDCOUNT; i++) {
			nHandCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nHandCardCount[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
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
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
