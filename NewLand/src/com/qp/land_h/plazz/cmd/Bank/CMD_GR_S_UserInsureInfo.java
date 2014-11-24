package com.qp.land_h.plazz.cmd.Bank;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * 银行资料
 * 
 */
public class CMD_GR_S_UserInsureInfo extends Cmd {
	/** 游戏动作 **/
	public byte cbActivityGame;;
	/** 取款税收比例 **/
	public int wRevenueTake;
	/** 转账税收比例 **/
	public int wRevenueTransfer;
	/** 房间标识 **/
	public int wServerID;
	/** 用户金币 **/
	public long lUserScore;
	/** 银行金币 **/
	public long lUserInsure;
	/** 转账条件 **/
	public long lTransferPrerequisite;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cbActivityGame = data[nIndex++];
		wRevenueTake = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		wRevenueTransfer = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		wServerID = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		lUserScore = Utility.read8Byte(data, nIndex);
		nIndex += 8;
		lUserInsure = Utility.read8Byte(data, nIndex);
		nIndex += 8;
		lTransferPrerequisite = Utility.read8Byte(data, nIndex);
		nIndex += 8;

		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
}
