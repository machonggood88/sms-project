package com.qp.land_h.plazz.cmd.Game;

import Net_Struct.Cmd;

public class CMD_GF_GameStatus extends Cmd {

	public int nGameStatus;
	public int nAllowLookon;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		nGameStatus = data[index++];
		nAllowLookon = data[index++];

		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
