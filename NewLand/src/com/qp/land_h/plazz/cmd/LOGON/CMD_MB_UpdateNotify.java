package com.qp.land_h.plazz.cmd.LOGON;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_MB_UpdateNotify extends Cmd {
	public byte cbMustUpdate;
	public byte cbAdviceUpdate;
	public long lCurrentVersion;

	public CMD_MB_UpdateNotify() {

	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cbMustUpdate = data[nIndex++];
		cbAdviceUpdate = data[nIndex++];
		lCurrentVersion = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		return nIndex - pos;
	}
}
