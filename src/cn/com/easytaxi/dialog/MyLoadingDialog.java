package cn.com.easytaxi.dialog;

import android.content.Context;
import android.widget.TextView;

import com.aiton.yc.client.R;

public class MyLoadingDialog extends CommonLogdingDialog {
	private TextView tv;

	public MyLoadingDialog(Context context) {
		super(context);
		tv = (TextView) findViewById(R.id.tipTextView);
		setCanceledOnTouchOutside(false);
	}

	public void showWithText(String text) {
		if (tv != null) {
			tv.setText(text);
		}
		showWithAnimation();

	}

}
