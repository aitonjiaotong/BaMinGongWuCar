package cn.com.easytaxi.util;

import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.platform.RegisterActivity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class YdLoginUtil {

	/**
	 * 判断是否登录，如果没登录则跳到登录页面
	 */
	public static boolean isLogin(Context context) {
		SessionAdapter session = new SessionAdapter(context);
		String phone = session.get("_MOBILE");
		if (TextUtils.isEmpty(phone)) {
			context.startActivity(new Intent(context, RegisterActivity.class));
			return false;
		}
		return true;
	}
}
