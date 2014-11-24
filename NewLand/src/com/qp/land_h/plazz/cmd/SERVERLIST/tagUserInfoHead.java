package com.qp.land_h.plazz.cmd.SERVERLIST;

import Net_Struct.Cmd;
import Net_Utility.Utility;
import android.util.Log;

import com.qp.land_h.plazz.df.PDF;

public class tagUserInfoHead extends Cmd {

	public final static int DTP_NULL = 0;
	public final static int DTP_GR_USER_NICKNAME = 10;
	public final static int DTP_GR_USER_GROUP_NAME = 11;
	public final static int DTP_GR_USER_UNDER_WRITE = 12;

	public long lGameID;
	public long lUserID;

	public int nFaceID;
	public long nCustomID;

	public byte cbGender;
	public byte cbMemberOrder;

	public int nTableID = PDF.INVALID_TABLE;
	public int nChairID = PDF.INVALID_CHAIR;
	public byte cbUserStatus;

	public long lScore;

	public long lWinCount;
	public long lLostCount;
	public long lDrawCount;
	public long lFleeCount;
	public long lExperience;

	public String szNickName = "";

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;

		lGameID = Utility.read4Byte(data, index);
		index += 4;

		lUserID = Utility.read4Byte(data, index);
		index += 4;

		nFaceID = Utility.read2Byte(data, index);
		index += 2;
		nCustomID = Utility.read4Byte(data, index);
		index += 4;
		cbGender = data[index++];
		cbMemberOrder = data[index++];

		nTableID = Utility.read2Byte(data, index);
		index += 2;
		nChairID = Utility.read2Byte(data, index);
		index += 2;
		cbUserStatus = data[index++];

		lScore = Utility.read8Byte(data, index);
		index += 8;

		lWinCount = Utility.read4Byte(data, index);
		index += 4;
		lLostCount = Utility.read4Byte(data, index);
		index += 4;
		lDrawCount = Utility.read4Byte(data, index);
		index += 4;
		lFleeCount = Utility.read4Byte(data, index);
		index += 4;
		lExperience = Utility.read4Byte(data, index);
		index += 4;

		int datasize = 0, datadescribe = 0;

		while (index + 4 < data.length) {
			datasize = Utility.read2Byte(data, index);
			index += 2;
			datadescribe = Utility.read2Byte(data, index);
			index += 2;
			if (datadescribe == DTP_NULL) {
				break;
			}
			switch (datadescribe) {
				case DTP_GR_USER_NICKNAME : {
					szNickName = Utility.wcharUnicodeBytesToString(data, index,
							datasize);
					index += datasize;
					break;
				}
				default : {
					Log.i("datadescribe", "unkwon dtp:" + datadescribe);
					break;
				}
			}
		}
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
