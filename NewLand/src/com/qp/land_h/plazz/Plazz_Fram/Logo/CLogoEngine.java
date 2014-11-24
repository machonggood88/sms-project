package com.qp.land_h.plazz.Plazz_Fram.Logo;

import Lib_Graphics.CImage;
import Lib_System.View.CViewEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ProgressBar;

import com.qp.land_h.plazz.ClientPlazz;

public class CLogoEngine extends CViewEngine {

	protected ProgressBar	m_ProBar;

	public CLogoEngine(Context context) {
		super(context);
		m_ProBar = new ProgressBar(context);
		addView(m_ProBar);
		m_ProBar.layout(ClientPlazz.SCREEN_WIDHT / 2 - 32,
				ClientPlazz.SCREEN_HEIGHT / 2 - 32,
				ClientPlazz.SCREEN_WIDHT / 2 + 32,
				ClientPlazz.SCREEN_HEIGHT / 2 + 32);
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnDestoryRes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnInitRes() {
		setBackgroundDrawable(new BitmapDrawable(
				new CImage(getContext(), ClientPlazz.RES_PATH + "welcome/welcome.png", null, true).GetBitMap()));
	}

	@Override
	protected void Render(Canvas arg0) {
	

	}

	public void OnStart() {
		ClientPlazz.GetInstance().LoadFram(0, null);
	}

}
