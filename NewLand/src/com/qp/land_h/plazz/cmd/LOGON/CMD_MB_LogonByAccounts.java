package com.qp.land_h.plazz.cmd.LOGON;

import java.security.NoSuchAlgorithmException;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

public class CMD_MB_LogonByAccounts extends Cmd {

	public int nModuleID;
	public long lPlazaVersion;
	public byte cbDeviceYype;
	public String szPassWord = "";
	public String szAccounts = "";
	public String szMachineID = "";
	public String szMobilePhone = "";

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nModuleID, nIndex);
		nIndex += 2;
		// �㳡�汾
		Utility.write4byte(data, lPlazaVersion, nIndex);
		nIndex += 4;
		// �豸��
		data[nIndex++] = cbDeviceYype;
		// ��¼����
		String MD5Str = "";
		try {
			MD5Str = changeToMD5(szPassWord);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);

		nIndex += PDF.LEN_MD5 * 2;
		// ��¼�ʺ�
		if (szAccounts != null && !szAccounts.equals(""))
			Utility.stringToWcharUnicodeBytes(szAccounts, data, nIndex);
		nIndex += PDF.LEN_ACCOUNTS * 2;
		if (szMachineID != null && !szMachineID.equals("")) {
			try {
				MD5Str = changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_MACHINE_ID * 2;
		// �绰����
		if (szMobilePhone != null && !szMobilePhone.equals(""))
			Utility.stringToWcharUnicodeBytes(szMobilePhone, data, nIndex);
		nIndex += PDF.LEN_MOBILE_PHONE * 2;
		return nIndex - pos;
	}

	@Override
	public int ReadFromByteArray(byte[] arg0, int arg1) {

		return 0;
	}

}
