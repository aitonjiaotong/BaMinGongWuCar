package cn.com.easytaxi.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 图片处理工具
 * 
 * @author xxb
 * 
 */
public class BitmapRadiusUtil {

	public static GradientDrawable createBg(int strokewidth, int roundRadius, int strokecolor, int fillcolor, int[] colors) {

		GradientDrawable gd = null;
		if (colors != null) {
			gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
			gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		} else {
			gd = new GradientDrawable();//
			gd.setColor(fillcolor);
			gd.setStroke(strokewidth, strokecolor);
		}
		gd.setCornerRadius(roundRadius);
		return gd;
	}

	public static StateListDrawable getDrawable(Drawable normal, Drawable pressed) {
		StateListDrawable sd = new StateListDrawable();

		sd.addState(new int[] { android.R.attr.state_pressed }, pressed);
		sd.addState(new int[] { android.R.attr.state_enabled }, normal);
		sd.addState(new int[] {}, pressed);
		return sd;
	}

	/**
	 * 获取view尺寸
	 * 
	 * @param view
	 * @param listener
	 */
	public static void getViewSize(final View view, final ViewSizeListener listener) {
		if (listener == null)
			return;
		view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				listener.onSize(view.getWidth(), view.getHeight());
				view.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});

	}

	public interface ViewSizeListener {
		void onSize(int width, int height);
	}
}
