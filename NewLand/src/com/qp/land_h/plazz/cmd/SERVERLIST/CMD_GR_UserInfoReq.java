package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_UserInfoReq extends Cmd {
	public long lUserIDReq;
	public int nTablePos;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write4byte(data, lUserIDReq, nIndex);
		nIndex += 4;
		Utility.write2byte(data, nTablePos, nIndex);
		nIndex += 2;
		return nIndex - pos;
	}

}
