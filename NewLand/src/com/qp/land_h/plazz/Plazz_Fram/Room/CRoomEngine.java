package com.qp.land_h.plazz.Plazz_Fram.Room;

import java.util.ArrayList;
import java.util.List;

import Lib_Control.TimeManager.CTimeManage;
import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Interface.IKeyBackDispatch;
import Lib_Interface.ButtonInterface.IScrollListenner;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_Interface.ITimeInterface.ITimeDispath;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Net_Interface.ICmd;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.CFram;
import com.qp.land_h.plazz.Plazz_Fram.Bank.CBankEx;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureFailure;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureInfo;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureSuccess;
import com.qp.land_h.plazz.cmd.User.CMD_GR_UserLookon;
import com.qp.land_h.plazz.cmd.User.CMD_GR_UserSitDown;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public class CRoomEngine extends CViewEngine implements IKeyBackDispatch, ISingleClickListener, IScrollListenner,
		ITimeDispath, IMainMessage {
        
	public final static int	IDM_MOVE_VIEW		= 1;
	public final static int	IDM_REGAIN_VIEW		= 2;
	public final static int	IDM_UNLOCK			= 3;
	public final static int	IDM_USERINFO		= 4;
	public final static int	IDM_HIDEUSERINFO	= 2005;

	public static int		LINE_PERPAGE		= 3;								// 每列个数
	public static int		ROW_PERPAGE			= 2;								// 每列个数

	public final static int	IDI_TIME_PAGE		= 1;								// 移动计时标识

	public final static int	TIME_MOVE_SPACE		= 40;								// 移动间隔
	public final static int	MOVE_SETUP			= 8;								// 移动步数

	public final static int	PAGE_DISTANCE		= 150;								// 翻页判断
	public final static int	REGAIN_DECIDE		= 99999;							// 回弹判断
	public final static int	REGAIN_SETUP		= 10;								// 回弹步数
	public final static int	REGAIN_DISTANCE		= 30;								// 回弹距离

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

	boolean					m_bFreshDesk;											// 刷新用户信息控制
	boolean					m_bMove;
	//CImageButton			m_btShop;
	CImageButton			m_btBank;												// 银行按钮
	CImageButton			m_btKeyBack;											// 返回按钮
	CImageButton			m_btOption;											// 游戏设置按钮
	CImageButton			m_btQuickSitDown;										// 快速游戏按钮

	CRoomAssist				m_RoomAssist;											// 辅助界面
	CBankEx					m_BankView;
	CTimeManage				m_TimeManger;											// 计时器管理
	CTipView				m_Tip;													// 提示界面
	List<CTablekViewItem>	m_TableViewItem		= new ArrayList<CTablekViewItem>();

	Point					ViewItemSpace		= new Point(0, 0);
	CUserInfoViewEx			m_UserInfoView;
	CFram					m_FarmTop;
	CFram					m_FarmBottom;

	Paint					m_Paint;

	long					m_lRoomRule;
	int						m_nRoomType;

	public CRoomEngine(Context context) {
		super(context);
		setWillNotDraw(true);

		int topheight = 0, bottomheight = 0;
		m_Paint = new Paint();
		m_Paint.setAntiAlias(true);

		m_Paint.setStrokeWidth(10);
		m_Paint.setColor(Color.WHITE);
		switch (CActivity.nDeviceType) {
		case DF.DEVICETYPE_WVGA:
			topheight = 45;
			bottomheight = 60;
			m_Paint.setTextSize(20);
			break;
		case DF.DEVICETYPE_HVGA:
			topheight = 25;
			bottomheight = 20;
			m_Paint.setTextSize(20);
			break;
		case DF.DEVICETYPE_QVGA:
			topheight = 25;
			bottomheight = 25;
			ROW_PERPAGE = 1;
			m_Paint.setTextSize(12);
			break;
		}

		m_TimeManger = new CTimeManage(this);
		m_RoomAssist = new CRoomAssist(context);

		m_Tip = new CTipView(context);
		m_BankView = new CBankEx(context);
		m_UserInfoView = new CUserInfoViewEx(context);

		m_FarmTop = new CFram(context, "", ClientPlazz.SCREEN_WIDHT, topheight);
		m_FarmTop.SetMsg("请选择桌子！");
		m_FarmBottom = new CFram(context, ClientPlazz.RES_PATH + "custom/flag_line.png", ClientPlazz.SCREEN_WIDHT,
				bottomheight);

		m_btBank = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_bank.png");
		m_btKeyBack = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_cancel.png");
		m_btOption = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_option.png");
		m_btQuickSitDown = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_fastgame.png");
		//m_btShop = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_shop.png");
		addView(m_RoomAssist);
		addView(m_FarmTop);
		addView(m_FarmBottom);
		addView(m_btBank);
		addView(m_btKeyBack);
		addView(m_btOption);
		addView(m_btQuickSitDown);
		//addView(m_btShop);

		m_RoomAssist.setScrollListenner(this);
		m_btBank.setSingleClickListener(this);
		m_btKeyBack.setSingleClickListener(this);
		m_btOption.setSingleClickListener(this);
		m_btQuickSitDown.setSingleClickListener(this);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		m_RoomAssist.layout(l, t, r, b);
		m_FarmTop.layout(l, t, r, t + m_FarmTop.GetH());
		m_FarmBottom.layout(l, b - m_FarmBottom.GetH(), r, b);

		m_btQuickSitDown.layout((r - l) / 2 - m_btQuickSitDown.getW() / 2, b - m_btQuickSitDown.getH(), 0, 0);

		int nx = 5;
		int ny = b - m_btKeyBack.getH();
		m_btKeyBack.layout(nx, ny, 0, 0);

		nx = r - 5 - m_btOption.getW();
		ny = b - m_btOption.getH();
		m_btOption.layout(nx, ny, 0, 0);

//		nx = r - 5 - m_btOption.getW() - 5 - m_btShop.getW();
//		ny = b - m_btShop.getH();
//		m_btShop.layout(nx, ny, 0, 0);

		nx = nx - 5 - m_btBank.getW();
		ny = b - m_btBank.getH();
		m_btBank.layout(nx, ny, 0, 0);

		nx = nx - m_BankView.GetW() + m_btBank.getW() + 20;
		ny = ny - m_BankView.GetH();
		m_BankView.layout(nx, ny, nx + m_BankView.GetW(), ny + m_BankView.GetH());

		m_Tip.layout((r - l) / 2 - m_Tip.GetW() / 2, (b - t) / 2 - m_Tip.GetH() / 2, 0, 0);

		m_UserInfoView.layout(r / 2 - l / 2 - m_UserInfoView.GetW() / 2, b / 2 - t / 2 - m_UserInfoView.GetH() / 2, r
				/ 2 - l / 2 + m_UserInfoView.GetW() / 2, b / 2 - t / 2 + m_UserInfoView.GetH() / 2);

	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		ShowBankControl(false);
		ShowTips(false);
		ShowUserInfo(false);

		ClientPlazz.DestoryBackGround(m_RoomAssist);
		m_FarmTop.OnDestoryRes();
		m_FarmBottom.OnDestoryRes();
		m_BankView.OnDestoryRes();
		CChairViewItem.OnDestoryRes();
		CTablekViewItem.OnDestoryResEx();
		m_Tip.OnDestoryRes();
		m_UserInfoView.OnDestoryRes();

		//m_btShop.setVisibility(INVISIBLE);
		m_btBank.setVisibility(INVISIBLE);
		m_btKeyBack.setVisibility(INVISIBLE);
		m_btOption.setVisibility(INVISIBLE);
		m_btQuickSitDown.setVisibility(INVISIBLE);

		setChildrenDrawingCacheEnabled(false);
		destroyDrawingCache();
	}

	@Override
	public void OnInitRes() {
		m_RoomAssist.setBackgroundDrawable(new BitmapDrawable(new CImage(getContext(), ClientPlazz.RES_PATH
				+ "custom/bg.png", null, true).GetBitMap()));
		m_FarmTop.OnInitRes();
		m_FarmBottom.OnInitRes();
		m_BankView.OnInitRes();
		m_BankView.ShowBankByMode(0, false);
		m_bFreshDesk = true;
		MoveOver();
		ViewMove();
		m_Tip.OnInitRes();
		CTablekViewItem.OnInitResEx();
		CChairViewItem.OnInitRes();
		m_UserInfoView.OnInitRes();

		//m_btShop.setVisibility(VISIBLE);
		m_btBank.setVisibility(VISIBLE);
		m_btKeyBack.setVisibility(VISIBLE);
		m_btOption.setVisibility(VISIBLE);
		m_btQuickSitDown.setVisibility(VISIBLE);

		setChildrenDrawingCacheEnabled(true);
		
	}

	@Override
	protected void Render(Canvas canvas) {

	}

	@Override
	public boolean KeyBackDispatch() {
		if (m_BankView.getVisibility() == VISIBLE) {
			ShowBankControl(false);
			m_BankView.ShowBankByMode(0, false);
			return true;
		}
		return false;
	}

	/**
	 * 滚动
	 */
	@Override
	public boolean onScroll(View view, MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (m_Tip.getVisibility() == VISIBLE || m_bMove) {
			return true;
		}
		int ox = (int) e1.getX();
		int nx = (int) e2.getX();
		int distance = Math.abs(-ClientPlazz.SCREEN_WIDHT * m_nCurrentPage + nx - ox - m_nCurrentPagePosX);

		if (distance > 10) {
			m_nCurrentPagePosX = m_nRecordPagePosX + nx - ox;
			ViewMove();
		}
		return true;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		if (m_Tip.getVisibility() == VISIBLE || m_bMove)
			return false;
		int id = view.getId();
		/** 快速坐下 **/
		if (id == m_btQuickSitDown.getId()) {
			ShowBankControl(false);
			m_BankView.ShowBankByMode(0, false);
			ShowUserInfo(false);
			onQuickSitDown();
			return true;
		}
		/** 保险柜界面 **/
		else if (id == m_btBank.getId()) {
			//此功能需使用电脑版本，www.laidapai.cn
			
			new  AlertDialog.Builder(this.getContext())    
			                .setTitle("提示" )  
			                .setMessage("此功能需使用电脑版本，www.laidapai.cn" )  
			                .setPositiveButton("确定" ,  null )  
			                .show();  
			return true;
//			int visibility = m_BankView.getVisibility() == INVISIBLE ? 1 : 0;
//			boolean fresh = m_BankView.getVisibility() == INVISIBLE ? true : false;
//			m_BankView.ShowBankByMode(visibility, fresh);
//			ShowBankControl(visibility == 1);
//			return true;
		}
		/** 退出询问 **/
		else if (id == m_btKeyBack.getId()) {
			PDF.SendMainMessage(ClientPlazz.MM_QUERY_EXIT, 0, null);
			return true;
		}
		/** 设置界面 **/
		else if (id == m_btOption.getId()) {
			PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_OPTION, null);
			return true;
		}

		return false;
	}

	/**
	 * 快速坐下
	 */
	public void onQuickSitDown() {
		IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
		if (kernel != null) {
			ShowTips(true);
			kernel.QuickSitDown();
		}
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

	/**
	 * 触碰处理
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			// // 移动中断判断
			// if (m_Tip.getVisibility() == VISIBLE) {
			// StopMove();
			// }
			break;
		}
		case MotionEvent.ACTION_UP: {
			if (m_bMove == false) {
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
					m_bMove = true;
					m_TimeManger.SetTime(IDI_TIME_PAGE, TIME_MOVE_SPACE, null);
				}
				// else {
				// ShowTips(false);
				// }
			}
			break;
		}
		}
		return super.onInterceptTouchEvent(event);
	}

	/**
	 * 下一页
	 */
	private void PageNext() {
		if (m_nCurrentPage + 1 > m_nMaxPage) {
			m_nPageDistance = -m_nScrollDistance;
		} else {
			m_nPageDistance = -ClientPlazz.SCREEN_WIDHT - m_nScrollDistance;
			m_nCurrentPage++;
			m_bFreshDesk = true;
		}
	}

	/**
	 * 上一页
	 */
	private void PageLast() {
		if (m_nCurrentPage - 1 < 0) {
			m_nPageDistance = -m_nScrollDistance;
		} else {
			m_nPageDistance = ClientPlazz.SCREEN_WIDHT - m_nScrollDistance;
			m_nCurrentPage--;
			m_bFreshDesk = true;
		}
	}

	@Override
	public void MainMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
		// 画面移动
		case IDM_MOVE_VIEW: {
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
			break;
		}
		case IDM_REGAIN_VIEW: {
			m_nCurrentPagePosX = m_nRecordPagePosX + m_nRegainDistance * sub / REGAIN_SETUP;
			ViewMove();
			if (sub >= REGAIN_SETUP) {
				MoveOver();
			}
			break;
		}
		case IDM_UNLOCK: {
			// if (sub == m_nCurrentPage && m_Tip.getVisibility() == VISIBLE) {
			// PDF.RemoveMainMessages(NetCommand.SUB_GR_USER_CHAIR_REQ);
			// ShowTips(false);
			// }
			break;
		}
		case IDM_USERINFO: {
			if (obj != null) {
				m_UserInfoView.ShowUserInfo((IClientUserItem) obj);
				ShowUserInfo(true);
				PDF.SendMainMessageDelayed(IDM_HIDEUSERINFO, 0, GetTag(), null, 2000);
			}
			break;
		}
		case IDM_HIDEUSERINFO: {
			ShowUserInfo(false);
			break;
		}
		case NetCommand.SUB_GR_USER_CHAIR_REQ: {
			if (sub == 0 && m_Tip.getVisibility() == VISIBLE) {
				ShowTips(false);
			}
			break;
		}
		case NetCommand.SUB_GR_CONFIG_SERVER: {
			onSubRoomList(sub);
			break;
		}
		default:
			Log.e(this.getClass().getName(), "MainMessagedispatch>>unkwon mainid>>" + main);
		}

	}

	private void ShowUserInfo(boolean show) {
		m_UserInfoView.setVisibility(show ? VISIBLE : INVISIBLE);
		if (show) {
			if (m_UserInfoView.getParent() == null)
				addView(m_UserInfoView);
		} else {
			removeView(m_UserInfoView);
		}
	}

	private void ShowTips(boolean show) {
		m_Tip.setVisibility(show ? VISIBLE : INVISIBLE);
		if (show) {
			if (m_Tip.getParent() == null)
				addView(m_Tip);
		} else {
			removeView(m_Tip);
		}
	}

	private void ShowBankControl(boolean show) {
		m_BankView.setVisibility(show ? VISIBLE : INVISIBLE);
		if (show) {
			if (m_BankView.getParent() == null)
				addView(m_BankView);
		} else {
			removeView(m_BankView);
		}
	}

	protected void ViewMove() {
		if (m_TableViewItem.size() < 1)
			return;
		int x = 0, y = 0;
		int w = CTablekViewItem.GetW(), h = CTablekViewItem.GetH();
		int count = ROW_PERPAGE * LINE_PERPAGE;
		for (int i = 0; i < m_TableViewItem.size(); i++) {
			x = (i / LINE_PERPAGE + 1) * ViewItemSpace.x + i / LINE_PERPAGE * w + (i / count) * ViewItemSpace.x;
			y = m_FarmTop.GetH() + ViewItemSpace.y + (i % LINE_PERPAGE) * (ViewItemSpace.y + h);
			m_TableViewItem.get(i).layout(m_nCurrentPagePosX + x, y, 0, 0);
		}
	}

	protected void MoveOver() {
		m_TimeManger.KillTime(IDI_TIME_PAGE);
		m_nRecordPagePosX = m_nCurrentPagePosX;
		m_bReginEffect = false;
		m_nRegainDistance = 0;
		m_nPageDistance = 0;
		m_nMoveSetup = 0;
		m_nRegainSetup = 0;
		m_nScrollDistance = 0;

		if (m_bFreshDesk) {
			ShowTips(true);
			int count = ROW_PERPAGE * LINE_PERPAGE;
			for (int i = m_nCurrentPage * count; i < (m_nCurrentPage * count + count); i++) {
				if (i < m_TableViewItem.size()) {
					m_TableViewItem.get(i).RemoveTableUserItem();
				}
			}
			PDF.SendMainMessageDelayed(NetCommand.SUB_GR_USER_CHAIR_REQ, 0, GetTag(), null, 1500);
			((IClientKernelEx) ClientPlazz.GetKernel()).FrushUserInfo(PDF.INVALID_ITEM, m_nCurrentPage * count);
			m_bFreshDesk = false;
		} else {
			ShowTips(false);
		}
		m_bMove = false;
		postInvalidate();
	}

	protected void StopMove() {

	}

	public void Clear() {
		m_nCurrentPagePosX = 0;
		m_nCurrentPage = 0;
		m_nMaxPage = 0;
		MoveOver();
		for (int i = 0; i < m_TableViewItem.size(); i++) {
			CTablekViewItem serverview = m_TableViewItem.get(i);
			if (serverview != null)
				removeView(serverview);
		}
		m_TableViewItem.clear();

	}

	public void onSubRoomList(int tablecount) {
		ShowBankControl(false);
		ShowTips(false);
		ShowUserInfo(false);
		Clear();
		for (int i = 0; i < tablecount; i++) {
			CTablekViewItem desk = new CTablekViewItem(PDF.GetContext(), i);
			addView(desk);
			m_TableViewItem.add(desk);
		}
		if (tablecount > 0) {
			m_nMaxPage = (tablecount - 1) / (ROW_PERPAGE * LINE_PERPAGE);
		}

		int w = CTablekViewItem.GetW(), h = CTablekViewItem.GetH();
		int spacex = (ClientPlazz.SCREEN_WIDHT - w * ROW_PERPAGE) / (ROW_PERPAGE + 1);
		int spacey = (ClientPlazz.SCREEN_HEIGHT - m_FarmTop.GetH() - m_FarmBottom.GetH() - h * LINE_PERPAGE)
				/ (LINE_PERPAGE + 1);
		ViewItemSpace.set(spacex, spacey);

	}

	public void onRoomConfig(int type, long rule, int tablecount, int chaircount) {
		m_nRoomType = type;
		m_lRoomRule = rule;

		onSubRoomList(tablecount);
	}

	public long getRoomRule() {
		return m_lRoomRule;
	}

	public int getRoomType() {
		return m_nRoomType;
	}

	public void SetTableUserItem(int table, int chair, IClientUserItem useritem) {
		int maxtable = m_TableViewItem.size();
		if (table < maxtable && table > -1) {
			m_TableViewItem.get(table).SetTableUserItem(chair, useritem);

			// if (table >= m_nCurrentPage * 6 && table < m_nCurrentPage * 6 + 6) {
			// if (m_Tip.getVisibility() == VISIBLE) {
			// PDF.SendMainMessage(IDM_UNLOCK, m_nCurrentPage, this.GetTag(), null);
			// }
			// }

			if (ClientPlazz.GetGameClientView() != null)
				ClientPlazz.GetGameClientView().OnEventUserStatus(table, chair, useritem);
		} else {
			Log.e("SetTableUserItem", "ERROR-TABLEorCHAIR>>table:" + table + ">>chair:" + chair);
		}
	}

	public IClientUserItem GetTableUserItem(int table, int chair) {
		int maxtable = m_TableViewItem.size();
		if (table < maxtable && table > -1) {
			return m_TableViewItem.get(table).GetTableUserItem(chair);
		}
		return null;
	}

	/**
	 * 设置刷新桌子信息
	 * 
	 * @param fresh
	 */
	public void SetFreshDesk(boolean fresh) {
		m_bFreshDesk = fresh;
	}

	/**
	 * 执行观看
	 * 
	 * @param tableid
	 * @param chairid
	 */
	public void PerformLookonAction(int tableid, int chairid) {
		if (m_Tip.getVisibility() == VISIBLE)
			return;
		IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
		if (kernel != null) {
			CMD_GR_UserLookon cmd = new CMD_GR_UserLookon();
			cmd.nTableID = tableid;
			cmd.nChairID = chairid;
			kernel.SendSocketData(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_USER_LOOKON, cmd);
		}
	}

	/**
	 * 执行坐下
	 * 
	 * @param tableid
	 * @param chairid
	 * @param bEfficacyPass
	 */
	public void PerformSitDownAction(int tableid, int chairid, boolean bEfficacyPass) {

		if (m_Tip.getVisibility() == VISIBLE)
			return;

		CMD_GR_UserSitDown cmd = new CMD_GR_UserSitDown();
		cmd.nTableID = tableid;
		cmd.nChairID = chairid;

		PDF.SendMainMessage(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_USER_SITDOWN, ClientPlazz.GetCutscenesEngine()
				.GetTag(), cmd);
	}

	/**
	 * 显示用户信息
	 * 
	 * @param table
	 * @param chair
	 * @param useritem
	 */
	public void ShowUserInfo(int table, int chair, IClientUserItem useritem) {
		if (m_Tip.getVisibility() == VISIBLE)
			return;
		PDF.RemoveMainMessages(IDM_HIDEUSERINFO);
		PDF.SendMainMessage(IDM_USERINFO, 0, GetTag(), useritem);
	}

	/**
	 * 银行服务器消息处理
	 * 
	 * @param sub
	 * @param data
	 * @return
	 */
	public boolean OnScocketMainInsure(int sub, byte[] data) {
		ICmd cmd = null;
		switch (sub) {
		case NetCommand.SUB_GR_USER_INSURE_INFO: {
			cmd = new CMD_GR_S_UserInsureInfo();
			break;
		}
		case NetCommand.SUB_GR_USER_INSURE_SUCCESS: {
			cmd = new CMD_GR_S_UserInsureSuccess();

			break;
		}
		case NetCommand.SUB_GR_USER_INSURE_FAILURE: {
			cmd = new CMD_GR_S_UserInsureFailure();
			break;
		}
		}
		if (cmd != null) {
			cmd.ReadFromByteArray(data, 0);
			m_BankView.onUserInsureInfo(sub, cmd);
			return true;
		}
		return false;
	}

	public CBankEx GetBank() {
		return m_BankView;
	}

}
