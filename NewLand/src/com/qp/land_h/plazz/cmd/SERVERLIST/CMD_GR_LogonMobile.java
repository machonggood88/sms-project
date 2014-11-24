package com.qp.land_h.plazz.cmd.SERVERLIST;

import java.security.NoSuchAlgorithmException;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

public class CMD_GR_LogonMobile extends Cmd {

	public int nGameID;
	public long lVersion;
	public byte cbDeviceType; // 设备类型
	public int wBehaviorFlags; // 行为标识
	public int wPageTableCount; // 分页桌数
	public long lUserID;
	public String szPassword = "";
	public String szMachineID = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		Utility.write2byte(data, nGameID, nIndex);
		nIndex += 2;
		// 广场版本
		Utility.write4byte(data, lVersion, nIndex);
		nIndex += 4;

		data[nIndex++] = cbDeviceType;
		Utility.write2byte(data, wBehaviorFlags, nIndex);
		nIndex += 2;

		Utility.write2byte(data, wPageTableCount, nIndex);
		nIndex += 2;

		Utility.write4byte(data, lUserID, nIndex);
		nIndex += 4;

		String MD5Str = "";
		try {
			MD5Str = changeToMD5(szPassword);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);

		nIndex += PDF.LEN_MD5 * 2;
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
		return nIndex - pos;
	}

}
