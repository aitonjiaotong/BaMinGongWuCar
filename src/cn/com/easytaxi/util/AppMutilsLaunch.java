package cn.com.easytaxi.util;

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * @author xxb
 * @version 创建时间：2015年12月5日 上午10:59:42
 */
public class AppMutilsLaunch {
	public static boolean isMutil(Context context, String packName) {
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid, context);
		// 如果app启用了远程的service，此application:onCreate会被调用2次
		// 为了防初始化2次，加此判断会保证SDK被初始化1次
		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
		// name就立即返回
		if (processAppName == null || !processAppName.equalsIgnoreCase(packName)) {
			Log.e("xxb", "enter the service process!");
			// "com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
			// 则此application::onCreate 是被service 调用的，直接返回
			return true;
		}
		return false;
	}

	private static String getAppName(int pID, Context context) {
		String processName = null;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = context.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
			}
		}
		return processName;
	}
}
