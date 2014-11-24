package com.qp.land_h.plazz.Plazz_Fram.Login;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Interface.IListControl;

public class CListView extends CViewEngine implements ISingleClickListener {

	public final static int	MAXCOUNT	= 5;

	private CImageButton[]	m_btDelet	= new CImageButton[MAXCOUNT];
	private Rect[]			m_rcSelect	= new Rect[MAXCOUNT];
	/** 状态记录 **/
	private Point			m_ptPressed;

	private CImageEx		m_ImageFramTop;
	private CImageEx		m_ImageFramCentre;
	private CImageEx		m_ImageFramBottom;
	private CImageEx		m_ImageFramSign;

	private CImageEx		m_ImageMaskFramTop;
	private CImageEx		m_ImageMaskFramCentre;
	private CImageEx		m_ImageMaskFramBottom;
	private CImageEx		m_ImageMaskFramSign;

	private Paint			m_Paint;

	private IListControl	m_ListControl;

	public CListView(Context context) {
		super(context);
		// 设置绘制
		setWillNotDraw(false);
		setBackgroundDrawable(null);
		m_Paint = new Paint();
		m_Paint.setTextSize(28);
		m_Paint.setAntiAlias(true);
		m_ptPressed = new Point(0, 0);

		try {
			m_ImageFramTop = new CImageEx(ClientPlazz.RES_PATH + "login/list_top.png");
			m_ImageFramCentre = new CImageEx(ClientPlazz.RES_PATH + "login/list_centre.png");
			m_ImageFramBottom = new CImageEx(ClientPlazz.RES_PATH + "login/list_bottom.png");
			m_ImageFramSign = new CImageEx(ClientPlazz.RES_PATH + "login/list_single.png");
			m_ImageMaskFramTop = new CImageEx(ClientPlazz.RES_PATH + "login/list_mask_top.png");
			m_ImageMaskFramCentre = new CImageEx(ClientPlazz.RES_PATH + "login/list_mask_centre.png");
			m_ImageMaskFramBottom = new CImageEx(ClientPlazz.RES_PATH + "login/list_mask_bottom.png");
			m_ImageMaskFramSign = new CImageEx(ClientPlazz.RES_PATH + "login/list_mask_single.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < MAXCOUNT; i++) {
			m_rcSelect[i] = new Rect(0, 2 + i * m_ImageFramCentre.GetH(),
					m_ImageFramCentre.GetW(), 2 + (i + 1)
							* m_ImageFramCentre.GetH());
			m_btDelet[i] = new CImageButton(context, ClientPlazz.RES_PATH
					+ "login/bt_delete.png");
			addView(m_btDelet[i]);
			m_btDelet[i].layout(m_rcSelect[i].right - m_btDelet[i].getW() - 5,
					m_rcSelect[i].top + m_ImageFramCentre.GetH() / 2
							- m_btDelet[i].getH() / 2 - 2, 0, 0);
			m_btDelet[i].setVisibility(INVISIBLE);
			m_btDelet[i].setSingleClickListener(this);
		}

	}

	public void SetListControlListener(IListControl listcontrol) {
		m_ListControl = listcontrol;
	}

	@Override
	public void ActivateView() {

	}

	/**
	 * 设置大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Point p = GetWH();
		setMeasuredDimension(p.x, p.y);

	}

	/**
	 * 设置位置
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Point p = GetWH();

		this.layout(l, t, l + p.x, t + p.y);
	}

	/**
	 * 计算区域
	 * 
	 * @return
	 */
	private Point GetWH() {
		Point p = new Point(0, 0);
		int count = 0;
		if (m_ListControl != null) {
			count = m_ListControl.GetItemCount() > MAXCOUNT
					? MAXCOUNT
					: m_ListControl.GetItemCount();

			switch (count) {
				case 0:
					break;
				case 1:
					m_btDelet[0].setVisibility(VISIBLE);
					p.x = m_ImageFramSign.GetW();
					p.y = m_ImageFramSign.GetH();
					break;
				default:
					p.x = m_ImageFramSign.GetW();
					p.y = m_ImageFramTop.GetH() + m_ImageMaskFramBottom.GetH()
							+ (m_ListControl.GetItemCount() - 2)
							* m_ImageFramCentre.GetH();
					break;
			}
		}
		return p;
	}

	@Override
	public void OnDestoryRes() {
		m_ImageFramTop.OnReleaseImage();
		m_ImageFramCentre.OnReleaseImage();
		m_ImageFramBottom.OnReleaseImage();
		m_ImageFramSign.OnReleaseImage();
		m_ImageMaskFramTop.OnReleaseImage();
		m_ImageMaskFramCentre.OnReleaseImage();
		m_ImageMaskFramBottom.OnReleaseImage();
		m_ImageMaskFramSign.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageFramTop.OnReLoadImage();
			m_ImageFramCentre.OnReLoadImage();
			m_ImageFramBottom.OnReLoadImage();
			m_ImageFramSign.OnReLoadImage();
			m_ImageMaskFramTop.OnReLoadImage();
			m_ImageMaskFramCentre.OnReLoadImage();
			m_ImageMaskFramBottom.OnReLoadImage();
			m_ImageMaskFramSign.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void Render(Canvas canvas) {
		int count = 0;
		int x = 0,y = 0;
		if (m_ListControl != null) {
			count = m_ListControl.GetItemCount() > MAXCOUNT
					? MAXCOUNT
					: m_ListControl.GetItemCount();
			if (count == 0)
				return;
			switch (count) {
				case 1: {
					DrawSign(canvas, m_rcSelect[0].contains(m_ptPressed.x,
							m_ptPressed.y));
					break;
				}
				default: {

					DrawTop(canvas, m_rcSelect[0].contains(m_ptPressed.x,
							m_ptPressed.y));
					y += m_ImageFramTop.GetH();
					for (int i = 0; i < count - 2; i++) {
						DrawCentre(canvas, x, y, m_rcSelect[i + 1].contains(
								m_ptPressed.x, m_ptPressed.y));
						y += m_ImageFramCentre.GetH();
					}
					DrawBottom(canvas, x, y, m_rcSelect[count - 1].contains(
							m_ptPressed.x, m_ptPressed.y));
					break;
				}
			}
			x = 10;
			y = 2;
			int yspace = (int) (m_ImageFramCentre.GetH() / 2 - CText
					.GetTextHeight(m_Paint) / 2);
			for (int i = 0; i < count; i++) {
				CText.DrawTextEllip(canvas, (String) m_ListControl.GetItem(i),
						x, y + yspace, m_ImageFramSign.GetW() - 20, m_Paint);
				y += m_ImageFramCentre.GetH();
			}
		}
	}

	/**
	 * 单个绘制
	 * 
	 * @param canvas
	 * @param bPressed
	 */
	private void DrawSign(Canvas canvas, boolean bPressed) {
		m_ImageFramSign.DrawImage(canvas, 0, 00);
		if (bPressed) {
			m_ImageMaskFramSign.DrawImage(canvas, 0, 0);
		}
	}

	/**
	 * 绘制顶部
	 * 
	 * @param canvas
	 * @param bPressed
	 */
	private void DrawTop(Canvas canvas, boolean bPressed) {
		m_ImageFramTop.DrawImage(canvas, 0, 0);
		if (bPressed) {
			m_ImageMaskFramTop.DrawImage(canvas, 0, 0);
		}
	}

	/**
	 * 绘制中间
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 * @param bPressed
	 */
	private void DrawCentre(Canvas canvas, int x, int y, boolean bPressed) {

		m_ImageFramCentre.DrawImage(canvas, x, y);
		if (bPressed) {
			m_ImageMaskFramCentre.DrawImage(canvas, x, y);
		}
	}

	/**
	 * 绘制底部
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 * @param bPressed
	 */
	private void DrawBottom(Canvas canvas, int x, int y, boolean bPressed) {
		m_ImageFramBottom.DrawImage(canvas, x, y);
		if (bPressed) {
			m_ImageMaskFramBottom.DrawImage(canvas, x, y);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int nAciton = event.getAction();
		int nXpos = (int) event.getX();
		int nYpos = (int) event.getY();

		switch (nAciton) {
			case MotionEvent.ACTION_DOWN: {
				m_ptPressed.set(nXpos, nYpos);
				postInvalidate();
				return true;
			}
			case MotionEvent.ACTION_MOVE: {
				m_ptPressed.set(nXpos, nYpos);
				postInvalidate();
				return true;
			}
			case MotionEvent.ACTION_UP: {
				m_ptPressed.set(0, 0);
				if (m_ListControl != null) {
					Rect rc = new Rect();
					getDrawingRect(rc);
					int count = m_ListControl.GetItemCount() > MAXCOUNT
							? MAXCOUNT
							: m_ListControl.GetItemCount();
					if (rc.contains(nXpos, nYpos)) {
						for (int i = 0; i < count; i++) {
							if (m_rcSelect[i].contains(nXpos, nYpos)) {
								m_ListControl.SeleteItem(i);
								break;
							}
						}
					}
				}
				postInvalidate();
				return true;
			}
		}

		return super.onTouchEvent(event);
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		int viewid = view.getId();
		if (m_ListControl != null) {
			for (int i = 0; i < m_btDelet.length; i++) {
				if (m_btDelet[i].getId() == viewid) {
					m_ListControl.DeleteItem(i);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 更新视图
	 */
	public void UpDataView() {
		for (CImageButton element : m_btDelet) {
			element.setVisibility(INVISIBLE);
		}

		if (m_ListControl != null) {
			String szaccounts;
			for (int i = 0; i < m_ListControl.GetItemCount(); i++) {
				szaccounts = (String) m_ListControl.GetItem(i);
				if (szaccounts != null && !szaccounts.equals("")) {
					m_btDelet[i].setVisibility(VISIBLE);
				}
			}
		}

		layout(this.getLeft(), this.getTop(), 0, 0);
	}

}
