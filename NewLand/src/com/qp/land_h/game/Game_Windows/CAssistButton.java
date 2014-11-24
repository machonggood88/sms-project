package com.qp.land_h.game.Game_Windows;

import Lib_DF.DF;
import Lib_Interface.ButtonInterface.IScrollListenner;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Button;

public class CAssistButton extends Button implements OnGestureListener {
	protected GestureDetector m_GestureScanner;

	protected IScrollListenner ScrollListenner;
	protected ISingleClickListener SingleClickListener;

	public CAssistButton(Context context) {
		super(context);
		setBackgroundDrawable(null);
		m_GestureScanner = new GestureDetector(context, this);
	}

	/**
	 * 触碰处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (m_GestureScanner != null) {
			return m_GestureScanner.onTouchEvent(event);
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return isClickable();
	}

	@Override
	public boolean onFling(MotionEvent e, MotionEvent e1, float arg2, float arg3) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (ScrollListenner != null) {
			return ScrollListenner.onScroll(this, e1, e2, distanceX, distanceY);
		}
		return isClickable();
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		if (SingleClickListener != null
				&& DF.InRect(this, (int) event.getX(), (int) event.getY()))
			return SingleClickListener.onSingleClick(this, event);
		return isClickable();
	}

	/** 添加单击处理 **/
	public boolean setSingleClickListener(
			ISingleClickListener singleclicklistener) {
		SingleClickListener = singleclicklistener;
		return true;
	}

	public boolean setScrollListenner(IScrollListenner scrolllistenner) {
		ScrollListenner = scrolllistenner;
		return true;
	}

}
