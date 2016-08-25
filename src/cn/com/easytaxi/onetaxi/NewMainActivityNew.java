package cn.com.easytaxi.onetaxi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.BookConfig;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.NewNetworkRequest;
import cn.com.easytaxi.book.NewBookDetail;
import cn.com.easytaxi.client.channel.TcpClient;
import cn.com.easytaxi.client.common.MsgConst;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.NetChecker;
import cn.com.easytaxi.common.ToolUtil;
import cn.com.easytaxi.common.User;
import cn.com.easytaxi.dialog.CommonDialog;
import cn.com.easytaxi.dialog.DiaoduCancelDialog;
import cn.com.easytaxi.dialog.DiaoduCancelDialog.DialoAction;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.expandable.Child;
import cn.com.easytaxi.expandable.Group;
import cn.com.easytaxi.onetaxi.common.BookBean;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.platform.service.EasyTaxiCmd;
import cn.com.easytaxi.platform.service.OneBookService;
import cn.com.easytaxi.receiver.LocationBroadcastReceiver.LocationReceive;
import cn.com.easytaxi.ui.BookFragementActivity;
import cn.com.easytaxi.ui.BookPublishFragement;
import cn.com.easytaxi.ui.CarSelectContoller;
import cn.com.easytaxi.util.InfoConfig;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.XTCPUtil;
import cn.com.easytaxi.util.YdLoginUtil;
import passenger.constant.Constant;
import passenger.utils.ACache;
import passenger.utils.ApiClient;
import shane_library.shane.utils.VolleyListener;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.aiton.yc.client.R;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.umeng.socialize.utils.Log;

public class NewMainActivityNew extends YdLocaionBaseActivity implements OnClickListener, OnCheckedChangeListener {
	private int where_no_end = 1;
	private int where_end = 2;
	private int where = where_no_end;// 进行到哪一步了

	private final static int SELECT_ENDADDR = 100;
	private final static int SELECT_STARTADDR = 101;
	private boolean show = false;
	private long currentBookId = 0;
	private String mobile;// 本机电话号码
	public static final int GPS_DLG = 100;
	private BitmapDescriptor che1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_car1_map);
	private BitmapDescriptor che2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_car2_map);
	private BitmapDescriptor che3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_car4_map);
	private BitmapDescriptor che4 = BitmapDescriptorFactory.fromResource(R.drawable.icon_car3_map);
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private TitleBar bar;
	private TextView mDizhi;
	private ImageButton mquxiao;
	private TextView progress_info;
	private RelativeLayout progressBar_layout;
	private View submitLayout;
	private TextView timeTxt;
	private LinearLayout linearLayout1;
	private LinearLayout relativeLayout;
	private LinearLayout top_layout;
	private ImageView dingwei;
	private ImageView topimg;
	private long nearByCarRefresInterval = InfoConfig.NEARBY_TAXI_REFRESH_INTERVAL;
	public boolean isDestroy = false;
	protected long currentTaxiId;
	private ProgressDialog mProgressDialog;
	private Callback<JSONObject> taxiLocationCallback = null;
	protected String soundPath;
	private static final String SOUND_FILE_NAME = "/audio_huang.wav";
	public static final String SOUND_FILE_NAME_spx = "voice_44.wav";
	protected Animation anim;
	private ProgressBar progress_time;
	private BroadcastReceiver onBookAction;
	private boolean isCanBook = true;
	public static final int BOOK_STAT_NORMAL = 0; // 没有发起订单的正常状态
	private int carstate = 0;
	private int startLatitude;
	private int startLongitude;
	private String startCity;
	private int endLatitude;
	private int endLongitude;
	private GeoCoder coder; // 坐标地址解析
	private String jiedao = "";
	private MyUserInfo myuserinfo;// 左边的个人信息
	private String biaozhi = "";
	private TextView biaozhiTextView;
	private int yugutyp = 1;
	private String forecastDistance;
	private View where_no_end_view;
	private RelativeLayout end_address_layout;
	private RelativeLayout start_address_layout;
	private View mashangLayout;// 马上用车按钮
	private TextView msPrice;// 马上用车的（预估价）
	private TextView start_address;
	private TextView end_address;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.arg1 == 1) {
				requestNearbyTaxi(yugutyp * 2, new NearByCallbacK());
			}
			if (msg.arg1 == 2) {
				AppLog.LogD("refresh locaion car");
				taxiLocationCallback = new TaxiLocationCallback();
				requestTaxiLocation(currentTaxiId, taxiLocationCallback);
			}

			if (msg.arg1 == 3) {
				BookBean bk = ETApp.getInstance().getCacheBookbean();
				if (bk == null) {
					dispatchStat(4);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date nowtime;
					Date usetime;
					try {
						show = true;
						startLatitude = bk.getStartLatitude();
						startLongitude = bk.getStartLongitude();
						startCity = bk.getCityName();
						endLatitude = bk.getEndLatitude();
						endLongitude = bk.getEndLongitude();
						biaozhiTextView.setText(bk.getStartAddress());
						start_address.setText(bk.getStartAddress());
						end_address.setText(bk.getEndAddress());
						nowtime = sdf.parse(bk.getCurrentDate());
						usetime = sdf.parse(bk.getUseTime());
						long nowTime = nowtime.getTime();
						long useTime = usetime.getTime();
						int time = (int) (120000 - (useTime - nowTime));
						if (time > 120000) {
							bk.setProgress(119000);
						} else {
							bk.setProgress(time);
						}
					} catch (ParseException e) {
						bk.setProgress(119000);
						e.printStackTrace();
					}
					dispatchStat(Config.STATE_DIAODUZHONG);
					if (bk.getBookType() % 2 == 0) {
						bk.startWait();
					}
				}
			}
		};
	};

	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		if (myuserinfo != null)
			myuserinfo.onResume();
	};

	private CarSelectContoller carcontoller;// 车型选择控制器

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// if (MainService.udpChannelService != null) {
		// MainService.udpChannelService.sendMessage(new
		// SendMsgBean(Const.UDP_LOCATION_UP, new byte[24]));
		// SysDeug.logD("发送一条信息到服务器");
		// }
		setContentView(R.layout.onetaxi_new_main);
		initViews();
		//getispay();
		mProgressDialog = new ProgressDialog(NewMainActivityNew.this);
		mProgressDialog.setMessage("请稍后...");
		initSystemConfig();
		mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
		mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
		coder = GeoCoder.newInstance();
		// 解析结果回调
		coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				// 坐标转地址
				try {
					String biaozhi1 = result.getPoiList().get(0).name;
					String jiedao1 = result.getAddressDetail().street;
					jiedao = jiedao1;
					biaozhi = biaozhi1;
				} catch (Exception e) {
					jiedao = result.getAddress();
					biaozhi = "";
				}
				if (TextUtils.isEmpty(jiedao) || jiedao.contains("null")) {
					jiedao = "滑动地图获取位置";
					biaozhi = "";
				}
				mDizhi.setText(jiedao);
				biaozhiTextView.setText(biaozhi + "上车");
				try {
					startCity = result.getAddressDetail().city;
					startLatitude = (int) (result.getLocation().latitude * 1E6);
					startLongitude = (int) (result.getLocation().longitude * 1E6);
					start_address.setText(biaozhi);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (show) {
					yugu();
				}
			}

			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				System.out.println("onGetGeoCodeResult:" + result.getAddress());

			}
		});
		regReceiver();
		new LoadBooks().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		if (getCurrentlat() == 0) {
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(getCurrentlat() / 1E6, getCurrentLng() / 1E6)));
			startLatitude = getCurrentlat();
			startLongitude = getCurrentLng();
		}
		setLocReveive(new LocationReceive() {

			@Override
			public void onLocReveive(int lat, int lng, String city) {
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(lat / 1E6, lng / 1E6)));
				startLatitude = lat;
				startLongitude = lng;
				startCity = city;
				ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
				ReverseGeoCodeOption result = reverseCode.location(new LatLng(lat / 1E6, lng / 1E6));
				coder.reverseGeoCode(result);
				if (TextUtils.isEmpty(jiedao) || jiedao.contains("null")) {
					jiedao = "滑动地图获取位置";
					biaozhi = "";
				}
				mDizhi.setText(jiedao);
				biaozhiTextView.setText(biaozhi + "上车");
				setLocReveive(null);
			}
		});
		if (getCurrentlat() != 0 && getCurrentLng() != 0) {
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(getCurrentlat() / 1E6, getCurrentLng() / 1E6)));
			startLatitude = getCurrentlat();
			startLongitude = getCurrentLng();
			ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
			ReverseGeoCodeOption result = reverseCode.location(new LatLng(getCurrentlat() / 1E6, getCurrentLng() / 1E6));
			coder.reverseGeoCode(result);
			if (TextUtils.isEmpty(jiedao) || jiedao.contains("null")) {
				jiedao = "滑动地图获取位置";
				biaozhi = "";
			}
			mDizhi.setText(jiedao);
			biaozhiTextView.setText(biaozhi + "上车");
		}
	}

	OnMapStatusChangeListener onMapStatusChangeListener = new OnMapStatusChangeListener() {
		LatLng startLng, finishLng;

		@Override
		public void onMapStatusChangeStart(MapStatus mapStatus) {
			startLng = mapStatus.target;
		}

		@Override
		public void onMapStatusChangeFinish(MapStatus mapStatus) {
			// AppLog.LogD("mapStatus" + mapStatus.);
			// 滑动搜索
			requestNearbyTaxi(yugutyp * 2, new NearByCallbacK());
			finishLng = mapStatus.target;
			if (startLng.latitude != finishLng.latitude || startLng.longitude != finishLng.longitude) {
				Projection ject = mBaiduMap.getProjection();
				Point startPoint = ject.toScreenLocation(startLng);
				Point finishPoint = ject.toScreenLocation(finishLng);
				double x = Math.abs(finishPoint.x - startPoint.x);
				double y = Math.abs(finishPoint.y - startPoint.y);
				if (x > 1 || y > 1) {
					// showLoadingDialog("正在获取该地址,请稍等...");
					startLatitude = (int) (mapStatus.target.latitude * 1E6);
					startLongitude = (int) (mapStatus.target.longitude * 1E6);
					ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
					ReverseGeoCodeOption result = reverseCode.location(mapStatus.target);
					coder.reverseGeoCode(result);
				}
			}
		}

		@Override
		public void onMapStatusChange(MapStatus mapStatus) {
		}
	};

	/**
	 * 1、检测gps是否打开； 2、初始化声音 3、获取初始其实地址（）；
	 * 
	 */
	private void initSystemConfig() {
		checkNetwork();
		checkGps();
		initSound();
		String dir = ETApp.getInstance().getMobileInfo().getSDCardPath();
		soundPath = dir + SOUND_FILE_NAME;
		anim = AnimationUtils.loadAnimation(NewMainActivityNew.this, R.anim.up_enter);
	}

	private void checkNetwork() {
		if (!new NetChecker(NewMainActivityNew.this).checkNetwork()) {
			ToastUtil.show(NewMainActivityNew.this, R.string.network_notgood);
		}
	}

	private void checkGps() {
		if (ETApp.getInstance().getMobileInfo().isGpgOpened()) {
		} else {
			if (ETApp.getInstance().getCacheInt("gps_no") == 1) {

			} else {
				showDialog(GPS_DLG);
			}
		}
	}

	@Override
	protected void initViews() {
		end_address_layout = (RelativeLayout) findViewById(R.id.end_address_layout);
		end_address_layout.setOnClickListener(this);
		start_address_layout = (RelativeLayout) findViewById(R.id.start_address_layout);
		start_address_layout.setOnClickListener(this);
		start_address = (TextView) findViewById(R.id.start_address);
		end_address = (TextView) findViewById(R.id.end_address);
		bar = new TitleBar(NewMainActivityNew.this);
		bar.setTitleName("");
		// bar.setTitleName("即时叫车");
		// bar.switchToCityButton();
		// bar.getRightCityButton().setVisibility(View.GONE);
		bar.getRightHomeButton().setVisibility(View.VISIBLE);
		bar.getRightHomeButton().setText("预约");
		bar.getLeftImageButton().setImageResource(R.drawable.top_back);
		bar.getLeftImageButton().setBackgroundResource(0);
		bar.setBackCallback(new Callback<Object>() {
			@Override
			public void handle(Object param) {
				// doBack();
				if (bar.getLeftImageButton().isShown()) {
					finish();
				}
			}
		});
		mashangLayout = findViewById(R.id.mashangLayout);
		msPrice = (TextView) findViewById(R.id.yuguprice);
		mashangLayout.setOnClickListener(this);
		where_no_end_view = findViewById(R.id.gowhere);

		bar.getRightHomeButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 预约订单
				if (YdLoginUtil.isLogin(NewMainActivityNew.this)) {
					Intent intent = new Intent(NewMainActivityNew.this, BookFragementActivity.class);
					intent.putExtra("startLat", startLatitude);
					intent.putExtra("startLng", startLongitude);
					intent.putExtra("carType", yugutyp);
					intent.putExtra("city", startCity);
					intent.putExtra("address", start_address.getText().toString().trim());
					startActivity(intent);
				}
			}
		});
		// bar.setRightCallback(new Callback<Object>() {
		//
		// @Override
		// public void handle(Object param) {
		// // 预约订单
		// if (YdLoginUtil.isLogin(NewMainActivityNew.this)) {
		// Intent intent = new Intent(NewMainActivityNew.this,
		// BookFragementActivity.class);
		// intent.putExtra("startLat", startLatitude);
		// intent.putExtra("startLng", startLongitude);
		// intent.putExtra("address",
		// startTextView.getText().toString().trim());
		// startActivity(intent);
		// }
		// }
		// });
		carcontoller = new CarSelectContoller(this);
		carcontoller.setOnCheckedChangeListener(this);
		myuserinfo = new MyUserInfo(this, (ViewGroup) findViewById(R.id.viewgroup));
		mquxiao = (ImageButton) findViewById(R.id.quxiao);
		mquxiao.setOnClickListener(this);
		mDizhi = (TextView) findViewById(R.id.dizhi);
		progress_info = (TextView) findViewById(R.id.progress_info);
		progress_time = (ProgressBar) findViewById(R.id.progress_time);
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		relativeLayout = (LinearLayout) findViewById(R.id.relativeLayout);
		relativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		progressBar_layout = (RelativeLayout) findViewById(R.id.progressBar_layout);
		submitLayout = findViewById(R.id.submitLayout);
		top_layout = (LinearLayout) findViewById(R.id.top_layout);
		topimg = (ImageView) findViewById(R.id.top_img);
		biaozhiTextView = (TextView) findViewById(R.id.biaozhi);
		timeTxt = (TextView) findViewById(R.id.time_txt);
		// top_layout.setOnClickListener(this);
//		carstate = 2;
//		yugutyp = 1;  //20160705修改
		
		carstate = 4;
		yugutyp = 2;
		
		dingwei = (ImageView) findViewById(R.id.dingwei);
		dingwei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (getCurrentlat() != 0) {
						mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(getCurrentlat() / 1E6, getCurrentLng() / 1E6)));
						startLatitude = getCurrentlat();
						startLongitude = getCurrentLng();
						ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
						ReverseGeoCodeOption result = reverseCode.location(new LatLng(getCurrentlat() / 1E6, getCurrentLng() / 1E6));
						coder.reverseGeoCode(result);
						mDizhi.setText(jiedao);
						biaozhiTextView.setText(biaozhi + "上车");
						start_address.setText(biaozhi);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		progressBar_layout.setVisibility(View.GONE);
		submitLayout.setVisibility(View.VISIBLE);
		linearLayout1.setVisibility(View.GONE);
		requestNearbyTaxi(yugutyp * 2, new NearByCallbacK());
	}

	@Override
	protected void onDestroy() {
		try {
			che1.recycle();
			che2.recycle();
			che3.recycle();
			che4.recycle();
			ETApp.getInstance().getCacheBookbean().stopWait();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (myuserinfo != null)
			myuserinfo.onDestory();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (myuserinfo != null)
			myuserinfo.onResume();
		super.onResume();
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
		if (onBookAction == null) {
			onBookAction = new OnBookActionReceiver();
			registerReceiver(onBookAction, new IntentFilter(EasyTaxiCmd.ONE_TAXI_BOOK_MAIN_CMD_RESP));
		}
	}

	@Override
	protected void unRegReceiver() {
		// TODO Auto-generated method stub
		if (onBookAction != null) {
			unregisterReceiver(onBookAction);
		}
	}

	public class NearByCallbacK extends Callback<Object> {
		@Override
		public void handle(Object param) {
			if (param != null) {
				try {
					JSONObject json = (JSONObject) param;
					if (json.isNull("interval")) {
						nearByCarRefresInterval = InfoConfig.NEARBY_TAXI_REFRESH_INTERVAL;
					} else {
						nearByCarRefresInterval = ((JSONObject) param).getLong("interval");
					}
					JSONArray array = ((JSONObject) param).getJSONArray("taxis");
					if (isDestroy) {
						return;
					} else {
						mBaiduMap.clear();
						final int len = array.length();
						for (int i = 0; i < len; i++) {
							final JSONObject object = (JSONObject) array.get(i);
							final int carLat = object.getInt("lat");
							final int carLng = object.getInt("lng");
							final int state = object.getInt("state");
							if (state == 0) {
								OverlayOptions carOptions = null;
								switch (carstate) {
								case 2:
									carOptions = new MarkerOptions().position(new LatLng(carLat / 1E6, carLng / 1E6)).icon(che1).zIndex(5);
									break;
								case 4:
									carOptions = new MarkerOptions().position(new LatLng(carLat / 1E6, carLng / 1E6)).icon(che2).zIndex(5);
									break;
								case 6:
									carOptions = new MarkerOptions().position(new LatLng(carLat / 1E6, carLng / 1E6)).icon(che3).zIndex(5);
									break;
								case 8:
									carOptions = new MarkerOptions().position(new LatLng(carLat / 1E6, carLng / 1E6)).icon(che4).zIndex(5);
									break;

								default:
									break;
								}
								mBaiduMap.addOverlay(carOptions);
							}
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			long interval = nearByCarRefresInterval;

		}
	}

	public class TaxiLocationCallback extends Callback<JSONObject> {

		@Override
		public void handle(JSONObject param) {
			if (param != null) {
				try {
					int latitude = param.getInt("latitude");
					int longitude = param.getInt("longitude");
					BookBean bookBean = ETApp.getInstance().getCacheBookbean();
					if (latitude != 0 && longitude != 0) {

					}

				} catch (Exception e) {

				}

			}

		}
	}

	public class OnBookActionReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int cmd = intent.getIntExtra(EasyTaxiCmd.ONE_TAXI_BOOK_MAIN_SUB_CMD_RESP, 0);
			switch (cmd) {
			// 订单提交成功
			case EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_SUBMIT_RESP_OK:
				cancelLoadingDialog();
				BookBean bk = ETApp.getInstance().getCacheBookbean();
				currentBookId = bk.getId();
				dispatchStat(Config.STATE_DIAODUZHONG);
				startWait(currentBookId, bk.getTimeOut());
				
				/**
				 * 传机构号和订单号给后台
				 */
				
				ACache aCache = ACache.get(NewMainActivityNew.this);
				String jigouhao = aCache.getAsString(Constant.AcacheKey.JIGOUHAO);
				Map<String, String> params = new HashMap<String, String>();
				params.put("bookId", currentBookId+"");
				params.put("code", jigouhao);
				Log.e("commit", "订单号"+currentBookId);
				Log.e("commit", "机构号"+jigouhao);
				ApiClient.chuanDacheOrderJigouhao(NewMainActivityNew.this, params, new VolleyListener() {
					
					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub
						Log.e("comit", "向后台提交订单号和机构号成功"+s);
					}
					
					@Override
					public void onErrorResponse(VolleyError s) {
						// TODO Auto-generated method stub
						Log.e("commit", "向后台传订单号和机构号失败"+s);
						
					}
				});
				break;

				// 订单提交失败
			case EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_SUBMIT_RESP_FAILED:
				// playSound(R.raw.playend, true, null, null);
				cancelLoadingDialog();
				// dispatchStat(Config.STATE_DIAODUSHIBAI);
				break;

				// 订单提交成功 等待司机接单中...
			case EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_SUBMIT_RESP_WAITTING:
				// AppLog.LogD("progress :  " + intent.getIntExtra("progress",
				// 0));
				cancelLoadingDialog();
				dispatchStat(Config.STATE_DIAODUZHONG);
				BookBean bk1 = ETApp.getInstance().getCacheBookbean();
				currentBookId = bk1.getId();
				if (intent.getLongExtra("bookId", currentBookId) == currentBookId) {
					int progress = intent.getIntExtra("progress", 0);
					progress_time.setProgress(progress);
					timeTxt.setText(getTimeForMills(progress));
				}
				break;

				// 订单提交后一直等待司机接单，但是由于没人接单，到时订单超时
			case EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_SUBMIT_RESP_TIMEOUT:
				if (intent.getLongExtra("bookId", currentBookId) == currentBookId) {
					// AppLog.LogD(tag, "订单超时了 需要用户加价重发" + currentBookId);
					progress_time.setProgress(0);
					timeTxt.setText("");
					cancelImmediateBook();
					playSound(R.raw.playend, true, null, null);
					// ETApp.getInstance().getCacheBookbean().stopWait();
					dialog();
				}
				break;

				// 订单提交后 ，等待司机接单，司机接成功接单后...
			case EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_SUBMIT_RESP_JIEDAN_OK:
				BookBean okBookbean = (BookBean) intent.getSerializableExtra("bookbean");
				if (okBookbean == null) {
					okBookbean = ETApp.getInstance().getCacheBookbean();
				}
				okBookbean.stopWait();
				dispatchStat(Config.STATE_YIJIEDAN);

				break;

				// 订单在提交，等待，接单等过程中返回的UDP消息
			case EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_UDP_RESP:
				int msgId = intent.getIntExtra("msgId", 0);
				byte[] message = intent.getByteArrayExtra("message");
				handleUdp(msgId, message);
				break;
			default:
				break;
			}

		}

	}

	private void dialog() {
		CommonDialog dialog = MyDialog.comfirm(NewMainActivityNew.this, "温馨提示", "订单已超时，是否重新提交订单？", new SureCallback() {
			@Override
			public void onClick(int tag) {
				if (tag == LEFT) {
					dispatchStat(Config.STATE_QUXIAO);
				} else {
					if (jiedao.equals("滑动地图获取位置")) {
						dispatchStat(Config.STATE_QUXIAO);
						ToastUtil.show(NewMainActivityNew.this, jiedao);
					} else {
						if (jiedao.startsWith("null") || jiedao.equals("")) {
							ToastUtil.show(NewMainActivityNew.this, "滑动地图获取位置");
						} else {
							startSendBookService(false, "");
						}
					}
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

	private String getTimeForMills(int time) {
		int times = time / 1000;
		int min = times / 60;
		int ms = times % 60;
		return min + "分" + (ms < 10 ? "0" + ms : String.valueOf(ms)) + "秒";
	}

	private void dispatchStat(int stat) {
		AppLog.LogD("xyw", "dispatchStat stat ---=========------- " + stat);
		BookBean bk = ETApp.getInstance().getCacheBookbean();
		if (bk != null) {
			bk.setDispStat(stat);
			AppLog.LogD(bk.toString());
		}
		if (stat == Config.STATE_DIAODUZHONG) {
			bar.getRightHomeButton().setVisibility(View.GONE);
			bar.getLeftImageButton().setVisibility(View.GONE);
		} else {
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.getLeftImageButton().setVisibility(View.VISIBLE);
		}
		// currentStat = stat;
		switch (stat) {
		// 一般情况 ，改情况发生于，第一次提交订单，或者某订单提交成功后，用户取消该订单，则进入该状态
		case Config.STATE_QUXIAO:
			setback(false);
			requestNearbyTaxi(yugutyp * 2, new NearByCallbacK());
			progressBar_layout.setVisibility(View.GONE);
			submitLayout.setVisibility(View.VISIBLE);
			top_layout.setVisibility(View.VISIBLE);
			topimg.setVisibility(View.VISIBLE);
//			carstate = 2;
//			yugutyp = 1;//0705修改
			
//			carstate = 4;
//			yugutyp = 2;
			
			carcontoller.setVisibility(View.VISIBLE);//0520修改只有出租车
			show = false;
			where = where_no_end;
			end_address.setText("");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			clearCheck();
			break;

			// 订单提交成功，等待各种回应中... waitting
		case Config.STATE_DIAODUZHONG:
			if (bk.getBookType() % 2 == 0) {
				if (bk != null) {
					int timeout = (int) bk.getTimeOut();
					progress_time.setMax(timeout);
					timeTxt.setText(getTimeForMills(timeout));
					progress_info.setText(bk.getUdp003Info());
				}
				progressBar_layout.setVisibility(View.VISIBLE);
				submitLayout.setVisibility(View.GONE);
				progress_time.setVisibility(View.VISIBLE);
				linearLayout1.setVisibility(View.VISIBLE);
				mquxiao.setVisibility(View.VISIBLE);
				carcontoller.setVisibility(View.GONE);
				top_layout.setVisibility(View.INVISIBLE);
				topimg.setVisibility(View.INVISIBLE);
				show = false;
			}
			bar.getRightHomeButton().setVisibility(View.GONE);
			break;

			// 司机成功接单...
		case Config.STATE_YIJIEDAN:
			BookBean bookBean = ETApp.getInstance().getCacheBookbean();
			if (bookBean != null) {
				// 新版，直接进入详情
				boolean isShow = true;
				if (isShow) {
					// 由下一步更改为跳转到详情页面
					Intent intent = new Intent(NewMainActivityNew.this, NewBookDetail.class);
					intent.putExtra("bookId", bookBean.getId());
					intent.putExtra("1", 1);
					NewMainActivityNew.this.startActivity(intent);
					dispatchStat(Config.STATE_QUXIAO);
				}
			}
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			break;
			// 订单等待回应后，订单超时了..
		case Config.STATE_DIAODUSHIBAI:
			progress_info.setText("暂无空车下单");
			progress_time.setProgress(0);
			timeTxt.setText("");
			progressBar_layout.setVisibility(View.VISIBLE);
			submitLayout.setVisibility(View.GONE);
			progress_time.setVisibility(View.GONE);
			linearLayout1.setVisibility(View.GONE);
			mquxiao.setVisibility(View.GONE);
			carcontoller.setVisibility(View.GONE);
			top_layout.setVisibility(View.VISIBLE);
			topimg.setVisibility(View.VISIBLE);
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

		// progress_time.setProgress(0);
	}

	/**
	 * 开始发送订单 给service
	 * 
	 * @param isSound
	 */
	private void startSendBookService(boolean isSound, String carType) {
		showLoadingDialog("");
		User user = ETApp.getInstance().getCurrentUser();
		Intent intent = new Intent(this, OneBookService.class);
		intent.setAction(EasyTaxiCmd.ONE_TAXI_BOOK_MAIN_CMD);
		intent.putExtra(EasyTaxiCmd.ONE_TAXI_BOOK_MAIN_SUB_CMD_REQ, EasyTaxiCmd.ONE_TAXI_BOOK_SUB_CMD_SUBMIT_REQ);
		mobile = user.getPhoneNumber("_MOBILE");
		BookBean bb = new BookBean();
		bb.setPassengerPhone(user.getPhoneNumber("_MOBILE")); // [必填]*
		bb.setPassengerName(StringUtils.isEmpty(user.getUserNickName()) ? mobile : user.getUserNickName());
		bb.setPassengerId(user.getPassengerId() > 10000 ? user.getPassengerId() : Long.valueOf(mobile));
		bb.setType(carstate);// [可选]订单类型
		bb.setBookType(carstate);
		bb.setSource(1);
		bb.setSourceName(BookConfig.ClientType.CLIENT_TYPE_PASSENGER + ETApp.getInstance().getMobileInfo().getVerisonCode());
		bb.setStartAddress(start_address.getText().toString());// startAddress为gps定位地址
		// extraAddress为用户添加的地址详细描述
		bb.setStartLatitude(startLatitude);
		bb.setStartLongitude(startLongitude);
		bb.setEndLatitude(endLatitude);
		bb.setEndLongitude(endLongitude);
		bb.setForecastPrice(((int) (Double.parseDouble(yuGuPrice) * 100)) + "");
		bb.setForecastDistance(forecastDistance);
		bb.setEndAddress(end_address.getText().toString());
		String city = getcity();
		String cityId = "0";
		try {
			bb.setCityId(Integer.valueOf(cityId));
			bb.setCityName(city);
		} catch (Exception e) {
		}
		bb.setDispStat(Config.STATE_DIAODUZHONG);
		if (isSound) {
			bb.setEndAddress(carType);
			intent.putExtra("appsound", true);
		} else {
			intent.putExtra("appsound", false);
		}
		ETApp.getInstance().setCacheBookbean(bb);
		intent.putExtra("bookbean", bb);
		startService(intent);
	}

	private void clearCheck() {
//		carcontoller.setCheckId(R.id.rb_taxi);
		setPaoViewVis();
		yuGuPrice = "0";
	}

	protected void startWait(long bookId, long timeout) {

		int displayTime = (int) (timeout);
		progress_time.setMax(displayTime);
		timeTxt.setText(getTimeForMills(displayTime));
	}

	private void playSound(final int resId, boolean isSound, final OnPreparedListener startlistener, final OnCompletionListener endListener) {
		if (isSound) {
			ToolUtil.playSound(NewMainActivityNew.this, resId, startlistener, endListener);
		}
	}

	protected void handleUdp(int msgId, byte[] message) {

		try {
			AppLog.LogD(" udp : " + msgId + " , msg : " + new String(message, "utf-8"));
			String udpReply = new String(message, "utf-8");

			JSONObject json = new JSONObject(udpReply);
			dispatchMessag(msgId, json);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void dispatchMessag(int msgId, JSONObject json) throws JSONException {

		long bookId = json.getLong("bookId");

		if (msgId == cn.com.easytaxi.platform.common.common.Const.UDP_BOOK_TAXI_SCHEDULE) {

			progress_info.setText(json.getString("msg"));

		}

		if (msgId == 0xFF0006) {
			if (!ispop && bookId == currentBookId) {
				dispatchStat(Config.STATE_QUXIAO);
				// showDialog(DRIVER_CANCEL_SERVICE_DLG);
				ispop = true;
			}
		}
	}

	boolean ispop = false;

	private class LoadBooks extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			JsonObject json = new JsonObject();
			json.addProperty("action", "scheduleAction");
			json.addProperty("method", "getActiveBookListByPassenger");
			json.addProperty("passengerId", getPassengerId());
			json.addProperty("size", 10);
			json.addProperty("startId", 0);
			try {
				byte[] response = TcpClient.send(1L, MsgConst.MSG_TCP_ACTION, json.toString().getBytes("UTF-8"));
				if (response != null && response.length > 0) {
					JSONObject jsonObject = new JSONObject(new String(response, "UTF-8"));
					AppLog.LogD("xyw", "book list-->" + jsonObject.toString());
					if (jsonObject.getInt("error") == 0) {
						JSONArray jsonArray = jsonObject.getJSONArray("bookList");
						int length = jsonArray.length();
						JSONObject jsonObjectBookBean;
						for (int i = 0; i < length; i++) {
							jsonObjectBookBean = (JSONObject) jsonArray.get(i);
							int type = BookPublishFragement.getJsonInt(jsonObjectBookBean, "bookState");
							if (type == 1) {
								int booktyp = BookPublishFragement.getJsonInt(jsonObjectBookBean, "bookType");
								if (booktyp % 2 == 0) {
									BookBean bookBean = setBookDatas(jsonObjectBookBean);
									ETApp.getInstance().setCacheBookbean(bookBean);
									android.os.Message msg = new Message();
									msg.arg1 = 3;
									handler.sendMessage(msg);
									return false;
								}
							}
						}
						// 无即时订单，可下新订单
						return true;
					} else {
						// errorcode != 0
					}
				} else {
					// no datas
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			isCanBook = result;
			if (!result) {
				BookBean bk = ETApp.getInstance().getCacheBookbean();
				if (bk == null)
					return;
				// 订单的状态
				// int currentDispStat = BOOK_STAT_NORMAL;
				// if (bk != null) {
				//
				// // currentBookId = bk.getId();
				// // currentDispStat = bk.getDispStat();
				// // dispatchStat(bk.getDispStat());
				//
				// // 由下一步更改为跳转到详情页面
				// Intent intent = new Intent(NewMainActivityNew.this,
				// NewBookDetail.class);
				// intent.putExtra("bookId", bk.getId());
				// NewMainActivityNew.this.startActivity(intent);
				// NewMainActivityNew.this.finish();
				// } else {
				// currentDispStat = BOOK_STAT_NORMAL;
				// dispatchStat(currentDispStat);
				// }
				dispatchStat(bk.getBookType());
				startWait(bk.getId(), bk.getTimeOut());
			}

			mProgressDialog.cancel();
		}
	}

	private BookBean setBookDatas(JSONObject jsonObjectBookBean) {
		BookBean bean = new BookBean();
		bean.setStartAddress(BookPublishFragement.getJsonString(jsonObjectBookBean, "startAddr"));
		bean.setStartLongitude(BookPublishFragement.getJsonInt(jsonObjectBookBean, "startLng"));
		bean.setStartLatitude(BookPublishFragement.getJsonInt(jsonObjectBookBean, "startLat"));
		bean.setEndAddress(BookPublishFragement.getJsonString(jsonObjectBookBean, "endAddr"));
		bean.setEndLongitude(BookPublishFragement.getJsonInt(jsonObjectBookBean, "endLng"));
		bean.setEndLatitude(BookPublishFragement.getJsonInt(jsonObjectBookBean, "endLat"));
		bean.setCityId(BookPublishFragement.getJsonInt(jsonObjectBookBean, "cityId"));
		bean.setPassengerId(BookPublishFragement.getJsonLong(jsonObjectBookBean, "passengerId"));
		bean.setUseTime(BookPublishFragement.getJsonString(jsonObjectBookBean, "useTime"));
		bean.setState(BookPublishFragement.getJsonInt(jsonObjectBookBean, "state"));
		bean.setBookType(BookPublishFragement.getJsonInt(jsonObjectBookBean, "bookType"));
		bean.setPassengerName(BookPublishFragement.getJsonString(jsonObjectBookBean, "contact"));
		bean.setId(BookPublishFragement.getJsonLong(jsonObjectBookBean, "id"));
		bean.setReplyerId(BookPublishFragement.getJsonLong(jsonObjectBookBean, "taxiId"));
		bean.setReplyerPhone(BookPublishFragement.getJsonString(jsonObjectBookBean, "taxiPhone"));
		bean.setReplyerNumber(BookPublishFragement.getJsonString(jsonObjectBookBean, "taxiNumber"));
		bean.setReplyerName(BookPublishFragement.getJsonString(jsonObjectBookBean, "replyerName"));
		bean.setReplyerCompany(BookPublishFragement.getJsonString(jsonObjectBookBean, "replyerCompany"));
		bean.setReplyerLongitude(BookPublishFragement.getJsonInt(jsonObjectBookBean, "replyerLongitude"));
		bean.setReplyerLatitude(BookPublishFragement.getJsonInt(jsonObjectBookBean, "replyerLatitude"));
		bean.setCalledNumber(BookPublishFragement.getJsonString(jsonObjectBookBean, "calledNumber"));
		bean.setLevel(BookPublishFragement.getJsonString(jsonObjectBookBean, "levels"));
		bean.setCx(BookPublishFragement.getJsonString(jsonObjectBookBean, "cx"));
		bean.setReason(BookPublishFragement.getJsonString(jsonObjectBookBean, "reason"));
		bean.setIsPl(BookPublishFragement.getJsonInt(jsonObjectBookBean, "isPl"));
		bean.setTimeLong(BookPublishFragement.getJsonString(jsonObjectBookBean, "timeLong"));
		bean.setCurrentDate(BookPublishFragement.getJsonString(jsonObjectBookBean, "currentDate"));

		if (bean.getReplyerId() > 0) {
			bean.setDispStat(Config.STATE_YIJIEDAN);
		} else {
			bean.setDispStat(Config.STATE_DIAODUZHONG);
		}
		return bean;
	}

	public static final String[] childName = new String[] { "司机没来", "司机绕道", "司机迟到", "其他", "已打到车", "行程有变", "其他" };

	/**
	 * 根据评价类型，生成评价选项
	 * 
	 * @param type
	 *            1为待接单状态只能取消，2为已接单状态有评价跟取消，3为已超时只评价
	 * @return
	 * @return List<Group>
	 */
	public static List<Group> getGroupData(int type) {
		// TODO Auto-generated method stub
		List<Group> group = new ArrayList<Group>();
		// 非常满意，一般满意，不太满意，取消订单
		String[] groupName = new String[] { "非常满意", "一般满意", "不太满意", "取消订单" };

		if (type == 1) {
			// group4-->取消订单
			Group group4 = new Group();
			List<Child> childrenList4 = new ArrayList<Child>();

			Child child41 = new Child();
			child41.setHeadImage(null);
			child41.setName(childName[4]);
			child41.setIndex(5);

			Child child42 = new Child();
			child42.setHeadImage(null);
			child42.setName(childName[5]);
			child42.setIndex(6);

			Child child43 = new Child();
			child43.setHeadImage(null);
			child43.setName(childName[6]);
			child43.setIndex(7);

			childrenList4.add(child41);
			childrenList4.add(child42);
			childrenList4.add(child43);

			group4.setGroupName(groupName[3]);
			group4.setChildrenList(childrenList4);
			group4.setIndex(4);

			group.add(group4);
		} else if (type == 2) {
			// group1-->非常满意
			Group group1 = new Group();
			List<Child> childrenList1 = new ArrayList<Child>();

			group1.setGroupName(groupName[0]);
			group1.setChildrenList(childrenList1);

			// group2-->一般满意
			Group group2 = new Group();
			List<Child> childrenList2 = new ArrayList<Child>();

			group2.setGroupName(groupName[1]);
			group2.setChildrenList(childrenList2);

			// group3-->不太满意
			Group group3 = new Group();
			List<Child> childrenList3 = new ArrayList<Child>();

			Child child31 = new Child();
			child31.setHeadImage(null);
			child31.setName(childName[0]);
			child31.setIndex(1);

			// Child child32 = new Child();
			// child32.setHeadImage(null);
			// child32.setName(childName[1]);
			// child32.setIndex(2);

			Child child33 = new Child();
			child33.setHeadImage(null);
			child33.setName(childName[2]);
			child33.setIndex(3);

			Child child34 = new Child();
			child34.setHeadImage(null);
			child34.setName(childName[3]);
			child34.setIndex(4);

			childrenList3.add(child31);
			// childrenList3.add(child32);
			childrenList3.add(child33);
			childrenList3.add(child34);

			group3.setGroupName(groupName[2]);
			group3.setChildrenList(childrenList3);

			// group4-->取消订单
			Group group4 = new Group();
			List<Child> childrenList4 = new ArrayList<Child>();

			Child child41 = new Child();
			child41.setHeadImage(null);
			child41.setName(childName[4]);
			child41.setIndex(5);

			Child child42 = new Child();
			child42.setHeadImage(null);
			child42.setName(childName[5]);
			child42.setIndex(6);

			Child child43 = new Child();
			child43.setHeadImage(null);
			child43.setName(childName[6]);
			child43.setIndex(7);

			childrenList4.add(child41);
			childrenList4.add(child42);
			childrenList4.add(child43);

			group4.setGroupName(groupName[3]);
			group4.setChildrenList(childrenList4);
			group1.setIndex(1);
			group2.setIndex(2);
			group3.setIndex(3);
			group4.setIndex(4);
			group.add(group1);
			group.add(group2);
			group.add(group3);
			group.add(group4);
		} else {
			// group1-->非常满意
			Group group1 = new Group();
			List<Child> childrenList1 = new ArrayList<Child>();

			group1.setGroupName(groupName[0]);
			group1.setChildrenList(childrenList1);

			// group2-->一般满意
			Group group2 = new Group();
			List<Child> childrenList2 = new ArrayList<Child>();

			group2.setGroupName(groupName[1]);
			group2.setChildrenList(childrenList2);

			// group3-->不太满意
			Group group3 = new Group();
			List<Child> childrenList3 = new ArrayList<Child>();

			Child child31 = new Child();
			child31.setHeadImage(null);
			child31.setName(childName[0]);
			child31.setIndex(1);

			// Child child32 = new Child();
			// child32.setHeadImage(null);
			// child32.setName(childName[1]);
			// child32.setIndex(2);

			Child child33 = new Child();
			child33.setHeadImage(null);
			child33.setName(childName[2]);
			child33.setIndex(3);

			Child child34 = new Child();
			child34.setHeadImage(null);
			child34.setName(childName[3]);
			child34.setIndex(4);

			childrenList3.add(child31);
			// childrenList3.add(child32);
			childrenList3.add(child33);
			childrenList3.add(child34);

			group3.setGroupName(groupName[2]);
			group3.setChildrenList(childrenList3);
			group1.setIndex(1);
			group2.setIndex(2);
			group3.setIndex(3);
			group.add(group1);
			group.add(group2);
			group.add(group3);
		}

		return group;
	}

	private void chooseend() {
		switchAddress(SELECT_ENDADDR, getcity());
	}

	private void chooseZhuanche(int num) {
		switch (num) {
		case 1:// 舒适
			carstate = 4;
			yugutyp = 2;
			break;
		case 2: // 豪华
			carstate = 6;
			yugutyp = 4;
			break;
		case 3:// 商务
			carstate = 8;
			yugutyp = 3;
			break;
		case 0: // 出租车
			carstate = 2;
			yugutyp = 1;
			break;
		default:
			break;
		}
		yugu();
		requestNearbyTaxi(yugutyp * 2, new NearByCallbacK());
	}

	/**
	 * 没有司机接单时，取消即时订单
	 * 
	 * @return void
	 */
	private void cancelImmediateBook() {
		cancelBookCheckInfo("", false, 500);
		// dispatchStat(Config.STATE_QUXIAO);
	}

	private void switchAddress(int type, String cityName) {
		Intent intent = new Intent(NewMainActivityNew.this, SearchAddressActivity.class);
		intent.putExtra("cityName", cityName);
		AppLog.LogD(cityName + "");
		startActivityForResult(intent, type);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			setback(false);
			if (!paoViewBack()) {
				return true;
			} else {
				if (myuserinfo.onBack()) {
//					long secondTime = System.currentTimeMillis();
//					if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
//						ToastUtil.show(this, "再按一次退出程序");
//						firstTime = secondTime;// 更新firstTime
//						return true;
//					} else { // 两次按键小于2秒时，退出应用
						finish();
//					}
				} else {
					return true;
				}
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private long firstTime = 0;

	@Override
	public void onBackPressed() {
		if (myuserinfo.onBack())
			super.onBackPressed();
	}

	protected void cancelBookCheckInfo(String cancelInfo, boolean isConfirm, int value) {
		BookBean bk = ETApp.getInstance().getCacheBookbean();
		NewNetworkRequest.cancelBook(bk.getId(), currentTaxiId, mobile, null);
	}

	/**
	 * 设置气泡内容根据状态改变
	 */
	private void setPaoViewVis() {
		topimg.setVisibility(View.VISIBLE);
		mashangLayout.setVisibility(where == where_end ? View.VISIBLE : View.GONE);
	}

	/**
	 * 点击返回，首先判断是否在第二个状态
	 */
	private boolean paoViewBack() {
		if (where == where_end) {
			top_layout.setVisibility(View.VISIBLE);
			topimg.setVisibility(View.VISIBLE);
			show = false;
			where = where_no_end;
			setPaoViewVis();
			end_address.setText("");
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case SELECT_ENDADDR: // 结束地址
				topimg.setVisibility(View.VISIBLE);
				show = true;
				where = where_end;
				setPaoViewVis();
				end_address.setText(data.getStringExtra("address"));
				endLatitude = (int) data.getLongExtra("lat", 0);
				endLongitude = (int) data.getLongExtra("lng", 0);
				yugu();
				break;
			case SELECT_STARTADDR: // 开始地址
				topimg.setVisibility(View.VISIBLE);
				show = true;
				setPaoViewVis();
				start_address.setText(data.getStringExtra("address"));
				startLatitude = (int) data.getLongExtra("lat", 0);
				startLongitude = (int) data.getLongExtra("lng", 0);
				try {
					if (getCurrentlat() != 0) {
						mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(startLatitude / 1E6, startLongitude / 1E6)));
						ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
						ReverseGeoCodeOption result = reverseCode.location(new LatLng(startLatitude / 1E6, startLongitude / 1E6));
						coder.reverseGeoCode(result);
						// mDizhi.setText(jiedao);
						// biaozhiTextView.setText(biaozhi + "上车");
						// start_address.setText(biaozhi);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				yugu();
				break;

			}
		}
	}

	private String yuGuPrice = "0";// 预估价

	// 获取预估价格
	private void yugu() {
		if (where == where_end) {
			setback(true);
			if (TextUtils.isEmpty(end_address.getText().toString())) {
				// Toast.makeText(NewMainActivityNew.this, "输入终点会有预估价格哦！",
				// Toast.LENGTH_LONG).show();
			} else {
				showLoadingDialog("");
				try {
					JSONObject param = new JSONObject();
					param.put("action", "geoAction");
					param.put("method", "getRoutePlan");
					param.put("slng", startLongitude);
					param.put("slat", startLatitude);
					param.put("elng", endLongitude);
					param.put("elat", endLatitude);
					param.put("carType", yugutyp);
					param.put("cityName", startCity);
					
					SysDeug.logD("yugu request - > " + param.toString());
					XTCPUtil.send(param, new XTCPUtil.XNetCallback() {

						@Override
						public void onSuc(String result) {
							try {
								JSONObject jo = new JSONObject(result);
								yuGuPrice = jo.getString("price");
								msPrice.setText("(预估价¥" + yuGuPrice + ")");
								forecastDistance = jo.getString("dis");
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
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mashangLayout: // 提交订单
			if (YdLoginUtil.isLogin(NewMainActivityNew.this)) {
				if (TextUtils.isEmpty(start_address.getText().toString())) {
					ToastUtil.show(NewMainActivityNew.this, "请确认起点是否正确");
				} else if (TextUtils.isEmpty(end_address.getText().toString())) {
					ToastUtil.show(NewMainActivityNew.this, "请确认终点是否正确");
				} else {
					getispay();
				}
			}
			break;
		case R.id.end_address_layout:
			switchAddress(SELECT_ENDADDR, getcity());
			break;
		case R.id.start_address_layout:
			switchAddress(SELECT_STARTADDR, getcity());
			break;
			// case R.id.top_layout:
			// if (YdLoginUtil.isLogin(NewMainActivityNew.this))
			// if (jiedao.equals("滑动地图获取位置")) {
			// ToastUtil.show(NewMainActivityNew.this, jiedao);
			// } else {
			// if (jiedao.startsWith("null") || jiedao.equals("")) {
			// ToastUtil.show(NewMainActivityNew.this, "滑动地图获取位置");
			// } else {
			// chooseend();
			// }
			// }
			// break;
		case R.id.quxiao:
			DiaoduCancelDialog.newInstance(NewMainActivityNew.this).setActionListener(new DialoAction() {

				@Override
				public void onClick(int tag) {
					if (tag == RIGHT) {
						cancelImmediateBook();
						ETApp.getInstance().getCacheBookbean().stopWait();
						dispatchStat(Config.STATE_QUXIAO);
					}
				}
			}).show();

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

	private void setback(boolean isShow) {
		if (isShow) {
			bar.getLeftImageButton().setImageResource(R.drawable.top_back);
			bar.setTitleName("");
			bar.getRightHomeButton().setVisibility(View.GONE);
			bar.setBackCallback(new Callback<Object>() {
				@Override
				public void handle(Object param) {
					if (bar.getLeftImageButton().isShown()) {
						setback(false);
						paoViewBack();
					}
				}
			});
		} else {
			bar.getLeftImageButton().setImageResource(R.drawable.top_back);
			bar.setTitleName("");
			bar.getRightHomeButton().setVisibility(View.VISIBLE);
			bar.setBackCallback(new Callback<Object>() {
				@Override
				public void handle(Object param) {
					if (bar.getLeftImageButton().isShown()) {
						finish();
					}
				}
			});
			
		}
	}

	//把原有的toast 改为的dialog
	private void dialog1(){
		CommonDialog dialog = MyDialog.comfirm(NewMainActivityNew.this, "温馨提示", "您还有未完成的订单", new SureCallback() {
			@Override
			public void onClick(int tag) {
				if (tag == RIGHT) {
					Intent intent = new Intent(NewMainActivityNew.this, NewBookDetail.class);
					intent.putExtra("bookid", bookid);
					intent.putExtra("2", 2);
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

	private long bookid;

	//判断是否支付
	private void getispay(){
		mProgressDialog.show();
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
						startSendBookService(false, "");
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
	
	/**
	 * 查询周围的出租车
	 * startLatitude = (int) (mapStatus.target.latitude * 1E6);
					startLongitude = (int) (mapStatus.target.longitude * 1E6);
	 * @param callback
	 */
	@Override
	protected void requestNearbyTaxi(int carstate,Callback<Object> callback) {
		NewNetworkRequest.getNearbyTaxis(carstate,getUserPhoneNum(), startLatitude, startLongitude, callback, getCityId());
	}

	

}
