package com.qp.land_h.game.Game_Windows;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Lib_DF.DF;
import Lib_Graphics.CImageEx;
import Lib_Interface.ButtonInterface.ISingleClickListener;
import Lib_Interface.HandleInterface.ISubMessage;
import Lib_System.CActivity;
import Lib_System.GlobalUnits.CGlobalUnitsEx;
import Lib_System.View.CViewEngine;
import Lib_System.View.ButtonView.CImageButton;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qp.new_land.R;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Control.EmptyEditText;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;

public class CGameChat extends CViewEngine implements OnItemClickListener, ISingleClickListener, ISubMessage {

	private final static int	SEND_USERCHAT	= 110;

	EmptyEditText				m_EdChatMessage;

	CImageButton				m_btSend;
	CImageButton				m_btClean;
	CImageButton				m_btNormal;
	CImageButton				m_btFace;
	CImageButton				m_btRecord;

	CImageEx					m_ImageBack;
	CImageEx					m_ImageTextBG;
	CExpressionView				m_Eexpression;

	ListView					m_NromalListView;
	ListView					m_RecordListView;
	List<String>				m_NromalData	= new ArrayList<String>();
	List<String>				m_RecordData	= new ArrayList<String>();
	ArrayAdapter<String>		m_NromalItemAdapter;

	CRecordTextView				m_RecordText;
	String						m_szFastMsg[]	= new String[GDF.FASTINFOCOUNT];

	public CGameChat(Context context) {
		super(context);

		/** 设置绘制 **/
		setWillNotDraw(false);

		/** 按钮控件 **/
		try {
			m_ImageBack = new CImageEx(ClientPlazz.RES_PATH + "chat/bg_chat.png");
			m_ImageTextBG = new CImageEx(ClientPlazz.RES_PATH + "chat/txt_bg.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		m_btSend = new CImageButton(context, ClientPlazz.RES_PATH + "chat/bt_send.png");
		m_btNormal = new CImageButton(context, ClientPlazz.RES_PATH + "chat/bt_normal.png");
		m_btFace = new CImageButton(context, ClientPlazz.RES_PATH + "chat/bt_expression.png");
		m_btRecord = new CImageButton(context, ClientPlazz.RES_PATH + "chat/bt_record.png");
		m_btClean = new CImageButton(context, ClientPlazz.RES_PATH + "chat/bt_clean.png");

		/** 聊天输入框 **/
		m_EdChatMessage = new EmptyEditText(context, true, InputType.TYPE_CLASS_TEXT, false);
		m_EdChatMessage.setPadding(5, 5, 5, 1);
		m_EdChatMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(GDF.LEN_USER_CHAT / 2)});
		m_EdChatMessage.setHint("请输入聊天信息");

		/** 常用聊天用语列表 **/
		String variable[] = new String[GDF.FASTINFOCOUNT];
		CGlobalUnitsEx globalunits = ClientPlazz.GetGlobalUnits();

		if (globalunits != null) {
			globalunits.LoadGameSound(R.raw.msg_tip, GDF.SOUND_MSG_TIP);
		}

		for (int i = 0; i < GDF.FASTINFOCOUNT; i++) {
			variable[i] = "ChatItem" + i;
			if (globalunits != null) {
				globalunits.LoadGameSound(R.raw.msg_m_0 + i, GDF.SOUND_MSG_M_0 + i * 2);
				globalunits.LoadGameSound(R.raw.msg_w_0 + i, GDF.SOUND_MSG_M_0 + i * 2 + 1);
			}
		}

		m_szFastMsg[0] = "快点吧，等到花儿都谢了。";
		m_szFastMsg[1] = "大家好，很高兴见到各位。";
		m_szFastMsg[2] = "又断线了，网络怎么这么差啊。";
		m_szFastMsg[3] = "和你合作真是太愉快了。";
		m_szFastMsg[4] = "我们交个朋友吧，能告诉我你的联系方式么？";
		m_szFastMsg[5] = "你是MM，还是GG？";
		m_szFastMsg[6] = "不要吵了，不要吵了，专心玩游戏吧。";
		m_szFastMsg[7] = "不要走，决战到天亮。";
		m_szFastMsg[8] = "各位真是不好意思，我要走了。";
		m_szFastMsg[9] = "再见了，我会想念大家的。";

		// try {
		// CIniHelper.getProfileStringEx(
		// ClientPlazz.GetInstance().getAssets(), GDF.PHRASEINFOPATH,
		// GDF.PHRASEINFOSECTION, variable, GDF.NULL, m_szFastMsg);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		for (int i = 0; i < GDF.FASTINFOCOUNT; i++) {
			if (m_szFastMsg != null && !m_szFastMsg.equals(GDF.NULL))
				m_NromalData.add(m_szFastMsg[i]);
		}

		/** 聊天记录列表 **/
		m_RecordListView = new ListView(context);
		m_RecordListView.setSelector(R.drawable.timer_list_selector);
		m_RecordListView.setCacheColorHint(Color.TRANSPARENT);

		m_RecordText = new CRecordTextView(context);

		/** 设置监听 **/
		m_btSend.setSingleClickListener(this);
		m_btClean.setSingleClickListener(this);
		m_btNormal.setSingleClickListener(this);
		m_btFace.setSingleClickListener(this);
		m_btRecord.setSingleClickListener(this);

		/** 绑定显示 **/
		addView(m_EdChatMessage);

		addView(m_btSend);
		addView(m_btClean);
		addView(m_btNormal);
		addView(m_btFace);
		addView(m_btRecord);

		addView(m_RecordListView);
		m_RecordListView.setVisibility(INVISIBLE);

		m_Eexpression = new CExpressionView(context);
		addView(m_Eexpression);
		m_Eexpression.setVisibility(INVISIBLE);

		addView(m_RecordText);
		m_RecordText.setVisibility(INVISIBLE);
	}

	/**
	 * 设置区域
	 */
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

	private void onLayoutH(boolean changed, int l, int t, int r, int b) {
		m_btNormal.layout(15, 95, r, b);
		m_btFace.layout(15 + m_btNormal.getW(), 95, r, b);
		m_btRecord.layout(15 + m_btNormal.getW() + m_btFace.getW(), 95, r, b);

		int y = 95 + m_btNormal.getH();

		m_RecordListView.layout(20, y, m_ImageBack.GetW() - 20, y + 240);
		m_Eexpression.layout(20, y, m_ImageBack.GetW() - 20, y + 240);
		m_RecordText.layout(20, y, m_ImageBack.GetW() - 20, y + 240);

		m_EdChatMessage.layout(15, 20, 25 + m_ImageTextBG.GetW(), 20 + m_ImageTextBG.GetH());

		m_btClean.layout(10 + m_ImageTextBG.GetW() - m_btClean.getW(), 20 + (m_ImageTextBG.GetH() - m_btClean.getH()) / 2, 0, 0);

		m_btSend.layout(m_ImageBack.GetW() - m_btSend.getW() - 15, 20, 0, 0);

	}

	private void onLayoutM(boolean changed, int l, int t, int r, int b) {
		m_btNormal.layout(10, 52, r, b);
		m_btFace.layout(10 + m_btNormal.getW(), 52, r, b);
		m_btRecord.layout(10 + m_btNormal.getW() + m_btFace.getW(), 52, r, b);

		int y = 55 + m_btNormal.getH();
		m_RecordListView.layout(10, y, m_ImageBack.GetW() - 10, y + 120);
		m_Eexpression.layout(10, y, m_ImageBack.GetW() - 10, y + 120);
		m_RecordText.layout(10, y, m_ImageBack.GetW() - 10, y + 120);

		m_EdChatMessage.layout(15, 20, 25 + m_ImageTextBG.GetW(), 20 + m_ImageTextBG.GetH());

		m_btClean.layout(5 + m_ImageTextBG.GetW() - m_btClean.getW(), 20 + (m_ImageTextBG.GetH() - m_btClean.getH()) / 2, 0, 0);

		m_btSend.layout(m_ImageBack.GetW() - m_btSend.getW() - 10, 20, 0, 0);

	}

	private void onLayoutL(boolean changed, int l, int t, int r, int b) {
		m_btNormal.layout(10, 40, r, b);
		m_btFace.layout(10 + m_btNormal.getW(), 40, r, b);
		m_btRecord.layout(10 + m_btNormal.getW() + m_btFace.getW(), 40, r, b);

		int y = 42 + m_btNormal.getH();
		m_RecordListView.layout(15, y, m_ImageBack.GetW() - 15, y + 100);
		m_Eexpression.layout(15, y, m_ImageBack.GetW() - 15, y + 100);
		m_RecordText.layout(15, y, m_ImageBack.GetW() - 15, y + 100);

		m_EdChatMessage.layout(15, 10, 15 + m_ImageTextBG.GetW(), 10 + m_ImageTextBG.GetH());

		m_btClean.layout(170, 13, 0, 0);

		m_btSend.layout(m_ImageBack.GetW() - m_btSend.getW() - 10, 12, 0, 0);
	}

	@Override
	public void ActivateView() {

	}

	@Override
	public void OnDestoryRes() {
		SaveGameChat();
		m_RecordData.clear();
		m_RecordText.setText(GDF.NULL);
		m_btSend.setVisibility(INVISIBLE);
		m_btClean.setVisibility(INVISIBLE);
		m_btNormal.setVisibility(INVISIBLE);
		m_btFace.setVisibility(INVISIBLE);
		m_btRecord.setVisibility(INVISIBLE);
		m_ImageBack.OnReleaseImage();

		if (m_EdChatMessage.getBackground() != null)
			ClientPlazz.DestoryBackGround(m_EdChatMessage);
		m_ImageTextBG.OnReleaseImage();
		m_Eexpression.OnDestoryRes();
		removeView(m_NromalListView);
	}

	@Override
	public void OnInitRes() {
		try {
			m_ImageTextBG.OnReLoadImage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BitmapDrawable drawable = new BitmapDrawable(m_ImageTextBG.GetBitMap());
		m_EdChatMessage.setBackgroundDrawable(drawable);

		m_EdChatMessage.setText(GDF.NULL);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yy_MM_dd HH_mm_ss");
		m_RecordData.add(sf.format(date) + "-" + "聊天记录");
		m_RecordText.setText("聊天记录" + "\n");

		m_NromalListView = new ListView(getContext());
		m_NromalListView.setSelector(R.drawable.timer_list_selector);
		m_NromalListView.setCacheColorHint(Color.TRANSPARENT);
		m_NromalItemAdapter = new ArrayAdapter<String>(getContext(), R.layout.normallistitem, m_NromalData);
		m_NromalListView.setAdapter(m_NromalItemAdapter);
		m_NromalListView.setOnItemClickListener(this);
		addView(m_NromalListView);

		switch (CActivity.nDeviceType) {
			case DF.DEVICETYPE_WVGA : {
				int y = 95 + m_btNormal.getH();
				m_NromalListView.layout(20, y, m_ImageBack.GetW() - 20, y + 240);
				break;
			}
			case DF.DEVICETYPE_HVGA : {
				int y = 55 + m_btNormal.getH();
				m_NromalListView.layout(10, y, m_ImageBack.GetW() - 10, y + 120);
				break;
			}
			case DF.DEVICETYPE_QVGA : {
				int y = 42 + m_btNormal.getH();
				m_NromalListView.layout(15, y, m_ImageBack.GetW() - 15, y + 100);
				break;
			}
		}

		m_Eexpression.setVisibility(INVISIBLE);
		m_RecordListView.setVisibility(INVISIBLE);
		m_NromalListView.setVisibility(VISIBLE);
	}
	@Override
	public void setVisibility(int visibility) {
		if (visibility == VISIBLE) {
			m_btSend.setVisibility(VISIBLE);
			m_btClean.setVisibility(VISIBLE);
			m_btNormal.setVisibility(VISIBLE);
			m_btFace.setVisibility(VISIBLE);
			m_btRecord.setVisibility(VISIBLE);
			try {
				m_ImageBack.OnReLoadImage();
				// m_ImageTextBG.OnReLoadImage();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// BitmapDrawable drawable = new
			// BitmapDrawable(m_ImageTextBG.GetBitMap());
			// m_EdChatMessage.setBackgroundDrawable(drawable);
		}
		super.setVisibility(visibility);
		if (visibility == INVISIBLE) {
			m_btSend.setVisibility(INVISIBLE);
			m_btClean.setVisibility(INVISIBLE);
			m_btNormal.setVisibility(INVISIBLE);
			m_btFace.setVisibility(INVISIBLE);
			m_btRecord.setVisibility(INVISIBLE);
			m_ImageBack.OnReleaseImage();
		}

	}

	@Override
	protected void Render(Canvas canvas) {
		m_ImageBack.DrawImage(canvas, 0, 0);

	}

	public int GetW() {
		if (m_ImageBack != null)
			return m_ImageBack.GetW();
		return super.getWidth();
	}

	public int GetH() {
		if (m_ImageBack != null)
			return m_ImageBack.GetH();
		return super.getHeight();
	}

	/**
	 * 常用聊天信息列表监听
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int postion, long arg3) {
		if (postion < m_NromalData.size()) {
			setVisibility(INVISIBLE);
			view.clearFocus();
			SendUserChat(m_NromalData.get(postion));
		}
	}

	@Override
	public boolean onSingleClick(View view, Object obj) {
		int id = view.getId();
		if (id == m_btClean.getId()) {
			m_EdChatMessage.setText(GDF.NULL);
			return true;
		} else if (id == m_btNormal.getId()) {
			m_btClean.setClickable(true);
			m_btSend.setClickable(true);
			m_RecordListView.setVisibility(INVISIBLE);
			m_Eexpression.setVisibility(INVISIBLE);
			m_RecordText.setVisibility(INVISIBLE);
			m_NromalListView.setVisibility(VISIBLE);
			m_EdChatMessage.setVisibility(VISIBLE);
			m_btClean.setVisibility(VISIBLE);

			return true;
		} else if (id == m_btFace.getId()) {
			m_btClean.setClickable(false);
			m_btSend.setClickable(false);
			m_RecordListView.setVisibility(INVISIBLE);
			m_NromalListView.setVisibility(INVISIBLE);
			m_EdChatMessage.setVisibility(INVISIBLE);
			m_btClean.setVisibility(INVISIBLE);
			m_RecordText.setVisibility(INVISIBLE);
			m_Eexpression.setVisibility(VISIBLE);

			return true;
		} else if (id == m_btRecord.getId()) {
			m_btClean.setClickable(false);
			m_btSend.setClickable(false);
			m_Eexpression.setVisibility(INVISIBLE);
			m_NromalListView.setVisibility(INVISIBLE);
			m_EdChatMessage.setVisibility(INVISIBLE);
			m_btClean.setVisibility(INVISIBLE);
			m_RecordText.setVisibility(VISIBLE);
			return true;
		} else if (id == m_btSend.getId()) {
			setVisibility(INVISIBLE);
			GDF.SendSubMessage(SEND_USERCHAT, 0, this.GetTag(), null);
			return true;
		}

		return false;
	}

	/**
	 * 记录聊天
	 * 
	 * @param username
	 * @param info
	 * @return
	 */
	public boolean onRecordGameMessage(String username, String info, int gender) {
		// 文本记录
		m_RecordData.add(username + ":" + info);

		// 记录显示
		String user = username;
		SpannableString spname = new SpannableString(user + ":\n");
		spname.setSpan(new ForegroundColorSpan(Color.RED), 0, username.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		m_RecordText.append(spname);
		String userinfo = info + "\n";
		SpannableString spinfo = new SpannableString(userinfo);

		spinfo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, info.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		m_RecordText.append(spinfo);
		// 音效播放
		CGlobalUnitsEx globalunits = ClientPlazz.GetGlobalUnits();
		if (info != null && !info.equals(GDF.NULL) && globalunits != null) {
			for (int i = 0; i < m_szFastMsg.length; i++) {
				if (m_szFastMsg[i] != null && !m_szFastMsg[i].equals(GDF.NULL)) {
					if (info.equals(m_szFastMsg[i])) {
						globalunits.PlayGameSound(GDF.SOUND_MSG_M_0 + i * 2 + (gender == GDF.GENDER_FEMALE ? 1 : 0), 0);
						return true;
					}
				}
			}
			globalunits.PlayGameSound(GDF.SOUND_MSG_TIP, 0);
		}
		return true;

	}

	/**
	 * 表情记录
	 * 
	 * @param username
	 * @param index
	 * @return
	 */
	public boolean onRecordGameMessage(String username, int index) {
		String szDescribe = m_Eexpression.GetExpressionDescribe(index);
		if (szDescribe != null && !szDescribe.equals(GDF.NULL)) {
			// 文本记录
			m_RecordData.add(username + ":" + szDescribe);

			// 记录显示
			SpannableString spname = new SpannableString(username + ":\n");
			spname.setSpan(new ForegroundColorSpan(Color.RED), 0, username.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			m_RecordText.append(spname);

			SpannableString spannable = new SpannableString(szDescribe + "\n");
			ImageSpan span = new ImageSpan(ClientPlazz.getExpressionRes().GetImage(index).GetBitMap(), ImageSpan.ALIGN_BASELINE);
			spannable.setSpan(span, 0, szDescribe.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			m_RecordText.append(spannable);
			// 音效播放
			CGlobalUnitsEx globalunits = ClientPlazz.GetGlobalUnits();
			if (globalunits != null)
				globalunits.PlayGameSound(GDF.SOUND_MSG_TIP, 0);
			return true;
		}
		return false;
	}

	/**
	 * 发送聊天
	 */
	protected void SendUserChat() {
		String szchat = m_EdChatMessage.getText().toString();
		if (szchat != null && !szchat.equals(GDF.NULL)) {
			IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
			if (kernel != null)
				kernel.SendUserChatMessage(0, szchat, Color.BLACK);
		}
	}

	protected void SendUserChat(String msg) {
		if (msg != null && !msg.equals(GDF.NULL)) {
			IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
			if (kernel != null)
				kernel.SendUserChatMessage(0, msg, Color.BLACK);
		}
	}

	@Override
	public void SubMessagedispatch(int main, int sub, Object obj) {
		switch (main) {
			case SEND_USERCHAT :
				SendUserChat();
				break;
			default :
				break;
		}

	}

	public void SaveGameChat() {
		if (m_RecordData.size() < 2)
			return;
		// 路径
		String szPath = ClientPlazz.GetInstance().getResources().getString(R.string.app_name) + "/" + "chat/";
		String filename = GDF.NULL;
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yy_MM_dd");
		SimpleDateFormat sfex = new SimpleDateFormat("HH_mm_ss");
		filename += sf.format(date);
		filename += "_";
		filename += sfex.format(date);
		filename += ".txt";
		// 创建文件
		int type = GDF.CreateFileToSDCard(szPath, filename);
		if (type != -1) {

			try {
				OutputStream Output = new FileOutputStream(GDF.GetSDCardPath() + "/" + szPath + "/" + filename);
				for (int i = 0; i < m_RecordData.size(); i++) {
					String temp = m_RecordData.get(i) + "\r\n";
					Output.write(temp.getBytes("gb2312"));
				}
				Output.flush();
				Output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return true;
	}
}
