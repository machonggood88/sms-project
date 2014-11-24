package com.qp.land_h.plazz.Plazz_Interface;

import Lib_Interface.IClientKernel;
import Lib_Interface.UserInterface.IUserManageSkin;

/**
 * 核心扩展接口
 * 
 * @note
 * @remark
 */
public interface IClientKernelEx extends IClientKernel {

	/** 获取用户管理 **/
	public IUserManageSkin GetUserManage();

	/** 设置接收模式 **/
	public void SetSocketReadMode(int mode);

	public int GetUserClock(int chair);

	public void ReleasUserTime();

	public void QuickSitDown();

	public void SendGameOption();

	/** 刷新请求 **/
	public void FrushUserInfo(long userid, int tablepos);

	public void SendUserChairInfoReq(int table, int chair);

}
