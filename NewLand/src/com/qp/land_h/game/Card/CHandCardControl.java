package com.qp.land_h.game.Card;

import Lib_DF.DF;
import Lib_Interface.ButtonInterface.IDoubleClickListener;
import Lib_System.CActivity;
import Lib_System.View.ButtonView.CButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;

public class CHandCardControl extends CButton implements
/* ILongpressListener, */IDoubleClickListener {

	protected int	nSpaceX		= 35;
	protected int	nSpaceY		= 30;

	tagCardItem		CardItem[]	= new tagCardItem[GDF.MAX_CARDCOUNT];

	protected int	nCardCount	= 0;
	protected int	nShowCount	= 0;

	public CHandCardControl(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setDoubleClickListener(this);
		for (int i = 0; i < CardItem.length; i++) {
			CardItem[i] = new tagCardItem();
		}
		//nSpaceX = (ClientPlazz.SCREEN_WIDHT - CCard_Big.GetW()) / 19;
		nSpaceX = (ClientPlazz.SCREEN_WIDHT - CardModule.getWidth(0)) / 19;
		setDrawingCacheEnabled(true);
		if (CActivity.nDeviceType == DF.DEVICETYPE_QVGA) {
			nSpaceY = 20;
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (nCardCount > 0 && nShowCount > 0) {
			int drawcount = Math.min(nShowCount, nCardCount);
			int count = 0;
			for (int i = 0; i < CardItem.length; i++) {
				if (CardItem[i].data == 0)
					continue;
				if (count == drawcount)
					break;
				count++;
//				CCard_Big
//						.DrawCard(canvas, CardItem[i].pt.x, CardItem[i].shoot ? 0 : CardItem[i].pt.y, CardItem[i].data);
				CardModule.DrawCard(canvas,CardItem[i].pt.x, CardItem[i].shoot ? 0 : CardItem[i].pt.y, CardItem[i].data, CardItem[i].mask, 0);
//				if (CardItem[i].mask) {
//					CCard_Big.DrawMask(canvas, CardItem[i].pt.x, CardItem[i].shoot ? 0 : CardItem[i].pt.y);
//				}
			}
		}
	}

	/**
	 * ´¥Åö´¦Àí
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			for (int i = 0; i < CardItem.length; i++) {
				if (CardItem[i].data == 0)
					continue;
				if (CardItem[i].mask) {
					CardItem[i].shoot = !CardItem[i].shoot;
					CardItem[i].mask = false;
				}
			}
			invalidate();
		}
		return super.onTouchEvent(event);
	}

	public void SetAllCardMask(boolean mask) {
		for (int i = 0; i < CardItem.length; i++) {
			CardItem[i].mask = mask;
		}
	}

	public void SetAllCardShoot(boolean shoot) {
		boolean bRender = false;
		for (int i = 0; i < CardItem.length; i++) {
			if (CardItem[i].shoot != shoot)
				bRender = true;
			CardItem[i].shoot = shoot;
		}
		if (bRender)
			postInvalidate();
	}

	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		if (nShowCount == 0 || nCardCount == 0 || nShowCount < nCardCount)
			return true;
		int startx = (int) event1.getX();
		int nowx = (int) event2.getX();

		int max = startx > nowx ? startx : nowx;
		int min = startx > nowx ? nowx : startx;

		for (int i = 0; i < CardItem.length; i++) {
			if (CardItem[i].data == 0)
				continue;
			if (CardItem[i].pt.x > min && CardItem[i].pt.x < max) {
				CardItem[i].mask = true;
			} else if (CardItem[i].pt.x + nSpaceX > min && CardItem[i].pt.x + nSpaceX < max) {
				CardItem[i].mask = true;
			} else {
				CardItem[i].mask = false;
			}
		}
		invalidate();
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		if (nShowCount == 0 || nCardCount == 0 || nShowCount < nCardCount)
			return true;
		int count = 0;
		int x = (int) event.getX();
		int y = (int) event.getY();
		for (int i = 0; i < CardItem.length; i++) {
			if (CardItem[i].data == 0)
				continue;
			count++;
			int left = CardItem[i].pt.x;
			int right = left + (count == nCardCount ? CardModule.getWidth(0) : nSpaceX);
			int top = CardItem[i].shoot ? 0 : CardItem[i].pt.y;
			int bottom = top + CardModule.getHeight(0);
			Rect rc = new Rect(left, top, right, bottom);
			if (rc.contains(x, y)) {
				CardItem[i].shoot = !CardItem[i].shoot;
				invalidate();
				break;
			}
		}
		return true;
	}

	public void SetCardData(int data[], int count) {
		RestData();
		nCardCount = count;
		if (data != null) {
			for (int i = 0; i < count; i++) {
				CardItem[i].data = data[i];
			}
		} else {
			for (int i = 0; i < count; i++) {
				CardItem[i].data = 0;
			}
		}
		RectifyControl();
		postInvalidate();
	}

	public void RestData() {
		for (int i = 0; i < CardItem.length; i++) {
			CardItem[i].data = 0;
			CardItem[i].shoot = false;
			CardItem[i].mask = false;
			CardItem[i].pt.set(0, 0);
		}
	}

	public void RectifyControl() {
		if (nCardCount > 0 && nShowCount > 0) {
			int rectifycount = Math.min(nShowCount, nCardCount);
			int count = 0;
			int startx = (getWidth() - (rectifycount - 1) * nSpaceX - CardModule.getWidth(0)) / 2;
			for (int i = 0; i < CardItem.length; i++) {
				if (CardItem[i].data == 0)
					continue;
				if (count == rectifycount)
					break;
				CardItem[i].pt.set(startx + count * nSpaceX, nSpaceY);
				count++;
			}
		}
	}

	public int GetShootCard(int data[], boolean shoot) {
		int shootcount = 0;
		for (int i = 0; i < CardItem.length; i++) {
			if (CardItem[i].data == 0)
				continue;
			if (CardItem[i].shoot == shoot) {
				data[shootcount++] = CardItem[i].data;
			}
		}

		return shootcount;
	}

	public void SetCardShoot(int[] outcard, int ncount) {
		for (int j = 0; j < ncount; j++) {
			for (int i = 0; i < CardItem.length; i++) {
				if (CardItem[i].data == 0)
					continue;
				if (CardItem[i].data == outcard[j]) {
					CardItem[i].shoot = true;
					break;
				}
			}
		}
	}

	// @Override
	// public void onLongpress(View view) {
	// if (nShowCount == 0 || nCardCount == 0 || nShowCount < nCardCount)
	// return;
	// PDF.SendSubMessage(CGameClientView.IDM_GAME_SKIP, 0,
	// ClientPlazz.GetGameClientView().GetTag(), null);
	// }

	public void SetShowCount(int count) {
		nShowCount = count;
	}

	public int GetShowCount() {
		return nShowCount;
	}

	@Override
	public boolean OnDoubleClick(View view) {
		if (nShowCount == 0 || nCardCount == 0 || nShowCount < nCardCount)
			return true;
		SetAllCardShoot(false);
		return true;
	}
}
