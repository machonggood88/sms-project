package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import com.qp.land_h.game.Card.CardModule;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Engine.CGameClientView;
import com.qp.land_h.plazz.ClientPlazz;

public class CToolsBar extends CViewEngine
				implements
				ISingleClickListener,
				IRangeObtain{

	CImageEx			m_ImageBack;
	Paint				m_Paint;

	public CImageButton	m_btBackRoom;
	public CImageButton	m_btChangePos;
	public CImageButton	m_btOption;
	public CImageButton	m_btChat;
	public CImageButton	m_btTrustee;

	public CImageButton	m_btLandCard;

	public long			m_lCellscore;
	public int			m_nPoint;
	public int			BackCard[]		= new int[3];
	boolean				m_bmask;

	Point				m_ptCard		= new Point();
	Point				m_ptCellScore	= new Point();
	Point				m_ptBankerScore	= new Point();

	public CToolsBar(Context context){
		super(context);
		setWillNotDraw(false);

		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "gameres/bg_toolsbar.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_Paint = new Paint();
		m_Paint.setTextSize(18);
		m_Paint.setAntiAlias(true);
		m_Paint.setStrokeWidth(8);
		m_Paint.setColor(Color.rgb(238, 184, 121));

		m_btChangePos = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_changepos.png");
		m_btOption = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_option.png");
		m_btChat = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_chat.png");
		m_btBackRoom = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_backroom.png");
		m_btTrustee = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_trustee.png");

		addView(m_btChangePos);
		addView(m_btOption);
		addView(m_btChat);
		addView(m_btBackRoom);
		addView(m_btTrustee);

		m_btChangePos.setSingleClickListener(this);
		m_btOption.setSingleClickListener(this);
		m_btChat.setSingleClickListener(this);
		m_btBackRoom.setSingleClickListener(this);
		m_btTrustee.setSingleClickListener(this);

		switch (CActivity.nDeviceType) {
			case GDF.DEVICETYPE_WVGA:
				m_Paint.setTextSize(18);
				m_ptCard.set(185, 25);
				m_ptCellScore.set(300, 25);
				m_ptBankerScore.set(300, 50);
				break;
			case GDF.DEVICETYPE_HVGA:
				m_Paint.setTextSize(12);
				m_ptCard.set(115, 15);
				m_ptCellScore.set(200, 15);
				m_ptBankerScore.set(200, 30);
				break;
			case GDF.DEVICETYPE_QVGA:
				m_Paint.setTextSize(12);
				m_btLandCard = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/bt_landcard.png");
				addView(m_btLandCard);
				m_btLandCard.setSingleClickListener(this);

				break;

		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		switch (CActivity.nDeviceType) {
			case GDF.DEVICETYPE_WVGA:
				onLayoutH(changed, l, t, r, b);
				break;
			case GDF.DEVICETYPE_HVGA:
				onLayoutM(changed, l, t, r, b);
				break;
			case GDF.DEVICETYPE_QVGA:
				onLayoutL(changed, l, t, r, b);
				break;
		}
	}

	private void onLayoutL(boolean changed, int l, int t, int r, int b) {
		int xspace = m_btTrustee.getW() + 1;
		m_btTrustee.layout(10, 6, 0, 0);
		m_btOption.layout(10 + xspace, 6, 0, 0);
		m_btChat.layout(10 + xspace * 2, 6, 0, 0);
		m_btChangePos.layout(10 + xspace * 3, 6, 0, 0);
		m_btBackRoom.layout(10 + xspace * 4, 6, 0, 0);
		m_btLandCard.layout(10 + xspace * 5 + 3, 7, 0, 0);
	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		m_btTrustee.layout(3, 15, 0, 0);
		m_btOption.layout(3 + m_btTrustee.getW(), 15, 0, 0);
		m_btChat.layout(3 + m_btTrustee.getW() * 2, 15, 0, 0);

		m_btChangePos.layout(m_ImageBack.GetW() - m_btChangePos.getW(), 15, 0, 0);
		m_btBackRoom.layout(m_ImageBack.GetW() - m_btChangePos.getW() * 2, 15, 0, 0);

	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		m_btTrustee.layout(5, 25, 0, 0);
		m_btOption.layout(5 + m_btTrustee.getW(), 25, 0, 0);
		m_btChat.layout(5 + m_btTrustee.getW() * 2, 25, 0, 0);

		m_btChangePos.layout(m_ImageBack.GetW() - m_btChangePos.getW(), 25, 0, 0);
		m_btBackRoom.layout(m_ImageBack.GetW() - m_btChangePos.getW() * 2, 25, 0, 0);

	}

	@Override
	public void ActivateView() {
	}

	@Override
	public void OnDestoryRes() {
		m_ImageBack.OnReleaseImage();
		m_btBackRoom.setVisibility(INVISIBLE);
		m_btChangePos.setVisibility(INVISIBLE);
		m_btOption.setVisibility(INVISIBLE);
		m_btChat.setVisibility(INVISIBLE);
		m_btTrustee.setVisibility(INVISIBLE);
		if (m_btLandCard != null)
			m_btLandCard.setVisibility(INVISIBLE);
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_btBackRoom.setVisibility(VISIBLE);
		m_btChangePos.setVisibility(VISIBLE);
		m_btOption.setVisibility(VISIBLE);
		m_btChat.setVisibility(VISIBLE);
		m_btTrustee.setVisibility(VISIBLE);
		if (m_btLandCard != null)
			m_btLandCard.setVisibility(VISIBLE);
	}

	@Override
	protected void Render(Canvas canvas) {
		if (m_ImageBack != null) {
			m_ImageBack.DrawImage(canvas, 0, 0);
			if (CActivity.nDeviceType == GDF.DEVICETYPE_QVGA)
				return;
			CText.DrawTextEllip(canvas, "µ×: " + m_lCellscore, m_ptCellScore.x, m_ptCellScore.y, 80, m_Paint);
			CText.DrawTextEllip(canvas, "±¶: " + m_nPoint, m_ptBankerScore.x, m_ptBankerScore.y, 80, m_Paint);
			if (BackCard != null) {
				for (int i = 0; i < BackCard.length; i++) {
					if (BackCard[i] == 0) {
						if (m_bmask){
							CardModule.DrawLandCard(canvas, m_ptCard.x + i * CardModule.getWidth(2), m_ptCard.y, 2);
							//CCard_Small.DrawLand(canvas, m_ptCard.x + i * CCard_Small.GetW(), m_ptCard.y);
						}
					} else {
						CardModule.DrawCard(canvas, m_ptCard.x + i * CardModule.getWidth(2), m_ptCard.y, BackCard[i], false, 2);
						//CCard_Small.DrawCard(canvas, m_ptCard.x + i * CCard_Small.GetW(), m_ptCard.y, BackCard[i]);
					}

				}
			}
		}

	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		int id = view.getId();
		CGameClientView gameview = (CGameClientView)ClientPlazz
						.GetGameClientView();
		if (id == m_btOption.getId()) {
			gameview.OnOptionClick();
			return true;
		} else if (id == m_btChat.getId()) {
			gameview.OnChatClick();
			return true;
		} else if (id == m_btBackRoom.getId()) {
			gameview.OnBackRoomClick();
			return true;
		} else if (id == m_btTrustee.getId()) {
			gameview.OnTrusteeClick();
			return true;
		} else if (id == m_btChangePos.getId()) {
			gameview.OnChangePosClick();
			return true;
		} else if (m_btLandCard != null && id == m_btLandCard.getId()) {
			gameview.OnShowLandCard(0);
			return true;
		}
		return false;
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

	public void SetBackData(int data[], boolean mask) {
		m_bmask = mask;
		if (data == null) {
			for (int i = 0; i < BackCard.length; i++)
				BackCard[i] = 0;
		} else {
			System.arraycopy(data, 0, BackCard, 0, 3);
		}
	}

}
