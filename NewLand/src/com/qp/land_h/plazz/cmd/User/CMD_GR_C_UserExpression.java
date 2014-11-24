package com.qp.land_h.plazz.cmd.User;

import Net_Interface.ICmd;
import Net_Utility.Utility;

public class CMD_GR_C_UserExpression implements ICmd {

	public int wItemIndex;
	public long dwTargetUserID;

	@Override
	public int ReadFromByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
		Utility.write2byte(data, wItemIndex, index);
		index += 2;
		Utility.write4byte(data, dwTargetUserID, index);
		index += 4;
		return index - pos;
	}

}
