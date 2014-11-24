package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import com.qp.land_h.game.Card.CardModule;
import com.qp.land_h.plazz.ClientPlazz;

import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ResInterface.IResManage;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class CBackCardView extends View implements IResManage,IRangeObtain{

	CImageEx	m_ImageBack;
	public int	BackCard[];

	public CBackCardView(Context context){
		super(context);
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "gameres/userinfo_fram2.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		BackCard = new int[3];
	}

	@Override
	public void OnDestoryRes() {
		m_ImageBack.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(Canvas canvas) {
		m_ImageBack.DrawImage(canvas, 0, 0);
		if (BackCard != null) {
			for (int i = 0; i < BackCard.length; i++) {
				if (BackCard[i] == 0) {
					CardModule.DrawLandCard(canvas, 15 + i * (CardModule.getWidth(2) + 15), 30, 2);
					//CCard_Small.DrawLand(canvas, 15 + i * (CCard_Small.GetW() + 15), 30);
				} else {
					CardModule.DrawCard(canvas, 15 + i * (CardModule.getWidth(2) + 15), 30, BackCard[i], false, 2);
					//CCard_Small.DrawCard(canvas, 15 + i * (CCard_Small.GetW() + 15), 30, BackCard[i]);
				}

			}
		}
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

	public void SetBackData(int data[]) {

		if (data == null) {
			for (int i = 0; i < BackCard.length; i++)
				BackCard[i] = 0;
		} else {
			System.arraycopy(data, 0, BackCard, 0, 3);
		}
	}

	public void OnShowLandCard(int reason) {
		switch (reason) {
			case 0:
				setVisibility(BackCard[0] != 0?VISIBLE:INVISIBLE);
				break;
			case 1:
				setVisibility(VISIBLE);
				break;
			case 2:
				setVisibility(INVISIBLE);
				break;
			default:
				break;
		}
	}
}
