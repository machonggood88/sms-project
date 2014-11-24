package com.qp.land_h.plazz.Plazz_Fram.Server;

import Lib_Interface.ButtonInterface.IScrollListenner;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Button;

public class CServerAssist extends Button implements OnGestureListener {

	protected GestureDetector m_GestureScanner;

	protected IScrollListenner ScrollListenner;

	public CServerAssist(Context context) {
		super(context);
		setBackgroundDrawable(null);
		m_GestureScanner = new GestureDetector(context, this);
	}

	public boolean setScrollListenner(IScrollListenner scrolllistenner) {
		ScrollListenner = scrolllistenner;
		return true;
	}

	/**
	 * ´¥Åö´¦Àí
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
			Log.i(this.getClass().getName(),"onScroll-");
			return ScrollListenner.onScroll(this, e1, e2, distanceX, distanceY);
		}
		return isClickable();
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return isClickable();
	}

}
