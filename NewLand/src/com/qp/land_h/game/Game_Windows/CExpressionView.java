package com.qp.land_h.game.Game_Windows;

import Lib_Interface.ButtonInterface.IScrollListenner;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Engine.CGameClientView;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.new_land.R;

/**
 * �������
 * 
 * @note
 * @remark
 */
public class CExpressionView extends CViewEngine
				implements
				ISingleClickListener,
				IScrollListenner{

	/** ����ѡ�� **/
	CExpressionButton	m_btExpression[]	= new CExpressionButton[GDF.EXPRESSIONCOUNT];
	/** �����¼ **/
	protected Point		m_ptSpace			= new Point(5, 5);
	/** ���ҳ **/
	public int			m_nMaxPage			= 11;
	/** ÿ����Ŀ **/
	protected int		m_nLineCount		= 6;
	/** ÿҳ���� **/
	protected int		m_nRowCount			= 3;
	/** ��ǰ��¼ **/
	protected int		m_nCurrentIndex		= 0;
	/** �������� **/
	CAssistButton		m_Assist;
	/** �������� **/
	//public String		m_ExDescribe[]		= new String[GDF.EXPRESSIONCOUNT];

	protected int		nSpaceX				= 5;

	/**
	 * ���鰴ť
	 * 
	 * @note
	 * @remark
	 */
	public class CExpressionButton extends CButton{

		/** ����ID **/
		int	m_nExpressionId;

		public CExpressionButton(Context context,int id){
			super(context);
			m_nExpressionId = id;
			setBackgroundDrawable(null);
		}

		@Override
		public void onDraw(Canvas canvas) {
			CExpressionRes res = ClientPlazz.getExpressionRes();
			if (res != null) {
				res.DrawImage(m_nExpressionId, canvas, 0, 0);
			}
		}
	}

	/**
	 * ������������
	 * 
	 * @return
	 */
	public boolean InitDescribe() {
		// for (int i = 0; i < GDF.EXPRESSIONCOUNT; i++) {
		// m_ExDescribe[i]
		// }
		// String variable[] = new String[GDF.EXPRESSIONCOUNT];
		// for (int i = 0; i < GDF.EXPRESSIONCOUNT; i++) {
		// if (i < 10)
		// variable[i] = "CN00" + i;
		// else if (i < 100)
		// variable[i] = "CN0" + i;
		// else
		// variable[i] = "CN" + i;
		// }
		// try {
		// CIniHelper.getProfileStringEx(
		// ClientPlazz.GetInstance().getAssets(), "ini/exconfig.ini",
		// "Expression", variable, "", m_ExDescribe);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		return true;
	}

	public CExpressionView(Context context){
		super(context);
		setBackgroundDrawable(null);

		if (CActivity.nDeviceType == GDF.DEVICETYPE_QVGA) {
			m_nMaxPage = 16;
			m_nLineCount = 5;
			m_nRowCount = 2;
			nSpaceX = 0;
		}
		m_Assist = new CAssistButton(context);
		addView(m_Assist);
		m_Assist.setScrollListenner(this);
		m_Assist.setSingleClickListener(this);
		for (int i = 0; i < m_btExpression.length; i++) {
			m_btExpression[i] = new CExpressionButton(context, i);
			addView(m_btExpression[i]);
			m_btExpression[i].setClickable(false);
		}
		InitDescribe();

	}

	/**
	 * ��������
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		m_Assist.layout(0, 0, r - l, b - t);
		CExpressionRes res = ClientPlazz.getExpressionRes();
		int w = r - l;
		int h = b - t;
		m_ptSpace.set((w - res.GetW() * m_nLineCount) / (m_nLineCount + 1),
						(h - m_nRowCount * res.GetH()) / (m_nRowCount + 1));
		MoveExpression();
	}

	/**
	 * ����λ��
	 */
	protected void MoveExpression() {
		CExpressionRes res = ClientPlazz.getExpressionRes();
		if (res != null) {
			int x = 0,y = 0,ybegin = -m_nCurrentIndex
							* (res.GetW() + m_ptSpace.y);
			for (int i = 0; i < m_btExpression.length; i++) {
				x = (i % m_nLineCount) * (res.GetW() + m_ptSpace.x) + nSpaceX;
				y = ybegin + (i / m_nLineCount) * (res.GetW() + m_ptSpace.y);
				m_btExpression[i].layout(x, y, x + res.GetW(), y + res.GetH());
			}
		}
	}

	/**
	 * �������
	 */
	@Override
	public boolean onSingleClick(View view, Object obj) {
		Rect rc = new Rect();
		int x = (int)((MotionEvent)obj).getX();
		int y = (int)((MotionEvent)obj).getY();
		int index = GDF.INVALID_ITEM;
		for (int i = m_nCurrentIndex * m_nLineCount; i < m_nCurrentIndex
						* m_nLineCount + m_nLineCount * m_nRowCount
						&& i < m_btExpression.length; i++) {
			m_btExpression[i].getHitRect(rc);
			if (rc.contains(x, y)) {
				index = i;
				break;
			}
		}
		if (index < m_btExpression.length) {
			GDF.SendMainMessage(CGameClientView.IDM_HIDE_CHAT, 0, ClientPlazz
							.GetGameClientView().GetTag(), null);
			IClientKernelEx kernel = (IClientKernelEx)ClientPlazz.GetKernel();
			if (kernel != null) {
				kernel.SendUserExpression(0,
								m_btExpression[index].m_nExpressionId);
			}
		}

		return true;
	}

	/**
	 * ��������
	 */
	@Override
	public boolean onScroll(View arg0, MotionEvent arg1, MotionEvent arg2,
					float arg3, float arg4) {

		if (arg4 > 10 && m_nCurrentIndex < m_nMaxPage) {
			m_nCurrentIndex++;
			MoveExpression();
		} else if (arg4 < -10 && m_nCurrentIndex > 0) {
			m_nCurrentIndex--;
			MoveExpression();
		}
		return true;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param index
	 * @return
	 */
	public String GetExpressionDescribe(int index) {
		return ClientPlazz.GetInstance().getResources().getString(R.string.CN000 + index);
		// // if (m_ExDescribe != null && index > -1 && index < m_ExDescribe.length)
		// // return m_ExDescribe[index];
		// // else
		// return "";
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnDestoryRes() {
		CExpressionRes res = ClientPlazz.getExpressionRes();
		res.OnReleaseExpression();
	}

	@Override
	public void OnInitRes() {

	}

	@Override
	protected void Render(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVisibility(int visibility) {

		super.setVisibility(visibility);
	}
}
