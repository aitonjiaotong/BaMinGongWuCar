package cn.com.easytaxi.onetaxi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.XGsonUtil;
import cn.com.easytaxi.util.XTCPUtil;
import cn.com.easytaxi.util.XTCPUtil.SimpleTcpCallback;

import com.aiton.yc.client.R;
import com.google.gson.JsonObject;

public class CityActivity extends YdLocaionBaseActivity {

	public static final String CITY_ACTION = "com.book.cityaction";
	private TitleBar bar;
	private GridView gridview;
	private CityAdapter adapter;
	private List<CityBean> cityBeans;
	private String dingweiname;
	private String name;
	private TextView dingweiTextView;
	private TextView item;
	private TextView shengname;
	private LinearLayout cityLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_activity);
		name = getIntent().getStringExtra("name");
		dingweiname = getIntent().getStringExtra("dingweiname");
		initview();
	}

	private void initview() {
		cityBeans = new ArrayList<CityBean>();
		bar = new TitleBar(CityActivity.this);
		bar.setTitleName("八闽专车");
		gridview = (GridView) findViewById(R.id.gridview);
		dingweiTextView = (TextView) findViewById(R.id.dingweitext);
		item = (TextView) findViewById(R.id.base_item_tv);
		shengname = (TextView) findViewById(R.id.shengname);
		cityLayout = (LinearLayout) findViewById(R.id.citylayout);
		adapter = new CityAdapter(cityBeans, CityActivity.this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (cityBeans.get(arg2).getCityId().equals("0")) {
					Intent intent = new Intent();
					intent.setClass(CityActivity.this, CityActivity.class);
					intent.putExtra("name", cityBeans.get(arg2).getCity());
					startActivity(intent);
				} else {
					Intent intent = new Intent(CITY_ACTION);
					intent.putExtra("name", cityBeans.get(arg2).getCity());
					intent.putExtra("id", cityBeans.get(arg2).getCityId());
					intent.putExtra("carNumber", cityBeans.get(arg2).getCarNumber());
					sendBroadcast(intent);
					finish();
				}
			}
		});
		setdata();
	}

	private void setdata() {
		if (!TextUtils.isEmpty(name)) {
			bar.setTitleName("城市选择");
			getCityAndCarNumber();
			dingweiTextView.setVisibility(View.GONE);
			cityLayout.setVisibility(View.GONE);
			shengname.setText(name);
		} else {
			bar.setTitleName("省份选择");
			getSheng();
			dingweiTextView.setVisibility(View.VISIBLE);
			cityLayout.setVisibility(View.VISIBLE);
			if (TextUtils.isEmpty(dingweiname)) {
				item.setText("定位失败");
			} else {
				item.setText(dingweiname);
			}
			shengname.setText("全国");
			register();
		}
	}

	private void getSheng() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("action", "receivedAction");
			jo.put("method", "getProvince");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       XTCPUtil.send(jo, new SimpleTcpCallback(){

		@Override
		public void onSuc(String param) {

			// 改变成功没
			try {
				JsonObject result = XGsonUtil.getJsonObject(param);
				int error = result.get("error").getAsInt();
				if (error == 0) {
					List<CityBean> beans = XGsonUtil.getListFromJson(false, result.get("provin").getAsJsonArray(), CityBean.class);
					cityBeans.clear();
					cityBeans.addAll(beans);
					adapter.notifyDataSetChanged();
				} else {
					ToastUtil.show(CityActivity.this, result.get("errormsg").getAsString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		}}
       );
	}

	private void getCityAndCarNumber() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("action", "receivedAction");
			jo.put("method", "getCityAndCarNumber");
			jo.put("province", name);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       XTCPUtil.send(jo, new SimpleTcpCallback(){

		@Override
		public void onSuc(String param) {

			// 改变成功没
			try {
				JsonObject result = XGsonUtil.getJsonObject(param);
				int error = result.get("error").getAsInt();
				if (error == 0) {
					List<CityBean> beans = XGsonUtil.getListFromJson(false, result.get("city").getAsJsonArray(), CityBean.class);
					cityBeans.clear();
					cityBeans.addAll(beans);
					adapter.notifyDataSetChanged();
				} else {
					ToastUtil.show(CityActivity.this, result.get("errormsg").getAsString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		}}
       );
	}


	private CitySelectCompleteReceiver receiver;

	private void register() {
		if (receiver == null) {
			receiver = new CitySelectCompleteReceiver();
			registerReceiver(receiver, new IntentFilter(CITY_ACTION));
		}
	}

	@Override
	protected void onDestroy() {
		unRegister();
		super.onDestroy();
	}

	private void unRegister() {
		if (receiver != null)
			unregisterReceiver(receiver);
	}

	class CitySelectCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
