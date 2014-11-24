package com.qp.land_h.plazz.cmd.Bank;

import java.security.NoSuchAlgorithmException;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

/**
 * 转账请求
 * 
 */
public class CMD_GR_C_TransferScoreRequest extends Cmd {
	/** 游戏动作 **/
	public int cbActivityGame;
	/** 昵称赠送 **/
	public int cbByNickName;
	/** 转账金币 **/
	public long lTransferScore;
	/** 目标用户 **/
	public String szNickName = "";
	/** 银行密码 **/
	public String szInsurePass = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = (byte) cbActivityGame;
		data[nIndex++] = (byte) cbByNickName;
		Utility.write8byte(data, lTransferScore, nIndex);
		nIndex += 8;
		if (szNickName != null && !szNickName.equals("")) {
			Utility.stringToWcharUnicodeBytes(szNickName, data, nIndex);
		}
		nIndex += PDF.LEN_NICENAME * 2;
		if (szInsurePass != null && !szInsurePass.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = changeToMD5(szInsurePass);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += PDF.LEN_PASSWORD * 2;

		return nIndex - pos;
	}
}
