package com.qp.land_h.plazz.cmd.User;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_UserStandUp extends Cmd {
	public int nTableID;
	public int nChairID;
	public byte cbForceLeave;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nTableID, nIndex);
		nIndex += 2;
		Utility.write2byte(data, nChairID, nIndex);
		nIndex += 2;
		data[nIndex++] = cbForceLeave;
		return nIndex - pos;
	}

}
