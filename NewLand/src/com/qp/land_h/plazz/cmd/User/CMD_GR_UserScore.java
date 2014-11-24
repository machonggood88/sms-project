package com.qp.land_h.plazz.cmd.User;

import Lib_Struct.tagUserScore;
import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GR_UserScore extends Cmd {
	/** Íæ¼ÒID **/
	public long lUserID;

	public tagUserScore UserScore = new tagUserScore();

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		lUserID = Utility.read4Byte(data, index);
		index += 4;

		UserScore.lScore = Utility.read8Byte(data, index);
		index += 8;

		UserScore.lWinCount = Utility.read4Byte(data, index);
		index += 4;
		UserScore.lLostCount = Utility.read4Byte(data, index);
		index += 4;
		UserScore.lDrawCount = Utility.read4Byte(data, index);
		index += 4;
		UserScore.lFleeCount = Utility.read4Byte(data, index);
		index += 4;
		UserScore.lExperience = Utility.read4Byte(data, index);
		index += 4;

		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
