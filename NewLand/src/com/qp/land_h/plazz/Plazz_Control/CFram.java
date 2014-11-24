package com.qp.land_h.plazz.Plazz_Control;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ResInterface.IResManage;
import Lib_System.CActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.widget.Button;

public class CFram extends Button implements IRangeObtain,IResManage{

	CImageEx	m_ImageBack;
	String		m_szmsg	= "";
	int			m_width;
	int			m_height;
	Paint		m_paint	= new Paint();

	public CFram(Context context,String respath,int w,int h){
		super(context);
		setBackgroundDrawable(null);
		setClickable(false);
		if (respath != null && !respath.equals("")) {
			try {
				m_ImageBack = new CImageEx(respath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			m_ImageBack = null;
		}
		m_width = w;
		m_height = h;
		m_paint.setColor(Color.WHITE);
		m_paint.setAntiAlias(true);
		m_paint.setStrokeWidth(4);
		m_paint.setTextAlign(Align.CENTER);
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				m_paint.setTextSize(30);
				break;
			case DF.DEVICETYPE_HVGA:
				m_paint.setTextSize(18);
				break;
			case DF.DEVICETYPE_QVGA:
				m_paint.setTextSize(12);
				break;

		}

	}

	public void SetMsg(String msg) {
		m_szmsg = msg;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (m_ImageBack != null) {
			Rect rcsrc = new Rect(0, 0, m_ImageBack.GetW(), m_ImageBack.GetH());
			Rect rcdst = new Rect(0, 0, m_width, m_height);
			canvas.drawBitmap(m_ImageBack.GetBitMap(), rcsrc, rcdst, new Paint());

		}
		if (m_szmsg != null && !m_szmsg.equals("")) {
			CText.DrawTextEllip(canvas, m_szmsg, m_width / 2, 2, m_width, m_paint);
		}
	}

	@Override
	public int GetH() {
		return m_height;
	}

	@Override
	public int GetW() {
		return m_width;
	}

	@Override
	public void OnDestoryRes() {
		if (m_ImageBack != null)
			m_ImageBack.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		if (m_ImageBack != null) {
			try {
				m_ImageBack.OnReLoadImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

}
