package com.qp.land_h.plazz.df;

/**
 * 网络连接相关命令
 * 
 * @note
 * @remark
 */
public class NetCommand {

	// 视图模式
	/** 全部可视 **/
	public static final int VIEW_MODE_ALL = 0x0001;
	/** 部分可视 **/
	public static final int VIEW_MODE_PART = 0x0002;

	// 信息模式
	/** 部分信息 **/
	public static final int VIEW_INFO_LEVEL_1 = 0x0010;
	/** 部分信息 **/
	public static final int VIEW_INFO_LEVEL_2 = 0x0020;
	/** 部分信息 **/
	public static final int VIEW_INFO_LEVEL_3 = 0x0040;
	/** 部分信息 **/
	public static final int VIEW_INFO_LEVEL_4 = 0x0080;

	// 其他配置
	/** 接收聊天 **/
	public static final int RECVICE_GAME_CHAT = 0x0100;
	/** 接收聊天 **/
	public static final int RECVICE_ROOM_CHAT = 0x0200;
	/** 接收私聊 **/
	public static final int RECVICE_ROOM_WHISPER = 0x0400;

	// 行为标识
	/** 普通登录 **/
	public static final int BEHAVIOR_LOGON_NORMAL = 0x0000;
	/** 立即登录 **/
	public static final int BEHAVIOR_LOGON_IMMEDIATELY = 0x1000;

	// 设备版本
	/** VGA VideoGraphics Array,即:显示绘图矩阵,相当于640×480 像素 **/
	public static final byte DEVICETYPE_VGA = 0x10;
	/** QVGA QuarterVGA;即:VGA的四分之一,分辨率为320×240 **/
	public static final byte DEVICETYPE_QVGA = 0x11;
	/** HVGA Half-sizeVGA;即:VGA的一半,分辨率为480×320 **/
	public static final byte DEVICETYPE_HVGA = 0x12;
	/** WVGA WideVideoGraphicsArray;即:扩大的VGA,分辨率为800×480像素 **/
	public static final byte DEVICETYPE_WVGA = 0x13;
	/** WQVGA WideQuarterVGA即:扩大的QVGA,分辨率比QVGA高,比VGA低,一般是:400×240,480×272 **/
	public static final byte DEVICETYPE_WQVGA = 0x14;
	/** DVGA 960 640 **/
	public static final byte DEVICETYPE_DQVGA = 0x15;

	/**
	 * 登录命令logonSever相关
	 */
	/** 广场登录 **/
	public final static int MDM_MB_LOGON = 100;
	/** ID登录 **/
	public final static int SUB_MB_LOGON_GAMEID = 1;
	/** 帐号登录 **/
	public final static int SUB_MB_LOGON_ACCOUNTS = 2;
	/** 注册帐号 **/
	public final static int SUB_MB_REGISTER_ACCOUNTS = 3;
	/** 登录成功 **/
	public final static int SUB_MB_LOGON_SUCCESS = 100;
	/** 登录失败 **/
	public final static int SUB_MB_LOGON_FAILURE = 101;
	/** 登录完成 **/
	public final static int SUB_MB_LOGON_FINISH = 102;
	/** 版本升级 **/
	public final static int SUB_MB_UPDATE_NOTIFY = 200;

	/** 广场登录 **/
	public final static int MDM_GP_LOGON = 1;
	public final static int SUB_GP_UPDATE_NOTIFY = 200;

	/**
	 * 列表命令logonSever相关
	 */
	/** 列表信息 **/
	public final static int MDM_MB_SERVER_LIST = 101;
	/** 获取列表 **/
	public final static int SUB_MB_LIST_KIND = 100;
	/** 获取房间 **/
	public final static int SUB_MB_LIST_SERVER = 101;
	/** 获取完成 **/
	public final static int SUB_MB_LIST_FINISH = 200;

	/**
	 * 服务命令logonSever相关
	 */
	/** 用户服务 **/
	public final static int SUB_MB_USER_SERVICE = 102;
	/** 修改帐号 **/
	public final static int SUB_MB_MODIFY_ACCOUNTS = 1;
	/** 修改密码 **/
	public final static int SUB_MB_MODIFY_LOGON_PASS = 2;
	/** 修改密码 **/
	public final static int SUB_MB_MODIFY_INSURE_PASS = 3;
	/** 修改资料 **/
	public final static int SUB_MB_MODIFY_INDIVIDUAL = 4;
	/** 查询信息 **/
	public final static int SUB_MB_QUERY_INDIVIDUAL = 10;
	/** 操作成功 **/
	public final static int SUB_MB_OPERATE_SUCCESS = 100;
	/** 操作失败 **/
	public final static int SUB_MB_OPERATE_FAILURE = 101;
	/** 个人资料 **/
	public final static int SUB_MB_USER_INDIVIDUAL = 200;

	/**
	 * 登录命令 GameServer相关
	 */
	/** 登录信息 **/
	public final static int MDM_GR_LOGON = 1;
	/** 手机登录 **/
	public final static int SUB_GR_LOGON_MOBILE = 2;
	/** 登录成功 **/
	public final static int SUB_GR_LOGON_SUCCESS = 100;
	/** 登录失败 **/
	public final static int SUB_GR_LOGON_FAILURE = 101;
	/** 登录完成 **/
	public final static int SUB_GR_LOGON_FINISH = 102;
	/** 升级提示 **/
	public final static int SUB_GR_UPDATE_NOTIFY = 200;

	/**
	 * 配置命令 GameServer相关
	 */
	/** 配置信息 **/
	public final static int MDM_GR_GONFIG = 2;
	/** 列表配置 **/
	public final static int SUB_GR_CONFIG_COLUMN = 100;
	/** 房间配置 **/
	public final static int SUB_GR_CONFIG_SERVER = 101;
	/** 道具配置 **/
	public final static int SUB_GR_CONFIG_PROPERTY = 102;
	/** 配置完成 **/
	public final static int SUB_GR_CONFIG_FINISH = 103;

	/**
	 * 用户命令 GameServer相关
	 */
	/** 用户信息 **/
	public final static int MDM_GR_USER = 3;
	/** 用户规则 **/
	public final static int SUB_GR_USER_RULE = 1;
	/** 旁观请求 **/
	public final static int SUB_GR_USER_LOOKON = 2;
	/** 坐下请求 **/
	public final static int SUB_GR_USER_SITDOWN = 3;
	/** 起立请求 **/
	public final static int SUB_GR_USER_STANDUP = 4;
	/** 请求用户信息 **/
	public final static int SUB_GR_USER_INFO_REQ = 9;
	/** 请求更换位置 **/
	public final static int SUB_GR_USER_CHAIR_REQ = 10;
	/** 请求椅子用户信息 **/
	public final static int SUB_GR_USER_CHAIR_INFO_REQ = 11;
	/** 用户进入 **/
	public final static int SUB_GR_USER_ENTER = 100;
	/** 用户分数 **/
	public final static int SUB_GR_USER_SCORE = 101;
	/** 用户状态 **/
	public final static int SUB_GR_USER_STATUS = 102;
	/** 请求失败 **/
	public final static int SUB_GR_REQUEST_FAILURE = 103;
	/** 聊天信息 **/
	public final static int SUB_GR_USER_CHAT = 201;
	/** 私语信息 **/
	public final static int SUB_GR_WISPER_CHAT = 202;
	/** 表情信息 **/
	public final static int SUB_GR_USER_EXPRESSION = 203;
	/** 会话消息 **/
	public final static int SUB_GR_WISPER_EXPRESSION = 204;
	/** 邀请用户 **/
	public final static int SUB_GR_INVITE_USER = 300;

	/**
	 * 状态命令 GameServer相关
	 */
	/** 状态信息 **/
	public final static int MDM_GR_STATUS = 4;
	/** 桌子信息 **/
	public final static int SUB_GR_TABLE_INFO = 100;
	/** 桌子状态 **/
	public final static int SUB_GR_TABLE_STATUS = 101;

	/**
	 * 框架命令 GameServer相关
	 */
	/** 框架命令 **/
	public final static int MDM_GF_FRAME = 100;
	/** 游戏配置 **/
	public final static int SUB_GF_GAME_OPTION = 1;
	/** 用户准备 **/
	public final static int SUB_GF_USER_READY = 2;
	/** 旁观配置 **/
	public final static int SUB_GF_LOOKON_GONFIG = 3;
	/** 用户聊天 **/
	public final static int SUB_GF_USER_CHAT = 10;
	/** 用户表情 **/
	public final static int SUB_GF_USER_EXPRESSION = 11;
	/** 游戏状态 **/
	public final static int SUB_GF_GAME_STATUS = 100;
	/** 游戏场景 **/
	public final static int SUB_GF_GAME_SCENE = 101;
	/** 旁观状态 **/
	public final static int SUB_GF_LOOKON_STATUS = 102;
	/** 系统消息 **/
	public final static int SUB_GF_SYSTEM_MESSAGE = 200;
	/** 动作消息 **/
	public final static int SUB_GF_ACTION_MESSAGE = 201;

	/**
	 * 游戏命令 GameServer相关
	 */
	/** 游戏命令 **/
	public final static int MDM_GF_GAME = 200;

	/**
	 * 系统命令 Commom相关
	 */
	/** 系统消息 **/
	public final static int MDM_CM_SYSTEM = 1000;
	/**  **/
	public final static int SUB_CM_SYSTEM_MESSAGE = 1;
	/**  **/
	public final static int SUB_CM_ACTION_MESSAGE = 2;
	/**  **/
	public final static int SUB_CM_DOWN_LOAD_MODULE = 3;

	/** 银行操作 **/

	// 主命令码
	/** 银行信息 **/
	public static final int MDM_GR_INSURE = 5;

	// 子命令码
	/** 查询银行 **/
	public static final int SUB_GR_QUERY_INSURE_INFO = 1;
	/** 存款操作 **/
	public static final int SUB_GR_SAVE_SCORE_REQUEST = 2;
	/** 取款操作 **/
	public static final int SUB_GR_TAKE_SCORE_REQUEST = 3;
	/** 转账操作 **/
	public static final int SUB_GR_TRANSFER_SCORE_REQUEST = 4;
	/** 银行资料 **/
	public static final int SUB_GR_USER_INSURE_INFO = 100;
	/** 银行成功 **/
	public static final int SUB_GR_USER_INSURE_SUCCESS = 101;
	/** 银行失败 **/
	public static final int SUB_GR_USER_INSURE_FAILURE = 102;
}
