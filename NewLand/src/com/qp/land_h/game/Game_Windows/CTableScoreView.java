package com.qp.land_h.game.Game_Windows;

import Lib_Graphics.CImage;
import Lib_Interface.IRangeObtain;
import Lib_System.View.ButtonView.CButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;

public class CTableScoreView extends CButton implements IRangeObtain {

	static CImage m_ImageScore;
	static {
		m_ImageScore = new CImage(GDF.GetContext(), ClientPlazz.RES_PATH
				+ "gameres/call_score.png", null, false);
	}
	public int point;

	public CTableScoreView(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setClickable(false);
		m_paint = new Paint();
		m_paint.setAntiAlias(true);
		m_paint.setTextAlign(Align.CENTER);
		m_paint.setTextSize(16);
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (m_ImageScore != null) {
			setMeasuredDimension(m_ImageScore.GetW() / 4, m_ImageScore.GetH());
		}
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageScore != null) {
			layout(l, t, l + m_ImageScore.GetW() / 4, t + m_ImageScore.GetH());
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (point == 0)
			return;
		int index = 0;
		switch (point) {
			case 1 :
				index = 0;
				break;
			case 2 :
				index = 1;
				break;
			case 3 :
				index = 2;
				break;
			default :
				index = 3;
				break;
		}
		m_ImageScore.DrawImage(canvas, 0, 0, m_ImageScore.GetW() / 4,
				m_ImageScore.GetH(), index * m_ImageScore.GetW() / 4, 0, 200);

	}

	@Override
	public int GetH() {
		if (m_ImageScore != null)
			return m_ImageScore.GetH();
		return 0;
	}

	@Override
	public int GetW() {
		if (m_ImageScore != null)
			return m_ImageScore.GetW() / 4;
		return 0;
	}

}
