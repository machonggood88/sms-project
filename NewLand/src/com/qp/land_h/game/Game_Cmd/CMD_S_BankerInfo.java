package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 庄家信息
 * 
 */
public class CMD_S_BankerInfo extends Cmd {

	public int nBankerUser; // 庄家玩家
	public int nCurrentUser; // 当前玩家
	public int nBankerScore; // 庄家叫分
	public int nBankerCard[] = new int[3]; // 庄家扑克

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		nBankerUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nCurrentUser = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nBankerScore = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		for (int i = 0; i < 3; i++) {
			nBankerCard[i] = (data[nIndex] < 0
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
