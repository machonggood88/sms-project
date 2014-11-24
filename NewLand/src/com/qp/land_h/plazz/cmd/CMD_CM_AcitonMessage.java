package com.qp.land_h.plazz.cmd;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_CM_AcitonMessage extends Cmd {

	public int nType;
	public int nLength;
	public int nButtonType;
	public String szString = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		nType = Utility.read2Byte(data, index);
		index += 2;
		nLength = Utility.read2Byte(data, index);
		index += 2;
		nButtonType = Utility.read4Byte(data, index);
		index += 2;
		szString = Utility.wcharUnicodeBytesToString(data, index, 0);
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
