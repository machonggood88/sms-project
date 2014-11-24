package com.qp.land_h.game.Game_Engine;

/**
 * 逻辑分析结构
 * 
 * @author CHENPENG
 * 
 */
public class CAnalyseResult {
	public int nFourCount; // 四张张数
	public int nThreeCount; // 三张张数
	public int nDoubleCount; // 二张张数
	public int nSignedCount; // 单张张数
	public int[] nFourCardData; // 四张数据
	public int[] nThreeCardData; // 三张数据
	public int[] nDoubleCardData; // 二张数据
	public int[] nSignedCardData; // 单张数据

	/**
	 * 构造函数
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
	 * 重置
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
