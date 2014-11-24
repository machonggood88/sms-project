package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_Struct.tagUserInfo;
import Lib_System.CActivity;
import Lib_System.View.ButtonView.CButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;

/**
 * 头像控件类
 * 
 * @note
 * @remark
 */
public class CUserHead extends CButton implements IRangeObtain{

	/** 头像框图片 **/
	protected static CImageEx	m_ImageHeadFram;

	protected static CImageEx	m_ImageTrustee;

	protected int				m_nLand;
	protected int				m_TextH;
	protected int				m_TextW;
	protected int				m_Chair		= GDF.INVALID_CHAIR;

	IClientUserItem				m_Chairitem	= new tagUserInfo();

	boolean						m_bHorizontal;
	boolean						m_bTrustee;
	boolean						m_bDrawMsg;
	int							m_nCount;

	protected static boolean	m_bHideFace;

	/**
	 * 静态变量初始化
	 */
	static {
		try {
			m_ImageHeadFram = new CImageEx(ClientPlazz.RES_PATH + "gameres/headfram.png");
			m_ImageTrustee = new CImageEx(ClientPlazz.RES_PATH + "gameres/flag_trustee.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnInitRes() {
		try {
			m_ImageHeadFram.OnReLoadImage();
			m_ImageTrustee.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnHideFace(boolean hide) {
		m_bHideFace = hide;
	}

	public static void OnDestoryRes() {
		m_ImageHeadFram.OnReleaseImage();
		m_ImageTrustee.OnReleaseImage();
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param w
	 * @param h
	 * @param landscape
	 */
	public CUserHead(Context context,boolean horizontal){
		super(context);
		// 去除背景
		setBackgroundDrawable(null);
		m_bHorizontal = horizontal;
		// 画笔
		m_paint = new Paint();

		m_paint.setStrokeWidth(4);
		m_paint.setColor(Color.WHITE);
		m_paint.setAntiAlias(true);

		m_bDrawMsg = true;
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				m_paint.setTextSize(20);
				break;
			case DF.DEVICETYPE_HVGA:
				m_paint.setTextSize(12);
				break;
			case DF.DEVICETYPE_QVGA:
				m_paint.setTextSize(10);
				if (m_bHorizontal)
					m_bDrawMsg = false;
				break;
		}

		m_TextH = (int)CText.GetTextHeight(m_paint);
		m_TextW = (int)m_paint.measureText("单");
	}

	// /**
	// * 设置区域
	// */
	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// if (m_ImageHeadFram != null)
	// setMeasuredDimension(m_ImageHeadFram.GetW()
	// + (m_bHorizontal?(6 * m_TextW):0),
	// m_ImageHeadFram.GetH() + 2 * m_TextH);
	// }

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageHeadFram != null)
			layout(l,
							t,
							l + m_ImageHeadFram.GetW() + ((m_bHorizontal && m_bDrawMsg)?(6 * m_TextW):0),
							t + m_ImageHeadFram.GetH() + (m_bHorizontal?0:(2 * m_TextH)));
	}

	/**
	 * 绘制函数
	 */
	@Override
	public void onDraw(Canvas canvas) {

		DrawHead(canvas);
		DrawInfo(canvas);

		// int table = CGameClientEngine.m_TableID;
		// if (table == GDF.INVALID_TABLE)
		// return;

	}

	private void DrawInfo(Canvas canvas) {
		if (m_Chairitem == null || m_Chairitem.GetUserID() == 0 || m_bDrawMsg == false)
			return;
		int x = 0,y = 0;
		if (m_bHorizontal) {
			x = m_ImageHeadFram.GetW();
			y = 0;
		} else {
			x = 0;
			y = m_ImageHeadFram.GetH();
		}
		m_paint.setColor(Color.GREEN);
		CText.DrawTextEllip(canvas, m_Chairitem.GetNickName(), x, y,
						m_ImageHeadFram.GetW(), m_paint);
		m_paint.setColor(Color.YELLOW);
		CText.DrawTextEllip(canvas, m_Chairitem.GetUserScore() + "", x, y
						+ m_TextH, m_ImageHeadFram.GetW(), m_paint);
		// m_paint.setColor(Color.RED);
		// if (m_nCount > 0) {
		// CText.DrawTextEllip(canvas, "剩" + m_nCount + "张", x, y + m_TextH * 2, m_ImageHeadFram.GetW(), m_paint);
		// }

	}

	private void DrawHead(Canvas canvas) {
		if (m_Chairitem == null || m_Chairitem.GetUserID() == 0 )
			return;
		if (m_bTrustee) {
			m_ImageHeadFram.DrawImage(canvas, 0, 0);
			m_ImageTrustee.DrawImage(canvas, m_ImageHeadFram.GetW() / 2 - m_ImageTrustee.GetW() / 2,
							m_ImageHeadFram.GetH() / 2 - m_ImageTrustee.GetH() / 2);
		} else if(m_bHideFace==false){

			m_ImageHeadFram.DrawImage(canvas, 0, 0);

			CPlazzGraphics graphic = CPlazzGraphics.onCreate();
			// 用户头像
			if (graphic != null) {
				graphic.DrawUserAvatar(canvas, m_ImageHeadFram.GetW() / 2 - graphic.GetFaceW() / 2,
								m_ImageHeadFram.GetH() / 2 - graphic.GetFaceH() / 2, m_Chairitem);
			}
		}
	}

	/**
	 * 设置庄家
	 * 
	 * @param banker
	 */
	public void SetLandMode(int land) {
		m_nLand = land;
	}

	public int GetLandMode() {
		return m_nLand;
	}

	/**
	 * 获取控件宽
	 * 
	 * @return
	 */
	@Override
	public int GetW() {
		if (m_ImageHeadFram != null)
			return m_ImageHeadFram.GetW() + ((m_bHorizontal && m_bDrawMsg)?(6 * m_TextW):0);
		return 0;
	}

	/**
	 * 获取控件高
	 * 
	 * @return
	 */
	@Override
	public int GetH() {
		if (m_ImageHeadFram != null)
			return m_ImageHeadFram.GetH() + (m_bHorizontal?0:(2 * m_TextH));
		return 0;
	}

	public void Setchair(int chair) {
		m_Chair = chair;
	}

	public void SetTrustee(boolean trustee) {
		m_bTrustee = trustee;
	}

	public boolean GetTrustee() {
		return m_bTrustee;
	}

	public void OnEventUserStatus(IClientUserItem item) {
		((tagUserInfo)m_Chairitem).SetUserItem(item);
		postInvalidate();
	}
}
