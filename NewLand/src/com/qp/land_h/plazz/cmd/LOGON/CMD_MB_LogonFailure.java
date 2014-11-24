package com.qp.land_h.plazz.cmd.LOGON;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_MB_LogonFailure extends Cmd {
	public long lErrorCode;
	public String szDescribeString;

	public CMD_MB_LogonFailure(byte[] data, int pos) {
		super(data, pos);
	}

	public CMD_MB_LogonFailure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lErrorCode = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = Utility.wcharUnicodeBytesToString(data, nIndex, 0);
		return nIndex - pos;
	}

}
