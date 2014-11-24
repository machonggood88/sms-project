package com.qp.land_h.plazz.cmd.User;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

public class CMD_GR_UserSitDown extends Cmd {
	public int nTableID;
	public int nChairID;
	public String szPassWord = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nTableID, nIndex);
		nIndex += 2;
		Utility.write2byte(data, nChairID, nIndex);
		nIndex += 2;
		nIndex += 2 * PDF.LEN_PASSWORD;
		return nIndex - pos;
	}

}
