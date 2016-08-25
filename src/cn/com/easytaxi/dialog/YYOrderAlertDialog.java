package cn.com.easytaxi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.util.BitmapRadiusUtil;

import com.aiton.yc.client.R;
import com.xc.lib.utils.Tools;

public class YYOrderAlertDialog extends Dialog {
	private TextView title;
	private TextView content;
	private TextView sure;

	public YYOrderAlertDialog(Context context, int theme) {
		super(context, theme);
		initview();
	}

	public static YYOrderAlertDialog newInstance(Context context) {
		return new YYOrderAlertDialog(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
	}
	private long bookId = 0;
	public void setBookid(long bookId){
		this.bookId = bookId;
	}
	private void initview() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.yy_alert_dialog_view, null);
		title = (TextView) view.findViewById(R.id.title);
		content = (TextView) view.findViewById(R.id.content);
		sure = (TextView) view.findViewById(R.id.sure);

		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);
		makeNotFullScreen(this);
		setContentView(view);

		Tools.getViewSize(sure, new Tools.ViewSizeListener() {

			@Override
			public void onSize(int arg0, int arg1) {
				sure.setBackground(getYellowBg(arg1 / 2));
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if(callback != null)
					callback.onSure(bookId);
			}
		});
	}
	
	public interface MyCallback{
		void onSure(long bookId);
	}

	private MyCallback callback;

	public void setCallback(MyCallback callback) {
		this.callback = callback;
	}

	public void makeNotFullScreen(Dialog dialog) {
		android.view.Window window = dialog.getWindow();
		WindowManager.LayoutParams wmLayoutParams = window.getAttributes();
		// 获取屏幕宽、高用
		Display d = ((WindowManager) (dialog.getContext().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
		if (dialog.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			wmLayoutParams.width = (int) (d.getWidth() * 0.92); // 竖屏时，宽度设置为屏幕的0.92
		} else {
			wmLayoutParams.width = (int) (d.getWidth() * 0.6); // 横屏时，宽度设置为屏幕的0.6
		}
		// wmLayoutParams.gravity = Gravity.BOTTOM | Gravity.FILL_HORIZONTAL;
		window.setAttributes(wmLayoutParams);
	}

	/**
	 * 创建黄色点击背景
	 * 
	 * @param dialog
	 */
	private Drawable getYellowBg(int width) {
		return BitmapRadiusUtil.getDrawable(BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#FFB628"), null), BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#FF8C00"), null));
	}

}
