package cn.com.easytaxi.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BDLocationServer implements BDLocationListener {

	// public static final String ACTION_MSG_LOCATION =
	// "com.action.get_loction";
	private LocationClient locationClient;
	private Context mContext;

	private LocReceive locreceive;

	private static BDLocationServer instance;

	public static BDLocationServer getInstance(Context context) {
		if (instance == null)
			instance = new BDLocationServer(context);
		return instance;
	}

	private BDLocationServer(Context context) {
		mContext = context.getApplicationContext();
		locationClient = new LocationClient(context);
		// 注册监听函数
		locationClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setProdName(context.getPackageName());
		option.setAddrType("all");
		// option.setIsNeedAddress(true);
		locationClient.setLocOption(option);
	}

	public void start() {
		locationClient.start();
	}

	/**
	 * 设置定位回调
	 * 
	 * @param listener
	 */
	public void setReceiveListenern(LocReceive listener) {
		this.locreceive = listener;
	}

	/**
	 * 请求网络地址
	 */
	public void requestLocation() {
		if (locationClient != null) {
			locationClient.start();
			locationClient.requestLocation();
		}
	}

	public void onReceiveLocation(BDLocation location) {
		if (locreceive != null)
			locreceive.onReceiveLocation(location);
		stop();
	}

	public void onReceivePoi(BDLocation location) {

	}

	public void stop() {
		if (locationClient != null)
			locationClient.stop();
	}

	public interface LocReceive {
		void onReceiveLocation(BDLocation location);
	}
}
