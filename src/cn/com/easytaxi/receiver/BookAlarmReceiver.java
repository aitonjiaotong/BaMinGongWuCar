package cn.com.easytaxi.receiver;

import cn.com.easytaxi.common.NetChecker;
import cn.com.easytaxi.service.AlarmClockBookService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BookAlarmReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//判断网络状态
		boolean isNetAvailable = NetChecker.getInstance(context).isAvailableNetwork();
		if(isNetAvailable){
			// 开启订单闹钟提醒服务
			Intent alarmIntent = new Intent(context, AlarmClockBookService.class);
			context.startService(alarmIntent);
		}
	}
	
}
