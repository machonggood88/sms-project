package com.qp.land_h.plazz.Plazz_Fram.Bank;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import Lib_Graphics.CText;
import Lib_System.View.ButtonView.CImageButton;

public class CSelectButton extends CImageButton {

	boolean	m_bSelect;

	public CSelectButton(Context context, String respath) {
		super(context, respath);
		// TODO Auto-generated constructor stub
	}

	public void SetSelect(boolean select) {
		if (m_bSelect != select) {
			m_bSelect = select;
			postInvalidate();
		}
	}

	public boolean IsSelect() {
		return m_bSelect;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// �Ի�
		if (m_ImageBack != null) {
			// ��������
			int w = getW();
			int h = getH();

			if (isClickable()) {
				if (m_bSelect)
					m_ImageBack.DrawImage(canvas, 0, 0, w, h, w, 0);
				else
					m_ImageBack.DrawImage(canvas, 0, 0);
			} else {
				// ��Ҵ���
				Paint p = new Paint();
				ColorMatrix cm = new ColorMatrix();
				cm.setSaturation(0);
				ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
				p.setColorFilter(cf);

				m_ImageBack.DrawImage(canvas, 0, 0, w, h, p);

			}
			// ���ֻ���
			if (m_szMessage != null && !m_szMessage.equals("")) {
				CText.DrawTextEllip(canvas, m_szMessage, w / 2, h / 2
						- (int) CText.GetTextHeight(m_paint) / 2, w, m_paint);
			}
		}
	}

}
