package cn.com.easytaxi.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import cn.com.easytaxi.book.view.scrollwheel.ArrayWheelAdapter;
import cn.com.easytaxi.book.view.scrollwheel.WheelView;
import cn.com.easytaxi.book.view.scrollwheel.WheelView.OnWheelChangedListener;
import cn.com.easytaxi.common.Callback;

import com.aiton.yc.client.R;

public class WheelSelectDate extends Dialog implements OnWheelChangedListener, android.view.View.OnClickListener {
	WheelView day, hour, min;
	private String[] days = new String[] { "今天", "明天", "后天" };
	private String[] hours;
	private String[] mins;

	public WheelSelectDate(Context context) {
		super(context, R.style.Transparent);
		initViews();
	}

	private void initViews() {

		setContentView(R.layout.time_select);

		findViewById(R.id.sure).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
		day = (WheelView) findViewById(R.id.day_wheel);
		hour = (WheelView) findViewById(R.id.hour_wheel);
		min = (WheelView) findViewById(R.id.min_wheel);

		day.setBackgroundResource(Color.TRANSPARENT);
		hour.setBackgroundResource(Color.TRANSPARENT);
		min.setBackgroundResource(Color.TRANSPARENT);
		initData();
		day.setAdapter(new ArrayWheelAdapter<String>(days));
		day.setVisibleItems(3);
		day.setCyclic(false);
		day.setCurrentItem(0);

		hour.setAdapter(new ArrayWheelAdapter<String>(hours));
		hour.setVisibleItems(3);
		hour.setCyclic(false);
		hour.setCurrentItem(0);

		min.setAdapter(new ArrayWheelAdapter<String>(mins));
		min.setVisibleItems(3);
		min.setCyclic(false);
		min.setCurrentItem(0);
		day.addChangingListener(this);
		hour.addChangingListener(this);
		min.addChangingListener(this);
		getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
		setParams();

	}

	private void setParams() {
		WindowManager.LayoutParams lay = getWindow().getAttributes();
		lay.width = getContext().getResources().getDisplayMetrics().widthPixels;
	}

	private void setCurrentSelect() {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE) / 10;
		if (hour >= 24)
			this.hour.setCurrentItem(0);
		else
			this.hour.setCurrentItem(hour);
		if (min >= 6)
			this.min.setCurrentItem(0);
		else
			this.min.setCurrentItem(min);

	}

	private Callback<Calendar> callback;

	public void show(Callback<Calendar> callback) {
		this.callback = callback;
		show();
	}

	@Override
	public void show() {
		setCurrentSelect();
		super.show();
	}

	private void initData() {
		// 初始化小时
		hours = new String[24];
		for (int i = 0; i < 24; i++) {
			hours[i] = i + "点";
		}
		mins = new String[6];
		for (int i = 0; i < 6; i++) {
			mins[i] = (i == 0 ? "" : String.valueOf(i)) + "0分";
		}
	}

	private String getSelectHour(int value) {
		if (value < 10) {
			return "0" + value;
		}
		return String.valueOf(value);
	}

	private String getSelectMin(int value) {
		return value + "0";
	}

	private int[] selectIndex = new int[3];

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		switch (wheel.getId()) {
		case R.id.day_wheel:
			selectIndex[0] = newValue;
			break;
		case R.id.min_wheel:
			selectIndex[2] = newValue;
			break;
		case R.id.hour_wheel:
			selectIndex[1] = newValue;
			break;
		}
	}

	private static SimpleDateFormat f_show_time = new SimpleDateFormat("yyyy年MM月dd日");
	private static SimpleDateFormat f_date_time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

	private void setTime() {
		Calendar c = Calendar.getInstance();
		if (selectIndex[0] > 0) {
			c.add(Calendar.DATE, selectIndex[0]);
		}
		String selectTime = f_show_time.format(c.getTimeInMillis()) + " " + getSelectHour(selectIndex[1]) + ":" + getSelectMin(selectIndex[2]);
		// SysDeug.logD("选择时间- " + selectTime);
		if (callback != null) {
			try {
				c.setTime(f_date_time.parse(selectTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			callback.handle(c);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:
			dismiss();
			setTime();
			break;
		case R.id.cancel:
			dismiss();
			break;
		}
	}

}
