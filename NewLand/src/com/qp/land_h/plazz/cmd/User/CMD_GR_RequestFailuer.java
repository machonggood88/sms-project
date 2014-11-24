package com.qp.land_h.plazz.cmd.User;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_RequestFailuer extends Cmd {

	public long lFailuerCode;
	public String szDescribeString = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		lFailuerCode = Utility.read8Byte(data, index);
		index += 8;
		szDescribeString = Utility.wcharUnicodeBytesToString(data, index, 0);
		index += szDescribeString.length() * 2;
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
