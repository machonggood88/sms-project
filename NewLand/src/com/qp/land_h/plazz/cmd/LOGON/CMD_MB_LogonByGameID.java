package com.qp.land_h.plazz.cmd.LOGON;

import java.security.NoSuchAlgorithmException;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

public class CMD_MB_LogonByGameID extends Cmd {
	public int nModuleID;
	public long lPlazaVersion;
	public long lGameID;
	public String szPassWord = "";
	public String szMachineID = "";
	public String szMobilePhone = "";

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nModuleID, nIndex);
		nIndex += 2;
		// ¹ã³¡°æ±¾
		Utility.write4byte(data, lPlazaVersion, nIndex);
		nIndex += 4;
		// µÇÂ¼ID
		Utility.write4byte(data, lGameID, nIndex);
		nIndex += 4;
		// µÇÂ¼ÃÜÂë
		if (szPassWord != null && !szPassWord.equals("")) {

			String MD5Str = "";
			try {
				MD5Str = changeToMD5(szPassWord);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_MD5 * 2;
		if (szMachineID != null && !szMachineID.equals("")) {

			String MD5Str = "";
			try {
				MD5Str = changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_MACHINE_ID * 2;
		// µç»°ºÅÂë
		if (szMobilePhone != null && !szMobilePhone.equals(""))
			Utility.stringToWcharUnicodeBytes(szMobilePhone, data, nIndex);
		nIndex += PDF.LEN_MOBILE_PHONE * 2;
		return nIndex - pos;
	}

	@Override
	public int ReadFromByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
}
