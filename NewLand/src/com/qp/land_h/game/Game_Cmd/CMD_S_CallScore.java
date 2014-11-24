package com.qp.land_h.game.Game_Cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 用户叫分
 * 
 * @author CHENPENG
 * 
 */
public class CMD_S_CallScore extends Cmd {
	public int nCurrentUser; // 当前玩家
	public int nCallScoreUser; // 叫分玩家
	public int nCurrentScore; // 当前叫分
	public int nUserCallScore; // 上次叫分

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
