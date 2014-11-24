package com.qp.land_h.plazz.Plazz_Fram.Game;

import Lib_Interface.HandleInterface.ISubMessage;
import Lib_Interface.ITimeInterface.IUserTimeDispath;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.GlobalUnits.CGlobalUnitsEx;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.Plazz_Interface.IGameClientManage;
import com.qp.land_h.plazz.cmd.User.CMD_GR_UserStandUp;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public abstract class CGameFramEngine
				implements
				IUserTimeDispath,
				ISubMessage,
				IGameClientManage{

	protected int				m_nGameStatus	= PDF.GAME_STATUS_FREE;
	protected boolean			m_bAllowLookon;
	protected IClientUserItem	m_MeUserItem	= null;

	protected boolean			m_bActive;
	public static int			m_TableID		= PDF.INVALID_TABLE;
	public static int			m_ChairID		= PDF.INVALID_CHAIR;

	public CGameFramEngine(){

	}

	public void SetActive(int table, int chair) {
		if (table != PDF.INVALID_TABLE && chair != PDF.INVALID_CHAIR) {
			m_TableID = table;
			m_ChairID = chair;
			m_bActive = true;
		} else {
			m_TableID = PDF.INVALID_TABLE;
			m_ChairID = PDF.INVALID_CHAIR;
			m_bActive = false;
			((IClientKernelEx)ClientPlazz.GetKernel()).ReleasUserTime();
		}
	}

	public boolean IsActive() {
		return m_bActive;
	}

	@Override
	public abstract boolean OnInitGameEngine();

	@Override
	public abstract boolean OnResetGameEngine();

	/**
	 * 游戏消息处理
	 * 
	 * @param sub
	 * @param data
	 * @return
	 */
	@Override
	public abstract boolean OnEventGameMessage(int sub, byte[] data);

	/**
	 * 场景信息处理
	 * 
	 * @param gamestatus
	 * @param blookon
	 * @param data
	 */
	@Override
	public abstract boolean OnEventSceneMessage(int gamestatus,
					boolean blookon, byte[] data);

	/**
	 * 起立
	 * 
	 * @param bforce
	 *            强制退出
	 */
	public void PerformStandupAction(boolean bforce) {
		IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();
		if (kernel != null && m_MeUserItem != null) {
			CMD_GR_UserStandUp cmd = new CMD_GR_UserStandUp();
			cmd.nChairID = m_MeUserItem.GetChairID();
			cmd.nTableID = m_MeUserItem.GetTableID();
			cmd.cbForceLeave = (byte)(bforce?1:0);
			kernel.SendSocketData(NetCommand.MDM_GR_USER,
							NetCommand.SUB_GR_USER_STANDUP, cmd);
		}
		if (m_nGameStatus == PDF.GAME_STATUS_FREE || bforce)
			((IClientKernelEx)ClientPlazz.GetKernel()).ReleasUserTime();
	}

	/**
	 * 发送网络消息
	 * 
	 * @param main
	 * @param sub
	 * @param obj
	 * @return
	 */
	public boolean SendSocketData(int sub, Object obj) {
		IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();
		if (kernel != null)
			return kernel.SendSocketData(NetCommand.MDM_GF_GAME, sub, obj);
		return false;
	}

	/**
	 * 发送准备
	 * 
	 * @return
	 */
	public boolean SendUserReady() {
		IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();
		if (kernel != null)
			return kernel.SendUserReady(null, 0);
		return false;
	}

	/**
	 * 获取游戏状态
	 * 
	 * @return
	 */
	public int GetGameStatus() {
		return m_nGameStatus;
	}

	/**
	 * 设置游戏状态
	 * 
	 * @param gamestatus
	 */
	public void SetGameStatus(int gamestatus) {
		m_nGameStatus = gamestatus;
	}

	/**
	 * 获取桌子玩家
	 * 
	 * @param chair
	 * @return
	 */
	public IClientUserItem GetTableUserItem(int chair) {
		int Player = ClientPlazz.GetKernel().GetGameAttribute().ChairCount;
		if (m_bActive && chair > -1 && chair < Player) {
			return ClientPlazz.GetRoomEngine().GetTableUserItem(m_TableID,
							chair);
		}
		return null;
	}

	/**
	 * 是否允许观看
	 * 
	 * @return
	 */
	public boolean IsAllowLookon() {
		return m_bAllowLookon;
	}

	/**
	 * 设置是否允许观看
	 * 
	 * @param allowlookon
	 */
	public void SetAllowLookon(boolean allowlookon) {
		m_bAllowLookon = allowlookon;
	}

	/**
	 * 场景信息
	 * 
	 * @param data
	 */
	@Override
	public boolean OnEventSceneMessage(byte[] data) {
		if (m_MeUserItem != null) {
			if (OnEventSceneMessage(m_nGameStatus,
							m_MeUserItem.GetUserStatus() == PDF.US_LOOKON, data)) {
				if (ClientPlazz.IsViewEngineShow(ClientPlazz.MS_CUTSCENES)) {
					PDF.SendMainMessage(NetCommand.MDM_GF_FRAME, NetCommand.SUB_GF_GAME_SCENE,
									ClientPlazz.GetCutscenesEngine().GetTag(), null);
				}
				else {
					PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_GAME, null);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取自己信息
	 * 
	 * @return
	 */
	public IClientUserItem GetMeUserItem() {
		return m_MeUserItem;
	}

	/**
	 * 设置自己信息
	 * 
	 * @param useritem
	 */
	public void SetMeUserItem(IClientUserItem useritem) {
		m_MeUserItem = useritem;
	}

	/**
	 * 获取自己位置
	 * 
	 * @return
	 */
	public int GetMeChairID() {
		if (m_MeUserItem != null) {
			return m_MeUserItem.GetChairID();
		}
		return PDF.INVALID_CHAIR;
	}

	/**
	 * 获取自己状态
	 * 
	 * @return
	 */
	public int GetMeUserStatus() {
		if (m_MeUserItem != null) {
			return m_MeUserItem.GetUserStatus();
		}
		return PDF.US_NULL;
	}

	public boolean IsLookonMode() {
		if (m_MeUserItem != null) {
			return m_MeUserItem.GetUserStatus() == PDF.US_LOOKON;
		}
		return true;
	}

	/**
	 * 删除玩家时钟
	 * 
	 * @param clockid
	 * @return
	 */
	public boolean KillGameClock(int clockid) {
		IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();

		if (kernel != null) {
			int user = kernel.GetClockChairID();
			kernel.KillGameClock(clockid);
			if (ClientPlazz.GetGameClientView() != null)
				ClientPlazz.GetGameClientView().postInvalidteClock(SwitchViewChairID(user));
			return true;
		}

		return false;

	}

	/**
	 * 设置玩家时钟
	 * 
	 * @param chairid
	 * @param clockid
	 * @param time
	 * @return
	 */
	public boolean SetGameClock(int chairid, int clockid, int time) {
		IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();
		if (kernel != null) {
			kernel.SetGameClock(chairid, clockid, time);
			return true;
		}
		return false;

	}

	/**
	 * 视图位置
	 * 
	 * @param chairid
	 * @return
	 */
	public int SwitchViewChairID(int chairid) {
		IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();
		if (kernel != null) {
			return kernel.SwitchViewChairID(chairid);
		}
		return PDF.INVALID_CHAIR;
	}

	/** 用户进入 **/
	@Override
	public void OnEventUserEnter(IClientUserItem useritem, boolean lookonuser) {
		// if (m_bActive && useritem != null && lookonuser == false) {
		// if (m_GameFramView != null) {
		// m_GameFramView.postInvalidate();
		// }
		// }
	}

	/** 用户离开 **/
	@Override
	public void OnEventUserLeave(IClientUserItem useritem, boolean lookonuser) {
		// if (m_bActive && useritem != null && lookonuser == false) {
		// if (m_GameFramView != null) {
		// m_GameFramView.postInvalidate();
		// }
		// }
	}

	/** 用户积分 **/
	@Override
	public void OnEventUserScore(IClientUserItem useritem, boolean lookonuser) {
		// if (m_bActive && useritem != null && lookonuser == false) {
		// if (m_GameFramView != null) {
		// m_GameFramView.postInvalidate();
		// }
		// }
	}

	/** 用户状态 **/
	@Override
	public void OnEventUserStatus(IClientUserItem useritem, boolean lookonuser) {
		// if (m_bActive && useritem != null && lookonuser == false) {
		// if (m_GameFramView != null) {
		// m_GameFramView.postInvalidate();
		// }
		// }
	}

	@Override
	public abstract void SubMessagedispatch(int main, int sub, Object obj);

	/**
	 * 加载音效
	 * 
	 * @param szpath
	 */
	public void LoadGameSound(int raw, int id) {
		CGlobalUnitsEx globalunits = ClientPlazz.GetGlobalUnits();
		if (globalunits != null)
			globalunits.LoadGameSound(raw, id);
	}

	/**
	 * 播放音效
	 * 
	 * @param szpath
	 * @param uLoop
	 */
	public void PlayGameSound(int id, int uLoop) {
		CGlobalUnitsEx globalunits = ClientPlazz.GetGlobalUnits();
		if (globalunits != null)
			globalunits.PlayGameSound(id, uLoop);
	}

	/**
	 * 播放音效
	 * 
	 * @param szpath
	 */
	public void PlayGameSound(int id) {
		CGlobalUnitsEx globalunits = ClientPlazz.GetGlobalUnits();
		if (globalunits != null)
			globalunits.PlayGameSound(id, 0);
	}

}
