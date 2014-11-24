package com.qp.land_h.plazz.Plazz_Fram.Bank;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ButtonInterface.IClickedChangeListener;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Lib_System.View.ButtonView.CRadioButton;
import Lib_System.View.ButtonView.CRadioGroup;
import Net_Interface.ICmd;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.EmptyEditText;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.Plazz_Interface.IInsureCounterAction;
import com.qp.land_h.plazz.Plazz_Struct.tagUserInsureInfo;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_C_QuerInsureInfoRequest;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_C_SaveScoreRequest;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_C_TakeScoreRequest;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_C_TransferScoreRequest;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureFailure;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureInfo;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureSuccess;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public class CBankEx extends CViewEngine implements ISingleClickListener, IInsureCounterAction, IClickedChangeListener, IMainMessage, IRangeObtain {

	public final static int		HIDE_MODE			= 0;						// 隐藏模式
	public final static int		TAKE_SAVE_MODE		= 1;						// 存取模式
	public final static int		TRANSFER_MODE		= 2;						// 转账模式

	protected CImageEx			m_ImageBack;
	protected CImageEx			m_ImageTextBG;

	protected EmptyEditText		m_EdPassWord;
	protected EmptyEditText		m_EdNickName;
	protected EmptyEditText		m_EdUserID;
	protected EmptyEditText		m_EdScore;

	protected CSelectButton		m_btTakeSaveSelect;
	protected CSelectButton		m_btTransferSelect;

	protected CImageButton		m_btSave;
	protected CImageButton		m_btTake;
	protected CImageButton		m_btTransfer;
	protected CImageButton		m_btClose;
	protected CImageButton		m_btFresh;

	// 单选按钮
	protected CRadioGroup		m_TransferModeGroup;							// 单选控制
	protected CRadioButton		m_rbtByID;										// 依据ID
	protected CRadioButton		m_rbtByNickName;								// 依据昵称

	/** 等待进度 **/
	protected ProgressBar		m_ProBar;

	protected tagUserInsureInfo	m_UserInsureInfo	= new tagUserInsureInfo();

	protected Paint				m_paint				= new Paint();

	public CBankEx(Context context) {
		super(context);
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "bank/bg_bank.png");
			m_ImageTextBG = new CImageEx(ClientPlazz.RES_PATH + "bank/bg_txt.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_ProBar = new ProgressBar(context);

		m_rbtByID = new CRadioButton(context, ClientPlazz.RES_PATH + "bank/bg_tickoff.png", ClientPlazz.RES_PATH + "bank/rbt_tickoff.png", "依据ID");
		m_rbtByNickName = new CRadioButton(context, ClientPlazz.RES_PATH + "bank/bg_tickoff.png", ClientPlazz.RES_PATH + "bank/rbt_tickoff.png", "依据昵称");
		m_TransferModeGroup = new CRadioGroup(this);

		m_btTakeSaveSelect = new CSelectButton(context, ClientPlazz.RES_PATH + "bank/bt_save_take.png");
		m_btTransferSelect = new CSelectButton(context, ClientPlazz.RES_PATH + "bank/bt_transfer.png");

		m_btTake = new CImageButton(context, ClientPlazz.RES_PATH + "bank/bt_take.png");
		m_btSave = new CImageButton(context, ClientPlazz.RES_PATH + "bank/bt_save.png");
		m_btTransfer = new CImageButton(context, ClientPlazz.RES_PATH + "bank/bt_transfer_1.png");
		m_btClose = new CImageButton(context, ClientPlazz.RES_PATH + "bank/bt_close.png");
		m_btFresh = new CImageButton(context, ClientPlazz.RES_PATH + "bank/bt_fresh.png");

		m_EdNickName = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, false);
		m_EdUserID = new EmptyEditText(context, true, InputType.TYPE_CLASS_NUMBER, false);
		m_EdScore = new EmptyEditText(context, true, InputType.TYPE_CLASS_NUMBER, false);
		m_EdPassWord = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, true);

		m_EdNickName.setTextColor(Color.GREEN);
		m_EdUserID.setTextColor(Color.GREEN);
		m_EdScore.setTextColor(Color.GREEN);
		m_EdPassWord.setTextColor(Color.GREEN);

		m_EdNickName.setPadding(5, 5, 5, 1);
		m_EdUserID.setPadding(5, 5, 5, 1);
		m_EdScore.setPadding(5, 5, 5, 1);
		m_EdPassWord.setPadding(5, 5, 5, 1);

		m_EdPassWord.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_PASSWORD)});
		m_EdNickName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_NICENAME)});
		m_EdUserID.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
		m_EdScore.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});

		m_btTake.setSingleClickListener(this);
		m_btSave.setSingleClickListener(this);
		m_btTransfer.setSingleClickListener(this);
		m_btClose.setSingleClickListener(this);
		m_btFresh.setSingleClickListener(this);
		m_btTakeSaveSelect.setSingleClickListener(this);
		m_btTransferSelect.setSingleClickListener(this);

		// 设置监听
		m_TransferModeGroup.onBindingRadio(m_rbtByID);
		m_TransferModeGroup.onBindingRadio(m_rbtByNickName);

		m_rbtByID.setTextColor(Color.rgb(250, 250, 0));
		m_rbtByNickName.setTextColor(Color.rgb(250, 250, 0));

		addView(m_btTake);
		addView(m_btSave);
		addView(m_btTransfer);
		addView(m_btClose);
		addView(m_btFresh);

		addView(m_EdNickName);
		addView(m_EdUserID);
		addView(m_EdScore);
		addView(m_EdPassWord);

		addView(m_ProBar);

		addView(m_rbtByID);
		addView(m_rbtByNickName);

		addView(m_btTakeSaveSelect);
		addView(m_btTransferSelect);

		m_paint.setStrokeWidth(10);
		m_paint.setAntiAlias(true);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				m_rbtByID.setTextSize(26);
				m_rbtByNickName.setTextSize(26);
				break;
			case DF.DEVICETYPE_HVGA :
				m_rbtByID.setTextSize(16);
				m_rbtByNickName.setTextSize(16);
				break;
			case DF.DEVICETYPE_QVGA :
				m_rbtByID.setTextSize(12);
				m_rbtByNickName.setTextSize(12);
				break;
		}
	}

	@Override
	public void OnDestoryRes() {
		ClientPlazz.DestoryBackGround(this);
		m_btClose.setVisibility(INVISIBLE);
		m_btFresh.setVisibility(INVISIBLE);

		m_btSave.setVisibility(INVISIBLE);
		m_btTake.setVisibility(INVISIBLE);
		m_btTransfer.setVisibility(INVISIBLE);

		m_rbtByID.setVisibility(INVISIBLE);
		m_rbtByNickName.setVisibility(INVISIBLE);

		m_EdPassWord.setVisibility(INVISIBLE);
		m_EdNickName.setVisibility(INVISIBLE);
		m_EdUserID.setVisibility(INVISIBLE);
		m_EdScore.setVisibility(INVISIBLE);

		m_btTakeSaveSelect.setVisibility(INVISIBLE);
		m_btTransferSelect.setVisibility(INVISIBLE);

		ClientPlazz.DestoryBackGround(m_EdNickName);
		ClientPlazz.DestoryBackGround(m_EdUserID);
		ClientPlazz.DestoryBackGround(m_EdScore);
		ClientPlazz.DestoryBackGround(m_EdPassWord);

		m_ImageBack.OnReleaseImage();
		m_ImageTextBG.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();
			m_ImageTextBG.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBackgroundDrawable(new BitmapDrawable(m_ImageBack.GetBitMap()));

		BitmapDrawable drawable = new BitmapDrawable(m_ImageTextBG.GetBitMap());

		m_EdNickName.setBackgroundDrawable(drawable);
		m_EdUserID.setBackgroundDrawable(drawable);
		m_EdScore.setBackgroundDrawable(drawable);
		m_EdPassWord.setBackgroundDrawable(drawable);
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
		int nx = 0;
		int ny = 0;

		m_btTakeSaveSelect.layout(5, 5, 0, 0);
		m_btTransferSelect.layout(5 + m_btTakeSaveSelect.getW(), 5, 0, 0);

		m_btClose.layout(m_ImageBack.GetW() - 10 - m_btClose.getW(), 5, 0, 0);
		m_btFresh.layout(215, 27, 0, 0);

		m_ProBar.layout(215, 27, 215 + m_btFresh.getW(), 27 + m_btFresh.getH());

		m_rbtByID.layout(10, 62, 0, 0);
		m_rbtByNickName.layout(80, 62, 0, 0);

		nx = 55;
		ny = 85;
		m_EdNickName.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		m_EdUserID.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = 55;
		ny += m_ImageTextBG.GetH() + 10;
		m_EdScore.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = 185;
		m_btSave.layout(nx, ny, 0, 0);

		nx = 55;
		ny += m_ImageTextBG.GetH() + 10;
		m_EdPassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = 185;
		m_btTake.layout(nx, ny, 0, 0);
		m_btTransfer.layout(nx, ny, 0, 0);

	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		int nx = 0;
		int ny = 0;

		m_btTakeSaveSelect.layout(3, 5, 0, 0);
		m_btTransferSelect.layout(3 + m_btTakeSaveSelect.getW(), 5, 0, 0);

		m_btClose.layout(m_ImageBack.GetW() - 15 - m_btClose.getW(), 5, 0, 0);
		m_btFresh.layout(m_ImageBack.GetW() - 60, 35, 0, 0);

		m_ProBar.layout(m_ImageBack.GetW() - 60, 35, m_ImageBack.GetW() - 60 + m_btFresh.getW(), 35 + m_btFresh.getH());

		m_rbtByID.layout(20, 85, 0, 0);
		m_rbtByNickName.layout(120, 85, 0, 0);

		nx = 80;
		ny = 130;
		m_EdNickName.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		m_EdUserID.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = 80;
		ny += m_ImageTextBG.GetH() + 10;
		m_EdScore.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ImageBack.GetW() - 15 - m_btSave.getW();
		m_btSave.layout(nx, ny, 0, 0);

		nx = 80;
		ny += m_ImageTextBG.GetH() + 10;
		m_EdPassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ImageBack.GetW() - 15 - m_btTake.getW();
		m_btTake.layout(nx, ny, 0, 0);
		nx = m_ImageBack.GetW() - 15 - m_btTransfer.getW();
		m_btTransfer.layout(nx, ny, 0, 0);

	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		int nx = 0;
		int ny = 0;

		m_btTakeSaveSelect.layout(2, 5, 0, 0);
		m_btTransferSelect.layout(2 + m_btTakeSaveSelect.getW(), 5, 0, 0);

		m_btClose.layout(m_ImageBack.GetW() - 15 - m_btClose.getW(), 5, 0, 0);
		m_btFresh.layout(m_ImageBack.GetW() - 100, 60, 0, 0);

		m_ProBar.layout(m_ImageBack.GetW() - 100, 60, m_ImageBack.GetW() - 100 + m_btFresh.getW(), 60 + m_btFresh.getH());

		m_rbtByID.layout(400, 140, 0, 0);
		m_rbtByNickName.layout(400, 180, 0, 0);

		nx = 120;
		ny = 150;
		m_EdNickName.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		m_EdUserID.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = 120;
		ny += m_ImageTextBG.GetH() + 20;
		m_EdScore.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ImageBack.GetW() - 15 - m_btSave.getW();
		m_btSave.layout(nx, ny, 0, 0);

		nx = 120;
		ny += m_ImageTextBG.GetH() + 20;
		m_EdPassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ImageBack.GetW() - 15 - m_btTake.getW();
		m_btTake.layout(nx, ny, 0, 0);
		nx = m_ImageBack.GetW() - 15 - m_btTransfer.getW();
		m_btTransfer.layout(nx, ny, 0, 0);

	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void MainMessagedispatch(int mainid, int subid, Object obj) {
		switch (mainid) {
			case NetCommand.SUB_GR_USER_INSURE_INFO : {

				// 隐藏等待
				m_ProBar.setVisibility(INVISIBLE);
				m_btFresh.setVisibility(VISIBLE);
				// 读取信息
				CMD_GR_S_UserInsureInfo cmd = (CMD_GR_S_UserInsureInfo) obj;
				m_UserInsureInfo.wRevenueTake = cmd.wRevenueTake;
				m_UserInsureInfo.wRevenueTransfer = cmd.wRevenueTransfer;
				m_UserInsureInfo.wServerID = cmd.wServerID;
				m_UserInsureInfo.lUserScore = cmd.lUserScore;
				m_UserInsureInfo.lUserInsure = cmd.lUserInsure;
				m_UserInsureInfo.lTransferPrerequisite = cmd.lTransferPrerequisite;
				String msg = "";
				msg += "取款税率：" + cmd.wRevenueTake + "\n";
				msg += "转账税率：" + cmd.wRevenueTransfer + "\n";
				msg += "最低转账：" + cmd.lTransferPrerequisite + "\n";
				Toast.makeText(PDF.GetContext(), msg, Toast.LENGTH_LONG).show();

				break;
			}
			case NetCommand.SUB_GR_USER_INSURE_SUCCESS : {
				m_ProBar.setVisibility(INVISIBLE);
				m_btFresh.setVisibility(VISIBLE);

				// 读取信息
				CMD_GR_S_UserInsureSuccess cmd = (CMD_GR_S_UserInsureSuccess) obj;
				m_UserInsureInfo.lUserInsure = cmd.lUserInsure;
				m_UserInsureInfo.lUserScore = cmd.lUserScore;

				// 通知提示
				Toast.makeText(PDF.GetContext(), cmd.szDescribeString, Toast.LENGTH_LONG).show();
				break;
			}
			case NetCommand.SUB_GR_USER_INSURE_FAILURE : {

				if (obj != null) {
					CMD_GR_S_UserInsureFailure cmd = (CMD_GR_S_UserInsureFailure) obj;
					// 通知提示
					Toast.makeText(PDF.GetContext(), cmd.szDescribeString, Toast.LENGTH_LONG).show();
				}
				m_ProBar.setVisibility(INVISIBLE);
				m_btFresh.setVisibility(VISIBLE);
				break;
			}
		}
	}

	@Override
	public void onCheckedChanged(int groupid, int viewid) {
		m_EdNickName.setVisibility(m_rbtByID.GetTickOff() ? INVISIBLE : VISIBLE);
		m_EdUserID.setVisibility(m_rbtByID.GetTickOff() ? VISIBLE : INVISIBLE);
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				postInvalidate(20, 150, 80, 190);
				break;
			case DF.DEVICETYPE_HVGA :
				postInvalidate(15, 127, 95, 147);
				break;
			case DF.DEVICETYPE_QVGA :
				postInvalidate(15, 127, 95, 147);
				break;

		}

	}

	@Override
	public void PerformQueryInfo() {
		CMD_GR_C_QuerInsureInfoRequest cmd = new CMD_GR_C_QuerInsureInfoRequest();
		cmd.szPassWord = ClientPlazz.GetPlazzInstance().GetLogonPassWord();
		IClientKernelEx kernle = (IClientKernelEx) ClientPlazz.GetKernel();
		kernle.SendSocketData(NetCommand.MDM_GR_INSURE, NetCommand.SUB_GR_QUERY_INSURE_INFO, cmd);
	}

	@Override
	public void PerformSaveScore(long lSaveScore) {
		// 输入检测
		String szscore = m_EdScore.getText().toString();
		if (szscore == null || szscore.equals("")) {
			Toast.makeText(PDF.GetContext(), "请输入游戏币数目！", Toast.LENGTH_SHORT).show();
			return;
		}

		long score = Long.parseLong(szscore);
		if (score <= 0) {
			Toast.makeText(PDF.GetContext(), "请输入游戏币数目！", Toast.LENGTH_SHORT).show();
			return;
		}

		if (score > m_UserInsureInfo.lUserScore) {
			Toast.makeText(PDF.GetContext(), "请输入游戏币数目大于当前数目！", Toast.LENGTH_SHORT).show();
			return;
		}

		m_ProBar.setVisibility(VISIBLE);
		m_btFresh.setVisibility(INVISIBLE);

		CMD_GR_C_SaveScoreRequest cmd = new CMD_GR_C_SaveScoreRequest();
		cmd.cbActivityGame = PDF.INSURE_SAVE;
		cmd.lSaveScore = score;

		IClientKernelEx kernle = (IClientKernelEx) ClientPlazz.GetKernel();
		kernle.SendSocketData(NetCommand.MDM_GR_INSURE, NetCommand.SUB_GR_SAVE_SCORE_REQUEST, cmd);
		m_EdScore.setText("");
	}

	@Override
	public void PerformTakeScore(long lTakeScore, String szInsurePass) {
		// 输入检测
		String szscore = m_EdScore.getText().toString();
		if (szscore == null || szscore.equals("")) {
			Toast.makeText(PDF.GetContext(), "请输入游戏币数目！", Toast.LENGTH_SHORT).show();
			return;
		}

		String szpass = m_EdPassWord.getText().toString();
		if (szpass == null || szpass.equals("")) {
			Toast.makeText(PDF.GetContext(), "请输入保险柜密码！", Toast.LENGTH_SHORT).show();
			return;
		}
		long score = Long.parseLong(szscore);

		if (score > m_UserInsureInfo.lUserInsure) {
			Toast.makeText(PDF.GetContext(), "请输入游戏币数目大于保险柜数目！", Toast.LENGTH_SHORT).show();
			return;
		}
		m_ProBar.setVisibility(VISIBLE);
		m_btFresh.setVisibility(INVISIBLE);

		CMD_GR_C_TakeScoreRequest cmd = new CMD_GR_C_TakeScoreRequest();

		cmd.cbActivityGame = PDF.INSURE_SAVE;
		cmd.lTakeScore = score;
		cmd.szInsurePass = szpass;
		IClientKernelEx kernle = (IClientKernelEx) ClientPlazz.GetKernel();
		kernle.SendSocketData(NetCommand.MDM_GR_INSURE, NetCommand.SUB_GR_TAKE_SCORE_REQUEST, cmd);

		m_EdScore.setText("");
		m_EdPassWord.setText("");

	}

	@Override
	public void PerformTransferScore(int cbByNickName, String szNickName, long lTransferScore, String szInsurePass) {
		// 输入检测
		String szscore = m_EdScore.getText().toString();
		if (szscore == null || szscore.equals("")) {
			Toast.makeText(PDF.GetContext(), "请输入游戏币数目！", Toast.LENGTH_LONG).show();
			return;
		}

		String szpass = m_EdPassWord.getText().toString();
		if (szpass == null || szpass.equals("")) {
			Toast.makeText(PDF.GetContext(), "请输入保险柜密码！", Toast.LENGTH_LONG).show();
			return;
		}

		int bynickname = m_rbtByID.GetTickOff() ? 0 : 1;
		String szuser = "";
		if (bynickname == 1) {
			szuser = m_EdNickName.getText().toString();
		} else {
			szuser = m_EdUserID.getText().toString();
		}

		if (szuser == null || szuser.equals("")) {
			Toast.makeText(PDF.GetContext(), "请输入转账用户！", Toast.LENGTH_LONG).show();
			return;
		}

		long score = Long.parseLong(szscore);

		if (score < m_UserInsureInfo.lTransferPrerequisite) {
			Toast.makeText(PDF.GetContext(), "转账限制为：" + m_UserInsureInfo.lTransferPrerequisite, Toast.LENGTH_LONG).show();
			return;
		}

		m_ProBar.setVisibility(VISIBLE);
		m_btFresh.setVisibility(INVISIBLE);
		m_EdScore.setText("");

		CMD_GR_C_TransferScoreRequest cmd = new CMD_GR_C_TransferScoreRequest();

		cmd.cbActivityGame = PDF.INSURE_TRANSFER;
		cmd.cbByNickName = bynickname;
		cmd.lTransferScore = score;
		cmd.szNickName = szuser;
		cmd.szInsurePass = szpass;

		PDF.SendMainMessage(ClientPlazz.MM_QUERY_TRANSFER, 0, cmd);
		m_EdScore.setText("");
		m_EdPassWord.setText("");
		m_EdNickName.setText("");
		m_EdUserID.setText("");
	}

	@Override
	public boolean onSingleClick(View v, Object obj) {
		int id = v.getId();
		if (id == m_btClose.getId()) {
			setVisibility(INVISIBLE);
			return true;
		}
		if (m_ProBar.getVisibility() == VISIBLE) {
			Toast.makeText(getContext(), "正在更新中，请稍后!", Toast.LENGTH_SHORT);
			return true;
		}
		if (id == m_btTakeSaveSelect.getId()) {
			if (m_btTakeSaveSelect.IsSelect() == false) {
				ShowBankByMode(TAKE_SAVE_MODE, false);
				postInvalidate();
			}
			return true;
		} else if (id == m_btTransferSelect.getId()) {
			if (m_btTransferSelect.IsSelect() == false) {
				ShowBankByMode(TRANSFER_MODE, false);
				postInvalidate();
			}
			return true;
		} else if (id == m_btTake.getId()) {
			PerformTakeScore(0, "");
			return true;
		} else if (id == m_btSave.getId()) {
			PerformSaveScore(0);
			return true;
		} else if (id == m_btTransfer.getId()) {
			PerformTransferScore(0, "", 0, "");
			return true;
		} else if (id == m_btFresh.getId()) {
			m_ProBar.setVisibility(VISIBLE);
			m_btFresh.setVisibility(INVISIBLE);
			PerformQueryInfo();
			return true;
		}
		return false;
	}

	@Override
	protected void Render(Canvas canvas) {

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
		m_paint.setColor(Color.WHITE);
		m_paint.setTextSize(14);

		if (m_btTransferSelect.IsSelect()) {
			CText.DrawTextEllip(canvas, m_rbtByID.GetTickOff() ? "ID" : "昵称", 10, 88, 80, m_paint);
		}
		CText.DrawTextEllip(canvas, "游戏币", 10, 117, 80, m_paint);
		CText.DrawTextEllip(canvas, "密码", 10, 146, 80, m_paint);

		m_paint.setColor(Color.rgb(250, 250, 0));
		m_paint.setTextSize(12);
		CText.DrawTextEllip(canvas, "当前  " + m_UserInsureInfo.lUserScore, 50, 26, 160, m_paint);

		CText.DrawTextEllip(canvas, "保险柜" + m_UserInsureInfo.lUserInsure, 50, 42, 160, m_paint);

	}

	private void onRenderM(Canvas canvas) {
		m_paint.setColor(Color.WHITE);
		m_paint.setTextSize(20);

		if (m_btTransferSelect.IsSelect()) {
			CText.DrawTextEllip(canvas, m_rbtByID.GetTickOff() ? "ID" : "昵称", 15, 127, 80, m_paint);
		}
		CText.DrawTextEllip(canvas, "游戏币", 15, 165, 80, m_paint);
		CText.DrawTextEllip(canvas, "密码", 15, 205, 80, m_paint);

		m_paint.setColor(Color.rgb(250, 250, 0));
		m_paint.setTextSize(14);
		CText.DrawTextEllip(canvas, "当前游戏币     " + m_UserInsureInfo.lUserScore, 80, 40, 200, m_paint);

		CText.DrawTextEllip(canvas, "保险柜游戏币 " + m_UserInsureInfo.lUserInsure, 80, 65, 200, m_paint);
	}

	private void onRenderH(Canvas canvas) {
		m_paint.setColor(Color.WHITE);
		m_paint.setTextSize(24);

		if (m_btTransferSelect.IsSelect()) {
			CText.DrawTextEllip(canvas, m_rbtByID.GetTickOff() ? "ID" : "昵称", 30, 160, 80, m_paint);
		}
		CText.DrawTextEllip(canvas, "游戏币", 30, 235, 80, m_paint);
		CText.DrawTextEllip(canvas, "密码", 30, 310, 80, m_paint);

		m_paint.setColor(Color.rgb(250, 250, 0));
		m_paint.setTextSize(18);
		CText.DrawTextEllip(canvas, "当前游戏币     " + m_UserInsureInfo.lUserScore, 120, 65, 300, m_paint);

		CText.DrawTextEllip(canvas, "保险柜游戏币 " + m_UserInsureInfo.lUserInsure, 120, 100, 300, m_paint);
	}

	@Override
	public int GetW() {
		return m_ImageBack.GetW();
	}

	@Override
	public int GetH() {
		return m_ImageBack.GetH();
	}

	public void onUserInsureInfo(int sub, ICmd cmd) {
		PDF.SendMainMessage(sub, 0, GetTag(), cmd);
	}

	public void ShowBankByMode(int mode, boolean bfresh) {

		m_ProBar.setVisibility(bfresh ? VISIBLE : INVISIBLE);
		m_btFresh.setVisibility(bfresh ? INVISIBLE : VISIBLE);
		if (bfresh)
			PerformQueryInfo();
		switch (mode) {
			case TAKE_SAVE_MODE :
				m_btClose.setVisibility(VISIBLE);

				m_btSave.setVisibility(VISIBLE);
				m_btTake.setVisibility(VISIBLE);

				m_btTransfer.setVisibility(INVISIBLE);
				m_rbtByID.setVisibility(INVISIBLE);
				m_rbtByNickName.setVisibility(INVISIBLE);

				m_EdPassWord.setVisibility(VISIBLE);
				m_EdNickName.setVisibility(INVISIBLE);
				m_EdUserID.setVisibility(INVISIBLE);
				m_EdScore.setVisibility(VISIBLE);

				m_btTakeSaveSelect.setVisibility(VISIBLE);
				m_btTransferSelect.setVisibility(VISIBLE);
				m_btTakeSaveSelect.SetSelect(true);
				m_btTransferSelect.SetSelect(false);

				break;
			case TRANSFER_MODE :
				m_btClose.setVisibility(VISIBLE);

				m_btSave.setVisibility(INVISIBLE);
				m_btTake.setVisibility(INVISIBLE);
				m_btTransfer.setVisibility(VISIBLE);

				m_rbtByID.setVisibility(VISIBLE);
				m_rbtByNickName.setVisibility(VISIBLE);

				m_EdPassWord.setVisibility(VISIBLE);
				m_EdNickName.setVisibility(m_rbtByID.GetTickOff() ? INVISIBLE : VISIBLE);
				m_EdUserID.setVisibility(m_rbtByID.GetTickOff() ? VISIBLE : INVISIBLE);
				m_EdScore.setVisibility(VISIBLE);

				m_btTakeSaveSelect.setVisibility(VISIBLE);
				m_btTransferSelect.setVisibility(VISIBLE);
				m_btTakeSaveSelect.SetSelect(false);
				m_btTransferSelect.SetSelect(true);

				break;

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		super.onTouchEvent(event);
		return true;
	}
}
