package com.qp.land_h.plazz.Plazz_Fram.Room;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.df.PDF;

public class CUserInfoViewEx extends CViewEngine
				implements
				ISingleClickListener,
				IRangeObtain{

	CImageButton	m_btCopyNickName;
	CImageButton	m_btCopyGameID;
	CImageEx		m_ImageBack;
	IClientUserItem	m_UserItem;
	Point			m_ptBase	= new Point();

	public CUserInfoViewEx(Context context){
		super(context);
		setBackgroundDrawable(null);
		setWillNotDraw(false);
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "custom/userinfo.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_btCopyNickName = new CImageButton(context, ClientPlazz.RES_PATH
						+ "custom/bt_copyname.png");
		m_btCopyGameID = new CImageButton(context, ClientPlazz.RES_PATH
						+ "custom/bt_copyid.png");

		addView(m_btCopyGameID);
		addView(m_btCopyNickName);
		m_btCopyNickName.setSingleClickListener(this);
		m_btCopyGameID.setSingleClickListener(this);

		m_ptBase.set(20, 10);
	}

	@Override
	public void ActivateView() {

	}

	/**
	 * 设置区域
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		m_btCopyNickName.layout(r / 2 - l / 2 + 10, m_ImageBack.GetH()
						- m_btCopyNickName.getH() - 10, r, b);
		m_btCopyGameID.layout(r / 2 - l / 2 - 10 - m_btCopyNickName.getW(),
						m_ImageBack.GetH() - m_btCopyNickName.getH() - 10, r, b);
	}

	@Override
	public void OnDestoryRes() {
		m_ImageBack.OnReleaseImage();
		m_btCopyGameID.setVisibility(INVISIBLE);
		m_btCopyNickName.setVisibility(INVISIBLE);
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_btCopyGameID.setVisibility(VISIBLE);
		m_btCopyNickName.setVisibility(VISIBLE);
	}

	@Override
	protected void Render(Canvas canvas) {
		if (m_UserItem != null) {
			m_ImageBack.DrawImage(canvas, 0, 0);
			CPlazzGraphics graphic = CPlazzGraphics.onCreate();
			graphic.DrawUserAvatar(canvas, m_ptBase.x, m_ptBase.y, m_UserItem);
			int nTmpPosy = m_ptBase.y - 5;
			int nTmpPosx = m_ptBase.x + graphic.GetFaceW() + 5;
			Paint p = new Paint();
			p.setAntiAlias(true);
			p.setTextSize(14);
			p.setStrokeWidth(2.0f);
			p.setColor(Color.GREEN);
			int texth = (int)CText.GetTextHeight(p);
			CText.DrawTextEllip(canvas, "昵称：" + m_UserItem.GetNickName(),
							nTmpPosx, nTmpPosy, 240, p);
			nTmpPosy += texth;
			CText.DrawTextEllip(canvas, "ID  ：" + m_UserItem.GetGameID() + "",
							nTmpPosx, nTmpPosy, 240, p);
			nTmpPosy += texth;
			p.setColor(Color.WHITE);
			CText.DrawTextEllip(
							canvas,
							"等级：" + ClientPlazz.GetUserLevel(m_UserItem.GetUserScore()),
							nTmpPosx, nTmpPosy, 240, p);
			nTmpPosy += texth;
			p.setColor(0xfffac800);
			CText.DrawTextEllip(canvas, "金币：" + m_UserItem.GetUserScore() + "",
							nTmpPosx, nTmpPosy, 240, p);
			nTmpPosy += texth;
			p.setColor(Color.WHITE);
			CText.DrawTextEllip(canvas, "胜率：" + m_UserItem.GetUserWinRate()
							+ "% " + "逃跑：" + m_UserItem.GetUserFleeRate() + "%",
							nTmpPosx, nTmpPosy, 240, p);

		}

	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		if (m_UserItem != null) {
			ClipboardManager cm = (ClipboardManager)PDF.GetContext()
							.getSystemService(Context.CLIPBOARD_SERVICE);
			if (cm == null)
				return isClickable();
			if (view.getId() == m_btCopyGameID.getId()) {
				cm.setText(m_UserItem.GetGameID() + "");
				Toast.makeText(PDF.GetContext(),
								"获取用户GAMEID：" + m_UserItem.GetGameID(),
								Toast.LENGTH_SHORT).show();
				return true;
			} else if (view.getId() == m_btCopyNickName.getId()) {
				cm.setText(m_UserItem.GetNickName());
				Toast.makeText(PDF.GetContext(),
								"获取用户昵称：" + m_UserItem.GetNickName(),
								Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return isClickable();
	}

	@Override
	public int GetH() {
		if (m_ImageBack != null)
			return m_ImageBack.GetH();
		return 0;
	}

	@Override
	public int GetW() {
		if (m_ImageBack != null)
			return m_ImageBack.GetW();
		return 0;
	}

	public void ShowUserInfo(IClientUserItem item) {
		if (item == null) {
			m_UserItem = null;
		} else {
			m_UserItem = item;
		}
		postInvalidate();
	}
}
