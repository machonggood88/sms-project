package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import com.qp.land_h.plazz.ClientPlazz;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;

public class CPlaneView extends View implements IRangeObtain, AnimationListener {

	final static int	PLANE_MAX_INDEX	= 5;

	static CImageEx		m_ImagePlane[]	= new CImageEx[PLANE_MAX_INDEX];
	static int			m_w					= 0;
	static int			m_h					= 0;
	int					m_nCurrentIndex;

	static {
		try {
			for (int i = 0; i < m_ImagePlane.length; i++) {
				m_ImagePlane[i] = new CImageEx(ClientPlazz.RES_PATH + "gameres/animation/plane/plane_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_w = m_ImagePlane[0].GetW();
		m_h = m_ImagePlane[1].GetH();
	}

	public CPlaneView(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setClickable(false);

	}

	public static void OnInitRes() {
		try {
			for (int i = 0; i < m_ImagePlane.length; i++)
				m_ImagePlane[i].OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnDestoryRes() {
		for (int i = 0; i < m_ImagePlane.length; i++)
			m_ImagePlane[i].OnReleaseImage();
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
		m_ImagePlane[m_nCurrentIndex % PLANE_MAX_INDEX].DrawImage(canvas, 0, 0);
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
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onAnimationStart(Animation animation) {
		super.onAnimationStart();
	}
}
