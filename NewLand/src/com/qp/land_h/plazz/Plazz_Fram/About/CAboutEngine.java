package com.qp.land_h.plazz.Plazz_Fram.About;

import java.io.IOException;
import java.io.InputStream;

import Lib_Graphics.CImage;
import Lib_Interface.IKeyBackDispatch;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.df.PDF;

public class CAboutEngine extends CViewEngine
		implements
			IKeyBackDispatch,
			ISingleClickListener {

	TextView textView;
	CImageButton m_btKeyBack;

	public CAboutEngine(final Context context) {
		super(context);
		Init(context);
		m_btKeyBack = new CImageButton(context, ClientPlazz.RES_PATH
				+ "custom/button/bt_cancel.png");
		m_btKeyBack.setSingleClickListener(this);
		addView(m_btKeyBack);
		setWillNotDraw(false);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int nx = 25;
		int ny = b - m_btKeyBack.getH();
		m_btKeyBack.layout(nx, ny, 0, 0);
		textView.layout(30, 55, r - 30, b - m_btKeyBack.getH() - 10);

	}

	private void Init(Context context) {
		try {
			InputStream is = CActivity.GetInstance().getAssets()
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
					textView.setTextSize(13);
					textView.setText(str);
					textView.setMovementMethod(ScrollingMovementMethod
							.getInstance());
					addView(textView);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
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
		// …Ë÷√±≥æ∞
		setBackgroundDrawable(new BitmapDrawable(new CImage(getContext(),
				ClientPlazz.RES_PATH + "custom/bg.png", null, true).GetBitMap()));

	}

	@Override
	protected void Render(Canvas canvas) {
		Paint p = new Paint();
		p.setTextSize(32);
		p.setTextAlign(Align.CENTER);
		p.setAntiAlias(true);
		p.setStrokeWidth(2.5f);
		p.setColor(Color.BLACK);
		canvas.drawText("", ClientPlazz.SCREEN_WIDHT / 2, 20, p);

	}

	@Override
	public boolean KeyBackDispatch() {
		PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_LOGIN,
				null);
		return true;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		if (view.getId() == m_btKeyBack.getId())
			return KeyBackDispatch();
		return false;
	}

}
