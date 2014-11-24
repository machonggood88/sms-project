package com.qp.land_h.plazz.cmd.Bank;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * ���гɹ�
 * 
 */
public class CMD_GR_S_UserInsureSuccess extends Cmd {
	/** ��Ϸ���� **/
	public byte cbActivityGame;
	/** �û���� **/
	public long lUserScore;
	/** ���н�� **/
	public long lUserInsure;
	/** ������Ϣ **/
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
