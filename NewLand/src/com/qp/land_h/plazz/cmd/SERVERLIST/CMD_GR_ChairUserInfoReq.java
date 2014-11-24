package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_ChairUserInfoReq extends Cmd {
	public int nTablePos;
	public int nChairPos;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nTablePos, nIndex);
		nIndex += 2;
		Utility.write2byte(data, nChairPos, nIndex);
		nIndex += 2;
		return nIndex - pos;
	}
}
