package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Interface.IRangeObtain;
import android.graphics.Canvas;

import com.qp.land_h.plazz.ClientPlazz;

public class CExpressionRes implements IRangeObtain {

	public CImageEx	m_ImageExpression[]	= new CImageEx[80];

	public int		m_nW;
	public int		m_nH;

	public CExpressionRes() {

		try {
			for (int i = 0; i < m_ImageExpression.length; i++)
				m_ImageExpression[i] = new CImageEx(ClientPlazz.RES_PATH + "expression/ex_" + i + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (m_ImageExpression[0] != null) {
			m_nW = m_ImageExpression[0].GetW();
			m_nH = m_ImageExpression[0].GetH();
		}
	}

	@Override
	public int GetH() {
		if (m_ImageExpression[0] != null)
			return m_ImageExpression[0].GetH();
		return 0;
	}

	@Override
	public int GetW() {
		if (m_ImageExpression[0] != null)
			return m_ImageExpression[0].GetW();
		return 0;
	}

	public void DrawImage(int index, Canvas canvas, int x, int y) {
		if (index < m_ImageExpression.length) {
			if (m_ImageExpression[index].GetBitMap() == null) {
				try {
					m_ImageExpression[index].OnReLoadImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			m_ImageExpression[index].DrawImage(canvas, x, y);
		}

	}

	public CImageEx GetImage(int index) {
		if (index < m_ImageExpression.length)
			return m_ImageExpression[index];
		return null;
	}

	public void OnReleaseExpression(){
		for (int i = 0; i < m_ImageExpression.length; i++)
			m_ImageExpression[i].OnReleaseImage();
	}
}
