package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import com.qp.land_h.plazz.ClientPlazz;

import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class CRocketView extends View
				implements
				IRangeObtain,
				AnimationListener{

	final static int	ROCKET_MAX_INDEX	= 6;
	static CImageEx		m_ImageRocket[]		= new CImageEx[ROCKET_MAX_INDEX];
	static int			m_w					= 0;
	static int			m_h					= 0;

	int					m_nCurrentIndex;

	static {
		try {
			for (int i = 0; i < m_ImageRocket.length; i++) {
				m_ImageRocket[i] = new CImageEx(ClientPlazz.RES_PATH + "gameres/animation/rocket/rocket_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_w = m_ImageRocket[0].GetW();
		m_h = m_ImageRocket[1].GetH();
	}

	public static void OnInitRes() {
		try {
			for (int i = 0; i < m_ImageRocket.length; i++)
				m_ImageRocket[i].OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnDestoryRes() {
		for (int i = 0; i < m_ImageRocket.length; i++)
			m_ImageRocket[i].OnReleaseImage();
	}

	public CRocketView(Context context){
		super(context);
		setBackgroundDrawable(null);
		setClickable(false);

	}

	@Override
	public void setVisibility(int visibility) {
		if (visibility == VISIBLE) {
			OnInitRes();
			m_nCurrentIndex = 0;
		}
		super.setVisibility(visibility);
		if (visibility == INVISIBLE) {
			OnDestoryRes();
		}
	}

	@Override
	public void startAnimation(Animation ani) {
		ani.setAnimationListener(this);
		super.setAnimation(ani);
	}

	@Override
	public void onDraw(Canvas canvas) {
		m_ImageRocket[m_nCurrentIndex % ROCKET_MAX_INDEX].DrawImage(canvas, 0, 0);
		m_nCurrentIndex++;
	}

	@Override
	public int GetH() {

		return m_h;
	}

	@Override
	public int GetW() {

		return m_w;
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		super.onAnimationEnd();
		this.setVisibility(INVISIBLE);
		OnDestoryRes();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onAnimationStart(Animation animation) {
		super.onAnimationStart();
	}
}
