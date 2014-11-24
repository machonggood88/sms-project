package com.qp.land_h.plazz.Plazz_Control;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Handle.CTagID;
import Lib_Interface.ResInterface.IResManage;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.Button;

import com.qp.land_h.plazz.Plazz_Interface.ISeekControl;

public class CSeekBar extends Button implements IResManage {

	CImageEx		m_ImagePoint;

	CImageEx		m_ImageBack;

	CImageEx		m_ImageFull;

	int				nCurrentPos	= 0;

	CTagID			m_TagID;

	ISeekControl	SeekControl;

	int				nMax		= 100;
	int				nProgress	= 0;

	public void OnInitRes() {
		try {
			m_ImagePoint.OnReLoadImage();
			m_ImageBack.OnReLoadImage();
			m_ImageFull.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void OnDestoryRes() {
		m_ImagePoint.OnReleaseImage();
		m_ImageBack.OnReleaseImage();
		m_ImageFull.OnReleaseImage();
	}

	CSeekBar(Context context) {
		super(context);
	}

	public CSeekBar(Context context, String respoint, String resbg, String resfull) {
		super(context);
		m_TagID = new CTagID();
		setId(m_TagID.GetTag());
		setBackgroundDrawable(null);

		try {
			m_ImagePoint = new CImageEx(respoint);
			m_ImageBack = new CImageEx(resbg);
			m_ImageFull = new CImageEx(resfull);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (m_ImageBack != null && m_ImagePoint != null) {
			setMeasuredDimension(m_ImageBack.GetW(), m_ImagePoint.GetH());
		}
	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (m_ImageBack != null && m_ImagePoint != null) {
			this.layout(l, t, l + m_ImageBack.GetW(), t + m_ImagePoint.GetH());
		}
	}

	@Override
	public void onDraw(Canvas canvas) {

		int BackPosY = m_ImagePoint.GetH() / 2 - m_ImageBack.GetH() / 2;
		m_ImageBack.DrawImage(canvas, 0, BackPosY);

		m_ImageFull.DrawImage(canvas, 0, BackPosY,
				nCurrentPos + m_ImagePoint.GetW() / 2,
				BackPosY + m_ImageFull.GetH());

		m_ImagePoint.DrawImage(canvas, nCurrentPos, 0);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int x = (int) event.getX();
		int changepos = 0;
		if (x < 0)
			changepos = 0;
		else if (x > m_ImageBack.GetW() - m_ImagePoint.GetW())
			changepos = m_ImageBack.GetW() - m_ImagePoint.GetW();
		else
			changepos = x;

		if (changepos != nCurrentPos) {
			nCurrentPos = changepos;
			OnPointChange();
			postInvalidate();
		}

		return isClickable();
	}

	private void OnPointChange() {
		nProgress = nMax * nCurrentPos
				/ (m_ImageBack.GetW() - m_ImagePoint.GetW());
		if (SeekControl != null)
			SeekControl.OnSeekChange(this);
	}

	public void SetMax(int max) {
		nMax = max;
		if (nProgress > nMax)
			nProgress = nMax;
	}

	public void SetProgress(int progress, boolean control) {
		if (progress >= 0 && progress <= nMax && nProgress != progress) {
			nProgress = progress;
			nCurrentPos = (m_ImageBack.GetW() - m_ImagePoint.GetW())
					* nProgress / nMax;
			postInvalidate();
			if (SeekControl != null && control)
				SeekControl.OnSeekChange(this);
		}
	}

	public int GetMax() {
		return nMax;
	}

	public int GetProgress() {
		return nProgress;
	}

	public void SetSeekControl(ISeekControl seekcontrol) {
		SeekControl = seekcontrol;
	}

	public int GetW() {
		if (m_ImageBack != null)
			return m_ImageBack.GetW();
		return 0;
	}

	public int GetH() {
		if (m_ImagePoint != null)
			return m_ImagePoint.GetH();
		return 0;
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}
}
