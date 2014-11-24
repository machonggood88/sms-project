package com.qp.land_h.game.Game_Windows;

import Lib_Graphics.CImage;
import Lib_Interface.IRangeObtain;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;

public class CChatMessageView extends CViewEngine implements IRangeObtain{

	TextView	ChatMessage;
	int			m_nShowMode;
	int			m_nItemIndex	= -1;
	CImage		m_ImageBack;

	public CChatMessageView(Context context,int mode){
		super(context);
		setWillNotDraw(false);
		m_nShowMode = mode;
		ChatMessage = new TextView(context);
		ChatMessage.setBackgroundDrawable(null);
		ChatMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
		ChatMessage.setTextSize(12);
		ChatMessage.setVisibility(VISIBLE);
		m_ImageBack = new CImage(getContext(), ClientPlazz.RES_PATH + "chat/chatfram_"
						+ m_nShowMode + ".png", null, false);

		switch (CActivity.nDeviceType) {
			case GDF.DEVICETYPE_WVGA:
				ChatMessage.setTextSize(12);
				break;
			case GDF.DEVICETYPE_HVGA:
				ChatMessage.setTextSize(14);
				break;
			case GDF.DEVICETYPE_QVGA:
				ChatMessage.setTextSize(12);
				break;

		}

		addView(ChatMessage);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		ChatMessage.layout(10, 20, r - l - 20, b - t - 20);
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

	public void SetChatMessage(String chatmessage, int color) {
		ChatMessage.setTextColor(color);
		ChatMessage.setText(chatmessage);
		if (chatmessage != null && !chatmessage.equals(""))
			ChatMessage.setVisibility(VISIBLE);
		else
			ChatMessage.setVisibility(INVISIBLE);
	}

	public void SetChatExpression(int ItemIndex) {
		m_nItemIndex = ItemIndex;

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

	@Override
	protected void Render(Canvas canvas) {
		if (m_ImageBack != null)
			m_ImageBack.DrawImage(canvas, 0, 0);
		if (m_nItemIndex > -1) {
			CExpressionRes res = ClientPlazz.getExpressionRes();
			if (res != null) {
				res.DrawImage(m_nItemIndex % 80, canvas, 5, 15);
			}
		}
	}
}
