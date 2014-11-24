package com.qp.land_h.plazz.Plazz_Control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class EmptyEditText extends EditText {

	public EmptyEditText(final Context context) {
		super(context);
	}

	public EmptyEditText(Context context, boolean singleline, int type,
			boolean bpassword) {
		super(context);
		setSingleLine(singleline);
		setInputType(type);
		if (bpassword)
			SetPassWordMode();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (getBackground() == null) {
			// 定义画笔
			Paint paint = getPaint();
			// 定义笔画粗细样式
			paint.setStyle(Paint.Style.STROKE);
			// 定义笔画颜色
			paint.setColor(Color.GRAY);

			paint.setAntiAlias(true);

			paint.setStrokeWidth(2);

			RectF rect = new RectF();
			rect.set(0, 0, getWidth(), getHeight());
			canvas.drawRoundRect(rect, 5, 5, paint);
		}
	}

	public void SetPassWordMode() {

		addTextChangedListener(new TextWatcher() {

			String tmp = "";
			String digits = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+[]\\;',./{}|:\"<>?";

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				tmp = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {

				String str = s.toString();
				if (str.equals(tmp))
					return;
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < str.length(); i++)
					if (digits.indexOf(str.charAt(i)) >= 0)
						sb.append(str.charAt(i));
				tmp = sb.toString();

				setText(tmp);
				setSelection(tmp.length());
			}
		});
		setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
}
