package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * ��Ϸ����
 * 
 */
public class CMD_S_GameConClude extends Cmd {
	// ���ֱ���
	public long lCellScore; // ��Ԫ����
	public long[] lGameScore = new long[GDF.GAME_PLAYER]; // ��Ϸ����
	// �����־
	public int nChunTian; // �����־
	public int nFanChunTian; // �����־
	// ը����Ϣ
	public int nBombCount; // ը������
	public int[] nEachBombCount = new int[GDF.GAME_PLAYER]; // ը������
	// ��Ϸ��Ϣ
	public int nBankerScore; // �з���Ŀ
	public int[] nCardCount = new int[GDF.GAME_PLAYER]; // �˿���Ŀ
	public int[] nHandCardData = new int[GDF.FULL_COUNT]; // �˿��б�

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lCellScore = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			lGameScore[i] = Utility.read8Byte(data, nIndex);
			nIndex += 8;
		}

		nChunTian = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nFanChunTian = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nBombCount = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nEachBombCount[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		nBankerScore = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		int nCount = 0;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nCardCount[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
			nCount += nCardCount[i];
		}
		for (int i = 0; i < nCount; i++) {
			nHandCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
