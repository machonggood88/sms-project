package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_TableInfo extends Cmd {

	public int nTableCount;

	public tagTableStatus[] TableStatusArray;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		nTableCount = Utility.read2Byte(data, pos);
		index += 2;
		if (nTableCount != 0) {
			TableStatusArray = new tagTableStatus[nTableCount];
			for (int i = 0; i < nTableCount; i++) {
				TableStatusArray[i] = new tagTableStatus();
				index += (TableStatusArray[i].ReadFromByteArray(data, index));
			}
		}
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
