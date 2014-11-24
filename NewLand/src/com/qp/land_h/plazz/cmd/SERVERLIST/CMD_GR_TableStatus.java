package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_TableStatus extends Cmd {

	public int nTableID;
	public tagTableStatus TableStatus = new tagTableStatus();

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		nTableID = Utility.read2Byte(data, pos);
		index += 2;
		index += TableStatus.ReadFromByteArray(data, index);
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
