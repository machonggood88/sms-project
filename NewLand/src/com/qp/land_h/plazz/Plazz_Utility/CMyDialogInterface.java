package com.qp.land_h.plazz.Plazz_Utility;

import android.content.DialogInterface;

public class CMyDialogInterface implements DialogInterface.OnClickListener {

	public Object obj;

	public CMyDialogInterface(Object obj) {
		this.obj = obj;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

}
