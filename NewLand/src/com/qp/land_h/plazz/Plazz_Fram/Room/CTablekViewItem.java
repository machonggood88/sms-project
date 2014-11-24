package com.qp.land_h.plazz.Plazz_Fram.Room;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Interface.IClientKernel;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.df.PDF;

public class CTablekViewItem extends CViewEngine
				implements
				ISingleClickListener{

	CChairViewItem				m_Chair[];

	int							m_nTableID;

	protected static CImageEx	m_ImageBack;
	public static CImageEx		m_ImageNum;
	protected static Paint		m_paint;

	static {
		m_paint = new Paint();
		m_paint.setAntiAlias(true);
		m_paint.setTextSize(32);
		m_paint.setColor(Color.WHITE);
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "room/bg_fram.png");
			m_ImageNum = new CImageEx(ClientPlazz.RES_PATH + "custom/score_number.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CTablekViewItem(Context context,int table){
		super(context);
		setWillNotDraw(false);
		m_nTableID = table;

		IClientKernel kernel = ClientPlazz.GetKernel();
		int count = kernel.GetGameAttribute().ChairCount;
		m_Chair = new CChairViewItem[count];
		for (int i = 0; i < m_Chair.length; i++) {
			m_Chair[i] = new CChairViewItem(context, i);
			m_Chair[i].setSingleClickListener(this);
			addView(m_Chair[i]);
		}
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

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				onLayoutH(changed, l, t, r, b);
				break;
			case DF.DEVICETYPE_HVGA:
				onLayoutM(changed, l, t, r, b);
				break;
			case DF.DEVICETYPE_QVGA:
				onLayoutL(changed, l, t, r, b);
				break;

		}

	}

	private void onLayoutL(boolean changed, int l, int t, int r, int b) {
		if (m_ImageBack != null) {
			this.layout(l, t, l + m_ImageBack.GetW(), t + m_ImageBack.GetH());

			if (m_Chair != null) {
				int totel = 45;
				int chairwidth = CChairViewItem.GetW();
				int xspace = (m_ImageBack.GetW() - totel - chairwidth * 3) / (m_Chair.length + 1);
				int yspace = (m_ImageBack.GetH() - CChairViewItem.GetH()) / 2;
				for (int i = 0; i < m_Chair.length; i++) {
					m_Chair[i].layout(totel + xspace + i * (xspace + chairwidth), yspace, 0, 0);
				}
			}
		}
	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		if (m_ImageBack != null) {
			this.layout(l, t, l + m_ImageBack.GetW(), t + m_ImageBack.GetH());

			if (m_Chair != null) {
				int totel = 35;
				int chairwidth = CChairViewItem.GetW();
				int xspace = (m_ImageBack.GetW() - totel - chairwidth * 3) / (m_Chair.length + 1);
				int yspace = (m_ImageBack.GetH() - CChairViewItem.GetH()) / 2;
				for (int i = 0; i < m_Chair.length; i++) {
					m_Chair[i].layout(totel + xspace + i * (xspace + chairwidth), yspace, 0, 0);
				}
			}
		}
	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		if (m_ImageBack != null) {
			this.layout(l, t, l + m_ImageBack.GetW(), t + m_ImageBack.GetH());

			if (m_Chair != null) {
				int totel = 60;
				int chairwidth = CChairViewItem.GetW();
				int xspace = (m_ImageBack.GetW() - totel - chairwidth * 3) / (m_Chair.length + 1);
				int yspace = (m_ImageBack.GetH() - CChairViewItem.GetH()) / 2;
				for (int i = 0; i < m_Chair.length; i++) {
					m_Chair[i].layout(totel + xspace + i * (xspace + chairwidth), yspace, 0, 0);
				}
			}
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnDestoryRes() {

	}

	@Override
	public void OnInitRes() {

	}

	public static void OnDestoryResEx() {

		m_ImageBack.OnReleaseImage();
		m_ImageNum.OnReleaseImage();

	}

	public static void OnInitResEx() {

		try {
			m_ImageNum.OnReLoadImage();
			m_ImageBack.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void Render(Canvas canvas) {
		m_ImageBack.DrawImage(canvas, 0, 0);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
			case DF.DEVICETYPE_HVGA:
				CPlazzGraphics.DrawVerticalNum(canvas, m_ImageNum, 8, 5, "0123456789", m_nTableID + "", PDF.STYLE_TOP);
				break;	
			case DF.DEVICETYPE_QVGA:
				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum, m_ImageBack.GetH() / 2 - m_ImageNum.GetH() / 2, 5,
								"0123456789", m_nTableID + "", PDF.STYLE_LEFT);
				break;
		}
	}

	public static int GetW() {
		if (m_ImageBack != null)
			return m_ImageBack.GetW();
		return 0;
	}

	public static int GetH() {
		if (m_ImageBack != null)
			return m_ImageBack.GetH();
		return 0;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		CRoomEngine room = ClientPlazz.GetRoomEngine();
		if (room != null) {
			CChairViewItem chair = (CChairViewItem)view;
			if (chair.GetUserItem() == null) {
				room.PerformSitDownAction(m_nTableID, chair.GetChairID(), false);
			} else {
				room.ShowUserInfo(m_nTableID, chair.GetChairID(),
								chair.GetUserItem());
			}
			return true;
		}
		return false;
	}

	public IClientUserItem GetTableUserItem(int chair) {
		if (chair < m_Chair.length && chair > -1) {
			return m_Chair[chair].GetUserItem();
		}
		return null;
	}

	public void SetTableUserItem(int chair, IClientUserItem useritem) {
		if (chair < m_Chair.length && chair > -1) {
			m_Chair[chair].SetChairUserItem(useritem);

			postInvalidate();
		}
	}

	public void RemoveTableUserItem() {
		for (int i = 0; i < m_Chair.length; i++) {
			m_Chair[i].SetChairUserItem(null);
		}
	}

	public int GetTableID() {
		return m_nTableID;
	}
}
