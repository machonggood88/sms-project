package com.qp.land_h.plazz.cmd.User;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.Plazz_Struct.tagUserStatus;

public class CMD_GR_UserStatus extends Cmd {
	public long lUserID;
	public tagUserStatus UserStatus = new tagUserStatus();

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		lUserID = Utility.read4Byte(data, index);
		index += 4;
		UserStatus.nTableID = Utility.read2Byte(data, index);
		index += 2;
		UserStatus.nChairID = Utility.read2Byte(data, index);
		index += 2;
		UserStatus.nStatus = data[index++];
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
