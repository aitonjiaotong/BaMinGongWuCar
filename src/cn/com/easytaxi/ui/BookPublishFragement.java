package cn.com.easytaxi.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.BookConfig;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.NewNetworkRequest;
import cn.com.easytaxi.NewNetworkRequest.TimeLines;
import cn.com.easytaxi.airport.AirportBookPublishFragement;
import cn.com.easytaxi.book.BookBean;
import cn.com.easytaxi.book.BookHistoryFragementActivity;
import cn.com.easytaxi.book.BookUtil;
import cn.com.easytaxi.book.NewBookDetail;
import cn.com.easytaxi.client.channel.TcpClient;
import cn.com.easytaxi.client.common.MsgConst;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.MapUtil;
import cn.com.easytaxi.common.SocketUtil;
import cn.com.easytaxi.common.ToolUtil;
import cn.com.easytaxi.common.Window;
import cn.com.easytaxi.dialog.CommonDialog;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.WheelSelectDate;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.onetaxi.NewMainActivityNew;
import cn.com.easytaxi.onetaxi.SearchAddressActivity;
import cn.com.easytaxi.platform.EditLinkActivity;
import cn.com.easytaxi.platform.MyWebViewActivity;
import cn.com.easytaxi.platform.RegisterActivity;
import cn.com.easytaxi.platform.service.SystemService;
import cn.com.easytaxi.util.DateTimePickDialog;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.TimeTool;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.XTCPUtil;
import cn.com.easytaxi.workpool.bean.GeoPointLable;

import com.aiton.yc.client.R;
import com.aiton.yc.client.wxapi.PayActivity;
import com.google.gson.JsonObject;

/**
 * 预约订单发布页面
 * 
 * @ClassName: BookPublishFragement
 * @Description: TODO
 * @author Brook Xu
 * @date 2015年4月18日 下午4:30:20
 * @version 1.0
 */
public class BookPublishFragement extends BookBaseFragement implements View.OnClickListener, OnCheckedChangeListener {
	private DateTimePickDialog mDateTimePicker;
	private AlertDialog detailDialog;
	private ProgressDialog mProgressDialog;
	/**
	 * 获取以前发过的历史订单
	 */
	private List<BookBean> historyBooks;
	//private int yugutyp = 1;
	private int yugutyp = 2;
	protected static final int START_CITY_REQ_CODE = 400;
	protected static final int END_CITY_REQ_CODE = 401;
	protected static final int EDIT_CCR = 110;// 编写乘车人信息
	private static SimpleDateFormat f_use_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat f_show_time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	private String mobile;
	private long timestamp;
	private static final int D_TIME = 10;
	// InputMethodManager imm;
	/**
	 * 是否强制在线支付： 0代表不需要在线支付；1表示必须在线支付
	 */
	private int onlinePayment = 0;
	private String tempstr = "高峰期适当加价，有助于更快坐到车";
	// 位置广播监听
	private LocationBroadcastReceiver locationReceiver;
	private RegisterReceiver registerReceiver;

	// 乘客位置信息（通过监听平台广播获得的,第一次直接取平台的）
	int p_lat = 0;
	int p_lng = 0;

	// 乘客起始地点位置
	int u_lat = 0;
	int u_lng = 0;
	// 起始点城市
	private String uCity = "";
	// 乘客结束地点位置
	int u_lat_end = 0;
	int u_lng_end = 0;

	/** price list pager */

	private Date submitTime;

	String checkedType = "";

	private int carType = 1;

	protected int priceKey = 0;
	protected String priceValue = "0";
	// protected List<DiaoDuPrice> priceList = new
	// ArrayList<NewNetworkRequest.DiaoDuPrice>(12);
	/**
	 * 滚动提示文字：显示加价上面的提示文字
	 */
	/**
	 * 默认的订车最小时间：单位分钟
	 */
	private static final int DEFAULT_MIN_USE_TIME = 30;

	/**
	 * 关键字航班
	 */
	private String[] keys = new String[] { "深圳机场", "宝安机场", "深圳国际机场", "宝安国际机场", "深圳宝安机场", "深圳宝安国际机场" };
	/**
	 * 默认的订车最大时间：单位分钟
	 */
	private int defaultMaxUseCarTime = 3 * 24 * 60;

	/**
	 * 订车时间上下线
	 */
	private NewNetworkRequest.TimeLine timeLine;
	/**
	 * 时间上下线获取回调
	 */
	private Callback<Object> timeLinesCallBack = new Callback<Object>() {

		@Override
		public void handle(Object param) {
			if (param != null) {
				TimeLines timeLines = (NewNetworkRequest.TimeLines) param;
				ArrayList<NewNetworkRequest.TimeLine> timeLineList = timeLines.datas;
				if (timeLineList.size() > 0) {
					timeLine = timeLineList.get(0);
					// 获取到时间上下线，重置默认的用车时间
					setUseCarTime(Integer.parseInt(timeLine.lower));
					// 显示时间
					// if (Integer.parseInt(timeLine.lower) > 0) {
					// default_book_time.setText(getTimeStr(Integer.parseInt(timeLine.lower)));
					// }

				}
			}
		}
	};
	private String startAddr = "";
	private CarSelectContoller carContoller;// 卡车控制器

	public void setInit(int startlat, int startlng, String address, String city, int carType) {
		this.initCarType = carType;
		if (startlat == 0 || startlng == 0 || TextUtils.isEmpty(address)) {
			return;
		}
		if (address.contains("滑动地图"))
			return;
		this.u_lat = startlat;
		this.u_lng = startlng;
		this.uCity = city;
		this.startAddr = address;
		if (startAddress != null && !TextUtils.isEmpty(address))
			startAddress.setText(address);
		// this.startAddress.settet
	}

	/**
	 * 预估价格
	 */
	private String yuPrice = "0";
	private String yuDistance = "0";
	private int initCarType = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new ProgressDialog(this.getActivity());
		mProgressDialog.setMessage("请稍后...");
		mobile = getPassengerId();
		linkMan_name = getUserName();
		linkMan_phone = mobile;
		p_lat = ETApp.getInstance().getCacheInt("_P_LAT");
		p_lng = ETApp.getInstance().getCacheInt("_P_LNG");
		// u_lat = p_lat;
		// u_lng = p_lng;

	}

	private String linkMan_name, linkMan_phone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.book_yy_order, container, false);
		init(view);
		regReceiver();
		setCheck();
		linkManTv.setText(linkMan_name + "  " + linkMan_phone);
		return view;
	}

	/**
	 * 是否需要填写航班信息
	 */
	private void isFeijiNo() {
		if (!sGd || !eGd || isKey(startAddress.getText().toString().trim()) || isKey(endAddress.getText().toString().trim())) {
			feijiNumber_layout.setVisibility(View.VISIBLE);
		} else {
			feijiNumber_layout.setVisibility(View.INVISIBLE);
			fejiNumber.setText("");
		}
	}

	private boolean isKey(String str) {
		int len = keys.length;
		for (int i = 0; i < len; i++) {
			if (str.contains(keys[i])) {
				return true;
			}
		}
		return false;
	}

	private boolean sGd = true;
	private boolean eGd = true;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 10: // 开始地址
				startAddress.setText(data.getStringExtra("address"));
				uCity = data.getStringExtra("city");
				AppLog.LogW("BootPublicFragment------:uCity", data.getStringExtra("city"));
				u_lat = (int) data.getLongExtra("lat", 0);
				u_lng = (int) data.getLongExtra("lng", 0);
				sGd = data.getBooleanExtra("isGuding", true);
				// 如果选择了目的地，这判断是否显示航班信息填写
				isFeijiNo();
				getYuPriceInNet();
				break;
			case 11: // 结束地址
				endAddress.setText(data.getStringExtra("address"));
				u_lat_end = (int) data.getLongExtra("lat", 0);
				u_lng_end = (int) data.getLongExtra("lng", 0);
				// 如果选择了目的地，这判断是否显示航班信息填写
				eGd = data.getBooleanExtra("isGuding", true);
				isFeijiNo();
				getYuPriceInNet();
				break;
			case EDIT_CCR: // 编辑联系人
				linkMan_name = data.getStringExtra("name");
				linkMan_phone = data.getStringExtra("phone");
				linkManTv.setText(linkMan_name + "  " + linkMan_phone);
				break;
			}
		}

		// if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
		// u_lat = data.getIntExtra("lat", 0);
		// u_lng = data.getIntExtra("lng", 0);
		// // etStartAddr.setText(data.getStringExtra("address"));
		// }
		// if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
		// u_lat_end = data.getIntExtra("lat", 0);
		// u_lng_end = data.getIntExtra("lng", 0);
		// // etEndAddr.setText(data.getStringExtra("address"));
		// }
		// setDistance();

	}

	protected boolean isChooseStartCity;

	// 出发时间
	private View startTime_layout;
	private TextView startTime;

	// 开始地址
	private TextView startAddress;
	private View startAddress_layout;

	// 结束地址
	private TextView endAddress;
	private View endAddress_layout;

	// 航班
	private TextView fejiNumber;
	private View feijiNumber_layout;
	//
	public TextView ygjTv;
	public TextView linkManTv;

	private void init(View view) {

		startTime_layout = view.findViewById(R.id.time_select_layout);
		startTime = (TextView) view.findViewById(R.id.book_usetime);

		startAddress = (TextView) view.findViewById(R.id.start_address);
		startAddress_layout = view.findViewById(R.id.start_address_layout);

		if (!TextUtils.isEmpty(startAddr))
			startAddress.setText(startAddr);
		endAddress = (TextView) view.findViewById(R.id.end_address);
		endAddress_layout = view.findViewById(R.id.end_address_layout);

		feijiNumber_layout = view.findViewById(R.id.feiji_address_layout);
		fejiNumber = (TextView) view.findViewById(R.id.feiji_address);
		carContoller = new CarSelectContoller(bookParent, view);
		carContoller.setOnCheckedChangeListener(this);
		ygjTv = (TextView) view.findViewById(R.id.yuguprice);
		linkManTv = (TextView) view.findViewById(R.id.linkmethod);
		view.findViewById(R.id.name_phone_select).setOnClickListener(this);
		startAddress.addTextChangedListener(new MyTextWatch());
		endAddress.addTextChangedListener(new MyTextWatch());
		startTime_layout.setOnClickListener(this);
		startAddress_layout.setOnClickListener(this);
		endAddress_layout.setOnClickListener(this);
		view.findViewById(R.id.mashangLayout).setOnClickListener(this);
		initCarCotroll(view);
		// 初始化默认为30分钟后用车
		setUseCarTime(DEFAULT_MIN_USE_TIME);
		mDateTimePicker = new DateTimePickDialog(this.getActivity(), f_show_time.format(submitTime));
		setSubmitEnable();
	}

	// 出租车，专车控制

	/**
	 * 底部出租车控制
	 */
	private void initCarCotroll(View view) {

	}

	private void switchAddress(int type, String cityName) {
		Intent intent = new Intent(bookParent, SearchAddressActivity.class);
		intent.putExtra("cityName", cn.com.easytaxi.receiver.LocationBroadcastReceiver.getcity());
		startActivityForResult(intent, type);
	}

	protected void restet(LinearLayout parent) {
		int count = parent.getChildCount();
		for (int i = 0; i < count; i++) {
			View v = parent.getChildAt(i);
			RadioButton cb = (RadioButton) v.findViewById(R.id.price_item);
			cb.setChecked(false);
		}
	}

	@Override
	public void onDestroy() {
		if (addrSetReceiver != null) {
			bookParent.unregisterReceiver(addrSetReceiver);
			addrSetReceiver = null;
		}
		unRegReceiver();
		super.onDestroy();
	}

	/**
	 * 编辑乘车人信息
	 */
	private void getCCRInfo() {
		Intent intent = new Intent(bookParent, EditLinkActivity.class);
		intent.putExtra(EditLinkActivity.default_name, linkMan_name);
		intent.putExtra(EditLinkActivity.default_phone, linkMan_phone);
		startActivityForResult(intent, EDIT_CCR);
	}

	//private int carstate = 1;
	private int carstate = 3;

	private void chooseZhuanche(int num) {
		switch (num) {
		case 1: // 舒适
			if (carstate != 3) {
				carstate = 3;
				yugutyp = 2;
				getYuPriceInNet();
			}
			break;
		case 2: // 豪华
			if (carstate != 5) {
				carstate = 5;
				yugutyp = 4;
				getYuPriceInNet();
			}
			break;
		case 3: // 商务
			if (carstate != 7) {
				carstate = 7;
				yugutyp = 3;
				getYuPriceInNet();
			}
			break;
		case 0:
			if (carstate != 1) {
				carstate = 1;
				yugutyp = 1;
				getYuPriceInNet();
			}
			break;
		default:
			break;
		}
	}

	class MyTextWatch implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			setSubmitEnable();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}
	}

	/**
	 * 设置提交订单是否可用
	 */
	private void setSubmitEnable() {
		// sendBook.setEnabled(submitEnable());
	}

	private long preTime = 1000 * 60 * 30;

	private boolean checkTime(Date selectTime) {
		Calendar now = Calendar.getInstance();
		Calendar use = Calendar.getInstance();
		use.setTime(selectTime);
		try {
			if (use.getTimeInMillis() < now.getTimeInMillis() + preTime) {
				ToastUtil.show(bookParent, "用车时间必须提前30分钟");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// private boolean submitEnable() {
	// String strStartAddr = startAddress.getText().toString();
	// String strEndAddr = endAddress.getText().toString();
	//
	// if (startTime.getText().toString().trim().length() == 0) {
	// Toast.makeText(bookParent, "请填写出发地址", Toast.LENGTH_SHORT).show();
	// return false;
	// }
	// if (TextUtils.isEmpty(strStartAddr)) {
	// Toast.makeText(bookParent, "请填写出发地址", Toast.LENGTH_SHORT).show();
	// return false;
	// }
	// if (TextUtils.isEmpty(strEndAddr)) {
	// return false;
	// }
	// return true;
	// }

	private void check() {
		if (!StringUtils.isEmpty(getPassengerId())) {

			// final String time = etUseTime.getText().toString().trim();
			String strStartAddr = startAddress.getText().toString();
			String strEndAddr = endAddress.getText().toString();

			try {

				if (!checkTime(submitTime))
					return;
				if (TextUtils.isEmpty(strStartAddr)) {
					ToastUtil.show(bookParent, "请输入上车地点");
					return;
				}
				if (TextUtils.isEmpty(strEndAddr)) {
					ToastUtil.show(bookParent, "请输入下车地点");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			getispay();
//			String feijiNumber = "";
//			if (feijiNumber_layout.getVisibility() == View.VISIBLE)
//				feijiNumber = fejiNumber.getText().toString();
//			send(carType, f_use_time.format(submitTime), strStartAddr, strEndAddr, feijiNumber);
		} else {

			Intent intent = new Intent(bookParent, RegisterActivity.class);
			startActivity(intent);

		}
	}
	private long bookid;
	//判断是否支付
		private void getispay(){
			mProgressDialog.show();
			// final String time = etUseTime.getText().toString().trim();
			final String strStartAddr = startAddress.getText().toString();
			final String strEndAddr = endAddress.getText().toString();

			JSONObject param = new JSONObject();
			try {
				param.put("action", "bookAction");
				param.put("method", "getBookByPay");
				param.put("passengerId", getPassengerId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			XTCPUtil.send(param, new XTCPUtil.SimpleTcpCallback() {
				@Override
				public void onSuc(String result) {
					try {
						JSONObject object = new JSONObject(result);
						int error = object.getInt("error");
						mProgressDialog.cancel();
						if (error == 1) {
							bookid = object.getLong("id");
							dialog1();
						}else if (error == 0) {
							String feijiNumber = "";
							if (feijiNumber_layout.getVisibility() == View.VISIBLE)
								feijiNumber = fejiNumber.getText().toString();
							send(carType, f_use_time.format(submitTime), strStartAddr, strEndAddr, feijiNumber);
						}
					} catch (Exception e) {
						e.printStackTrace();
						mProgressDialog.cancel();
					}
				}
				@Override
				public void error(Throwable e, int errorcode) {
					// TODO Auto-generated method stub
					super.error(e, errorcode);
					mProgressDialog.cancel();
				}
			});
		}
		//把原有的toast 改为的dialog
		private void dialog1(){
			CommonDialog dialog = MyDialog.comfirm(getActivity(), "温馨提示", "您还有未完成的订单", new SureCallback() {
				@Override
				public void onClick(int tag) {
					if (tag == RIGHT) {
						Intent intent = new Intent(getActivity(), NewBookDetail.class);
						intent.putExtra("bookid", bookid);
						intent.putExtra("4", 4);
						startActivity(intent);
					}
				}
			}, false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						return true;
					}
					return false;
				}
			});
			dialog.show();
		}	
	/**
	 * 发送预约订单
	 * 
	 * @param carType
	 * @param time
	 * @param strStartAddr
	 * @param strEndAddr
	 * @param flightNo
	 */
	private void send(int carType, String time, String strStartAddr, String strEndAddr, String flightNo) {
		showLoadingDialog("正在提交订单");
		try {
			int versionCode = ETApp.getInstance().getMobileInfo().getVerisonCode();
			final JSONObject json = new JSONObject();
			json.put("action", "scheduleAction");
			json.put("method", "submitBook");
			json.put("timestamp", timestamp);
			json.put("bookType", carstate);
			json.put("cityId", "0");
			json.put("cityName", uCity);
			json.put("passengerId", getPassengerId());
			json.put("passengerPhone", getPassengerId());
			json.put("phone", getPassengerId());
			String price = String.valueOf((int) (Double.parseDouble(yuPrice) * 100));
			json.put("forecastPrice", price);
			json.put("forecastDistance", yuDistance);
			// 1.android,2,ios
			json.put("source", 1);
			json.put("passengerName", getUserName());
			json.put("clientType", BookConfig.ClientType.CLIENT_TYPE_PASSENGER);
			json.put("sourceName", "" + versionCode);

			json.put("startAddress", strStartAddr);
			if (u_lng == 0 || u_lat == 0) {
				json.put("startLongitude", p_lng);
				json.put("startLatitude", p_lat);
			} else {
				json.put("startLongitude", u_lng);
				json.put("startLatitude", u_lat);
			}
			json.put("endAddress", strEndAddr);
			json.put("contacts", linkMan_name);
			json.put("contactPhone", linkMan_phone);
			json.put("endLongitude", u_lng_end);
			json.put("endLatitude", u_lat_end);
			json.put("useTime", time);
			json.put("flightNo", flightNo);
			System.out.println("预约订单 提交- >" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(mobile), json, new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {

					try {
						if (param != null) {
							SysDeug.logD("预约订单返回结果- >" + param.toString());
							int error = param.getInt("error");
							switch (error) {
							// 成功
							case 0x0000:
								ToastUtil.show(bookParent, "提交成功");
								Intent intent = new Intent("cn.com.easytaxi.book.refresh_list");
								intent.putExtra("fromSubmited", true);
								bookParent.sendBroadcast(intent);
								// 跳转到详情页面
								Intent intent1 = new Intent(bookParent, NewBookDetail.class);
								intent1.putExtra("bookiid", param.getLong("bookId"));
								intent1.putExtra("3", 3);
								bookParent.startActivity(intent1);
								bookParent.finish();
								break;
								// 需要在线支付，直接显示errormsg
							case 0x0004:
							default:
								ToastUtil.show(bookParent, param.getString("errormsg"));
								break;
							}
						} else {
							AppLog.LogD("error--->提交订单返回结果为空");
							ToastUtil.show(bookParent, "您的网络不给力！");
						}
					} catch (Exception e) {
						//
						e.printStackTrace();
					}

				}

				@Override
				public void error(Throwable e) {
					super.error(e);
					ToastUtil.show(bookParent, "您的网络不给力！");
				}

				@Override
				public void complete() {
					super.complete();
					cancelLoadingDialog();
					// try {
					// // dismissDialog(0);
					// } catch (Exception e1) {
					// e1.printStackTrace();
					// }
				}
			});






			// // 写入日志
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_phoneNum, phone);// 手机号
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_bookCar_time, time);// 用车时间
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_start_city, startCityName);//
			// 开始城市
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_start_addr, strStartAddr);//
			// 上车地点
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_start_city, endCityName);//
			// 结束城市
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_end_addr, strEndAddr);// 下车地点
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_add_price, priceValue);// 加价
			// ActionLogUtil.writeActionLog(bookParent,
			// R.array.book_bookpublishFragement_submit, "");// 提交按钮

		} catch (Exception e) {
			e.printStackTrace();
			cancelLoadingDialog();
		}
	}


















	public void selectDateByWheel() {

		/*
		 * Window.selectDate(bookParent, new Callback<String[]>() {
		 * 
		 * @Override public void handle(String[] param) {
		 * 
		 * Date date = new Date(ToolUtil.getLongTime(param)); String strDate =
		 * f_use.format(date); // etUseTime.setText(strDate);
		 * etUseTime.setText(ToolUtil.showTime(date)); submitTime = strDate; }
		 * });
		 */
		Callback<String[]> callBack = new Callback<String[]>() {

			@Override
			public void handle(String[] param) {
				int dayChoosedIndex = Integer.parseInt(param[0]);
				String dayName = param[1];
				int hour = Integer.parseInt(param[2]);
				int minites = Integer.parseInt(param[3]);
				submitTime = new Date(ToolUtil.getLongTimeNew(dayChoosedIndex, hour, minites, AirportBookPublishFragement.removeDays));
				// etUseTime.setText(ToolUtil.showTime(dayName, hour, minites));
			}
		};
		Window.selectDate(bookParent, callBack, timeLine, DEFAULT_MIN_USE_TIME, defaultMaxUseCarTime);
	}

	AddressSetBroadCast addrSetReceiver;

	private class AddressSetBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent data) {
			if (addrSetReceiver != null) {
				bookParent.unregisterReceiver(addrSetReceiver);
				addrSetReceiver = null;
			}
			int who = data.getIntExtra("who", 0);
			GeoPointLable point = (GeoPointLable) data.getSerializableExtra("result");
			if (point == null) {
				ToastUtil.show(bookParent, "未选择地点");
				return;
			}
			// setDistance();
		}
	}

	private class LocationBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			p_lat = intent.getIntExtra("latitude", 0);
			p_lng = intent.getIntExtra("longitude", 0);

		}
	}

	private class RegisterReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			mobile = getPassengerId();
			// mobile = session.get("_MOBILE");
			NewNetworkRequest.getAddressByLocation(p_lat, p_lng, firstAddressCallback);
		}
	}

	// 初始化广播监听
	private void regReceiver() {
		locationReceiver = new LocationBroadcastReceiver();
		bookParent.registerReceiver(locationReceiver, new IntentFilter(SystemService.BROADCAST_LOCATION));
		registerReceiver = new RegisterReceiver();
		bookParent.registerReceiver(registerReceiver, new IntentFilter(RegisterActivity.ACTION_REGISTER));
	}

	private void unRegReceiver() {
		if (locationReceiver != null) {
			bookParent.unregisterReceiver(locationReceiver);
			locationReceiver = null;
		}

		if (registerReceiver != null) {
			bookParent.unregisterReceiver(registerReceiver);
			registerReceiver = null;
		}

	}

	Callback<String> firstAddressCallback = new Callback<String>() {
		@Override
		public void handle(String param) {
			if (param != null && !isChooseStartCity) {

				// etStartAddr.setText(String.valueOf(param));
				// etEndAddr.requestFocus();
			}
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		timestamp = System.currentTimeMillis();
	}

	private void setDistance() {
		if (u_lat != 0 && u_lng != 0 && u_lat_end != 0 && u_lng_end != 0) {
			try {
				MapUtil.getRoutePlanAsync(Long.valueOf(mobile), u_lng, u_lat, u_lng_end, u_lat_end, new Callback<JSONObject>() {

					@Override
					public void handle(JSONObject param) {
						if (param != null) {
							try {
								String distance = param.getString("dis");
								String price = param.getString("price");

								AppLog.LogD("xyw", "distance--->" + distance);
								AppLog.LogD("xyw", "price--->" + price);

								String txt = bookParent.getString(R.string.book_distance, BookUtil.getDecimalNumber(Integer.parseInt(distance)), price);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

					@Override
					public void error(Throwable e) {
						// TODO Auto-generated method stub
						super.error(e);
					}
				});
			} catch (Exception e) {
			}
		}
	};

	/**
	 * 设置用车时间
	 * 
	 * @param minites
	 *            为多少分钟后用车
	 */
	public void setUseCarTime(int minites) {
		submitTime = new Date(System.currentTimeMillis() + minites * 60 * 1000);
	}

	/**
	 * 转换分钟数为天,时,分
	 * 
	 * @param minites
	 */
	public String getTimeStr(int minites) {
		HashMap<String, String> map = TimeTool.formatDuring(minites * 60 * 1000);
		StringBuffer str = new StringBuffer();
		if (Integer.parseInt(map.get("days")) > 0) {
			str.append(map.get("days") + "天");
		}

		if (Integer.parseInt(map.get("hours")) > 0) {
			str.append(map.get("hours") + "小时");
		}

		if (Integer.parseInt(map.get("minutes")) > 0) {
			str.append(map.get("minutes") + "分钟");
		}
		return str.toString();
	}

	private class CutDownTime extends AsyncTask<AlertDialog.Builder, Integer, Boolean> {

		private Builder alert;

		@Override
		protected Boolean doInBackground(Builder... params) {
			// TODO Auto-generated method stub

			alert = params[0];
			publishProgress(5);
			for (int i = 4; i >= 0; i--) {
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				publishProgress(i);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				detailDialog.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			// alert.setNegativeButton("取消" + values[0], dilClick);
			detailDialog.setButton2("取消" + values[0], dilClick);
		}

	}

	private class LoadBooks extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showMyProgressDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JsonObject json = new JsonObject();
			json.addProperty("action", "scheduleAction");
			json.addProperty("method", "getActiveBookListByPassenger");
			json.addProperty("passengerId", mobile);
			json.addProperty("size", 10);
			json.addProperty("startId", 0);
			try {
				byte[] response = TcpClient.send(1L, MsgConst.MSG_TCP_ACTION, json.toString().getBytes("UTF-8"));
				if (response != null && response.length > 0) {
					JSONObject jsonObject = new JSONObject(new String(response, "UTF-8"));
					AppLog.LogD("xyw", "book list-->" + jsonObject.toString());
					if (jsonObject.getInt("error") == 0) {
						historyBooks = new ArrayList<BookBean>();
						JSONArray jsonArray = jsonObject.getJSONArray("bookList");
						int length = jsonArray.length();
						JSONObject jsonObjectBookBean;
						for (int i = 0; i < length; i++) {
							jsonObjectBookBean = (JSONObject) jsonArray.get(i);
							BookBean bookBean = new BookBean();
							bookBean.setBookType(getJsonInt(jsonObjectBookBean, "bookType"));
							bookBean.setId(getJsonLong(jsonObjectBookBean, "id"));
							historyBooks.add(bookBean);
						}
					} else {
						// errorcode != 0
						historyBooks = null;
					}
				} else {
					// no datas
					historyBooks = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				historyBooks = null;
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (historyBooks != null && historyBooks.size() != 0) {
				for (BookBean book : historyBooks) {
					if (book.getBookType() == 5 || book.getBookType() == 6) {
						AlertDialog.Builder alert = showConfirm(book.getId());
						// new CutDownTime().execute(alert);
						break;
					}
				}
			} else {
				// do nothing, 发布新订单
			}

			dismissMyProgressDialog();
		}

	}

	private AlertDialog.Builder showConfirm(final long bookId) {
		// TODO Auto-generated method stub
		try {
			AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
			alert.setCancelable(false);
			alert.setMessage("您当前还有未完成的订单！");
			alert.setTitle("提示");
			alert.setPositiveButton("查看", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Intent intent = new
					// Intent(BookPublishFragement.this.getActivity(),
					// NewBookDetail.class);
					Intent intent = new Intent(BookPublishFragement.this.getActivity(), BookHistoryFragementActivity.class);
					intent.putExtra("bookId", bookId);
					intent.putExtra("bookid", 4);
					BookPublishFragement.this.getActivity().startActivity(intent);
					dialog.dismiss();
				}
			});
			alert.setNegativeButton("取消", dilClick);
			detailDialog = alert.create();
			detailDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			detailDialog.show();

			return alert;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	private DialogInterface.OnClickListener dilClick = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	public static String getJsonString(JSONObject jsonObject, String name) {
		try {
			return jsonObject.getString(name);
		} catch (Exception e) {
			return "";
		}
	}

	public static int getJsonInt(JSONObject jsonObject, String name) {
		try {
			return Integer.parseInt(jsonObject.getString(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getJsonLong(JSONObject jsonObject, String name) {
		try {
			return Long.parseLong(jsonObject.getString(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public void showMyProgressDialog(String msg) {
		if (!TextUtils.isEmpty(msg)) {
			mProgressDialog.setMessage(msg);
		}
		mProgressDialog.show();
	}

	public void showMyProgressDialog() {
		showMyProgressDialog(null);
	}

	public void dismissMyProgressDialog() {
		mProgressDialog.dismiss();
	}

	private void getYuPriceInNet() {
		if (u_lat != 0 && u_lng != 0 && u_lat_end != 0 && u_lng_end != 0) {
			getYuPrice();
		}
	}

	/**
	 * 预估价格接口
	 * 
	 * @throws JSONException
	 */
	public void getYuPrice() {
		showLoadingDialog("");
		try {
			JSONObject param = new JSONObject();
			param.put("action", "geoAction");
			param.put("method", "getRoutePlan");
			param.put("slng", u_lng);
			param.put("slat", u_lat);
			param.put("elng", u_lng_end);
			param.put("elat", u_lat_end);
			param.put("carType", yugutyp);
			param.put("cityName", uCity);
			SysDeug.logD("yugu request - > " + param.toString());
			XTCPUtil.send(param, new XTCPUtil.XNetCallback() {

				@Override
				public void onSuc(String result) {
					try {
						yuPrice = new JSONObject(result).getString("price");
						String price = "(预估价¥ " + yuPrice + ")";
						ygjTv.setText(price);
						yuDistance = new JSONObject(result).getString("dis");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onStart() {

				}

				@Override
				public void onComplete() {
					cancelLoadingDialog();
				}

				@Override
				public void error(Throwable e, int errorcode) {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			cancelLoadingDialog();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.end_address_layout:
			switchAddress(11, "");
			break;
		case R.id.start_address_layout:
			// 选取开始地址
			switchAddress(10, "");
			break;
		case R.id.btn_book_endloc:
			// switchAddress(2, endCityName);
			break;
		case R.id.name_phone_select:
			getCCRInfo();
			break;
		case R.id.time_select_layout:
			new WheelSelectDate(getActivity()).show(new Callback<Calendar>() {

				@Override
				public void handle(Calendar param) {
					Date time = param.getTime();
					if (checkTime(time)) {
						submitTime = time;
						startTime.setText(f_show_time.format(submitTime));
						setSubmitEnable();
					}
				}
			});
			break;

		case R.id.mashangLayout:
			check();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_hh:
			chooseZhuanche(2);
			break;
		case R.id.rb_ss:
			chooseZhuanche(1);
			break;
		case R.id.rb_sw:
			chooseZhuanche(3);
			break;
//		case R.id.rb_taxi:
//			chooseZhuanche(0);
//			break;
		}
	}

	private void setCheck() {
		switch (initCarType) {
//		case 1:// 出租车
//			carContoller.setCheckId(R.id.rb_taxi);
//			chooseZhuanche(0);
//			break;
		case 2:// 舒适车
			carContoller.setCheckId(R.id.rb_ss);
			chooseZhuanche(1);
			break;
		case 3:// 商务车
			carContoller.setCheckId(R.id.rb_sw);
			chooseZhuanche(3);
			break;
		case 4:// 豪华车
			carContoller.setCheckId(R.id.rb_hh);
			chooseZhuanche(2);
			break;
		}
	}

}
