package cn.com.easytaxi.onetaxi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.platform.view.AddressEditView;
import cn.com.easytaxi.platform.view.AddressEditView.OnEditOverListner;
import cn.com.easytaxi.util.BDLocationServer;
import cn.com.easytaxi.util.BDLocationServer.LocReceive;
import cn.com.easytaxi.workpool.bean.GeoPointLable;

import com.baidu.location.BDLocation;
import com.aiton.yc.client.R;

public class SearchAddressActivity extends Activity implements OnItemClickListener, OnEditOverListner, LocReceive {

	protected static final String tag = "SearchAddressActivity";
//	private TitleBar bar;
	private SessionAdapter session;
	private String mobile;
	private RadioGroup radioGroup_choice;
	private ListView addressListView;
	private String cityName = "";
	private String cityId;
	private cn.com.easytaxi.platform.view.AddressEditView search_button_panel;

	private List<GeoPointLable> history = new ArrayList<GeoPointLable>(12);
	private List<GeoPointLable> usual = new ArrayList<GeoPointLable>(12);
	private List<GeoPointLable> current = new ArrayList<GeoPointLable>(12);

	private GeoLableAdapter currentAdapter;

	private View line1, line2;
	private TextView line1Text, line2Text;
	private TextView cityTV;

	// private BroadcastReceiver addUsualReceiver = new BroadcastReceiver() {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// if (intent != null) {
	// String name = intent.getStringExtra("address");
	// int lat = intent.getIntExtra("lat", 0);
	// int lng = intent.getIntExtra("lng", 0);
	// if (lat != 0 && lng != 0 && StringUtils.isEmpty(name)) {
	// GeoPointLable gp = new GeoPointLable(lat, lng, name);
	// if (usual != null) {
	// usual.add(0, gp);
	// }
	// }
	// }
	//
	// }
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ontexi_address_search);
		initDbData();
		initViews();
		register();
		initListener();
		initUserData();
		BDLocationServer.getInstance(this).setReceiveListenern(this);
		BDLocationServer.getInstance(this).requestLocation();
		// registerReceiver(addUsualReceiver, new
		// IntentFilter("cn.com.easytaxi.action.add.usual.address"));
	}

	private void initUserData() {
		search_button_panel.setCityName("");

		currentAdapter = new GeoLableAdapter(current, this);
		currentAdapter.setType(2);
		addressListView.setAdapter(currentAdapter);
		// history = 1
		history = session.getPois(cityName, 1, mobile);
		// usual = 2
		usual = session.getPois(cityName, 2, mobile);

		refreshData(radioGroup_choice.getCheckedRadioButtonId());
	}

	/**
	 * 常用地址
	 */
	private List<GeoPointLable> getDeaultUsual() {
		List<GeoPointLable> usual = new ArrayList<GeoPointLable>();
		usual.add(new GeoPointLable(22629889, 113819754, "深圳宝安国际机场T3", "深圳市").disDelate());
		usual.add(new GeoPointLable(22537956, 114123606, "深圳站", "深圳市").disDelate());
		usual.add(new GeoPointLable(22615102, 114035527, "深圳北站", "深圳市").disDelate());
		usual.add(new GeoPointLable(22608046, 114127379, "深圳东站", "深圳市").disDelate());
		usual.add(new GeoPointLable(22534133, 113913968, "深圳西站", "深圳市").disDelate());
		return usual;
	}

	private void refreshData(int id) {
		if (id == R.id.radio_usual) {
			current.clear();
			usual = session.getPois(cityName, 2, mobile);
			current.addAll(getDeaultUsual());
			current.addAll(usual);
			currentAdapter.setType(2);
			selectTab(1);
		}

		if (id == R.id.radio_history) {
			current.clear();
			history = session.getPois(cityName, 1, mobile);
			current.addAll(history);
			currentAdapter.setType(1);
			selectTab(0);
		}

		currentAdapter.notifyDataSetChanged();
	}

	private void initListener() {
		
		findViewById(R.id.textView_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.ontexi_address_input).setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				try {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager) SearchAddressActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					}
				} catch (Exception e) {
				}
				return false;
			}
		});

		search_button_panel.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		search_button_panel.setOnEditOverListner(new OnEditOverListner() {

			@Override
			public void OnEditOver(AddressEditView view) {
				GeoPointLable gp = (GeoPointLable) view.getTag();
				addressResult(gp);
				// if (gp == null) {
				// return;
				// }
				// intent.putExtra("address", gp.getName());
				// intent.putExtra("lat", gp.getLat());
				// intent.putExtra("lng", gp.getLog());
				// // 返回是不是固定的地址，没有删除按钮的则是常用地址
				// intent.putExtra("isGuding", gp.isDelete());
				// AppLog.LogD("gp.getName() -000000000-- " + gp.getName() +
				// " ,lat ====  " + gp.getLat() + " ; lng ==" + gp.getLog() +
				// " isusual - > " + !gp.isDelete());
				// setResult(RESULT_OK, intent);
				// finish();

			}
		});

		addressListView.setOnItemClickListener(this);
		addressListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				hideInputMethod(v);
				return false;
			}
		});

		radioGroup_choice.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup view, int id) {
				refreshData(id);
				hideInputMethod(view);
			}
		});

//		bar.setBackCallback(new Callback<Object>() {
//
//			@Override
//			public void handle(Object param) {
//
//				doBack();
//			}
//		});

		cityTV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 城市选择
				Intent intent = new Intent();
				intent.setClass(SearchAddressActivity.this, CityActivity.class);
				intent.putExtra("dingweiname", cityName);
				startActivity(intent);
			}
		});

	}

	protected void doBack() {
		this.finish();
	}

	private void initViews() {

		radioGroup_choice = (RadioGroup) findViewById(R.id.radioGroup_choice);
		addressListView = (ListView) findViewById(R.id.listView_address);
		line1 = findViewById(R.id.line_btn1);
		line2 = findViewById(R.id.line_btn2);
		line1Text = (TextView) findViewById(R.id.radio_history);
		line2Text = (TextView) findViewById(R.id.radio_usual);
		cityTV = (TextView) findViewById(R.id.cityText);
//		bar = new TitleBar(this);
//		bar.setTitleName("地址选择");
//		bar.switchToCityButton();
//		bar.getRightCityButton().setVisibility(View.GONE);
//		bar.getRightHomeButton().setVisibility(View.GONE);

		search_button_panel = (AddressEditView) findViewById(R.id.search_button_panel);
	}

	public void selectTab(int tab) {
		if (tab == 0) {
			line1.setBackgroundColor(Color.parseColor("#FFB628"));
			line2.setBackgroundColor(Color.parseColor("#E7E5E0"));
			line1Text.setTextColor(Color.parseColor("#FFB628"));
			line2Text.setTextColor(Color.BLACK);
		} else {
			line2.setBackgroundColor(Color.parseColor("#FFB628"));
			line1.setBackgroundColor(Color.parseColor("#E7E5E0"));
			line2Text.setTextColor(Color.parseColor("#FFB628"));
			line1Text.setTextColor(Color.BLACK);
		}
	}

	private void initDbData() {
		session = new SessionAdapter(SearchAddressActivity.this);
		mobile = session.get("_MOBILE");
		// Intent intent = getIntent();
		// cityName = intent.getStringExtra("cityName");
		// if (TextUtils.isEmpty(cityName)) {
		// cityName = session.get("_CITY_NAME");
		// }
		//
		// AppLog.LogD("initDbData  cityName " + cityName);
	}

	@Override
	protected void onDestroy() {
		unRegister();
//		if (bar != null) {
//			bar.close();
//		}
		// if (addUsualReceiver != null) {
		// unregisterReceiver(addUsualReceiver);
		// }
		if (session != null) {
			session.close();
			session = null;
		}

		super.onDestroy();
	}

	class GeoLableAdapter extends BaseAdapter {
		private List<GeoPointLable> data;
		private Context context;
		private LayoutInflater inflater;
		private int type = 1;

		public GeoLableAdapter(List<GeoPointLable> data, Context context) {
			this.data = data;
			this.context = context;
			inflater = LayoutInflater.from(context);

		}

		public void setType(int type) {
			this.type = type;
		}

		@Override
		public int getCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getItem(int location) {

			return data.get(location);
		}

		@Override
		public long getItemId(int arg0) {

			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final GeoPointLable gp = data.get(position);
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.onetaxi_search_address_item, null);
				holder = new Holder();
				holder.add = (Button) convertView.findViewById(R.id.search_add);
				holder.infoTv = (TextView) convertView.findViewById(R.id.search_address);
				holder.address_parent = (RelativeLayout) convertView.findViewById(R.id.address_parent);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			/*
			 * if(position % 2 == 0){
			 * holder.address_parent.setBackgroundResource(R.drawable.btn038);
			 * }else{
			 * holder.address_parent.setBackgroundResource(R.drawable.btn037); }
			 */

			holder.infoTv.setText(gp.getName());
//			holder.infoTv.setText(gp.getName() + "(" + gp.getCityName() + ")");
			if (!gp.isDelete())
				holder.add.setVisibility(View.GONE);
			else
				holder.add.setVisibility(View.VISIBLE);
			holder.add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// session.savePoi(cityName, mobile, type, gp);
					session.deletePoi(cityName, mobile, type, gp); // type 1 or
																	// 2
					data.remove(gp);
					notifyDataSetChanged();
				}
			});

			return convertView;
		}

		class Holder {
			public TextView infoTv;
			public RelativeLayout address_parent;
			public Button add;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Intent intent = new Intent();
		if (position >= 0) {
			GeoPointLable gp = (GeoPointLable) currentAdapter.getItem(position);
			addressResult(gp);
			// intent.putExtra("address", gp.getName());
			// intent.putExtra("lat", gp.getLat());
			// intent.putExtra("lng", gp.getLog());
			// intent.putExtra("isGuding", gp.isDelete());
			// // AppLog.LogD("gp.getName() --- "+ gp.getName() +
			// " ,lat ====  "+
			// // gp.getLat() + " ; lng =="+gp.getLog());
			// setResult(RESULT_OK, intent);
			this.finish();
		}
	}

	@Override
	public void OnEditOver(AddressEditView view) {
		GeoPointLable gp = (GeoPointLable) view.getTag();
		addressResult(gp);
	}

	private void addressResult(GeoPointLable gp) {
		Intent intent = new Intent();
		if (gp == null) {
			return;
		}
		intent.putExtra("address", gp.getName());
		intent.putExtra("lat", gp.getLat());
		intent.putExtra("lng", gp.getLog());
		intent.putExtra("isGuding", gp.isDelete());
		intent.putExtra("city", gp.getCityName());
		AppLog.LogD("city------->>>", gp.getCityName());
		AppLog.LogD("gp.getName() --- " + gp.getName() + " ,lat ====  " + gp.getLat() + " ; lng ==" + gp.getLog());
		setResult(RESULT_OK, intent);
		this.finish();
	}

	public void hideInputMethod(View v) {
		try {
			InputMethodManager imm = (InputMethodManager) SearchAddressActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		} catch (Exception e) {
		}
	}

	private CitySelectCompleteReceiver receiver;

	private void register() {
		if (receiver == null) {
			receiver = new CitySelectCompleteReceiver();
			registerReceiver(receiver, new IntentFilter(CityActivity.CITY_ACTION));
		}
	}

	private void unRegister() {
		if (receiver != null)
			unregisterReceiver(receiver);
	}

	class CitySelectCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			search_button_panel.setCityName(intent.getStringExtra("name"));
			cityTV.setText(intent.getStringExtra("name"));
		}
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location != null) {
			if (TextUtils.isEmpty(location.getCity()) || location.getCity().contains("null")) {
				cityTV.setText("定位失败");
			} else {
				search_button_panel.setCityName(location.getCity());
				cityTV.setText(location.getCity());
				cityName = location.getCity();
			}
		} else {
			cityTV.setText("定位失败");
		}
	}
}
