package com.qp.land_h.plazz.cmd.Bank;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/**
 * �������
 * 
 */
public class CMD_GR_C_SaveScoreRequest extends Cmd {
	/** ��Ϸ���� **/
	public byte cbActivityGame;
	/** �����Ŀ **/
	public long lSaveScore;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = cbActivityGame;
		Utility.write8byte(data, lSaveScore, nIndex);
		nIndex += 8;
		return nIndex - pos;
	}
}
