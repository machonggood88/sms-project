package com.qp.land_h.plazz.df;

/**
 * ���������������
 * 
 * @note
 * @remark
 */
public class NetCommand {

	// ��ͼģʽ
	/** ȫ������ **/
	public static final int VIEW_MODE_ALL = 0x0001;
	/** ���ֿ��� **/
	public static final int VIEW_MODE_PART = 0x0002;

	// ��Ϣģʽ
	/** ������Ϣ **/
	public static final int VIEW_INFO_LEVEL_1 = 0x0010;
	/** ������Ϣ **/
	public static final int VIEW_INFO_LEVEL_2 = 0x0020;
	/** ������Ϣ **/
	public static final int VIEW_INFO_LEVEL_3 = 0x0040;
	/** ������Ϣ **/
	public static final int VIEW_INFO_LEVEL_4 = 0x0080;

	// ��������
	/** �������� **/
	public static final int RECVICE_GAME_CHAT = 0x0100;
	/** �������� **/
	public static final int RECVICE_ROOM_CHAT = 0x0200;
	/** ����˽�� **/
	public static final int RECVICE_ROOM_WHISPER = 0x0400;

	// ��Ϊ��ʶ
	/** ��ͨ��¼ **/
	public static final int BEHAVIOR_LOGON_NORMAL = 0x0000;
	/** ������¼ **/
	public static final int BEHAVIOR_LOGON_IMMEDIATELY = 0x1000;

	// �豸�汾
	/** VGA VideoGraphics Array,��:��ʾ��ͼ����,�൱��640��480 ���� **/
	public static final byte DEVICETYPE_VGA = 0x10;
	/** QVGA QuarterVGA;��:VGA���ķ�֮һ,�ֱ���Ϊ320��240 **/
	public static final byte DEVICETYPE_QVGA = 0x11;
	/** HVGA Half-sizeVGA;��:VGA��һ��,�ֱ���Ϊ480��320 **/
	public static final byte DEVICETYPE_HVGA = 0x12;
	/** WVGA WideVideoGraphicsArray;��:�����VGA,�ֱ���Ϊ800��480���� **/
	public static final byte DEVICETYPE_WVGA = 0x13;
	/** WQVGA WideQuarterVGA��:�����QVGA,�ֱ��ʱ�QVGA��,��VGA��,һ����:400��240,480��272 **/
	public static final byte DEVICETYPE_WQVGA = 0x14;
	/** DVGA 960 640 **/
	public static final byte DEVICETYPE_DQVGA = 0x15;

	/**
	 * ��¼����logonSever���
	 */
	/** �㳡��¼ **/
	public final static int MDM_MB_LOGON = 100;
	/** ID��¼ **/
	public final static int SUB_MB_LOGON_GAMEID = 1;
	/** �ʺŵ�¼ **/
	public final static int SUB_MB_LOGON_ACCOUNTS = 2;
	/** ע���ʺ� **/
	public final static int SUB_MB_REGISTER_ACCOUNTS = 3;
	/** ��¼�ɹ� **/
	public final static int SUB_MB_LOGON_SUCCESS = 100;
	/** ��¼ʧ�� **/
	public final static int SUB_MB_LOGON_FAILURE = 101;
	/** ��¼��� **/
	public final static int SUB_MB_LOGON_FINISH = 102;
	/** �汾���� **/
	public final static int SUB_MB_UPDATE_NOTIFY = 200;

	/** �㳡��¼ **/
	public final static int MDM_GP_LOGON = 1;
	public final static int SUB_GP_UPDATE_NOTIFY = 200;

	/**
	 * �б�����logonSever���
	 */
	/** �б���Ϣ **/
	public final static int MDM_MB_SERVER_LIST = 101;
	/** ��ȡ�б� **/
	public final static int SUB_MB_LIST_KIND = 100;
	/** ��ȡ���� **/
	public final static int SUB_MB_LIST_SERVER = 101;
	/** ��ȡ��� **/
	public final static int SUB_MB_LIST_FINISH = 200;

	/**
	 * ��������logonSever���
	 */
	/** �û����� **/
	public final static int SUB_MB_USER_SERVICE = 102;
	/** �޸��ʺ� **/
	public final static int SUB_MB_MODIFY_ACCOUNTS = 1;
	/** �޸����� **/
	public final static int SUB_MB_MODIFY_LOGON_PASS = 2;
	/** �޸����� **/
	public final static int SUB_MB_MODIFY_INSURE_PASS = 3;
	/** �޸����� **/
	public final static int SUB_MB_MODIFY_INDIVIDUAL = 4;
	/** ��ѯ��Ϣ **/
	public final static int SUB_MB_QUERY_INDIVIDUAL = 10;
	/** �����ɹ� **/
	public final static int SUB_MB_OPERATE_SUCCESS = 100;
	/** ����ʧ�� **/
	public final static int SUB_MB_OPERATE_FAILURE = 101;
	/** �������� **/
	public final static int SUB_MB_USER_INDIVIDUAL = 200;

	/**
	 * ��¼���� GameServer���
	 */
	/** ��¼��Ϣ **/
	public final static int MDM_GR_LOGON = 1;
	/** �ֻ���¼ **/
	public final static int SUB_GR_LOGON_MOBILE = 2;
	/** ��¼�ɹ� **/
	public final static int SUB_GR_LOGON_SUCCESS = 100;
	/** ��¼ʧ�� **/
	public final static int SUB_GR_LOGON_FAILURE = 101;
	/** ��¼��� **/
	public final static int SUB_GR_LOGON_FINISH = 102;
	/** ������ʾ **/
	public final static int SUB_GR_UPDATE_NOTIFY = 200;

	/**
	 * �������� GameServer���
	 */
	/** ������Ϣ **/
	public final static int MDM_GR_GONFIG = 2;
	/** �б����� **/
	public final static int SUB_GR_CONFIG_COLUMN = 100;
	/** �������� **/
	public final static int SUB_GR_CONFIG_SERVER = 101;
	/** �������� **/
	public final static int SUB_GR_CONFIG_PROPERTY = 102;
	/** ������� **/
	public final static int SUB_GR_CONFIG_FINISH = 103;

	/**
	 * �û����� GameServer���
	 */
	/** �û���Ϣ **/
	public final static int MDM_GR_USER = 3;
	/** �û����� **/
	public final static int SUB_GR_USER_RULE = 1;
	/** �Թ����� **/
	public final static int SUB_GR_USER_LOOKON = 2;
	/** �������� **/
	public final static int SUB_GR_USER_SITDOWN = 3;
	/** �������� **/
	public final static int SUB_GR_USER_STANDUP = 4;
	/** �����û���Ϣ **/
	public final static int SUB_GR_USER_INFO_REQ = 9;
	/** �������λ�� **/
	public final static int SUB_GR_USER_CHAIR_REQ = 10;
	/** ���������û���Ϣ **/
	public final static int SUB_GR_USER_CHAIR_INFO_REQ = 11;
	/** �û����� **/
	public final static int SUB_GR_USER_ENTER = 100;
	/** �û����� **/
	public final static int SUB_GR_USER_SCORE = 101;
	/** �û�״̬ **/
	public final static int SUB_GR_USER_STATUS = 102;
	/** ����ʧ�� **/
	public final static int SUB_GR_REQUEST_FAILURE = 103;
	/** ������Ϣ **/
	public final static int SUB_GR_USER_CHAT = 201;
	/** ˽����Ϣ **/
	public final static int SUB_GR_WISPER_CHAT = 202;
	/** ������Ϣ **/
	public final static int SUB_GR_USER_EXPRESSION = 203;
	/** �Ự��Ϣ **/
	public final static int SUB_GR_WISPER_EXPRESSION = 204;
	/** �����û� **/
	public final static int SUB_GR_INVITE_USER = 300;

	/**
	 * ״̬���� GameServer���
	 */
	/** ״̬��Ϣ **/
	public final static int MDM_GR_STATUS = 4;
	/** ������Ϣ **/
	public final static int SUB_GR_TABLE_INFO = 100;
	/** ����״̬ **/
	public final static int SUB_GR_TABLE_STATUS = 101;

	/**
	 * ������� GameServer���
	 */
	/** ������� **/
	public final static int MDM_GF_FRAME = 100;
	/** ��Ϸ���� **/
	public final static int SUB_GF_GAME_OPTION = 1;
	/** �û�׼�� **/
	public final static int SUB_GF_USER_READY = 2;
	/** �Թ����� **/
	public final static int SUB_GF_LOOKON_GONFIG = 3;
	/** �û����� **/
	public final static int SUB_GF_USER_CHAT = 10;
	/** �û����� **/
	public final static int SUB_GF_USER_EXPRESSION = 11;
	/** ��Ϸ״̬ **/
	public final static int SUB_GF_GAME_STATUS = 100;
	/** ��Ϸ���� **/
	public final static int SUB_GF_GAME_SCENE = 101;
	/** �Թ�״̬ **/
	public final static int SUB_GF_LOOKON_STATUS = 102;
	/** ϵͳ��Ϣ **/
	public final static int SUB_GF_SYSTEM_MESSAGE = 200;
	/** ������Ϣ **/
	public final static int SUB_GF_ACTION_MESSAGE = 201;

	/**
	 * ��Ϸ���� GameServer���
	 */
	/** ��Ϸ���� **/
	public final static int MDM_GF_GAME = 200;

	/**
	 * ϵͳ���� Commom���
	 */
	/** ϵͳ��Ϣ **/
	public final static int MDM_CM_SYSTEM = 1000;
	/**  **/
	public final static int SUB_CM_SYSTEM_MESSAGE = 1;
	/**  **/
	public final static int SUB_CM_ACTION_MESSAGE = 2;
	/**  **/
	public final static int SUB_CM_DOWN_LOAD_MODULE = 3;

	/** ���в��� **/

	// ��������
	/** ������Ϣ **/
	public static final int MDM_GR_INSURE = 5;

	// ��������
	/** ��ѯ���� **/
	public static final int SUB_GR_QUERY_INSURE_INFO = 1;
	/** ������ **/
	public static final int SUB_GR_SAVE_SCORE_REQUEST = 2;
	/** ȡ����� **/
	public static final int SUB_GR_TAKE_SCORE_REQUEST = 3;
	/** ת�˲��� **/
	public static final int SUB_GR_TRANSFER_SCORE_REQUEST = 4;
	/** �������� **/
	public static final int SUB_GR_USER_INSURE_INFO = 100;
	/** ���гɹ� **/
	public static final int SUB_GR_USER_INSURE_SUCCESS = 101;
	/** ����ʧ�� **/
	public static final int SUB_GR_USER_INSURE_FAILURE = 102;
}
