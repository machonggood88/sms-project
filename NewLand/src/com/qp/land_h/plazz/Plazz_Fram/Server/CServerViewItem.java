package com.qp.land_h.plazz.Plazz_Fram.Server;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_System.View.ButtonView.CButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Struct.tagServerItem;
import com.qp.land_h.plazz.df.PDF;

public class CServerViewItem extends CButton{

	/** 文字属性 **/
	protected static String	m_szRoom			= "房间名称：";
	protected static String	m_szOnLineCounts	= "在线人数：";
	/** 图片资源 **/
	public static CImageEx	m_ImageBack;
	public static CImageEx	m_ImageFlagFull;
	public static CImageEx	m_ImageNum;

	protected int			m_nRecordX;

	protected tagServerItem	m_ServerItem;

	static {
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "server/bg_fram.png");
			m_ImageFlagFull = new CImageEx(ClientPlazz.RES_PATH + "server/flag_full.png");
			m_ImageNum = new CImageEx(ClientPlazz.RES_PATH + "custom/score_number.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();
			m_ImageFlagFull.OnReLoadImage();
			m_ImageNum.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnDestoryRes() {
		m_ImageBack.OnReleaseImage();
		m_ImageFlagFull.OnReleaseImage();
		m_ImageNum.OnReleaseImage();
	}

	public CServerViewItem(Context context,tagServerItem serveritem,Paint paint){
		super(context);
		setBackgroundDrawable(null);
		if (paint == null) {
			m_paint = new Paint();
			m_paint.setAntiAlias(true);
			m_paint.setTextSize(20);
			m_paint.setStrokeWidth(10);
			m_paint.setColor(Color.WHITE);
		} else {
			m_paint = paint;
		}
		m_ServerItem = serveritem;
	}

	public CServerViewItem(Context context,int kindid,int serverid,int serverport,long online,long fullcount,
					String serverurl,String servername,Paint paint){
		super(context);
		setBackgroundDrawable(null);
		if (paint == null) {
			m_paint = new Paint();
			m_paint.setAntiAlias(true);
			m_paint.setTextSize(20);
			m_paint.setStrokeWidth(10);
			m_paint.setColor(Color.WHITE);
		} else {
			m_paint = paint;
		}
		m_ServerItem = new tagServerItem(kindid, serverid, serverport, online,
						fullcount, serverurl, servername);
	}

	public tagServerItem GetServerItem() {
		return m_ServerItem;
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (m_ImageBack != null) {
			setMeasuredDimension(m_ImageBack.GetW(), m_ImageBack.GetH());
		}
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageBack != null) {
			this.layout(l, t, l + m_ImageBack.GetW(), t + m_ImageBack.GetH());
		}
	}

	@Override
	public void onDraw(Canvas canvas) {

		m_ImageBack.DrawImage(canvas, 0, 0);

		if (m_ServerItem == null)
			return;

		if (m_ServerItem.lOnFullCount == m_ServerItem.lOnLineCount) {
			m_ImageFlagFull.DrawImage(canvas, m_ImageBack.GetW() - m_ImageFlagFull.GetW() - 5, 0);
		}
		int texth = (int)CText.GetTextHeight(m_paint);
		int yspace = m_ImageBack.GetH() / 4 - texth / 2;
		CText.DrawTextEllip(canvas, m_ServerItem.szServerName, 10, yspace, m_ImageBack.GetW() * 3 / 4, m_paint);
		CText.DrawTextEllip(canvas, "在线", 10, m_ImageBack.GetH() / 2 + yspace, m_ImageBack.GetW() * 3 / 4, m_paint);
		yspace = m_ImageBack.GetH() / 4 - m_ImageNum.GetH() / 2;
		CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum, m_ImageBack.GetW() - 15, m_ImageBack.GetH() / 2+yspace, "0123456789",
						m_ServerItem.lOnLineCount + "", PDF.STYLE_RIGHT);
	}

	// 获取宽
	public static int GetW() {
		if (m_ImageBack != null && m_ImageFlagFull != null) {
			return m_ImageBack.GetW();
		}
		return 0;
	}

	// 获取高
	public static int GetH() {
		if (m_ImageBack != null && m_ImageFlagFull != null) {
			return m_ImageBack.GetH();
		}
		return 0;
	}

	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2,
					float velocityX, float velocityY) {
	
		if (ScrollListenner != null) {

			int srcx = m_nRecordX;
			int dstx = (int)(this.getLeft() + event2.getX());
			return ScrollListenner.onScroll(this, event1, event2, srcx, dstx);
		}
		return isClickable();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				m_nRecordX = (int)event.getX() + this.getLeft();
				break;
			case MotionEvent.ACTION_UP:
				m_nRecordX = 0;
				break;
		}
		return super.onTouchEvent(event);
	}

}
