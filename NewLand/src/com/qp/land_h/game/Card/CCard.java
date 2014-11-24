package com.qp.land_h.game.Card;

import com.qp.land_h.game.Game_Cmd.GDF;

public class CCard {

	/**
	 * 获取卡牌下标
	 * 
	 * @return
	 */
	public static int GetCardIndex(int carddata) {
		if (carddata == 0x4e) {
			return 53;
		} else if (carddata == 0x4f) {
			return 52;
		}
		int index = (carddata & GDF.MASK_VALUE)
				+ ((carddata & GDF.MASK_COLOR) >> 4) * 13 - 1;
		return (index < 0 || index > 51) ? -1 : index;
	}

}
