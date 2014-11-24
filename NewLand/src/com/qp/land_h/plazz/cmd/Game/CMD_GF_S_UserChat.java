package com.qp.land_h.plazz.cmd.Game;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_GF_S_UserChat extends Cmd {
	public int wChatLength;
	public int nChatColor;
	public long lSendUserID;
	public long lTargetUserID;
	public String szChatString = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		wChatLength = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		nChatColor = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		lSendUserID = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		lTargetUserID = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		int lenght = 0;
		szChatString = Utility.wcharUnicodeBytesToString(data, nIndex, lenght);
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
}
