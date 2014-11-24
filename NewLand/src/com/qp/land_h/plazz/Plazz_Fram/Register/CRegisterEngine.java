package com.qp.land_h.plazz.Plazz_Fram.Register;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Graphics.CImageEx;
import Lib_Interface.IKeyBackDispatch;
import Lib_Interface.ButtonInterface.IClickedChangeListener;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Lib_System.View.ButtonView.CRadioButton;
import Lib_System.View.ButtonView.CRadioGroup;
import Net_Struct.CPackMessage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.EmptyEditText;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Interface.IListControl;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_RegisterAccounts;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

/**
 * ע�����
 * 
 * @note
 * @remark
 */
public class CRegisterEngine extends CViewEngine implements IKeyBackDispatch, IClickedChangeListener, ISingleClickListener, IListControl, IMainMessage {

	protected Button		m_btHead;

	protected CImageButton	m_btRegister;		// ע�ᰴť
	protected CImageButton	m_btSelectFace;	// ѡ��ͷ��
	protected CImageButton	m_btCancel;		// ���ذ�ť
	// protected CImageButton m_btOption; // ��Ϸ����

	protected CRadioGroup	m_GenderGroup;		// ��ѡ����
	protected CRadioButton	m_rbtGenderM;		// �����Ա�
	protected CRadioButton	m_rbtGenderW;		// Ů���Ա�

	protected CSelectFace	m_FaceView;		// ͷ����ʾ�ؼ�

	protected EmptyEditText	m_EdAccounts;		// �ʺ�����
	protected EmptyEditText	m_EdPassWord;		// ��������
	protected EmptyEditText	m_EdNickName;		// �ǳ�����
	protected EmptyEditText	m_EdInsurePassWord; // �ǳ�����

	CImageEx				m_ImageFram;		// ע��򱳾�
	CImageEx				m_ImageHeadBg;		// ��ǰͷ�񱳾�
	CImageEx				m_ImageTextBG;
	int						m_nFaceIDM	= 0;	// ����ͷ��ID��¼
	int						m_nFaceIDW	= 8;	// Ů��ͷ��ID��¼

	Point					m_ptFram;			// ע���λ��
	Point					m_ptInputWH;		// ���������Ŀ��

	public CRegisterEngine(Context context) {
		super(context);
		// ͼƬ��Դ
		try {
			m_ImageHeadBg = new CImageEx(ClientPlazz.RES_PATH + "register/bg_head.png");
			m_ImageFram = new CImageEx(ClientPlazz.RES_PATH + "register/bg_fram.png");
			m_ImageTextBG = new CImageEx(ClientPlazz.RES_PATH + "register/bg_txt.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ���ð�ť
		// m_btOption = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_option.png");
		// ���ذ�ť
		m_btCancel = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_cancel.png");
		// ע��
		m_btRegister = new CImageButton(context, ClientPlazz.RES_PATH + "register/bt_register.png");
		// ѡͷ��
		m_btSelectFace = new CImageButton(context, ClientPlazz.RES_PATH + "register/bt_head.png");
		// ��
		m_rbtGenderM = new CRadioButton(context, ClientPlazz.RES_PATH + "register/bg_tickoff.png", ClientPlazz.RES_PATH + "register/rbt_tickoff.png", " ��");
		// Ů
		m_rbtGenderW = new CRadioButton(context, ClientPlazz.RES_PATH + "register/bg_tickoff.png", ClientPlazz.RES_PATH + "register/rbt_tickoff.png", " Ů");
		// �ʺ�����
		m_EdAccounts = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, false);
		// ��������
		m_EdPassWord = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, true);
		// �ǳ�����
		m_EdNickName = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, false);
		// ���չ�����
		m_EdInsurePassWord = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, true);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				m_rbtGenderM.setTextSize(32);
				m_rbtGenderW.setTextSize(32);
				break;
			case DF.DEVICETYPE_HVGA :
				m_rbtGenderM.setTextSize(18);
				m_rbtGenderW.setTextSize(18);
				break;
			case DF.DEVICETYPE_QVGA :
				m_rbtGenderM.setTextSize(12);
				m_rbtGenderW.setTextSize(12);
				break;

		}
		m_rbtGenderM.setTextColor(Color.WHITE);
		m_rbtGenderW.setTextColor(Color.WHITE);

		// ͷ��ѡ��
		m_FaceView = new CSelectFace(context);

		m_btHead = new Button(context);
		m_btHead.setBackgroundDrawable(null);
		addView(m_btHead);
		m_btHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_FaceView.getVisibility() == INVISIBLE)
					m_FaceView.setVisibility(VISIBLE);
			}
		});

		// �ؼ���������
		m_EdAccounts.setIncludeFontPadding(false);
		m_EdPassWord.setIncludeFontPadding(false);
		m_EdNickName.setIncludeFontPadding(false);
		m_EdInsurePassWord.setIncludeFontPadding(false);

		m_EdAccounts.setPadding(5, 5, 5, 1);
		m_EdPassWord.setPadding(5, 5, 5, 1);
		m_EdNickName.setPadding(5, 5, 5, 1);
		m_EdInsurePassWord.setPadding(5, 5, 5, 1);

		m_EdAccounts.setGravity(Gravity.BOTTOM);
		m_EdPassWord.setGravity(Gravity.BOTTOM);
		m_EdInsurePassWord.setGravity(Gravity.BOTTOM);
		m_EdNickName.setGravity(Gravity.BOTTOM);

		m_EdAccounts.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_ACCOUNTS)});
		m_EdPassWord.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_PASSWORD)});
		m_EdNickName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_NICENAME)});
		m_EdInsurePassWord.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PDF.LEN_PASSWORD)});
		
		m_EdAccounts.setEllipsize(TruncateAt.END);
		m_EdPassWord.setEllipsize(TruncateAt.END);
		m_EdNickName.setEllipsize(TruncateAt.END);
		m_EdInsurePassWord.setEllipsize(TruncateAt.END);

		// ����ʾ
		// addView(m_btOption);
		addView(m_btCancel);
		addView(m_btRegister);
		addView(m_btSelectFace);
		addView(m_rbtGenderM);
		addView(m_rbtGenderW);
		addView(m_EdAccounts);
		addView(m_EdPassWord);
		addView(m_EdNickName);
		addView(m_EdInsurePassWord);
		addView(m_FaceView);

		// ���ü���
		// m_btOption.setSingleClickListener(this);
		m_btCancel.setSingleClickListener(this);
		m_btRegister.setSingleClickListener(this);
		m_btSelectFace.setSingleClickListener(this);
		m_FaceView.SetListControlListener(this);
		// ���ü���
		m_GenderGroup = new CRadioGroup(this);
		m_GenderGroup.onBindingRadio(m_rbtGenderM);
		m_GenderGroup.onBindingRadio(m_rbtGenderW);

		// λ����Ϣ
		m_ptFram = new Point(ClientPlazz.SCREEN_WIDHT / 2 - m_ImageFram.GetW() / 2, ClientPlazz.SCREEN_HEIGHT / 2 - m_ImageFram.GetH() / 2);

	}

	/**
	 * �ƶ�λ��
	 */
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
		int nx, ny;
		nx = 5;
		ny = ClientPlazz.SCREEN_HEIGHT - m_btCancel.getH();
		m_btCancel.layout(nx, ny, 0, 0);

		// nx = ClientPlazz.SCREEN_WIDHT - 5 - m_btOption.getW();
		// ny = ClientPlazz.SCREEN_HEIGHT - m_btOption.getH();
		// m_btOption.layout(nx, ny, 0, 0);

		nx = ClientPlazz.SCREEN_WIDHT / 2 - m_btRegister.getW() / 2;
		ny = ClientPlazz.SCREEN_HEIGHT - m_btRegister.getH();
		m_btRegister.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 235;
		ny = m_ptFram.y + 75;
		m_rbtGenderM.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 235;
		ny = m_ptFram.y + 100;
		m_rbtGenderW.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 95;
		ny = m_ptFram.y + 15;
		m_EdAccounts.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 45;
		m_EdNickName.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 125;
		m_EdPassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 155;
		m_EdInsurePassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ptFram.x + 95;
		ny = m_ptFram.y + 120;
		m_FaceView.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 95;
		ny = m_ptFram.y + 75;
		m_btHead.layout(nx, ny, nx + m_ImageHeadBg.GetW(), ny + m_ImageHeadBg.GetH());

		nx = m_ptFram.x + 140;
		ny = m_ptFram.y + 95;
		m_btSelectFace.layout(nx, ny, 0, 0);
	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		int nx, ny;
		nx = 5;
		ny = ClientPlazz.SCREEN_HEIGHT - m_btCancel.getH();
		m_btCancel.layout(nx, ny, 0, 0);

		// nx = ClientPlazz.SCREEN_WIDHT - 5 - m_btOption.getW();
		// ny = ClientPlazz.SCREEN_HEIGHT - m_btOption.getH();
		// m_btOption.layout(nx, ny, 0, 0);

		nx = ClientPlazz.SCREEN_WIDHT / 2 - m_btRegister.getW() / 2;
		ny = ClientPlazz.SCREEN_HEIGHT - m_btRegister.getH() - 10;
		m_btRegister.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 275;
		ny = m_ptFram.y + 95;
		m_rbtGenderM.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 335;
		ny = m_ptFram.y + 95;
		m_rbtGenderW.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 125;
		ny = m_ptFram.y + 12;
		m_EdAccounts.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 47;
		m_EdNickName.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 150;
		m_EdPassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 190;
		m_EdInsurePassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ptFram.x + 125;
		ny = m_ptFram.y + 140;
		m_FaceView.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 125;
		ny = m_ptFram.y + 75;
		m_btHead.layout(nx, ny, nx + m_ImageHeadBg.GetW(), ny + m_ImageHeadBg.GetH());

		nx = m_ptFram.x + 190;
		ny = m_ptFram.y + 120;
		m_btSelectFace.layout(nx, ny, 0, 0);
	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		int nx, ny;
		nx = 5;
		ny = ClientPlazz.SCREEN_HEIGHT - m_btCancel.getH();
		m_btCancel.layout(nx, ny, 0, 0);

		// nx = ClientPlazz.SCREEN_WIDHT - 5 - m_btOption.getW();
		// ny = ClientPlazz.SCREEN_HEIGHT - m_btOption.getH();
		// m_btOption.layout(nx, ny, 0, 0);

		nx = ClientPlazz.SCREEN_WIDHT / 2 - m_btRegister.getW() / 2;
		ny = m_ptFram.y + m_ImageFram.GetH() - 25 - m_btRegister.getH() - 5;
		m_btRegister.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 450;
		ny = m_ptFram.y + 210;
		m_rbtGenderM.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 450 + m_rbtGenderM.getW() + 10;
		ny = m_ptFram.y + 210;
		m_rbtGenderW.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 200;
		ny = m_ptFram.y + 40;
		m_EdAccounts.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 110;
		m_EdNickName.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 260;
		m_EdPassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());
		ny = m_ptFram.y + 330;
		m_EdInsurePassWord.layout(nx, ny, nx + m_ImageTextBG.GetW(), ny + m_ImageTextBG.GetH());

		nx = m_ptFram.x + 265;
		ny = m_ptFram.y + 250;
		m_FaceView.layout(nx, ny, 0, 0);

		nx = m_ptFram.x + 200;
		ny = m_ptFram.y + 168;
		m_btHead.layout(nx, ny, nx + m_ImageHeadBg.GetW(), ny + m_ImageHeadBg.GetH());

		nx = m_ptFram.x + 290;
		ny = m_ptFram.y + 210;
		m_btSelectFace.layout(nx, ny, 0, 0);

	}

	@Override
	protected void Render(Canvas canvas) {

		// ע��򱳾�
		m_ImageFram.DrawImage(canvas, m_ptFram.x, m_ptFram.y);

		// ��ǰѡ��ͷ�񱳾�
		m_ImageHeadBg.DrawImage(canvas, m_btHead.getLeft(), m_btHead.getTop());

		// ��ǰѡ��ͷ��
		// ˢ����ѡ��ͷ����ʾ
		int space = 0;
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				space = 12;
				break;
			case DF.DEVICETYPE_HVGA :
				space = 7;
				break;
			case DF.DEVICETYPE_QVGA :
				space = 5;
				break;
		}

		CPlazzGraphics graphics = CPlazzGraphics.onCreate();
		graphics.DrawUserAvatar(canvas, m_btHead.getLeft() + space, m_btHead.getTop() + space, m_rbtGenderM.GetTickOff() ? m_nFaceIDM : m_nFaceIDW);

	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {

		m_FaceView.OnDestoryRes();
		m_btRegister.setVisibility(INVISIBLE);
		m_btSelectFace.setVisibility(INVISIBLE);
		m_btCancel.setVisibility(INVISIBLE);
		// m_btOption.setVisibility(INVISIBLE);
		m_rbtGenderM.setVisibility(INVISIBLE);
		m_rbtGenderW.setVisibility(INVISIBLE);

		m_EdAccounts.setText("");
		m_EdPassWord.setText("");
		m_EdNickName.setText("");
		m_EdInsurePassWord.setText("");

		ClientPlazz.DestoryBackGround(m_EdAccounts);
		ClientPlazz.DestoryBackGround(m_EdPassWord);
		ClientPlazz.DestoryBackGround(m_EdInsurePassWord);
		ClientPlazz.DestoryBackGround(m_EdNickName);

		m_ImageFram.OnReleaseImage();
		m_ImageHeadBg.OnReleaseImage();
		m_ImageTextBG.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		setBackgroundDrawable(new BitmapDrawable(new CImage(getContext(), ClientPlazz.RES_PATH + "custom/bg.png", null, true).GetBitMap()));
		setClickable(true);
		// ȡ��ͷ��ѡ��ռ���ʾ
		m_FaceView.OnInitRes();
		m_FaceView.setVisibility(INVISIBLE);

		try {
			m_ImageFram.OnReLoadImage();
			m_ImageHeadBg.OnReLoadImage();
			m_ImageTextBG.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapDrawable drawable = new BitmapDrawable(m_ImageTextBG.GetBitMap());
		m_EdAccounts.setBackgroundDrawable(drawable);
		m_EdPassWord.setBackgroundDrawable(drawable);
		m_EdInsurePassWord.setBackgroundDrawable(drawable);
		m_EdNickName.setBackgroundDrawable(drawable);

		m_EdAccounts.setHint("6-32���ַ��˺�");
		m_EdPassWord.setHint("����6λ��Ϸ����");
		m_EdNickName.setHint("6-32���ַ��ǳ�");
		m_EdInsurePassWord.setHint("����6λ���չ�����");

		m_btRegister.setVisibility(VISIBLE);
		m_btSelectFace.setVisibility(VISIBLE);
		m_btCancel.setVisibility(VISIBLE);
		// m_btOption.setVisibility(VISIBLE);
		m_rbtGenderM.setVisibility(VISIBLE);
		m_rbtGenderW.setVisibility(VISIBLE);
	}

	@Override
	public boolean KeyBackDispatch() {
		// ��ͷ��ѡ����ʾ ����ȡ��ͷ����ʾ
		if (m_FaceView.getVisibility() == VISIBLE) {
			m_FaceView.setVisibility(INVISIBLE);
			return true;
		}
		// ���ص�¼����
		PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_LOGIN, null);
		return true;
	}

	/**
	 * ��ѡ��ť�ı�
	 */
	@Override
	public void onCheckedChanged(int groupid, int viewid) {
		// ����ͷ����ʾ�Ա�ģʽ
		m_FaceView.SetGenderMode(m_rbtGenderM.GetTickOff());
		onPosInvalidateHead();

	}

	private void onPosInvalidateHead() {
		// ˢ����ѡ��ͷ����ʾ
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				postInvalidate(m_ptFram.x + 200 + 12, m_ptFram.y + 168 + 12, m_ptFram.x + 200 + 12 + m_ImageHeadBg.GetW(), m_ptFram.y + 168 + 12
						+ m_ImageHeadBg.GetH());
				break;
			case DF.DEVICETYPE_HVGA :
				postInvalidate(m_ptFram.x + 125 + 7, m_ptFram.y + 88 + 7, m_ptFram.x + 125 + 7 + m_ImageHeadBg.GetW(),
						m_ptFram.y + 88 + 7 + m_ImageHeadBg.GetH());
				break;
			case DF.DEVICETYPE_QVGA :
				postInvalidate(m_ptFram.x + 95 + 5, m_ptFram.y + 75 + 5, m_ptFram.x + 95 + 5 + m_ImageHeadBg.GetW(), m_ptFram.y + 75 + 5 + m_ImageHeadBg.GetH());
				break;
		}
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		// ȡ����ť
		if (view.getId() == m_btCancel.getId()) {
			KeyBackDispatch();
		}
		// ע�ᰴť
		else if (view.getId() == m_btRegister.getId()) {
			CPackMessage sendpackge = onRegister();
			if (sendpackge != null) {
				ClientPlazz.GetLoginEngine().SendSocketRegister(sendpackge);
			}
			return true;
		}
		// ѡ��ͷ��
		else if (view.getId() == m_btSelectFace.getId()) {
			m_FaceView.setVisibility(m_FaceView.getVisibility() == VISIBLE ? INVISIBLE : VISIBLE);
			return true;
		}
		return false;
	}

	/**
	 * ����ע�����ݰ�
	 * 
	 * @return ע�����ݰ�
	 */
	public CPackMessage onRegister() {
		CPackMessage sendpackge = new CPackMessage();
		// �ʺźϷ��ж�
		String szAccounts = m_EdAccounts.getEditableText().toString();
		if (szAccounts == null || szAccounts.equals("")) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "�����������ʺ�", Toast.LENGTH_SHORT).show();
			return null;
		}

		try {
			if (szAccounts.getBytes("gb2312").length < 6) {
				Toast.makeText(ClientPlazz.GetPlazzInstance(), "�ʺ����ֵĳ������Ϊ6λ�ַ���������������", Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "��������������������", Toast.LENGTH_SHORT).show();
			return null;
		}

		// �ǳƺϷ��ж�
		String szNickName = m_EdNickName.getEditableText().toString();
		if (szNickName == null || szNickName.equals("")) {
			Toast.makeText(PDF.GetContext(), "�����������ǳ�", Toast.LENGTH_SHORT).show();
			return null;
		}

		try {
			if (szNickName.getBytes("gb2312").length < 6) {
				Toast.makeText(PDF.GetContext(), "��Ϸ�ǳƳ������Ϊ6λ�ַ�������������", Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(PDF.GetContext(), "��������������������", Toast.LENGTH_SHORT).show();
			return null;
		}

		// ����Ϸ��ж�
		String szPassWord = m_EdPassWord.getEditableText().toString();
		if (szPassWord == null || szPassWord.equals("")) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "��������������", Toast.LENGTH_SHORT).show();
			return null;
		}

		try {
			if (szPassWord.getBytes("gb2312").length < 6) {
				Toast.makeText(ClientPlazz.GetPlazzInstance(), "��Ϸ���볤�����Ϊ6λ�ַ���������������", Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(ClientPlazz.GetPlazzInstance(), "��������������������", Toast.LENGTH_SHORT).show();
			return null;
		}

		// ���չ�����Ϸ��ж�
		String szInsurePassWord = m_EdInsurePassWord.getEditableText().toString();
		if (szInsurePassWord == null || szInsurePassWord.equals("")) {
			Toast.makeText(PDF.GetContext(), "���������ı��չ�����", Toast.LENGTH_SHORT).show();
			return null;
		}

		try {
			if (szInsurePassWord.getBytes("gb2312").length < 6) {
				Toast.makeText(PDF.GetContext(), "���չ����볤�����Ϊ6λ�ַ���������������", Toast.LENGTH_SHORT).show();
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(PDF.GetContext(), "��������������������", Toast.LENGTH_SHORT).show();
			return null;
		}

		// ����ע�����ݰ�
		CMD_MB_RegisterAccounts cmd = new CMD_MB_RegisterAccounts();
		cmd.nModuleID = ClientPlazz.GetKernel().GetGameAttribute().KindId;
		cmd.lPlazaVersion = ClientPlazz.GetKernel().GetGameAttribute().ProcesVersion;
		cmd.cbDeviceType = (byte) ClientPlazz.GetGlobalUnits().GetDeviceType();
		cmd.szLogonPass = szPassWord;
		cmd.szInsurePass = szInsurePassWord;
		cmd.nFaceID = m_rbtGenderM.GetTickOff() ? m_nFaceIDM : m_nFaceIDW;
		cmd.cbGender = (byte) (m_rbtGenderM.GetTickOff() ? PDF.GENDER_MANKIND : PDF.GENDER_FEMALE);
		cmd.szAccounts = szAccounts;
		cmd.szNickName = szNickName;
		cmd.szMachineID = PDF.GetPhoneIMEI();
		cmd.szMobilePhone = PDF.GetPhoneNum();

		sendpackge.main = NetCommand.MDM_MB_LOGON;
		sendpackge.sub = NetCommand.SUB_MB_REGISTER_ACCOUNTS;
		sendpackge.Obj = cmd;

		return sendpackge;
	}

	@Override
	public void DeleteItem(int index) {

	}

	/**
	 * ͷ��ѡ��
	 */
	@Override
	public void SeleteItem(int index) {

		m_FaceView.setVisibility(INVISIBLE);

		if (m_rbtGenderM.GetTickOff()) {
			m_nFaceIDM = index;
		} else {
			m_nFaceIDW = index + 8;
		}
		onPosInvalidateHead();
	}

	/**
	 * ��ȡͷ��ѡ��
	 */
	@Override
	public Object GetItem(int index) {
		if (m_rbtGenderM.GetTickOff()) {
			return m_nFaceIDM;
		} else {
			return m_nFaceIDW;
		}
	}

	@Override
	public int GetItemCount() {

		return 0;
	}

	public void OnLogonFailure(Object obj) {

	}

	public void OnLogonSucceed(Object obj) {
		m_EdAccounts.setText("");
		m_EdPassWord.setText("");
		// m_EdNickName.setText("");
		// m_EdInsurePassWord.setText("");
		m_rbtGenderM.SetTickOff(true);
		m_rbtGenderW.SetTickOff(false);
		m_nFaceIDM = 0;
		m_nFaceIDW = 8;
		m_FaceView.SetGenderMode(true);

	}

	@Override
	public void MainMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
			case NetCommand.MDM_MB_LOGON :
				if (sub == NetCommand.SUB_MB_LOGON_FAILURE) {
					OnLogonFailure(obj);
				} else if (sub == NetCommand.SUB_MB_LOGON_SUCCESS) {
					OnLogonSucceed(obj);
				}
				break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (m_FaceView.getVisibility() == VISIBLE)
			m_FaceView.setVisibility(INVISIBLE);
		return super.onTouchEvent(event);
	}
}
