package com.qp.land_h.game.Game_Cmd;

import com.qp.land_h.plazz.df.PDF;

public class GDF extends PDF {

	public final static String	NULL					= "";
	public final static String	PHRASEINFOPATH			= "ini/phraseinfo.ini";
	public final static String	PHRASEINFOSECTION		= "GameChatShort";
	public final static int		EXPRESSIONCOUNT			= 80;
	public final static int		FASTINFOCOUNT			= 10;
	public final static int		MASK_COLOR				= 0x000000F0;
	public final static int		MASK_VALUE				= 0x0000000F;

	public final static String	GAME_NAME				= "������";
	public final static int		KIND_ID					= 220;
	public final static int		GAME_PLAYER				= 3;
	public final static int		MAX_CARDCOUNT			        = 20;
	public final static int		NORMAL_COUNT			        = 17;			// ��������
	public final static int		FULL_COUNT				= 54;			// ȫ����Ŀ
	public final static int		DISPATCH_COUNT			        = 51;			// �ɷ���Ŀ

	/** �߼����� **/
	public static final int		CT_ERROR				= 0;			// ��������
	public static final int		CT_1					= 1;			// ��������
	public static final int		CT_2					= 2;			// ��������
	public static final int		CT_3					= 3;			// ��������
	public static final int		CT_1_LINE				= 4;			// ��������
	public static final int		CT_2_LINE				= 5;			// ��������
	public static final int		CT_3_LINE				= 6;			// ��������
	public static final int		CT_3_LINE_1				= 7;			// ����һ��
	public static final int		CT_3_LINE_2				= 8;			// ����һ��
	public static final int		CT_4_LINE_1				= 9;			// �Ĵ�����
	public static final int		CT_4_LINE_2				= 10;			// �Ĵ�����
	public static final int		CT_BOME_CARD			        = 11;			// ը������
	public static final int		CT_MISSILE_CARD			        = 12;			// �������

	/** ״̬���� **/
	public final static int		GAME_SCENE_FREE			= GAME_STATUS_FREE;	// �ȴ���ʼ
	public final static int		GAME_SCENE_CALL			= GAME_STATUS_PLAY;	// �з�״̬
	public final static int		GAME_SCENE_PLAY			= GAME_STATUS_PLAY + 1; // ��Ϸ����

	/** ����������Ϸ���� **/
	public final static int		SUB_S_GAME_START		= 100;					// ��Ϸ��ʼ
	public final static int		SUB_S_CALL_SCORE		= 101;					// �û��з�
	public final static int		SUB_S_BANLER_INFO		= 102;					// ׯ����Ϣ
	public final static int		SUB_S_OUT_CARD			= 103;					// �û�����
	public final static int		SUB_S_PASS_CARD			= 104;					// �û�����
	public final static int		SUB_S_GAME_CONCLUDE		= 105;					// ��Ϸ����

	/** �ͻ�����Ϸ���� **/
	public final static int		SUB_C_CALL_SCORE		= 1;					// �û��з�
	public final static int		SUB_C_OUT_CARD			= 2;					// �û�����
	public final static int		SUB_C_PASS_CARD			= 3;					// �û�����

	public final static int		SOUND_GAME_BOMB			= 1;
	public final static int		SOUND_GAME_PLANE		= 2;
	public final static int		SOUND_GAME_END			= 3;
	public final static int		SOUND_GAME_START		= 4;
	public final static int		SOUND_GAME_WARN			= 5;
	public final static int		SOUND_GAME_SEND			= 6;
	public final static int		SOUND_GAME_SHULLE		= 7;
	public final static int		SOUND_GAME_ALERT		= 8;
	public final static int		SOUND_GAME_BANKERINFO	= 9;

	public final static int		SOUND_CS_M_0			= 10;
	public final static int		SOUND_CS_W_0			= 11;
	public final static int		SOUND_CS_M_1			= 12;
	public final static int		SOUND_CS_W_1			= 13;
	public final static int		SOUND_CS_M_2			= 14;
	public final static int		SOUND_CS_W_2			= 15;
	public final static int		SOUND_CS_M_3			= 16;
	public final static int		SOUND_CS_W_3			= 17;

	public final static int		SOUND_PASS_M_0			= 18;
	public final static int		SOUND_PASS_W_0			= 19;
	public final static int		SOUND_PASS_M_1			= 20;
	public final static int		SOUND_PASS_W_1			= 21;

	public final static int		SOUND_YA_M_0			= 22;
	public final static int		SOUND_YA_W_0			= 23;
	public final static int		SOUND_YA_M_1			= 24;
	public final static int		SOUND_YA_W_1			= 25;

	public final static int		SOUND_MSG_M_0			= 26;
	public final static int		SOUND_MSG_W_0			= 27;
	public final static int		SOUND_MSG_M_1			= 28;
	public final static int		SOUND_MSG_W_1			= 29;
	public final static int		SOUND_MSG_M_2			= 30;
	public final static int		SOUND_MSG_W_2			= 31;
	public final static int		SOUND_MSG_M_3			= 32;
	public final static int		SOUND_MSG_W_3			= 33;
	public final static int		SOUND_MSG_M_4			= 34;
	public final static int		SOUND_MSG_W_4			= 35;
	public final static int		SOUND_MSG_M_5			= 36;
	public final static int		SOUND_MSG_W_5			= 37;
	public final static int		SOUND_MSG_M_6			= 38;
	public final static int		SOUND_MSG_W_6			= 39;
	public final static int		SOUND_MSG_M_7			= 40;
	public final static int		SOUND_MSG_W_7			= 41;
	public final static int		SOUND_MSG_M_8			= 42;
	public final static int		SOUND_MSG_W_8			= 43;
	public final static int		SOUND_MSG_M_9			= 44;
	public final static int		SOUND_MSG_W_9			= 45;
	public final static int		SOUND_MSG_TIP			= 46;

	public final static int		SOUND_CARD_M_1			= 47;
	public final static int		SOUND_CARD_W_1			= 48;
	public final static int		SOUND_CARD_M_2			= 49;
	public final static int		SOUND_CARD_W_2			= 50;
	public final static int		SOUND_CARD_M_3			= 51;
	public final static int		SOUND_CARD_W_3			= 52;
	public final static int		SOUND_CARD_M_4			= 53;
	public final static int		SOUND_CARD_W_4			= 54;
	public final static int		SOUND_CARD_M_5			= 55;
	public final static int		SOUND_CARD_W_5			= 56;
	public final static int		SOUND_CARD_M_6			= 57;
	public final static int		SOUND_CARD_W_6			= 58;
	public final static int		SOUND_CARD_M_7			= 59;
	public final static int		SOUND_CARD_W_7			= 60;
	public final static int		SOUND_CARD_M_8			= 61;
	public final static int		SOUND_CARD_W_8			= 62;
	public final static int		SOUND_CARD_M_9			= 63;
	public final static int		SOUND_CARD_W_9			= 64;
	public final static int		SOUND_CARD_M_10			= 65;
	public final static int		SOUND_CARD_W_10			= 66;
	public final static int		SOUND_CARD_M_11			= 67;
	public final static int		SOUND_CARD_W_11			= 68;
	public final static int		SOUND_CARD_M_12			= 69;
	public final static int		SOUND_CARD_W_12			= 70;
	public final static int		SOUND_CARD_M_13			= 71;
	public final static int		SOUND_CARD_W_13			= 72;
	public final static int		SOUND_CARD_M_14			= 73;
	public final static int		SOUND_CARD_W_14			= 74;
	public final static int		SOUND_CARD_M_15			= 75;
	public final static int		SOUND_CARD_W_15			= 76;

	public final static int		SOUND_TYPE_M_DUI		= 77;
	public final static int		SOUND_TYPE_W_DUI		= 78;
	public final static int		SOUND_TYPE_M_SAN		= 79;
	public final static int		SOUND_TYPE_W_SAN		= 80;
	public final static int		SOUND_TYPE_M_DANSUN		= 81;
	public final static int		SOUND_TYPE_W_DANSUN		= 82;
	public final static int		SOUND_TYPE_M_DUISUN		= 83;
	public final static int		SOUND_TYPE_W_DUISUN		= 84;
	public final static int		SOUND_TYPE_M_SANSUN		= 85;
	public final static int		SOUND_TYPE_W_SANSUN		= 86;
	public final static int		SOUND_TYPE_M_SANDAIYI	= 87;
	public final static int		SOUND_TYPE_W_SANDAIYI	= 88;
	public final static int		SOUND_TYPE_M_SANDAIER	= 89;
	public final static int		SOUND_TYPE_W_SANDAIER	= 90;
	public final static int		SOUND_TYPE_M_SIDAIER	= 91;
	public final static int		SOUND_TYPE_W_SIDAIER	= 92;
	public final static int		SOUND_TYPE_M_HUIJIAN	= 93;
	public final static int		SOUND_TYPE_W_HUIJIAN	= 94;

}
