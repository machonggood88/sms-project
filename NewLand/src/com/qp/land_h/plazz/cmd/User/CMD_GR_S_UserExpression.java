package com.qp.land_h.plazz.cmd.User;

import Net_Interface.ICmd;
import Net_Utility.Utility;

public class CMD_GR_S_UserExpression implements ICmd {

	public int wItemIndex;
	public long lSendUserID;
	public long lTargetUserID;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		wItemIndex = Utility.read2Byte(data, index);
		index += 2;
		lSendUserID = Utility.read4Byte(data, index);
		index += 4;
		lTargetUserID = Utility.read4Byte(data, index);
		index += 4;
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
