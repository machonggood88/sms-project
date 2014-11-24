package com.qp.land_h.plazz.cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_CM_SystemMsg extends Cmd {

	public int nType;
	public int nLength;
	public String szString = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;

		nType = Utility.read2Byte(data, index);
		index += 2;
		nLength = Utility.read2Byte(data, index);
		index += 2;
		szString = Utility.wcharUnicodeBytesToString(data, index, 0);
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
