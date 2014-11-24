package com.qp.land_h.game.Game_Cmd;


public interface IFramesAnimationConrol{
	
	public void Stop(boolean release) ;
	
	public void Pause();
	
	public void Start(long spacetimes, boolean loop) ;
}
