package cn.com.easytaxi.platform.service;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.WindowManager;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.book.NewBookDetail;
import cn.com.easytaxi.dialog.CommonDialog;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.platform.IndexActivity;
import cn.com.easytaxi.platform.common.common.Const;
import cn.com.easytaxi.platform.common.common.ReceiveMsgBean;
import cn.com.easytaxi.platform.common.common.SendMsgBean;
import cn.com.easytaxi.platform.common.common.UdpMessageHandleListener;
import cn.com.easytaxi.util.XGsonUtil;

import com.aiton.yc.client.R;
import com.google.gson.JsonObject;

public class MainService extends Service {

	public static UdpChannelService udpChannelService;
	public static LocationService locationService;
	//

	private ETApp app;
	private OneBookCore oneBookCore;

	private UdpMessageHandleListener udpMessageHandleListener_002 = new UdpMessageHandleListener() {

		@Override
		public void handle(ReceiveMsgBean msg) {

			if (oneBookCore == null) {
				oneBookCore = OneBookCore.getInstance(MainService.this);
			}
			oneBookCore.dispatchUdp(msg, 0xFF0002);
		}
	};
	private UdpMessageHandleListener udpMessageHandleListener_003 = new UdpMessageHandleListener() {

		@Override
		public void handle(ReceiveMsgBean msg) {

			if (oneBookCore == null) {
				oneBookCore = OneBookCore.getInstance(MainService.this);
			}
			oneBookCore.dispatchUdp(msg, 0xFF0003);
		}
	};

	private UdpMessageHandleListener udpMessageHandleListener_006 = new UdpMessageHandleListener() {

		@Override
		public void handle(ReceiveMsgBean msg) {
			if (oneBookCore == null) {
				oneBookCore = OneBookCore.getInstance(MainService.this);
			}
			oneBookCore.dispatchUdp(msg, 0xFF0006);
		}
	};

	private UdpMessageHandleListener udpMessageHandleListener_001 = new UdpMessageHandleListener() {
		@Override
		public void handle(ReceiveMsgBean msg) {
			if (oneBookCore == null) {
				oneBookCore = OneBookCore.getInstance(MainService.this);
			}
			oneBookCore.dispatchUdp(msg, 0xFF0001);
		}
	};

	// 0xFF0010 新消息
	private UdpMessageHandleListener newMessageHandleListener = new UdpMessageHandleListener() {
		@Override
		public void handle(ReceiveMsgBean msg) {
			AppLog.LogD("====---1111--udp--new message ----  " + Integer.toHexString(msg.getMsgId()));
			if (oneBookCore == null) {
				oneBookCore = OneBookCore.getInstance(MainService.this);
			}
			oneBookCore.dispatchUdp(msg, 0xFF0010);
		}
	};

	// 订单状态变化通知
	private UdpMessageHandleListener bookStateChangedLis = new UdpMessageHandleListener() {
		@Override
		public void handle(ReceiveMsgBean msg) {
			// replyServerUdp(msg);
			if (oneBookCore == null) {
				oneBookCore = OneBookCore.getInstance(MainService.this);
			}
			oneBookCore.dispatchUdp(msg, Const.UDP_BOOK_STATE_CHANGED);
		}
	};

	// 回应给服务器
	private void replyServerUdp(ReceiveMsgBean msg) {
		try {
			byte[] buf = msg.getBody();
			String json = new String(buf, "UTF-8");
			JSONObject jobj = new JSONObject(json);
			JSONObject replyData = new JSONObject();
			replyData.put("id", jobj.optLong("id", -1));
			AppLog.LogD("reply udp：" + replyData.toString());
			MainService.udpChannelService.sendMessage(new SendMsgBean(Const.UDP_NOTIFY_ECHO, replyData.toString().getBytes("utf-8")));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	// 催款
	private UdpMessageHandleListener bookStateCuikuan = new UdpMessageHandleListener() {
		@Override
		public void handle(ReceiveMsgBean msg) {
			replyServerUdp(msg);
			String msgString = new String(msg.getBody());
			try {
				JsonObject object = XGsonUtil.getJsonObject(msgString);
				Long id = object.get("bookId").getAsLong();
				Message msgg = new Message();
				msgg.obj = id;
				handler.sendMessage(msgg);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		app = ETApp.getInstance();
		AppLog.LogD("MainService onCreate === ");
		try {
			// 启动UDP服务
			udpChannelService = new UdpChannelService();
			// 启动位置定位服务
			locationService = new LocationService(this);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	boolean isStarted = false;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			final long bookId = (Long) msg.obj;
			Intent intent = new Intent(NewBookDetail.CUIKUANBROCAST);
			intent.putExtra("bookId", bookId);
			sendBroadcast(intent);
		};
	};

	private void regListener() {
		udpChannelService.regMsgHandleListener(Const.UDP_BOOK_TAXI_SCHEDULE, udpMessageHandleListener_003);
		udpChannelService.regMsgHandleListener(Const.UDP_BOOK_TAXI_REPLY, udpMessageHandleListener_002);
		udpChannelService.regMsgHandleListener(0xFF0006, udpMessageHandleListener_006);
		udpChannelService.regMsgHandleListener(0xFF0001, udpMessageHandleListener_001);
		// 推送新的消息
		udpChannelService.regMsgHandleListener(0xFF0010, newMessageHandleListener);
		udpChannelService.regMsgHandleListener(Const.UDP_BOOK_STATE_CHANGED, bookStateChangedLis);
		udpChannelService.regMsgHandleListener(0xFF0009, bookStateCuikuan);
	}

	private void unRegListener() {
		udpChannelService.unregMsgHandleListener(cn.com.easytaxi.platform.common.common.Const.UDP_BOOK_TAXI_REPLY);
		udpChannelService.unregMsgHandleListener(cn.com.easytaxi.platform.common.common.Const.UDP_BOOK_TAXI_SCHEDULE);
		udpChannelService.unregMsgHandleListener(Const.UDP_BOOK_STATE_CHANGED);
		udpChannelService.unregMsgHandleListener(0xFF0006);
		udpChannelService.unregMsgHandleListener(0xFF0001);
		udpChannelService.unregMsgHandleListener(0xFF0010);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String mobile = null;
		long id = 0;
		mobile = app.login();
		if (StringUtils.isEmpty(mobile)) {
			id = 0;
		} else {
			id = Long.valueOf(mobile);
		}
		AppLog.LogD(app.getCurrentUser().toString());

		try {

			if (isStarted) {
				udpChannelService.stop();
				// udpChannelService.start(id);
				String ids = "1" + String.valueOf(id);// 乘客端和司机端可以同一个账号，所以为了区分通道需要在前面加一个
				udpChannelService.start(Long.parseLong(ids));
				return START_STICKY;
			} else {
				if (udpChannelService == null) {

					udpChannelService = new UdpChannelService();
					locationService = new LocationService(this);
				}

				// switchNotification();
				String ids = "1" + String.valueOf(id);// 乘客端和司机端可以同一个账号，所以为了区分通道需要在前面加一个
				udpChannelService.start(Long.parseLong(ids));
				locationService.start();

				regListener();
				launchBusinessService();

				isStarted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return START_STICKY;
	}

	private void launchBusinessService() {
		Intent intent = new Intent(this, OneBookService.class);
		intent.setAction(EasyTaxiCmd.ONE_TAXI_BOOK_MAIN_START);
		startService(intent);
	}

	public void showNotification(String title, String msgBody) {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.nitify_logo003, "八闽专车", System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent openIntent = new Intent(this, IndexActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, openIntent, 0);
		notification.setLatestEventInfo(this, title, msgBody, contentIntent);
		mNotificationManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		unRegListener();
		udpChannelService.stop();
		locationService.stop();
		udpChannelService = null;
		locationService = null;
		isStarted = false;
		AppLog.LogD("mainservice destroy");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
