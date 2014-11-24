package com.qp.land_h.plazz.cmd.Bank;

import java.security.NoSuchAlgorithmException;

import com.qp.land_h.plazz.df.PDF;

import Net_Struct.Cmd;
import Net_Utility.Utility;

/** 查询银行 **/
public class CMD_GR_C_QuerInsureInfoRequest extends Cmd {
	/** 游戏动作 */
	public byte		cbActivityGame;
	public String	szPassWord	= "";
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = cbActivityGame;
		String MD5Str = "";
		try {
			MD5Str = changeToMD5(szPassWord);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Utility.stringToWcharUnicodeBytes(MD5Str, data, nIndex);

		nIndex += PDF.LEN_MD5 * 2;
		return nIndex - pos;
	}

}
