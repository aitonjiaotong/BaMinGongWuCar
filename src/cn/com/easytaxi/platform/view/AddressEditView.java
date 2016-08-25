package cn.com.easytaxi.platform.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.common.Const;
import cn.com.easytaxi.common.MapUtil.GeoPoint;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.workpool.bean.GeoPointLable;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.aiton.yc.client.R;

public class AddressEditView extends LinearLayout implements View.OnFocusChangeListener, OnGetSuggestionResultListener {
	private AutoCompleteTextView textView;
	private ProgressBar pb;
	private AddressAdapter adapter;
	private SessionAdapter session;
	private MyTextWatcher textWatcher;
	private GeoPointLable selectedGeoPoint;
	private OnEditOverListner onEditOverListner;
	private boolean loadOnInflated = false;
	private Context context;

	/**
	 * pois搜索
	 */
	// private PoiSearch poiSearch;

	private MyThreadGetPoiAddressList th;
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 2:
					// ArrayList<MKPoiInfo> arrayList = (ArrayList<MKPoiInfo>)
					// msg.obj;
					// if(arrayList.size() <=0){
					// return;
					// }
					// pb.setVisibility(View.INVISIBLE);
					// adapter.getDatas().clear();
					// for(MKPoiInfo mkPoiInfo:arrayList){
					// adapter.getDatas().add(new
					// GeoPointLable(mkPoiInfo.pt.getLatitudeE6(),
					// mkPoiInfo.pt.getLongitudeE6(),
					// mkPoiInfo.name+mkPoiInfo.address));
					// }
					// adapter.notifyDataSetChanged();
					// textView.setVisibility(View.VISIBLE);
					// textView.showDropDown();
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public boolean isLoadOnInflated() {
		return loadOnInflated;
	}

	public MyTextWatcher getTextWatcher() {
		return textWatcher;
	}

	public void setLoadOnInflated(boolean loadOnInflated) {
		this.loadOnInflated = loadOnInflated;
	}

	public AddressEditView(Context context) {
		super(context);
		this.context = context;
		LayoutInflater.from(getContext()).inflate(R.layout.workpool_addr_editview, this, true);
		init();
	}

	public AddressEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(getContext()).inflate(R.layout.workpool_addr_editview, this, true);
		init();
		TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.AddressAutoCompleteEditView);
		CharSequence hint = t.getString(R.styleable.AddressAutoCompleteEditView_hint);
		loadOnInflated = t.getBoolean(R.styleable.AddressAutoCompleteEditView_load_oninflated, false);
		t.recycle();
		if (hint != null) {
			textView.setHint(hint);
		}
	}

	private void init() {
		// poiSearch = PoiSearch.newInstance();
		// poiSearch.setOnGetPoiSearchResultListener(this);
		//
		mSearch = SuggestionSearch.newInstance();
		mSearch.setOnGetSuggestionResultListener(this);

		// poiSearch.searchInCity(new PoiCitySearchOption().);
		textView = (AutoCompleteTextView) findViewById(R.id.workpool_addredit);
		session = new SessionAdapter(getContext());
		pb = (ProgressBar) findViewById(R.id.workpool_addredit_progress);
		adapter = new AddressAdapter(getContext(), R.layout.addr_adpter_item, android.R.id.text1, session);
		textView.setAdapter(adapter);
		textView.setThreshold(2);
		textView.setOnItemClickListener(new MyItemClickLisener());

		textView.clearFocus();
		textWatcher = new MyTextWatcher();
		textView.addTextChangedListener(textWatcher);
		textView.setOnFocusChangeListener(this);
	}

	public void setHint(String info) {
		if (textView != null) {
			textView.setHint(info);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	private boolean newestValue = false;

	private class MyTextWatcher implements TextWatcher {

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			AppLog.LogD("beforeTextChanged::" + s);
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			AppLog.LogD("onTextChanged::" + s);
		}

		public void afterTextChanged(Editable s) {
			AppLog.LogD("afterTextChanged::" + s);

			if (session == null) {
				// Log.e("AddressEditView",
				// "session is null,may view were onDetachedFromWindow");
				return;
			}

			if (loadOnInflated) {// 刚进入会加载当前位置，不弹出下拉框
				loadOnInflated = false;
				return;
			}
			// 在autocomplete下拉列表中选择一项，也会触发afterTextChanged，造成死循环，判断一下
			boolean enable = true;
			ArrayList<GeoPointLable> datas = ((AddressAdapter) textView.getAdapter()).getDatas();
			for (int i = 0; i < datas.size(); i++) {
				if (s.toString().equals(datas.get(i).getName())) {
					selectedGeoPoint = new GeoPointLable(datas.get(i).getLat(), datas.get(i).getLog(), "", "");
					enable = false;
				}
			}
			if (enable && s.length() >= 2) {
				String city = getCityName();
				if (TextUtils.isEmpty(city)) {
					city = "深圳市";
				}

				if (TextUtils.isEmpty(city)) {
					return;
				}

				newestValue = false;
				SysDeug.logD("AddressEditView执行搜索 - > " + city + " : " + s.toString());
				AppLog.LogI("AddressEditView------>>", city+":::-->"+s.toString());
				poiSearchInCity(city, s.toString());
				// AppLog.LogD("触发：" + AddressEditView.this);
				pb.setVisibility(View.VISIBLE);

			}
		}
	}

	private class MyItemClickLisener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			newestValue = true;
			// 关闭输入法
			InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getWindowToken(), 0);
			// 获取经纬度
			selectedGeoPoint = (GeoPointLable) view.getTag();
			// new
			// MyGetPoiTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
			// "");

			if (onEditOverListner != null) {
				AddressEditView.this.setTag(selectedGeoPoint);
				onEditOverListner.OnEditOver(AddressEditView.this);
			}
			String city = session.get("_CITY_NAME");
			String mobile = session.get("_MOBILE");
			session.savePoi(city, mobile, 1, selectedGeoPoint);

		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// AppLog.LogD("AddressEditView", "onDetachedFromWindow");
		if (session != null) {
			session.close();
			session = null;
		}
		textWatcher = null;

	}

	public String getCityName() {

		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	private String cityName;

	int sessionId = 0;

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {

		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}

	public void poiSearchInHistory() {
		/*
		 * ArrayList<GeoPointLable> data =
		 * session.getPois(session.get("_CITY_NAME"), session.get("_MOBILE"));
		 * adapter.getDatas().clear(); adapter.getDatas().addAll(data);
		 * adapter.notifyDataSetChanged();
		 * 
		 * AppLog.LogD( " ===" + data.size()); textView.showDropDown();
		 */
		// textView.setDropDownAnchor(textView.getId());
		/*
		 * if(data.size() >0){ editText.showDropDown(); }
		 */
	}

	private SuggestionSearch mSearch;

	public void poiSearchInCity(final String city, final String key) {
		// poiSearch.searchInCity(new
		// PoiCitySearchOption().city(city).keyword(key).pageNum(20));
		AppLog.LogI("AddressEditView---poiSearchInCity-->", "city-->"+city + "---> key" + key);
		mSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(key));
	}

	public GeoPoint getSelectedGeoPoint() {
		return new GeoPoint(selectedGeoPoint.getLat(), selectedGeoPoint.getLog());
		// return new GeoPointLable();
	}

	public void setSelectedGeoPoint(GeoPointLable selectedGeoPoint) {
		this.selectedGeoPoint = selectedGeoPoint;
	}

	public Editable getText() {
		return textView.getText();
	}

	public void setText(CharSequence text) {
		newestValue = true;
		textView.removeTextChangedListener(textWatcher);
		textView.setText(text);
		textView.setSelection(textView.getText().length());
		textView.addTextChangedListener(textWatcher);
	}

	public OnEditOverListner getOnEditOverListner() {
		return onEditOverListner;
	}

	public void setOnEditOverListner(OnEditOverListner onEditOverListner) {
		this.onEditOverListner = onEditOverListner;
	}

	public interface OnEditOverListner {
		public void OnEditOver(AddressEditView view);
	}

	public void setError(CharSequence msg) {
		textView.setError(msg);
	}

	public AutoCompleteTextView getEditText() {
		return textView;
	}

	public void setEditText(AutoCompleteTextView editText) {
		this.textView = editText;
	}

	public void showProgress() {
		pb.setVisibility(View.VISIBLE);
	}

	public void hiddenProgress() {
		pb.setVisibility(View.GONE);
	}

	public boolean isNewestValue() {
		return newestValue;
	}

	public void setNewestValue(boolean newestValue) {
		this.newestValue = newestValue;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// 关闭输入法
		try {
			InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (hasFocus) {// 如果有焦点就显示软件盘
				imm.showSoftInputFromInputMethod(v.getWindowToken(), 0);
			} else {// 否则就隐藏
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 点击输入框，跳转
	 * 
	 * @param activity
	 * @param cityName
	 * @param requestCode
	 */
	public void setTextViewOnclickListener(final Activity activity, final String cityName, final int requestCode, final String className) {
		textView.clearFocus();
		textView.setFocusable(false);
		textView.setFocusableInTouchMode(false);

		textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭输入法
				try {
					InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getWindowToken(), 0);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Intent intent = null;
					intent = new Intent(activity, Class.forName(className));
					intent.putExtra("cityName", cityName);
					activity.startActivityForResult(intent, requestCode);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Get方式从百度获取推荐查询数据
	 * 
	 * @param url
	 *            反解析的百度url
	 * @param query
	 *            地址名字
	 * @param region
	 *            城市名字
	 * @param output
	 *            输出格式 json 或 xml
	 * @param ak
	 *            开发者申请的百度秘钥
	 * @throws Exception
	 */
	// public ArrayList<MKPoiInfo> executeGetAddressNameList(String url,String
	// query,String region,String output,String ak){
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("query", query));
	// params.add(new BasicNameValuePair("region", region));
	// params.add(new BasicNameValuePair("output", output));
	// params.add(new BasicNameValuePair("ak",ak));
	//
	// url = url +"?"+ URLEncodedUtils.format(params, HTTP.UTF_8);
	// String result = HttpGetMethod(url);
	// //解析返回结果
	// ArrayList<MKPoiInfo> array = new ArrayList<MKPoiInfo>();
	// try {
	// JSONObject jsonObject = new JSONObject(result);
	// JSONArray jsonArray = jsonObject.getJSONArray("result");
	// int length = jsonArray.length();
	// JSONObject item = new JSONObject();
	// for(int i=0;i<length;i++){
	// MKPoiInfo mkPoiInfo = new MKPoiInfo();
	// item = jsonArray.getJSONObject(i);
	// mkPoiInfo.address = item.getString("name");
	// mkPoiInfo.city = item.getString("city");
	// mkPoiInfo.name = item.getString("district");
	// mkPoiInfo.pt = new GeoPoint(0, 0);
	// if(mkPoiInfo.address.startsWith(mkPoiInfo.name)){
	// mkPoiInfo.name = "";
	// }
	// array.add(mkPoiInfo);
	// }
	// return array;
	// } catch (JSONException e) {
	// e.printStackTrace();
	// array = null;
	// }
	// return array;
	// }

	/**
	 * 反解析地址的poi
	 * 
	 * @param mkPoiInfo
	 * @param url
	 *            反解析的百度url
	 * @param address
	 *            地址名字
	 * @param city
	 *            地址城市
	 * @param output
	 *            输出格式 json 或 xml
	 * @param ak
	 *            开发者申请的百度秘钥
	 */
	// public void executeGetPoi(MKPoiInfo mkPoiInfo,String url,String
	// address,String city,String output,String ak){
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("address", address));
	// params.add(new BasicNameValuePair("city", city));
	// params.add(new BasicNameValuePair("output", output));
	// params.add(new BasicNameValuePair("ak",ak));
	//
	// url = url +"?"+ URLEncodedUtils.format(params, HTTP.UTF_8);
	// String result = HttpGetMethod(url);
	// //解析返回结果
	// try {
	// JSONObject js = new JSONObject(result);
	// JSONObject location =
	// js.getJSONObject("result").getJSONObject("location");
	// mkPoiInfo.pt = new GeoPoint(0, 0);
	// mkPoiInfo.pt.setLongitudeE6((int)(Float.parseFloat(location.getString("lng"))*1000000));
	// mkPoiInfo.pt.setLatitudeE6((int)(Float.parseFloat(location.getString("lat"))*1000000));
	// } catch (JSONException e) {
	// e.printStackTrace();
	// mkPoiInfo.pt = null;
	// }
	// }

	/**
	 * 反解析地址的poi
	 * 
	 * @param geoPointLable
	 * @param url
	 *            反解析的百度url
	 * @param address
	 *            地址名字
	 * @param city
	 *            地址城市
	 * @param output
	 *            输出格式 json 或 xml
	 * @param ak
	 *            开发者申请的百度秘钥
	 */
	public void executeGetPoi(GeoPointLable geoPointLable, String url, String address, String city, String output, String ak) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("address", address));
		params.add(new BasicNameValuePair("city", city));
		params.add(new BasicNameValuePair("output", output));
		params.add(new BasicNameValuePair("ak", ak));

		url = url + "?" + URLEncodedUtils.format(params, HTTP.UTF_8);
		String result = HttpGetMethod(url);
		// 解析返回结果
		try {
			JSONObject js = new JSONObject(result);
			JSONObject location = js.getJSONObject("result").getJSONObject("location");
			geoPointLable.setLat((int) (Float.parseFloat(location.getString("lat")) * 1000000));
			geoPointLable.setLog((int) (Float.parseFloat(location.getString("lng")) * 1000000));
		} catch (JSONException e) {
			e.printStackTrace();
			// 当异常的时候
			geoPointLable.setLat(0);
			geoPointLable.setLog(0);
		}
	}

	/**
	 * Get方式
	 * 
	 * @param url
	 *            请求的Url
	 * @return
	 */
	public static String HttpGetMethod(String url) {
		String strResult = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				strResult = EntityUtils.toString(entity);
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			strResult = "";
		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
		}
		return strResult;
	}

	/**
	 * 
	 * @param callback
	 * @param address
	 * @param cityName
	 * @param baiduKey
	 *            开发者秘钥：我们是"F1f3c50228554ec93f1b734dc3761a5d"
	 */
	public void getFromBaidu(final String address, final String cityName, final String baiduKey) {
		try {
			if (th != null) {
				th.setFlag(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		th = new MyThreadGetPoiAddressList(address, baiduKey);
		th.start();
	}

	class MyThreadGetPoiAddressList extends Thread {
		boolean flag = false;
		String address = "";
		String baiduKey = "";

		public MyThreadGetPoiAddressList(String address, String baiduKey) {
			this.address = address;
			this.baiduKey = baiduKey;
		}

		@Override
		public void run() {
			// flag = true;
			// ArrayList<MKPoiInfo> list =
			// executeGetAddressNameList("http://api.map.baidu.com/place/v2/suggestion",
			// address,cityName, "json", baiduKey);
			// if(flag){
			// Message msg = new Message();
			// msg.what = 2;
			// msg.obj = list;
			// handler.sendMessage(msg);
			// }
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}
	}

	private class MyGetPoiTask extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;

		@Override
		protected String doInBackground(String... params) {
			executeGetPoi(selectedGeoPoint, "http://api.map.baidu.com/geocoder/v2/", selectedGeoPoint.getName(), cityName, "json", getBaiduWebPoiKey());
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (selectedGeoPoint.getLat() == 0 && selectedGeoPoint.getLog() == 0) {
				progressDialog.dismiss();
				ToastUtil.show(context, "解析地址经纬度失败，请重试...");
			} else {// 获取经纬度没有发生异常
				if (onEditOverListner != null) {
					AddressEditView.this.setTag(selectedGeoPoint);
					onEditOverListner.OnEditOver(AddressEditView.this);
				}
				String city = session.get("_CITY_NAME");
				String mobile = session.get("_MOBILE");
				session.savePoi(city, mobile, 1, selectedGeoPoint);
				progressDialog.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(true);
			progressDialog.setMessage("正在获取地理位置信息,请稍等...");
			progressDialog.show();
		}
	}

	/**
	 * 随机获取百度key
	 * 
	 * @return
	 */
	public String getBaiduWebPoiKey() {
		int length = Const.baiduWebPoiKeys.length;
		int index = (int) (Math.random() * length);
		return Const.baiduWebPoiKeys[index];
	}

	// @Override
	// public void onGetPoiDetailResult(PoiDetailResult arg0) {
	//
	// }
	//
	// @Override
	// public void onGetPoiResult(PoiResult arg0) {
	// if (arg0 == null || arg0.error != SearchResult.ERRORNO.NO_ERROR)
	// return;
	// List<PoiInfo> pois = arg0.getAllPoi();
	// ArrayList<GeoPointLable> datas = adapter.getDatas();
	// datas.clear();
	// for (PoiInfo poiInfo : pois) {
	// long lat = (long) (poiInfo.location.latitude * 1E6);
	// long lng = (long) (poiInfo.location.longitude * 1E6);
	// datas.add(new GeoPointLable(lat, lng, poiInfo.name));
	// }
	// adapter.setDatas(datas);
	// adapter.notifyDataSetChanged();
	//
	// }

	public void onDestory() {
		// if (poiSearch != null)
		// poiSearch.destroy();
		if (mSearch != null)
			mSearch.destroy();
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		List<SuggestionInfo> addrrs = res.getAllSuggestions();
		ArrayList<GeoPointLable> datas = adapter.getDatas();
		datas.clear();
		for (SuggestionInfo poiInfo : addrrs) {
			if (poiInfo == null || poiInfo.key == null || poiInfo.pt == null)
				continue;
			long lat = (long) (poiInfo.pt.latitude * 1E6);
			long lng = (long) (poiInfo.pt.longitude * 1E6);
			try {
				if (getCityName() != null && getCityName().equals(poiInfo.city))
					datas.add(new GeoPointLable(lat, lng, poiInfo.key, poiInfo.city));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		adapter.setDatas(datas);
		adapter.notifyDataSetChanged();
		hiddenProgress();
	}
}
