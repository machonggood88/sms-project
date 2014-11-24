package com.qp.land_h.game.Card;

import java.io.IOException;

import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_System.CActivity;

public class CardModule {
	static CImageEx	m_ImageCardBg[]		= new CImageEx[3];
	static CImageEx	m_ImageValue[]		= new CImageEx[3];
	static CImageEx	m_ImageColor[]		= new CImageEx[3];
	static CImageEx	m_ImageColorS[]		= new CImageEx[3];
	static CImageEx	m_ImageKing[]		= new CImageEx[3];
	static CImageEx	m_ImageSmallKing[]	= new CImageEx[3];
	static CImageEx	m_ImageFarm[]		= new CImageEx[3];
	static CImageEx	m_ImageLand[]		= new CImageEx[3];

	static Paint	m_MaskPaint;

	// static Matrix m_CardMatrix;
	static Rect		rcCard[]			= new Rect[3];
	static Rect		rcCardValue[]		= new Rect[3];
	static Rect		rcCardColor[]		= new Rect[3];
	static Rect		rcCardColorSmall[]	= new Rect[3];
	static Rect		rcBound[]			= new Rect[3];

	public static void OnInit(int screenw, int screenh) {
		try {
			String[] szpath = new String[] { "card/h/", "card/m/", "card/l/" };
			for (int i = 0; i < 3; i++) {
				m_ImageCardBg[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_bg.png");
				m_ImageValue[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_value.png");
				m_ImageColor[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_color_b.png");
				m_ImageColorS[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_color_s.png");
				m_ImageKing[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_king.png");
				m_ImageSmallKing[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_smallking.png");
				m_ImageFarm[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_land.png");
				m_ImageLand[i] = new CImageEx(ClientPlazz.RES_PATH + szpath[i] + "card_farm.png");

				rcCardValue[i] = new Rect(0, 0, m_ImageValue[i].GetW() / 13, m_ImageValue[i].GetH() / 2);
				rcCardColorSmall[i] = new Rect(0, 0, m_ImageColorS[i].GetW() / 4, m_ImageColorS[i].GetH());
				rcCardColor[i] = new Rect(0, 0, m_ImageColor[i].GetW() / 4, m_ImageColor[i].GetH());
				rcCard[i] = new Rect(0, 0, m_ImageCardBg[i].GetW(), m_ImageCardBg[i].GetH());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_MaskPaint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		float fcolor[] = { 1, 0, 0, 0, -50, 0, 1, 0, 0, -50, 0, 0, 1, 0, 255, 0, 0, 0, 1, -10, };
		cm.set(fcolor);
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
		m_MaskPaint.setColorFilter(cf);
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA:
				rcBound = new Rect[] { new Rect(4, 5, 10, 10), new Rect(4, 4, 6, 6), new Rect(2, 2, 4, 4) };
				break;
			case DF.DEVICETYPE_HVGA:
				rcBound = new Rect[] { new Rect(3, 4, 4, 4), new Rect(3,3,3,3), new Rect(2, 2, 2, 2) };
				break;
			case DF.DEVICETYPE_QVGA:
				rcBound = new Rect[] { new Rect(3, 4, 4, 3), new Rect(2,2,2,2), new Rect(2, 2, 2, 2) };
				break;
		}

	}

	public static void OnInitRes() {
		try {
			for (int i = 0; i < 3; i++) {
				m_ImageCardBg[i].OnReLoadImage();
				m_ImageValue[i].OnReLoadImage();
				m_ImageColor[i].OnReLoadImage();
				m_ImageColorS[i].OnReLoadImage();
				m_ImageKing[i].OnReLoadImage();
				m_ImageSmallKing[i].OnReLoadImage();
				m_ImageFarm[i].OnReLoadImage();
				m_ImageLand[i].OnReLoadImage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OnDestoryRes() {
		for (int i = 0; i < 3; i++) {
			m_ImageCardBg[i].OnReleaseImage();
			m_ImageValue[i].OnReleaseImage();
			m_ImageColor[i].OnReleaseImage();
			m_ImageColorS[i].OnReleaseImage();
			m_ImageKing[i].OnReleaseImage();
			m_ImageSmallKing[i].OnReleaseImage();
			m_ImageFarm[i].OnReleaseImage();
			m_ImageLand[i].OnReleaseImage();
		}
	}

	/**
	 * »æÖÆº¯Êý
	 */
	public static void DrawCard(Canvas canvas, int x, int y, int data, boolean bmask, int type) {
		if ((data & GDF.MASK_VALUE) != 0 && type >= 0) {
			int index = type % 3;
			if (bmask)
				m_ImageCardBg[index].DrawImage(canvas, x, y, m_MaskPaint);
			else
				m_ImageCardBg[index].DrawImage(canvas, x, y);

			if (data == 0x4e || data == 0x4f) {
				DrawKingCard(canvas, x, y, data, bmask, type);
				return;
			}

			int color = ((data & GDF.MASK_COLOR) >> 4) % 4;
			int value = ((data & GDF.MASK_VALUE) - 1) % 13;
			int dstx = x + rcBound[index].left;
			int dsty = y + rcBound[index].top;
			m_ImageValue[index].DrawImage(canvas, dstx, dsty, rcCardValue[index].width(), rcCardValue[index].height(),
					value * rcCardValue[index].width(), (color == 0 || color == 2) ? 0 : rcCardValue[index].height());

			m_ImageColorS[index].DrawImage(canvas,
					dstx + rcCardValue[index].width() / 2 - rcCardColorSmall[index].width() / 2, dsty
							+ rcCardValue[index].height(), rcCardColorSmall[index].width(),
					rcCardColorSmall[index].height(), color * rcCardColorSmall[index].width(), 0);

			m_ImageColor[index].DrawImage(canvas,
					x + rcCard[index].width() - rcBound[index].right - rcCardColor[index].width(),
					y + rcCard[index].height() - rcBound[index].bottom - rcCardColor[index].height(),
					rcCardColor[index].width(), rcCardColor[index].height(), color * rcCardColor[index].width(), 0);
		}
	}

	public static void DrawFramCard(Canvas canvas, int x, int y, int type) {
		int index = type % 3;
		m_ImageFarm[index].DrawImage(canvas, x, y);
	}

	public static void DrawLandCard(Canvas canvas, int x, int y, int type) {
		int index = type % 3;
		m_ImageLand[index].DrawImage(canvas, x, y);
	}

	public static void DrawKingCard(Canvas canvas, int x, int y, int data, boolean bmask, int type) {
		int index = type % 3;
		if (data == 0x4f) {
			if (bmask)
				m_ImageKing[index].DrawImage(canvas, x, y, m_MaskPaint);
			else
				m_ImageKing[index].DrawImage(canvas, x, y);
		} else if (data == 0x4e) {
			if (bmask)
				m_ImageSmallKing[index].DrawImage(canvas, x, y, m_MaskPaint);
			else
				m_ImageSmallKing[index].DrawImage(canvas, x, y);
		}
	}

    public static int getWidth(int type) {
        return rcCard[type % 3].width();
    }

    public static int getHeight(int type) {
        return rcCard[type % 3].height();
    }
}
