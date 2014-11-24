package com.qp.land_h.plazz.Plazz_Interface;

import Lib_Interface.UserInterface.IClientUserItem;

public interface IGameClientManage {

	/** ��ʼ������ **/
	public boolean OnInitGameEngine();

	/** ���ú��� **/
	public boolean OnResetGameEngine();

	/** �Թ���Ϣ **/
	public boolean OnEventLookonMode(byte data[]);

	/** ��Ϸ��Ϣ **/
	public boolean OnEventGameMessage(int sub, byte[] data);

	/** ������Ϣ **/
	public boolean OnEventSceneMessage(int gamestatus, boolean lookonuser,
			byte[] data);

	/** ������Ϣ **/
	public boolean OnEventSceneMessage(byte[] data);

	/** ������Ϣ **/
	public boolean OnEventInsureMessage(int sub, byte data[]);

	/** �û����� **/
	public void OnEventUserEnter(IClientUserItem useritem, boolean lookonuser);

	/** �û��뿪 **/
	public void OnEventUserLeave(IClientUserItem useritem, boolean lookonuser);

	/** �û����� **/
	public void OnEventUserScore(IClientUserItem useritem, boolean lookonuser);

	/** �û�״̬ **/
	public void OnEventUserStatus(IClientUserItem useritem, boolean lookonuser);
}
