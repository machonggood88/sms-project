package com.qp.land_h.plazz.cmd.LOGON;

import Net_Struct.Cmd;
import Net_Utility.Utility;

public class CMD_MB_LogonSuccess extends Cmd {
	public int nFaceID;
	public byte cbGender;
	public long lUserID;
	public long lGameID;
	public long lExperience;
	public long lLoveLiness;
	public String szNickName;

	public CMD_MB_LogonSuccess(byte[] data, int pos) {
		super(data, pos);
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		nFaceID = Utility.read2Byte(data, nIndex);
		nIndex += 2;
		cbGender = data[nIndex++];
		lUserID = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		lGameID = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		lExperience = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		lLoveLiness = Utility.read4Byte(data, nIndex);
		nIndex += 4;
		szNickName = Utility.wcharUnicodeBytesToString(data, nIndex, 0);
		return nIndex - pos;
	}

}
