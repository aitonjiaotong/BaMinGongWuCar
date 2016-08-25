package cn.com.easytaxi.util;

import android.content.Context;
import cn.com.easytaxi.ETApp;

import com.xc.lib.xutils.BitmapUtils;

public class TaxiImageLoader {

	private static BitmapUtils instance;

	public static BitmapUtils getInstance(Context context) {
		if (instance == null)
			instance = new BitmapUtils(context, ETApp.getmSdcardAppDir() + "/cache");
		return instance;
	}

}
