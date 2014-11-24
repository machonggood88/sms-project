package com.qp.land_h.plazz.cmd;

import com.qp.land_h.plazz.df.PDF;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class TCP_Validate extends Cmd {

	public String szValidateKey = "";

	@Override
	public int ReadFromByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		if (szValidateKey != null && !szValidateKey.equals(""))
			Utility.stringToWcharUnicodeBytes(szValidateKey, data, nIndex);
		nIndex += PDF.LEN_USER_CHAT;
		return nIndex - pos;
	}
}
