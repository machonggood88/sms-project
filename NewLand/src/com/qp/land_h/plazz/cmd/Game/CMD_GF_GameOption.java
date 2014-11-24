package com.qp.land_h.plazz.cmd.Game;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GF_GameOption extends Cmd {

	public byte cbAllowLookon;
	public long lFrameVersion;
	public long lClientVersion;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = cbAllowLookon;
		Utility.write4byte(data, lFrameVersion, nIndex);
		nIndex += 4;
		Utility.write4byte(data, lClientVersion, nIndex);
		nIndex += 4;
		return nIndex - pos;
	}

}
