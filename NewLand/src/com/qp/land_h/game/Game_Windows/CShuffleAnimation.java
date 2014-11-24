package com.qp.land_h.game.Game_Windows;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Engine.CGameClientView;
import com.qp.land_h.plazz.ClientPlazz;

public class CShuffleAnimation extends View implements IRangeObtain {

	protected CImageEx				m_ImageFrame[]	= new CImageEx[24];

	protected boolean				m_bShuffle		= false;
	protected int					m_nMaxFrame		= 24;
	protected int					m_nCurrentFrame	= 24;
	protected int					m_nSpaceTime	= 30;

	protected ShuffleCardTimerTask	myTask;
	protected Timer					timer;

	/**
	 * ¼ÆÊ±Æ÷
	 */
	protected class ShuffleCardTimerTask extends TimerTask {

		@Override
		public void run() {
			if (!m_bShuffle)
				return;
			if (m_nCurrentFrame >= m_nMaxFrame) {
				GDF.SendMainMessage(CGameClientView.IDM_SHUFFLE_END, 0,
						ClientPlazz.GetGameClientView().GetTag(), null);
				StopAnimation();
			} else {
				m_ImageFrame[m_nCurrentFrame].OnReleaseImage();
				m_nCurrentFrame++;
				if (m_nCurrentFrame < m_nMaxFrame)
				{
					try {
						m_ImageFrame[m_nCurrentFrame].OnReLoadImage();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				postInvalidate();
			}
		}
	}

	public CShuffleAnimation(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setClickable(false);

		try {
			for (int i = 0; i < m_ImageFrame.length; i++) {
				m_ImageFrame[i] = new CImageEx(ClientPlazz.RES_PATH + "card/shuffle/send_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDraw(Canvas canvas) {
		if (m_nCurrentFrame < m_ImageFrame.length && m_bShuffle)
			m_ImageFrame[m_nCurrentFrame].DrawImage(canvas, 0, 0);
	}

	public void StartAnimation() {
		if (!m_bShuffle) {
			timer = new Timer();
			myTask = new ShuffleCardTimerTask();
			m_bShuffle = true;
			m_nCurrentFrame = 0;
			try {
				m_ImageFrame[m_nCurrentFrame].OnReLoadImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			postInvalidate();
			timer.schedule(myTask, 0, m_nSpaceTime);
		}
	}

	public void StopAnimation() {
		m_bShuffle = false;
		if (myTask != null) {
			myTask.cancel();
			myTask = null;
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (m_nCurrentFrame < m_nMaxFrame && m_ImageFrame[m_nCurrentFrame].GetBitMap() != null)
			m_ImageFrame[m_nCurrentFrame].OnReleaseImage();
		m_nCurrentFrame = m_nMaxFrame;
		postInvalidate();
	}

	@Override
	public int GetH() {
		if (m_ImageFrame[0] != null)
			return m_ImageFrame[0].GetH();
		return 0;
	}

	@Override
	public int GetW() {
		if (m_ImageFrame[0] != null)
			return m_ImageFrame[0].GetW();
		return 0;
	}

}
