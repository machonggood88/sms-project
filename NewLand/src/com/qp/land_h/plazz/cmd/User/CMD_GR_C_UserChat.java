package com.qp.land_h.plazz.cmd.User;

import Net_Interface.ICmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

public class CMD_GR_C_UserChat implements ICmd {

	public int wChatLength;
	public int nChatColor;
	public long lTargetUserID;
	public String szChatString = "";

	@Override
	public int ReadFromByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, wChatLength
				+ (PDF.LEN_USER_CHAT > wChatLength ? 1 : 0), nIndex);
		nIndex += 2;
		Utility.write4byte(data, nChatColor, nIndex);
		nIndex += 4;
		Utility.write4byte(data, lTargetUserID, nIndex);
		nIndex += 4;
		Utility.stringToWcharUnicodeBytes(szChatString, data, nIndex);
		nIndex += (szChatString.length() + (PDF.LEN_USER_CHAT > szChatString
				.length() ? 1 : 0)) * 2;
		assert szChatString.length() == wChatLength;
		return nIndex - pos;
	}

}
