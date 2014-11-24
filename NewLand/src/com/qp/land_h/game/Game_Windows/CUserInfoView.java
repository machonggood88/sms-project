package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.CActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;

public class CUserInfoView extends View implements IRangeObtain{

	public static CImageEx	m_ImageFram[]	= new CImageEx[GDF.GAME_PLAYER];
	IClientUserItem			m_UserItem;
	int						m_ChairID;

	static {

		try {
			for (int i = 0; i < m_ImageFram.length; i++)
				m_ImageFram[i] = new CImageEx(ClientPlazz.RES_PATH + "gameres/userinfo_fram" + i + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void OnInitRes() {
//		try {
//			for (int i = 0; i < m_ImageFram.length; i++)
//				m_ImageFram[i].OnReLoadImage();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void OnDestoryRes() {
		for (int i = 0; i < m_ImageFram.length; i++)
			m_ImageFram[i].OnReleaseImage();
	}

	public CUserInfoView(Context context,int chair){
		super(context);
		setBackgroundDrawable(null);
		m_ChairID = chair;
	}

	@Override
	public void onDraw(Canvas canvas) {

		int nTmpImageIndex = m_ChairID;
		m_ImageFram[nTmpImageIndex].DrawImage(canvas, 0, 0, 200);
		if (m_UserItem == null || m_ChairID == GDF.INVALID_CHAIR)
			return;

		int nTmpPosx = 0,nTmpPosy = 0;
		int lenght = 0;
		Paint p = new Paint();
		p.setAntiAlias(true);
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				p.setTextSize(16);
				nTmpPosx = 20;
				nTmpPosy = (m_ChairID == 1?20:45);
				lenght = 260;
				break;
			case DF.DEVICETYPE_HVGA:
				p.setTextSize(10);
				nTmpPosx = 10;
				nTmpPosy = (m_ChairID == 1?10:20);
				lenght = 100;
				break;
			case DF.DEVICETYPE_QVGA:
				p.setTextSize(10);
				nTmpPosx = 10;
				nTmpPosy = (m_ChairID == 1?10:20);
				lenght = 100;
				break;

		}

		p.setStrokeWidth(2.0f);
		p.setColor(Color.GREEN);
		int texth = (int)CText.GetTextHeight(p) + 2;
		CText.DrawTextEllip(canvas, "昵称：" + m_UserItem.GetNickName(), nTmpPosx, nTmpPosy, lenght, p);
		nTmpPosy += texth;
		CText.DrawTextEllip(canvas, "ID  ：" + m_UserItem.GetGameID() + "", nTmpPosx, nTmpPosy, lenght, p);
		nTmpPosy += texth;
		p.setColor(Color.WHITE);
		CText.DrawTextEllip(canvas, "等级：" + ClientPlazz.GetUserLevel(m_UserItem.GetUserScore()), nTmpPosx, nTmpPosy,
						lenght, p);
		nTmpPosy += texth;
		p.setColor(0xfffac800);
		CText.DrawTextEllip(canvas, "金币：" + m_UserItem.GetUserScore() + "", nTmpPosx, nTmpPosy, lenght, p);
		nTmpPosy += texth;
	//	p.setColor(Color.WHITE);
	//	CText.DrawTextEllip(canvas, "胜率：" + m_UserItem.GetUserWinRate() + "% " + "逃跑：" + m_UserItem.GetUserFleeRate()
	//					+ "%", nTmpPosx, nTmpPosy, lenght, p);

	}

	public void ShowUserInfo(IClientUserItem item) {
		m_UserItem = item;
	}

	@Override
	public int GetH() {
		if (m_ImageFram[0] != null)
			return m_ImageFram[0].GetH();
		return 0;
	}

	@Override
	public int GetW() {
		if (m_ImageFram[0] != null)
			return m_ImageFram[0].GetW();
		return 0;
	}
	
	@Override
	public void setVisibility(int visibility){
		if(visibility==VISIBLE){
			if(m_ImageFram[m_ChairID].GetBitMap()==null){
				try {
		
						m_ImageFram[m_ChairID].OnReLoadImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		super.setVisibility(visibility);
		
		if(visibility==INVISIBLE){
			m_ImageFram[m_ChairID].OnReleaseImage();
		}
	}

}
