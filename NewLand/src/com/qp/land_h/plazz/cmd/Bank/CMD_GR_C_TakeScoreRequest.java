package com.qp.land_h.plazz.cmd.Bank;

import java.security.NoSuchAlgorithmException;

import Net_Struct.Cmd;
import Net_Utility.Utility;

import com.qp.land_h.plazz.df.PDF;

/**
 * ȡ������
 * 
 */
public class CMD_GR_C_TakeScoreRequest extends Cmd {
	/** ��Ϸ���� **/
	public byte cbActivityGame;
	/** ȡ����Ŀ **/
	public long lTakeScore;
	/** �������� **/
	public String szInsurePass = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		data[nIndex++] = cbActivityGame;
		Utility.write8byte(data, lTakeScore, nIndex);
		nIndex += 8;
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
