package com.qp.land_h.plazz.Plazz_Fram.Server;

import java.util.ArrayList;
import java.util.List;

import Lib_Control.TimeManager.CTimeManage;
import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Interface.IClientKernel;
import Lib_Interface.IKeyBackDispatch;
import Lib_Interface.ButtonInterface.IScrollListenner;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_Interface.ITimeInterface.ITimeDispath;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_Interface.UserInterface.IUserManageSkin;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Net_Interface.ITCPSocketReadSink;
import Net_Utility.Utility;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.CFram;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.Plazz_Struct.tagServerItem;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_LogonFailure;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_LogonMobile;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

/**
 * @note
 * @remark
 */
public class CServerEngine extends CViewEngine
		implements
			IKeyBackDispatch,
			ITCPSocketReadSink,
			IScrollListenner,
			ISingleClickListener,
			ITimeDispath,
			IMainMessage {

	public final static int	IDM_MOVE_VIEW		= 1;
	public final static int	IDM_REGAIN_VIEW		= 2;

	public final static int	IDI_TIME_PAGE		= 1;								// 移动计时标识

	public final static int	TIME_MOVE_SPACE		= 25;								// 移动间隔
	public final static int	MOVE_SETUP			= 10;								// 移动步数

	public final static int	PAGE_DISTANCE		= 150;								// 翻页判断
	public final static int	REGAIN_DECIDE		= 99999;							// 回弹判断
	public final static int	REGAIN_SETUP		= 10;								// 回弹步数
	public final static int	REGAIN_DISTANCE		= 30;								// 回弹距离

	public static int		LINE_PERPAGE		= 3;								// 每列个数
	public static int		ROW_PERPAGE			= 2;								// 每列个数

	boolean					m_bPageLocked;											// 滑动锁定
	boolean					m_bReginEffect;										// 回弹效果

	int						m_nRecordPagePosX;										// 开始位置
	int						m_nCurrentPagePosX;									// 现在位置
	int						m_nCurrentPage;										// 现在页码
	int						m_nMaxPage;											// 最大页码
	int						m_nPageDistance;										// 翻页移动距离记录
	int						m_nScrollDistance;										// 触碰距离记录
	int						m_nMoveSetup;											// 移动步数
	int						m_nRegainSetup;										// 回弹步数
	int						m_nRegainDistance;										// 回弹距离

	List<CServerViewItem>	m_ServerViewItem	= new ArrayList<CServerViewItem>();
	CImageButton			m_btQuickEnter;
	CImageButton			m_btKeyBack;
	CImageButton			m_btOption;
	CServerAssist			m_ServerAssist;
	CTimeManage				m_TimeManger;
	CFram					m_FarmTop;
	CFram					m_FarmBottom;
	Point					ViewItemSpace		= new Point(0, 0);
	Paint					m_Paint;

	public CServerEngine(Context context) {
		super(context);
		int topheight = 0, bottomheight = 0;
		m_Paint = new Paint();
		m_Paint.setAntiAlias(true);

		m_Paint.setStrokeWidth(10);
		m_Paint.setColor(Color.WHITE);
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				topheight = 45;
				bottomheight = 60;
				m_Paint.setTextSize(20);
				break;
			case DF.DEVICETYPE_HVGA :
				topheight = 25;
				bottomheight = 25;
				m_Paint.setTextSize(20);
				break;
			case DF.DEVICETYPE_QVGA :
				topheight = 25;
				bottomheight = 25;
				ROW_PERPAGE = 1;
				m_Paint.setTextSize(12);
				break;
		}
		m_TimeManger = new CTimeManage(this);
		m_ServerAssist = new CServerAssist(context);
		m_btQuickEnter = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_fastgame.png");
		m_btKeyBack = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_cancel.png");
		m_btOption = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_option.png");

		m_FarmTop = new CFram(context, "", ClientPlazz.SCREEN_WIDHT, topheight);
		m_FarmTop.SetMsg("请选择房间！");
		m_FarmBottom = new CFram(context, ClientPlazz.RES_PATH + "custom/flag_line.png", ClientPlazz.SCREEN_WIDHT, bottomheight);

		m_btQuickEnter.setSingleClickListener(this);
		m_ServerAssist.setScrollListenner(this);
		m_btKeyBack.setSingleClickListener(this);
		m_btOption.setSingleClickListener(this);

		addView(m_ServerAssist);
		addView(m_FarmTop);
		addView(m_FarmBottom);
		addView(m_btKeyBack);
		addView(m_btOption);
		addView(m_btQuickEnter);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		m_ServerAssist.layout(l, t, r, b);
		m_FarmTop.layout(l, t, r, t + m_FarmTop.GetH());
		m_FarmBottom.layout(l, b - m_FarmBottom.GetH(), r, b);
		m_btQuickEnter.layout((r - l) / 2 - m_btQuickEnter.getW() / 2, b - m_btQuickEnter.getH(), 0, 0);

		int nx = 5;
		int ny = b - m_btKeyBack.getH();
		m_btKeyBack.layout(nx, ny, 0, 0);

		nx = r - 5 - m_btOption.getW();
		ny = b - m_btOption.getH();
		m_btOption.layout(nx, ny, 0, 0);

	}

	protected void ViewMove() {
		if (m_ServerViewItem.size() < 1)
			return;
		int x = 0, y = 0;
		int w = CServerViewItem.GetW(), h = CServerViewItem.GetH();

		int count = LINE_PERPAGE * ROW_PERPAGE;
		for (int i = 0; i < m_ServerViewItem.size(); i++) {
			x = (i / LINE_PERPAGE + 1) * ViewItemSpace.x + i / LINE_PERPAGE * w + (i / count) * ViewItemSpace.x;
			y = m_FarmTop.GetH() + ViewItemSpace.y + (i % LINE_PERPAGE) * (ViewItemSpace.y + h);
			m_ServerViewItem.get(i).layout(m_nCurrentPagePosX + x, y, 0, 0);
		}

	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		ClientPlazz.DestoryBackGround(m_ServerAssist);
		m_FarmTop.OnDestoryRes();
		m_FarmBottom.OnDestoryRes();
		m_TimeManger.StopTime();
		m_btQuickEnter.setVisibility(INVISIBLE);
		m_btKeyBack.setVisibility(INVISIBLE);
		m_btOption.setVisibility(INVISIBLE);
		CServerViewItem.OnDestoryRes();
	}

	@Override
	public void OnInitRes() {
		IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
		if (kernel != null) {
			kernel.IntemitConnect();
			kernel.SetSocketReadMode(1);
			IUserManageSkin usermanage = kernel.GetUserManage();
			if (usermanage != null) {
				usermanage.ReleaseUserItem(true);
			}
		}
		m_ServerAssist.setBackgroundDrawable(new BitmapDrawable(new CImage(getContext(), ClientPlazz.RES_PATH + "custom/bg.png", null, true).GetBitMap()));
		m_nCurrentPagePosX = -m_nCurrentPage * ClientPlazz.SCREEN_WIDHT;
		m_FarmTop.OnInitRes();
		m_FarmBottom.OnInitRes();
		CServerViewItem.OnInitRes();
		m_btQuickEnter.setVisibility(VISIBLE);
		m_btKeyBack.setVisibility(VISIBLE);
		m_btOption.setVisibility(VISIBLE);
		MoveOver();
		ViewMove();

	}

	@Override
	protected void Render(Canvas canvas) {

	}

	@Override
	public boolean onEventTCPSocketRead(int main, int sub, Object obj) {

		return false;
	}

	@Override
	public boolean KeyBackDispatch() {
		if (m_bPageLocked)
			return true;
		return false;
	}

	@Override
	public boolean onScroll(View view, MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (m_bPageLocked) {
			return true;
		}
		Log.i(this.getClass().getName(), "onScroll-");
		int ox = (int) ((view.getId() == m_ServerAssist.getId()) ? e1.getX() : distanceX);
		int nx = (int) ((view.getId() == m_ServerAssist.getId()) ? e2.getX() : distanceY);

		int distance = Math.abs(-ClientPlazz.SCREEN_WIDHT * m_nCurrentPage + nx - ox - m_nCurrentPagePosX);

		if (distance > 20) {
			m_nCurrentPagePosX = m_nRecordPagePosX + nx - ox;
			ViewMove();
		}

		return true;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {

		if (m_bPageLocked) {
			return true;
		}

		int id = view.getId();
		boolean bSucceed = true;
		if (id == m_btQuickEnter.getId()) {
			bSucceed = onQuickEnterRoom();
		} else if (id == m_btOption.getId()) {
			PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_OPTION, null);
		} else if (id == m_btKeyBack.getId()) {
			PDF.SendMainMessage(ClientPlazz.MM_QUERY_EXIT, 0, null);
		} else {
			for (int i = 0; i < m_ServerViewItem.size(); i++) {
				if (id == m_ServerViewItem.get(i).getId()) {
					Log.i(this.getClass().getName(), "服务器选择-" + i);
					bSucceed = onNormalEnterRoom(i);
					break;
				}
			}
		}
		if (!bSucceed) {
			Toast.makeText(PDF.GetContext(), "未找到合适房间进入", Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	@Override
	public void OnTime(int timeid) {
		if (timeid == IDI_TIME_PAGE) {
			if (m_nMoveSetup < MOVE_SETUP) {
				m_nMoveSetup++;
				PDF.SendMainMessage(IDM_MOVE_VIEW, m_nMoveSetup, this.GetTag(), m_bReginEffect);
			} else if (m_bReginEffect && m_nRegainSetup < REGAIN_SETUP) {
				m_nRegainSetup++;
				PDF.SendMainMessage(IDM_REGAIN_VIEW, m_nRegainSetup, this.GetTag(), m_bReginEffect);
			}
		}

	}

	public void MoveOver() {
		m_TimeManger.KillTime(IDI_TIME_PAGE);
		m_nRecordPagePosX = m_nCurrentPagePosX;
		m_bReginEffect = false;
		m_nRegainDistance = 0;
		m_nPageDistance = 0;
		m_nMoveSetup = 0;
		m_nRegainSetup = 0;
		m_nScrollDistance = 0;
		m_bPageLocked = false;

	}

	private void StopMove() {

	}

	/**
	 * 触碰处理
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN : {
				// 移动中断判断
				if (m_bPageLocked) {
					StopMove();
				}
				break;
			}
			case MotionEvent.ACTION_UP : {
				if (m_bPageLocked == false) {
					m_bPageLocked = true;

					m_nScrollDistance = m_nCurrentPagePosX - m_nRecordPagePosX;
					m_nRecordPagePosX = m_nCurrentPagePosX;
					// 上页判断
					if (m_nScrollDistance > PAGE_DISTANCE) {
						PageLast();
					}
					// 下页判断
					else if (m_nScrollDistance < -PAGE_DISTANCE) {
						PageNext();
					} else {
						m_nPageDistance = -m_nScrollDistance;
					}
					if (m_nPageDistance != 0) {
						// 回弹效果判断
						if (Math.abs(m_nPageDistance) > REGAIN_DECIDE) {
							m_bReginEffect = true;
							m_nRegainDistance = m_nPageDistance > 0 ? -REGAIN_DISTANCE : REGAIN_DISTANCE;
							m_nPageDistance -= m_nRegainDistance;
						}
						m_TimeManger.SetTime(IDI_TIME_PAGE, TIME_MOVE_SPACE, null);
					} else {
						m_bPageLocked = false;
					}
				}
				break;
			}
		}
		return super.onInterceptTouchEvent(event);
	}

	private void PageNext() {
		if (m_nCurrentPage + 1 > m_nMaxPage) {
			m_nPageDistance = -m_nScrollDistance;
		} else {
			m_nPageDistance = -ClientPlazz.SCREEN_WIDHT - m_nScrollDistance;
			m_nCurrentPage++;
		}
	}

	private void PageLast() {
		if (m_nCurrentPage - 1 < 0) {
			m_nPageDistance = -m_nScrollDistance;
		} else {
			m_nPageDistance = ClientPlazz.SCREEN_WIDHT - m_nScrollDistance;
			m_nCurrentPage--;
		}
	}

	@Override
	public void MainMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
		// 画面移动
			case IDM_MOVE_VIEW : {
				m_nCurrentPagePosX = m_nRecordPagePosX + m_nPageDistance * sub / MOVE_SETUP;
				ViewMove();
				if (sub >= MOVE_SETUP) {
					if ((Boolean) obj == false) {
						MoveOver();
					} else {
						m_nCurrentPagePosX = m_nRecordPagePosX + m_nPageDistance;
						m_nRecordPagePosX = m_nCurrentPagePosX;
					}
				}
				return;
			}
			case IDM_REGAIN_VIEW : {
				m_nCurrentPagePosX = m_nRecordPagePosX + m_nRegainDistance * sub / REGAIN_SETUP;
				ViewMove();
				if (sub >= REGAIN_SETUP) {
					MoveOver();
				}
				break;
			}
			default :
				Log.e(this.getClass().getName(), "MainMessagedispatch>>unkwon mainid>>" + main);
		}

	}

	public boolean onQuickEnterRoom() {
		for (int i = 0; i < m_ServerViewItem.size(); i++) {
			tagServerItem serveritem = m_ServerViewItem.get(i).m_ServerItem;
			if (serveritem.lOnFullCount > serveritem.lOnLineCount) {
				ClientPlazz.FAST_ENTER = true;
				onNormalEnterRoom(i);
				return true;
			}
		}
		Toast.makeText(PDF.GetContext(), "未找到合适房间进入", Toast.LENGTH_SHORT).show();
		return false;
	}

	public boolean onNormalEnterRoom(int index) {
		if (index < m_ServerViewItem.size() && m_ServerViewItem.get(index) != null) {
			tagServerItem item = m_ServerViewItem.get(index).GetServerItem();

			if (!ConnectServer(item.szServerUrl, item.nServerPort)) {
				Toast.makeText(PDF.GetContext(), "网络连接失败...", Toast.LENGTH_SHORT).show();
				return true;
			}

			// 发送登录
			IClientKernelEx clientkernel = (IClientKernelEx) ClientPlazz.GetKernel();
			CMD_GR_LogonMobile cmd = new CMD_GR_LogonMobile();
			IClientUserItem Meitem = clientkernel.GetMeUserItem();
			cmd.nGameID = clientkernel.GetGameAttribute().KindId;
			cmd.lVersion = clientkernel.GetGameAttribute().GameVersion;
			cmd.cbDeviceType = (byte) ClientPlazz.GetGlobalUnits().GetDeviceType();
			cmd.wBehaviorFlags = (NetCommand.VIEW_MODE_PART | NetCommand.VIEW_INFO_LEVEL_2 | NetCommand.RECVICE_GAME_CHAT | NetCommand.BEHAVIOR_LOGON_NORMAL);
			cmd.wPageTableCount = LINE_PERPAGE * ROW_PERPAGE;
			cmd.lUserID = Meitem.GetUserID();
			cmd.szMachineID = PDF.GetPhoneIMEI();
			cmd.szPassword = ClientPlazz.GetPlazzInstance().GetLogonPassWord();

			PDF.SendMainMessage(NetCommand.MDM_GR_LOGON, NetCommand.SUB_GR_LOGON_MOBILE, ClientPlazz.GetCutscenesEngine().GetTag(), cmd);

			return true;
		}
		return false;
	}

	/** 连接服务器 **/
	private boolean ConnectServer(String url, int port) {

		boolean bSucceed = false;
		IClientKernel clientkernel = ClientPlazz.GetKernel();
		bSucceed = clientkernel.CreateConnect(url, port);
		if (!bSucceed) {
			CMD_MB_LogonFailure cmd = new CMD_MB_LogonFailure();
			cmd.lErrorCode = 0;
			cmd.szDescribeString = "建立链接失败，请检测网络设置!";
			PDF.SendMainMessage(NetCommand.MDM_MB_LOGON, NetCommand.SUB_MB_LOGON_FAILURE, this.GetTag(), cmd);
		}
		return bSucceed;
	}

	public void Clear() {
		m_nCurrentPage = 0;
		m_nCurrentPagePosX = 0;
		m_nMaxPage = 0;
		for (int i = 0; i < m_ServerViewItem.size(); i++) {
			CServerViewItem serverview = m_ServerViewItem.get(i);
			if (serverview != null)
				removeView(serverview);
		}

		m_ServerViewItem.clear();
	}

	/**
	 * 网络服务器列表接收
	 * 
	 * @param data
	 * @return
	 */
	public boolean onSubServerList(byte data[]) {
		Clear();
		if (data != null) {
			if (data.length % 146 != 0)
				return false;
			int count = data.length / 146;
			for (int i = 0; i < count; i++) {
				tagServerItem serveritem = new tagServerItem();
				int nIndex = i * 146;
				serveritem.nKindID = Utility.read2Byte(data, nIndex);
				nIndex += 2;
				serveritem.nNodeID = Utility.read2Byte(data, nIndex);
				nIndex += 2;
				serveritem.nSortID = Utility.read2Byte(data, nIndex);
				nIndex += 2;
				serveritem.nServerID = Utility.read2Byte(data, nIndex);
				nIndex += 2;
				serveritem.nServerPort = Utility.read2Byte(data, nIndex);
				nIndex += 2;
				serveritem.lOnLineCount = Utility.read4Byte(data, nIndex);
				nIndex += 4;
				serveritem.lOnFullCount = Utility.read4Byte(data, nIndex);
				nIndex += 4;
				serveritem.szServerUrl = Utility.wcharUnicodeBytesToString(data, nIndex, 64);
				nIndex += 64;
				serveritem.szServerName = Utility.wcharUnicodeBytesToString(data, nIndex, 64);
//				if (serveritem.szServerName.indexOf("财富") == -1)
//					continue;
				CServerViewItem serverview = new CServerViewItem(PDF.GetContext(), serveritem, m_Paint);
				addView(serverview);
				serverview.setSingleClickListener(this);
				serverview.setScrollListenner(this);
				m_ServerViewItem.add(serverview);
			}
		}
		if (m_ServerViewItem.size() > 0) {
			m_nMaxPage = (m_ServerViewItem.size() - 1) / (LINE_PERPAGE * ROW_PERPAGE);
			int w = CServerViewItem.GetW(), h = CServerViewItem.GetH();
			int spacex = (ClientPlazz.SCREEN_WIDHT - w * ROW_PERPAGE) / (ROW_PERPAGE + 1);
			int spacey = (ClientPlazz.SCREEN_HEIGHT - m_FarmTop.GetH() - m_FarmBottom.GetH() - h * LINE_PERPAGE) / (LINE_PERPAGE + 1);
			ViewItemSpace.set(spacex, spacey);
		}
		return true;
	}
}
