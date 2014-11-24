package com.qp.land_h.game.Game_Windows;

import java.io.IOException;

import Lib_Graphics.CImageEx;
import Lib_Interface.ICallBack;
import Lib_Interface.IRangeObtain;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_System.CActivity;
import Lib_System.GlobalUnits.CGlobalUnitsEx;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import Lib_System.View.ButtonView.CRadioButton;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.CSeekBar;
import com.qp.land_h.plazz.Plazz_Interface.ISeekControl;

public class CGameOptionEngine extends CViewEngine implements IRangeObtain, ISeekControl, ISingleClickListener, ICallBack {

	public final static String	TickOffPath		= ClientPlazz.RES_PATH + "gameres/option/rbt_tickoff.png";
	public final static String	TickFramPath	= ClientPlazz.RES_PATH + "gameres/option/bg_tickoff.png";

	public final static String	BarPoint		= ClientPlazz.RES_PATH + "gameres/option/seekbar_p.png";
	public final static String	BarBG			= ClientPlazz.RES_PATH + "gameres/option/seekbarbg.png";
	public final static String	BarFull			= ClientPlazz.RES_PATH + "gameres/option/seekbarfull.png";

	/** 图片资源 **/
	protected CImageEx			m_ImageBack;
	// protected CImageEx m_ImageWordShake;
	// protected CImageEx m_ImageWordSveMode;
	protected CImageEx			m_ImageWordMusic;
	protected CImageEx			m_ImageWordSound;
	// protected CImageEx m_ImageWordLight;
	// protected CImageEx m_ImageWordAbout;

	protected CImageEx			m_ImageTickoff;
	protected CImageEx			m_ImageTickFram;

	// protected CImageEx m_ImageTop;

	protected CImageButton		m_btMusicMax;
	protected CImageButton		m_btSoundMax;
	// protected CImageButton m_btLightMax;
	protected CImageButton		m_btMusicMin;
	protected CImageButton		m_btSoundMin;
	// protected CImageButton m_btLightMin;

	/** 选择控件 **/
	// protected CRadioButton m_rbtShake;
	// protected CRadioButton m_rbtSveMode;
	// protected CRadioButton m_rbtAbout;

	protected CSeekBar			m_MusicSeek;
	protected CSeekBar			m_SoundSeek;

	// protected CSeekBar m_LightSeek;

	public CGameOptionEngine(Context context) {
		super(context);
		setWillNotDraw(false);
		m_MusicSeek = new CSeekBar(context, BarPoint, BarBG, BarFull);
		m_SoundSeek = new CSeekBar(context, BarPoint, BarBG, BarFull);
		// m_LightSeek = new CSeekBar(context);

		// 文字描述
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/game_option_bg.png");
			// m_ImageWordShake = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/word_shake.png");
			// m_ImageWordSveMode = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/word_sve.png");
			m_ImageWordMusic = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/word_music.png");
			m_ImageWordSound = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/word_sound.png");
			// m_ImageWordLight = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/word_light.png");
			// m_ImageWordAbout = new CImageEx(ClientPlazz.RES_PATH + "gameres/option/word_freshman.png");
			// m_ImageTop = new CImageEx(ClientPlazz.RES_PATH + "option/option_top.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_btMusicMax = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/option/flag_music_big.png", true);
		m_btSoundMax = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/option/flag_music_big.png", true);
		// m_btLightMax = new CImageButton(context, ClientPlazz.RES_PATH+ "option/flag_light_big.png", true);

		m_btMusicMin = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/option/flag_music.png", true);
		m_btSoundMin = new CImageButton(context, ClientPlazz.RES_PATH + "gameres/option/flag_music.png", true);
		// m_btLightMin = new CImageButton(context, ClientPlazz.RES_PATH+ "option/flag_light.png", true);

		// m_rbtShake = new CRadioButton(context, TickFramPath, TickOffPath, "");
		// m_rbtSveMode = new CRadioButton(context, TickFramPath, TickOffPath, "");

		// m_rbtAbout = new CRadioButton(context, TickFramPath, TickOffPath, "");

		m_MusicSeek.SetSeekControl(this);
		m_SoundSeek.SetSeekControl(this);
		// m_LightSeek.SetSeekControl(this);

		// m_rbtAbout.setSingleClickListener(this);
		// m_rbtShake.setSingleClickListener(this);
		// m_rbtSveMode.setSingleClickListener(this);

		m_btMusicMax.setSingleClickListener(this);
		m_btSoundMax.setSingleClickListener(this);
		// m_btLightMax.setSingleClickListener(this);

		m_btMusicMin.setSingleClickListener(this);
		m_btSoundMin.setSingleClickListener(this);
		// m_btLightMin.setSingleClickListener(this);

		// addView(m_rbtShake);
		// addView(m_rbtSveMode);

		addView(m_btMusicMax);
		addView(m_btSoundMax);
		// addView(m_btLightMax);
		addView(m_btMusicMin);
		addView(m_btSoundMin);
		// addView(m_btLightMin);
		// addView(m_rbtAbout);

		addView(m_MusicSeek);
		addView(m_SoundSeek);
		// addView(m_LightSeek);
	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		m_MusicSeek.OnDestoryRes();
		m_SoundSeek.OnDestoryRes();;
		m_ImageBack.OnReleaseImage();
		// m_ImageWordShake.OnReleaseImage();
		// m_ImageWordSveMode.OnReleaseImage();
		m_ImageWordMusic.OnReleaseImage();
		m_ImageWordSound.OnReleaseImage();
		// m_ImageWordLight.OnReleaseImage();
		// m_ImageWordAbout.OnReleaseImage();
		// m_ImageTop.OnReleaseImage();
		m_btMusicMax.setVisibility(INVISIBLE);
		m_btSoundMax.setVisibility(INVISIBLE);
		// m_btLightMax.setVisibility(INVISIBLE);
		m_btMusicMin.setVisibility(INVISIBLE);
		m_btSoundMin.setVisibility(INVISIBLE);
		// m_btLightMin.setVisibility(INVISIBLE);
		// m_rbtAbout.setVisibility(INVISIBLE);
		// m_rbtShake.setVisibility(INVISIBLE);
		// m_rbtSveMode.setVisibility(INVISIBLE);
	}

	@Override
	public void OnInitRes() {
		m_MusicSeek.OnInitRes();
		m_SoundSeek.OnInitRes();
		try {
			m_ImageBack.OnReLoadImage();
			// m_ImageWordShake.OnReLoadImage();
			// m_ImageWordSveMode.OnReLoadImage();
			m_ImageWordMusic.OnReLoadImage();
			m_ImageWordSound.OnReLoadImage();
			// m_ImageWordLight.OnReLoadImage();
			// m_ImageWordAbout.OnReLoadImage();
			// m_ImageTop.OnReLoadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_btMusicMax.setVisibility(VISIBLE);
		m_btSoundMax.setVisibility(VISIBLE);
		// m_btLightMax.setVisibility(VISIBLE);
		m_btMusicMin.setVisibility(VISIBLE);
		m_btSoundMin.setVisibility(VISIBLE);
		// m_btLightMin.setVisibility(VISIBLE);
		// m_rbtAbout.setVisibility(VISIBLE);
		// m_rbtShake.setVisibility(VISIBLE);
		// m_rbtSveMode.setVisibility(VISIBLE);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		switch (CActivity.nDeviceType) {
			case GDF.DEVICETYPE_WVGA :
				onLayoutH(changed, l, t, r, b);
				break;
			case GDF.DEVICETYPE_HVGA :
				onLayoutM(changed, l, t, r, b);
				break;
			case GDF.DEVICETYPE_QVGA :
				onLayoutL(changed, l, t, r, b);
				break;

		}

	}

	private void onLayoutL(boolean changed, int l, int t, int r, int b) {
		int x = 0, y = 0, spacex = 5, spacey = 0;

		int w = m_ImageBack.GetW();
		int h = m_ImageBack.GetH();
		int width = spacex * 3 + m_btMusicMin.getW() + m_MusicSeek.GetW() + m_btMusicMax.getW() + m_ImageWordMusic.GetW();
		x = (w - width) / 2 + m_ImageWordMusic.GetW() + spacex;
		y = 30;
		spacey = (m_btMusicMin.getH() - m_MusicSeek.GetH()) / 2;
		m_btMusicMin.layout(x, y - spacey, 0, 0);
		m_MusicSeek.layout(x + spacex + m_btMusicMin.getW(), y, 0, 0);
		m_btMusicMax.layout(x + spacex * 2 + m_MusicSeek.GetW() + m_btMusicMin.getW(), y - spacey, 0, 0);

		y = 75;
		spacey = (m_btSoundMin.getH() - m_SoundSeek.GetH()) / 2;
		m_btSoundMin.layout(x, y - spacey, 0, 0);
		m_SoundSeek.layout(x + spacex + m_btSoundMin.getW(), y, 0, 0);
		m_btSoundMax.layout(x + spacex * 2 + m_SoundSeek.GetW() + m_btSoundMin.getW(), y - spacey, 0, 0);

		// x = w / 2 - 10 - m_rbtShake.getW();
		// y = h - 75;
		// m_rbtShake.layout(x, y, 0, 0);

		// x = w / 2 + 20 + m_ImageWordSveMode.GetW();
		// y = h - 80;
		// m_rbtSveMode.layout(x, y, 0, 0);
		//
		// x = w / 2 - 10 - m_rbtAbout.getW();
		// y = h - 45;
		// m_rbtAbout.layout(x, y, 0, 0);

	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		int x = 0, y = 0, spacex = 5, spacey = 0;

		int w = m_ImageBack.GetW();
		int h = m_ImageBack.GetH();
		int width = spacex * 3 + m_btMusicMin.getW() + m_MusicSeek.GetW() + m_btMusicMax.getW() + m_ImageWordMusic.GetW();
		x = (w - width) / 2 + m_ImageWordMusic.GetW() + spacex;
		y = 30;
		spacey = (m_btMusicMin.getH() - m_MusicSeek.GetH()) / 2;
		m_btMusicMin.layout(x, y - spacey, 0, 0);
		m_MusicSeek.layout(x + spacex + m_btMusicMin.getW(), y, 0, 0);
		m_btMusicMax.layout(x + spacex * 2 + m_MusicSeek.GetW() + m_btMusicMin.getW(), y - spacey, 0, 0);

		y = 75;
		spacey = (m_btSoundMin.getH() - m_SoundSeek.GetH()) / 2;
		m_btSoundMin.layout(x, y - spacey, 0, 0);
		m_SoundSeek.layout(x + spacex + m_btSoundMin.getW(), y, 0, 0);
		m_btSoundMax.layout(x + spacex * 2 + m_SoundSeek.GetW() + m_btSoundMin.getW(), y - spacey, 0, 0);

		// x = w / 2 - 10 - m_rbtShake.getW();
		// y = h - 75;
		// m_rbtShake.layout(x, y, 0, 0);
		//
		// x = w / 2 + 20 + m_ImageWordSveMode.GetW();
		// y = h - 80;
		// m_rbtSveMode.layout(x, y, 0, 0);
		//
		// x = w / 2 - 10 - m_rbtAbout.getW();
		// y = h - 45;
		// m_rbtAbout.layout(x, y, 0, 0);

	}

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		int x = 0, y = 0, spacex = 5, spacey = 0;

		int w = m_ImageBack.GetW();
		int h = m_ImageBack.GetH();
		int width = spacex * 3 + m_btMusicMin.getW() + m_MusicSeek.GetW() + m_btMusicMax.getW() + m_ImageWordMusic.GetW();
		x = (w - width) / 2 + m_ImageWordMusic.GetW() + spacex;
		y = 45;
		spacey = (m_btMusicMin.getH() - m_MusicSeek.GetH()) / 2;
		m_btMusicMin.layout(x, y - spacey, 0, 0);
		m_MusicSeek.layout(x + spacex + m_btMusicMin.getW(), y, 0, 0);
		m_btMusicMax.layout(x + spacex * 2 + m_MusicSeek.GetW() + m_btMusicMin.getW(), y - spacey, 0, 0);

		y = 110;
		spacey = (m_btSoundMin.getH() - m_SoundSeek.GetH()) / 2;
		m_btSoundMin.layout(x, y - spacey, 0, 0);
		m_SoundSeek.layout(x + spacex + m_btSoundMin.getW(), y, 0, 0);
		m_btSoundMax.layout(x + spacex * 2 + m_SoundSeek.GetW() + m_btSoundMin.getW(), y - spacey, 0, 0);

		// x = w / 2 - 10 - m_rbtShake.getW();
		// y = h - 135;
		// m_rbtShake.layout(x, y, 0, 0);
		//
		// x = w / 2 + 20 + m_ImageWordSveMode.GetW();
		// y = h - 135;
		// m_rbtSveMode.layout(x, y, 0, 0);
		//
		// x = w / 2 - 10 - m_rbtAbout.getW();
		// y = h - 75;
		// m_rbtAbout.layout(x, y, 0, 0);
	}

	@Override
	public void setVisibility(int visibility) {
		CGlobalUnitsEx Global = ClientPlazz.GetGlobalUnits();
		if (visibility == VISIBLE) {
			OnInitRes();
			if (Global != null) {
				ConfigBySystem(Global);
				Global.SetCallBack(this);
			}
		} else {
			if (Global != null) {
				ConfigBySystem(Global);
				Global.SetCallBack(null);
			}

		}
		super.setVisibility(visibility);

		if (visibility == INVISIBLE) {
			OnDestoryRes();
		}
	}

	@Override
	protected void Render(Canvas canvas) {
		int x = 0, y = 0;
		m_ImageBack.DrawImage(canvas, 0, 0);

		x = m_btMusicMin.getLeft() - 5 - m_ImageWordMusic.GetW();
		y = m_btMusicMin.getTop();
		m_ImageWordMusic.DrawImage(canvas, x, y);

		x = m_btSoundMin.getLeft() - 5 - m_ImageWordSound.GetW();
		y = m_btSoundMin.getTop();
		m_ImageWordSound.DrawImage(canvas, x, y);

		// x = m_rbtAbout.getLeft() - m_ImageWordAbout.GetW() - 5;
		// y = m_rbtAbout.getTop() + m_rbtAbout.getH() / 2 - m_ImageWordAbout.GetH() / 2;
		// m_ImageWordAbout.DrawImage(canvas, x, y);
		//
		// x = m_rbtShake.getLeft() - m_ImageWordShake.GetW() - 5;
		// y = m_rbtShake.getTop() + m_rbtShake.getH() / 2 - m_ImageWordShake.GetH() / 2;
		// m_ImageWordShake.DrawImage(canvas, x, y);
		//
		// x = m_rbtSveMode.getLeft() - m_ImageWordSveMode.GetW() - 5;
		// y = m_rbtSveMode.getTop() + m_rbtSveMode.getH() / 2 - m_ImageWordSveMode.GetH() / 2;
		// m_ImageWordSveMode.DrawImage(canvas, x, y);

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
		// case CGlobalUnitsEx.IDM_SVE: {
		// ConfigBySystem(Global);
		// if (succed)
		// m_LightSeek.SetProgress(0, false);
		// break;
		// }
			case CGlobalUnitsEx.IDM_SHAKE : {

				break;
			}
			case CGlobalUnitsEx.IDM_LIGHT : {

				break;
			}
			// case CGlobalUnitsEx.IDM_LIGHTUP: {
			// int light = Global.GetSystemLight();
			// m_SoundSeek.SetProgress((Math.max(0, light - PDF.MIN_LIGHT))
			// * m_LightSeek.GetMax()
			// / (PDF.MAX_LIGHT - PDF.MIN_LIGHT), false);
			// break;
			// }
			// case CGlobalUnitsEx.IDM_LIGHTDOWN: {
			// int light = Global.GetSystemLight();
			// m_SoundSeek.SetProgress((Math.max(0, light - PDF.MIN_LIGHT))
			// * m_LightSeek.GetMax()
			// / (PDF.MAX_LIGHT - PDF.MIN_LIGHT), false);
			// break;
			// }
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

	@Override
	public boolean onSingleClick(View view, Object obj) {
		if (view.getId() == m_btMusicMax.getId()) {
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
		}
		// else if (view.getId() == m_btLightMax.getId()) {
		// m_LightSeek.SetProgress(m_LightSeek.GetMax(), true);
		// return true;
		// } else if (view.getId() == m_btLightMin.getId()) {
		// m_LightSeek.SetProgress(0, true);
		// return true;
		// }
		// else if (view.getId() == m_rbtShake.getId()) {
		// CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
		// if (global != null) {
		// m_rbtShake.SetTickOff(!m_rbtShake.GetTickOff());
		// global.OnVibrator(m_rbtShake.GetTickOff());
		// }
		// return true;
		// } else if (view.getId() == m_rbtSveMode.getId()) {
		// CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
		// if (global != null) {
		// m_rbtSveMode.SetTickOff(!m_rbtSveMode.GetTickOff());
		// global.OnSve(m_rbtSveMode.GetTickOff());
		// }
		// return true;
		// }
		// else if (view.getId() == m_rbtAbout.getId()) {
		// CGlobalUnitsEx global = ClientPlazz.GetGlobalUnits();
		// if (global != null) {
		// m_rbtAbout.SetTickOff(!m_rbtAbout.GetTickOff());
		// global.OnFreshManMode(m_rbtAbout.GetTickOff());
		// }
		// return true;
		// }
		return false;
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
			}
			// else if (view.getId() == m_LightSeek.getId()) {
			// int light = PDF.MIN_LIGHT + (PDF.MAX_LIGHT - PDF.MIN_LIGHT)
			// * m_LightSeek.GetProgress() / m_LightSeek.GetMax();
			// global.OnSetLight(light);
			// }
		}

	}

	private void ConfigBySystem(CGlobalUnitsEx Global) {
		if (Global != null) {
			int music = Global.GetSystemMusic();
			int sound = Global.GetSystemSound();
			// int light = Global.GetSystemLight();
			m_MusicSeek.SetProgress(music * m_MusicSeek.GetMax() / Global.GetMaxMusic(), false);
			m_SoundSeek.SetProgress(sound * m_SoundSeek.GetMax() / Global.GetMaxSound(), false);
			// m_LightSeek.SetProgress((Math.max(0, light - PDF.MIN_LIGHT))
			// * m_LightSeek.GetMax() / (PDF.MAX_LIGHT - PDF.MIN_LIGHT),
			// false);

			// m_rbtShake.SetTickOff(Global.IsShakeMode());
			// m_rbtSveMode.SetTickOff(Global.IsSVEMode());
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return true;
	}
}
