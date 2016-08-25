package passenger.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import passenger.constant.Constant;

import com.aiton.yc.client.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;


public class MapForChooseAddrActivity extends Activity implements View.OnClickListener, OnGetGeoCoderResultListener
{
    private ImageView mIv_zuche_stores_map_back;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    boolean isFirstLoc = true;// 是否首次定位
    public MyLocationListenner myListener = new MyLocationListenner();
    private ImageView mIv_zuche_stores_loc;
    private double mLocLatitude;
    private double mLocLongitude;
    private UiSettings mUiSettings;
    private ImageView mIv_org_b_poi;
    private TextView mTv_org_show_where;
    private int mIntExtra;//为了区分是取车还是还车
    private GeoCoder mGeoCoder;
    private LatLng mStLatLng;
    private TextView mTv_done;

    private EditText mEt_search_zone;
    private ImageView mIv_clear;
    private ListView mLv_search_ruslt;
    private SuggestionSearch mSuggestionSearch;
    private List<SuggestionResult.SuggestionInfo> mAllSuggestions = new ArrayList<SuggestionInfo>();
    private SuggestionSearchAdapter mSuggestionSearchAdapter = new SuggestionSearchAdapter();
    private GeoCoder mSearch;
    private String mCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_map_for_choose_addr);
        getIntentData();
        findViewID();
        initUI();
        setListener();
    }

    private void getIntentData()
    {
        Intent intent = getIntent();
        mIntExtra = intent.getIntExtra(Constant.IntentKey.GET_MAP_LOC_KEY, -1);
        mCityName = intent.getStringExtra(Constant.IntentKey.CITY);
    }

    private void findViewID()
    {
        mIv_zuche_stores_loc = (ImageView) findViewById(R.id.iv_zuche_stores_loc);
        mIv_zuche_stores_map_back = (ImageView) findViewById(R.id.iv_zuche_stores_map_back);
        mMapView = (MapView) findViewById(R.id.mapview_dache_stores);
        mIv_org_b_poi = (ImageView) findViewById(R.id.iv_org_b_poi);
        mTv_org_show_where = (TextView) findViewById(R.id.tv_org_show_where);
        mTv_done = (TextView) findViewById(R.id.tv_done);

        mIv_clear = (ImageView) findViewById(R.id.iv_clear);
        mEt_search_zone = (EditText) findViewById(R.id.et_search_zone);
        mLv_search_ruslt = (ListView) findViewById(R.id.lv_search_ruslt);
    }

    private void initUI()
    {
        initLocationImage();
        initBaseMap();
        initLoc();
        initSearchRusltListView();
        initSearchEditText();
        initBaiDuSuggestionSearchInstance();
    }

    private void initSearchRusltListView()
    {
        mLv_search_ruslt.setAdapter(mSuggestionSearchAdapter);
        mLv_search_ruslt.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
            }
        });
        mLv_search_ruslt.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mSearch = GeoCoder.newInstance();
                mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener()
                {
                    public void onGetGeoCodeResult(GeoCodeResult result)
                    {
                        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                        {
                            //没有检索到结果
                        }
                        //获取地理编码结果
                        mTv_org_show_where.setText(result.getAddress());
                        mapStatusUpDate(result.getLocation(), mBaiduMap);
                        mLv_search_ruslt.setVisibility(View.GONE);
                        mEt_search_zone.setText("");
                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result)
                    {
                        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                        {
                            //没有找到检索结果
                        }
                        //获取反向地理编码结果
                    }
                });
                mSearch.geocode(new GeoCodeOption()
                        .city(mAllSuggestions.get(position).city)
                        .address(mAllSuggestions.get(position).key));
            }
        });
    }

    private void initSearchEditText()
    {
        mEt_search_zone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if ("".equalsIgnoreCase(s.toString()))
                {
                    mIv_clear.setVisibility(View.GONE);
                    mLv_search_ruslt.setVisibility(View.GONE);
                } else
                {
                    mIv_clear.setVisibility(View.VISIBLE);
                    // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                            .keyword(s.toString())
                            .city(mCityName));
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void initBaiDuSuggestionSearchInstance()
    {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener()
        {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult)
            {
                if (suggestionResult == null || suggestionResult.getAllSuggestions() == null)
                {
                    //未找到相关结果
                    Toast.makeText(MapForChooseAddrActivity.this, "未找到相关结果，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取在线建议检索结果
                mAllSuggestions = suggestionResult.getAllSuggestions();
                mLv_search_ruslt.setVisibility(View.VISIBLE);
                mSuggestionSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    class SuggestionSearchAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mAllSuggestions.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View layout = getLayoutInflater().inflate(R.layout.search_item, null);
            TextView suggestionText = (TextView) layout.findViewById(R.id.textView_search);
            if (mAllSuggestions != null && mAllSuggestions.size() > 0)
            {
                suggestionText.setText(mAllSuggestions.get(position).key);
            }
            return layout;
        }
    }


    /**
     * 初始化百度基本地图
     */
    public void initBaseMap()
    {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(19.0f);
        mBaiduMap.setMapStatus(msu);
        /**------百度地图UI手势操作相关------*/
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setOverlookingGesturesEnabled(false);//取消俯视手势操作
        mUiSettings.setRotateGesturesEnabled(false);//取消旋转手势操作
        setMapStatusChangeListener();
    }

    /**
     * 地图状态改变监听回调
     */
    private void setMapStatusChangeListener()
    {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener()
        {
            public void onMapStatusChangeStart(MapStatus mapStatus)
            {
            }

            public void onMapStatusChange(MapStatus mapStatus)
            {
            }

            public void onMapStatusChangeFinish(MapStatus mapStatus)
            {
                updateMapState(mapStatus);
            }
        });
    }

    /**
     * 更新地图移动后的状态
     */
    private void updateMapState(MapStatus status)
    {
        LatLng centerLatLng = status.target;
        /**----地图移动后获取的新坐标进行反地理编码----*/
        // 初始化搜索模块，注册事件监听------***地理反编码***------
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(this);
        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(centerLatLng));
    }

    /**
     * 设置逆地理编码查询回调接口【实现界面中心点不变，地图移动后可动态获得地图中心点地理位置信息】
     */
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult)
    {
    }

    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult)
    {
        if (reverseGeoCodeResult != null && reverseGeoCodeResult.getPoiList() != null)
        {
            List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
            if (poiList.size() > 0)
            {
                mTv_org_show_where.setText(poiList.get(0).name + "(" + poiList.get(0).address + ")");
            }
            mStLatLng = reverseGeoCodeResult.getLocation();
        }
    }

    /**
     * 百度初始化定位
     */
    private void initLoc()
    {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initLocationImage()
    {
        /**----初始化定位图标，使其底部尖角处位于中心点位置----*/
        if (mIntExtra == Constant.IntentKey.GET_MAP_LOC_GET)
        {
            mIv_org_b_poi.setImageResource(R.drawable.sent_icon);
        } else
        {
            mIv_org_b_poi.setImageResource(R.drawable.return_icon);
        }
        mIv_org_b_poi.measure(0, 0);
        int mManualLocationToInitMeasuredHeight = mIv_org_b_poi.getMeasuredHeight();
        mIv_org_b_poi.setY(-(mManualLocationToInitMeasuredHeight / 2));
        mTv_org_show_where.setY(-(mManualLocationToInitMeasuredHeight / 2));
    }

    private void setListener()
    {
        mIv_zuche_stores_map_back.setOnClickListener(this);
        mIv_zuche_stores_loc.setOnClickListener(this);
        mTv_done.setOnClickListener(this);
        mIv_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_stores_map_back:
                finish();
                break;
            case R.id.iv_zuche_stores_loc:
                mLocClient.start();
                mapStatusUpDate(new LatLng(mLocLatitude, mLocLongitude), mBaiduMap);
                break;
            case R.id.tv_done:
                Intent data = new Intent();
                data.putExtra(Constant.IntentKey.MAP_FOR_CHOOSE_ADDR, mTv_org_show_where.getText().toString().trim());
                setResult(Constant.ResultCode.MAP_FOR_CHOOSE_ADDR_RESULT_CODE, data);
                finish();
                break;
            case R.id.iv_clear:
                mEt_search_zone.setText("");
                mIv_clear.setVisibility(View.GONE);
                mAllSuggestions.clear();
                mLv_search_ruslt.setVisibility(View.GONE);
                mSuggestionSearchAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void mapStatusUpDate(LatLng latlng, BaiduMap baiduMap)
    {
        //地图移动到marker位置为中心
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latlng);
        baiduMap.animateMapStatus(msu);
    }


    @Override
    protected void onPause()
    {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        super.onDestroy();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation location)
        {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
            {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //初始化经纬度
            mLocLatitude = location.getLatitude();
            mLocLongitude = location.getLongitude();
            if (isFirstLoc)
            {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                mTv_org_show_where.setText(location.getAddrStr());
            }
        }

        public void onReceivePoi(BDLocation poiLocation)
        {
        }
    }
}