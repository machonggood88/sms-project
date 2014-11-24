package com.qp.land_h.plazz.df;

import Lib_DF.DF;

/**
 * 定义
 * 
 * @note
 * @remark
 */
public class PDF extends DF {

	/** 列表最大个数 **/
	public final static int		MAX_ACCOUNTS_LIST	= 5;

	/** 存取方式 **/
	public static final int		INSURE_SAVE			= 0;
	/** 转账方式 **/
	public static final int		INSURE_TRANSFER		= 1;

	public static final String	RES_PATH_H			= "/landres/h/";
	public static final String	RES_PATH_M			= "/landres/m/";
	public static final String	RES_PATH_L			= "/landres/l/";

	static final public int		STYLE_LEFT			= 0x00;
	static final public int		STYLE_CENTRE		= 0x01;
	static final public int		STYLE_RIGHT			= 0x02;

	static final public int		STYLE_TOP			= 0x10;
	static final public int		STYLE_VCENTRE		= 0x20;
	static final public int		STYLE_BOTTOM		= 0x40;
}
