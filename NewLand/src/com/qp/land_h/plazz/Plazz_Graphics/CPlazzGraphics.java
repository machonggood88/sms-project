package com.qp.land_h.plazz.Plazz_Graphics;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Interface.UserInterface.IClientUserItem;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.view.Gravity;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.df.PDF;

/**
 * Plazz ͨ�û�����
 * 
 * @note 11.12.5 ���ͷ�����
 * @remark
 */
public class CPlazzGraphics{

	public final static int		FACE_COUNT	= 16;
	/** ͷ�� **/
	protected static CImageEx	m_ImageFace[];
	/** �ײ����� **/
	protected static CImageEx	m_ImageLine;

	static CPlazzGraphics		instance;

	static {
		m_ImageFace = new CImageEx[FACE_COUNT];
		instance = null;
		try {
			m_ImageLine = new CImageEx(ClientPlazz.RES_PATH + "custom/flag_line.png");
			for (int i = 0; i < m_ImageFace.length; i++)
				m_ImageFace[i] = new CImageEx(ClientPlazz.RES_PATH + "custom/face/head_" + (i + 1) + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	CPlazzGraphics(){
		InitAvatarMode();
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	public static CPlazzGraphics onCreate() {
		if (instance == null)
			instance = new CPlazzGraphics();
		return instance;
	}

	/**
	 * ͷ��ģ��
	 */
	protected void InitAvatarMode() {
		try {
			if (m_ImageLine.GetBitMap() == null)
				m_ImageLine.OnReLoadImage();
			for (int i = 0; i < m_ImageFace.length; i++) {
				if (m_ImageFace[i].GetBitMap() == null)
					m_ImageFace[i].OnReLoadImage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �û�ͷ�����
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 * @param useritem
	 */
	public void DrawUserAvatar(Canvas canvas, int x, int y, IClientUserItem useritem) {
		if (useritem == null) {
			DrawUserAvatarDebug(canvas, x, y);
			return;
		}
		DrawUserAvatar(canvas, x, y, (useritem.GetFaceID() - 1) % 16);
	}

	/**
	 * IDͷ�����
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 * @param id
	 */
	public void DrawUserAvatar(Canvas canvas, int x, int y, int id) {
		if (id < 0 || id > 15) {
			DrawUserAvatarDebug(canvas, x, y);
			return;
		}
		m_ImageFace[id].DrawImage(canvas, x, y);

	}

	/**
	 * Ĭ��ͷ�����
	 * 
	 * @param canvas
	 * @param x
	 * @param y
	 */
	private void DrawUserAvatarDebug(Canvas canvas, int x, int y) {
		m_ImageFace[0].DrawImage(canvas, x, y);
	}

	public int GetFaceH() {
		if (m_ImageFace[0] != null)
			return m_ImageFace[0].GetW();
		return 0;
	}

	public int GetFaceW() {
		if (m_ImageFace[0] != null)
			return m_ImageFace[0].GetW();
		return 0;
	}

	/**
	 * �ײ�װ����
	 * 
	 * @param canvas
	 */
	public static void DrawLine(Canvas canvas, int x, int y) {
		Rect rcsrc = new Rect(0, 0, m_ImageLine.GetW(), m_ImageLine.GetH());
		Rect rcdst = new Rect(0, y, ClientPlazz.SCREEN_WIDHT, y
						+ m_ImageLine.GetH());
		canvas.drawBitmap(m_ImageLine.GetBitMap(), rcsrc, rcdst, null);
	}

	public static void ShowToast(int x, int y, String szinfo) {
		Toast toast = Toast.makeText(DF.GetContext(), szinfo, Toast.LENGTH_SHORT);

		toast.setGravity(Gravity.TOP | Gravity.LEFT, x, y);

		toast.show();
	}

	public static void ShowToast(String szinfo) {
		Toast toast = Toast.makeText(DF.GetContext(), szinfo, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * �������֣�����
	 * 
	 * @param canvas �豸
	 * @param image ͼƬ
	 * @param x ����
	 * @param y ����
	 * @param szsrc �����������
	 * @param szdst �������
	 * @param type ����
	 */
	public static void DrawHorizontalNum(Canvas canvas, CImageEx image, int x, int y, String szsrc, String szdst,
					int type) {
		int srclenght = szsrc.length();
		int dstlenght = szdst.length();

		if (srclenght < 1 || dstlenght < 1)
			return;

		int width = image.GetW() / srclenght;
		int height = image.GetH();

		int startx = x;
		int starty = y;

		switch (type) {
			case PDF.STYLE_LEFT:
				break;
			case PDF.STYLE_CENTRE:
				startx -= (dstlenght * width) / 2;
				break;
			case PDF.STYLE_RIGHT:
				startx -= dstlenght * width;
				break;
		}
		for (int i = 0; i < dstlenght; i++) {
			int index = szsrc.indexOf(szdst.substring(i, i + 1));
			if (index != -1) {
				image.DrawImage(canvas, startx, starty, width, height, index * width, 0);
				startx += width;
			}
		}

	}

	/**
	 * �������֣�����
	 * 
	 * @param canvas �豸
	 * @param image ͼƬ
	 * @param x ����
	 * @param y ����
	 * @param szsrc �����������
	 * @param szdst �������
	 * @param type ����
	 */
	public static void DrawVerticalNum(Canvas canvas, CImageEx image, int x, int y, String szsrc, String szdst, int type) {
		int srclenght = szsrc.length();
		int dstlenght = szdst.length();

		if (srclenght < 1 || dstlenght < 1)
			return;

		int width = image.GetW() / srclenght;
		int height = image.GetH();

		int startx = x;
		int starty = y;

		switch (type) {
			case PDF.STYLE_TOP:
				break;
			case PDF.STYLE_VCENTRE:
				starty -= (dstlenght * height) / 2;
				break;
			case PDF.STYLE_BOTTOM:
				starty -= dstlenght * height;
				break;
		}

		for (int i = 0; i < dstlenght; i++) {
			int index = szsrc.indexOf(szdst.substring(i, i + 1));
			if (index != -1) {
				image.DrawImage(canvas, startx, starty, width, height, index * width, 0);
				starty += height;
			}
		}

	}
}
