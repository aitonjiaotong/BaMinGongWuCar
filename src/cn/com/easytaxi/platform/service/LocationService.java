package cn.com.easytaxi.platform.service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.com.easytaxi.platform.common.common.Const;
import cn.com.easytaxi.platform.common.common.SendMsgBean;
import cn.com.easytaxi.platform.common.common.SocketUtil;
import cn.com.easytaxi.util.LocalPreference;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

public class LocationService implements BDLocationListener {

	// private static final String tag = "LocationService";
	public static final String LASTLAT = "medi_sj_lastlat";
	public static final String LASTLNG = "meidi_sj_lastlng";
	private MainService mainService;

	// 前一次系统时间
	private long preSysTime = 0;
	/**
	 * 间隔时间
	 */
	private int SPACE_TIME = 10 * 1000;

	LocationClient locationClient;

	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public LocationService(MainService mainService) {
		this.mainService = mainService;
		locationClient = new LocationClient(mainService);
		locationClient.registerLocationListener(this); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(SPACE_TIME);
		option.setAddrType("all");
		option.setIsNeedLocationDescribe(true);
		locationClient.setLocOption(option);
	}

	public void start() {
		locationClient.start();
		locationClient.requestLocation();
	}

	/**
	 * 保存地址
	 * 
	 * @param loc
	 */
	private void saveLastLoc(BDLocation loc) {
		if (loc != null) {
			LocalPreference.getInstance(mainService).setString(LASTLAT, loc.getLatitude() + "");
			LocalPreference.getInstance(mainService).setString(LASTLNG, loc.getLongitude() + "");
		}
	}

	/**
	 * 获取最后一次定位位置
	 * 
	 * @param context
	 * @return
	 */
	public static LatLng getLastLoc(Context context) {
		String lat = LocalPreference.getInstance(context).getString(LASTLAT, "");
		String lng = LocalPreference.getInstance(context).getString(LASTLNG, "");
		LatLng ll = null;
		try {
			ll = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ll;
	}

	public void onReceiveLocation(BDLocation location) {
		saveLastLoc(location);
		try {

			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			// long sysTime = Calendar.getInstance().getTimeInMillis();
			long sysTime = System.currentTimeMillis();

			// 上传经纬度信息
			if (sysTime - preSysTime > 10000) { // 上传经纬度为0主要为了保持UDP通讯
				preSysTime = sysTime;
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				bout.write(SocketUtil.toHH(lng));
				bout.write(SocketUtil.toHH(lat));

				MainService.udpChannelService.sendMessage(new SendMsgBean(Const.UDP_LOCATION_UP, bout.toByteArray()));
			}

			// 广播经纬度
			if (lat <= 0 || lng <= 0)
				return;

			Intent intent = new Intent(SystemService.BROADCAST_LOCATION);

			// intent.putExtra("time", sysTime);
			// intent.putExtra("type", location.getLocType());
			intent.putExtra("latitude", lat);
			intent.putExtra("longitude", lng);
			intent.putExtra("radius", location.getRadius());
			intent.putExtra("derect", location.getDerect());
			intent.putExtra("address", location.getAddrStr());
			intent.putExtra("city", location.getCity());
			// intent.putExtra("speed", location.getSpeed());

			// intent.putExtra("satellite", location.getSatelliteNumber());

			/*
			 * AppLog.LogD(tag, "time : " + sysTime + " , type : " +
			 * location.getLocType()); AppLog.LogD(tag, "lat : " + lat +
			 * " , lng : " + lng + " , radius : " + location.getRadius());
			 * AppLog.LogD(tag, "radius : " + location.getRadius() +
			 * " , speed : "+ location.getSpeed() + ", satellite : " +
			 * location.getSatelliteNumber());
			 */
			mainService.sendBroadcast(intent);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void onReceivePoi(BDLocation location) {

	}

	public void stop() {
		Log.d("yhq", "停止定位");
		if (locationClient != null)
			locationClient.stop();
	}

}
