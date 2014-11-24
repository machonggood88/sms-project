package com.qp.land_h.plazz.Plazz_Interface;

import Lib_Interface.UserInterface.IClientUserItem;

public interface IGameClientManage {

	/** 初始化函数 **/
	public boolean OnInitGameEngine();

	/** 重置函数 **/
	public boolean OnResetGameEngine();

	/** 旁观信息 **/
	public boolean OnEventLookonMode(byte data[]);

	/** 游戏消息 **/
	public boolean OnEventGameMessage(int sub, byte[] data);

	/** 场景消息 **/
	public boolean OnEventSceneMessage(int gamestatus, boolean lookonuser,
			byte[] data);

	/** 场景消息 **/
	public boolean OnEventSceneMessage(byte[] data);

	/** 银行消息 **/
	public boolean OnEventInsureMessage(int sub, byte data[]);

	/** 用户进入 **/
	public void OnEventUserEnter(IClientUserItem useritem, boolean lookonuser);

	/** 用户离开 **/
	public void OnEventUserLeave(IClientUserItem useritem, boolean lookonuser);

	/** 用户积分 **/
	public void OnEventUserScore(IClientUserItem useritem, boolean lookonuser);

	/** 用户状态 **/
	public void OnEventUserStatus(IClientUserItem useritem, boolean lookonuser);
}
