package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import com.qp.land_h.game.Game_Cmd.IFramesAnimationConrol;

import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ResInterface.IResManage;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * 帧动画
 * 
 * @node
 */
public class CFramesAnimation extends View implements IRangeObtain,IResManage,IFramesAnimationConrol{

	int			m_w;				// 宽
	int			m_h;				// 高
	int			m_nMaxIndex;		// 最大帧
	int			m_nCurrentIndex;	// 当前帧
	boolean		m_bPause;

	long		m_lSpaceTimes;		// 间隔时间

	boolean		m_bUpdate;			// 更新控制
	boolean		m_bLoop;			// 循环控制

	Thread		m_Thread;			// 更新线程
	CImageEx	m_ImageFrames[];	// 资源

	CFramesAnimation(Context context){
		super(context);
	}

	public CFramesAnimation(Context context,String respath,int maxframes){
		super(context);
		m_ImageFrames = new CImageEx[maxframes];
		m_nMaxIndex = maxframes;
		try {
			for (int i = 0; i < m_ImageFrames.length; i++)
				m_ImageFrames[i] = new CImageEx(respath + i + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_w = m_ImageFrames[0].GetW();
		m_h = m_ImageFrames[0].GetH();
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (!m_bUpdate || m_nCurrentIndex >= m_nMaxIndex)
			return;
		// 绘制
		m_ImageFrames[m_bPause?0:m_nCurrentIndex].DrawImage(canvas, 0, 0);

	}

	@Override
	public void Start(long spacetimes, boolean loop) {
		OnInitRes();

		if (m_bPause && m_bUpdate) {
			m_bPause = false;
			m_nCurrentIndex = 0;
			return;
		}
		if (m_bUpdate)
			Stop(false);

		m_nCurrentIndex = 0;
		m_bUpdate = true;
		postInvalidate();

		m_lSpaceTimes = spacetimes;
		m_bLoop = loop;

		m_Thread = new Thread(){

			@Override
			public void run() {
				while (m_bUpdate) {
					if (m_nCurrentIndex + 1 >= m_nMaxIndex) {
						if (m_bLoop) {
							m_nCurrentIndex = 0;
						} else {
							Stop(true);
						}
					} else {
						m_nCurrentIndex++;
					}
					if (m_bPause == false)
						postInvalidate();
					try {
						Thread.sleep(m_lSpaceTimes);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		m_Thread.start();
	}

	@Override
	public void Pause() {
		if (m_bLoop) {
			m_bPause = true;
			for (int i = 1; i < m_ImageFrames.length; i++)
				m_ImageFrames[i].OnReleaseImage();
		}
	}

	@Override
	public void Stop(boolean release) {
		m_bPause = false;
		m_bUpdate = false;
		if (m_Thread != null) {
			m_Thread.interrupt();
			m_Thread = null;
		}
		m_bLoop = false;
		m_lSpaceTimes = 0;
		m_nCurrentIndex = 0;
		if (release)
			OnDestoryRes();
		postInvalidate();
	}

	@Override
	public int GetW() {
		return m_w;
	}

	@Override
	public int GetH() {
		return m_h;
	}

	@Override
	public void OnDestoryRes() {
		for (int i = 0; i < m_ImageFrames.length; i++)
			m_ImageFrames[i].OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			for (int i = 0; i < m_ImageFrames.length; i++)
				m_ImageFrames[i].OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

}
