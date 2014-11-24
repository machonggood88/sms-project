package com.qp.land_h.plazz.cmd.User;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_UserLookon extends Cmd {
	public int nTableID;
	public int nChairID;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {

		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nTableID, nIndex);
		nIndex += 2;
		Utility.write2byte(data, nChairID, nIndex);
		nIndex += 2;
		return nIndex - pos;
	}
}
