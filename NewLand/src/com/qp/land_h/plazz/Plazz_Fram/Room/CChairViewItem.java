package com.qp.land_h.plazz.Plazz_Fram.Room;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.CActivity;
import Lib_System.View.ButtonView.CButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.df.PDF;

public class CChairViewItem extends CButton{

	protected IClientUserItem	Useritem	= null;

	public static CImageEx		m_ImageChair;
	public static CImageEx		m_ImageFlagPlay;
	public static CImageEx		m_ImageFlagReady;

	private int					m_nChairID;

	static {
		try {
			m_ImageChair = new CImageEx(ClientPlazz.RES_PATH + "room/chair.png");

			m_ImageFlagPlay = new CImageEx(ClientPlazz.RES_PATH + "room/flag_play.png");
			m_ImageFlagReady = new CImageEx(ClientPlazz.RES_PATH + "room/flag_ready.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void OnInitRes() {
		try {
			m_ImageChair.OnReLoadImage();

			m_ImageFlagPlay.OnReLoadImage();
			m_ImageFlagReady.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnDestoryRes() {
		m_ImageChair.OnReleaseImage();

		m_ImageFlagPlay.OnReleaseImage();
		m_ImageFlagReady.OnReleaseImage();
	}

	public CChairViewItem(Context context,int chair){
		super(context);
		setBackgroundDrawable(null);
		m_nChairID = chair;
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (m_ImageChair != null)
			setMeasuredDimension(m_ImageChair.GetW(),
							m_ImageChair.GetH());
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageChair != null)
			layout(l, t, l + m_ImageChair.GetW(), t + m_ImageChair.GetH());
	}

	@Override
	public void onDraw(Canvas canvas) {
		int space = 0;
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				space = 12;
				break;
			case DF.DEVICETYPE_HVGA:
				space = 7;
				break;
			case DF.DEVICETYPE_QVGA:
				space = 5;
				break;

		}

		m_ImageChair.DrawImage(canvas, 0, 0);
		if (Useritem != null) {

			CPlazzGraphics graphics = CPlazzGraphics.onCreate();
			graphics.DrawUserAvatar(canvas, space, space, Useritem);
			if (Useritem != null && Useritem.GetUserStatus() == PDF.US_READY) {
				m_ImageFlagReady
								.DrawImage(canvas, m_ImageChair.GetW() - m_ImageFlagReady.GetW(), m_ImageChair.GetH()
												- m_ImageFlagReady.GetH());
			} else if (Useritem != null
							&& Useritem.GetUserStatus() == PDF.US_PLAYING) {
				m_ImageFlagPlay.DrawImage(canvas, m_ImageChair.GetW() - m_ImageFlagPlay.GetW(), m_ImageChair.GetH()
								- m_ImageFlagPlay.GetH());
			}
		}
	}

	public static int GetW() {
		if (m_ImageChair != null)
			return m_ImageChair.GetW();
		return 0;
	}

	public static int GetH() {
		if (m_ImageChair != null)
			return m_ImageChair.GetH();
		return 0;
	}

	public void SetChairUserItem(IClientUserItem useritem) {
		this.Useritem = useritem;
		postInvalidate();
	}

	public IClientUserItem GetUserItem() {
		return Useritem;
	}

	/**
	 * 在touch down后又没有滑动（onScroll），又没有长按（onLongPress），然后Touchup时触发。
	 * 点击一下非常快的（不滑动）Touchup： onDown->onSingleTapUp->onSingleTapConfirmed
	 * 点击一下稍微慢点的（不滑动）Touchup：
	 * onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
	 */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		Rect rc = new Rect(0, 0, m_ImageChair.GetW(), m_ImageChair.GetH());
		if (rc.contains((int)event.getX(), (int)event.getY())) {
			return super.onSingleTapConfirmed(event);
		}
		return false;
	}

	public int GetChairID() {
		return m_nChairID;
	}

}
