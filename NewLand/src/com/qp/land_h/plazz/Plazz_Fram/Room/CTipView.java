package com.qp.land_h.plazz.Plazz_Fram.Room;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.qp.land_h.plazz.ClientPlazz;

/**
 * 提示界面
 * 
 * @note
 * @remark
 */
public class CTipView extends CViewEngine implements ISingleClickListener{

	/** 默认文字 **/
	protected final static String	DF_INFO	= "请稍候！";
	/** 背景图片 **/
	protected CImageEx				m_ImageBack;
	/** 等待进度 **/
	// protected ProgressBar m_ProBar;
	/** 关闭按钮 **/
	protected CImageButton			m_btClose;
	/** 文字信息 **/
	protected String				m_szInfo;
	/** 画笔 **/
	protected Paint					m_Paint;

	public CTipView(Context context){
		super(context);
		setWillNotDraw(false);

		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "room/bg_wait.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_btClose = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_close.png");
		// m_ProBar = new ProgressBar(context);
		m_Paint = new Paint();

		// m_ProBar.setLayoutParams(PDF.NewLayoutParams_WW());
		m_btClose.setSingleClickListener(this);

		m_Paint.setAntiAlias(true);
		m_Paint.setColor(0xfff5f500);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				m_Paint.setTextSize(24);
				break;
			case DF.DEVICETYPE_HVGA:
				m_Paint.setTextSize(18);
				break;
			case DF.DEVICETYPE_QVGA:
				m_Paint.setTextSize(16);
				break;
		}
		// addView(m_ProBar);
		addView(m_btClose);
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (m_ImageBack != null) {
			setMeasuredDimension(m_ImageBack.GetW(), m_ImageBack.GetH());
		}
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageBack != null) {
			this.layout(l, t, l + m_ImageBack.GetW(), t + m_ImageBack.GetH());
			switch (CActivity.nDeviceType) {
				case DF.DEVICETYPE_WVGA:
					m_btClose.layout(m_ImageBack.GetW() - m_btClose.getW() - 15,
									m_ImageBack.GetH() / 2 - m_btClose.getH() / 2 - 5, r, b);
					break;
				case DF.DEVICETYPE_HVGA:
					m_btClose.layout(m_ImageBack.GetW() - m_btClose.getW() - 5,
									m_ImageBack.GetH() / 2 - m_btClose.getH() / 2, r, b);
					break;
				case DF.DEVICETYPE_QVGA:
					m_btClose.layout(m_ImageBack.GetW() - m_btClose.getW() - 5,
									m_ImageBack.GetH() / 2 - m_btClose.getH() / 2, r, b);
					break;

			}
			// int tmpbian = (m_ImageBack.GetH() - 12) / 2;
			// int tmpy = (m_ImageBack.GetH() - 8) / 2 - tmpbian / 2;
			// m_ProBar.layout(10, tmpy, 10 + tmpbian, tmpy + tmpbian);

		}
	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		m_ImageBack.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_btClose.setVisibility(VISIBLE);
	}

	@Override
	protected void Render(Canvas canvas) {
		m_ImageBack.DrawImage(canvas, 0, 0);
		if (m_szInfo == null || m_szInfo.equals("")) {
			switch (CActivity.nDeviceType) {
				case DF.DEVICETYPE_WVGA:
					CText.DrawTextEllip(canvas, DF_INFO, 80, 15, 240, m_Paint);
					break;
				case DF.DEVICETYPE_HVGA:
					CText.DrawTextEllip(canvas, DF_INFO, 80, 5, 120, m_Paint);
					break;
				case DF.DEVICETYPE_QVGA:
					CText.DrawTextEllip(canvas, DF_INFO, 80, 5, 120, m_Paint);
					break;

			}

		}
	}

	public int GetW() {
		if (m_ImageBack != null)
			return m_ImageBack.GetW();
		return 0;
	}

	public int GetH() {
		if (m_ImageBack != null)
			return m_ImageBack.GetH();
		return 0;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		int id = view.getId();
		if (id == m_btClose.getId()) {
			setVisibility(INVISIBLE);
			return true;
		}
		return false;
	}

}
