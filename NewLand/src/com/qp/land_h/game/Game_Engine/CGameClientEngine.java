package com.qp.land_h.game.Game_Engine;

import java.util.Random;

import Lib_Interface.ICallBack;
import Lib_Interface.UserInterface.IClientUserItem;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qp.new_land.R;
import com.qp.land_h.game.Game_Cmd.CMD_C_CallScore;
import com.qp.land_h.game.Game_Cmd.CMD_C_OutCard;
import com.qp.land_h.game.Game_Cmd.CMD_S_BankerInfo;
import com.qp.land_h.game.Game_Cmd.CMD_S_CallScore;
import com.qp.land_h.game.Game_Cmd.CMD_S_GameConClude;
import com.qp.land_h.game.Game_Cmd.CMD_S_GameStart;
import com.qp.land_h.game.Game_Cmd.CMD_S_OutCard;
import com.qp.land_h.game.Game_Cmd.CMD_S_PassCard;
import com.qp.land_h.game.Game_Cmd.CMD_S_StatusCall;
import com.qp.land_h.game.Game_Cmd.CMD_S_StatusFree;
import com.qp.land_h.game.Game_Cmd.CMD_S_StatusPlay;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Cmd.tagScoreInfo;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Fram.Game.CGameFramEngine;

public class CGameClientEngine extends CGameFramEngine {

	public final static int		IDI_CALL_POINT	= 300;
	public final static int		IDI_OUT_CARD	= 301;
	public final static int		IDI_GAME_START	= 299;

	public final static int		TIME_START		= 30;
	public final static int		TIME_CALL_POINT	= 30;
	public final static int		TIME_OUT_CARD	= 30;

	/** 牌记录 **/
	public int					m_HandCardData[];		// 手牌 数据
	public int					m_HandCardCount[];		// 手牌数目

	/** 辅助变量 **/
	public int					m_nTrusteeCount;		// 超时次数
	public boolean				m_bTrustee;			// 是否托管

	/** 游戏变量 **/
	public int					m_nBombCount;
	public int					m_nBankerUser;			// 地主玩家
	public int					m_nBankerScore;		// 地主分数
	public int					m_nCurrentUser;		// 当前玩家

	public int					m_nTurnCardCount;		// 上轮牌数目
	public int					m_nTurnCardData[];		// 上轮牌数据

	public int					m_nSortCardType;

	/** 逻辑控件 **/
	public CGameLogic			GameLogic;
	/** 显示控件 **/
	protected CGameClientView	m_GameClientView;

	protected Random			rand;

	protected int				SkipCardData[];
	protected int				SkipCount;

	protected int				AnalyseData[];
	protected int				AnalyseCount;

	public CGameClientEngine(CGameClientView gameClientView) {
		super();
		m_GameClientView = gameClientView;
		GameLogic = new CGameLogic();
		rand = new Random(System.currentTimeMillis());
		m_nSortCardType = CGameLogic.ST_ORDER;
		OnInitGameEngine();
	}

	@Override
	public boolean OnInitGameEngine() {
		LoadGameSound(R.raw.game_alert, GDF.SOUND_GAME_ALERT);
		LoadGameSound(R.raw.game_bankerinfo, GDF.SOUND_GAME_BANKERINFO);
		LoadGameSound(R.raw.game_bomb, GDF.SOUND_GAME_BOMB);
		LoadGameSound(R.raw.game_end, GDF.SOUND_GAME_END);
		LoadGameSound(R.raw.game_plane, GDF.SOUND_GAME_PLANE);
		LoadGameSound(R.raw.game_sendcard, GDF.SOUND_GAME_SEND);
		LoadGameSound(R.raw.game_shuffle, GDF.SOUND_GAME_SHULLE);
		LoadGameSound(R.raw.game_start, GDF.SOUND_GAME_START);
		LoadGameSound(R.raw.game_warn, GDF.SOUND_GAME_WARN);

		for (int i = 0; i < 4; i++) {
			LoadGameSound(R.raw.game_m_cs0 + i, GDF.SOUND_CS_M_0 + i * 2);
			LoadGameSound(R.raw.game_w_cs0 + i, GDF.SOUND_CS_W_0 + i * 2);
		}

		for (int i = 0; i < 2; i++) {
			LoadGameSound(R.raw.game_m_pass0 + i, GDF.SOUND_PASS_M_0 + i * 2);
			LoadGameSound(R.raw.game_w_pass0 + i, GDF.SOUND_PASS_W_0 + i * 2);
			LoadGameSound(R.raw.game_m_ya0 + i, GDF.SOUND_YA_M_0 + i * 2);
			LoadGameSound(R.raw.game_w_ya0 + i, GDF.SOUND_YA_W_0 + i * 2);
		}

		for (int i = 0; i < 9; i++) {
			LoadGameSound(R.raw.game_m_type_1 + i, GDF.SOUND_TYPE_M_DUI + i * 2);
			LoadGameSound(R.raw.game_w_type_1 + i, GDF.SOUND_TYPE_W_DUI + i * 2);
		}

		for (int i = 0; i < 15; i++) {
			LoadGameSound(R.raw.card_m_01 + i, GDF.SOUND_CARD_M_1 + i * 2);
			LoadGameSound(R.raw.card_w_01 + i, GDF.SOUND_CARD_W_1 + i * 2);
		}

		OnResetGameEngine();
		return true;
	}

	@Override
	public boolean OnResetGameEngine() {
		KillGameClock(IDI_CALL_POINT);
		KillGameClock(IDI_GAME_START);
		KillGameClock(IDI_OUT_CARD);
		m_HandCardData = new int[GDF.MAX_CARDCOUNT];
		m_HandCardCount = new int[GDF.GAME_PLAYER];

		m_nTrusteeCount = 0;
		m_bTrustee = false;

		m_nBombCount = 0;
		m_nBankerUser = GDF.INVALID_CHAIR;
		m_nBankerScore = 0;
		m_nCurrentUser = GDF.INVALID_CHAIR;

		m_nTurnCardCount = 0;
		m_nTurnCardData = new int[GDF.MAX_CARDCOUNT];

		SkipCardData = new int[GDF.MAX_CARDCOUNT];
		SkipCount = 0;

		AnalyseCount = 0;
		AnalyseData = new int[GDF.MAX_CARDCOUNT];

		return true;
	}

	@Override
	public boolean OnEventGameClockInfo(int nChairID, int clockid, int time) {

		m_GameClientView.postInvalidteClock(SwitchViewChairID(nChairID));
		if (time < 5)
			PlayGameSound(GDF.SOUND_GAME_WARN);
		if (!IsLookonMode()) {
			switch (clockid) {
			// 叫分
				case IDI_CALL_POINT :
					if ((m_bTrustee || time == 0) && nChairID == GetMeChairID()) {
						ICallBack callback = new ICallBack() {

							@Override
							public boolean OnCallBackDispath(boolean arg0, Object arg1) {
								for (int j = 0; j < 4; j++) {
									m_GameClientView.m_btScore[j].setVisibility(View.INVISIBLE);
								}
								return true;
							}
						};
						GDF.SendMainMessage(CGameClientView.IDM_CALLBACK, 0, m_GameClientView.GetTag(), callback);
						OnCallScore(255);
					}
					return true;
					// 出牌
				case IDI_OUT_CARD :
					if ((m_bTrustee || time == 0) && nChairID == GetMeChairID()) {
						OnSkipCard(true);
					}
					return true;
					// 开始
				case IDI_GAME_START :
					if ((m_bTrustee || time == 0) && nChairID == GetMeChairID()) {
						KillGameClock(IDI_GAME_START);
						ExitToRoom();
					}
					return true;
			}
		}
		return false;
	}

	private void OnCallScore(int score) {
		if (m_nCurrentUser != GetMeChairID())
			return;
		m_nCurrentUser = GDF.INVALID_CHAIR;
		KillGameClock(IDI_CALL_POINT);
		PlayActionSound(CGameClientView.IDM_CALL_POINT, score, GetMeChairID());

		CMD_C_CallScore cmd = new CMD_C_CallScore();
		cmd.nCallScore = (score == 0 ? 255 : score);
		SendSocketData(GDF.SUB_C_CALL_SCORE, cmd);
		m_GameClientView.SetUserCallScore(1, score);
		m_GameClientView.postInvalidate();
	}

	private synchronized void OnSkipCard(boolean bout) {

		int nMeChairID = GetMeChairID();
		if (m_nCurrentUser != nMeChairID)
			return;
		if (bout == false) {
			if (OnSkipSkipCard())
				return;
		}
		OnSkipCardNormal(bout);
	}

	private boolean OnSkipSkipCard() {
		if (SkipCount != 0) {
			int outcard[] = new int[GDF.MAX_CARDCOUNT];
			int ncount = GameLogic.SeachOutCard(AnalyseData, AnalyseCount, SkipCardData, SkipCount, outcard);
			if (ncount > 0) {
				SkipCardData = new int[GDF.MAX_CARDCOUNT];
				SkipCount = ncount;
				System.arraycopy(outcard, 0, SkipCardData, 0, SkipCount);
				m_GameClientView.m_HandCardControl.SetAllCardShoot(false);
				m_GameClientView.m_HandCardControl.SetCardShoot(outcard, ncount);
				m_GameClientView.m_HandCardControl.postInvalidate();
				return true;
			}
		}
		return false;
	}

	private void OnSkipCardNormal(boolean bout) {
		int outcard[] = new int[GDF.MAX_CARDCOUNT];
		int ncount = GameLogic.SeachOutCard(AnalyseData, AnalyseCount, m_nTurnCardData, m_nTurnCardCount, outcard);
		if (ncount > 0) {
			m_GameClientView.m_HandCardControl.SetAllCardShoot(false);
			m_GameClientView.m_HandCardControl.SetCardShoot(outcard, ncount);
			m_GameClientView.m_HandCardControl.postInvalidate();
			if (bout) {
				OnOutCard();
			} else {
				SkipCardData = new int[GDF.MAX_CARDCOUNT];
				SkipCount = ncount;
				System.arraycopy(outcard, 0, SkipCardData, 0, SkipCount);
			}
		} else {
			OnPassCard();
		}
		m_GameClientView.postInvalidate();
	}

	private void OnPassCard() {
		KillGameClock(IDI_OUT_CARD);
		if (m_nCurrentUser != GetMeChairID())
			return;
		PlayActionSound(CGameClientView.IDM_GAME_PASS, 0, GetMeChairID());
		m_GameClientView.m_HandCardControl.SetAllCardShoot(false);
		m_GameClientView.m_TableCard[1].SetCardData(null, 0);
		m_GameClientView.SetUserPassState(1, true);
		ICallBack callback = new ICallBack() {

			@Override
			public boolean OnCallBackDispath(boolean arg0, Object arg1) {

				m_GameClientView.m_btOutCard.setVisibility(View.INVISIBLE);
				m_GameClientView.m_btPass.setVisibility(View.INVISIBLE);
				m_GameClientView.m_btSkip.setVisibility(View.INVISIBLE);
				m_GameClientView.m_btReSelect.setVisibility(View.INVISIBLE);
				return true;
			}
		};
		GDF.SendMainMessage(CGameClientView.IDM_CALLBACK, 0, m_GameClientView.GetTag(), callback);

		SkipCardData = new int[GDF.MAX_CARDCOUNT];
		SkipCount = 0;

		SendSocketData(GDF.SUB_C_PASS_CARD, null);

		m_GameClientView.postInvalidate();
	}

	private synchronized void OnOutCard() {

		int nMeChairID = GetMeChairID();
		if (m_nCurrentUser != nMeChairID)
			return;
		// 获取弹起牌
		int nOutCard[] = new int[GDF.MAX_CARDCOUNT];
		int nCount = m_GameClientView.m_HandCardControl.GetShootCard(nOutCard, true);

		if (nCount > 0) {
			// 整理
			if (nCount > 1) {
				GameLogic.SortCardList(nOutCard, nCount, CGameLogic.ST_ORDER);
			}
			// 获取牌型
			int nType = GameLogic.GetCardType(nOutCard, nCount);
			if (nType == CGameLogic.CT_ERROR) {
				Toast.makeText(GDF.GetContext(), "请选择正确牌型", Toast.LENGTH_SHORT).show();
				return;
			}

			// 判断出牌
			if (m_nTurnCardCount == 0 || (m_nTurnCardCount > 0 && GameLogic.CompareCard(m_nTurnCardData, nOutCard, m_nTurnCardCount, nCount))) {
				KillGameClock(IDI_OUT_CARD);
				// 按钮隐藏
				ICallBack callback = new ICallBack() {

					@Override
					public boolean OnCallBackDispath(boolean arg0, Object arg1) {

						m_GameClientView.m_btOutCard.setVisibility(View.INVISIBLE);
						m_GameClientView.m_btPass.setVisibility(View.INVISIBLE);
						m_GameClientView.m_btSkip.setVisibility(View.INVISIBLE);
						m_GameClientView.m_btReSelect.setVisibility(View.INVISIBLE);
						return true;
					}
				};
				GDF.SendMainMessage(CGameClientView.IDM_CALLBACK, 0, m_GameClientView.GetTag(), callback);

				// 发送消息
				CMD_C_OutCard cmd = new CMD_C_OutCard();
				cmd.nCardCount = nCount;
				for (int i = 0; i < nCount; i++) {
					cmd.nCardData[i] = nOutCard[i];
				}
				SendSocketData(GDF.SUB_C_OUT_CARD, cmd);
				// 整理手牌
				int nNewData[] = new int[GDF.MAX_CARDCOUNT];
				int nIndex = 0;
				for (int i = 0; i < m_HandCardCount[nMeChairID]; i++) {
					for (int j = 0; j < nCount; j++) {
						if (m_HandCardData[i] == nOutCard[j]) {
							m_HandCardData[i] = 0;
						}
					}
					if (m_HandCardData[i] != 0) {
						nNewData[nIndex++] = m_HandCardData[i];
					}
				}
				m_HandCardCount[GetMeChairID()] = nIndex;
				m_HandCardData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(nNewData, 0, m_HandCardData, 0, nIndex);
				GameLogic.SortCardList(m_HandCardData, nIndex, m_nSortCardType);
				m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, nIndex);
				m_GameClientView.SetCardCount(1, nIndex);
				m_GameClientView.SetUserPassState(1, false);
				m_GameClientView.m_TableCard[1].SetCardData(nOutCard, nCount);

				// 保存分析
				AnalyseData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[nMeChairID]);
				AnalyseCount = m_HandCardCount[nMeChairID];
				GameLogic.SortCardList(AnalyseData, AnalyseCount, CGameLogic.ST_ORDER);

				SkipCardData = new int[GDF.MAX_CARDCOUNT];
				SkipCount = 0;

				PlayCardTypeSound(nType, nOutCard[0], nCount, GetMeChairID(), m_nTurnCardCount == 0);
			}
		} else {
			Toast.makeText(GDF.GetContext(), "请选择正确牌型", Toast.LENGTH_SHORT).show();
			return;
		}
		m_GameClientView.postInvalidate();

	}

	private void ExitToRoom() {
		PerformStandupAction(false);
	}

	@Override
	public boolean OnEventGameMessage(int sub, byte[] data) {
		switch (sub) {
			case GDF.SUB_S_GAME_START :
				return OnSubGameStart(data);
			case GDF.SUB_S_GAME_CONCLUDE :
				return OnSubGameConclude(data);
			case GDF.SUB_S_CALL_SCORE :
				return OnSubCallScore(data);
			case GDF.SUB_S_BANLER_INFO :
				return OnSubBankerInfo(data);
			case GDF.SUB_S_OUT_CARD :
				return OnSubOutCard(data);
			case GDF.SUB_S_PASS_CARD :
				return OnSubPassCard(data);
		}
		return false;
	}

	private boolean OnSubPassCard(byte[] data) {
		CMD_S_PassCard cmd = new CMD_S_PassCard();
		cmd.ReadFromByteArray(data, 0);
		if (cmd.nPassCardUser != GetMeChairID() || IsLookonMode()) {
			KillGameClock(IDI_OUT_CARD);
			m_GameClientView.m_TableCard[SwitchViewChairID(cmd.nPassCardUser)].SetCardData(null, 0);
			m_GameClientView.SetUserPassState(SwitchViewChairID(cmd.nPassCardUser), true);
			PlayActionSound(CGameClientView.IDM_GAME_PASS, 0, cmd.nPassCardUser);
		}
		m_nCurrentUser = cmd.nCurrentUser;

		// 清理当前
		if (m_nCurrentUser != GDF.INVALID_CHAIR) {
			m_GameClientView.m_TableCard[SwitchViewChairID(m_nCurrentUser)].SetCardData(null, 0);
			m_GameClientView.SetUserPassState(SwitchViewChairID(m_nCurrentUser), false);
		}

		SetGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);

		if (cmd.nTurnOver == 1) {
			m_nTurnCardCount = 0;
			m_nTurnCardData = new int[GDF.MAX_CARDCOUNT];
		}
		int nMyTurn = 0;
		if (m_nCurrentUser == GetMeChairID() && !IsLookonMode()) {
			nMyTurn = 1;
		}
		GDF.SendMainMessage(CGameClientView.IDM_GAME_OUTCARD, m_nTurnCardCount, m_GameClientView.GetTag(), nMyTurn);

		m_GameClientView.SetCurrentUser(SwitchViewChairID(m_nCurrentUser));

		m_GameClientView.postInvalidate();
		return true;
	}

	private boolean OnSubOutCard(byte[] data) {
		CMD_S_OutCard cmd = new CMD_S_OutCard();
		cmd.ReadFromByteArray(data, 0);
		boolean nNewTurn = (m_nTurnCardCount == 0);
		m_nTurnCardCount = cmd.nCardCount;
		for (int i = 0; i < m_nTurnCardCount; i++) {
			m_nTurnCardData[i] = cmd.nCardData[i];
		}

		// 整理手牌
		if (GetMeChairID() == cmd.nOutCardUser && IsLookonMode()) {
			KillGameClock(IDI_OUT_CARD);
			int nNewData[] = new int[GDF.MAX_CARDCOUNT];
			int nIndex = 0;
			for (int i = 0; i < m_HandCardCount[GetMeChairID()]; i++) {
				for (int j = 0; j < m_nTurnCardCount; j++) {
					if (m_HandCardData[i] == m_nTurnCardData[j]) {
						m_HandCardData[i] = 0;
					}
				}
				if (m_HandCardData[i] != 0) {
					nNewData[nIndex++] = m_HandCardData[i];
				}
			}
			m_HandCardCount[GetMeChairID()] = nIndex;
			m_HandCardData = new int[GDF.MAX_CARDCOUNT];
			System.arraycopy(nNewData, 0, m_HandCardData, 0, nIndex);
			GameLogic.SortCardList(m_HandCardData, nIndex, m_nSortCardType);
			m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, nIndex);
			m_GameClientView.SetCardCount(1, nIndex);
			m_GameClientView.SetUserPassState(1, false);
		}
		// 整理手牌
		if (cmd.nOutCardUser != GetMeChairID()) {
			KillGameClock(IDI_OUT_CARD);
			m_HandCardCount[cmd.nOutCardUser] -= m_nTurnCardCount;
			m_GameClientView.SetCardCount(SwitchViewChairID(cmd.nOutCardUser), m_HandCardCount[cmd.nOutCardUser]);
			m_GameClientView.SetUserPassState(SwitchViewChairID(cmd.nOutCardUser), false);
			if (m_HandCardCount[cmd.nOutCardUser] < 3 && m_HandCardCount[cmd.nOutCardUser] > 0) {
				PlayGameSound(GDF.SOUND_GAME_ALERT);
			}
		}
		int nType = GDF.CT_ERROR;
		// 出牌界面
		if (cmd.nOutCardUser != GetMeChairID() || IsLookonMode()) {
			m_GameClientView.m_TableCard[SwitchViewChairID(cmd.nOutCardUser)].SetCardData(m_nTurnCardData, m_nTurnCardCount);

			nType = GameLogic.GetCardType(m_nTurnCardData, m_nTurnCardCount);
			if (nType == CGameLogic.CT_BOME_CARD || nType == CGameLogic.CT_MISSILE_CARD) {
				GDF.SendMainMessage(CGameClientView.IDM_BOMB_START, 0, m_GameClientView.GetTag(), null);
			}
			PlayCardTypeSound(nType, cmd.nCardData[0], m_nTurnCardCount, cmd.nOutCardUser, nNewTurn);
		}

		// 当前玩家
		m_nCurrentUser = cmd.nCurrentUser;

		// 当前玩家界面清理
		if (m_nCurrentUser != GDF.INVALID_ITEM) {
			m_GameClientView.SetUserPassState(SwitchViewChairID(m_nCurrentUser), false);
			m_GameClientView.m_TableCard[SwitchViewChairID(m_nCurrentUser)].SetCardData(null, 0);
			int nMyTurn = 0;
			if (m_nCurrentUser == GetMeChairID() && !IsLookonMode()) {
				nMyTurn = 1;
			}
			GDF.SendMainMessage(CGameClientView.IDM_GAME_OUTCARD, m_nTurnCardCount, m_GameClientView.GetTag(), nMyTurn);
			SetGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);
			if (cmd.nOutCardUser == cmd.nCurrentUser) {
				m_nTurnCardCount = 0;
				for (int i = 0; i < GDF.GAME_PLAYER; i++) {
					if (i != cmd.nCurrentUser) {
						m_GameClientView.SetUserPassState(SwitchViewChairID(i), true);
						m_GameClientView.m_TableCard[SwitchViewChairID(i)].SetCardData(null, 0);
					}
				}
			}
			m_GameClientView.SetCurrentUser(SwitchViewChairID(m_nCurrentUser));
		}

		m_GameClientView.postInvalidate();
		return true;
	}

	private boolean OnSubBankerInfo(byte[] data) {

		CMD_S_BankerInfo cmd = new CMD_S_BankerInfo();
		cmd.ReadFromByteArray(data, 0);
		SetGameStatus(GDF.GAME_SCENE_PLAY);
		OnFinishSendCard();

		int nMeChairID = GetMeChairID();

		KillGameClock(IDI_CALL_POINT);
		// 玩家设置
		m_nBankerScore = cmd.nBankerScore;
		m_nBankerUser = cmd.nBankerUser;
		m_nCurrentUser = cmd.nCurrentUser;

		// 底牌设置
		int nCardData[] = new int[3];
		for (int i = 0; i < 3; i++) {
			nCardData[i] = cmd.nBankerCard[i];
		}
		m_GameClientView.SetBackCard(nCardData, false);
		// 状态设置
		m_GameClientView.SetWaitCallScore(false);
		m_GameClientView.SetUserCallScore(GDF.INVALID_ITEM, 0);
		m_GameClientView.SetBankerScore(cmd.nBankerScore);
		m_GameClientView.SetBankerUser(SwitchViewChairID(m_nBankerUser));

		GDF.SendMainMessage(CGameClientView.IDM_BANKER_INFO, SwitchViewChairID(m_nBankerUser), m_GameClientView.GetTag(), null);

		m_HandCardCount[m_nBankerUser] += 3;
		m_GameClientView.SetCardCount(SwitchViewChairID(m_nBankerUser), 20);

		int nMyTurn = 0;
		if (m_nBankerUser == nMeChairID) {
			for (int i = 0; i < 3; i++) {
				m_HandCardData[GDF.NORMAL_COUNT + i] = nCardData[i];
			}
			GameLogic.SortCardList(m_HandCardData, m_HandCardCount[m_nBankerUser], m_nSortCardType);
			m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, m_HandCardCount[GetMeChairID()]);
			if (!IsLookonMode())
				nMyTurn = 1;
		}

		// 保存分析
		AnalyseData = new int[GDF.MAX_CARDCOUNT];
		System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[nMeChairID]);
		AnalyseCount = m_HandCardCount[nMeChairID];
		GameLogic.SortCardList(AnalyseData, m_HandCardCount[nMeChairID], CGameLogic.ST_ORDER);

		GDF.SendMainMessage(CGameClientView.IDM_GAME_OUTCARD, 0, m_GameClientView.GetTag(), nMyTurn);

		// 开启计时
		SetGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);
		m_GameClientView.postInvalidate();
		return true;
	}

	private boolean OnSubCallScore(byte[] data) {
		// 读取数据
		CMD_S_CallScore cmd = new CMD_S_CallScore();
		cmd.ReadFromByteArray(data, 0);

		OnFinishSendCard();

		// 清除时钟
		KillGameClock(IDI_CALL_POINT);

		// 当前玩家
		m_nCurrentUser = cmd.nCurrentUser;

		// 设置叫分
		if (cmd.nCallScoreUser != GetMeChairID() || IsLookonMode()) {
			m_GameClientView.SetUserCallScore(SwitchViewChairID(cmd.nCallScoreUser), cmd.nUserCallScore);
			PlayActionSound(CGameClientView.IDM_CALL_POINT, cmd.nUserCallScore, cmd.nCallScoreUser);
		}

		if (cmd.nCallScoreUser != GetMeChairID() || IsLookonMode())
			GDF.SendMainMessage(CGameClientView.IDM_CALL_ANIMATION, SwitchViewChairID(cmd.nCallScoreUser), m_GameClientView.GetTag(), cmd.nUserCallScore);
		// 到我叫分
		int nMyTurn = 0;
		if (m_nCurrentUser == GetMeChairID() && !IsLookonMode()) {
			nMyTurn = 1;
		}

		GDF.SendMainMessage(CGameClientView.IDM_CALL_POINT, cmd.nCurrentScore, m_GameClientView.GetTag(), nMyTurn);
		// 等待他人
		m_GameClientView.SetWaitCallScore(m_nCurrentUser != GetMeChairID());

		// 设置时钟
		if (m_nCurrentUser != GDF.INVALID_ITEM) {
			SetGameClock(m_nCurrentUser, IDI_CALL_POINT, TIME_CALL_POINT);
		}

		// 刷新屏幕
		m_GameClientView.postInvalidate();
		return true;
	}

	private boolean OnSubGameConclude(byte[] data) {
		if (ClientPlazz.IsViewEngineShow(ClientPlazz.MS_GAME) == false)
			return true;
		PlayGameSound(GDF.SOUND_GAME_END);
		SetGameStatus(GDF.GAME_SCENE_FREE);
		CMD_S_GameConClude cmd = new CMD_S_GameConClude();
		cmd.ReadFromByteArray(data, 0);
		KillGameClock(IDI_CALL_POINT);
		KillGameClock(IDI_OUT_CARD);
		int index = 0;
		m_GameClientView.m_HandCardControl.SetCardData(null, 0);
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {

			int carddata[] = new int[cmd.nCardCount[i]];
			System.arraycopy(cmd.nHandCardData, index, carddata, 0, cmd.nCardCount[i]);
			m_GameClientView.m_TableCard[SwitchViewChairID(i)].SetCardData(carddata, cmd.nCardCount[i]);

			index += cmd.nCardCount[i];
			m_GameClientView.SetUserPassState(i, false);
		}
		tagScoreInfo ScoreInfo = new tagScoreInfo();
		ScoreInfo.nBankerScore = cmd.nBankerScore;
		ScoreInfo.nBombCount = cmd.nBombCount;
		ScoreInfo.bSpring = cmd.nChunTian == 1;
		ScoreInfo.bSpringSpring = cmd.nFanChunTian == 1;
		ScoreInfo.lCellScore = cmd.lCellScore;
		ScoreInfo.bBanker = m_nBankerUser == GetMeChairID();
		ScoreInfo.lGameScore = cmd.lGameScore[GetMeChairID()];

		m_GameClientView.m_GameScoreView.SetScoreDate(ScoreInfo);

		if (!IsLookonMode()) {
			SetGameClock(GetMeChairID(), IDI_GAME_START, TIME_START);
		}

		m_bTrustee = false;
		m_GameClientView.SetWaitCallScore(false);
		GDF.SendMainMessage(CGameClientView.IDM_GAME_END, 0, m_GameClientView.GetTag(), null);
		return true;
	}

	/**
	 * 开始游戏
	 */
	public void OnStart() {
		KillGameClock(IDI_GAME_START);
		OnResetGameEngine();
		SendUserReady();
	}

	private boolean OnSubGameStart(byte[] data) {
		PlayGameSound(GDF.SOUND_GAME_START);
		SetGameStatus(GDF.GAME_SCENE_CALL);
		CMD_S_GameStart cmd = new CMD_S_GameStart();
		cmd.ReadFromByteArray(data, 0);
		// 观看用户
		if (IsLookonMode()) {
			m_HandCardData = new int[GDF.MAX_CARDCOUNT];
			m_HandCardCount = new int[GDF.GAME_PLAYER];
			m_bTrustee = false;
			m_nBombCount = 0;
			m_nBankerUser = GDF.INVALID_ITEM;
			m_nBankerScore = 0;

			m_nTurnCardCount = 0;
			m_nTurnCardData = new int[GDF.MAX_CARDCOUNT];

			m_GameClientView.SetBankerScore(0);
			m_GameClientView.SetBankerUser(GDF.INVALID_ITEM);
			m_GameClientView.SetUserCallScore(GDF.INVALID_ITEM, 0);
			m_GameClientView.SetUserPassState(GDF.INVALID_ITEM, false);
			m_GameClientView.SetUserCountWarn(GDF.INVALID_ITEM, false);
			m_GameClientView.SetWaitCallScore(false);
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				m_GameClientView.m_TableCard[i].SetCardData(null, 0);
			}
			m_GameClientView.m_HandCardControl.SetCardData(null, 0);
			m_GameClientView.SetBackCard(null, true);
			// 刷新界面
			m_GameClientView.postInvalidate();
		}
		// 当前用户
		m_nCurrentUser = cmd.nCurrentUser;
		// 获取手牌
		for (int i = 0; i < GDF.NORMAL_COUNT; i++) {
			m_HandCardData[i] = cmd.nCardData[i];
		}
		// 手牌数目
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_HandCardCount[i] = GDF.NORMAL_COUNT;
		}
		m_GameClientView.m_HandCardControl.SetShowCount(0);
		m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, m_HandCardCount[GetMeChairID()]);
		m_GameClientView.m_bAnimation = true;
		m_GameClientView.SetStartUser(SwitchViewChairID(cmd.nStartUser));
		GDF.SendMainMessage(CGameClientView.IDM_SHUFFLE_END, 0, m_GameClientView.GetTag(), null);

		return true;
	}

	@Override
	public boolean OnEventSceneMessage(int nGameStatus, boolean blookon, byte[] data) {
		switch (nGameStatus) {
			case GDF.GAME_SCENE_FREE : {

				CMD_S_StatusFree cmdfree = new CMD_S_StatusFree();
				cmdfree.ReadFromByteArray(data, 0);
				// 底分
				m_GameClientView.SetCellScore(cmdfree.lCellScore);
				// 开始设置
				if (!IsLookonMode() && GetMeUserItem().GetUserStatus() != GDF.US_READY) {
					GDF.SendMainMessage(CGameClientView.IDM_GAME_READY, 1, m_GameClientView.GetTag(), null);
					SetGameClock(GetMeChairID(), IDI_GAME_START, TIME_START);
				} else {
					GDF.SendMainMessage(CGameClientView.IDM_GAME_READY, 0, m_GameClientView.GetTag(), null);
				}
				return true;
			}
			case GDF.GAME_SCENE_CALL : {

				CMD_S_StatusCall cmdcall = new CMD_S_StatusCall();
				cmdcall.ReadFromByteArray(data, 0);

				// 底分
				m_GameClientView.SetCellScore(cmdcall.lCellScore);
				// 当前玩家
				m_nCurrentUser = cmdcall.nCurrentUser;

				for (int i = 0; i < GDF.GAME_PLAYER; i++) {
					// 保存手牌数目
					m_HandCardCount[i] = GDF.NORMAL_COUNT;
					m_GameClientView.SetCardCount(i, GDF.NORMAL_COUNT);

					// 叫分设置
					if (cmdcall.nScoreInfo[i] != 0) {
						m_GameClientView.SetUserCallScore(SwitchViewChairID(i), cmdcall.nScoreInfo[i]);
					}
				}
				// 保存手牌
				for (int i = 0; i < GDF.NORMAL_COUNT; i++) {
					m_HandCardData[i] = cmdcall.nHandCardData[i];
				}

				GameLogic.SortCardList(m_HandCardData, m_HandCardCount[GetMeChairID()], m_nSortCardType);
				// 设置扑克
				m_GameClientView.m_HandCardControl.SetShowCount(GDF.MAX_CARDCOUNT);
				m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, m_HandCardCount[GetMeChairID()]);

				// 叫分按钮
				int nMyTurn = 0;
				if (!IsLookonMode() && m_nCurrentUser == GetMeChairID()) {
					nMyTurn = 1;
				}
				// 保存分析
				AnalyseData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[GetMeChairID()]);
				AnalyseCount = m_HandCardCount[GetMeChairID()];
				GameLogic.SortCardList(AnalyseData, AnalyseCount, CGameLogic.ST_ORDER);

				GDF.SendMainMessage(CGameClientView.IDM_CALL_POINT, cmdcall.nBankerScore, m_GameClientView.GetTag(), nMyTurn);
				m_GameClientView.SetUserCallScore(SwitchViewChairID(m_nCurrentUser), 0);
				SetGameClock(m_nCurrentUser, IDI_CALL_POINT, TIME_CALL_POINT);

				m_GameClientView.postInvalidate();
				return true;
			}
			case GDF.GAME_SCENE_PLAY : {
				// 读取数据
				CMD_S_StatusPlay cmdplay = new CMD_S_StatusPlay();
				cmdplay.ReadFromByteArray(data, 0);

				// 设置变量
				m_nBombCount = cmdplay.cbBombCount;
				m_nBankerUser = cmdplay.nBankerUser;
				m_nBankerScore = cmdplay.cbBankerScore;
				m_nCurrentUser = cmdplay.nCurrentUser;

				// 出牌变量
				m_nTurnCardCount = cmdplay.nTurnCardCount;
				for (int i = 0; i < m_nTurnCardCount; i++) {
					m_nTurnCardData[i] = cmdplay.nTurnCardData[i];
				}

				// 扑克数据
				for (int i = 0; i < GDF.GAME_PLAYER; i++) {
					m_HandCardCount[i] = cmdplay.nHandCardCount[i];
					m_GameClientView.SetCardCount(SwitchViewChairID(i), m_HandCardCount[i]);
				}
				for (int i = 0; i < m_HandCardCount[GetMeChairID()]; i++) {
					m_HandCardData[i] = cmdplay.nHandCardData[i];
				}

				// 设置界面
				m_GameClientView.SetBombCount(m_nBombCount);
				m_GameClientView.SetCellScore(cmdplay.lCellScore);
				m_GameClientView.SetBankerScore(cmdplay.cbBankerScore);
				m_GameClientView.SetBankerUser(SwitchViewChairID(m_nBankerUser));

				m_GameClientView.SetBackCard(cmdplay.nBankerCard, false);

				// 出牌界面
				if (cmdplay.nTurnWiner != GDF.INVALID_CHAIR) {
					m_GameClientView.m_TableCard[SwitchViewChairID(cmdplay.nTurnWiner)].SetCardData(m_nTurnCardData, m_nTurnCardCount);
				}

				// 手牌界面
				GameLogic.SortCardList(m_HandCardData, m_HandCardCount[GetMeChairID()], m_nSortCardType);
				m_GameClientView.m_HandCardControl.SetShowCount(GDF.MAX_CARDCOUNT);
				m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, m_HandCardCount[GetMeChairID()]);

				AnalyseData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[GetMeChairID()]);
				AnalyseCount = m_HandCardCount[GetMeChairID()];
				GameLogic.SortCardList(AnalyseData, AnalyseCount, CGameLogic.ST_ORDER);

				// 控件更新
				int nMyTurn = 0;
				if (m_nCurrentUser == GetMeChairID() && !IsLookonMode()) {
					nMyTurn = 1;
				}
				GDF.SendMainMessage(CGameClientView.IDM_GAME_OUTCARD, m_nTurnCardCount, m_GameClientView.GetTag(), nMyTurn);

				GDF.SendMainMessage(CGameClientView.IDM_BANKER_INFO, SwitchViewChairID(m_nBankerUser), m_GameClientView.GetTag(), null);
				// 设置时间
				SetGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);

				// 刷新界面
				m_GameClientView.postInvalidate();

				return true;
			}
		}
		return false;
	}

	@Override
	public void SubMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
			case CGameClientView.IDM_CALL_POINT :
				OnCallScore(sub);
				break;
			case CGameClientView.IDM_GAME_SKIP :
				OnSkipCard(false);
				break;
			case CGameClientView.IDM_GAME_PASS :
				OnPassCard();
				break;
			case CGameClientView.IDM_GAME_OUTCARD :
				OnOutCard();
				break;
			case CGameClientView.IDM_GAME_START :
				OnStart();
				break;
			case CGameClientView.IDM_DISPATH_END :
				OnFinishSendCard();
				break;
		}
	}

	public void OnFinishSendCard() {
		// 手牌数目
		if (m_GameClientView.m_bAnimation == false)
			return;
		m_GameClientView.m_bAnimation = false;

		ICallBack callback = new ICallBack() {

			@Override
			public boolean OnCallBackDispath(boolean arg0, Object arg1) {
				m_GameClientView.StopAnimation();
				return true;
			}
		};
		GDF.SendMainMessage(CGameClientView.IDM_CALLBACK, 0, m_GameClientView.GetTag(), callback);

		Log.d("发牌结束", "*********************************************");
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_GameClientView.SetCardCount(i, GDF.NORMAL_COUNT);
		}

		// 手牌显示
		m_GameClientView.m_HandCardControl.SetShowCount(GDF.MAX_CARDCOUNT);
		GameLogic.SortCardList(m_HandCardData, m_HandCardCount[GetMeChairID()], m_nSortCardType);
		m_GameClientView.m_HandCardControl.SetCardData(m_HandCardData, m_HandCardCount[GetMeChairID()]);

		// 等待标识
		m_GameClientView.SetWaitCallScore(m_nCurrentUser != GetMeChairID());
		// 设置计时器
		if (m_nCurrentUser != GDF.INVALID_ITEM && GetGameStatus() == GDF.GAME_SCENE_CALL) {
			SetGameClock(m_nCurrentUser, IDI_CALL_POINT, TIME_CALL_POINT);
		}

		// 界面操作更新
		if (!IsLookonMode())
			GDF.SendMainMessage(CGameClientView.IDM_GAME_START, (m_nCurrentUser == GetMeChairID()) ? 1 : 0, m_GameClientView.GetTag(), null);
	}

	@Override
	public boolean OnEventGameClockKill(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int GetTag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean OnEventLookonMode(byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean OnEventInsureMessage(int sub, byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	private void PlayActionSound(int main, int sub, int chair) {
		IClientUserItem item = GetTableUserItem(chair);
		int nGender = 0;
		if (item != null) {
			nGender = (item.GetGender() == GDF.GENDER_FEMALE ? 1 : 0);
		}
		switch (main) {
			case CGameClientView.IDM_CALL_POINT :
				if (sub > 0 && sub < 4)
					PlayGameSound(GDF.SOUND_CS_M_1 + (sub - 1) * 2 + nGender);
				else
					PlayGameSound(GDF.SOUND_CS_M_0 + nGender);
				break;
			case CGameClientView.IDM_GAME_PASS :
				PlayGameSound(GDF.SOUND_PASS_M_0 + (rand.nextInt(2) * 2 + nGender));
				break;
			default :
				break;
		}

	}

	private void PlayCardTypeSound(int type, int data, int count, int chair, boolean bnewturn) {
		IClientUserItem item = GetTableUserItem(chair);
		int nGender = 0;
		if (item != null) {
			nGender = (item.GetGender() == GDF.GENDER_FEMALE ? 1 : 0);
		}
		switch (type) {
			case GDF.CT_1 :
				PlaySingleCard(data, nGender);
				break;
			case GDF.CT_2 :
				if (bnewturn)
					PlayGameSound(GDF.SOUND_TYPE_M_DUI + nGender);
				else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_3 :
				if (bnewturn)
					PlayGameSound(GDF.SOUND_TYPE_M_SAN + nGender);
				else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_1_LINE :
				if (bnewturn)
					PlayGameSound(GDF.SOUND_TYPE_M_DANSUN + nGender);
				else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_2_LINE :
				if (bnewturn)
					PlayGameSound(GDF.SOUND_TYPE_M_DUISUN + nGender);
				else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_3_LINE :
				if (bnewturn)
					PlayGameSound(GDF.SOUND_TYPE_M_SANSUN + nGender);
				else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				GDF.SendMainMessage(CGameClientView.IDM_PLANE, 1, m_GameClientView.GetTag(), null);
				break;
			case GDF.CT_3_LINE_1 :
				if (bnewturn) {
					if (count > 4)
						PlayGameSound(GDF.SOUND_GAME_PLANE);
					else
						PlayGameSound(GDF.SOUND_TYPE_M_SANDAIYI + nGender);
				} else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);

				if (count > 4)
					GDF.SendMainMessage(CGameClientView.IDM_PLANE, 1, m_GameClientView.GetTag(), null);
				break;
			case GDF.CT_3_LINE_2 :
				if (bnewturn) {
					if (count > 5)
						PlayGameSound(GDF.SOUND_GAME_PLANE);
					else
						PlayGameSound(GDF.SOUND_TYPE_M_SANDAIER + nGender);
				} else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				if (count > 5)
					GDF.SendMainMessage(CGameClientView.IDM_PLANE, 1, m_GameClientView.GetTag(), null);
				break;
			case GDF.CT_4_LINE_1 :

				break;
			case GDF.CT_4_LINE_2 :
				if (bnewturn)
					PlayGameSound(GDF.SOUND_TYPE_M_SIDAIER + nGender);
				else
					PlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_BOME_CARD :
				PlayGameSound(GDF.SOUND_GAME_BOMB);
				GDF.SendMainMessage(CGameClientView.IDM_BOMB_START, 0, m_GameClientView.GetTag(), null);
				break;
			case GDF.CT_MISSILE_CARD :
				PlayGameSound(GDF.SOUND_TYPE_M_HUIJIAN + nGender);
				GDF.SendMainMessage(CGameClientView.IDM_ROCKET, 1, m_GameClientView.GetTag(), null);
				break;
			default :
				break;
		}
	}

	private void PlaySingleCard(int data, int gender) {
		int index = data & GDF.MASK_VALUE;
		if (index > 0 && index < 16)
			PlayGameSound(GDF.SOUND_CARD_M_1 + (index - 1) * 2 + gender);
	}
}
