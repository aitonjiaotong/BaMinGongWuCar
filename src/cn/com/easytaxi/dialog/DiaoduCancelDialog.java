package cn.com.easytaxi.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import cn.com.easytaxi.util.BitmapRadiusUtil;
import com.aiton.yc.client.R;
import com.xc.lib.utils.Tools;

public class DiaoduCancelDialog extends Dialog {
	private View left, right;
	private DialoAction action;
	private Context context;
	public DiaoduCancelDialog setActionListener(DialoAction action) {
		this.action = action;
		return this;
	}

	public DiaoduCancelDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		initview();
	}

	public static DiaoduCancelDialog newInstance(Context context) {
		return new DiaoduCancelDialog(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
	}

	private void initview() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.dialog_diaodu_cancel, null);
		left = view.findViewById(R.id.left);
		right = view.findViewById(R.id.right);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);
//		makeNotFullScreen(this);
		setContentView(view);
		Tools.getViewSize(left, new Tools.ViewSizeListener() {

			@SuppressLint("NewApi") @Override
			public void onSize(int arg0, int arg1) {
//				left.setBackground(getYellowBg(arg1 / 2));
//				right.setBackground(getGrayBg(arg1 / 2));
				right.setBackground(getYellowBg(arg1 / 2));
				left.setBackground(getGrayBg(arg1 / 2));
			}
		});

		left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (action == null)
					action.onClick(action.LEFT);
			}
		});
		right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (action != null)
					action.onClick(action.RIGHT);
			}
		});

	}

	/**
	 * 创建黄色点击背景
	 * 
	 * @param dialog
	 */
	private Drawable getYellowBg(int width) {
		//return BitmapRadiusUtil.getDrawable(BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#FFB628"), null), BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#FF8C00"), null));
		return BitmapRadiusUtil.getDrawable(BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#ff9704"), null), BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#ff9704"), null));
	}

	/**
	 * 创建灰色点击背景
	 * 
	 * @param dialog
	 */
	private Drawable getGrayBg(int width) {
		return BitmapRadiusUtil.getDrawable(BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#D5D2CD"), null), BitmapRadiusUtil.createBg(1, width, Color.TRANSPARENT, Color.parseColor("#696969"), null));
	}

	public void makeNotFullScreen(Dialog dialog) {
		android.view.Window window = dialog.getWindow();
		WindowManager.LayoutParams wmLayoutParams = window.getAttributes();
		// 获取屏幕宽、高用
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		if (dialog.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			wmLayoutParams.width = (int) (d.widthPixels * 0.96); // 竖屏时，宽度设置为屏幕的0.92
		} else {
			wmLayoutParams.width = (int) (d.widthPixels * 0.6); // 横屏时，宽度设置为屏幕的0.6
		}
		// wmLayoutParams.gravity = Gravity.BOTTOM | Gravity.FILL_HORIZONTAL;
		window.setAttributes(wmLayoutParams);
	}

	public static abstract class DialoAction {
		public int LEFT = 0;
		public int RIGHT = 1;

		public abstract void onClick(int tag);
	}

}
