package com.qp.land_h.game.Game_Windows;

import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class CRecordTextView extends TextView {

	public CRecordTextView(Context context) {
		super(context);
		setBackgroundDrawable(null);
		setTextSize(13);
		setTextColor(Color.BLACK);
		setMovementMethod(ScrollingMovementMethod.getInstance());
	}

}
