package com.qp.land_h.game.Game_Windows;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import com.qp.land_h.plazz.ClientPlazz;

public class CBombAnimation extends View implements IRangeObtain {

	final static int		BOMB_MAX_INDEX	= 5;
	static CImageEx			m_ImageBomb[]	= new CImageEx[BOMB_MAX_INDEX];
	protected boolean		m_bBomb			= false;

	protected int			m_nCurrentFrame	= 0;
	protected int			m_nSpaceTime	= 100;
	protected static int	m_nw;
	protected static int	m_nh;
	protected BombTimerTask	myTask;
	protected Timer			timer;

	static {
		try {
			for (int i = 0; i < m_ImageBomb.length; i++)
				m_ImageBomb[i] = new CImageEx(ClientPlazz.RES_PATH + "gameres/animation/bomb/bomb_" + i + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_nw = m_ImageBomb[0].GetW();
		m_nh = m_ImageBomb[0].GetH();
	}

	public static void OnInitRes() {
		try {
			for (int i = 0; i < m_ImageBomb.length; i++)
				m_ImageBomb[i].OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnDestoryRes() {
		for (int i = 0; i < m_ImageBomb.length; i++)
			m_ImageBomb[i].OnReleaseImage();
	}

	/**
	 * ¼ÆÊ±Æ÷
	 */
	protected class BombTimerTask extends TimerTask {

		@Override
		public void run() {
			if (!m_bBomb)
				return;
			if (m_nCurrentFrame >= BOMB_MAX_INDEX) {
				StopAnimation(true);
			} else {
				m_nCurrentFrame++;
				postInvalidate();
			}
		}
	}

	public CBombAnimation(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setClickable(false);
	}

	@Override
	public void setVisibility(int visibility) {
		if (visibility == VISIBLE) {
			OnInitRes();
		}
		super.setVisibility(visibility);
		if (visibility == INVISIBLE) {
			OnDestoryRes();
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (m_nCurrentFrame < BOMB_MAX_INDEX && m_bBomb) {
			Log.d("BombAnimation","onDraw"+m_nCurrentFrame);
			m_ImageBomb[m_nCurrentFrame].DrawImage(canvas, 0, 0);
		}
	}

	public void StartAnimation() {
		if (!m_bBomb) {
			OnInitRes();
			m_nCurrentFrame = 0;
			setVisibility(VISIBLE);
			timer = new Timer();
			myTask = new BombTimerTask();
			m_bBomb = true;
			timer.schedule(myTask, m_nSpaceTime, m_nSpaceTime);
		}
	}

	public void StopAnimation(boolean release) {
		setVisibility(INVISIBLE);
		m_bBomb = false;
		if (myTask != null) {
			myTask.cancel();
			myTask = null;
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		m_nCurrentFrame = BOMB_MAX_INDEX;
		if (release)
			OnDestoryRes();
	}

	@Override
	public int GetH() {
		return m_nh;
	}

	@Override
	public int GetW() {
		return m_nw;
	}

}
