package com.qp.land_h.game.Game_Cmd;

public class tagScoreInfo{

	public boolean	bReady;
	public int		nBombCount;
	public int		nBankerScore;
	public long		lCellScore;
	public long		lGameScore;
	public boolean	bBanker;
	public boolean	bSpring;
	public boolean	bSpringSpring;

	public tagScoreInfo(){

	}

	public void ReSetData() {
		bReady=false;
		nBombCount = 0;
		nBankerScore = 0;
		lCellScore = 0;
		lGameScore = 0;
		bBanker = false;
		bSpring = false;
	}
}
