package com.qp.land_h.plazz.Plazz_Fram.About;

import java.io.IOException;
import java.io.InputStream;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.qp.land_h.plazz.ClientPlazz;

public class CAboutView extends CViewEngine
		implements
		IRangeObtain,
		ISingleClickListener {

	CImageEx		m_ImageTop;
	CImageEx		m_ImageBack;
	CImageButton	m_btClose;
	TextView		textView;

	public CAboutView(Context context) {
		super(context);
		setWillNotDraw(false);
		setBackgroundDrawable(null);
		try {
			m_ImageTop = new CImageEx(ClientPlazz.RES_PATH + "about/top_help.png");
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "about/bg_help.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		m_btClose = new CImageButton(context, ClientPlazz.RES_PATH
				+ "custom/button/bt_close.png");
		m_btClose.setSingleClickListener(this);
		addView(m_btClose);
		try {
			InputStream is = ClientPlazz.GetInstance().getAssets()
					.open("txt/help.txt");
			if (is != null) {
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();

				String str = new String(buffer, "UTF-8");
				if (str != null && !"".equals(str)) {
					str = str.replace("\r\n", "\n");
					textView = new TextView(context);
					textView.setBackgroundDrawable(null);
					textView.setTextSize(13);
					textView.setText(str);
					textView.setHighlightColor(Color.WHITE);
					textView.setHintTextColor(Color.WHITE);
					textView.setMovementMethod(ScrollingMovementMethod
							.getInstance());
					addView(textView);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnDestoryRes() {
		m_ImageTop.OnReleaseImage();
		m_ImageBack.OnReleaseImage();
		m_btClose.setVisibility(INVISIBLE);
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageTop.OnReLoadImage();
			m_ImageBack.OnReLoadImage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		m_btClose.setVisibility(VISIBLE);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		m_btClose.layout(m_ImageBack.GetW() - m_btClose.getW() - 15, 5, 0, 0);

		textView.layout(15, m_ImageTop.GetH() + 15, m_ImageBack.GetW() - 15,
				m_ImageBack.GetH() - 30);
	}

	@Override
	protected void Render(Canvas canvas) {
		m_ImageBack.DrawImage(canvas, 0, 0);

		m_ImageTop.DrawImage(canvas, m_ImageBack.GetW() / 2 - m_ImageTop.GetW()
				/ 2, 10);
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setAntiAlias(true);
		p.setTextAlign(Align.CENTER);
		p.setTextSize(m_ImageTop.GetH() - 10);
		CText.DrawTextEllip(canvas, "”Œœ∑∞Ô÷˙", m_ImageBack.GetW() / 2, 10,
				m_ImageTop.GetW(), p);
	}

	@Override
	public int GetH() {
		if (m_ImageBack != null)
			return m_ImageBack.GetH();
		return 0;
	}

	@Override
	public int GetW() {
		if (m_ImageBack != null)
			return m_ImageBack.GetW();
		return 0;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		if (view.getId() == m_btClose.getId()) {
			//ClientPlazz.RemoveAboutView();
		}
		return false;
	}
}
