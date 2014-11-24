package com.qp.land_h.game.Game_Engine;

import java.util.Random;

import android.util.Log;

public class CGameLogic {
	// ��Ŀ����
	public static final int MAX_COUNT = 20; // �����Ŀ
	public static final int FULL_COUNT = 54; // ȫ����Ŀ
	public static final int BACK_COUNT = 3; // ������Ŀ
	public static final int NORMAL_COUNT = 17; // ������Ŀ

	// ��ֵ����
	public static final int MASK_COLOR = 0xF0; // ��ɫ����
	public static final int MASK_VALUE = 0x0F; // ��ֵ����
	// �˿�����
	public static final int CT_ERROR = 0; // ��������
	public static final int CT_1 = 1; // ��������
	public static final int CT_2 = 2; // ��������
	public static final int CT_3 = 3; // ��������
	public static final int CT_1_LINE = 4; // ��������
	public static final int CT_2_LINE = 5; // ��������
	public static final int CT_3_LINE = 6; // ��������
	public static final int CT_3_LINE_1 = 7; // ����һ��
	public static final int CT_3_LINE_2 = 8; // ����һ��
	public static final int CT_4_LINE_1 = 9; // �Ĵ�����
	public static final int CT_4_LINE_2 = 10; // �Ĵ�����
	public static final int CT_BOME_CARD = 11; // ը������
	public static final int CT_MISSILE_CARD = 12; // �������

	// ��������
	public static final int ST_ORDER = 1;
	public static final int ST_COUNT = 2;

	public static final int CardData[] = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
			0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x11, 0x12, 0x13, 0x14,
			0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x21, 0x22,
			0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D,
			0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B,
			0x3C, 0x3D, 0x4E, 0x4F,};

	// ���캯��
	public CGameLogic() {

	}

	public void RandCardList(int nCardData[], int nCardCount) {
		int nTempData[] = new int[54];
		System.arraycopy(CardData, 0, nTempData, 0, CardData.length);
		Random rand = new Random(System.currentTimeMillis());

		int nRandCount = 0, nPosition = 0;
		do {
			nPosition = rand.nextInt(nCardCount - nRandCount);
			nCardData[nRandCount++] = nTempData[nPosition];
			nTempData[nPosition] = nTempData[nCardCount - nRandCount];
		} while (nRandCount < nCardCount);
	}

	/**
	 * ��������
	 * 
	 * @param nCard
	 * @param nCount
	 * @param nTurnCard
	 * @param nTurnCount
	 * @param nResultCard
	 * @return
	 */
	public int SeachOutCard(int[] nCard, int nCount, int[] nTurnCard,
			int nTurnCount, int[] nResultCard) {
		// ��ȡ����
		int nTurnType = GetCardType(nTurnCard, nTurnCount);

		int nValue = 0;
		// ���Ʒ���
		switch (nTurnType) {
		// ��������
			case CT_ERROR : {
				// ��ȡ��ֵ
				nValue = GetCardLogicValue(nCard[nCount - 1]);
				int nSameCount = 1;
				// �����ж�
				for (int i = 1; i < nCount; ++i) {
					if (GetCardLogicValue(nCard[nCount - 1 - i]) == nValue) {
						nSameCount++;
					} else {
						break;
					}
				}
				// ��ɴ���
				for (int i = 0; i < nSameCount; i++) {
					nResultCard[i] = nCard[nCount - 1 - i];
				}
				return nSameCount;
			}
			// ���ƣ����ƣ���������
			case CT_1 :
			case CT_2 :
			case CT_3 : {
				// ��ȡ��ֵ
				nValue = GetCardLogicValue(nTurnCard[0]);
				// �����˿�
				CAnalyseResult AnalyseResult = new CAnalyseResult();
				AnalysebCardData(nCard, nCount, AnalyseResult);

				int nIndex = 0;
				// Ѱ�ҵ���
				if (nTurnCount <= 1) {
					for (int i = 0; i < AnalyseResult.nSignedCount; ++i) {
						nIndex = AnalyseResult.nSignedCount - i - 1;
						if (GetCardLogicValue(AnalyseResult.nSignedCardData[nIndex]) > nValue) {
							System.arraycopy(AnalyseResult.nSignedCardData,
									nIndex, nResultCard, 0, nTurnCount);
							return nTurnCount;
						}
					}
				}
				if (nTurnCount <= 2) {
					for (int i = 0; i < AnalyseResult.nDoubleCount; ++i) {
						nIndex = (AnalyseResult.nDoubleCount - i - 1) * 2;
						if (GetCardLogicValue(AnalyseResult.nDoubleCardData[nIndex]) > nValue) {
							System.arraycopy(AnalyseResult.nDoubleCardData,
									nIndex, nResultCard, 0, nTurnCount);
							return nTurnCount;
						}
					}
				}
				if (nTurnCount <= 3) {
					for (int i = 0; i < AnalyseResult.nThreeCount; ++i) {
						nIndex = (AnalyseResult.nThreeCount - i - 1) * 3;
						if (GetCardLogicValue(AnalyseResult.nThreeCardData[nIndex]) > nValue) {
							System.arraycopy(AnalyseResult.nThreeCardData,
									nIndex, nResultCard, 0, nTurnCount);
							return nTurnCount;
						}
					}
				}
				break;
			}
			// ��������
			case CT_1_LINE : {
				// �����ж�
				if (nCount < nTurnCount) {
					break;
				}

				// ��ȡ��ֵ
				nValue = GetCardLogicValue(nTurnCard[0]);

				// ��������
				for (int i = nTurnCount - 1; i < nCount; i++) {
					// ��ȡ��ֵ
					int nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
					// �����ж�
					if (nHandValue >= 15) {
						break;
					}
					if (nHandValue <= nValue) {
						continue;
					}
					// ��������
					int nLineCount = 0;
					for (int j = nCount - i - 1; j < nCount; j++) {
						if (GetCardLogicValue(nCard[j]) + nLineCount == nHandValue) {
							nResultCard[nLineCount++] = nCard[j];
							// ����ж�
							if (nLineCount == nTurnCount) {
								return nTurnCount;
							}
						}
					}
				}
				break;
			}
			case CT_2_LINE : {
				// �����ж�
				if (nCount < nTurnCount) {
					break;
				}

				// ��ȡ��ֵ
				nValue = GetCardLogicValue(nTurnCard[0]);

				// ��������
				for (int i = nTurnCount - 1; i < nCount; i++) {
					// ��ȡ��ֵ
					int nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
					// �����ж�
					if (nHandValue <= nValue) {
						continue;
					}
					if (nTurnCount > 1 && nHandValue >= 15) {
						break;
					}
					// ��������
					int nLineCount = 0;
					for (int j = nCount - i - 1; j < nCount - 1; j++) {
						if (GetCardLogicValue(nCard[j]) + nLineCount == nHandValue
								&& GetCardLogicValue(nCard[j + 1]) + nLineCount == nHandValue) {
							nResultCard[nLineCount * 2] = nCard[j];
							nResultCard[(nLineCount++) * 2 + 1] = nCard[j + 1];
							// ����ж�
							if (nLineCount * 2 == nTurnCount) {
								return nTurnCount;
							}
						}
					}
				}
				break;
			}
			case CT_3_LINE :
			case CT_3_LINE_1 :
			case CT_3_LINE_2 : {
				// �����ж�
				if (nCount < nTurnCount) {
					break;
				}

				// ��ȡ��ֵ
				nValue = 0;
				for (int i = 0; i < nTurnCount - 2; i++) {
					nValue = GetCardLogicValue(nTurnCard[i]);
					if (GetCardLogicValue(nTurnCard[i + 1]) != nValue) {
						continue;
					}
					if (GetCardLogicValue(nTurnCard[i + 2]) != nValue) {
						continue;
					}
					break;
				}
				// ������ֵ
				int nTurnLintCount = 0;
				if (nTurnType == CT_3_LINE_1) {
					nTurnLintCount = nTurnCount / 4;
				} else if (nTurnType == CT_3_LINE_2) {
					nTurnLintCount = nTurnCount / 5;
				} else {
					nTurnLintCount = nTurnCount / 3;
				}

				// ��������
				for (int i = nTurnLintCount * 3 - 1; i < nCount; i++) {
					// ��ȡ��ֵ
					int nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
					// �����ж�
					if (nHandValue <= nValue || nHandValue > 15) {
						continue;
					}
					if (nTurnLintCount > 1) {
						break;
					}

					int nLineCount = 0;
					for (int j = (nCount - i - 1); j < nCount - 2; j++) {
						int nResultCount = 0;
						// �����ж�
						if (GetCardLogicValue(nCard[j]) + nLineCount != nHandValue) {
							continue;
						}
						if (GetCardLogicValue(nCard[j + 1]) + nLineCount != nHandValue) {
							continue;
						}
						if (GetCardLogicValue(nCard[j + 2]) + nLineCount != nHandValue) {
							continue;
						}

						nResultCard[nLineCount * 3] = nCard[j];
						nResultCard[nLineCount * 3 + 1] = nCard[j + 1];
						nResultCard[(nLineCount++) * 3 + 2] = nCard[j + 2];

						// ����ж�
						if (nLineCount == nTurnLintCount) {
							int[] nLeftCard = new int[MAX_COUNT];
							nResultCount = nLineCount * 3;
							int nLeftCount = nCount;
							System.arraycopy(nCard, 0, nLeftCard, 0, nCount);
							RemoveCard(nResultCard, nResultCount, nLeftCard,
									nLeftCount);
							nLeftCount = nCount - nLineCount * 3;
							// �����˿�
							CAnalyseResult AnalyseLeft = new CAnalyseResult();
							AnalysebCardData(nLeftCard, nLeftCount, AnalyseLeft);

							if (nTurnType == CT_3_LINE_1) {
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nSignedCount; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}

									// �����˿�
									nResultCard[nResultCount++] = AnalyseLeft.nSignedCardData[AnalyseLeft.nSignedCount
											- k - 1];
								}
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nDoubleCount * 2; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}

									// �����˿�
									nResultCard[nResultCount++] = AnalyseLeft.nDoubleCardData[AnalyseLeft.nDoubleCount
											* 2 - k - 1];
								}
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nThreeCount * 3; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}

									// �����˿�
									nResultCard[nResultCount++] = AnalyseLeft.nThreeCardData[AnalyseLeft.nThreeCount
											* 3 - k - 1];
								}
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nFourCount * 4; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}

									// �����˿�
									nResultCard[nResultCount++] = AnalyseLeft.nFourCardData[AnalyseLeft.nFourCount
											* 4 - k - 1];
								}

							}
							// ���ƴ���
							if (nTurnType == CT_3_LINE_2) {
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nDoubleCount; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}
									nResultCard[nResultCount++] = AnalyseLeft.nDoubleCardData[(AnalyseLeft.nDoubleCount
											- k - 1) * 2];
									nResultCard[nResultCount++] = AnalyseLeft.nDoubleCardData[(AnalyseLeft.nDoubleCount
											- k - 1) * 2 + 1];
								}
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nThreeCount; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}
									nResultCard[nResultCount++] = AnalyseLeft.nThreeCardData[(AnalyseLeft.nThreeCount
											- k - 1) * 3];
									nResultCard[nResultCount++] = AnalyseLeft.nThreeCardData[(AnalyseLeft.nThreeCount
											- k - 1) * 3 + 1];
								}
								// ��ȡ����
								for (int k = 0; k < AnalyseLeft.nFourCount; k++) {
									// ��ֹ�ж�
									if (nResultCount == nTurnCount) {
										break;
									}
									nResultCard[nResultCount++] = AnalyseLeft.nFourCardData[(AnalyseLeft.nFourCount
											- k - 1) * 4];
									nResultCard[nResultCount++] = AnalyseLeft.nFourCardData[(AnalyseLeft.nFourCount
											- k - 1) * 4 + 1];
								}
							}
							if (nResultCount == nTurnCount) {
								return nTurnCount;
							}
						}
					}
				}
				break;
			}
		}

		// ����ը��
		if (nCount >= 4 && (nTurnType != CT_MISSILE_CARD)) {
			nValue = 0;
			if (nTurnType == CT_BOME_CARD) {
				nValue = GetCardLogicValue(nTurnCard[0]);
			}
			// ����ը��
			int nHandValue;
			for (int i = 3; i < nCount; i++) {
				// ��ȡ��ֵ
				nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
				// �����ж�
				if (nHandValue <= nValue) {
					continue;
				}
				// ը���ж�
				int nTempValue = GetCardLogicValue(nCard[nCount - i - 1]);
				int j = 1;
				for (; j < 4; j++) {
					if (GetCardLogicValue(nCard[nCount + j - i - 1]) != nTempValue) {
						break;
					}
				}
				if (j != 4) {
					continue;
				}

				nResultCard[0] = nCard[nCount - i - 1];
				nResultCard[1] = nCard[nCount - i];
				nResultCard[2] = nCard[nCount - i + 1];
				nResultCard[3] = nCard[nCount - i + 2];
				return 4;
			}
		}
		// �������
		if (nCount >= 2 && (nCard[0] == 0x4F) && (nCard[1] == 0x4E)) {
			nResultCard[0] = nCard[0];
			nResultCard[1] = nCard[1];
			return 2;
		}
		return 0;
	}

	// ��ȡ����
	// ��ȡ����
	public int GetCardType(int nCardData[], int nCardCount) {
		// ������
		switch (nCardCount) {
			case 0 : // ����
			{
				return CT_ERROR;
			}
			case 1 : // ����
			{
				return CT_1;
			}
			case 2 : // ���ƻ��
			{
				if ((nCardData[0] == 0x4f) && (nCardData[1] == 0x4E)) {
					return CT_MISSILE_CARD; // ���
				}
				if (GetCardLogicValue(nCardData[0]) == GetCardLogicValue(nCardData[1])) {
					return CT_2; // ����
				}
				return CT_ERROR;
			}
		}
		// �����˿�
		CAnalyseResult AnalyseResult = new CAnalyseResult();

		AnalysebCardData(nCardData, nCardCount, AnalyseResult);

		if (AnalyseResult.nFourCount == 1) {
			if (nCardCount == 4) {
				return CT_BOME_CARD;
			}
			if (AnalyseResult.nSignedCount == 2 && nCardCount == 6) {
				return CT_4_LINE_1;
			}
			if (AnalyseResult.nDoubleCount == 2 && nCardCount == 8) {
				return CT_4_LINE_2;
			}
			return CT_ERROR;

		}
		int nTempCardData = 0;
		int nFirstLogicValue = 0;
		if (AnalyseResult.nThreeCount > 0) {
			if (AnalyseResult.nThreeCount == 1 && nCardCount == 3) {
				return CT_3;
			}
			if (AnalyseResult.nThreeCount > 1) {
				nTempCardData = AnalyseResult.nThreeCardData[0];
				nFirstLogicValue = GetCardLogicValue(nTempCardData);

				if (nFirstLogicValue >= 15) {
					return CT_ERROR;
				}

				for (int i = 1; i < AnalyseResult.nThreeCount; i++) {
					if (nFirstLogicValue != GetCardLogicValue(AnalyseResult.nThreeCardData[i * 3])
							+ i) {
						return CT_ERROR;
					}
				}
			}
			// �����ж�
			if (AnalyseResult.nThreeCount * 3 == nCardCount) {
				return CT_3_LINE;
			}
			if (AnalyseResult.nThreeCount * 4 == nCardCount) {
				return CT_3_LINE_1;
			}
			if (AnalyseResult.nThreeCount * 5 == nCardCount
					&& AnalyseResult.nDoubleCount == AnalyseResult.nThreeCount) {
				return CT_3_LINE_2;
			}

			return CT_ERROR;
		}

		// ��������
		if (AnalyseResult.nDoubleCount >= 3) {
			nTempCardData = AnalyseResult.nDoubleCardData[0];
			nFirstLogicValue = GetCardLogicValue(nTempCardData);

			// �������
			if (nFirstLogicValue >= 15) {
				return CT_ERROR;
			}

			// �����ж�
			for (int i = 1; i < AnalyseResult.nDoubleCount; i++) {
				if (nFirstLogicValue != (GetCardLogicValue(AnalyseResult.nDoubleCardData[i * 2]) + i)) {
					return CT_ERROR;
				}
			}

			// �����ж�
			if (AnalyseResult.nDoubleCount * 2 == nCardCount) {
				return CT_2_LINE;
			}

			return CT_ERROR;
		}

		// �����ж�
		if (AnalyseResult.nSignedCount >= 5
				&& AnalyseResult.nSignedCount == nCardCount) {
			nTempCardData = AnalyseResult.nSignedCardData[0];
			nFirstLogicValue = GetCardLogicValue(nTempCardData);

			// �������
			if (nFirstLogicValue >= 15) {
				return CT_ERROR;
			}

			// �����ж�

			for (int i = 0; i < AnalyseResult.nSignedCount; i++) {
				if (nFirstLogicValue != GetCardLogicValue(AnalyseResult.nSignedCardData[i])
						+ i) {
					return CT_ERROR;
				}
			}

			return CT_1_LINE;
		}
		return CT_ERROR;
	}

	// �����˿�
	public void SortCardList(int nCardData[], int nCardCount, int nSortType) {
		if (nCardCount == 0) {
			return;
		}

		int[] nSortValue = new int[MAX_COUNT];
		for (int i = 0; i < nCardCount; i++) {
			nSortValue[i] = GetCardLogicValue(nCardData[i]);
		}

		boolean bSorted = true;
		int nThreeCount, nLast = nCardCount - 1;

		do {
			bSorted = true;
			for (int i = 0; i < nLast; i++) {
				if (nSortValue[i] < nSortValue[i + 1]
						|| (nSortValue[i] == nSortValue[i + 1] && nCardData[i] < nCardData[i + 1])) {
					nThreeCount = nCardData[i];
					nCardData[i] = nCardData[i + 1];
					nCardData[i + 1] = nThreeCount;
					nThreeCount = nSortValue[i];
					nSortValue[i] = nSortValue[i + 1];
					nSortValue[i + 1] = nThreeCount;
					bSorted = false;
				}
			}
			nLast--;
		} while (bSorted == false);

		if (nSortType == ST_COUNT) {
			int nIndex = 0;
			CAnalyseResult AnalyseResult = new CAnalyseResult();

			AnalysebCardData(nCardData, nCardCount, AnalyseResult);
			try {
				System.arraycopy(AnalyseResult.nFourCardData, 0, nCardData,
						nIndex, AnalyseResult.nFourCount * 4);
				nIndex += (AnalyseResult.nFourCount * 4);

				System.arraycopy(AnalyseResult.nThreeCardData, 0, nCardData,
						nIndex, AnalyseResult.nThreeCount * 3);
				nIndex += (AnalyseResult.nThreeCount * 3);

				System.arraycopy(AnalyseResult.nDoubleCardData, 0, nCardData,
						nIndex, AnalyseResult.nDoubleCount * 2);
				nIndex += (AnalyseResult.nDoubleCount * 2);

				System.arraycopy(AnalyseResult.nSignedCardData, 0, nCardData,
						nIndex, AnalyseResult.nSignedCount);
				nIndex += AnalyseResult.nSignedCount;
			} catch (IndexOutOfBoundsException ex) {
				Log.e("SortCardList", "ERROR");
			}
		}
	}

	// ɾ���˿�
	public boolean RemoveCard(int nRemoveCard[], int nRemoveCount,
			int nCardData[], int nCardCount) {
		// У��
		if (nRemoveCount > nCardCount || nCardCount > MAX_COUNT) {
			return false;
		}

		// �������
		int nDeleteCount = 0;
		int[] nTempCardData = new int[MAX_COUNT];
		System.arraycopy(nCardData, 0, nTempCardData, 0, nCardCount);

		// �����˿�
		for (int i = 0; i < nRemoveCount; i++) {
			for (int j = 0; j < nCardCount; j++) {
				if (nRemoveCard[i] == nTempCardData[j]) {
					nDeleteCount++;
					nTempCardData[j] = 0;
					break;
				}
			}
		}

		if (nDeleteCount != nRemoveCount) {
			Log.e("REMOVECARD", "ERROR");
			return false;
		}
		int nCardPos = 0;
		for (int i = 0; i < nCardCount; i++) {
			if (nTempCardData[i] != 0) {
				nCardData[nCardPos++] = nTempCardData[i];
			}
		}
		return true;
	}

	// ��Ч�ж�
	public static boolean IsValidCard(int nCardData) {
		if (nCardData == 0x4E || nCardData == 0x4F) {
			return true;
		}
		// ��ֵ��ɫ
		int nCardValue = GetCardValue(nCardData);
		int nCardColor = GetCardColor(nCardData);

		if (nCardColor <= 0x30 && nCardValue >= 0x01 && nCardValue <= 0x0D) {
			return true;
		}

		return false;
	}

	// ��ȡ��ֵ
	public static int GetCardValue(int nCardData) {
		return nCardData & MASK_VALUE;
	}

	// ��ȡ��ɫ
	public static int GetCardColor(int nCardData) {
		return nCardData & MASK_COLOR;
	}

	// �߼���ֵ
	public int GetCardLogicValue(int nCardData) {
		// ��ֵ��ɫ
		int nCardValue = GetCardValue(nCardData);
		int nCardColor = GetCardColor(nCardData);

		// ת��
		if (nCardColor == 0x40) {
			return nCardValue + 2;
		}
		return (nCardValue <= 2) ? (nCardValue + 13) : nCardValue;
	}

	// �Ա��˿�
	public boolean CompareCard(int nFirstCard[], int nNextCard[],
			int nFirstCount, int nNextCount) {
		// ��ȡ����
		int nNextType = GetCardType(nNextCard, nNextCount);

		// �����ж�
		if (nNextType == CT_ERROR) {
			return false;
		}
		if (nNextType == CT_MISSILE_CARD) {
			return true;
		}

		// ��ȡ����
		int nFirstType = GetCardType(nFirstCard, nFirstCount);

		// ը���ж�
		if (nFirstType != CT_BOME_CARD && nNextType == CT_BOME_CARD) {
			return true;
		}
		if (nFirstType == CT_BOME_CARD && nNextType != CT_BOME_CARD) {
			return false;
		}

		// �����ж�
		if (nFirstType != nNextType || nFirstCount != nNextCount) {
			return false;
		}

		// ��ʼ�Ա�
		switch (nNextType) {
			case CT_1 :
			case CT_2 :
			case CT_3 :
			case CT_1_LINE :
			case CT_2_LINE :
			case CT_3_LINE :
			case CT_BOME_CARD : {
				return GetCardLogicValue(nNextCard[0]) > GetCardLogicValue(nFirstCard[0]);
			}
			case CT_3_LINE_1 :
			case CT_3_LINE_2 : {
				// ������ȡ��ֵ
				CAnalyseResult AnalyseResult = new CAnalyseResult();
				AnalysebCardData(nNextCard, nNextCount, AnalyseResult);
				int nNextLogicValue = GetCardLogicValue(AnalyseResult.nThreeCardData[0]);
				AnalysebCardData(nFirstCard, nFirstCount, AnalyseResult);
				int nFirstLogicValue = GetCardLogicValue(AnalyseResult.nThreeCardData[0]);

				// ���ضԱ�
				return nNextLogicValue > nFirstLogicValue;
			}
			case CT_4_LINE_1 :
			case CT_4_LINE_2 : {
				// ������ȡ��ֵ
				CAnalyseResult AnalyseResult = new CAnalyseResult();
				AnalysebCardData(nNextCard, nNextCount, AnalyseResult);
				int nNextLogicValue = GetCardLogicValue(AnalyseResult.nFourCardData[0]);
				AnalysebCardData(nFirstCard, nFirstCount, AnalyseResult);
				int nFirstLogicValue = GetCardLogicValue(AnalyseResult.nFourCardData[0]);

				// ���ضԱ�
				return nNextLogicValue > nFirstLogicValue;
			}
		}
		return false;
	}

	// �����˿�
	public void AnalysebCardData(int nCardData[], int nCardCount,
			CAnalyseResult AnalyseResult) {
		// ��������
		AnalyseResult.ResetData();
		for (int i = 0; i < nCardCount; i++) {
			// ��������
			int nSameCount = 1;
			int nLogicValue = GetCardLogicValue(nCardData[i]);
			// ����ͬ��
			for (int j = i + 1; j < nCardCount; j++) {
				// ��ȡ�˿�
				if (GetCardLogicValue(nCardData[j]) != nLogicValue) {
					break;
				}
				// ���ñ���
				nSameCount++;
			}

			// ���ý��
			int nIndex = 0;
			switch (nSameCount) {
				case 1 : // ����
				{
					nIndex = AnalyseResult.nSignedCount++;
					AnalyseResult.nSignedCardData[nIndex * nSameCount] = nCardData[i];
					break;
				}
				case 2 : // ����
				{
					nIndex = AnalyseResult.nDoubleCount++;
					for (int k = 0; k < nSameCount; k++) {
						AnalyseResult.nDoubleCardData[nIndex * nSameCount + k] = nCardData[i
								+ k];
					}
					break;
				}
				case 3 : // ����
				{
					nIndex = AnalyseResult.nThreeCount++;
					for (int k = 0; k < nSameCount; k++) {
						AnalyseResult.nThreeCardData[nIndex * nSameCount + k] = nCardData[i
								+ k];
					}
					break;
				}
				case 4 : // ����
				{
					nIndex = AnalyseResult.nFourCount++;
					for (int k = 0; k < nSameCount; k++) {
						AnalyseResult.nFourCardData[nIndex * nSameCount + k] = nCardData[i
								+ k];
					}
					break;
				}
			}
			// ��������
			i += nSameCount - 1;
		}
	}
}
