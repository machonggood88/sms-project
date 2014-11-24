package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_LogonFailure extends Cmd {

	public long lErrorCode;
	public String szDescribeString = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lErrorCode = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = Utility.wcharUnicodeBytesToString(data, nIndex, 0);
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
