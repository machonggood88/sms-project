package com.qp.land_h.plazz.cmd.Bank;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * ��������
 * 
 */
public class CMD_GR_S_UserInsureInfo extends Cmd {
	/** ��Ϸ���� **/
	public byte cbActivityGame;;
	/** ȡ��˰�ձ��� **/
	public int wRevenueTake;
	/** ת��˰�ձ��� **/
	public int wRevenueTransfer;
	/** �����ʶ **/
	public int wServerID;
	/** �û���� **/
	public long lUserScore;
	/** ���н�� **/
	public long lUserInsure;
	/** ת������ **/
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
