package com.qp.land_h.plazz.Plazz_Fram.Option;

import java.io.IOException;

import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Graphics.CImageEx;
import Lib_Interface.ICallBack;
import Lib_Interface.IKeyBackDispatch;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.CActivity;
import Lib_System.GlobalUnits.CGlobalUnitsEx;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Lib_System.View.ButtonView.CRadioButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.CFram;
import com.qp.land_h.plazz.Plazz_Control.CSeekBar;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Interface.ISeekControl;
import com.qp.land_h.plazz.df.PDF;

public class CPlazzOptionEngine extends CViewEngine implements IKeyBackDispatch, ISeekControl, ISingleClickListener, ICallBack {

	public final static String	TickOffPath		= ClientPlazz.RES_PATH + "option/rbt_tickoff.png";
	public final static String	TickFramPath	= ClientPlazz.RES_PATH + "option/bg_tickoff.png";

	public final static String	BarPoint		= ClientPlazz.RES_PATH + "option/seekbar_p.png";
	public final static String	BarBG			= ClientPlazz.RES_PATH + "option/seekbarbg.png";
	public final static String	BarFull			= ClientPlazz.RES_PATH + "option/seekbarfull.png";
	/** 图片资源 **/
	// protected CImageEx m_ImageWordShake;
	// protected CImageEx m_ImageWordSveMode;
	protected CImageEx			m_ImageWordMusic;
	protected CImageEx			m_ImageWordSound;
	protected CImageEx			m_ImageWordLight;
	// protected CImageEx m_ImageWordAbout;

	protected CImageEx			m_ImageTickoff;
	protected CImageEx			m_ImageTickFram;

	protected CImageButton		m_btMusicMax;
	protected CImageButton		m_btSoundMax;
	protected CImageButton		m_btLightMax;
	protected CImageButton		m_btMusicMin;
	protected CImageButton		m_btSoundMin;
	protected CImageButton		m_btLightMin;

	protected CImageButton		m_btCancel;

	/** 选择控件 **/
	//protected CRadioButton		m_rbtAbout;
	//protected CRadioButton		m_rbtShake;
	// protected CRadioButton m_rbtSveMode;

	protected CSeekBar			m_MusicSeek;
	protected CSeekBar			m_SoundSeek;
	protected CSeekBar			m_LightSeek;
	protected int				m_nRecordID		= Integer.MIN_VALUE;

	protected CFram				m_FramTop;

	public CPlazzOptionEngine(Context context) {
		super(context);
		setWillNotDraw(false);

		int topheight = 0;
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				topheight = 45;
				break;
			case DF.DEVICETYPE_HVGA :
				topheight = 25;
				break;
			case DF.DEVICETYPE_QVGA :
				topheight = 20;
				break;

		}
		m_MusicSeek = new CSeekBar(context, BarPoint, BarBG, BarFull);
		m_SoundSeek = new CSeekBar(context, BarPoint, BarBG, BarFull);
		m_LightSeek = new CSeekBar(context, BarPoint, BarBG, BarFull);
		m_FramTop = new CFram(context, "", ClientPlazz.SCREEN_WIDHT, topheight);
		m_FramTop.SetMsg("游 戏 设 置");
		// 文字描述
		try {
			// m_ImageWordShake = new CImageEx(ClientPlazz.RES_PATH + "option/word_shake.png");
			// m_ImageWordSveMode = new CImageEx(ClientPlazz.RES_PATH + "option/word_sve.png");
			m_ImageWordMusic = new CImageEx(ClientPlazz.RES_PATH + "option/word_music.png");
			m_ImageWordSound = new CImageEx(ClientPlazz.RES_PATH + "option/word_sound.png");
			m_ImageWordLight = new CImageEx(ClientPlazz.RES_PATH + "option/word_light.png");
			// m_ImageWordAbout = new CImageEx(ClientPlazz.RES_PATH + "option/word_freshman.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_btMusicMax = new CImageButton(context, ClientPlazz.RES_PATH + "option/flag_music_big.png", true);
		m_btSoundMax = new CImageButton(context, ClientPlazz.RES_PATH + "option/flag_music_big.png", true);
		m_btLightMax = new CImageButton(context, ClientPlazz.RES_PATH + "option/flag_light_big.png", true);

		m_btMusicMin = new CImageButton(context, ClientPlazz.RES_PATH + "option/flag_music.png", true);
		m_btSoundMin = new CImageButton(context, ClientPlazz.RES_PATH + "option/flag_music.png", true);
		m_btLightMin = new CImageButton(context, ClientPlazz.RES_PATH + "option/flag_light.png", true);

		//m_rbtShake = new CRadioButton(context, TickFramPath, TickOffPath, "");
		// m_rbtSveMode = new CRadioButton(context, TickFramPath, TickOffPath, "");
		//m_rbtAbout = new CRadioButton(context, TickFramPath, TickOffPath, "");

		m_btCancel = new CImageButton(context, ClientPlazz.RES_PATH + "custom/button/bt_cancel.png");

		m_MusicSeek.SetSeekControl(this);
		m_SoundSeek.SetSeekControl(this);
		m_LightSeek.SetSeekControl(this);

		m_btCancel.setSingleClickListener(this);

		//m_rbtAbout.setSingleClickListener(this);
		//m_rbtShake.setSingleClickListener(this);
		// m_rbtSveMode.setSingleClickListener(this);

		m_btMusicMax.setSingleClickListener(this);
		m_btSoundMax.setSingleClickListener(this);
		m_btLightMax.setSingleClickListener(this);

		m_btMusicMin.setSingleClickListener(this);
		m_btSoundMin.setSingleClickListener(this);
		m_btLightMin.setSingleClickListener(this);

		addView(m_btCancel);

		//addView(m_rbtAbout);
		//addView(m_rbtShake);
		// addView(m_rbtSveMode);

		addView(m_btMusicMax);
		addView(m_btSoundMax);
		addView(m_btLightMax);
		addView(m_btMusicMin);
		addView(m_btSoundMin);
		addView(m_btLightMin);

		addView(m_MusicSeek);
		addView(m_SoundSeek);
		addView(m_LightSeek);

		addView(m_FramTop);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				onLayoutH(changed, l, t, r, b);
				break;
			case DF.DEVICETYPE_HVGA :
				onLayoutM(changed, l, t, r, b);
				break;
			case DF.DEVICETYPE_QVGA :
				onLayoutL(changed, l, t, r, b);
				break;

		}

	}

	private void onLayoutL(boolean changed, int l, int t, int r, int b) {
		int x = 0, y = 0;
		m_FramTop.layout(0, 0, ClientPlazz.SCREEN_WIDHT, m_FramTop.GetH());
		int centrex = (r - l) / 2 + m_ImageWordMusic.GetW() / 2;

		x = centrex - m_MusicSeek.GetW() / 2 - 5 - m_btMusicMin.getW() - 5;
		y = m_FramTop.GetH() + 20;
		m_btMusicMin.layout(x, y, 0, 0);
		x += (5 + m_btMusicMin.getW());
		m_MusicSeek.layout(x, y, 0, 0);
		x += (m_MusicSeek.GetW() + 15);
		m_btMusicMax.layout(x, y, 0, 0);

		x = centrex - m_SoundSeek.GetW() / 2 - 5 - m_btSoundMin.getW() - 5;
		y += (m_MusicSeek.GetH() + 10);
		m_btSoundMin.layout(x, y, 0, 0);
		x += (5 + m_btSoundMin.getW());
		m_SoundSeek.layout(x, y, 0, 0);
		x += (m_SoundSeek.GetW() + 15);
		m_btSoundMax.layout(x, y, 0, 0);

		x = centrex - m_LightSeek.GetW() / 2 - 5 - m_btLightMin.getW() - 5;
		y += (m_SoundSeek.GetH() + 10);
		m_btLightMin.layout(x, y, 0, 0);
		x += (5 + m_btLightMin.getW());
		m_LightSeek.layout(x, y, 0, 0);
		x += (m_LightSeek.GetW() + 15);
		m_btLightMax.layout(x, y, 0, 0);

		//centrex = (r - l) / 2;
		//x = centrex - m_rbtShake.getW() - 10;
		//y += (m_LightSeek.GetH() + 20);
		//m_rbtShake.layout(x, y, 0, 0);

		// x = centrex + 10 + m_ImageWordSveMode.GetW() + 5;
		// m_rbtSveMode.layout(x, y, 0, 0);

		//x = centrex - m_rbtShake.getW() - 10;
		//y += (m_rbtShake.getH() + 10);
		//m_rbtAbout.layout(x, y, 0, 0);

		m_btCancel.layout(5, b - m_btCancel.getH(), 0, 0);

	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		int x = 0, y = 0;
		m_FramTop.layout(0, 0, ClientPlazz.SCREEN_WIDHT, m_FramTop.GetH());
		int centrex = (r - l) / 2 + m_ImageWordMusic.GetW() / 2;

		x = centrex - m_MusicSeek.GetW() / 2 - 5 - m_btMusicMin.getW() - 15;
		y = m_FramTop.GetH() + 25;
		m_btMusicMin.layout(x, y, 0, 0);
		x += (5 + m_btMusicMin.getW());
		m_MusicSeek.layout(x, y, 0, 0);
		x += (m_MusicSeek.GetW() + 15);
		m_btMusicMax.layout(x, y, 0, 0);

		x = centrex - m_SoundSeek.GetW() / 2 - 5 - m_btSoundMin.getW() - 15;
		y += (m_MusicSeek.GetH() + 15);
		m_btSoundMin.layout(x, y, 0, 0);
		x += (5 + m_btSoundMin.getW());
		m_SoundSeek.layout(x, y, 0, 0);
		x += (m_SoundSeek.GetW() + 15);
		m_btSoundMax.layout(x, y, 0, 0);

		x = centrex - m_LightSeek.GetW() / 2 - 5 - m_btLightMin.getW() - 15;
		y += (m_SoundSeek.GetH() + 15);
		m_btLightMin.layout(x, y, 0, 0);
		x += (5 + m_btLightMin.getW());
		m_LightSeek.layout(x, y, 0, 0);
		x += (m_LightSeek.GetW() + 15);
		m_btLightMax.layout(x, y, 0, 0);

		//centrex = (r - l) / 2;
		//x = centrex - m_rbtShake.getW() - 10;
		//y += (m_LightSeek.GetH() + 25);
		//m_rbtShake.layout(x, y, 0, 0);

		// x = centrex + 10 + m_ImageWordSveMode.GetW() + 5;
		// m_rbtSveMode.layout(x, y, 0, 0);

		//x = centrex - m_rbtShake.getW() - 10;
		//y += (m_rbtShake.getH() + 15);
		//m_rbtAbout.layout(x, y, 0, 0);

		m_btCancel.layout(5, b - m_btCancel.getH(), 0, 0);
	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		int x = 0, y = 0;
		m_FramTop.layout(0, 0, ClientPlazz.SCREEN_WIDHT, m_FramTop.GetH());
		int centrex = (r - l) / 2 + m_ImageWordMusic.GetW() / 2;
		x = centrex - m_MusicSeek.GetW() / 2 - 5 - m_btMusicMin.getW() - 5;
		y = m_FramTop.GetH() + 30;

		m_btMusicMin.layout(x, y + 5, 0, 0);
		x += (5 + m_btMusicMin.getW());
		m_MusicSeek.layout(x, y, 0, 0);
		x += (m_MusicSeek.GetW() + 5);
		m_btMusicMax.layout(x, y, 0, 0);

		x = centrex - m_SoundSeek.GetW() / 2 - 5 - m_btSoundMin.getW() - 5;
		y += (m_MusicSeek.GetH() + 5);

		m_btSoundMin.layout(x, y + 5, 0, 0);
		x += (5 + m_btSoundMin.getW());
		m_SoundSeek.layout(x, y, 0, 0);
		x += (m_SoundSeek.GetW() + 5);
		m_btSoundMax.layout(x, y, 0, 0);

		x = centrex - m_LightSeek.GetW() / 2 - 5 - m_btLightMin.getW() - 5;
		y += (m_SoundSeek.GetH() + 5);

		m_btLightMin.layout(x, y + 5, 0, 0);
		x += (5 + m_btLightMin.getW());
		m_LightSeek.layout(x, y, 0, 0);
		x += (m_LightSeek.GetW() + 5);
		m_btLightMax.layout(x, y, 0, 0);

		//centrex = (r - l) / 2;
		//x = centrex - m_rbtShake.getW() - 10;
		//y += (m_LightSeek.GetH() + 5);
		//m_rbtShake.layout(x, y, 0, 0);

		// x = centrex + 10 + m_ImageWordSveMode.GetW() + 5;
		// m_rbtSveMode.layout(x, y, 0, 0);

		//x = centrex - m_rbtShake.getW() - 10;
		//y += (m_rbtShake.getH() + 5);
		//m_rbtAbout.layout(x, y, 0, 0);

		m_btCancel.layout(5, b - m_btCancel.getH(), 0, 0);
	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		CGlobalUnitsEx Global = ClientPlazz.GetGlobalUnits();
		if (Global != null) {
			Global.SetCallBack(null);
		}
		m_FramTop.OnDestoryRes();
		m_MusicSeek.OnDestoryRes();
		m_SoundSeek.OnDestoryRes();
		m_LightSeek.OnDestoryRes();

		// ClientPlazz.RemoveAboutView();

		// m_ImageWordShake.OnReleaseImage();
		// m_ImageWordSveMode.OnReleaseImage();
		m_ImageWordMusic.OnReleaseImage();
		m_ImageWordSound.OnReleaseImage();
		m_ImageWordLight.OnReleaseImage();
		// m_ImageWordAbout.OnReleaseImage();

		m_btMusicMax.setVisibility(INVISIBLE);
		m_btSoundMax.setVisibility(INVISIBLE);
		m_btLightMax.setVisibility(INVISIBLE);
		m_btMusicMin.setVisibility(INVISIBLE);
		m_btSoundMin.setVisibility(INVISIBLE);
		m_btLightMin.setVisibility(INVISIBLE);
		//m_rbtAbout.setVisibility(INVISIBLE);
		m_btCancel.setVisibility(INVISIBLE);
		//m_rbtShake.setVisibility(INVISIBLE);
		// m_rbtSveMode.setVisibility(INVISIBLE);
	}

	@Override
	public void OnInitRes() {
		// 设置背景
		setBackgroundDrawable(new BitmapDrawable(new CImage(getContext(), ClientPlazz.RES_PATH + "custom/bg.png", null, true).GetBitMap()));
		CGlobalUnitsEx Global = ClientPlazz.GetGlobalUnits();
		if (Global != null) {
			Global.SetCallBack(this);
			ConfigBySystem(Global);
		}
		m_FramTop.OnInitRes();
		m_MusicSeek.OnInitRes();
		m_SoundSeek.OnInitRes();
		m_LightSeek.OnInitRes();;
		try {
			// m_ImageWordShake.OnReLoadImage();
			// m_ImageWordSveMode.OnReLoadImage();
			m_ImageWordMusic.OnReLoadImage();
			m_ImageWordSound.OnReLoadImage();
			m_ImageWordLight.OnReLoadImage();
			// m_ImageWordAbout.OnReLoadImage();

		} catch (IOException e) {
			e.printStackTrace();
		}

		m_btMusicMax.setVisibility(VISIBLE);
		m_btSoundMax.setVisibility(VISIBLE);
		m_btLightMax.setVisibility(VISIBLE);
		m_btMusicMin.setVisibility(VISIBLE);
		m_btSoundMin.setVisibility(VISIBLE);
		m_btLightMin.setVisibility(VISIBLE);
		//m_rbtAbout.setVisibility(VISIBLE);
		m_btCancel.setVisibility(VISIBLE);
		//m_rbtShake.setVisibility(VISIBLE);
		// m_rbtSveMode.setVisibility(VISIBLE);
	}

	private void ConfigBySystem(CGlobalUnitsEx Global) {
		if (Global != null) {
			int music = Global.GetSystemMusic();
			int sound = Global.GetSystemSound();
			int light = Global.GetSystemLight();
			m_MusicSeek.SetProgress(music * m_MusicSeek.GetMax() / Global.GetMaxMusic(), false);
			m_SoundSeek.SetProgress(sound * m_SoundSeek.GetMax() / Global.GetMaxSound(), false);
			m_LightSeek.SetProgress((Math.max(0, light - PDF.MIN_LIGHT)) * m_LightSeek.GetMax() / (PDF.MAX_LIGHT - PDF.MIN_LIGHT), false);
			//m_rbtShake.SetTickOff(Global.IsShakeMode());
			// m_rbtSveMode.SetTickOff(Global.IsSVEMode());
		}
	}

	@Override
	public void OnSeekChange(View view) {
		CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
		if (global != null) {
			if (view.getId() == m_MusicSeek.getId()) {
				int music = global.GetMaxMusic() * m_MusicSeek.GetProgress() / m_MusicSeek.GetMax();
				global.OnSetMusic(music);
			} else if (view.getId() == m_SoundSeek.getId()) {
				int sound = global.GetMaxSound() * m_SoundSeek.GetProgress() / m_SoundSeek.GetMax();
				global.OnSetSound(sound);
			} else if (view.getId() == m_LightSeek.getId()) {
				int light = PDF.MIN_LIGHT + (PDF.MAX_LIGHT - PDF.MIN_LIGHT) * m_LightSeek.GetProgress() / m_LightSeek.GetMax();
				global.OnSetLight(light);
			}
		}
	}

	@Override
	protected void Render(Canvas canvas) {
		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				onRenderH(canvas);
				break;
			case DF.DEVICETYPE_HVGA :
				onRenderM(canvas);
				break;
			case DF.DEVICETYPE_QVGA :
				onRenderL(canvas);
				break;

		}

	}

	private void onRenderL(Canvas canvas) {
		int x = 0, y = 0;
		x = m_btMusicMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btMusicMin.getTop();
		m_ImageWordMusic.DrawImage(canvas, x, y);

		x = m_btSoundMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btSoundMin.getTop();
		m_ImageWordSound.DrawImage(canvas, x, y);

		x = m_btLightMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btLightMin.getTop();
		m_ImageWordLight.DrawImage(canvas, x, y);

		// x = m_rbtAbout.getLeft() - m_ImageWordAbout.GetW() - 5;
		// y = m_rbtAbout.getTop() + m_rbtAbout.getH() / 2 - m_ImageWordAbout.GetH() / 2;
		// m_ImageWordAbout.DrawImage(canvas, x, y);

		// x = m_rbtShake.getLeft() - m_ImageWordShake.GetW() - 5;
		// y = m_rbtShake.getTop() + m_rbtShake.getH() / 2 - m_ImageWordShake.GetH() / 2;
		// m_ImageWordShake.DrawImage(canvas, x, y);

		// x = m_rbtSveMode.getLeft() - m_ImageWordSveMode.GetW() - 5;
		// y = m_rbtSveMode.getTop() + m_rbtSveMode.getH() / 2 - m_ImageWordSveMode.GetH() / 2;
		// m_ImageWordSveMode.DrawImage(canvas, x, y);

		CPlazzGraphics.DrawLine(canvas, 0, ClientPlazz.SCREEN_HEIGHT - m_btCancel.getH() * 2 / 3);

	}

	private void onRenderM(Canvas canvas) {
		int x = 0, y = 0;
		x = m_btMusicMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btMusicMin.getTop();
		m_ImageWordMusic.DrawImage(canvas, x, y);

		x = m_btSoundMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btSoundMin.getTop();
		m_ImageWordSound.DrawImage(canvas, x, y);

		x = m_btLightMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btLightMin.getTop();
		m_ImageWordLight.DrawImage(canvas, x, y);

		// x = m_rbtAbout.getLeft() - m_ImageWordAbout.GetW() - 5;
		// y = m_rbtAbout.getTop() + m_rbtAbout.getH() / 2 - m_ImageWordAbout.GetH() / 2;
		// m_ImageWordAbout.DrawImage(canvas, x, y);

		// x = m_rbtShake.getLeft() - m_ImageWordShake.GetW() - 5;
		// y = m_rbtShake.getTop() + m_rbtShake.getH() / 2 - m_ImageWordShake.GetH() / 2;
		// m_ImageWordShake.DrawImage(canvas, x, y);

		// x = m_rbtSveMode.getLeft() - m_ImageWordSveMode.GetW() - 5;
		// y = m_rbtSveMode.getTop() + m_rbtSveMode.getH() / 2 - m_ImageWordSveMode.GetH() / 2;
		// m_ImageWordSveMode.DrawImage(canvas, x, y);

		CPlazzGraphics.DrawLine(canvas, 0, ClientPlazz.SCREEN_HEIGHT - m_btCancel.getH() * 2 / 3);

	}

	private void onRenderH(Canvas canvas) {
		int x = 0, y = 0;
		x = m_btMusicMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btMusicMin.getTop() - 5;
		m_ImageWordMusic.DrawImage(canvas, x, y);

		x = m_btSoundMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btSoundMin.getTop() - 5;
		m_ImageWordSound.DrawImage(canvas, x, y);

		x = m_btLightMin.getLeft() - 10 - m_ImageWordMusic.GetW();
		y = m_btLightMin.getTop() - 5;
		m_ImageWordLight.DrawImage(canvas, x, y);

		//x = m_rbtAbout.getLeft() - m_ImageWordAbout.GetW() - 5;
		//y = m_rbtAbout.getTop() + m_rbtAbout.getH() / 2 - m_ImageWordAbout.GetH() / 2;
		//m_ImageWordAbout.DrawImage(canvas, x, y);

		// x = m_rbtShake.getLeft() - m_ImageWordShake.GetW() - 5;
		// y = m_rbtShake.getTop() + m_rbtShake.getH() / 2 - m_ImageWordShake.GetH() / 2;
		// m_ImageWordShake.DrawImage(canvas, x, y);

		// x = m_rbtSveMode.getLeft() - m_ImageWordSveMode.GetW() - 5;
		// y = m_rbtSveMode.getTop() + m_rbtSveMode.getH() / 2 - m_ImageWordSveMode.GetH() / 2;
		// m_ImageWordSveMode.DrawImage(canvas, x, y);

		CPlazzGraphics.DrawLine(canvas, 0, ClientPlazz.SCREEN_HEIGHT - m_btCancel.getH() * 2 / 3);

	}

	@Override
	public boolean KeyBackDispatch() {
		if (m_nRecordID == Integer.MIN_VALUE || m_nRecordID == ClientPlazz.MS_OPTION)
			PDF.SendMainMessage(ClientPlazz.MM_EXITSYSTEM, 0, null);
		else
			PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, m_nRecordID, null);
		return true;
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		if (view.getId() == m_btCancel.getId()) {
			KeyBackDispatch();
			return true;
		} else if (view.getId() == m_btMusicMax.getId()) {
			m_MusicSeek.SetProgress(m_MusicSeek.GetMax(), true);
			return true;
		} else if (view.getId() == m_btMusicMin.getId()) {
			m_MusicSeek.SetProgress(0, true);
			return true;
		} else if (view.getId() == m_btSoundMax.getId()) {
			m_SoundSeek.SetProgress(m_SoundSeek.GetMax(), true);
			return true;
		} else if (view.getId() == m_btSoundMin.getId()) {
			m_SoundSeek.SetProgress(0, true);
			return true;
		} else if (view.getId() == m_btLightMax.getId()) {
			m_LightSeek.SetProgress(m_LightSeek.GetMax(), true);
			return true;
		} else if (view.getId() == m_btLightMin.getId()) {
			m_LightSeek.SetProgress(0, true);
			return true;
		} 
//		else if (view.getId() == m_rbtShake.getId()) {
//			CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
//			if (global != null) {
//				m_rbtShake.SetTickOff(!m_rbtShake.GetTickOff());
//				global.OnVibrator(m_rbtShake.GetTickOff());
//			}
//			return true;
//		}
		// else if (view.getId() == m_rbtSveMode.getId()) {
		// CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
		// if (global != null) {
		// m_rbtSveMode.SetTickOff(!m_rbtSveMode.GetTickOff());
		// global.OnSve(m_rbtSveMode.GetTickOff());
		// }
		// SetSveControl(!m_rbtSveMode.GetTickOff());
		// return true;
		//
		// }
//		else if (view.getId() == m_rbtAbout.getId()) {
//			CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
//			if (global != null) {
//				m_rbtAbout.SetTickOff(!m_rbtAbout.GetTickOff());
//				global.OnFreshManMode(m_rbtAbout.GetTickOff());
//			}
//			return true;
//		}
		return false;
	}

	protected void SetSveControl(boolean clickable) {
		//m_rbtShake.setClickable(clickable);
		m_SoundSeek.setClickable(clickable);
		m_MusicSeek.setClickable(clickable);
		m_LightSeek.setClickable(clickable);
		m_btSoundMax.setClickable(clickable);
		m_btSoundMax.setClickable(clickable);
		m_btMusicMax.setClickable(clickable);
		m_btMusicMin.setClickable(clickable);
		m_btLightMax.setClickable(clickable);
		m_btLightMin.setClickable(clickable);
	}

	public void SetRecordID(int id) {
		m_nRecordID = id;
	}

	@Override
	public boolean OnCallBackDispath(boolean succed, Object obj) {
		if (obj == null || succed == false)
			return false;
		CGlobalUnitsEx Global = ClientPlazz.GetGlobalUnits();
		if (Global == null)
			return false;
		int id = (Integer) obj;
		switch (id) {
			case CGlobalUnitsEx.IDM_SVE : {
				ConfigBySystem(Global);
				if (Global.IsSVEMode()) {
					m_LightSeek.SetProgress(0, false);
					m_LightSeek.setClickable(false);
					m_MusicSeek.setClickable(false);
					m_SoundSeek.setClickable(false);
					//m_rbtShake.setClickable(false);
				} else {
					m_LightSeek.SetProgress(m_LightSeek.GetMax(), false);
					m_LightSeek.setClickable(true);
					m_MusicSeek.setClickable(true);
					m_SoundSeek.setClickable(true);
					//m_rbtShake.setClickable(true);
				}
				break;
			}
			case CGlobalUnitsEx.IDM_SHAKE : {

				break;
			}
			case CGlobalUnitsEx.IDM_LIGHT : {

				break;
			}
			case CGlobalUnitsEx.IDM_LIGHTUP : {
				int light = Global.GetSystemLight();
				m_SoundSeek.SetProgress((Math.max(0, light - PDF.MIN_LIGHT)) * m_LightSeek.GetMax() / (PDF.MAX_LIGHT - PDF.MIN_LIGHT), false);
				break;
			}
			case CGlobalUnitsEx.IDM_LIGHTDOWN : {
				int light = Global.GetSystemLight();
				m_SoundSeek.SetProgress((Math.max(0, light - PDF.MIN_LIGHT)) * m_LightSeek.GetMax() / (PDF.MAX_LIGHT - PDF.MIN_LIGHT), false);
				break;
			}
			case CGlobalUnitsEx.IDM_MUSIC : {

				break;
			}
			case CGlobalUnitsEx.IDM_MUSICUP : {
				int music = Global.GetSystemMusic();
				m_MusicSeek.SetProgress(music * m_MusicSeek.GetMax() / Global.GetMaxMusic(), false);
				break;
			}
			case CGlobalUnitsEx.IDM_MUSICDOWN : {
				int music = Global.GetSystemMusic();
				m_MusicSeek.SetProgress(music * m_MusicSeek.GetMax() / Global.GetMaxMusic(), false);
				break;
			}
			case CGlobalUnitsEx.IDM_SOUND : {

				break;
			}
			case CGlobalUnitsEx.IDM_SOUNDUP : {
				int sound = Global.GetSystemSound();
				m_SoundSeek.SetProgress(sound * m_SoundSeek.GetMax() / Global.GetMaxSound(), false);

				break;
			}
			case CGlobalUnitsEx.IDM_SOUNDDOWN : {
				int sound = Global.GetSystemSound();
				m_SoundSeek.SetProgress(sound * m_SoundSeek.GetMax() / Global.GetMaxSound(), false);
				break;
			}
		}
		return false;
	}

}
