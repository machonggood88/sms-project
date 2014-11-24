package com.qp.land_h.plazz.cmd.LOGON;

import java.security.NoSuchAlgorithmException;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

public class CMD_MB_RegisterAccounts extends Cmd {
	public int nModuleID;
	public long lPlazaVersion;
	public byte cbDeviceType;
	public String szLogonPass = "";
	public String szInsurePass = "";
	public int nFaceID;
	public byte cbGender;
	public String szAccounts = "";
	public String szNickName = "";
	public String szMachineID = "";
	public String szMobilePhone = "";

	public CMD_MB_RegisterAccounts() {

	}

	public CMD_MB_RegisterAccounts(byte[] data, int pos) {
		super(data, pos);
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nModuleID, nIndex);
		nIndex += 2;
		// �㳡�汾
		Utility.write4byte(data, lPlazaVersion, nIndex);
		nIndex += 4;
		data[nIndex++] = 0x10;

		// ��¼����
		if (szLogonPass != null && !szLogonPass.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = changeToMD5(szLogonPass);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_MD5 * 2;
		// ��¼����
		if (szInsurePass != null && !szInsurePass.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = changeToMD5(szLogonPass);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_MD5 * 2;
		// ͷ���ʾ
		Utility.write2byte(data, nFaceID, nIndex);
		nIndex += 2;
		// �Ա�
		data[nIndex++] = cbGender;

		// ��¼�ʺ�
		if (szAccounts != null && !szAccounts.equals("")) {
			Utility.stringToWcharUnicodeBytes(szAccounts, data, nIndex);
		}
		nIndex += PDF.LEN_ACCOUNTS * 2;
		// �û��ǳ�
		if (szNickName != null && !szNickName.equals("")) {
			Utility.stringToWcharUnicodeBytes(szNickName, data, nIndex);
		}
		nIndex += PDF.LEN_NICENAME * 2;
		// ������
		if (szMachineID != null && !szMachineID.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_MACHINE_ID * 2;
		// �绰����
		if (szMobilePhone != null && !szMobilePhone.equals("")) {
			Utility.stringToWcharUnicodeBytes(szMobilePhone, data, nIndex);
		}
		nIndex += PDF.LEN_MOBILE_PHONE * 2;
		return nIndex - pos;
	}

	@Override
	public int ReadFromByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
