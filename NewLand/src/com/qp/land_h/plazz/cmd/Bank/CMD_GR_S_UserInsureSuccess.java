package com.qp.land_h.plazz.cmd.Bank;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 银行成功
 * 
 */
public class CMD_GR_S_UserInsureSuccess extends Cmd {
	/** 游戏动作 **/
	public byte cbActivityGame;
	/** 用户金币 **/
	public long lUserScore;
	/** 银行金币 **/
	public long lUserInsure;
	/** 描述信息 **/
	public String szDescribeString;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cbActivityGame = data[nIndex++];
		lUserScore = Utility.read8Byte(data, nIndex);
		nIndex += 8;
		lUserInsure = Utility.read8Byte(data, nIndex);
		nIndex += 8;
		szDescribeString = Utility.wcharUnicodeBytesToString(data, nIndex, 0);
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
}
