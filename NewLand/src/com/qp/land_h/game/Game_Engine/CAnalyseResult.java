package com.qp.land_h.game.Game_Engine;

/**
 * �߼������ṹ
 * 
 * @author CHENPENG
 * 
 */
public class CAnalyseResult {
	public int nFourCount; // ��������
	public int nThreeCount; // ��������
	public int nDoubleCount; // ��������
	public int nSignedCount; // ��������
	public int[] nFourCardData; // ��������
	public int[] nThreeCardData; // ��������
	public int[] nDoubleCardData; // ��������
	public int[] nSignedCardData; // ��������

	/**
	 * ���캯��
	 */
	public CAnalyseResult() {
		nFourCount = 0;
		nThreeCount = 0;
		nDoubleCount = 0;
		nSignedCount = 0;
		nFourCardData = new int[CGameLogic.MAX_COUNT];
		nThreeCardData = new int[CGameLogic.MAX_COUNT];
		nDoubleCardData = new int[CGameLogic.MAX_COUNT];
		nSignedCardData = new int[CGameLogic.MAX_COUNT];
	}

	/**
	 * ����
	 */
	public void ResetData() {
		nFourCount = 0;
		nThreeCount = 0;
		nDoubleCount = 0;
		nSignedCount = 0;
		for (int i = 0; i < CGameLogic.MAX_COUNT; i++) {
			nFourCardData[i] = 0;
			nThreeCardData[i] = 0;
			nDoubleCardData[i] = 0;
			nSignedCardData[i] = 0;
		}
	}
}
