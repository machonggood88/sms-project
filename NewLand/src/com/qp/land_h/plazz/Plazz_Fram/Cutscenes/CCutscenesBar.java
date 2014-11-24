package com.qp.land_h.plazz.Plazz_Fram.Cutscenes;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ResInterface.IResManage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class CCutscenesBar extends View implements IRangeObtain,IResManage{

	final static int	MAX_PRO		= 100;

	/** 资源 **/
	CImageEx			m_ImagePoint;
	CImageEx			m_ImageBack;
	CImageEx			m_ImageFull;
	Paint				m_Paint;

	int					nProgress	= 0;	// 显示进度
	int					nDest		= 0;	// 目标进度
	String				szMsg		= "";	// 文本信息

	/** 刷新控制 **/
	boolean				m_bUpdate;
	Thread				m_Thread;

	/** 完成响应 **/
	ISeekFinish			m_SeekFinish;

	public CCutscenesBar(Context context,String respoint,String resbg,String resfull,ISeekFinish SeekFinish){
		super(context);
		setBackgroundDrawable(null);

		m_SeekFinish = SeekFinish;

		m_Paint = new Paint();
		m_Paint.setAntiAlias(true);
		m_Paint.setTextSize(16);
		m_Paint.setColor(Color.GREEN);

		try {
			m_ImagePoint = new CImageEx(respoint);
			m_ImageBack = new CImageEx(resbg);
			m_ImageFull = new CImageEx(resfull);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SetTextSize(int size) {
		m_Paint.setTextSize(size);
	}

	@Override
	public void onDraw(Canvas canvas) {

		int texth = (int)CText.GetTextHeight(m_Paint) + 2;
		int x = m_ImagePoint.GetW() / 2;
		int w = nProgress * m_ImageFull.GetW() / MAX_PRO;
		int y = texth + m_ImagePoint.GetH() / 2 - m_ImageBack.GetH() / 2;

		if (szMsg != null && !szMsg.equals("")) {
			CText.DrawTextEllip(canvas, szMsg, x, 0, m_ImageBack.GetW(), m_Paint);
		}

		m_ImageBack.DrawImage(canvas, x, y);

		if (w > 0) {
			m_ImageFull.DrawImage(canvas, x, y, w, m_ImageFull.GetH());
		}

		m_ImagePoint.DrawImage(canvas, w, texth);

		if (nProgress == MAX_PRO) {
			m_SeekFinish.OnSeekFinish();
			return;
		}
	}

	@Override
	public int GetH() {
		return m_ImagePoint.GetH() + (int)CText.GetTextHeight(m_Paint) + 2;
	}

	@Override
	public int GetW() {

		return m_ImageBack.GetW() + m_ImagePoint.GetW();

	}

	/** 更新进度 **/
	public void SetInfo(int progress, String msg) {
		Log.d("进度TEST", "*设置进度*进度：" + progress + "描述:" + msg);
		if (progress < nProgress || progress > MAX_PRO)
			return;
		nDest = progress;
		if (msg != null) {
			szMsg = msg;
		}
	}

	/** 清空进度信息 **/
	public void Empty() {
		nProgress = 0;
		nDest = 0;
		szMsg = "";
	}

	/** 更新文字 **/
	public void SetTitle(String msg) {
		if (msg != null) {
			szMsg = msg;
		}
	}

	@Override
	public void OnDestoryRes() {
		Empty();
		OnStop();
		m_ImagePoint.OnReleaseImage();
		m_ImageBack.OnReleaseImage();
		m_ImageFull.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImagePoint.OnReLoadImage();
			m_ImageBack.OnReLoadImage();
			m_ImageFull.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OnStart();
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	/** 开始更新 **/
	public void OnStart() {
		Empty();
		if (m_Thread != null)
			OnStop();
		m_Thread = new Thread(){

			@Override
			public void run() {
				while (m_bUpdate) {
					if (nProgress == MAX_PRO) {
						return;
					}
					if (nProgress < nDest) {
						nProgress++;
						postInvalidate();
					}
					try {
						Thread.sleep(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		m_bUpdate = true;

		m_Thread.start();
	}

	/** 停止更新 **/
	public void OnStop() {
		if (m_Thread != null) {
			m_bUpdate = false;
			m_Thread.interrupt();
		}
		m_Thread = null;
		Empty();
	}

}