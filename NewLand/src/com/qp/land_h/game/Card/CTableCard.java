package com.qp.land_h.game.Card;

import Lib_DF.DF;
import Lib_Interface.IRangeObtain;
import Lib_System.CActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.qp.land_h.game.Game_Cmd.GDF;

public class CTableCard extends View implements IRangeObtain {

	final static int	MAX_LINE	= 8;
	final static int	MAX_ROW		= 1;
	protected int		nLine;
	protected int		nRow;

	protected int		nSpaceX		= 18;
	protected int		nSpaceY		= 35;

	protected int		nMode		= 1;

	tagCardItem			CardItem[]	= new tagCardItem[GDF.MAX_CARDCOUNT];
	protected int		nCardCount	= 0;

	public CTableCard(Context context, int mode, int line, int row) {
		super(context);
		setBackgroundDrawable(null);
		nMode = mode;
		nLine = line > 0 ? line : MAX_LINE;
		nRow = row > 0 ? row : MAX_ROW;
		for (int i = 0; i < CardItem.length; i++) {
			CardItem[i] = new tagCardItem();
		}

		if (CActivity.nDeviceType == DF.DEVICETYPE_QVGA) {
			nSpaceX = 10;
			nSpaceY = 25;
		}

	}

	@Override
	public void onDraw(Canvas canvas) {
		if (nCardCount > 0) {
			for (int i = 0; i < CardItem.length; i++) {
				if (CardItem[i].data == 0)
					break;
				// CCard_Middle.DrawCard(canvas, CardItem[i].pt.x,
				// CardItem[i].pt.y, CardItem[i].data);
				CardModule.DrawCard(canvas, CardItem[i].pt.x, CardItem[i].pt.y, CardItem[i].data, false, 1);
			}
		}
	}

	public void SetCardData(int data[], int count) {
		int removedata[] = null;
		int removecount = nCardCount;
		if (nCardCount > 0) {
			removedata = new int[GDF.MAX_CARDCOUNT];
			for (int i = 0; i < nCardCount; i++) {
				removedata[i] = CardItem[i].data;
			}
		}
		nCardCount = count;
		if (data != null) {
			for (int i = 0; i < CardItem.length; i++) {
				CardItem[i].data = i < count ? data[i] : 0;
			}
		}
		int tmpcount = count;
		int row = 0;
		int x = 0, y = 0;
		while (tmpcount > 0) {
			if (tmpcount >= nLine) {
				x = 0;
			} else {
				x = (getWidth() - (tmpcount - 1) * nSpaceX - CardModule.getWidth(1)) / 2;
			}
			y = row * nSpaceY;
			for (int i = 0; i < tmpcount && i < nLine; i++) {
				CardItem[row * nLine + i].pt.set(x + (i * nSpaceX), y);
			}
			row++;
			tmpcount -= 10;
		}
		if (removedata != null)
			ReleaseCard(removedata, removecount);

		postInvalidate();
	}

	private void ReleaseCard(int data[], int count) {
//		if (count > 0) {
//			for (int i = 0; i < data.length && i < count; i++) {
//				CCard_Middle.OnReleaseCard(data[i]);
//			}
//		}
	}

	@Override
	public int GetH() {
		return CardModule.getHeight(1) + nSpaceY * (nRow - 1);

	}

	@Override
	public int GetW() {
		return CardModule.getWidth(1) + nSpaceX * (nLine - 1);
	}
}
