package cn.com.easytaxi.book;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aiton.yc.client.R;
import com.aiton.yc.client.wxapi.PayActivity;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.BookConfig;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.SocketUtil;
import cn.com.easytaxi.common.Window;
import cn.com.easytaxi.dialog.CommonDialog;
import cn.com.easytaxi.dialog.DiaoduCancelDialog;
import cn.com.easytaxi.dialog.DiaoduCancelDialog.DialoAction;
import cn.com.easytaxi.dialog.ListSelectDialog;
import cn.com.easytaxi.dialog.ListSelectDialog.SelectListener;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.dialog.ToastDialog;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.ui.ComplainActivity;
import cn.com.easytaxi.ui.FeiYongMingXiActivity;
import cn.com.easytaxi.util.MyLog;
import cn.com.easytaxi.util.TimeTool;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.Utils;
import cn.com.easytaxi.util.XGsonUtil;
import cn.com.easytaxi.util.XTCPUtil;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.xc.lib.xutils.BitmapUtils;

public class NewBookDetail extends YdLocaionBaseActivity implements
OnClickListener, OnGetRoutePlanResultListener {
	public static final String CUIKUANBROCAST = "cuikuanborcast";
	public static final int TYPE_PAY = 11;
	private DecimalFormat deciformat = new DecimalFormat("#.#");
	private BitmapDescriptor che = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_car_map);
	private BitmapDescriptor che1 = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_car1_map);
	private BitmapDescriptor che2 = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_car2_map);
	private BitmapDescriptor che3 = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_car4_map);
	private BitmapDescriptor che4 = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_car3_map);
	private MapView mMapView = null; // 地图View
	private BaiduMap mBaidumap = null;
	private RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private DisplayMetrics dm;
	private Resources res;
	private String phones;
	private String vCode = "";
	private BookBean bookBean;
	private ImageView mHead;
	private TextView mName;
	private TextView mCar;
	private ImageView mSmall_star1;
	private ImageView mSmall_star2;
	private ImageView mSmall_star3;
	private ImageView mSmall_star4;
	private ImageView mSmall_star5;
	private TextView mFenshu;
	private TextView mDanshu;
	private TextView juli;
	private TextView shijian;
	private ImageView mCall_image;
	private LinearLayout mYuyue_layout;
	private LinearLayout bj;
	private ImageView bj_nomsg;
	private TextView bj_nomsgtext;
	private TextView mMileage_text;
	private TextView mLength_text;
	private TextView mPrice_text;
	private TitleBar bar;
	private LinearLayout mPingjia_layout;
	private ImageView mStar1;
	private ImageView mStar2;
	private ImageView mStar3;
	private ImageView mStar4;
	private ImageView mStar5;
	private LinearLayout mCuikuan_layout;
	private LinearLayout mBtn_layout;
	private TextView mBtn_text;
	private ImageView mDingwei;
	private int starnum = 0;
	private BitmapUtils bitmapUtils;
	private LinearLayout mQuxiaoLayout;
	private TextView mStart;
	private TextView mEnd;
	private ImageView mZhong;
	private TextView mShijian;
	private TextView mDannum;
	private TextView mWhen;
	private TextView mWhy;
	private TextView mState;
	private TextView mState2;
	private LinearLayout xiache;// 0428,新增下车
	private View sus_view;
	private BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.track_map2_che);
	private InfoWindow mInfoWindow;
	private int quxiaoChoose = 0;// 0与司机协商，1我单方，2司机原因
	private boolean iscar = true;
	private boolean bgmsg = false;
	BitmapDescriptor lu = BitmapDescriptorFactory
			.fromResource((R.drawable.jintous));
	BitmapDescriptor start = BitmapDescriptorFactory
			.fromResource(R.drawable.track_map2_start);
	BitmapDescriptor end = BitmapDescriptorFactory
			.fromResource(R.drawable.track_map2_end);
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.arg1 == 1) {
			} else if (msg.arg1 == 2) {
				AppLog.LogD("refresh locaion car");
			} else if (msg.arg1 == 3) {
				setMyLocation(getCurrentlat(), getCurrentLng(),
						getCurrentRadius(), getCurrentDerect(), false);
			} else if (msg.arg1 == 4) {
				getCar();
				if (bookBean.getState() == Config.STATE_YISHANGCHE) {
					getGuiJi(false);
				}
			}
		};
	};
	private CuikuanReceiver cuikuanreceiver = null;

	private class CuikuanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final long bookId = intent.getLongExtra("bookId", 0l);
			CommonDialog dialog = MyDialog.comfirm(NewBookDetail.this, "付款提醒",
					"花儿都谢了，快付款吧！", new SureCallback() {
				public void onClick(int tag) {
					if (tag == RIGHT) {
						Intent intent = new Intent();
						intent.setClass(NewBookDetail.this,
								NewBookDetail.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("bookId", bookId);
						startActivity(intent);
					}
				};
			}, false, false, true);
			dialog.setRightTxt("好的");
			dialog.show();
		}
	}

	private long bookid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_book_detail);
		bitmapUtils = new BitmapUtils(NewBookDetail.this);
		mMapView = (MapView) findViewById(R.id.map);
		mBaidumap = mMapView.getMap();
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
		mMapView.showScaleControl(false);
		mMapView.showZoomControls(false);
		mBaidumap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(15).build()));
		dm = getDisplayMetrics();
		Intent i = this.getIntent();
		res = this.getResources();
		registReceiver();
		bookBean = (BookBean) i.getSerializableExtra("book");
		// try {
		// if (bookBean!=null) {
		// bookid = bookBean.getId();
		// }else {
		// bookid = getIntent().getLongExtra("bookid", 0);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		try {
			bookBean.setDyTime((TimeTool.DEFAULT_DATE_FORMATTER.parse(
					bookBean.getUseTime()).getTime() - System
					.currentTimeMillis()) / 1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bookBean == null) {
			bookBean = new BookBean();
			if (i.getIntExtra("1", 0) == 1) {
				bookBean.setId(i.getLongExtra("bookId", -1));
			} else if (i.getIntExtra("2", 0) == 2) {
				bookBean.setId(i.getLongExtra("bookid", -1));
			} else if (i.getIntExtra("3", 0) == 3) {
				bookBean.setId(i.getLongExtra("bookiid", -1));
			} else if (i.getIntExtra("4", 4) == 4) {
				bookBean.setId(i.getLongExtra("bookid", -1));
			}
		} else {
			AppLog.LogD("xyw", "detail bean-->" + bookBean);
		}
		initViews();
		getDetail(bookBean.getId(), false);
		// getDetail(bookid, false);
		new Thread(new Runnable() {
			public void run() {
				while (iscar) {
					android.os.Message msg = new Message();
					msg.arg1 = 4;
					handler.sendMessage(msg);
					SystemClock.sleep(10 * 1000);
				}
			}
		}).start();

	}

	private void registerCuikuan() {
		if (cuikuanreceiver == null) {
			cuikuanreceiver = new CuikuanReceiver();
			IntentFilter filter = new IntentFilter(CUIKUANBROCAST);
			registerReceiver(cuikuanreceiver, filter);
		}
	}

	private void unRegisterCuikuan() {
		if (cuikuanreceiver != null) {
			unregisterReceiver(cuikuanreceiver);
			cuikuanreceiver = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.call_image:
			callDriver(phones);
			break;
		case R.id.star1:
			setstar(1);
			break;
		case R.id.star2:
			setstar(2);
			break;
		case R.id.star3:
			setstar(3);
			break;
		case R.id.star4:
			setstar(4);
			break;
		case R.id.star5:
			setstar(5);
			break;
		case R.id.dingwei:
			mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(
					getCurrentlat() / 1E6, getCurrentLng() / 1E6)));
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		bj = (LinearLayout) findViewById(R.id.bj);
		bj_nomsg = (ImageView) findViewById(R.id.bj_nomsg);
		bj_nomsgtext = (TextView) findViewById(R.id.bj_nomsgtext);
		bar = new TitleBar(NewBookDetail.this);
		bar.setTitleName("订单详情");
		mHead = (ImageView) findViewById(R.id.head);
		mName = (TextView) findViewById(R.id.name);
		mCar = (TextView) findViewById(R.id.car);
		mSmall_star1 = (ImageView) findViewById(R.id.small_star1);
		mSmall_star2 = (ImageView) findViewById(R.id.small_star2);
		mSmall_star3 = (ImageView) findViewById(R.id.small_star3);
		mSmall_star4 = (ImageView) findViewById(R.id.small_star4);
		mSmall_star5 = (ImageView) findViewById(R.id.small_star5);
		mFenshu = (TextView) findViewById(R.id.fenshu);
		mDanshu = (TextView) findViewById(R.id.danshu);
		mCall_image = (ImageView) findViewById(R.id.call_image);
		mCall_image.setOnClickListener(this);
		mYuyue_layout = (LinearLayout) findViewById(R.id.yuyue_layout);
		mMileage_text = (TextView) findViewById(R.id.mileage_text);
		mLength_text = (TextView) findViewById(R.id.length_text);
		mPrice_text = (TextView) findViewById(R.id.price_text);
		mPingjia_layout = (LinearLayout) findViewById(R.id.pingjia_layout);
		mStar1 = (ImageView) findViewById(R.id.star1);
		mStar2 = (ImageView) findViewById(R.id.star2);
		mStar3 = (ImageView) findViewById(R.id.star3);
		mStar4 = (ImageView) findViewById(R.id.star4);
		mStar5 = (ImageView) findViewById(R.id.star5);
		mCuikuan_layout = (LinearLayout) findViewById(R.id.cuikuan_layout);
		mBtn_layout = (LinearLayout) findViewById(R.id.btn_layout);
		mBtn_text = (TextView) findViewById(R.id.btn_text);
		mDingwei = (ImageView) findViewById(R.id.dingwei);
		mDingwei.setOnClickListener(this);
		mQuxiaoLayout = (LinearLayout) findViewById(R.id.quxiao_layout);
		mStart = (TextView) findViewById(R.id.start_text);
		mEnd = (TextView) findViewById(R.id.end_text);
		mZhong = (ImageView) findViewById(R.id.zhong_image);
		mShijian = (TextView) findViewById(R.id.time_text);
		mDannum = (TextView) findViewById(R.id.ordernum_text);
		mWhen = (TextView) findViewById(R.id.when);
		mWhy = (TextView) findViewById(R.id.why);
		mState = (TextView) findViewById(R.id.state);
		mState2 = (TextView) findViewById(R.id.state2);
		bj.setVisibility(View.VISIBLE);
		mQuxiaoLayout.setVisibility(View.GONE);
		mPingjia_layout.setVisibility(View.GONE);
		mCuikuan_layout.setVisibility(View.GONE);
		mBtn_layout.setVisibility(View.GONE);
		mCuikuan_layout.setVisibility(View.GONE);
		mYuyue_layout.setVisibility(View.GONE);

		mFeiyongmingxi = (TextView) findViewById(R.id.feiyongmingxi);
		feiyongmingxi();
		bj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!bgmsg) {
					getDetail(bookBean.getId(), false);
				}
			}
		});
		xiache = (LinearLayout) findViewById(R.id.xiache);// 04.28,新增下车
		xiache.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyDialog.comfirm(NewBookDetail.this, "温馨提示", "您是否已到达目的地？",
						new SureCallback() {
					@Override
					public void onClick(int tag) {
						if (tag == LEFT) {

						} else {
							getxiache();
						}
					}
				});
			}
		});
	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initUserData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void regReceiver() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unRegReceiver() {
		unRegisterCuikuan();

	}

	private void registReceiver() {
		IntentFilter filter = new IntentFilter(
				"cn.com.easytaxi.book.state.changed");
		// filter.addAction(Intent.ACTION_SCREEN_ON);
		this.registerReceiver(reloadReceiver, filter);
		registerCuikuan();
	}

	private BroadcastReceiver reloadReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			AppLog.LogD("xyw", "change onetaxi--->onReceive");
			getDetail(bookBean.getId(), true);
			// getDetail(bookid, true);
		}
	};
	private TextView mFeiyongmingxi;

	public DisplayMetrics getDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// AppLog.LogD("***************"+dm.widthPixels);
		// AppLog.LogD("***************"+dm.heightPixels);
		return dm;
	}

	@Override
	protected void onResume() {
		getDetail(bookBean.getId(), false);
		// getDetail(bookid, false);
		if (bookBean.getState() == Config.STATE_YIJIEDAN) {
			getCar();
		} else if (bookBean.getState() == Config.STATE_YISHANGCHE) {
			getCar();
			getGuiJi(false);
		}
		super.onResume();
	}

	/**
	 * 从服务端获取订单详情
	 * 
	 * @param bookId
	 * @param show
	 * @return void
	 */
	private void getDetail(long bookId, final boolean show) {
		bj.setVisibility(View.VISIBLE);
		try {
			// showDialog(0);
			JSONObject json = new JSONObject();
			json.put("action", "proxyAction");
			json.put("method", "query");
			json.put("op", "getBookInfoByPassenger");
			json.put("bookId", bookId);
			json.put("cityId", cn.com.easytaxi.platform.MainActivityNew.cityId);
			json.put("cityName",
					cn.com.easytaxi.platform.MainActivityNew.currentCityName);
			json.put("clientType", BookConfig.ClientType.CLIENT_TYPE_PASSENGER);
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json,
					new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JSONObject result = (JSONObject) param;

							AppLog.LogD("xyw", "book detail--->"
									+ result.toString());
							if (result.getInt("error") != 0) {
								throw new Exception(result.toString());
							} else {
								bj.setVisibility(View.GONE);
								result = result.getJSONArray("datas")
										.getJSONObject(0);
								vCode = getJsonString(result, "vCode");
								AppLog.LogD("xyw", "vCode--->9" + vCode);
								setBookBeanByJson(result, show);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						bj_nomsg.setVisibility(View.VISIBLE);
						bj_nomsgtext.setText("网络出错了,点击重试");
						bgmsg = true;
						// ToastUtil.show(NewBookDetail.this,
						// "获取订单详情失败");
					}
				}

				@Override
				public void error(Throwable e) {
					// TODO Auto-generated method stub
					super.error(e);
					e.printStackTrace();
					bj_nomsg.setVisibility(View.VISIBLE);
					bj_nomsgtext.setText("网络出错了,点击重试");
					bgmsg = true;
					// ToastUtil.show(NewBookDetail.this, "获取订单详情失败");
				}

				@Override
				public void complete() {
					cancelLoadingDialog();
					// try {
					// dismissDialog(0);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			bj_nomsg.setVisibility(View.VISIBLE);
			bj_nomsgtext.setText("网络出错了,点击重试");
			bgmsg = true;
		}

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// getBookSuggest(bookBean.getId());
			}
		}, 500);
	}

	// 初始化状态
	private void setMyLocation(int lat, int lng, float radius, float derect,
			boolean isCenter) {
		AppLog.LogD("setMyLocation = " + lat);
		if (lat != 0 && lng != 0) {

		}

		Message msg = Message.obtain();
		msg.arg1 = 3;
		msg.what = 3;

		handler.sendMessageDelayed(msg, 15000);

	}

	private void setBookBeanByJson(JSONObject jsonObjectBookBean, boolean show) {
		bookBean.setEvaluate(getJsonInt(jsonObjectBookBean, "evaluate"));
		bookBean.setStartAddress(getJsonString(jsonObjectBookBean, "startAddr"));
		bookBean.setEndLongitude(getJsonInt(jsonObjectBookBean, "endLng"));
		bookBean.setPassengerId(getJsonLong(jsonObjectBookBean, "passengerId"));
		bookBean.setSubmitTime(getJsonString(jsonObjectBookBean, "submitTime"));
		bookBean.setScore(getJsonInt(jsonObjectBookBean, "credits"));
		bookBean.setForecastPrice(getJsonString(jsonObjectBookBean,
				"forecastPrice"));
		bookBean.setBookType(getJsonInt(jsonObjectBookBean, "bookType"));
		bookBean.setReplyerNumber(getJsonString(jsonObjectBookBean,
				"replyerNumber"));
		bookBean.setEndAddress(getJsonString(jsonObjectBookBean, "endAddr"));
		bookBean.setPoints(getJsonInt(jsonObjectBookBean, "credits"));
		bookBean.setId(getJsonLong(jsonObjectBookBean, "id"));
		bookBean.setReplyerId(getJsonLong(jsonObjectBookBean, "replyerId"));
		bookBean.setReplyerPhone(getJsonString(jsonObjectBookBean,
				"replyerPhone"));
		bookBean.setReplyerName(getJsonString(jsonObjectBookBean, "replyerName"));
		bookBean.setState(getJsonInt(jsonObjectBookBean, "bookState"));
		bookBean.setEndLatitude(getJsonInt(jsonObjectBookBean, "endLat"));
		bookBean.setTimeLong(getJsonString(jsonObjectBookBean, "timeLong"));
		bookBean.setAvatarImage(getJsonString(jsonObjectBookBean, "avatarImage"));
		bookBean.setPlResult(getJsonInt(jsonObjectBookBean, "plResult"));
		bookBean.setPassengerPhone(getJsonString(jsonObjectBookBean,
				"passengerPhone"));
		bookBean.setReplyerCompany(getJsonString(jsonObjectBookBean,
				"replyerCompany"));
		bookBean.setStartLongitude(getJsonInt(jsonObjectBookBean, "startLng"));
		bookBean.setReplyTime(getJsonString(jsonObjectBookBean, "replyTime"));
		bookBean.setPriceMode(getJsonInt(jsonObjectBookBean, "priceMode"));
		try {
			long distance = getJsonLong(jsonObjectBookBean, "forecastDistance");
			bookBean.setForecastDistance(deciformat.format(distance / 1000d));
		} catch (Exception e) {
			e.printStackTrace();
		}
		bookBean.setPreTime(getJsonString(jsonObjectBookBean, "currentDate"));
		bookBean.setStartLatitude(getJsonInt(jsonObjectBookBean, "startLat"));
		bookBean.setPrice(getJsonInt(jsonObjectBookBean, "price"));
		bookBean.setBookFlags(getJsonInt(jsonObjectBookBean, "bookFlags"));
		bookBean.setIsPl(getJsonInt(jsonObjectBookBean, "isPl"));
		bookBean.setCalled_number(getJsonString(jsonObjectBookBean,
				"called_number"));
		bookBean.setLevel(getJsonString(jsonObjectBookBean, "levels"));
		bookBean.setCx(getJsonString(jsonObjectBookBean, "cx"));
		bookBean.setReason(getJsonString(jsonObjectBookBean, "reason"));
		bookBean.setFee(getJsonString(jsonObjectBookBean, "_FEE"));
		bookBean.setCarColor(getJsonString(jsonObjectBookBean, "carColor"));
		bookBean.setForecastPrice(getJsonString(jsonObjectBookBean,
				"forecastPrice"));
		try {
			String time = getJsonString(jsonObjectBookBean, "useTime");
			bookBean.setUseTime(time);
		} catch (Exception e) {
			AppLog.LogD(
					"xyw",
					"detail-useTime:"
							+ getJsonString(jsonObjectBookBean, "useTime"));
			e.printStackTrace();
		}
		if (show) {
			if (bookBean.getState() == Config.STATE_YISHANGCHE) {
				ToastDialog dialog = new ToastDialog(NewBookDetail.this);
				dialog.showDialog("您已上车!");
				//xiache.setVisibility(View.VISIBLE);
				xiache.setVisibility(View.GONE);
			} else if (bookBean.getState() == Config.STATE_YIXIACHE) {
				ToastDialog dialog = new ToastDialog(NewBookDetail.this);
				dialog.showDialog("您已下车!");
				xiache.setVisibility(View.GONE);
			} else if (bookBean.getState() == Config.STATE_YIZHIFU) {
				if (bookBean.getBookType() < 3) {
					ToastDialog dialog = new ToastDialog(NewBookDetail.this);
					dialog.showDialog("您已下车!");
					xiache.setVisibility(View.GONE);
				}
			}
		}
		setDetail(true);
	}

	public String getJsonString(JSONObject jsonObject, String name) {
		try {
			return jsonObject.getString(name);
		} catch (Exception e) {
			return "";
		}
	}

	public int getJsonInt(JSONObject jsonObject, String name) {
		try {
			return Integer.parseInt(jsonObject.getString(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public long getJsonLong(JSONObject jsonObject, String name) {
		try {
			return Long.parseLong(jsonObject.getString(name));
		} catch (Exception e) {
			return -1;
		}
	}

	private void setDetail(boolean isGone) {
		mBaidumap.clear();
		PlanNode stNode;
		PlanNode enNode;
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		AppLog.LogD("bookState-->" + bookBean.getState());
		AppLog.LogD("vCode22-->" + vCode);
		final Intent intent = new Intent();
		intent.setClass(NewBookDetail.this, ComplainActivity.class);
		intent.putExtra("bean", bookBean);
		// setYuyue();
		switch (bookBean.getState()) {
		case Config.STATE_DIAODUZHONG:
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mDanshu.setText(bookBean.getCalled_number() + "单");
			bar.getRightHomeButton().setText("取消");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DiaoduCancelDialog.newInstance(NewBookDetail.this)
					.setActionListener(new DialoAction() {

						@Override
						public void onClick(int tag) {
							if (tag == RIGHT) {
								showLoadingDialog("");
								cancelBook(bookBean.getId(),
										0x01 << 0x0a);
							}
						}
					}).show();
				}
			});
			mQuxiaoLayout.setVisibility(View.VISIBLE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.GONE);

			// if (getIntent().getIntExtra("1", 0) == 1) {
			// mState.setText("调度中");
			// }else if (getIntent().getIntExtra("2", 0) == 2) {
			// mState.setText("调度中");
			// }else if (getIntent().getIntExtra("3", 0) == 3) {
			// mState.setText("调度中，若30分钟后任未调度成功，请重新下单");
			// }

			mState2.setVisibility(View.VISIBLE);
			mState2.setText("调度中...");
			mState.setText("若30分钟后仍未调度成功，请重新下单");

			mStart.setText(bookBean.getStartAddress());
			if (TextUtils.isEmpty(bookBean.getEndAddress())) {
				mZhong.setVisibility(View.GONE);
				mEnd.setVisibility(View.GONE);
			} else {
				mZhong.setVisibility(View.VISIBLE);
				mEnd.setVisibility(View.VISIBLE);
				mEnd.setText(bookBean.getEndAddress());
			}
			mShijian.setText(bookBean.getUseTime());
			mDannum.setText(bookBean.getCalled_number());
			mWhy.setText("");
			mWhen.setText("");
			break;
		case Config.STATE_YISHANGCHE:
			mBaidumap.clear();
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mDanshu.setText(bookBean.getCalled_number() + "单");
			bar.getRightHomeButton().setText("投诉");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(intent);
				}
			});
			phones = bookBean.getReplyerPhone();
			bitmapUtils.display(mHead, bookBean.getAvatarImage());
			mName.setText(bookBean.getReplyerName());
			mCar.setText(bookBean.getReplyerNumber() + "    "
					+ bookBean.getCarColor() + "    " + bookBean.getCx());
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mQuxiaoLayout.setVisibility(View.GONE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.GONE);
			if (bookBean.getBookType() == 1 || bookBean.getBookType() == 2) {
				xiache.setVisibility(View.GONE);// 0428,新增
				//xiache.setVisibility(View.VISIBLE);// 0428,新增
			}else {
				xiache.setVisibility(View.GONE);// 0428,新增
			}

			getCar();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					getGuiJi(false);
				}
			}, 10000);
			break;
		case Config.STATE_YIJIEDAN:
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mDanshu.setText(bookBean.getCalled_number() + "单");
			bar.getRightHomeButton().setText("取消");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ListSelectDialog dialog = ListSelectDialog
							.newInstance(NewBookDetail.this);
					// dialog.canDisEnable(true);
					dialog.setTitle("取消订单");
					dialog.setDatas(Config.resonlist, 0);
					dialog.setSelectListener(new SelectListener() {

						@Override
						public void onSure(int flag, int tag, String value) {
							if (tag == 0) {
								quxiaoChoose = 0x01 << 0x0b;
							}
							showLoadingDialog("");
							cancelBook(bookBean.getId(), quxiaoChoose);
						}

						@Override
						public void onSelect(int flag, int tag) {
							switch (tag) {
							case 0:
								quxiaoChoose = 0x01 << 0x0b;
								break;
							case 1:
								quxiaoChoose = 0x01 << 0x0a;
								break;
							case 2:
								quxiaoChoose = 0x01 << 0x09;
								break;

							default:
								break;
							}
						}
					});
					dialog.show();
				}
			});
			phones = bookBean.getReplyerPhone();
			bitmapUtils.display(mHead, bookBean.getAvatarImage());
			mName.setText(bookBean.getReplyerName());
			mCar.setText(bookBean.getReplyerNumber() + "    "
					+ bookBean.getCarColor() + "    " + bookBean.getCx());
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mQuxiaoLayout.setVisibility(View.GONE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.GONE);
			stNode = PlanNode.withLocation(new LatLng(bookBean
					.getStartLatitude() / 1E6,
					bookBean.getStartLongitude() / 1E6));
			enNode = PlanNode.withLocation(new LatLng(
					bookBean.getEndLatitude() / 1E6,
					bookBean.getEndLongitude() / 1E6));
			mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode)
					.to(enNode));
			getCar();
			break;
		case Config.STATE_DIAODUSHIBAI:
			iscar = false;
			bar.getRightHomeButton().setVisibility(View.GONE);
			mQuxiaoLayout.setVisibility(View.VISIBLE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.GONE);
			mState2.setVisibility(View.GONE);
			mState.setText("调度失败");
			mStart.setText(bookBean.getStartAddress());
			if (TextUtils.isEmpty(bookBean.getEndAddress())) {
				mZhong.setVisibility(View.GONE);
				mEnd.setVisibility(View.GONE);
			} else {
				mZhong.setVisibility(View.VISIBLE);
				mEnd.setVisibility(View.VISIBLE);
				mEnd.setText(bookBean.getEndAddress());
			}
			mShijian.setText(bookBean.getUseTime());
			mDannum.setText(bookBean.getCalled_number());
			mWhy.setText("调度失败");
			bar.getRightHomeButton().setVisibility(View.GONE);
			mWhen.setText(bookBean.getCancellation_time() + "调度失败");
			break;
		case Config.STATE_QUXIAO:
			iscar = false;
			bar.getRightHomeButton().setVisibility(View.GONE);
			mQuxiaoLayout.setVisibility(View.VISIBLE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.GONE);
			mState2.setVisibility(View.GONE);
			mState.setText("订单已取消");
			mStart.setText(bookBean.getStartAddress());
			if (TextUtils.isEmpty(bookBean.getEndAddress())) {
				mZhong.setVisibility(View.GONE);
				mEnd.setVisibility(View.GONE);
			} else {
				mZhong.setVisibility(View.VISIBLE);
				mEnd.setVisibility(View.VISIBLE);
				mEnd.setText(bookBean.getEndAddress());
			}
			mShijian.setText(bookBean.getUseTime());
			mDannum.setText(bookBean.getCalled_number());
			mWhy.setText(getreson(bookBean.getReason()));
			mWhen.setText(bookBean.getCancellation_time() + "订单取消");
			bar.getRightHomeButton().setText("投诉");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(intent);
				}
			});
			break;
		case Config.STATE_ZHIXINGSHIBAI:
			iscar = false;
			bar.getRightHomeButton().setVisibility(View.GONE);
			mQuxiaoLayout.setVisibility(View.VISIBLE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.GONE);
			mState2.setVisibility(View.GONE);
			mState.setText("订单已取消");
			mStart.setText(bookBean.getStartAddress());
			if (TextUtils.isEmpty(bookBean.getEndAddress())) {
				mZhong.setVisibility(View.GONE);
				mEnd.setVisibility(View.GONE);
			} else {
				mZhong.setVisibility(View.VISIBLE);
				mEnd.setVisibility(View.VISIBLE);
				mEnd.setText(bookBean.getEndAddress());
			}
			mShijian.setText(bookBean.getUseTime());
			mDannum.setText(bookBean.getCalled_number());
			mWhy.setText(getreson(bookBean.getReason()));
			mWhen.setText(bookBean.getCancellation_time() + "订单取消");
			bar.getRightHomeButton().setText("投诉");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(intent);
				}
			});
			break;
		case Config.STATE_YIPINGJIA:
			iscar = false;

			mYuyue_layout.setVisibility(View.VISIBLE);

			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mDanshu.setText(bookBean.getCalled_number() + "单");
			bar.getRightHomeButton().setVisibility(View.GONE);
			phones = bookBean.getReplyerPhone();
			bitmapUtils.display(mHead, bookBean.getAvatarImage());
			mName.setText(bookBean.getReplyerName());
			mCar.setText(bookBean.getReplyerNumber() + "    "
					+ bookBean.getCarColor() + "    " + bookBean.getCx());
			mFenshu.setText(bookBean.getLevel());
			mQuxiaoLayout.setVisibility(View.GONE);
			mPingjia_layout.setVisibility(View.VISIBLE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.VISIBLE);

			mBtn_text.setText("已评价");
			mBtn_text.setVisibility(View.GONE);
			mFeiyongmingxi.setVisibility(View.VISIBLE);
			setstar(bookBean.getPlResult());
			mBtn_text.setBackgroundResource(R.drawable.yuanjiao_huang);
			mBtn_text.setOnClickListener(null);
			mStar1.setOnClickListener(null);
			mStar2.setOnClickListener(null);
			mStar3.setOnClickListener(null);
			mStar4.setOnClickListener(null);
			mStar5.setOnClickListener(null);
			stNode = PlanNode.withLocation(new LatLng(bookBean
					.getStartLatitude() / 1E6,
					bookBean.getStartLongitude() / 1E6));
			enNode = PlanNode.withLocation(new LatLng(
					bookBean.getEndLatitude() / 1E6,
					bookBean.getEndLongitude() / 1E6));
			// mSearch.drivingSearch((new
			// DrivingRoutePlanOption()).from(stNode).to(enNode));
			// mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new
			// LatLng(bookBean.getStartLatitude() / 1E6,
			// bookBean.getStartLongitude() / 1E6)));
			bar.getRightHomeButton().setText("投诉");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(intent);
				}
			});
			getGuiJi(true);
			break;
		case Config.STATE_YIXIACHE:
			iscar = false;
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mDanshu.setText(bookBean.getCalled_number() + "单");
			bar.getRightHomeButton().setVisibility(View.GONE);
			phones = bookBean.getReplyerPhone();
			bitmapUtils.display(mHead, bookBean.getAvatarImage());
			mName.setText(bookBean.getReplyerName());
			mCar.setText(bookBean.getReplyerNumber() + "    "
					+ bookBean.getCarColor() + "    " + bookBean.getCx());
			mFenshu.setText(bookBean.getLevel());
			mQuxiaoLayout.setVisibility(View.GONE);
			mPingjia_layout.setVisibility(View.GONE);
			mCuikuan_layout.setVisibility(View.VISIBLE);
			mBtn_layout.setVisibility(View.VISIBLE);
			mFeiyongmingxi.setVisibility(View.GONE);
			xiache.setVisibility(View.GONE);// 0428,新增下车
			mBtn_text.setText("马上支付");
			mBtn_text.setBackgroundResource(R.drawable.yuanjiao_hong);
			mBtn_text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(NewBookDetail.this,
							PayActivity.class);
					intent.putExtra("bookId", bookBean.getId().longValue());
					intent.putExtra("price", bookBean.getPrice().intValue());
					intent.putExtra("zhifu", "1");
					// intent.putExtra("price", 100);
					startActivityForResult(intent, TYPE_PAY);
					// showLoadingDialog("");
					// zhifu();
				}
			});
			stNode = PlanNode.withLocation(new LatLng(bookBean
					.getStartLatitude() / 1E6,
					bookBean.getStartLongitude() / 1E6));
			enNode = PlanNode.withLocation(new LatLng(
					bookBean.getEndLatitude() / 1E6,
					bookBean.getEndLongitude() / 1E6));
			// mSearch.drivingSearch((new
			// DrivingRoutePlanOption()).from(stNode).to(enNode));
			// mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new
			// LatLng(bookBean.getStartLatitude() / 1E6,
			// bookBean.getStartLongitude() / 1E6)));
			getGuiJi(true);
			bar.getRightHomeButton().setText("投诉");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(intent);
				}
			});
			break;
		case Config.STATE_YIZHIFU:

			mYuyue_layout.setVisibility(View.VISIBLE);

			mFenshu.setText(bookBean.getLevel());
			mDanshu.setText(bookBean.getCalled_number() + "单");
			bar.getRightHomeButton().setVisibility(View.GONE);
			phones = bookBean.getReplyerPhone();
			bitmapUtils.display(mHead, bookBean.getAvatarImage());
			mName.setText(bookBean.getReplyerName());
			mCar.setText(bookBean.getReplyerNumber() + "    "
					+ bookBean.getCarColor() + "    " + bookBean.getCx());
			try {
				setsamllstar(Integer.parseInt(bookBean.getLevel().substring(0,
						1)));
			} catch (Exception e) {
				e.printStackTrace();
				setsamllstar(0);
			}
			mFenshu.setText(bookBean.getLevel());
			mQuxiaoLayout.setVisibility(View.GONE);
			mPingjia_layout.setVisibility(View.VISIBLE);
			mCuikuan_layout.setVisibility(View.GONE);
			mBtn_layout.setVisibility(View.VISIBLE);
			mFeiyongmingxi.setVisibility(View.GONE);
			xiache.setVisibility(View.GONE);// 0428,新增下车

			if (bookBean.getIsPl() == 0) {

				mYuyue_layout.setVisibility(View.VISIBLE);

				xiache.setVisibility(View.GONE);// 0428,新增下车

				mBtn_text.setText("提交评价");
				mBtn_text.setBackgroundResource(R.drawable.yuanjiao_huang);
				mBtn_text.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (starnum != 0) {
							showLoadingDialog("");
							// putstar(bookid, starnum);
							putstar(bookBean.getId(), starnum);
						} else {
							ToastUtil.show(NewBookDetail.this, "请选择星级后提交");
						}
					}
				});
				mStar1.setOnClickListener(this);
				mStar2.setOnClickListener(this);
				mStar3.setOnClickListener(this);
				mStar4.setOnClickListener(this);
				mStar5.setOnClickListener(this);
			} else {

				mYuyue_layout.setVisibility(View.VISIBLE);

				setstar(bookBean.getPlResult());
				mBtn_text.setText("已评价");
				mBtn_text.setVisibility(View.GONE);
				mFeiyongmingxi.setVisibility(View.VISIBLE);
				mBtn_text.setBackgroundResource(R.drawable.yuanjiao_huang);
				mBtn_text.setOnClickListener(null);
				mStar1.setOnClickListener(null);
				mStar2.setOnClickListener(null);
				mStar3.setOnClickListener(null);
				mStar4.setOnClickListener(null);
				mStar5.setOnClickListener(null);
			}
			stNode = PlanNode.withLocation(new LatLng(bookBean
					.getStartLatitude() / 1E6,
					bookBean.getStartLongitude() / 1E6));
			enNode = PlanNode.withLocation(new LatLng(
					bookBean.getEndLatitude() / 1E6,
					bookBean.getEndLongitude() / 1E6));
			// mSearch.drivingSearch((new
			// DrivingRoutePlanOption()).from(stNode).to(enNode));
			// mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new
			// LatLng(bookBean.getStartLatitude() / 1E6,
			// bookBean.getStartLongitude() / 1E6)));
			bar.getRightHomeButton().setText("投诉");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(intent);
				}
			});
			getGuiJi(true);
			break;
		default:
			break;
		}
		// if (bookBean.getBookType() < 3 && bookBean.getState() !=
		// Config.STATE_YIXIACHE) {
		// mYuyue_layout.setVisibility(View.GONE);
		// }

		if (bookBean.getBookType() < 3
				&& (bookBean.getState() == Config.STATE_YIJIEDAN
				|| bookBean.getState() == Config.STATE_YISHANGCHE
				|| bookBean.getState() == Config.STATE_QUXIAO
				|| bookBean.getState() == Config.STATE_ZHIXINGSHIBAI || bookBean
				.getState() == Config.STATE_DIAODUSHIBAI)) {
			mYuyue_layout.setVisibility(View.GONE);
		} else {
			if (bookBean.getState() == Config.STATE_YISHANGCHE
					|| bookBean.getState() == Config.STATE_YIXIACHE
					|| bookBean.getState() == Config.STATE_YIZHIFU
					|| bookBean.getState() == Config.STATE_YIPINGJIA) {
				mYuyue_layout.setVisibility(View.VISIBLE);
				// String dis =
				// changlength(Integer.parseInt(bookBean.getForecastDistance()));
				mMileage_text.setText(bookBean.getForecastDistance() + "公里");
				mLength_text.setText(bookBean.getTimeLong() + "分钟");

				// 2016-04-19 修改
				// String pric = Utils.getDecimal("#.00", false,
				// bookBean.getPrice() / 100d);
				if (bookBean.getPrice() == -1) {
					mPrice_text.setText("0.00元");

				}else {
					String pric = Utils.getDecimal("0.00", false,bookBean.getPrice() / 100d);
					mPrice_text.setText(pric + "元");
				}

			} else
				// if (bookBean.getState() == Config.STATE_YIJIEDAN)
			{
				mYuyue_layout.setVisibility(View.VISIBLE);
				// String dis1 =
				// changlength(Integer.parseInt(bookBean.getForecastDistance()));
				mMileage_text.setText(bookBean.getForecastDistance() + "公里");
				mLength_text.setText(bookBean.getTimeLong() + "分钟");

				// 2016-04-09修改
				// String pric;
				// pric =
				// changpric(Integer.parseInt(bookBean.getForecastPrice())) +
				// "元";
				// mPrice_text.setText(pric.replace("-", ""));

				// 2016-4-19 修改
				// String ForecastPrice = Utils.getDecimal("#.00", false,
				// Integer.valueOf(bookBean.getForecastPrice()) / 100d);
				String ForecastPrice = Utils.getDecimal("0.00", false,Integer.valueOf(bookBean.getForecastPrice()) / 100d);
				mPrice_text.setText(ForecastPrice + "元");

				// String ForecastPrice = Utils.getDecimal("#.##", false,
				// Integer.valueOf(bookBean.getForecastPrice()) / 100d);
				// double ForecastPrice1 = getNumber(ForecastPrice);
				// mPrice_text.setText(String.format("%.2f", ForecastPrice1) +
				// "元");

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				MyLog.v("", "bookBean.getPrice()--》:" + bookBean.getPrice()
						+ ";mPrice_text--》:" + mPrice_text.getText() + "-----》"
						+ sdf.format(new Date()));
			}
		}
		if (isGone)
			bj.setVisibility(View.GONE);
	}

	private String getreson(String nums) {
		String reson = "";
		int num = Integer.parseInt(nums);
		if (num == 0x01 << 0x0b) {
			reson = Config.resonlist[0];
		} else if (num == 0x01 << 0x0a) {
			reson = Config.resonlist[1];
		} else if (num == 0x01 << 0x09) {
			reson = Config.resonlist[2];
		}
		return reson;
	}

	private void setYuyue() {
		if (bookBean.getBookType() % 2 != 0) {
			mYuyue_layout.setVisibility(View.VISIBLE);
			// carType = "预约打车";
			mMileage_text.setText(bookBean.getForecastDistance() + "公里");
			mLength_text.setText(bookBean.getTimeLong() + "分钟");
			String pric;
			if (bookBean.getState() == Config.STATE_YIJIEDAN) {
				pric = changpric(Integer.parseInt(bookBean.getForecastPrice()))
						+ "元";
			} else {
				pric = changpric(bookBean.getPrice()) + "元";
			}
			mPrice_text.setText(pric.replace("-", ""));
		} else {
			mYuyue_layout.setVisibility(View.GONE);
		}
	}

	private String changlength(int num) {
		DecimalFormat format = new DecimalFormat("#.#");
		if (num < 100) {
			return "0";
		} else {
			return format.format(num / 1000.0);
		}
	}

	@SuppressLint("NewApi")
	private String changpric(int num) {
		if (String.valueOf(num).equals("0")) {
			return "0";
		} else {
			DecimalFormat format = new DecimalFormat("#.00");
			format.setRoundingMode(RoundingMode.DOWN);
			return format.format(num / 100.0);
		}

	}

	protected void callDriver(final String phones) {
		// TODO Auto-generated method stub
		try {
			Window.callTaxi(NewBookDetail.this, phones);
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.show(this, "司机号码有误，请联系客服！");
		}

	}

	private void setstar(int num) {
		switch (num) {
		case 1:
			mStar1.setImageResource(R.drawable.star_down);
			mStar2.setImageResource(R.drawable.star_up);
			mStar3.setImageResource(R.drawable.star_up);
			mStar4.setImageResource(R.drawable.star_up);
			mStar5.setImageResource(R.drawable.star_up);
			starnum = 1;
			break;
		case 2:
			mStar1.setImageResource(R.drawable.star_down);
			mStar2.setImageResource(R.drawable.star_down);
			mStar3.setImageResource(R.drawable.star_up);
			mStar4.setImageResource(R.drawable.star_up);
			mStar5.setImageResource(R.drawable.star_up);
			starnum = 2;
			break;
		case 3:
			mStar1.setImageResource(R.drawable.star_down);
			mStar2.setImageResource(R.drawable.star_down);
			mStar3.setImageResource(R.drawable.star_down);
			mStar4.setImageResource(R.drawable.star_up);
			mStar5.setImageResource(R.drawable.star_up);
			starnum = 3;
			break;
		case 4:
			mStar1.setImageResource(R.drawable.star_down);
			mStar2.setImageResource(R.drawable.star_down);
			mStar3.setImageResource(R.drawable.star_down);
			mStar4.setImageResource(R.drawable.star_down);
			mStar5.setImageResource(R.drawable.star_up);
			starnum = 4;
			break;
		case 5:
			mStar1.setImageResource(R.drawable.star_down);
			mStar2.setImageResource(R.drawable.star_down);
			mStar3.setImageResource(R.drawable.star_down);
			mStar4.setImageResource(R.drawable.star_down);
			mStar5.setImageResource(R.drawable.star_down);
			starnum = 5;
			break;

		default:
			break;
		}
	}

	private void setsamllstar(int num) {
		switch (num) {
		case 0:
			mSmall_star1.setImageResource(R.drawable.small_star_on);
			mSmall_star2.setImageResource(R.drawable.small_star_on);
			mSmall_star3.setImageResource(R.drawable.small_star_on);
			mSmall_star4.setImageResource(R.drawable.small_star_on);
			mSmall_star5.setImageResource(R.drawable.small_star_on);
			break;
		case 1:
			mSmall_star1.setImageResource(R.drawable.small_star_down);
			mSmall_star2.setImageResource(R.drawable.small_star_on);
			mSmall_star3.setImageResource(R.drawable.small_star_on);
			mSmall_star4.setImageResource(R.drawable.small_star_on);
			mSmall_star5.setImageResource(R.drawable.small_star_on);
			break;
		case 2:
			mSmall_star1.setImageResource(R.drawable.small_star_down);
			mSmall_star2.setImageResource(R.drawable.small_star_down);
			mSmall_star3.setImageResource(R.drawable.small_star_on);
			mSmall_star4.setImageResource(R.drawable.small_star_on);
			mSmall_star5.setImageResource(R.drawable.small_star_on);
			break;
		case 3:
			mSmall_star1.setImageResource(R.drawable.small_star_down);
			mSmall_star2.setImageResource(R.drawable.small_star_down);
			mSmall_star3.setImageResource(R.drawable.small_star_down);
			mSmall_star4.setImageResource(R.drawable.small_star_on);
			mSmall_star5.setImageResource(R.drawable.small_star_on);
			break;
		case 4:
			mSmall_star1.setImageResource(R.drawable.small_star_down);
			mSmall_star2.setImageResource(R.drawable.small_star_down);
			mSmall_star3.setImageResource(R.drawable.small_star_down);
			mSmall_star4.setImageResource(R.drawable.small_star_down);
			mSmall_star5.setImageResource(R.drawable.small_star_on);
			break;
		case 5:
			mSmall_star1.setImageResource(R.drawable.small_star_down);
			mSmall_star2.setImageResource(R.drawable.small_star_down);
			mSmall_star3.setImageResource(R.drawable.small_star_down);
			mSmall_star4.setImageResource(R.drawable.small_star_down);
			mSmall_star5.setImageResource(R.drawable.small_star_down);
			break;

		default:
			break;
		}
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
			overlay = new MyWalkingRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	// 定制RouteOverly
	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.track_map2_start);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.track_map2_end);
		}
	}

	// 定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.track_map2_start);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.track_map2_end);
		}
	}

	// 评价
	private void putstar(final long id, int plResult) {
		try {
			JSONObject json = new JSONObject();
			json.put("action", "receivedAction");
			json.put("method", "setComplaintReview");
			json.put("id", id);
			json.put("plResult", plResult);
			AppLog.LogD("yhq", "cancelBook json-->" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json,
					new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JSONObject result = (JSONObject) param;
							AppLog.LogD("yhq", "cancelBook--->"
									+ result.toString());
							if (result.getInt("error") != 0) {
								cancelLoadingDialog();
								ToastUtil.show(NewBookDetail.this,
										"订单评价失败");
							} else {
								ToastUtil.show(NewBookDetail.this,
										"感谢您的评价！");
								// bookid = bookBean.getId();
								getDetail(bookBean.getId(), false);
								ETApp.getInstance()
								.sendBroadcast(
										new Intent(
												cn.com.easytaxi.common.Const.BOOK_STATE_CHANGED_LIST));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						cancelLoadingDialog();
						ToastUtil.show(NewBookDetail.this, "订单评价失败");
					}
				}

				@Override
				public void error(Throwable e) {
					e.printStackTrace();
					cancelLoadingDialog();
					ToastUtil.show(NewBookDetail.this, "订单评价失败");
				}

				@Override
				public void complete() {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取消订单
	 * 
	 * @param id
	 * @param reason
	 *            "与司机协商一致取消", "因我的原因，不用车了", "因司机原因，无法上车"
	 *            0x01<<0x0b,0x01<<0x0a,0x01<<0x09
	 * @return void
	 */
	private void cancelBook(final long id, int reason) {
		try {
			// showDialog(0);
			JSONObject json = new JSONObject();
			json.put("action", "scheduleAction");
			json.put("method", "cancelBookByPassenger");
			json.put("bookId", id);
			json.put("reason", reason);

			json.put("cityId", cn.com.easytaxi.platform.MainActivityNew.cityId);
			json.put("cityName",
					cn.com.easytaxi.platform.MainActivityNew.currentCityName);
			json.put("clientType", BookConfig.ClientType.CLIENT_TYPE_PASSENGER);
			AppLog.LogD("xyw", "cancelBook json-->" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json,
					new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JSONObject result = (JSONObject) param;
							AppLog.LogD("xyw", "cancelBook--->"
									+ result.toString());
							if (result.getInt("error") != 0) {
								cancelLoadingDialog();
								ToastUtil.show(NewBookDetail.this,
										"取消订单失败");
							} else {
								ToastUtil.show(NewBookDetail.this,
										"取消成功");
								getDetail(bookBean.getId(), false);
								// getDetail(bookid, false);
								ETApp.getInstance()
								.sendBroadcast(
										new Intent(
												cn.com.easytaxi.common.Const.BOOK_STATE_CHANGED_LIST));
								if (bookBean.getBookType() % 2 != 0
										&& bookBean.getState() == Config.STATE_DIAODUZHONG) {
									finish();
								}
							}
						}
					} catch (Exception e) {
						cancelLoadingDialog();
						e.printStackTrace();
						ToastUtil.show(NewBookDetail.this, "取消订单失败");
					}
				}

				@Override
				public void error(Throwable e) {
					// TODO Auto-generated method stub
					super.error(e);
					cancelLoadingDialog();
					e.printStackTrace();
					ToastUtil.show(NewBookDetail.this, "取消订单失败");
				}

				@Override
				public void complete() {
					// try {
					// dismissDialog(0);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private View createSuspensionView() {
		LayoutInflater flater = LayoutInflater.from(NewBookDetail.this);
		sus_view = flater.inflate(R.layout.top_layout, null);
		juli = (TextView) sus_view.findViewById(R.id.juli); // 距离
		shijian = (TextView) sus_view.findViewById(R.id.dizhi); // 距离
		return sus_view;
	}

	/**
	 * 假支付
	 * 
	 * @param id
	 * @param reason
	 *            "与司机协商一致取消", "因我的原因，不用车了", "因司机原因，无法上车"
	 *            0x01<<0x0b,0x01<<0x0a,0x01<<0x09
	 * @return void
	 */
	private void zhifu() {
		try {
			// showDialog(0);
			JSONObject json = new JSONObject();
			json.put("action", "receivedAction");
			json.put("method", "goPay");
			json.put("id", bookBean.getId());
			AppLog.LogD("xyw", "cancelBook json-->" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json,
					new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JSONObject result = (JSONObject) param;
							AppLog.LogD("xyw", "cancelBook--->"
									+ result.toString());
							if (result.getInt("error") != 0) {
								cancelLoadingDialog();
								ToastUtil.show(NewBookDetail.this,
										"支付订单失败");
							} else {
								ToastUtil.show(NewBookDetail.this,
										"支付成功");
								ETApp.getInstance()
								.sendBroadcast(
										new Intent(
												cn.com.easytaxi.common.Const.BOOK_STATE_CHANGED_LIST));
							}
						}
					} catch (Exception e) {
						cancelLoadingDialog();
						e.printStackTrace();
						ToastUtil.show(NewBookDetail.this, "支付订单失败");
					}
				}

				@Override
				public void error(Throwable e) {
					// TODO Auto-generated method stub
					super.error(e);
					cancelLoadingDialog();
					e.printStackTrace();
					ToastUtil.show(NewBookDetail.this, "支付订单失败");
				}

				@Override
				public void complete() {
					// try {
					// dismissDialog(0);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void movelatlng(LatLng lat) {
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(lat);
		mBaidumap.animateMapStatus(u);
	}

	private void paintingLine(List<LatLng> points) {
		if (points.size() > 0) {
			if (points.size() == 1) {
				points.add(points.get(0));
			}
			OverlayOptions ooPolyline = new PolylineOptions()
			.color(Color.parseColor("#90acdf")).width(7).points(points);
			mBaidumap.addOverlay(ooPolyline);
		}

	}

	// 轨迹获取
	private void getGuiJi(final boolean isend) {
		try {
			JSONObject json = new JSONObject();
			json.put("action", "trajectoryAction");
			json.put("method", "getTra");
			json.put("book_id", bookBean.getId());
			// json.put("book_id", 8694);
			AppLog.LogD("yhq", "cancelBook json-->" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json,
					new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JsonObject result = XGsonUtil
									.getJsonObject(param.toString());
							int error = result.get("error").getAsInt();
							if (error == 0) {
								mBaidumap.clear();
								JsonArray array = result.get(
										"trajectory").getAsJsonArray();
								List<PointBean> myPoint = XGsonUtil
										.getListFromJson(true, array,
												PointBean.class);
								List<LatLng> myPoints = new ArrayList<LatLng>();
								if (myPoint.size() != 0) {
									for (int i = 0; i < myPoint.size(); i++) {
										myPoints.add(new LatLng(
												myPoint.get(i).getLat() / 1E6,
												myPoint.get(i).getLng() / 1E6));
									}
									paintingLine(myPoints);
									OverlayOptions endpoint;
									if (isend) {
										endpoint = new MarkerOptions()
										.position(
												myPoints.get(myPoints
														.size() - 1))
														.icon(end);
									} else {
										switch ((bookBean.getBookType() + 1) / 2) {
										case 1:
											endpoint = new MarkerOptions()
											.position(
													myPoints.get(myPoints
															.size() - 1))
															.icon(che1)
															.zIndex(5);
											break;
										case 2:
											endpoint = new MarkerOptions()
											.position(
													myPoints.get(myPoints
															.size() - 1))
															.icon(che2)
															.zIndex(5);
											break;
										case 3:
											endpoint = new MarkerOptions()
											.position(
													myPoints.get(myPoints
															.size() - 1))
															.icon(che3)
															.zIndex(5);
											break;
										case 4:
											endpoint = new MarkerOptions()
											.position(
													myPoints.get(myPoints
															.size() - 1))
															.icon(che4)
															.zIndex(5);
											break;

										default:
											endpoint = new MarkerOptions()
											.position(
													myPoints.get(myPoints
															.size() - 1))
															.icon(che)
															.zIndex(5);
											break;
										}
									}
									OverlayOptions startpoint = new MarkerOptions()
									.position(myPoints.get(0))
									.icon(start);
									mBaidumap.addOverlay(startpoint);
									mBaidumap.addOverlay(endpoint);
									movelatlng(myPoints.get(myPoints
											.size() - 1));
								}
								// else {
									// mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new
											// LatLng(bookBean.getStartLatitude() /
													// 1E6,
								// bookBean.getStartLongitude() /
								// 1E6)));
								// }
							}
						} else {
							cancelLoadingDialog();
							ToastUtil
							.show(NewBookDetail.this, "获取轨迹失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
						ToastUtil.show(NewBookDetail.this, "当前没有轨迹");
					}
				}

				@Override
				public void error(Throwable e) {
					e.printStackTrace();
					cancelLoadingDialog();
					ToastUtil.show(NewBookDetail.this, "获取轨迹失败");
				}

				@Override
				public void complete() {
					// mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new
					// LatLng(bookBean.getStartLatitude() / 1E6,
					// bookBean.getStartLongitude() / 1E6)));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取车
	 * 
	 * @param id
	 * @param reason
	 *            "与司机协商一致取消", "因我的原因，不用车了", "因司机原因，无法上车"
	 *            0x01<<0x0b,0x01<<0x0a,0x01<<0x09
	 * @return void
	 */
	private void getCar() {
		try {
			// showDialog(0);
			JSONObject json = new JSONObject();
			json.put("action", "locationAction");
			json.put("method", "getTaxiLocation");
			json.put("taxiId", bookBean.getReplyerId());
			json.put("book_id", bookBean.getId());
			AppLog.LogD("xyw", "getCar json-->" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json,
					new Callback<JSONObject>() {

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JSONObject result = (JSONObject) param;
							AppLog.LogD("xyw","getCar--->" + result.toString());
							if (result.getInt("error") != 0) {
								ToastUtil.show(NewBookDetail.this,"获取司机位置失败");
							} else {
								String shijianString = result.getString("time");
								switch (bookBean.getState()) {
								case Config.STATE_YIJIEDAN:
									createSuspensionView();
									mBaidumap.clear();
									// PlanNode stNode;
									// PlanNode enNode;
									// stNode =PlanNode.withLocation(new LatLng(bookBean.getStartLatitude() / 1E6, bookBean.getStartLongitude() / 1E6));
									// enNode = PlanNode.withLocation(new LatLng(bookBean.getEndLatitude() / 1E6,bookBean.getEndLongitude() / 1E6));
									// mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode));
									String juliString = result.getString("mileage");
									long lat = result.getLong("latitude");
									long lng = result.getLong("longitude");
									if (lat != 0) {
										OverlayOptions carOptions = null;
										switch ((bookBean.getBookType() + 1) / 2) {
										case 1:
											carOptions = new MarkerOptions()
											.position(new LatLng(lat / 1E6,lng / 1E6))
											.icon(che1)
											.zIndex(5);
											break;
										case 2:
											carOptions = new MarkerOptions()
											.position(new LatLng(lat / 1E6,lng / 1E6))
											.icon(che2)
											.zIndex(5);
											break;
										case 3:
											carOptions = new MarkerOptions()
											.position(new LatLng(lat / 1E6,lng / 1E6))
											.icon(che3)
											.zIndex(5);
											break;
										case 4:
											carOptions = new MarkerOptions()
											.position(new LatLng(lat / 1E6,lng / 1E6))
											.icon(che4)
											.zIndex(5);
											break;

										default:
											carOptions = new MarkerOptions()
											.position(new LatLng(lat / 1E6,lng / 1E6))
											.icon(che)
											.zIndex(5);
											break;
										}
										if (juliString.equals("0")|| Double.parseDouble(juliString) < 1) {
											juli.setText("小于1千米");
											shijian.setText("即刻到达");
											mBaidumap.addOverlay(carOptions);
										} else {
											juli.setText("司机离我还有"+ juliString + "千米");
											shijian.setText("司机还有"+ shijianString+ "分钟左右到达");
											mBaidumap.addOverlay(carOptions);
										}
									} else {
										juli.setText("正在获取司机位置");
										shijian.setText("");
									}
									handler.postDelayed(new Runnable() {

										@Override
										public void run() {
											if (bookBean.getState() == Config.STATE_YIJIEDAN) {
												OverlayOptions ooA = new MarkerOptions()
												.position(new LatLng(bookBean.getStartLatitude() / 1E6,bookBean.getStartLongitude() / 1E6))
												.icon(start)
												.zIndex(9);
												mBaidumap.addOverlay(ooA);
												mInfoWindow = new InfoWindow(sus_view,new LatLng(bookBean.getStartLatitude() / 1E6,bookBean.getStartLongitude() / 1E6),0);
												mBaidumap.showInfoWindow(mInfoWindow);
												mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(bookBean.getStartLatitude() / 1E6,bookBean.getStartLongitude() / 1E6)));
											}
										}
									}, 2000);
									break;
								case Config.STATE_YISHANGCHE:
									Log.i("NewBookDetail","STATE_YISHANGCHE=====>result="+ result.toString());
									String xingjin = result.getString("mileageing");
									String jiageString = result.getString("price");
									String fenzhongString = result.getString("mobile");
									// String xinj = changlength(Integer.parseInt(xingjin));

									// int xingjin = result.getInt("mileageing");
									// String xinj = changlength(Integer.parseInt(xingjin));

									mMileage_text.setText(xingjin+ "公里");
									mLength_text.setText(fenzhongString+ "分钟");
									// String price =
									// Utils.getDecimal("#.00", false,Integer.valueOf(jiageString) / 100d);
									String price = Utils.getDecimal("0.00",false,Integer.valueOf(jiageString) / 100d);
									// double price1 = getNumber(price);
									// String price = Utils.getDecimal("#.##", false,Integer.valueOf(jiageString) / 100d);
									// double price1 = getNumber(price);
									mPrice_text.setText(price + "元");
									break;
								default:
									break;
								}

							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						// ToastUtil.show(NewBookDetail.this,
						// "获取司机位置失败");
					}
				}

				@Override
				public void error(Throwable e) {
					// TODO Auto-generated method stub
					super.error(e);
					e.printStackTrace();
					ToastUtil.show(NewBookDetail.this, "获取司机位置失败");
				}

				@Override
				public void complete() {
					// try {
					// dismissDialog(0);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == TYPE_PAY) {
			int type = data.getIntExtra("type", -1);

			switch (type) {
			case PayActivity.PAY_TYPE_WEIXIN:
				int error = data.getIntExtra("error", -1);
				if (error == BaseResp.ErrCode.ERR_OK && bookBean != null) {
					// 支付成功
					bookBean.setState(Config.STATE_YIZHIFU);
					setDetail(true);
				} else {
					// 支付失败
				}

				break;
			case PayActivity.PAY_TYPE_ZHIFUBAO:
				int error1 = data.getIntExtra("error", -1);
				if (error1 == 1 && bookBean != null) {
					// 支付成功
					bookBean.setState(Config.STATE_YIZHIFU);
					setDetail(true);
				} else {
					// 支付失败
				}

				break;
			case PayActivity.PAY_TYPE_XIANJIN:
				int error2 = data.getIntExtra("error", -1);
				if (error2 == 1 && bookBean != null) {
					// 支付成功
					bookBean.setState(Config.STATE_YIZHIFU);
					setDetail(true);
				} else {
					// 支付失败
				}
				break;
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		iscar = false;
		super.onDestroy();
	}

	private void feiyongmingxi() {
		mFeiyongmingxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewBookDetail.this,
						FeiYongMingXiActivity.class);
				intent.putExtra("bookid", bookBean.getId());
				startActivity(intent);
			}
		});

	}

	private double getNumber(String et) {
		try {
			return Double.parseDouble(change(et));
		} catch (Exception e) {
		}
		return 0;
	}

	private String change(String str) {
		if (str.equals("")) {
			return "0";
		} else if (str.startsWith(".")) {
			return "0" + str;
		} else {
			return str;
		}
	}
	private void getxiache() {
		showLoadingDialog("");
		try {     
			JSONObject param = new JSONObject();
			param.put("action", "receivedAction");
			param.put("method", "getOffByPassenger");
			param.put("bookId", bookBean.getId());
			param.put("address", bookBean.getEndAddress());
			param.put("latitude", bookBean.getEndLatitude());
			param.put("longitude", bookBean.getEndLongitude());
			XTCPUtil.send(param, new XTCPUtil.XNetCallback() {
				@Override
				public void onSuc(String result) {
					cancelLoadingDialog();
					try {
						JSONObject object = new JSONObject(result);
						int error = object.getInt("error");
						if (error == 0) {
							Intent intent = new Intent(NewBookDetail.this,
									PayActivity.class);
							intent.putExtra("bookId", bookBean.getId().longValue());
							intent.putExtra("price", 0);
							intent.putExtra("zhifu", "2");//支付区分
							startActivityForResult(intent, TYPE_PAY);
						}else {
							ToastUtil.show(NewBookDetail.this, object.getString("errormsg"));

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				@Override
				public void onStart() {

				}
				@Override
				public void onComplete() {

				}
				@Override
				public void error(Throwable e, int errorcode) {
					cancelLoadingDialog();
				}
			});
		} catch (Exception e) {
			cancelLoadingDialog();
			e.printStackTrace();
		}
	}
}
