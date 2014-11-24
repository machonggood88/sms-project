package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_ConfigServer extends Cmd {

	public int nTableCount;
	public int nChairCount;
	public int nServerType;
	public long lServerRule;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		nTableCount = Utility.read2Byte(data, index);
		index += 2;
		nChairCount = Utility.read2Byte(data, index);
		index += 2;
		nServerType = Utility.read2Byte(data, index);
		index += 2;
		lServerRule = Utility.read4Byte(data, index);
		index += 4;
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
