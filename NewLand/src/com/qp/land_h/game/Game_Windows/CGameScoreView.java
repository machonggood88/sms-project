package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Graphics.CText;
import Lib_Interface.IRangeObtain;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Cmd.tagScoreInfo;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;

public class CGameScoreView extends CViewEngine implements IRangeObtain{

	CImageEx		m_ImageBack;

	CImageEx		m_ImageWinFlag;

	CImageEx		m_ImageSpringFlag;

	CImageEx		m_ImageBomb;
	CImageEx		m_ImageCoin;

	CImageEx		m_ImageNum[]	= new CImageEx[2];

	CImageEx		m_ImageWin[]	= new CImageEx[2];
	CImageEx		m_ImageLose[]	= new CImageEx[2];

	tagScoreInfo	m_ScoreInfo		= new tagScoreInfo();

	Paint			m_Paint;

	public CGameScoreView(Context context){
		super(context);
		setWillNotDraw(false);
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/bg_gameend.png");

			m_ImageWinFlag = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_end.png");

			m_ImageSpringFlag = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_spring.png");

			m_ImageWin[0] = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_win_0.png");
			m_ImageWin[1] = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_win_1.png");

			m_ImageLose[0] = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_lose_0.png");
			m_ImageLose[1] = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_lose_1.png");

			m_ImageNum[0] = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/win_score_num.png");
			m_ImageNum[1] = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/lose_score_num.png");

			m_ImageBomb = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_bomb.png");
			m_ImageCoin = new CImageEx(ClientPlazz.RES_PATH + "gameres/end/flag_coin.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_Paint = new Paint();
		m_Paint.setAntiAlias(true);
		m_Paint.setColor(Color.WHITE);
		m_Paint.setStrokeWidth(10);

		switch (CActivity.nDeviceType) {
			case GDF.DEVICETYPE_WVGA:
				m_Paint.setTextSize(26);
				break;
			case GDF.DEVICETYPE_HVGA:
				m_Paint.setTextSize(14);
				break;
			case GDF.DEVICETYPE_QVGA:
				m_Paint.setTextSize(12);
				break;
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	@Override
	public void setVisibility(int visibility) {

		super.setVisibility(visibility);
		if (visibility != VISIBLE) {
			OnDestoryRes();
		}
	}

	@Override
	public void ActivateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnDestoryRes() {
		m_ScoreInfo.ReSetData();
		m_ImageBack.OnReleaseImage();

		m_ImageWinFlag.OnReleaseImage();

		m_ImageSpringFlag.OnReleaseImage();

		m_ImageWin[0].OnReleaseImage();
		m_ImageWin[1].OnReleaseImage();

		m_ImageLose[0].OnReleaseImage();
		m_ImageLose[1].OnReleaseImage();

		m_ImageNum[0].OnReleaseImage();
		m_ImageNum[1].OnReleaseImage();

		m_ImageBomb.OnReleaseImage();
		m_ImageCoin.OnReleaseImage();

	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageBack.OnReLoadImage();

			m_ImageWinFlag.OnReLoadImage();

			m_ImageSpringFlag.OnReLoadImage();

			m_ImageWin[0].OnReLoadImage();
			m_ImageWin[1].OnReLoadImage();

			m_ImageLose[0].OnReLoadImage();
			m_ImageLose[1].OnReLoadImage();

			m_ImageNum[0].OnReLoadImage();
			m_ImageNum[1].OnReLoadImage();

			m_ImageBomb.OnReLoadImage();
			m_ImageCoin.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void Render(Canvas canvas) {
		switch (CActivity.nDeviceType) {
			case GDF.DEVICETYPE_WVGA:
				onRenderH(canvas);
				break;
			case GDF.DEVICETYPE_HVGA:
				onRenderM(canvas);
				break;
			case GDF.DEVICETYPE_QVGA:
				onRenderL(canvas);
				break;
		}

	}

	private void onRenderL(Canvas canvas) {
		m_Paint.setTextAlign(Align.LEFT);
		if (m_ImageBack != null) {
			// µ×Í¼
			m_ImageBack.DrawImage(canvas, 0, 0);

			String szPoint = String.format("%+d", m_ScoreInfo.lGameScore);

			// ÊäÓ®ÐÞÊÎ
			if (m_ScoreInfo.lGameScore > 0) {
				if (m_ScoreInfo.bBanker)
					m_ImageWin[0].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageWin[0].GetH());
				else
					m_ImageWin[1].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageWin[1].GetH());

				m_ImageWinFlag.DrawImage(canvas, 10, 10, m_ImageWinFlag.GetW() / 2,
								m_ImageWinFlag.GetH(), 0, 0);
				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum[0], 220, 58, "+0123456789", szPoint,
								GDF.STYLE_RIGHT);

			} else {
				if (m_ScoreInfo.bBanker)
					m_ImageLose[0].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageLose[0].GetH());
				else
					m_ImageLose[1].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageLose[1].GetH());

				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum[1], 220, 58, "-0123456789", szPoint,
								GDF.STYLE_RIGHT);
				m_ImageWinFlag.DrawImage(canvas, 10, 10, m_ImageWinFlag.GetW() / 2,
								m_ImageWinFlag.GetH(), m_ImageWinFlag.GetW() / 2, 0);
			}

			String m_szCellScore = String.format("µ×·Ö X %d", m_ScoreInfo.lCellScore);
			String m_szTimeScore = String.format("±¶Êý X %d", m_ScoreInfo.nBankerScore);
			m_Paint.setTextAlign(Align.LEFT);
			CText.DrawTextEllip(canvas, m_szCellScore, 80, m_ImageBack.GetH() - 25, 85, m_Paint);
			m_Paint.setTextAlign(Align.RIGHT);
			CText.DrawTextEllip(canvas, m_szTimeScore, m_ImageBack.GetW() - 15, m_ImageBack.GetH() - 25, 85, m_Paint);

			int springx = 70 + ((m_ImageBack.GetW() - 70) - m_ImageSpringFlag.GetW()) / 2;
			if (m_ScoreInfo.bSpring) {
				m_ImageSpringFlag.DrawImage(canvas, springx, 7, m_ImageSpringFlag.GetW() / 2,
								m_ImageSpringFlag.GetH(), 0, 0);
			} else if (m_ScoreInfo.bSpringSpring) {
				m_ImageSpringFlag.DrawImage(canvas, springx, 7, m_ImageSpringFlag.GetW() / 2,
								m_ImageSpringFlag.GetH(), m_ImageSpringFlag.GetW() / 2, 0);
			}

			m_ImageBomb.DrawImage(canvas, 95, 25);
			CText.DrawTextEllip(canvas, "X " + m_ScoreInfo.nBombCount, m_ImageBack.GetW() - 20, 25, 85, m_Paint);
			m_ImageCoin.DrawImage(canvas, 95, 40);
			CText.DrawTextEllip(canvas, "X " + 0, m_ImageBack.GetW() - 20, 40, 85, m_Paint);
		}

	}

	private void onRenderM(Canvas canvas) {
		m_Paint.setTextAlign(Align.LEFT);
		if (m_ImageBack != null) {
			// µ×Í¼
			m_ImageBack.DrawImage(canvas, 0, 0);

			String szPoint = String.format("%+d", m_ScoreInfo.lGameScore);

			// ÊäÓ®ÐÞÊÎ
			if (m_ScoreInfo.lGameScore > 0) {
				if (m_ScoreInfo.bBanker)
					m_ImageWin[0].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageWin[0].GetH());
				else
					m_ImageWin[1].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageWin[1].GetH());

				m_ImageWinFlag.DrawImage(canvas, 10, 15, m_ImageWinFlag.GetW() / 2,
								m_ImageWinFlag.GetH(), 0, 0);
				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum[0], 300, 80, "+0123456789", szPoint,
								GDF.STYLE_RIGHT);

			} else {
				if (m_ScoreInfo.bBanker)
					m_ImageLose[0].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageLose[0].GetH());
				else
					m_ImageLose[1].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageLose[1].GetH());

				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum[1], 300, 80, "-0123456789", szPoint,
								GDF.STYLE_RIGHT);
				m_ImageWinFlag.DrawImage(canvas, 10, 15, m_ImageWinFlag.GetW() / 2,
								m_ImageWinFlag.GetH(), m_ImageWinFlag.GetW() / 2, 0);
			}

			String m_szCellScore = String.format("µ×·Ö X %d", m_ScoreInfo.lCellScore);
			String m_szTimeScore = String.format("±¶Êý X %d", m_ScoreInfo.nBankerScore);
			m_Paint.setTextAlign(Align.LEFT);
			CText.DrawTextEllip(canvas, m_szCellScore, 135, m_ImageBack.GetH() - 30, 85, m_Paint);
			m_Paint.setTextAlign(Align.RIGHT);
			CText.DrawTextEllip(canvas, m_szTimeScore, m_ImageBack.GetW() - 15, m_ImageBack.GetH() - 30, 85, m_Paint);

			int springx = 125 + ((m_ImageBack.GetW() - 125) - m_ImageSpringFlag.GetW()) / 2;
			if (m_ScoreInfo.bSpring) {
				m_ImageSpringFlag.DrawImage(canvas, springx, 15, m_ImageSpringFlag.GetW() / 2,
								m_ImageSpringFlag.GetH(), 0, 0);
			} else if (m_ScoreInfo.bSpringSpring) {
				m_ImageSpringFlag.DrawImage(canvas, springx, 15, m_ImageSpringFlag.GetW() / 2,
								m_ImageSpringFlag.GetH(), m_ImageSpringFlag.GetW() / 2, 0);
			}

			m_ImageBomb.DrawImage(canvas, 140, 33);
			CText.DrawTextEllip(canvas, "X " + m_ScoreInfo.nBombCount, m_ImageBack.GetW() - 30, 33, 85, m_Paint);
			m_ImageCoin.DrawImage(canvas, 140, 52);
			CText.DrawTextEllip(canvas, "X " + 0, m_ImageBack.GetW() - 30, 52, 85, m_Paint);

		}
	}

	private void onRenderH(Canvas canvas) {
		m_Paint.setTextAlign(Align.LEFT);
		if (m_ImageBack != null) {
			// µ×Í¼
			m_ImageBack.DrawImage(canvas, 0, 0);

			String szPoint = String.format("%+d", m_ScoreInfo.lGameScore);

			// ÊäÓ®ÐÞÊÎ
			if (m_ScoreInfo.lGameScore > 0) {
				if (m_ScoreInfo.bBanker)
					m_ImageWin[0].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageWin[0].GetH());
				else
					m_ImageWin[1].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageWin[1].GetH());

				m_ImageWinFlag.DrawImage(canvas, 20, 20, m_ImageWinFlag.GetW() / 2,
								m_ImageWinFlag.GetH(), 0, 0);
				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum[0], 485, 130, "+0123456789", szPoint,
								GDF.STYLE_RIGHT);

			} else {
				if (m_ScoreInfo.bBanker)
					m_ImageLose[0].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageLose[0].GetH());
				else
					m_ImageLose[1].DrawImage(canvas, 0, m_ImageBack.GetH() - m_ImageLose[1].GetH());

				CPlazzGraphics.DrawHorizontalNum(canvas, m_ImageNum[1], 485, 130, "-0123456789", szPoint,
								GDF.STYLE_RIGHT);
				m_ImageWinFlag.DrawImage(canvas, 20, 20, m_ImageWinFlag.GetW() / 2,
								m_ImageWinFlag.GetH(), m_ImageWinFlag.GetW() / 2, 0);
			}

			String m_szCellScore = String.format("µ×·Ö X %d", m_ScoreInfo.lCellScore);
			String m_szTimeScore = String.format("±¶Êý X %d", m_ScoreInfo.nBankerScore);
			m_Paint.setTextAlign(Align.LEFT);
			CText.DrawTextEllip(canvas, m_szCellScore, 165, m_ImageBack.GetH() - 55, 160, m_Paint);
			m_Paint.setTextAlign(Align.RIGHT);
			CText.DrawTextEllip(canvas, m_szTimeScore, m_ImageBack.GetW() - 20, m_ImageBack.GetH() - 55, 160, m_Paint);

			int springx = 245 + ((m_ImageBack.GetW() - 245) - m_ImageSpringFlag.GetW()) / 2;
			if (m_ScoreInfo.bSpring) {
				m_ImageSpringFlag.DrawImage(canvas, springx, 20, m_ImageSpringFlag.GetW() / 2,
								m_ImageSpringFlag.GetH(), 0, 0);
			} else if (m_ScoreInfo.bSpringSpring) {
				m_ImageSpringFlag.DrawImage(canvas, springx, 20, m_ImageSpringFlag.GetW() / 2,
								m_ImageSpringFlag.GetH(), m_ImageSpringFlag.GetW() / 2, 0);
			}

			m_ImageBomb.DrawImage(canvas, 165, 45);
			CText.DrawTextEllip(canvas, "X " + m_ScoreInfo.nBombCount, m_ImageBack.GetW() - 50, 55, 100, m_Paint);
			m_ImageCoin.DrawImage(canvas, 168, 85);
			CText.DrawTextEllip(canvas, "X " + 0, m_ImageBack.GetW() - 50, 85, 100, m_Paint);

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

	public void SetScoreDate(tagScoreInfo scoreinfo) {
		if (scoreinfo == null) {
			m_ScoreInfo.ReSetData();
			OnDestoryRes();
		} else {
			m_ScoreInfo.bReady = true;
			m_ScoreInfo.bBanker = scoreinfo.bBanker;
			m_ScoreInfo.bSpring = scoreinfo.bSpring;
			m_ScoreInfo.bSpringSpring = scoreinfo.bSpringSpring;
			m_ScoreInfo.nBankerScore = scoreinfo.nBankerScore;
			m_ScoreInfo.nBombCount = scoreinfo.nBombCount;
			m_ScoreInfo.lGameScore = scoreinfo.lGameScore;
			m_ScoreInfo.lCellScore = scoreinfo.lCellScore;
			try {
				if (m_ScoreInfo.lGameScore > 0) {
					if (m_ScoreInfo.bBanker)
						m_ImageWin[0].OnReLoadImage();
					else
						m_ImageWin[1].OnReLoadImage();
					m_ImageNum[0].OnReLoadImage();
				} else {
					if (m_ScoreInfo.bBanker)
						m_ImageLose[0].OnReLoadImage();
					else
						m_ImageLose[1].OnReLoadImage();
					m_ImageNum[1].OnReLoadImage();
				}

				m_ImageBack.OnReLoadImage();
				m_ImageWinFlag.OnReLoadImage();

				m_ImageBomb.OnReLoadImage();
				m_ImageCoin.OnReLoadImage();

				if (m_ScoreInfo.bSpring || m_ScoreInfo.bSpringSpring)
					m_ImageSpringFlag.OnReLoadImage();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
