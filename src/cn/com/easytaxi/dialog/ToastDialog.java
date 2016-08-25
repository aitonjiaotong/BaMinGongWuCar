package cn.com.easytaxi.dialog;

import com.baidu.platform.comapi.map.t;
import com.aiton.yc.client.R;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

public class ToastDialog extends CommonDialog {
	TextView textView;
	long time = 0;
	long nowtime = 0;
	Handler handler = new Handler();

	public ToastDialog(Context context) {
		super(context, R.layout.toast_layout, R.style.MyDialog);
	}

	public void showDialog(String text) {
		textView = (TextView) findViewById(R.id.toast_text);
		textView.setText(text);
		this.show();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				dismiss();
			}
		}, 5000);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
