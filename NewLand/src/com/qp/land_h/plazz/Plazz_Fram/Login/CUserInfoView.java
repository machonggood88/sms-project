package com.qp.land_h.plazz.Plazz_Fram.Login;

import java.io.IOException;

import com.qp.land_h.plazz.ClientPlazz;
import Lib_Graphics.CImageEx;
import Lib_Interface.ResInterface.IResManage;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class CUserInfoView extends View implements IResManage {

	CImageEx	m_ImageHeadBG;

	public CUserInfoView(Context context) {
		super(context);
		setBackgroundDrawable(null);

		try {
			m_ImageHeadBG = new CImageEx(ClientPlazz.RES_PATH + "login/bg_head.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
//		IClientUserItem item = ClientPlazz.GetKernel().GetMeUserItem();
//		if (item != null && item.GetUserID() != 0) {
//			// 头像
//
//			// 名字
//
//			// 积分
//
//		}
	}

	@Override
	public void OnDestoryRes() {
		m_ImageHeadBG.OnReleaseImage();
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageHeadBG.OnReLoadImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

}
