package com.qp.land_h.plazz.cmd.Bank;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 银行失败
 * 
 */
public class CMD_GR_S_UserInsureFailure extends Cmd {
	/** 游戏动作 **/
	public byte cbActivityGame;
	/** 错误代码 **/
	public long lErrorCode;
	/** 描述消息 **/
	public String szDescribeString = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cbActivityGame = data[nIndex++];
		lErrorCode = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = Utility.wcharUnicodeBytesToString(data, nIndex, 0);
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
}
