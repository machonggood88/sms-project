package com.qp.land_h.game.Game_Windows;

import com.qp.land_h.game.Card.CardModule;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Engine.CGameClientView;
import com.qp.land_h.plazz.ClientPlazz;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class CMoveCard extends View implements AnimationListener {

	public int	user;

	public CMoveCard(Context context) {
		super(context);
		setClickable(false);
		setBackgroundDrawable(null);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		super.onAnimationEnd();
		GDF.SendMainMessage(CGameClientView.IDM_DISPATH, user, ClientPlazz.GetGameClientView().GetTag(), null);
	}

	@Override
	public void onDraw(Canvas canvas) {
		//CCard_Middle.DrawLand(canvas, 0, 0);
		CardModule.DrawLandCard(canvas, 0, 0, 1);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {
		super.onAnimationStart();
	}

}
