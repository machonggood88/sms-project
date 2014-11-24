package com.qp.land_h.plazz.Plazz_Fram.Register;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Interface.IListControl;

public class CSelectFace extends CViewEngine{

	// 间距
	Rect			m_rcFram;
	// x ,y 代表头像间的x y 间距
	Point			m_ptSpace;
	// 性别标签
	boolean			m_bManKind		= true;
	// 底图
	CImageEx		m_ImageFram;
	// 控制接口
	IListControl	ListControl;
	// 判断区域
	Rect			m_rcTouchRect[]	= new Rect[8];

	public CSelectFace(Context context){
		super(context);
		setWillNotDraw(false);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				m_rcFram = new Rect(12, 12, 12, 12);
				m_ptSpace = new Point(12, 15);
				break;
			case DF.DEVICETYPE_HVGA:
				m_rcFram = new Rect(7, 7, 7, 7);
				m_ptSpace = new Point(8, 10);
				break;
			case DF.DEVICETYPE_QVGA:
				m_rcFram = new Rect(6, 5, 6, 6);
				m_ptSpace = new Point(7, 9);
				break;
		}

		try {
			m_ImageFram = new CImageEx(ClientPlazz.RES_PATH + "register/bg_select.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		CPlazzGraphics plazzgraphics = CPlazzGraphics.onCreate();
		int x = 0,y = 0;
		int w = plazzgraphics.GetFaceW();
		int h = plazzgraphics.GetFaceH();
		for (int i = 0; i < m_rcTouchRect.length; i++) {
			m_rcTouchRect[i] = new Rect();
			x = m_rcFram.left + i % 4 * m_ptSpace.x + i % 4 * w + 2;
			y = m_rcFram.top
							+ (i < 4?0:plazzgraphics.GetFaceH() + m_ptSpace.y) + 2;
			m_rcTouchRect[i].set(x, y, x + w, y + h);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageFram != null) {
			layout(l, t, l + m_ImageFram.GetW(), t + m_ImageFram.GetH());
		}
	}

	/**
	 * 设置控制接口
	 * 
	 * @param listcontrol
	 */
	public void SetListControlListener(IListControl listcontrol) {
		ListControl = listcontrol;

	}

	@Override
	public void Render(Canvas canvas) {
		m_ImageFram.DrawImage(canvas, 0, 0, 220);
		CPlazzGraphics plazzgraphics = CPlazzGraphics.onCreate();
		for (int i = 0; i < 8; i++) {
			plazzgraphics.DrawUserAvatar(canvas, m_rcTouchRect[i].left,
							m_rcTouchRect[i].top, i + (m_bManKind?0:8));
		}
	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		m_ImageFram.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageFram.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int aciton = event.getAction();
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch (aciton) {
			case MotionEvent.ACTION_DOWN: {
				return true;
			}
			case MotionEvent.ACTION_MOVE: {
				return true;
			}
			case MotionEvent.ACTION_UP: {
				for (int i = 0; i < m_rcTouchRect.length; i++) {
					if (m_rcTouchRect[i].contains(x, y)) {
						if (ListControl != null) {
							ListControl.SeleteItem(i);
						}
						break;
					}
				}

				return true;
			}
		}
		return true;
	}

	public void SetGenderMode(boolean mankind) {
		m_bManKind = mankind;
		postInvalidate();
	}
}
