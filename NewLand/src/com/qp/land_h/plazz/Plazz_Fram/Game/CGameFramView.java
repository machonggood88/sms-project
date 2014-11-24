package com.qp.land_h.plazz.Plazz_Fram.Game;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_Interface.HandleInterface.ISubMessage;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.View.CViewEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.df.PDF;

public abstract class CGameFramView extends CViewEngine
		implements
		IMainMessage,
		ISubMessage {

	protected Point				m_ptClock[];	// 时钟位置
	protected Point				m_ptReady[];	// 准备位置
	protected Point				m_ptAvatar[];	// 头像位置
	protected Point				m_ptNickName[]; // 名字位置
	protected Point				m_ptOffline[];	// 掉线位置
	protected Point				m_ptTrustee[];	// 托管位置

	/** 时钟背景 **/
	protected CImageEx			m_ImageClock;
	/** 时钟数字 **/
	protected CImageEx			m_ImageNum;
	/** 准备图标 **/
	protected CImageEx			m_ImageReady;

	protected CGameFramEngine	GameFramEngine;

	public CGameFramView(Context context) {
		super(context);
		int count = ClientPlazz.GetKernel().GetGameAttribute().ChairCount;
		m_ptClock = new Point[count];
		m_ptReady = new Point[count];
		m_ptAvatar = new Point[count];
		m_ptNickName = new Point[count];
		m_ptOffline = new Point[count];
		m_ptTrustee = new Point[count];

		try {
			m_ImageNum = new CImageEx(ClientPlazz.RES_PATH + "custom/timenum.png");
			m_ImageReady = new CImageEx(ClientPlazz.RES_PATH + "custom/ready.png");
			m_ImageClock = new CImageEx(ClientPlazz.RES_PATH + "custom/clock.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public CGameFramEngine GetGameClientEngine() {
		return GameFramEngine;
	}

	/**
	 * 获取玩家时间
	 * 
	 * @param chair
	 * @return
	 */
	public int GetUserClock(int viewid) {
		IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
		if (kernel != null) {
			int Player = ClientPlazz.GetKernel().GetGameAttribute().ChairCount;
			for (int i = 0; i < Player; i++) {
				if (GameFramEngine.SwitchViewChairID(i) == viewid) {
					return kernel.GetUserClock(i);
				}
			}
		}
		return 0;
	}

	/**
	 * 获取头像
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 * @param useritem
	 */
	public void DrawUserAvatar(Canvas canvas, int x, int y,
			IClientUserItem useritem) {
		CPlazzGraphics graphics = CPlazzGraphics.onCreate();
		if (graphics != null)
			graphics.DrawUserAvatar(canvas, x, y, useritem);
	}

	/**
	 * 绘制准备标识
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 */
	public void DrawUserReady(Canvas canvas, int x, int y) {
		m_ImageReady.DrawImage(canvas, x, y);
	}

	/**
	 * 绘制用户时间
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 * @param clock
	 */
	public void DrawUserClock(Canvas canvas, int x, int y, int clock) {
		int w = m_ImageNum.GetW() / 10;
		int h = m_ImageNum.GetH();
		int time = clock % 100;
		int Ten = time / 10;
		int Unit = time % 10;
		m_ImageClock.DrawImage(canvas, x - m_ImageClock.GetW() / 2, y
				- m_ImageClock.GetH() / 2);
		m_ImageNum.DrawImage(canvas, x - w, y - h / 2, w, h, Ten * w, 0);
		m_ImageNum.DrawImage(canvas, x, y - h / 2, w, h, Unit * w, 0);
	}

	public IClientUserItem GetClientUserItem(int viewid) {
		if (viewid == PDF.INVALID_CHAIR)
			return null;
		int Player = ClientPlazz.GetKernel().GetGameAttribute().ChairCount;
		for (int i = 0; i < Player; i++) {
			if (GameFramEngine.SwitchViewChairID(i) == viewid) {
				return ClientPlazz.GetRoomEngine().GetTableUserItem(
						CGameFramEngine.m_TableID, i);
			}
		}
		return null;
	}

	@Override
	public void SubMessagedispatch(int main, int sub, Object obj) {
		GameFramEngine.SubMessagedispatch(main, sub, obj);

	}

	public void postInvalidateChair(int chair) {
		postInvalidate();
	}

	public void postInvalidteClock(int chair) {
		if (chair >= 0 && chair < m_ptClock.length) {
			if (m_ptClock[chair] == null)
				return;
			postInvalidate(m_ptClock[chair].x - m_ImageClock.GetW() / 2,
					m_ptClock[chair].y - m_ImageClock.GetH() / 2,
					m_ptClock[chair].x + m_ImageClock.GetW() / 2,
					m_ptClock[chair].y + m_ImageClock.GetH() / 2);
		}
	}

	/**
	 * 快速坐下
	 */
	public void onQuickSitDown() {
		if (GameFramEngine.GetMeUserStatus() > PDF.US_READY) {
			Toast.makeText(PDF.GetContext(), "正在游戏中，无法换位！", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
		if (kernel != null){
			kernel.QuickSitDown();
		}
	}

	public int GetReadyW() {
		if (m_ImageReady != null)
			return m_ImageReady.GetW();
		return 0;
	}

	public int GetReadyH() {
		if (m_ImageReady != null)
			return m_ImageReady.GetH();
		return 0;
	}

	public int GetClockNumW() {
		if (m_ImageNum != null)
			return m_ImageNum.GetW();
		return 0;
	}

	public int GetClockNumH() {
		if (m_ImageNum != null)
			return m_ImageNum.GetH();
		return 0;
	}

	public int GetClockW() {
		if (m_ImageClock != null)
			return m_ImageClock.GetW();
		return 0;
	}

	public int GetClockH() {
		if (m_ImageClock != null)
			return m_ImageClock.GetH();
		return 0;
	}

	public abstract void RectifyControl(int width, int height);

	public abstract void ResetGameView();

	public void OnEventUserStatus(int table, int chair, IClientUserItem item) {
		if (GameFramEngine.m_bActive == false)
			return;
		if (CGameFramEngine.m_TableID != table)
			return;
		int viewid = GameFramEngine.SwitchViewChairID(chair);
		OnEventUserStatus(viewid, item);
	}

	public abstract void OnEventUserStatus(int chair, IClientUserItem item);

	@Override
	public void OnInitRes() {
		try {
			m_ImageNum.OnReLoadImage();
			m_ImageReady.OnReLoadImage();
			m_ImageClock.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void OnDestoryRes() {
		m_ImageNum.OnReleaseImage();
		m_ImageReady.OnReleaseImage();
		m_ImageClock.OnReleaseImage();
	}
}
