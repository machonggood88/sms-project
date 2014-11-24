package com.qp.land_h.plazz.Plazz_Fram.Login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IClientKernel;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_Interface.UserInterface.IUserManageSkin;
import Lib_Sql.CDatabaseService;
import Lib_Sql.CSQLHelp;
import Lib_Struct.tagAccountsDB;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Lib_System.View.ButtonView.CRadioButton;
import Net_Struct.CPackMessage;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.EmptyEditText;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_LogonByAccounts;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_LogonFailure;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_RegisterAccounts;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;
import com.qp.new_land.R;

/**
 * 登录界面类
 * 
 * @note
 * @remark
 */
public class CLoginEngine extends CViewEngine implements ISingleClickListener, IMainMessage, OnItemClickListener, OnItemLongClickListener {

	protected int					m_nShowMode;

	protected CPackMessage			m_LoginPack;

	protected String				m_szLoginMsg		= "";

	/** 登录等待 **/
	protected ProgressBar			m_ProBar;

	/** 输入背景 **/
	protected CImageEx				m_ImageTextBG;
	protected CImageEx				m_ImageInfoBG;
	protected CImageEx				m_ImageHeadBG;
	/** 位置信息 **/
	protected Point					m_ptFram			= new Point();

	protected CUserInfoView			m_UserInfoView;

	/** 名字输入框 **/
	protected EmptyEditText			m_EdAccounts;
	/** 密码输入框 **/
	protected EmptyEditText			m_EdPassWord;
	/** 登录按钮 **/
	protected CImageButton			m_btLogon;
	/** 注册按钮 **/
	protected CImageButton			m_btRegister;
	/** 开始游戏按钮 **/
	protected CImageButton			m_btStart;
	/** 切换帐号按钮 **/
	protected CImageButton			m_btChange;
	/** 下拉按钮 **/
	protected CImageButton			m_btListDown;
	/** 上拉按钮 **/
	protected CImageButton			m_btListUp;
	/** 记录按钮 **/
	protected CRadioButton			m_rbtRecord;
	/** 自动按钮 **/
	protected CRadioButton			m_rbtAutoLogon;
	/** 帐号列表 **/
	ArrayAdapter<String>			m_NromalItemAdapter;
	ListView						m_AccountsView;
	List<String>					m_szAccounts		= new ArrayList<String>();
	/** 保存帐号信息 **/
	protected List<tagAccountsDB>	m_AccountsInfoList	= new ArrayList<tagAccountsDB>();

	Paint							m_paint				= new Paint();
	public CLoginEngine(Context context) {
		super(context);
		setWillNotDraw(false);
		try {
			m_ImageTextBG = new CImageEx(ClientPlazz.RES_PATH + "login/text_bg.png");
			m_ImageInfoBG = new CImageEx(ClientPlazz.RES_PATH + "login/bg_info.png");
			m_ImageHeadBG = new CImageEx(ClientPlazz.RES_PATH + "login/bg_head.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 显示模式
		m_nShowMode = 0;
		// 登录等待
		m_ProBar = new ProgressBar(context);
		addView(m_ProBar);

		// 用户界面
		m_UserInfoView = new CUserInfoView(context);

		// 登录按钮
		m_btLogon = new CImageButton(context, ClientPlazz.RES_PATH + "login/bt_login.png");
		// 注册按钮
		m_btRegister = new CImageButton(context, ClientPlazz.RES_PATH + "login/bt_register.png");
		// 开始按钮
		m_btStart = new CImageButton(context, ClientPlazz.RES_PATH + "login/bt_startgame.png");
		// 切换按钮
		m_btChange = new CImageButton(context, ClientPlazz.RES_PATH + "login/bt_changname.png");
		// 自动按钮
		m_rbtAutoLogon = new CRadioButton(context, ClientPlazz.RES_PATH + "login/bg_tickoff.png", ClientPlazz.RES_PATH + "login/rbt_tickoff.png", "自动登录 ", 5);
		// 记录按钮
		m_rbtRecord = new CRadioButton(context, ClientPlazz.RES_PATH + "login/bg_tickoff.png", ClientPlazz.RES_PATH + "login/rbt_tickoff.png", "记住密码 ", 5);
		// 下拉按钮
		m_btListDown = new CImageButton(context, ClientPlazz.RES_PATH + "login/bt_down.png");
		// 上拉按钮
		m_btListUp = new CImageButton(context, ClientPlazz.RES_PATH + "login/bt_up.png");
		// 帐号输入
		m_EdAccounts = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, false);
		// 密码输入
		m_EdPassWord = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, true);

		// 文字设置

		m_rbtAutoLogon.setTextColor(Color.WHITE);
		m_rbtRecord.setTextColor(Color.WHITE);
		m_EdAccounts.setPadding(5, 5, 5, 1);
		m_EdPassWord.setPadding(5, 5, 5, 1);

		m_EdAccounts.setGravity(Gravity.BOTTOM);
		m_EdPassWord.setGravity(Gravity.BOTTOM);

		// 限制字数
		m_EdAccounts.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_ACCOUNTS)});
		m_EdPassWord.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_PASSWORD)});

		// 设置监听
		m_btLogon.setSingleClickListener(this);
		m_btRegister.setSingleClickListener(this);
		m_btChange.setSingleClickListener(this);
		m_btStart.setSingleClickListener(this);

		m_btListDown.setSingleClickListener(this);
		m_btListUp.setSingleClickListener(this);

		// 加入显示
		addView(m_btLogon);
		addView(m_btRegister);
		addView(m_btStart);
		addView(m_btChange);
		addView(m_EdAccounts);
		addView(m_EdPassWord);
		addView(m_UserInfoView);

		addView(m_rbtRecord);
		addView(m_rbtAutoLogon);
		addView(m_btListDown);
		addView(m_btListUp);

		m_paint.setAntiAlias(true);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				m_rbtAutoLogon.setTextSize(30);
				m_rbtRecord.setTextSize(30);
				m_paint.setTextSize(28);
				break;
			case DF.DEVICETYPE_HVGA :
				m_rbtAutoLogon.setTextSize(15);
				m_rbtRecord.setTextSize(15);
				m_paint.setTextSize(14);
				break;
			case DF.DEVICETYPE_QVGA :
				m_rbtAutoLogon.setTextSize(12);
				m_rbtRecord.setTextSize(12);
				m_paint.setTextSize(12);
				break;
		}

		// 加载信息
		LoadConfig();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				onLayoutH(changed, l, t, r, b);
				break;
			case DF.DEVICETYPE_HVGA :
				onLayoutM(changed, l, t, r, b);
				break;
			case DF.DEVICETYPE_QVGA :
				onLayoutL(changed, l, t, r, b);
				break;
		}
	}

	private void onLayoutL(boolean changed, int l, int t, int r, int b) {
		m_ptFram.set(ClientPlazz.SCREEN_WIDHT - 10 - m_ImageInfoBG.GetW(), ClientPlazz.SCREEN_HEIGHT / 2 - m_ImageInfoBG.GetH() / 2);

		int x = 0, y = 0;
		x = m_ptFram.x + m_ImageInfoBG.GetW() / 2 - 18;
		y = m_ptFram.y + 10;
		m_ProBar.layout(x, y, x + 36, y + 36);

		x = m_ptFram.x + 40;
		y = m_ptFram.y + 30;
		m_EdAccounts.layout(x, y, x + m_ImageTextBG.GetW(), y + m_ImageTextBG.GetH());
		m_EdPassWord.layout(x, y + m_ImageTextBG.GetH() + 10, x + m_ImageTextBG.GetW(), y + 2 * m_ImageTextBG.GetH() + 10);
		x = m_ptFram.x + 40 + m_ImageTextBG.GetW();
		y = m_ptFram.y + 30 + m_ImageTextBG.GetH() / 2 - m_btListDown.getH() / 2;
		m_btListDown.layout(x, y, 0, 0);
		m_btListUp.layout(x, y, 0, 0);

		x = m_ptFram.x + (m_ImageInfoBG.GetW() / 2 - m_btStart.getW() / 2);
		y = m_ptFram.y + 105;
		m_btLogon.layout(x, y, 0, 0);
		m_btStart.layout(x, y, 0, 0);
		m_btRegister.layout(x, y + 5 + m_btStart.getH(), 0, 0);
		m_btChange.layout(x, y + 5 + m_btStart.getH(), 0, 0);

		x = m_ptFram.x + 10;
		y = m_ptFram.y + m_ImageInfoBG.GetH() - 5 - m_rbtRecord.getH();
		m_rbtRecord.layout(x, y, 0, 0);
		x = m_ptFram.x + m_ImageInfoBG.GetW() - m_rbtRecord.getW() - 5;
		m_rbtAutoLogon.layout(x, y, 0, 0);
	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		m_ptFram.set(ClientPlazz.SCREEN_WIDHT - 10 - m_ImageInfoBG.GetW(), ClientPlazz.SCREEN_HEIGHT / 2 - m_ImageInfoBG.GetH() / 2);

		int x = 0, y = 0;
		x = m_ptFram.x + m_ImageInfoBG.GetW() / 2 - 18;
		y = m_ptFram.y + 10;
		m_ProBar.layout(x, y, x + 36, y + 36);

		x = m_ptFram.x + 60;
		y = m_ptFram.y + 50;
		m_EdAccounts.layout(x, y, x + m_ImageTextBG.GetW(), y + m_ImageTextBG.GetH());
		m_EdPassWord.layout(x, y + m_ImageTextBG.GetH() + 10, x + m_ImageTextBG.GetW(), y + 2 * m_ImageTextBG.GetH() + 10);
		x = m_ptFram.x + 65 + m_ImageTextBG.GetW();
		y = m_ptFram.y + 50 + m_ImageTextBG.GetH() / 2 - m_btListDown.getH() / 2;
		m_btListDown.layout(x, y, 0, 0);
		m_btListUp.layout(x, y, 0, 0);

		x = m_ptFram.x + (m_ImageInfoBG.GetW() / 2 - m_btStart.getW() / 2);
		y = m_ptFram.y + 140;
		m_btLogon.layout(x, y, 0, 0);
		m_btStart.layout(x, y, 0, 0);
		m_btRegister.layout(x, y + 5 + m_btStart.getH(), 0, 0);
		m_btChange.layout(x, y + 5 + m_btStart.getH(), 0, 0);

		x = m_ptFram.x + 10;
		y = m_ptFram.y + m_ImageInfoBG.GetH() - 10 - m_rbtRecord.getH();
		m_rbtRecord.layout(x, y, 0, 0);
		x = m_ptFram.x + m_ImageInfoBG.GetW() - m_rbtRecord.getW() - 5;
		m_rbtAutoLogon.layout(x, y, 0, 0);

	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		m_ptFram.set(ClientPlazz.SCREEN_WIDHT - 10 - m_ImageInfoBG.GetW(), ClientPlazz.SCREEN_HEIGHT / 2 - m_ImageInfoBG.GetH() / 2);

		int x = 0, y = 0;
		x = m_ptFram.x + m_ImageInfoBG.GetW() / 2 - 24;
		y = m_ptFram.y + 30;
		m_ProBar.layout(x, y, x + 48, y + 48);

		x = m_ptFram.x + 85;
		y = m_ptFram.y + 60;
		m_EdAccounts.layout(x, y, x + m_ImageTextBG.GetW(), y + m_ImageTextBG.GetH());
		m_EdPassWord.layout(x, y + m_ImageTextBG.GetH() + 10, x + m_ImageTextBG.GetW(), y + 2 * m_ImageTextBG.GetH() + 10);
		x = m_ptFram.x + 85 + m_ImageTextBG.GetW();
		y = m_ptFram.y + 60 + m_ImageTextBG.GetH() / 2 - m_btListDown.getH() / 2;
		m_btListDown.layout(x, y, 0, 0);
		m_btListUp.layout(x, y, 0, 0);

		x = m_ptFram.x + (m_ImageInfoBG.GetW() / 2 - m_btStart.getW() / 2);
		y = m_ptFram.y + 200;
		m_btLogon.layout(x, y, 0, 0);
		m_btStart.layout(x, y, 0, 0);
		m_btRegister.layout(x, y + 10 + m_btStart.getH(), 0, 0);
		m_btChange.layout(x, y + 10 + m_btStart.getH(), 0, 0);

		x = m_ptFram.x + 10;
		y = m_ptFram.y + m_ImageInfoBG.GetH() - 10 - m_rbtRecord.getH();
		m_rbtRecord.layout(x, y, 0, 0);
		x = m_ptFram.x + m_ImageInfoBG.GetW() - m_rbtRecord.getW() - 5;
		m_rbtAutoLogon.layout(x, y, 0, 0);

	}

	@Override
	public void ActivateView() {
		ShowListView(false);
	}

	@Override
	public void OnDestoryRes() {
		m_btLogon.setVisibility(INVISIBLE);
		m_btRegister.setVisibility(INVISIBLE);

		m_rbtAutoLogon.setVisibility(INVISIBLE);
		m_rbtRecord.setVisibility(INVISIBLE);

		removeView(m_AccountsView);
		m_btListDown.setVisibility(INVISIBLE);
		m_btListUp.setVisibility(INVISIBLE);

		ClientPlazz.DestoryBackGround(m_EdAccounts);
		ClientPlazz.DestoryBackGround(m_EdPassWord);
		m_ImageInfoBG.OnReleaseImage();
		m_ImageTextBG.OnReleaseImage();
		m_ImageHeadBG.OnReleaseImage();

	}

	@Override
	public void OnInitRes() {

		// 背景
		setBackgroundDrawable(new BitmapDrawable(new CImage(getContext(), ClientPlazz.RES_PATH + "login/bg_login.png", null, true).GetBitMap()));

		// 加载资源
		try {
			m_ImageInfoBG.OnReLoadImage();
			m_ImageTextBG.OnReLoadImage();
			m_ImageHeadBG.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapDrawable drawable = new BitmapDrawable(m_ImageTextBG.GetBitMap());
		m_EdAccounts.setBackgroundDrawable(drawable);
		m_EdPassWord.setBackgroundDrawable(drawable);

		OnInitSystem();
	}

	/** 系统配置 **/
	private void OnInitSystem() {
		IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
		if (kernel != null) {
			// 关闭链接
			kernel.IntemitConnect();
			kernel.SetSocketReadMode(0);
			// 清除个人信息
			IUserManageSkin usermanage = kernel.GetUserManage();
			if (usermanage != null) {
				usermanage.ReleaseUserItem(false);
			}
			// 重布界面
			m_nShowMode = 0;
			OnShowMode(m_nShowMode);
		}

		ClientPlazz.GetPlazzInstance().SetLogonInfo("", "", false, false);
		ClientPlazz.FAST_ENTER = false;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		// 按键ID
		int id = view.getId();
		if (id == m_btLogon.getId()) {
			removeView(m_AccountsView);
			m_LoginPack = OnLoginPack();
			if (m_LoginPack != null) {
				SendSocketLogin();
			}
			return true;
		}
		// 注册响应
		else if (id == m_btRegister.getId()) {
			removeView(m_AccountsView);
			PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_REGISTER, null);
			return true;
		}
		// 显示帐号列表
		else if (id == m_btListDown.getId()) {
			ShowListView(true);
			return true;
		}
		// 关闭帐号列表
		else if (id == m_btListUp.getId()) {
			ShowListView(false);
			return true;
		}
		// 开始游戏
		else if (id == m_btStart.getId()) {
			OnShowServerList();
			return true;
		} else if (id == m_btChange.getId()) {
			OnChangeAccounts();
			return true;
		}
		return false;
	}

	private void OnChangeAccounts() {
		Log.d("登陆界面", "切换帐号");
		OnInitSystem();
	}

	@Override
	protected void Render(Canvas canvas) {
		m_ImageInfoBG.DrawImage(canvas, m_ptFram.x, m_ptFram.y);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				onRenderH(canvas);
				break;
			case DF.DEVICETYPE_HVGA :
				onRenderM(canvas);
				break;
			case DF.DEVICETYPE_QVGA :
				onRenderL(canvas);
				break;
		}
	}

	private void onRenderL(Canvas canvas) {
		switch (m_nShowMode) {
			case 0 :
				m_paint.setTextAlign(Align.LEFT);
				m_paint.setColor(Color.WHITE);
				m_paint.setStrokeWidth(4);
				CText.DrawTextEllip(canvas, "帐号", m_ptFram.x + 10, m_ptFram.y + 35, 85, m_paint);

				CText.DrawTextEllip(canvas, "密码", m_ptFram.x + 10, m_ptFram.y + 70, 85, m_paint);

				break;
			case 1 :
				if (m_szLoginMsg != null && !m_szLoginMsg.equals("")) {
					m_paint.setTextAlign(Align.CENTER);

					m_paint.setColor(Color.GREEN);
					CText.DrawTextEllip(canvas, m_szLoginMsg, m_ptFram.x + m_ImageInfoBG.GetW() / 2, m_ptFram.y + 50, 160, m_paint);
				}
				break;
			case 2 :
				m_paint.setTextAlign(Align.LEFT);

				m_paint.setColor(Color.YELLOW);
				m_paint.setStrokeWidth(8);

				IClientUserItem useritem = ClientPlazz.GetKernel().GetMeUserItem();
				m_ImageHeadBG.DrawImage(canvas, m_ptFram.x + 15, m_ptFram.y + 35);
				CPlazzGraphics graphics = CPlazzGraphics.onCreate();
				graphics.DrawUserAvatar(canvas, m_ptFram.x + 15 + 5, m_ptFram.y + 35 + 5, useritem.GetFaceID());
				if (useritem != null && useritem.GetUserID() != 0) {
					m_paint.setColor(Color.YELLOW);
					CText.DrawTextEllip(canvas, useritem.GetNickName(), m_ptFram.x + 65, m_ptFram.y + 40, 90, m_paint);

					CText.DrawTextEllip(canvas, "Exp:" + useritem.GetUserExperience() + "", m_ptFram.x + 65, m_ptFram.y + 60, 90, m_paint);
				}
				break;
		}

	}

	private void onRenderM(Canvas canvas) {
		switch (m_nShowMode) {
			case 0 :
				m_paint.setTextAlign(Align.LEFT);
				m_paint.setColor(Color.WHITE);
				m_paint.setStrokeWidth(4);
				CText.DrawTextEllip(canvas, "帐号", m_ptFram.x + 15, m_ptFram.y + 58, 85, m_paint);

				CText.DrawTextEllip(canvas, "密码", m_ptFram.x + 15, m_ptFram.y + 97, 85, m_paint);

				break;
			case 1 :
				if (m_szLoginMsg != null && !m_szLoginMsg.equals("")) {
					m_paint.setTextAlign(Align.CENTER);

					m_paint.setColor(Color.GREEN);
					CText.DrawTextEllip(canvas, m_szLoginMsg, m_ptFram.x + m_ImageInfoBG.GetW() / 2, m_ptFram.y + 50, 200, m_paint);
				}
				break;
			case 2 :
				m_paint.setTextAlign(Align.LEFT);

				m_paint.setColor(Color.YELLOW);
				m_paint.setStrokeWidth(8);

				IClientUserItem useritem = ClientPlazz.GetKernel().GetMeUserItem();
				m_ImageHeadBG.DrawImage(canvas, m_ptFram.x + 15, m_ptFram.y + 35);
				CPlazzGraphics graphics = CPlazzGraphics.onCreate();
				graphics.DrawUserAvatar(canvas, m_ptFram.x + 15 + 7, m_ptFram.y + 35 + 7, useritem.GetFaceID());
				if (useritem != null && useritem.GetUserID() != 0) {
					m_paint.setColor(Color.YELLOW);
					CText.DrawTextEllip(canvas, useritem.GetNickName(), m_ptFram.x + 75, m_ptFram.y + 35, 150, m_paint);

					CText.DrawTextEllip(canvas, "Exp:" + useritem.GetUserExperience() + "", m_ptFram.x + 75, m_ptFram.y + 70, 150, m_paint);
				}
				break;

		}

	}

	private void onRenderH(Canvas canvas) {
		switch (m_nShowMode) {
			case 0 :
				m_paint.setTextAlign(Align.LEFT);
				m_paint.setColor(Color.WHITE);
				m_paint.setStrokeWidth(4);
				CText.DrawTextEllip(canvas, "帐号", m_ptFram.x + 15, m_ptFram.y + 70, 85, m_paint);

				CText.DrawTextEllip(canvas, "密码", m_ptFram.x + 15, m_ptFram.y + 130, 85, m_paint);

				break;
			case 1 :
				if (m_szLoginMsg != null && !m_szLoginMsg.equals("")) {
					m_paint.setTextAlign(Align.CENTER);

					m_paint.setColor(Color.GREEN);
					CText.DrawTextEllip(canvas, m_szLoginMsg, m_ptFram.x + m_ImageInfoBG.GetW() / 2, m_ptFram.y + 90, 200, m_paint);
				}
				break;
			case 2 :
				m_paint.setTextAlign(Align.LEFT);

				m_paint.setColor(Color.YELLOW);
				m_paint.setStrokeWidth(8);

				IClientUserItem useritem = ClientPlazz.GetKernel().GetMeUserItem();
				m_ImageHeadBG.DrawImage(canvas, m_ptFram.x + 15, m_ptFram.y + 65);
				CPlazzGraphics graphics = CPlazzGraphics.onCreate();
				graphics.DrawUserAvatar(canvas, m_ptFram.x + 15 + 12, m_ptFram.y + 65 + 12, useritem.GetFaceID());
				if (useritem != null && useritem.GetUserID() != 0) {
					m_paint.setColor(Color.YELLOW);
					CText.DrawTextEllip(canvas, useritem.GetNickName(), m_ptFram.x + 125, m_ptFram.y + 70, 185, m_paint);

					CText.DrawTextEllip(canvas, useritem.GetUserExperience() + "", m_ptFram.x + 125, m_ptFram.y + 130, 185, m_paint);
				}
				break;

		}

	}

	/**
	 * 创建登录数据包
	 * 
	 * @return 登录数据包
	 */
	public CPackMessage OnLoginPack() {
		CPackMessage sendpackge = new CPackMessage();
		// 字符判断
		String szAccounts = m_EdAccounts.getEditableText().toString();
		if (szAccounts == null || szAccounts.equals("")) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "请输入帐号", Toast.LENGTH_SHORT).show();
			return null;
		}

		try {
			if (szAccounts.getBytes("gb2312").length < 6) {
				Toast.makeText(ClientPlazz.GetPlazzInstance(), "帐号名字的长度最短为6位字符，请重新输入", Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "输入有误，请重新输入", Toast.LENGTH_SHORT).show();
			return null;
		}
		String szPassWord = m_EdPassWord.getEditableText().toString();
		if (szPassWord == null || szPassWord.equals("")) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "请输入密码", Toast.LENGTH_SHORT).show();
			return null;
		}

		try {
			if (szPassWord.getBytes("gb2312").length < 6) {
				Toast.makeText(ClientPlazz.GetPlazzInstance(), "游戏密码长度最短为6位字符，请重新输入", Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "输入有误，请重新输入", Toast.LENGTH_SHORT).show();
			return null;
		}

		CMD_MB_LogonByAccounts cmd = new CMD_MB_LogonByAccounts();
		cmd.nModuleID = ClientPlazz.GetKernel().GetGameAttribute().KindId;
		cmd.lPlazaVersion = ClientPlazz.GetKernel().GetGameAttribute().ProcesVersion;
		cmd.cbDeviceYype = (byte) ClientPlazz.GetGlobalUnits().GetDeviceType();
		cmd.szPassWord = szPassWord;
		cmd.szAccounts = szAccounts;
		cmd.szMachineID = PDF.GetPhoneIMEI();
		cmd.szMobilePhone = PDF.GetPhoneNum();

		sendpackge.main = NetCommand.MDM_MB_LOGON;
		sendpackge.sub = NetCommand.SUB_MB_LOGON_ACCOUNTS;
		sendpackge.Obj = cmd;

		return sendpackge;
	}

	@Override
	public void MainMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
		/** 登陆信息 **/
			case NetCommand.MDM_MB_LOGON : {
				if (sub == NetCommand.SUB_MB_LOGON_FAILURE) {
					OnLogonFailure(obj);
				} else if (sub == NetCommand.SUB_MB_LOGON_SUCCESS) {
					OnLogonSucceed(obj);
				}
				break;
			}
			/** 服务列表信息 **/
			case NetCommand.MDM_MB_SERVER_LIST : {
				if (sub == NetCommand.SUB_MB_LIST_FINISH) {
					OnLoginFinish();
				} else if (sub == NetCommand.SUB_MB_LIST_SERVER) {
					boolean bSucceed = ClientPlazz.GetServerEngine().onSubServerList((byte[]) obj);
					if (!bSucceed) {
						CMD_MB_LogonFailure cmd = new CMD_MB_LogonFailure();
						cmd.lErrorCode = 0;
						cmd.szDescribeString = "获取列表失败";
						OnLogonFailure(cmd);
					}
					break;
				}
			}
			default :
				break;
		}

	}

	/**
	 * 帐号列表显示
	 * 
	 * @param show
	 */
	private void ShowListView(boolean show) {

		m_btListDown.setVisibility(show ? INVISIBLE : VISIBLE);
		m_btListUp.setVisibility(show ? VISIBLE : INVISIBLE);
		if (show) {
			// 帐号列表
			m_AccountsView = new ListView(DF.GetContext());
			m_AccountsView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			// 属性设置
			GradientDrawable gd = new GradientDrawable();

			gd.setCornerRadius(4);
			gd.setColor(0xffffffff);
			m_AccountsView.setBackgroundDrawable(gd);
			m_AccountsView.setCacheColorHint(0);
			m_AccountsView.setVerticalFadingEdgeEnabled(false);
			m_AccountsView.setVerticalScrollBarEnabled(false);
			m_AccountsView.setOnItemLongClickListener(this);
			m_AccountsView.setOnItemClickListener(this);
			m_AccountsView.setClipChildren(false);
			m_NromalItemAdapter = new ArrayAdapter<String>(DF.GetContext(), R.layout.accountslistitem, m_szAccounts);
			m_AccountsView.setAdapter(m_NromalItemAdapter);
			addView(m_AccountsView);
			int x = m_EdAccounts.getLeft();
			int y = m_EdAccounts.getTop() + m_ImageTextBG.GetH();
			m_AccountsView.layout(x, y, x + m_ImageTextBG.GetW(), y + m_ImageTextBG.GetH() * 4);

		} else {
			removeView(m_AccountsView);
		}

	}

	/** 显示服务列表 **/
	private void OnShowServerList() {
		PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_SERVER, null);
	}

	/** 快速游戏 **/
	private boolean OnFastPlayGame() {
		ClientPlazz.FAST_ENTER = true;
		return ClientPlazz.GetServerEngine().onQuickEnterRoom();
	}

	/**
	 * 加载配置
	 */
	private void LoadConfig() {
		// 读取数据库 加载帐号信息
		CDatabaseService db = ClientPlazz.GetPlazzInstance().GetDBService();
		Cursor cursor = db.GetDBCursor();
		m_AccountsInfoList.clear();
		m_szAccounts.clear();
		Log.w("登陆界面", "************加载帐号************");
		int index = -1;
		if (cursor != null) {
			while (!cursor.isAfterLast()) {
				if (index == -1) {
					index = 0;
				}

				tagAccountsDB accountsinfo = new tagAccountsDB();
				accountsinfo.m_szAccounts = cursor.getString(cursor.getColumnIndex("accounts"));
				accountsinfo.m_lLastLogon = Long.parseLong(cursor.getString(cursor.getColumnIndex("lastlogon")));
				accountsinfo.m_nAutoLogon = cursor.getInt(cursor.getColumnIndex("autologon"));
				accountsinfo.m_szPass = cursor.getString(cursor.getColumnIndex("password"));
				m_AccountsInfoList.add(accountsinfo);
				m_szAccounts.add(accountsinfo.m_szAccounts);
				if (accountsinfo.m_lLastLogon > m_AccountsInfoList.get(index).m_lLastLogon) {
					index = m_AccountsInfoList.size() - 1;
				}

				Log.d("登陆界面", "*加载帐号-" + accountsinfo.m_szAccounts + "||" + accountsinfo.m_szPass + "*自动登陆-" + (m_rbtAutoLogon.GetTickOff() ? 1 : 0) + "*记住密码:"
						+ (m_rbtRecord.GetTickOff() ? 1 : 0));

				Log.d("LoadAccounts", accountsinfo.m_szAccounts);
				cursor.moveToNext();
			}
			cursor.close();
			if (m_AccountsInfoList.size() > 0 && index != -1) {
				Log.d("登陆界面", "最后位置" + index);
				tagAccountsDB accountsinfo = m_AccountsInfoList.get(index);
				if (accountsinfo != null) {
					m_EdAccounts.setText(accountsinfo.m_szAccounts);
					m_EdPassWord.setText(accountsinfo.m_szPass);
					m_rbtAutoLogon.SetTickOff(accountsinfo.m_nAutoLogon == 1);
					if (accountsinfo.m_szPass != null && !accountsinfo.m_szPass.equals("")) {
						m_rbtRecord.SetTickOff(true);
					} else {
						m_rbtRecord.SetTickOff(false);
					}
				}
			}
		}
	}

	/**
	 * 更新配置
	 */
	public boolean SaveConfig() {
		Log.d("登陆界面", "更新数据库记录");
		CDatabaseService db = ClientPlazz.GetPlazzInstance().GetDBService();
		if (db == null) {
			return false;
		}
		db.DeletDataAll(CSQLHelp.TABLE_NAME);
		// 更新帐号信息
		for (tagAccountsDB dbinfo : m_AccountsInfoList) {
			db.SaveTableLogon(dbinfo);
		}
		return true;
	}

	/** 登陆失败 **/
	public void OnLogonFailure(Object obj) {
		OnInitSystem();
		CMD_MB_LogonFailure cmd = (CMD_MB_LogonFailure) obj;
		Toast.makeText(PDF.GetContext(), cmd.szDescribeString, Toast.LENGTH_SHORT).show();
	}

	/** 登录成功 **/
	public void OnLogonSucceed(Object obj) {
		m_szLoginMsg = "登录成功，获取信息中...";
		ClientPlazz.FAST_ENTER = ClientPlazz.GetPlazzInstance().IsAutoLogin();
		postInvalidate();
	}

	/** 登录完成 **/
	public void OnLoginFinish() {
		OnRecordAccount();
		if (ClientPlazz.GetPlazzInstance().IsAutoLogin()) {
			if (OnFastPlayGame() == false)
				OnShowMode(2);
		} else {
			OnShowMode(2);
		}
		SaveConfig();
	}

	/** 记录帐号 **/
	private void OnRecordAccount() {
		ClientPlazz plazz = ClientPlazz.GetPlazzInstance();
		String szaccounts = plazz.GetLogonAccounts();
		String szpassword = plazz.GetLogonPassWord();
		Log.d("登陆界面", "更新及记录帐号");
		if (szaccounts != null && !szaccounts.equals("")) {
			// 更新信息
			boolean bUpdata = false;
			tagAccountsDB accountsinfo = null;
			for (int i = 0; i < m_AccountsInfoList.size(); i++) {
				accountsinfo = m_AccountsInfoList.get(i);
				if (szaccounts.equals(accountsinfo.m_szAccounts)) {
					accountsinfo.m_lLastLogon = System.currentTimeMillis();
					accountsinfo.m_nAutoLogon = plazz.IsAutoLogin() ? 1 : 0;
					accountsinfo.m_szPass = (plazz.IsSavePassWord() ? szpassword : "");
					bUpdata = true;
					Log.d("登陆界面", "*更新帐号-" + accountsinfo.m_szAccounts + "||" + accountsinfo.m_szPass + "*自动登陆-" + (m_rbtAutoLogon.GetTickOff() ? 1 : 0)
							+ "*记住密码:" + (m_rbtRecord.GetTickOff() ? 1 : 0));
					break;
				}
			}

			// 新帐号信息
			if (!bUpdata) {
				accountsinfo = new tagAccountsDB();
				accountsinfo.m_szAccounts = szaccounts;
				accountsinfo.m_nAutoLogon = (m_rbtAutoLogon.GetTickOff() ? 1 : 0);
				accountsinfo.m_szPass = (m_rbtRecord.GetTickOff() ? szpassword : "");
				m_AccountsInfoList.add(accountsinfo);
				m_szAccounts.add(accountsinfo.m_szAccounts);
				m_EdAccounts.setText(accountsinfo.m_szAccounts);
				m_EdPassWord.setText(accountsinfo.m_szPass);
				Log.d("登陆界面", "*添加帐号-" + accountsinfo.m_szAccounts + "||" + accountsinfo.m_szPass + "*自动登陆-" + (m_rbtAutoLogon.GetTickOff() ? 1 : 0) + "*记住密码:"
						+ (m_rbtRecord.GetTickOff() ? 1 : 0));
			}
		}

	}

	/** 显示模式 **/
	private void OnShowMode(int mode) {
		m_nShowMode = mode;
		setClickable(m_nShowMode != 1);
		m_btLogon.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);
		m_btRegister.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);

		m_rbtAutoLogon.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);
		m_rbtRecord.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);

		m_EdAccounts.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);
		m_EdPassWord.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);

		removeView(m_AccountsView);
		m_btListDown.setVisibility(m_nShowMode == 0 ? VISIBLE : INVISIBLE);
		m_btListUp.setVisibility(INVISIBLE);

		m_btStart.setVisibility(m_nShowMode == 2 ? VISIBLE : INVISIBLE);
		m_btChange.setVisibility(m_nShowMode == 2 ? VISIBLE : INVISIBLE);
		m_UserInfoView.setVisibility(m_nShowMode == 2 ? VISIBLE : INVISIBLE);

		m_ProBar.setVisibility(m_nShowMode == 1 ? VISIBLE : INVISIBLE);

		postInvalidate(m_ptFram.x, m_ptFram.y, m_ptFram.x + m_ImageInfoBG.GetW(), m_ptFram.y + m_ImageInfoBG.GetH());
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int postion, long arg3) {
		if (postion < m_szAccounts.size()) {
			ShowListView(false);
			DF.SendMainMessage(ClientPlazz.MM_QUERY_DELETEACCOUNT, 0, m_szAccounts.get(postion));
			return true;
		}

		return false;
	}

	/** 删除帐号 **/
	public void DeleteAccount(String szaccount) {

		for (int i = 0; i < m_AccountsInfoList.size(); i++) {
			tagAccountsDB accountsinfo = m_AccountsInfoList.get(i);
			if (szaccount.equals(accountsinfo.m_szAccounts)) {
				m_AccountsInfoList.remove(i);
				break;
			}
		}

		for (int i = 0; i < m_szAccounts.size(); i++) {
			if (szaccount.equals(m_szAccounts.get(i))) {
				m_szAccounts.remove(i);
				break;
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int postion, long arg3) {
		ShowListView(false);
		if (postion < m_szAccounts.size()) {
			for (int i = 0; i < m_AccountsInfoList.size(); i++) {

				tagAccountsDB accountsinfo = m_AccountsInfoList.get(i);
				if (accountsinfo.m_szAccounts == null || accountsinfo.m_szAccounts.equals(""))
					continue;
				if (!accountsinfo.m_szAccounts.equals(m_szAccounts.get(postion)))
					continue;

				m_EdAccounts.setText(accountsinfo.m_szAccounts);
				m_EdPassWord.setText(accountsinfo.m_szPass);
				m_rbtAutoLogon.SetTickOff(accountsinfo.m_nAutoLogon == 1);
				if (accountsinfo.m_szPass != null && !accountsinfo.m_szPass.equals("")) {
					m_rbtRecord.SetTickOff(true);
				} else {
					m_rbtRecord.SetTickOff(false);
				}

				break;
			}
		}

	}

	/** 连接服务器 **/
	private boolean ConnectServer() {
		OnShowMode(1);
		m_szLoginMsg = "正在链接服务器...";

		boolean bSucceed = false;
		IClientKernel clientkernel = ClientPlazz.GetKernel();
		bSucceed = clientkernel.CreateConnect(ClientPlazz.LOGIN_URL, ClientPlazz.LOGIN_PORT);
		if (!bSucceed) {
			CMD_MB_LogonFailure cmd = new CMD_MB_LogonFailure();
			cmd.lErrorCode = 0;
			cmd.szDescribeString = "建立链接失败，请检测网络设置!";
			PDF.SendMainMessage(NetCommand.MDM_MB_LOGON, NetCommand.SUB_MB_LOGON_FAILURE, this.GetTag(), cmd);
		}
		return bSucceed;
	}

	/** 发送注册 **/
	public void SendSocketRegister(CPackMessage sendpackge) {
		ClientPlazz.GetPlazzInstance().OnShowLoginView();
		if (!ConnectServer()) {
			return;
		}
		ClientPlazz.GetPlazzInstance().SetLogonInfo(((CMD_MB_RegisterAccounts) sendpackge.Obj).szAccounts,
				((CMD_MB_RegisterAccounts) sendpackge.Obj).szLogonPass, true, true);
		PDF.SendSubMessage(ClientPlazz.SEND_TO_SERVER, 0, sendpackge);

	}

	/** 发送登陆 **/
	public void SendSocketLogin() {

		if (!ConnectServer()) {
			return;
		}
		ClientPlazz.GetPlazzInstance().SetLogonInfo(((CMD_MB_LogonByAccounts) m_LoginPack.Obj).szAccounts,
				((CMD_MB_LogonByAccounts) m_LoginPack.Obj).szPassWord, m_rbtRecord.GetTickOff(), m_rbtAutoLogon.GetTickOff());
		PDF.SendSubMessage(ClientPlazz.SEND_TO_SERVER, 0, m_LoginPack);
	}

}
