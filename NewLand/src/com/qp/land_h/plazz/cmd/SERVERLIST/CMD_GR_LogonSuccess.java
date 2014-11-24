package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_LogonSuccess extends Cmd {

	public long lUserRight;
	public long lMasterRight;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		lUserRight = Utility.read4Byte(data, index);
		index += 4;
		lMasterRight = Utility.read4Byte(data, index);
		index += 4;
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
