package com.qp.land_h.plazz.Plazz_Interface;

import Lib_Interface.IClientKernel;
import Lib_Interface.UserInterface.IUserManageSkin;

/**
 * ������չ�ӿ�
 * 
 * @note
 * @remark
 */
public interface IClientKernelEx extends IClientKernel {

	/** ��ȡ�û����� **/
	public IUserManageSkin GetUserManage();

	/** ���ý���ģʽ **/
	public void SetSocketReadMode(int mode);

	public int GetUserClock(int chair);

	public void ReleasUserTime();

	public void QuickSitDown();

	public void SendGameOption();

	/** ˢ������ **/
	public void FrushUserInfo(long userid, int tablepos);

	public void SendUserChairInfoReq(int table, int chair);

}
