package com.qp.land_h.game.Game_Engine;

import java.io.IOException;
import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.ICallBack;
import Lib_Interface.IKeyBackDispatch;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.CActivity;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

//import com.qp.land_h.game.Card.CCard_Big;
//import com.qp.land_h.game.Card.CCard_Middle;
//import com.qp.land_h.game.Card.CCard_Small;
import com.qp.land_h.game.Card.CHandCardControl;
import com.qp.land_h.game.Card.CTableCard;
import com.qp.land_h.game.Card.CardModule;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Cmd.IFramesAnimationConrol;
import com.qp.land_h.game.Game_Windows.CBackCardView;
import com.qp.land_h.game.Game_Windows.CChatMessageView;
import com.qp.land_h.game.Game_Windows.CFramesAnimation;
import com.qp.land_h.game.Game_Windows.CGameChat;
import com.qp.land_h.game.Game_Windows.CGameOptionEngine;
import com.qp.land_h.game.Game_Windows.CGameScoreView;
import com.qp.land_h.game.Game_Windows.CMoveCard;
import com.qp.land_h.game.Game_Windows.CPlaneView;
import com.qp.land_h.game.Game_Windows.CRocketView;
import com.qp.land_h.game.Game_Windows.CTableScoreView;
import com.qp.land_h.game.Game_Windows.CToolsBar;
import com.qp.land_h.game.Game_Windows.CUserHead;
import com.qp.land_h.game.Game_Windows.CUserInfoView;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Fram.Game.CGameFramView;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.cmd.Game.CMD_GF_S_UserChat;
import com.qp.land_h.plazz.cmd.User.CMD_GR_S_UserExpression;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public class CGameClientView extends CGameFramView implements ISingleClickListener, IKeyBackDispatch {

	public static final int	IDM_CALLBACK		= 1100;
	public static final int	IDM_HIDEUSERINF_0	= 1102;
	public static final int	IDM_HIDEUSERINF_1	= 1103;
	public static final int	IDM_HIDEUSERINF_2	= 1104;
	public static final int	IDM_HIDECHAT_0		= 1105;
	public static final int	IDM_HIDECHAT_1		= 1106;
	public static final int	IDM_HIDECHAT_2		= 1107;
	public static final int	IDM_HIDE_CHAT		= 1108;
	public static final int	IDM_SHOW_HELP		= 1109;
	public static final int	IDM_CALL_POINT		= 1110;
	public static final int	IDM_GAME_END		= 1111;
	public static final int	IDM_GAME_READY		= 1112;
	public static final int	IDM_GAME_START		= 1113;
	public static final int	IDM_GAME_SKIP		= 1114;
	public static final int	IDM_GAME_OUTCARD	= 1115;
	public static final int	IDM_GAME_PASS		= 1116;
	public static final int	IDM_SHUFFLE			= 1117;
	public static final int	IDM_SHUFFLE_END		= 1118;
	public static final int	IDM_DISPATH			= 1119;
	public static final int	IDM_DISPATH_END		= 1120;
	public static final int	IDM_BOMB_START		= 1121;

	public static final int	IDM_ROCKET			= 1123;
	public static final int	IDM_PLANE			= 1124;

	public static final int	IDM_BANKER_INFO		= 1126;
	public static final int	IDM_CALL_ANIMATION	= 1127;

	public static final int	IDM_HIDE_BACKCARD	= 1128;

	public CGameScoreView	m_GameScoreView;

	CGameOptionEngine		m_GameOption;
	CGameChat				m_GameChat;
	CToolsBar				m_ToolBar;
	CUserHead				m_UserHead[]		= new CUserHead[GDF.GAME_PLAYER];
	CUserInfoView			m_UserInfoView[]	= new CUserInfoView[GDF.GAME_PLAYER];
	CChatMessageView		m_ChatMessageView[]	= new CChatMessageView[GDF.GAME_PLAYER];

	CTableCard				m_TableCard[]		= new CTableCard[GDF.GAME_PLAYER];
	CTableScoreView			m_TableScore[]		= new CTableScoreView[GDF.GAME_PLAYER];

	CImageButton			m_btScore[]			= new CImageButton[4];

	CImageButton			m_btStart;
	CImageButton			m_btOutCard;
	CImageButton			m_btSkip;
	CImageButton			m_btPass;
	CImageButton			m_btReSelect;

	CHandCardControl		m_HandCardControl;

	CBackCardView			m_BackCardView;

	CImageEx				m_ImagePass;
	CImageEx				m_ImageCardNum;
	CImageEx				m_ImageWaitCallPoint;
	CImageEx				m_ImageErrorOutCard;
	CImageEx				m_ImageNoCardOut;

	boolean					m_bNoCardOut;
	boolean					m_bErrorOutCard;
	boolean					m_bWaitCallPoint;
	boolean					m_bPassState[];
	boolean					m_bTrustee[];
	int						m_nHandCardCount[];
	Point					m_ptCardCount[];
	boolean					m_bAnimation;

	CFramesAnimation		m_BombAnimation;
	CRocketView				m_RocketView;
	CPlaneView				m_PlaneView;

	CFramesAnimation		m_Land;

	CFramesAnimation		m_CallPointView[]	= new CFramesAnimation[3];

	CFramesAnimation		m_Farmer[]			= new CFramesAnimation[2];

	IFramesAnimationConrol	m_FaceAnimation[]	= new CFramesAnimation[GDF.GAME_PLAYER];

	CMoveCard				m_Card[]			= new CMoveCard[51];
	int						m_nStartUser;
	Point					m_ptSrc				= new Point();
	Point					m_ptDest[]			= new Point[GDF.GAME_PLAYER];
	Point					m_ptFaceAnimation[]	= new Point[GDF.GAME_PLAYER];
	int						m_MoveStep			= 0;

	int						m_MoveCardSpaceX	= 10;

	// test
	// int ntestType;

	// end test
	public CGameClientView(Context context) {
		super(context);

		setWillNotDraw(false);

		int ScreenW = ClientPlazz.SCREEN_WIDHT;
		int ScreenH = ClientPlazz.SCREEN_HEIGHT;

		GameFramEngine = new CGameClientEngine(this);

		ClientPlazz.onRecordTimes("****CGameClientView-1******");

		try {
			m_ImagePass = new CImageEx(ClientPlazz.RES_PATH + "gameres/pass.png");
			m_ImageWaitCallPoint = new CImageEx(ClientPlazz.RES_PATH + "gameres/wait_call.png");
			m_ImageErrorOutCard = new CImageEx(ClientPlazz.RES_PATH + "gameres/out_card_error.png");
			m_ImageNoCardOut = new CImageEx(ClientPlazz.RES_PATH + "gameres/nocardout.png");
			m_ImageCardNum = new CImageEx(ClientPlazz.RES_PATH + "gameres/num_cardcount.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		CardModule.OnInit(ScreenW, ScreenH);

		ClientPlazz.onRecordTimes("****CGameClientView-2******");

		m_Land = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/land/land_", 10);
		m_Farmer[0] = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/farmer/farmer_", 10);
		m_Farmer[1] = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/farmer/farmer_", 10);

		m_CallPointView[0] = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/call_point_1/call_point_", 8);
		m_CallPointView[1] = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/call_point_2/call_point_", 8);
		m_CallPointView[2] = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/call_point_3/call_point_", 8);

		for (int i = 0; i < 51; i++) {
			m_Card[i] = new CMoveCard(context);
			m_Card[i].layout(0, 0, CardModule.getWidth(1), CardModule.getHeight(1));
		}

		m_ptCardCount = new Point[GDF.GAME_PLAYER];
		m_ptDest = new Point[GDF.GAME_PLAYER];

		m_GameOption = new CGameOptionEngine(context);
		m_GameChat = new CGameChat(context);
		m_ToolBar = new CToolsBar(context);
		m_GameScoreView = new CGameScoreView(context);
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_ptAvatar[i] = new Point();
			m_ptReady[i] = new Point();
			m_ptClock[i] = new Point();
			m_ptDest[i] = new Point();
			m_ptFaceAnimation[i] = new Point();
			m_UserHead[i] = new CUserHead(context, i == 1);
			m_UserHead[i].Setchair(i);
			m_UserHead[i].setSingleClickListener(this);
			m_UserInfoView[i] = new CUserInfoView(context, i);
			m_UserInfoView[i].setClickable(false);
			m_TableCard[i] = new CTableCard(context, i, 13, 2);
			m_ChatMessageView[i] = new CChatMessageView(context, i);
			m_TableScore[i] = new CTableScoreView(context);
			m_ptCardCount[i] = new Point();
			m_ptDest[i] = new Point();
		}
		m_BombAnimation = new CFramesAnimation(context, ClientPlazz.RES_PATH + "gameres/animation/bomb/bomb_", 5);
		m_HandCardControl = new CHandCardControl(context);
		// m_ShuffleAnimation = new CShuffleAnimation(context);
		m_RocketView = new CRocketView(context);
		m_PlaneView = new CPlaneView(context);

		m_btStart = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_start.png");

		m_btOutCard = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_outcard.png");
		m_btSkip = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_skip.png");
		m_btPass = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_pass.png");
		m_btReSelect = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_reselect.png");

		m_btStart.setSingleClickListener(this);
		m_btOutCard.setSingleClickListener(this);
		m_btSkip.setSingleClickListener(this);
		m_btPass.setSingleClickListener(this);
		m_btReSelect.setSingleClickListener(this);

		for (int i = 0; i < m_btScore.length; i++) {
			m_btScore[i] = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_score_" + i + ".png");
			m_btScore[i].setSingleClickListener(this);
		}

		ClientPlazz.onRecordTimes("****CGameClientView-3******");

		addView(m_btOutCard);
		addView(m_btSkip);
		addView(m_btPass);
		addView(m_btReSelect);
		for (int i = 0; i < m_btScore.length; i++) {
			addView(m_btScore[i]);
		}
		addView(m_ToolBar);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			addView(m_TableCard[i]);
		}
		addView(m_HandCardControl);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			addView(m_UserHead[i]);
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			addView(m_UserInfoView[i]);
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			addView(m_ChatMessageView[i]);
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			addView(m_TableScore[i]);
		}

		if (CActivity.nDeviceType == GDF.DEVICETYPE_QVGA) {
			m_BackCardView = new CBackCardView(context);
			addView(m_BackCardView);
		}
		addView(m_BombAnimation);
		addView(m_btStart);
		// addView(m_ShuffleAnimation);
		addView(m_PlaneView);
		addView(m_RocketView);
		addView(m_GameOption);
		addView(m_GameChat);
		addView(m_GameScoreView);

		addView(m_Land);
		addView(m_Farmer[0]);
		addView(m_Farmer[1]);
		addView(m_CallPointView[0]);
		addView(m_CallPointView[1]);
		addView(m_CallPointView[2]);

		ClientPlazz.onRecordTimes("****CGameClientView-4******");

		ResetGameView();
		RectifyControl(ScreenW, ScreenH);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// RectifyControl(r - l, b - t);
	}

	@Override
	public void MainMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
			case NetCommand.SUB_GF_USER_CHAT : {
				if (obj != null) {
					CMD_GF_S_UserChat cmd = (CMD_GF_S_UserChat) obj;
					OnUserChat(cmd.lSendUserID, cmd.lTargetUserID, cmd.nChatColor, cmd.szChatString, cmd.wChatLength);
				}
				break;
			}
			case NetCommand.SUB_GF_USER_EXPRESSION : {
				if (obj != null) {
					CMD_GR_S_UserExpression cmd = (CMD_GR_S_UserExpression) obj;
					Log.i("OnUserExpression", "index = " + cmd.wItemIndex);
					OnUserExpression(cmd.lSendUserID, cmd.lTargetUserID, cmd.wItemIndex);
				}
				break;
			}
			case IDM_CALLBACK :
				if (obj != null) {
					((ICallBack) obj).OnCallBackDispath(true, null);
				}
				break;
			case IDM_HIDE_CHAT : {
				m_GameChat.setVisibility(INVISIBLE);
				break;
			}
			// case IDM_SHOW_HELP: {
			// m_GameOption.setVisibility(INVISIBLE);
			// //CAboutView about = ClientPlazz.GetAboutView();
			// if (about != null) {
			// addView(about);
			// about.layout(getWidth() / 2 - about.GetW() / 2, getHeight()
			// / 2 - about.GetH() / 2,
			// getWidth() / 2 + about.GetW() / 2, getHeight() / 2
			// + about.GetH() / 2);
			// }
			// break;
			// }
			case IDM_HIDEUSERINF_0 :
			case IDM_HIDEUSERINF_1 :
			case IDM_HIDEUSERINF_2 : {
				int index = main - IDM_HIDEUSERINF_0;
				m_UserInfoView[index].setVisibility(INVISIBLE);
				m_UserInfoView[index].ShowUserInfo(null);
				break;
			}
			case IDM_HIDECHAT_0 :
			case IDM_HIDECHAT_1 :
			case IDM_HIDECHAT_2 : {
				int index = main - IDM_HIDECHAT_0;
				m_ChatMessageView[index].setVisibility(INVISIBLE);
				m_ChatMessageView[index].SetChatMessage("", 0);
				m_ChatMessageView[index].SetChatExpression(-1);
				break;
			}
			case IDM_CALL_POINT : {
				if (obj != null && (Integer) obj == 1) {
					int score = sub;
					m_btScore[0].setVisibility(VISIBLE);
					m_btScore[0].setClickable(true);
					for (int i = 1; i < 4; i++) {
						m_btScore[i].setVisibility(VISIBLE);
						m_btScore[i].setClickable(score < i);
					}
				}
				if (m_HandCardControl.isClickable() == false)
					m_HandCardControl.setClickable(!GameFramEngine.IsAllowLookon());
				break;
			}
			case IDM_GAME_END : {
				StopAnimation();
				for (int i = 0; i < 4; i++)
					m_btScore[i].setVisibility(View.INVISIBLE);
				m_btOutCard.setVisibility(View.INVISIBLE);
				m_btPass.setVisibility(View.INVISIBLE);
				m_btSkip.setVisibility(View.INVISIBLE);
				m_btReSelect.setVisibility(View.INVISIBLE);
				m_btStart.setVisibility((!GameFramEngine.IsLookonMode()) ? View.VISIBLE : View.INVISIBLE);
				m_HandCardControl.setClickable(false);

				m_GameScoreView.setVisibility(VISIBLE);
				postInvalidate();
				break;
			}
			case IDM_GAME_READY : {
				m_btStart.setVisibility(sub == 1 ? VISIBLE : INVISIBLE);
				m_btStart.setClickable(sub == 1);
				m_ToolBar.m_btTrustee.setClickable(!GameFramEngine.IsLookonMode());
				postInvalidteClock(1);
				m_GameScoreView.setVisibility(INVISIBLE);
				break;
			}
			case IDM_GAME_OUTCARD : {
				if (obj != null && (Integer) obj == 1) {
					m_btOutCard.setVisibility(View.VISIBLE);
					m_btPass.setVisibility(View.VISIBLE);
					m_btSkip.setVisibility(View.VISIBLE);
					m_btReSelect.setVisibility(View.VISIBLE);
					m_btOutCard.setClickable(true);
					m_btPass.setClickable(sub > 0);
					m_btSkip.setClickable(true);
					m_btReSelect.setClickable(true);
				}
				if (m_HandCardControl.isClickable() == false)
					m_HandCardControl.setClickable(!GameFramEngine.IsAllowLookon());
				break;
			}
			case IDM_GAME_START : {
				if (sub == 1) {
					for (int i = 0; i < 4; i++) {
						m_btScore[i].setVisibility(View.VISIBLE);
						m_btScore[i].setClickable(true);
					}
				}
				m_HandCardControl.setClickable(true);
				break;
			}
			case IDM_SHUFFLE : {
				StartShuffleCard();
				break;
			}
			case IDM_SHUFFLE_END : {
				StartDispathCard();
				break;
			}
			case IDM_DISPATH : {
				DispathCard(sub);
				break;
			}
			case IDM_DISPATH_END : {
				DispathCardEnd();
				break;
			}

			case IDM_BOMB_START : {
				StartBomb();
				break;
			}
			case IDM_PLANE : {
				StartPlane();
				break;
			}
			case IDM_ROCKET : {
				StartRocket();
				break;
			}
			case IDM_BANKER_INFO : {
				OnChangeFaceAnimation(sub);
				OnShowLandCard(1);
				break;
			}
			case IDM_CALL_ANIMATION : {
				OnCallPointAnimaton(sub, (Integer) obj);
				break;
			}
			case IDM_HIDE_BACKCARD : {
				if (m_BackCardView != null && m_BackCardView.getVisibility() == VISIBLE)
					m_BackCardView.setVisibility(INVISIBLE);
				break;
			}

		}
	}

	/** 叫分动画 **/
	private void OnCallPointAnimaton(int chair, int point) {
		if (point > 0 && point < 4) {
			int index = point - 1;

			m_CallPointView[index].Start(150, false);
		}
	}

	/** 头像动画 ***/
	private void OnChangeFaceAnimation(int sub) {
		if (sub >= 0 && sub < GDF.GAME_PLAYER) {
			CUserHead.OnHideFace(true);
			int index = 0;
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {

				CFramesAnimation item = ((i == sub) ? m_Land : m_Farmer[index++]);
				m_FaceAnimation[i] = item;
				((CFramesAnimation) m_FaceAnimation[i]).layout(m_ptFaceAnimation[i].x, m_ptFaceAnimation[i].y, m_ptFaceAnimation[i].x + item.GetW(),
						m_ptFaceAnimation[i].y + item.GetH());
				if (m_UserHead[i].GetTrustee() == false) {
					// m_UserHead[i].setVisibility(INVISIBLE);
					item.Start(200, true);
				}
			}
		} else {
			CUserHead.OnHideFace(false);
			m_Land.Stop(true);
			m_Farmer[0].Stop(true);
			m_Farmer[1].Stop(true);
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				// m_UserHead[i].setVisibility(VISIBLE);
				m_FaceAnimation[i] = null;
			}
		}

	}

	/** 用户表情 **/
	private void OnUserExpression(long lSendUserID, long lTargetUserID, int wItemIndex) {
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			IClientUserItem item = GetClientUserItem(i);
			if (item == null)
				continue;
			if (item.GetUserID() == lSendUserID) {
				m_GameChat.onRecordGameMessage(item.GetNickName(), wItemIndex);
				GDF.RemoveMainMessages(IDM_HIDECHAT_0 + i);
				m_ChatMessageView[i].SetChatMessage("", 0);
				m_ChatMessageView[i].SetChatExpression(wItemIndex);
				m_ChatMessageView[i].setVisibility(VISIBLE);
				GDF.SendMainMessageDelayed(IDM_HIDECHAT_0 + i, i, GetTag(), null, 2000);
				break;
			}
		}
	}

	/** 用户聊天 **/
	private void OnUserChat(long lSendUserID, long lTargetUserID, int nChatColor, String szChatString, int wChatLength) {
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			IClientUserItem item = GetClientUserItem(i);
			if (item == null)
				continue;
			if (item.GetUserID() == lSendUserID) {
				m_GameChat.onRecordGameMessage(item.GetNickName(), szChatString, item.GetGender());
				GDF.RemoveMainMessages(IDM_HIDECHAT_0 + i);
				int color = nChatColor | 0xff000000;
				m_ChatMessageView[i].SetChatExpression(-1);
				m_ChatMessageView[i].SetChatMessage(szChatString, color);
				m_ChatMessageView[i].setVisibility(VISIBLE);
				GDF.SendMainMessageDelayed(IDM_HIDECHAT_0 + i, i, GetTag(), null, 2000);
				break;
			}
		}
	}

	/** 用户信息 **/
	private boolean OnUserInfo(int chair) {
		if (chair > -1 && chair < GDF.GAME_PLAYER) {
			GDF.RemoveMainMessages(IDM_HIDEUSERINF_0 + chair);
			m_UserInfoView[chair].ShowUserInfo(GetClientUserItem(chair));
			m_UserInfoView[chair].setVisibility(VISIBLE);
			GDF.SendMainMessageDelayed(IDM_HIDEUSERINF_0 + chair, chair, GetTag(), null, 2000);
		}
		return false;
	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		super.OnDestoryRes();
		GameFramEngine.OnResetGameEngine();
		// ClientPlazz.RemoveAboutView();
		CUserHead.OnDestoryRes();
		for (int i = 0; i < m_UserHead.length; i++)
			m_UserHead[i].OnEventUserStatus(null);
		CUserInfoView.OnDestoryRes();
		m_GameChat.OnDestoryRes();
		m_ToolBar.OnDestoryRes();
		m_GameOption.OnDestoryRes();

		m_ImagePass.OnReleaseImage();
		m_ImageWaitCallPoint.OnReleaseImage();
		m_ImageErrorOutCard.OnReleaseImage();
		m_ImageNoCardOut.OnReleaseImage();
		m_ImageCardNum.OnReleaseImage();

		if (m_BackCardView != null)
			m_BackCardView.OnDestoryRes();
		// CCard_Big.OnReleaseCard();
		// CCard_Small.OnReleaseCard();
		// CCard_Middle.OnReleaseCard();
		CardModule.OnDestoryRes();
		ResetGameView();

		// setChildrenDrawingCacheEnabled(false);
	}

	@Override
	public void OnInitRes() {
		super.OnInitRes();
		setBackgroundDrawable(new BitmapDrawable(new CImage(PDF.GetContext(), ClientPlazz.RES_PATH + "gameres/bg_game.png", null, true).GetBitMap()));

		try {
			m_ImagePass.OnReLoadImage();
			m_ImageCardNum.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_GameScoreView.setVisibility(INVISIBLE);
		CUserHead.OnInitRes();
		// CUserInfoView.OnInitRes();
		m_ToolBar.OnInitRes();
		m_GameChat.OnInitRes();
		// m_GameOption.OnInitRes();
		if (m_BackCardView != null)
			m_BackCardView.OnInitRes();
		// setChildrenDrawingCacheEnabled(true);
		CardModule.OnInitRes();
	}

	@Override
	public boolean KeyBackDispatch() {
		boolean standup = true;
		if (m_GameOption.getVisibility() == VISIBLE) {
			m_GameOption.setVisibility(INVISIBLE);
			standup = false;
		}
		if (m_GameChat.getVisibility() == VISIBLE) {
			m_GameChat.setVisibility(INVISIBLE);
			standup = false;
		}
		if (m_GameScoreView.getVisibility() == VISIBLE) {
			m_GameScoreView.setVisibility(INVISIBLE);
			standup = false;
		}

		if (GameFramEngine.GetMeUserStatus() != GDF.US_PLAYING && standup) {
			GameFramEngine.PerformStandupAction(false);
			return true;
		}
		return !standup;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		int id = view.getId();
		if (id == m_btStart.getId()) {
			m_btStart.setVisibility(INVISIBLE);
			ResetGameView();
			GDF.SendSubMessage(IDM_GAME_START, 0, GetTag(), null);
			return true;
		} else if (id == m_btOutCard.getId()) {
			GDF.SendSubMessage(IDM_GAME_OUTCARD, 0, GetTag(), null);
			return true;
		} else if (id == m_btPass.getId()) {
			GDF.SendSubMessage(IDM_GAME_PASS, 0, GetTag(), null);
			return true;
		} else if (id == m_btSkip.getId()) {
			GDF.SendSubMessage(IDM_GAME_SKIP, 0, GetTag(), null);
			return true;
		} else if (id == m_btReSelect.getId()) {
			m_HandCardControl.SetAllCardShoot(false);
			return true;
		} else {
			for (int i = 0; i < m_btScore.length; i++) {
				if (id == m_btScore[i].getId()) {
					for (int j = 0; j < m_btScore.length; j++) {
						m_btScore[j].setVisibility(INVISIBLE);
					}
					OnCallPointAnimaton(1, i);
					GDF.SendSubMessage(IDM_CALL_POINT, i, GetTag(), null);

					return true;
				}
			}
			for (int i = 0; i < m_UserHead.length; i++) {

				if (id == m_UserHead[i].getId()) {
					if (GetClientUserItem(i) != null) {
						return OnUserInfo(i);
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void Render(Canvas canvas) {
		int DisplayW = ClientPlazz.SCREEN_WIDHT;
		int DisplayH = ClientPlazz.SCREEN_HEIGHT;
		Paint p = new Paint();
		p.setAntiAlias(true);

		// 玩家信息
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			IClientUserItem UserItem = GetClientUserItem(i);
			boolean bDrawReady = false;
			if (UserItem != null) {
				// 准备状态
				if (UserItem.GetUserStatus() == GDF.US_READY) {
					DrawUserReady(canvas, m_ptReady[i].x, m_ptReady[i].y);
					bDrawReady = true;
				}
				// 时钟信息
				int time = GetUserClock(i);
				if (time != 0) {
					DrawUserClock(canvas, m_ptClock[i].x, m_ptClock[i].y, time);
				}
			}

			if (m_bPassState[i] && !bDrawReady) {
				m_ImagePass.DrawImage(canvas, m_ptReady[i].x, m_ptReady[i].y);
			}
			if (m_nHandCardCount[i] > 0) {

				if (i != 1) {
					if (m_UserHead[i].GetLandMode() == 1) {
						CardModule.DrawLandCard(canvas, m_ptCardCount[i].x, m_ptCardCount[i].y, 2);
						// CCard_Small.DrawLand(canvas, m_ptCardCount[i].x, m_ptCardCount[i].y);
					} else {
						CardModule.DrawFramCard(canvas, m_ptCardCount[i].x, m_ptCardCount[i].y, 2);
						// CCard_Small.DrawFarm(canvas, m_ptCardCount[i].x, m_ptCardCount[i].y);
					}
				}
				int x = 0, y = 0;
				// if (i == 0) {
				// x = m_ptCardCount[i].x + CCard_Small.GetW();
				// }
				// else {
				// x = m_ptCardCount[i].x;
				// }
				x = m_ptCardCount[i].x;
				if (i != 1)
					y = m_ptCardCount[i].y + CardModule.getHeight(2) - m_ImageCardNum.GetH();
				else
					y = m_ptCardCount[i].y;
				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageCardNum, x, y, "0123456789", m_nHandCardCount[i] + "", GDF.STYLE_LEFT);
			}

		}

		// 等待叫分
		if (m_bWaitCallPoint) {
			if (m_ImageWaitCallPoint.GetBitMap() == null) {
				try {
					m_ImageWaitCallPoint.OnReLoadImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (m_ImageWaitCallPoint.GetBitMap() != null)
				m_ImageWaitCallPoint.DrawImage(canvas, DisplayW / 2 - m_ImageWaitCallPoint.GetW() / 2, DisplayH / 2 - m_ImageWaitCallPoint.GetH() / 2 - 10);
		} else {
			if (m_ImageWaitCallPoint.GetBitMap() != null) {
				m_ImageWaitCallPoint.OnReleaseImage();
			}
		}

		// 没牌出
		if (m_bNoCardOut) {
			if (m_ImageNoCardOut.GetBitMap() == null) {
				try {
					m_ImageNoCardOut.OnReLoadImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (m_ImageNoCardOut.GetBitMap() != null)
				m_ImageNoCardOut.DrawImage(canvas, DisplayW / 2 - m_ImageNoCardOut.GetW() / 2, DisplayH / 2 - m_ImageNoCardOut.GetH() / 2 - 10);
		} else {
			if (m_ImageNoCardOut.GetBitMap() != null) {
				m_ImageNoCardOut.OnReleaseImage();
			}
		}

		// 错误出牌
		if (m_bErrorOutCard) {
			if (m_ImageErrorOutCard.GetBitMap() == null) {
				try {
					m_ImageErrorOutCard.OnReLoadImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (m_ImageErrorOutCard.GetBitMap() != null)
				m_ImageErrorOutCard.DrawImage(canvas, DisplayW / 2 - m_ImageErrorOutCard.GetW() / 2, DisplayH / 2 - m_ImageErrorOutCard.GetH() / 2 - 10);
		} else {
			if (m_ImageErrorOutCard.GetBitMap() != null) {
				m_ImageErrorOutCard.OnReleaseImage();
			}
		}

		if (CActivity.nDeviceType == GDF.DEVICETYPE_QVGA) {
			p.setTextSize(14);
			p.setStrokeWidth(8);
			p.setColor(Color.rgb(250, 38, 0));
			p.setTextAlign(Align.RIGHT);
			CText.DrawTextEllip(canvas, "底: " + m_ToolBar.m_lCellscore, ClientPlazz.SCREEN_WIDHT / 2 - 5, 42, 80, p);
			p.setTextAlign(Align.LEFT);
			CText.DrawTextEllip(canvas, "倍: " + m_ToolBar.m_nPoint, ClientPlazz.SCREEN_WIDHT / 2 + 5, 42, 80, p);
		}

		// test
		// int x = 0, y = 0;
		// Random a = new Random(System.currentTimeMillis());
		// for (int i = 0; i < CGameLogic.CardData.length; i++) {
		//
		// CardModule.DrawCard(canvas, x, y, CGameLogic.CardData[i], a.nextInt(3) == 0, ntestType);
		// x += CardModule.getWidth(ntestType);
		// if (x > DisplayW - CardModule.getWidth(ntestType)) {
		// x = 0;
		// y += CardModule.getHeight(ntestType);
		// }
		// }

		// end test
	}

	public boolean OnChatClick() {
		if (m_GameOption.getVisibility() == VISIBLE)
			m_GameOption.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);
		m_GameChat.setVisibility((m_GameChat.getVisibility() == VISIBLE) ? INVISIBLE : VISIBLE);

		return true;
	}

	public boolean OnOptionClick() {
		// Random rand = new Random();
		// OnCallPointAnimaton(rand.nextInt(3), rand.nextInt(3) + 1);
		// tagScoreInfo scoreinfo = new tagScoreInfo();
		// scoreinfo.lCellScore = 1;
		// scoreinfo.nBankerScore = 3;
		// scoreinfo.bBanker = true;
		// scoreinfo.lGameScore = 9999;
		// scoreinfo.bSpring = true;
		// m_GameScoreView.SetScoreDate(scoreinfo);
		//
		// m_GameScoreView.setVisibility(VISIBLE);

		if (m_GameChat.getVisibility() == VISIBLE)
			m_GameChat.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);

		m_GameChat.setVisibility(INVISIBLE);
		m_GameOption.setVisibility((m_GameOption.getVisibility() == VISIBLE) ? INVISIBLE : VISIBLE);
		return true;
	}

	public boolean OnTrusteeClick() {
		// // test
		// ntestType = (ntestType + 1) % 3;
		// postInvalidate();
		// // end test
		if (m_GameChat.getVisibility() == VISIBLE)
			m_GameChat.setVisibility(INVISIBLE);
		if (m_GameOption.getVisibility() == VISIBLE)
			m_GameOption.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);

		m_UserHead[1].SetTrustee(!m_UserHead[1].GetTrustee());
		m_UserHead[1].postInvalidate();
		((CGameClientEngine) GameFramEngine).m_bTrustee = m_UserHead[1].GetTrustee();
		if (m_FaceAnimation[1] != null) {
			if (m_UserHead[1].GetTrustee()) {
				// m_UserHead[1].setVisibility(VISIBLE);
				m_FaceAnimation[1].Stop(false);
			} else {
				// m_UserHead[1].setVisibility(INVISIBLE);
				m_FaceAnimation[1].Start(200, true);
			}
		}
		return true;
	}

	public boolean OnShopClick() {
		return true;
	}

	public boolean OnChangePosClick() {
		if (m_GameChat.getVisibility() == VISIBLE)
			m_GameChat.setVisibility(INVISIBLE);
		if (m_GameOption.getVisibility() == VISIBLE)
			m_GameOption.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);
		GameFramEngine.PerformStandupAction(false);
		onQuickSitDown();
		return true;
	}

	public boolean OnBackRoomClick() {
		if (m_GameChat.getVisibility() == VISIBLE)
			m_GameChat.setVisibility(INVISIBLE);
		if (m_GameOption.getVisibility() == VISIBLE)
			m_GameOption.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);

		if (GameFramEngine.GetMeUserStatus() != GDF.US_PLAYING) {
			GameFramEngine.PerformStandupAction(false);
		} else {
			GDF.SendMainMessage(ClientPlazz.MM_QUERY_EXIT, 0, null);
		}
		return true;
	}

	/**
	 * 触碰处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (m_GameChat.getVisibility() == VISIBLE)
			m_GameChat.setVisibility(INVISIBLE);
		if (m_GameOption.getVisibility() == VISIBLE)
			m_GameOption.setVisibility(INVISIBLE);
		if (m_GameScoreView.getVisibility() == VISIBLE)
			m_GameScoreView.setVisibility(INVISIBLE);
		return super.onTouchEvent(event);
	}

	public void SetUserPassState(int chair, boolean pass) {
		if (chair != GDF.INVALID_CHAIR) {
			m_bPassState[chair] = pass;
		} else {
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				m_bPassState[i] = false;
			}
		}
	}

	public void SetUserCallScore(int chair, int score) {
		if (chair != GDF.INVALID_CHAIR) {
			m_TableScore[chair].point = score;
			m_TableScore[chair].postInvalidate();
		} else {
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				m_TableScore[i].point = 0;
				m_TableScore[i].postInvalidate();
			}
		}
	}

	public void SetBankerScore(int score) {
		m_ToolBar.m_nPoint = score;
		m_ToolBar.postInvalidate();
	}

	public void SetBankerUser(int chair) {
		if (chair == GDF.INVALID_ITEM) {
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				m_UserHead[i].SetLandMode(0);
				m_UserHead[i].postInvalidate();
			}
		} else {
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				m_UserHead[i].SetLandMode(i == chair ? 1 : 2);
				m_UserHead[i].postInvalidate();
			}
		}
	}

	public void SetCurrentUser(int chair) {

		for (int i = 0; i < m_FaceAnimation.length; i++) {
			if (m_FaceAnimation[i] == null || m_UserHead[i].GetTrustee())
				continue;
			if (i == chair)
				m_FaceAnimation[i].Start(200, true);
			else
				m_FaceAnimation[i].Pause();
		}
	}

	public void SetUserCountWarn(int invalidItem, boolean b) {

	}

	public void SetWaitCallScore(boolean wait) {
		m_bWaitCallPoint = wait;
	}

	public void SetNoCardOut(boolean nocard) {
		m_bNoCardOut = nocard;
	}

	public void SetErrorOutCard(boolean error) {
		m_bErrorOutCard = error;
	}

	public void SetCellScore(long lCellScore) {
		m_ToolBar.m_lCellscore = lCellScore;
		m_ToolBar.postInvalidate();
	}

	public void SetCardCount(int chair, int count) {
		if (chair != GDF.INVALID_CHAIR) {
			m_nHandCardCount[chair] = count;
		}

	}

	public void SetBombCount(int BombCount) {
		// TODO Auto-generated method stub

	}

	public void SetBackCard(int[] nCardData, boolean mask) {
		m_ToolBar.SetBackData(nCardData, mask);
		if (m_BackCardView != null) {
			m_BackCardView.SetBackData(nCardData);
		}
		m_ToolBar.postInvalidate();
	}

	@Override
	public void RectifyControl(int width, int height) {

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				onRectifyControlH(width, height);
				break;
			case DF.DEVICETYPE_HVGA :
				onRectifyControlM(width, height);
				break;
			case DF.DEVICETYPE_QVGA :
				onRectifyControlL(width, height);
				break;
		}
	}

	private void onRectifyControlL(int width, int height) {
		m_MoveCardSpaceX = 2;
		// 游戏按钮
		m_btStart.layout(width / 2 - m_btStart.getW() / 2, height / 2, 0, 0);

		int btw = m_btOutCard.getW() + 5;

		m_btOutCard.layout(width - 10 - btw, height / 2, 0, 0);
		m_btReSelect.layout(width - 10 - btw * 2, height / 2, 0, 0);
		m_btSkip.layout(width - 10 - btw * 3, height / 2, 0, 0);
		m_btPass.layout(width - 10 - btw * 4, height / 2, 0, 0);

		btw = m_btScore[0].getW() + 5;
		for (int i = m_btScore.length - 1; i >= 0; i--) {
			m_btScore[i].layout(width - 10 - btw * (i + 1), height / 2, 0, 0);
		}

		m_ptAvatar[0].set(0, 0);
		m_ptAvatar[1].set(0, height - CardModule.getHeight(0) - 15 - m_UserHead[1].GetH());
		m_ptAvatar[2].set(width - m_UserHead[2].GetW(), 0);

		m_ptReady[0].set(55, 65);
		m_ptReady[1].set(width / 2 - GetReadyW() / 2, height / 2);
		m_ptReady[2].set(245, 65);

		m_ptClock[0].set(width / 4, 55);

		m_ptClock[1].set(width / 2, m_btStart.getTop() - GetClockH() / 2);

		m_ptClock[2].set(width * 3 / 4, 55);

		m_ptCardCount[0].set(m_ptAvatar[0].x, m_ptAvatar[0].y + m_UserHead[0].GetH());
		m_ptCardCount[1].set(m_ptAvatar[1].x + 80, m_ptAvatar[1].y + m_UserHead[1].GetH() - m_ImageCardNum.GetH());
		m_ptCardCount[2].set(width - CardModule.getWidth(1), m_ptAvatar[2].y + m_UserHead[0].GetH());

		m_ptSrc.set(width / 2 - CardModule.getWidth(1) / 2, 50);
		m_ptDest[0].set(0, 50);
		m_ptDest[1].set(width / 2 - CardModule.getWidth(1) / 2, height - CardModule.getHeight(0) - CardModule.getHeight(1));
		m_ptDest[2].set(width - CardModule.getWidth(1), 50);

		int x = 0, y = 0;

		// 设置窗口
		m_GameOption.layout(width / 2 - m_GameOption.GetW() / 2, height / 2 - m_GameOption.GetH() / 2, width / 2 + m_GameOption.GetW() / 2, height / 2
				+ m_GameOption.GetH() / 2);
		// 聊天窗口
		m_GameChat.layout(width / 2 - m_GameChat.GetW() / 2, height / 2 - m_GameChat.GetH() / 2, width / 2 + m_GameChat.GetW() / 2,
				height / 2 + m_GameChat.GetH() / 2);

		// 用户头像
		for (int i = 0; i < m_UserHead.length; i++) {
			m_UserHead[i].layout(m_ptAvatar[i].x, m_ptAvatar[i].y, m_ptAvatar[i].x + m_UserHead[i].GetW(), m_ptAvatar[i].y + m_UserHead[i].GetH());
		}

		// 用户信息
		x = m_ptAvatar[0].x;
		y = m_ptAvatar[0].y + m_UserHead[0].GetH() - 35;
		m_UserInfoView[0].layout(x, y, x + m_UserInfoView[0].GetW(), y + m_UserInfoView[0].GetH());

		x = m_ptAvatar[1].x;
		y = m_ptAvatar[1].y - m_UserInfoView[1].GetH();

		m_UserInfoView[1].layout(x, y, x + m_UserInfoView[1].GetW(), y + m_UserInfoView[1].GetH());

		x = width - m_UserInfoView[2].GetW();
		y = m_ptAvatar[2].y + m_UserHead[2].GetH() - 35;
		m_UserInfoView[2].layout(x, y, x + m_UserInfoView[2].GetW(), y + m_UserInfoView[2].GetH());

		m_BackCardView.layout(x - 50, y, x - 50 + m_BackCardView.GetW(), y + m_BackCardView.GetH());
		// 用户聊天
		x = m_ptAvatar[0].x;
		y = m_ptAvatar[0].y + m_UserHead[0].GetH();
		m_ChatMessageView[0].layout(x, y, x + m_ChatMessageView[0].GetW(), y + m_ChatMessageView[0].GetH());
		x = m_ptAvatar[1].x;
		y = m_ptAvatar[1].y - m_ChatMessageView[1].GetH();
		m_ChatMessageView[1].layout(x, y, x + m_ChatMessageView[1].GetW(), y + m_ChatMessageView[1].GetH());
		x = width - m_ChatMessageView[2].GetW();
		y = m_ptAvatar[2].y + m_UserHead[2].GetH();
		m_ChatMessageView[2].layout(x, y, x + m_ChatMessageView[2].GetW(), y + m_ChatMessageView[2].GetH());

		// 工具条
		m_ToolBar.layout(width / 2 - m_ToolBar.GetW() / 2, 0, width / 2 + m_ToolBar.GetW() / 2, m_ToolBar.GetH());

		// 自己手牌
		m_HandCardControl.layout(0, height - CardModule.getHeight(0) - 20, width, height);

		// 桌面出牌
		x = width / 4 - m_TableCard[0].GetW() / 2 + 10;
		y = 50;
		m_TableCard[0].layout(x, y, x + m_TableCard[0].GetW(), y + m_TableCard[0].GetH());

		x = width / 2 - m_TableCard[1].GetW() / 2;
		y = height - CardModule.getHeight(0) - 5 - m_TableCard[1].GetH();
		m_TableCard[1].layout(x, y, x + m_TableCard[1].GetW(), y + m_TableCard[1].GetH());
		x = width * 3 / 4 - m_TableCard[2].GetW() / 2 - 10;
		y = 50;
		m_TableCard[2].layout(x, y, x + m_TableCard[2].GetW(), y + m_TableCard[2].GetH());

		m_GameScoreView.layout(width / 2 - m_GameScoreView.GetW() / 2, height / 2 - m_GameScoreView.GetH() / 2, width / 2 + m_GameScoreView.GetW() / 2, height
				/ 2 + m_GameScoreView.GetH() / 2);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_TableScore[i].layout(m_ptReady[i].x, m_ptReady[i].y, 0, 0);
		}

		m_BombAnimation.layout(width / 2 - m_BombAnimation.GetW() / 2, height / 2 - m_BombAnimation.GetH() / 2, width / 2 + m_BombAnimation.GetW() / 2, height
				/ 2 + m_BombAnimation.GetH() / 2);

		m_RocketView.layout(0, 0, m_RocketView.GetW(), m_RocketView.GetH());
		m_PlaneView.layout(0, 0, m_PlaneView.GetW(), m_PlaneView.GetH());

		m_ptFaceAnimation[0].set(m_ptAvatar[0].x, m_ptAvatar[0].y);
		m_ptFaceAnimation[1].set(m_ptAvatar[1].x, m_ptAvatar[1].y - 5);
		m_ptFaceAnimation[2].set(m_ptAvatar[2].x + m_UserHead[2].GetW() - m_Land.GetW(), m_ptAvatar[2].y);
		for (int i = 0; i < 3; i++)
			m_CallPointView[i].layout(width / 2 - m_CallPointView[i].GetW() / 2, height / 2 - m_CallPointView[i].GetH(), width / 2 + m_CallPointView[i].GetW()
					/ 2, height / 2);

	}

	private void onRectifyControlM(int width, int height) {
		// 游戏按钮
		m_btStart.layout(width / 2 - m_btStart.getW() / 2, height / 2, 0, 0);

		int btw = m_btOutCard.getW() + 10;

		m_btOutCard.layout(width - 25 - btw, height / 2, 0, 0);
		m_btReSelect.layout(width - 25 - btw * 2, height / 2, 0, 0);
		m_btSkip.layout(width - 25 - btw * 3, height / 2, 0, 0);
		m_btPass.layout(width - 25 - btw * 4, height / 2, 0, 0);

		btw = m_btScore[0].getW() + 10;
		for (int i = m_btScore.length - 1; i >= 0; i--) {
			m_btScore[i].layout(width - 25 - btw * (i + 1), height / 2, 0, 0);
		}

		m_ptAvatar[0].set(0, 0);
		m_ptAvatar[1].set(0, height - CardModule.getHeight(0) - 35 - m_UserHead[1].GetH());
		m_ptAvatar[2].set(width - m_UserHead[2].GetW(), 0);

		m_ptReady[0].set(width / 4, 110);
		m_ptReady[1].set(width / 2 - GetReadyW() / 2, height / 2);
		m_ptReady[2].set(width * 3 / 4, 110);

		m_ptClock[0].set(width / 4, 120);

		m_ptClock[1].set(width / 2, m_btStart.getTop() - GetClockH() / 2);

		m_ptClock[2].set(width * 3 / 4, 120);

		m_ptCardCount[0].set(m_ptAvatar[0].x, m_ptAvatar[0].y + m_UserHead[0].GetH() + 10);
		m_ptCardCount[1].set(m_ptAvatar[1].x + 80, m_ptAvatar[1].y + m_UserHead[1].GetH() / 2 + 5);
		m_ptCardCount[2].set(m_ptAvatar[2].x + 30, m_ptAvatar[2].y + m_UserHead[0].GetH() + 10);

		m_ptSrc.set(width / 2 - CardModule.getWidth(1) / 2, 90);
		m_ptDest[0].set(0, 90);
		m_ptDest[1].set(width / 2 - CardModule.getWidth(1) / 2, height - CardModule.getHeight(0) - CardModule.getHeight(1));
		m_ptDest[2].set(width - CardModule.getWidth(1), 90);

		int x = 0, y = 0;

		// 设置窗口
		m_GameOption.layout(width / 2 - m_GameOption.GetW() / 2, height / 2 - m_GameOption.GetH() / 2, width / 2 + m_GameOption.GetW() / 2, height / 2
				+ m_GameOption.GetH() / 2);
		// 聊天窗口
		m_GameChat.layout(width / 2 - m_GameChat.GetW() / 2, height / 2 - m_GameChat.GetH() / 2, width / 2 + m_GameChat.GetW() / 2,
				height / 2 + m_GameChat.GetH() / 2);

		// 用户头像
		for (int i = 0; i < m_UserHead.length; i++) {
			m_UserHead[i].layout(m_ptAvatar[i].x, m_ptAvatar[i].y, m_ptAvatar[i].x + m_UserHead[i].GetW(), m_ptAvatar[i].y + m_UserHead[i].GetH());
		}

		// 用户信息
		x = m_ptAvatar[0].x;
		y = m_ptAvatar[0].y + m_UserHead[0].GetH() - 35;
		m_UserInfoView[0].layout(x, y, x + m_UserInfoView[0].GetW(), y + m_UserInfoView[0].GetH());

		x = m_ptAvatar[1].x;
		y = m_ptAvatar[1].y - m_UserInfoView[1].GetH();

		m_UserInfoView[1].layout(x, y, x + m_UserInfoView[1].GetW(), y + m_UserInfoView[1].GetH());

		x = width - m_UserInfoView[2].GetW();
		y = m_ptAvatar[2].y + m_UserHead[2].GetH() - 35;
		m_UserInfoView[2].layout(x, y, x + m_UserInfoView[2].GetW(), y + m_UserInfoView[2].GetH());

		// 用户聊天
		x = m_ptAvatar[0].x;
		y = m_ptAvatar[0].y + m_UserHead[0].GetH();
		m_ChatMessageView[0].layout(x, y, x + m_ChatMessageView[0].GetW(), y + m_ChatMessageView[0].GetH());
		x = m_ptAvatar[1].x;
		y = m_ptAvatar[1].y - m_ChatMessageView[1].GetH();
		m_ChatMessageView[1].layout(x, y, x + m_ChatMessageView[1].GetW(), y + m_ChatMessageView[1].GetH());
		x = width - m_ChatMessageView[2].GetW();
		y = m_ptAvatar[2].y + m_UserHead[2].GetH();
		m_ChatMessageView[2].layout(x, y, x + m_ChatMessageView[2].GetW(), y + m_ChatMessageView[2].GetH());

		// 工具条
		m_ToolBar.layout(width / 2 - m_ToolBar.GetW() / 2, 0, width / 2 + m_ToolBar.GetW() / 2, m_ToolBar.GetH());

		// 自己手牌
		m_HandCardControl.layout(0, height - CardModule.getHeight(0) - 30, width, height);

		// 桌面出牌
		x = width / 4 - m_TableCard[0].GetW() / 2;
		y = 65;
		m_TableCard[0].layout(x, y, x + m_TableCard[0].GetW(), y + m_TableCard[0].GetH());

		x = width / 2 - m_TableCard[1].GetW() / 2;
		y = height - CardModule.getHeight(0) - 5 - m_TableCard[1].GetH();
		m_TableCard[1].layout(x, y, x + m_TableCard[1].GetW(), y + m_TableCard[1].GetH());
		x = width * 3 / 4 - m_TableCard[2].GetW() / 2;
		y = 65;
		m_TableCard[2].layout(x, y, x + m_TableCard[2].GetW(), y + m_TableCard[2].GetH());

		m_GameScoreView.layout(width / 2 - m_GameScoreView.GetW() / 2, height / 2 - m_GameScoreView.GetH() / 2, width / 2 + m_GameScoreView.GetW() / 2, height
				/ 2 + m_GameScoreView.GetH() / 2);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_TableScore[i].layout(m_ptReady[i].x, m_ptReady[i].y, 0, 0);
		}

		m_BombAnimation.layout(width / 2 - m_BombAnimation.GetW() / 2, height / 2 - m_BombAnimation.GetH() / 2, width / 2 + m_BombAnimation.GetW() / 2, height
				/ 2 + m_BombAnimation.GetH() / 2);

		m_RocketView.layout(0, 0, m_RocketView.GetW(), m_RocketView.GetH());
		m_PlaneView.layout(0, 0, m_PlaneView.GetW(), m_PlaneView.GetH());

		m_ptFaceAnimation[0].set(m_ptAvatar[0].x, m_ptAvatar[0].y);
		m_ptFaceAnimation[1].set(m_ptAvatar[1].x, m_ptAvatar[1].y - 5);
		m_ptFaceAnimation[2].set(m_ptAvatar[2].x + m_UserHead[2].GetW() - m_Land.GetW(), m_ptAvatar[2].y);

		for (int i = 0; i < 3; i++)
			m_CallPointView[i].layout(width / 2 - m_CallPointView[i].GetW() / 2, height / 2 - m_CallPointView[i].GetH(), width / 2 + m_CallPointView[i].GetW()
					/ 2, height / 2);
	}

	private void onRectifyControlH(int width, int height) {
		// 游戏按钮
		m_btStart.layout(width / 2 - m_btStart.getW() / 2, height / 2, 0, 0);

		int btw = m_btOutCard.getW() + 10;

		m_btOutCard.layout(width - 25 - btw, height / 2, 0, 0);
		m_btReSelect.layout(width - 25 - btw * 2, height / 2, 0, 0);
		m_btSkip.layout(width - 25 - btw * 3, height / 2, 0, 0);
		m_btPass.layout(width - 25 - btw * 4, height / 2, 0, 0);

		btw = m_btScore[0].getW() + 10;
		for (int i = m_btScore.length - 1; i >= 0; i--) {
			m_btScore[i].layout(width - 25 - btw * (i + 1), height / 2, 0, 0);
		}

		m_ptAvatar[0].set(0, 0);
		m_ptAvatar[1].set(0, height - CardModule.getHeight(0) - 35 - m_UserHead[1].GetH());
		m_ptAvatar[2].set(width - m_UserHead[2].GetW(), 0);

		m_ptReady[0].set(width / 4, 110);
		m_ptReady[1].set(width / 2 - GetReadyW() / 2, height / 2);
		m_ptReady[2].set(width * 3 / 4, 110);

		m_ptClock[0].set(width / 4, 120);

		m_ptClock[1].set(width / 2, m_btStart.getTop() - GetClockH() / 2);

		m_ptClock[2].set(width * 3 / 4, 120);

		m_ptCardCount[0].set(m_ptAvatar[0].x, m_ptAvatar[0].y + m_UserHead[0].GetH());
		m_ptCardCount[1].set(m_ptAvatar[1].x + 80, m_ptAvatar[1].y + m_UserHead[1].GetH() / 2 + 5);
		m_ptCardCount[2].set(m_ptAvatar[2].x + 30, m_ptAvatar[2].y + m_UserHead[0].GetH());

		m_ptSrc.set(width / 2 - CardModule.getWidth(1) / 2, 120);
		m_ptDest[0].set(0, 120);
		m_ptDest[1].set(width / 2 - CardModule.getWidth(1) / 2, height - CardModule.getHeight(0) - CardModule.getHeight(1));
		m_ptDest[2].set(width - CardModule.getWidth(1), 120);

		int x = 0, y = 0;

		// 设置窗口
		m_GameOption.layout(width / 2 - m_GameOption.GetW() / 2, height / 2 - m_GameOption.GetH() / 2, width / 2 + m_GameOption.GetW() / 2, height / 2
				+ m_GameOption.GetH() / 2);
		// 聊天窗口
		m_GameChat.layout(width / 2 - m_GameChat.GetW() / 2, height / 2 - m_GameChat.GetH() / 2, width / 2 + m_GameChat.GetW() / 2,
				height / 2 + m_GameChat.GetH() / 2);

		// 用户头像
		for (int i = 0; i < m_UserHead.length; i++) {
			m_UserHead[i].layout(m_ptAvatar[i].x, m_ptAvatar[i].y, m_ptAvatar[i].x + m_UserHead[i].GetW(), m_ptAvatar[i].y + m_UserHead[i].GetH());
		}

		// 用户信息
		x = m_ptAvatar[0].x;
		y = m_ptAvatar[0].y + m_UserHead[0].GetH() - 35;
		m_UserInfoView[0].layout(x, y, x + m_UserInfoView[0].GetW(), y + m_UserInfoView[0].GetH());

		x = m_ptAvatar[1].x;
		y = m_ptAvatar[1].y - m_UserInfoView[1].GetH();

		m_UserInfoView[1].layout(x, y, x + m_UserInfoView[1].GetW(), y + m_UserInfoView[1].GetH());

		x = width - m_UserInfoView[2].GetW();
		y = m_ptAvatar[2].y + m_UserHead[2].GetH() - 35;
		m_UserInfoView[2].layout(x, y, x + m_UserInfoView[2].GetW(), y + m_UserInfoView[2].GetH());

		// 用户聊天
		x = m_ptAvatar[0].x;
		y = m_ptAvatar[0].y + m_UserHead[0].GetH();
		m_ChatMessageView[0].layout(x, y, x + m_ChatMessageView[0].GetW(), y + m_ChatMessageView[0].GetH());
		x = m_ptAvatar[1].x;
		y = m_ptAvatar[1].y - m_ChatMessageView[1].GetH();
		m_ChatMessageView[1].layout(x, y, x + m_ChatMessageView[1].GetW(), y + m_ChatMessageView[1].GetH());
		x = width - m_ChatMessageView[2].GetW();
		y = m_ptAvatar[2].y + m_UserHead[2].GetH();
		m_ChatMessageView[2].layout(x, y, x + m_ChatMessageView[2].GetW(), y + m_ChatMessageView[2].GetH());

		// 工具条
		m_ToolBar.layout(width / 2 - m_ToolBar.GetW() / 2, 0, width / 2 + m_ToolBar.GetW() / 2, m_ToolBar.GetH());

		// 自己手牌
		m_HandCardControl.layout(0, height - CardModule.getHeight(0) - 30, width, height);

		// 桌面出牌
		x = width / 4 - m_TableCard[0].GetW() / 2;
		y = 110;
		m_TableCard[0].layout(x, y, x + m_TableCard[0].GetW(), y + m_TableCard[0].GetH());
		x = width / 2 - m_TableCard[1].GetW() / 2;
		y = height - CardModule.getHeight(0) - 35 - m_TableCard[1].GetH();
		m_TableCard[1].layout(x, y, x + m_TableCard[1].GetW(), y + m_TableCard[1].GetH());
		x = width * 3 / 4 - m_TableCard[2].GetW() / 2;
		y = 110;
		m_TableCard[2].layout(x, y, x + m_TableCard[2].GetW(), y + m_TableCard[2].GetH());

		m_GameScoreView.layout(width / 2 - m_GameScoreView.GetW() / 2, height / 2 - m_GameScoreView.GetH() / 2, width / 2 + m_GameScoreView.GetW() / 2, height
				/ 2 + m_GameScoreView.GetH() / 2);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_TableScore[i].layout(m_ptReady[i].x, m_ptReady[i].y, 0, 0);
		}

		m_BombAnimation.layout(width / 2 - m_BombAnimation.GetW() / 2, height / 2 - m_BombAnimation.GetH() / 2, width / 2 + m_BombAnimation.GetW() / 2, height
				/ 2 + m_BombAnimation.GetH() / 2);

		m_RocketView.layout(0, 0, m_RocketView.GetW(), m_RocketView.GetH());
		m_PlaneView.layout(0, 0, m_PlaneView.GetW(), m_PlaneView.GetH());

		m_ptFaceAnimation[0].set(m_ptAvatar[0].x, m_ptAvatar[0].y);
		m_ptFaceAnimation[1].set(m_ptAvatar[1].x, m_ptAvatar[1].y - 5);
		m_ptFaceAnimation[2].set(m_ptAvatar[2].x + m_UserHead[2].GetW() - m_Land.GetW(), m_ptAvatar[2].y);

		for (int i = 0; i < 3; i++)
			m_CallPointView[i].layout(width / 2 - m_CallPointView[i].GetW() / 2, height / 2 - m_CallPointView[i].GetH(), width / 2 + m_CallPointView[i].GetW()
					/ 2, height / 2);
	}

	@Override
	public void ResetGameView() {

		OnChangeFaceAnimation(GDF.INVALID_CHAIR);

		m_GameOption.setVisibility(INVISIBLE);
		m_GameChat.setVisibility(INVISIBLE);
		m_GameScoreView.setVisibility(INVISIBLE);
		// CCard_Big.OnReleaseCard();
		// CCard_Small.OnReleaseCard();
		// CCard_Middle.OnReleaseCard();
		StopAnimation();
		m_BombAnimation.Stop(true);
		for (int i = 0; i < m_CallPointView.length; i++)
			m_CallPointView[i].Stop(true);

		m_RocketView.setVisibility(INVISIBLE);
		m_PlaneView.setVisibility(INVISIBLE);

		m_ToolBar.SetBackData(null, false);
		m_ToolBar.m_nPoint = 0;
		m_ToolBar.postInvalidate();

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_UserHead[i].SetLandMode(0);
			m_UserHead[i].SetTrustee(false);
			m_UserInfoView[i].setVisibility(INVISIBLE);
			m_ChatMessageView[i].setVisibility(INVISIBLE);
			m_TableCard[i].SetCardData(null, 0);
			m_TableScore[i].point = 0;
		}
		for (int i = 0; i < m_btScore.length; i++) {
			m_btScore[i].setVisibility(INVISIBLE);
		}

		m_btStart.setVisibility(INVISIBLE);
		m_btOutCard.setVisibility(INVISIBLE);
		m_btSkip.setVisibility(INVISIBLE);
		m_btPass.setVisibility(INVISIBLE);
		m_btReSelect.setVisibility(INVISIBLE);

		m_HandCardControl.SetCardData(null, 0);
		m_HandCardControl.setClickable(false);
		m_HandCardControl.postInvalidate();

		m_bWaitCallPoint = false;
		m_bAnimation = false;
		m_bPassState = new boolean[GDF.GAME_PLAYER];
		m_nHandCardCount = new int[GDF.GAME_PLAYER];
		m_bTrustee = new boolean[GDF.GAME_PLAYER];
		if (m_BackCardView != null) {
			m_BackCardView.setVisibility(INVISIBLE);
			m_BackCardView.SetBackData(null);
		}
	}

	private void StartShuffleCard() {
		m_bAnimation = true;
		// m_ShuffleAnimation.setVisibility(VISIBLE);
		// m_ShuffleAnimation.StartAnimation();
		GameFramEngine.PlayGameSound(GDF.SOUND_GAME_SHULLE);
	}

	private void StartDispathCard() {
		if (!m_bAnimation)
			return;
		// m_ShuffleAnimation.setVisibility(INVISIBLE);
		m_MoveStep = 0;
		int startx = (ClientPlazz.SCREEN_WIDHT - 50 * m_MoveCardSpaceX - CardModule.getWidth(1)) / 2;
		int user = (m_nStartUser - 1 + GDF.GAME_PLAYER) % GDF.GAME_PLAYER;
		for (int i = 0; i < 51; i++) {
			m_Card[i].setVisibility(VISIBLE);
			m_Card[i].user = user;
			addView(m_Card[i]);
			Animation ani = new TranslateAnimation(m_ptSrc.x, startx + i * m_MoveCardSpaceX, m_ptSrc.y, m_ptSrc.y);
			ani.setAnimationListener(m_Card[i]);
			ani.setDuration(300);
			m_Card[i].startAnimation(ani);
			user = (user - 1 + GDF.GAME_PLAYER) % GDF.GAME_PLAYER;
		}
	}

	private void DispathCard(int user) {
		if (!m_bAnimation)
			return;
		if (user > -1 && user < GDF.GAME_PLAYER) {
			GameFramEngine.PlayGameSound(GDF.SOUND_GAME_SEND);
			if (m_MoveStep < 50) {
				m_MoveStep++;
			} else if (m_MoveStep == 50) {
				m_MoveStep = 51;
				int startx = (ClientPlazz.SCREEN_WIDHT - 50 * m_MoveCardSpaceX - CardModule.getWidth(1)) / 2;
				for (int i = 50; i >= 0; i--) {
					Animation ani = new TranslateAnimation(startx + i * m_MoveCardSpaceX, m_ptDest[m_Card[i].user].x, m_ptSrc.y, m_ptDest[m_Card[i].user].y);
					ani.setStartOffset((50 - i) * 20);
					ani.setAnimationListener(m_Card[i]);
					ani.setDuration(350);
					m_Card[i].startAnimation(ani);
				}
			} else {
				if (m_nHandCardCount[user] < GDF.NORMAL_COUNT) {
					m_nHandCardCount[user]++;
				}
				if (user == 1) {
					int show = m_HandCardControl.GetShowCount() + 1;
					if (show <= GDF.NORMAL_COUNT) {
						m_HandCardControl.SetShowCount(show);
						m_HandCardControl.RectifyControl();
						m_HandCardControl.invalidate();
					}
				}
				if (m_nHandCardCount[0] + m_nHandCardCount[1] + m_nHandCardCount[2] >= 51) {
					DispathCardEnd();
				}
			}
		}
	}

	private void DispathCardEnd() {
		if (m_bAnimation) {
			GDF.SendSubMessage(IDM_DISPATH_END, 0, GetTag(), null);
		}
	}

	public void StopAnimation() {
		for (int i = 0; i < 51; i++) {
			m_Card[i].setVisibility(INVISIBLE);
			removeView(m_Card[i]);
		}
	}

	public void SetStartUser(int user) {
		m_nStartUser = user;
	}

	private void StartRocket() {
		if (m_RocketView.getVisibility() == VISIBLE)
			return;
		Animation RocketAnimation = new TranslateAnimation(ClientPlazz.SCREEN_WIDHT / 2 - m_RocketView.GetW() / 2, ClientPlazz.SCREEN_WIDHT / 2
				- m_RocketView.GetW() / 2, ClientPlazz.SCREEN_HEIGHT, -m_RocketView.GetH());
		RocketAnimation.setAnimationListener(m_RocketView);
		RocketAnimation.setDuration(800);
		m_RocketView.setVisibility(VISIBLE);
		m_RocketView.startAnimation(RocketAnimation);

	}

	private void StartPlane() {
		if (m_PlaneView.getVisibility() == VISIBLE)
			return;
		m_PlaneView.setVisibility(VISIBLE);
		Animation PlaneAnimation = new TranslateAnimation(ClientPlazz.SCREEN_WIDHT, -m_PlaneView.GetW(), 120, 120);
		PlaneAnimation.setAnimationListener(m_PlaneView);
		PlaneAnimation.setDuration(800);
		m_PlaneView.startAnimation(PlaneAnimation);
		GameFramEngine.PlayGameSound(GDF.SOUND_GAME_PLANE);
	}

	private void StartBomb() {
		m_BombAnimation.Start(150, false);
	}

	public void OnEventUserStatus(int chair, IClientUserItem item) {
		if (chair >= 0 && chair < m_UserHead.length) {
			m_UserHead[chair].OnEventUserStatus(item);
		}
	}

	public void OnShowLandCard(int reason) {
		if (CActivity.nDeviceType == DF.DEVICETYPE_QVGA) {
			if (m_BackCardView != null) {
				m_BackCardView.OnShowLandCard(reason);
				if (m_BackCardView.getVisibility() == VISIBLE)
					GDF.SendMainMessageDelayed(IDM_HIDE_BACKCARD, 0, GetTag(), null, 4000);
				else
					GDF.RemoveMainMessages(IDM_HIDE_BACKCARD);
			}
		}
	}

}
