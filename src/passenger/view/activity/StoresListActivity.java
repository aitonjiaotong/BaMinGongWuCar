package passenger.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import passenger.constant.Constant;
import passenger.model.StoresMarkerInfo;
import passenger.model.StoresMarkerInfo.ContainsEntity;
import passenger.utils.ApiClient;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.VolleyListener;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StoresListActivity extends Activity implements View.OnClickListener
{

    private ImageView mIv_zuche_stores_map_back;
    private ListView mLv_stores_list;
    private StoresListAdapter mStoresListAdapter;
    private List<StoresMarkerInfo.ContainsEntity> mMStoresMarkerInfoContains = new ArrayList<ContainsEntity>();
    private TextView mTv_toshow_nothing;
//    private XRefreshView mCustom_view_refresh;
    private int mTotalNum;
    private int mPage;
    private int mIntExtra;//为了区分是取车还是还车
    private String mCityName;
    private LinearLayout mLl_loading_remind_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_list);

        getIntentData();
        findViewID();
        setListener();
        initUI();
        initData();
    }

    private void getIntentData()
    {
        Intent intent = getIntent();
        mIntExtra = intent.getIntExtra(Constant.IntentKey.GET_MAP_LOC_KEY, -1);
        mCityName = intent.getStringExtra(Constant.IntentKey.CITY);
    }

    private void initData()
    {
        //从后台服务端获取StoresMarkerInfo数据
        Map<String, String> params = new HashMap<String, String>();
        params.put("city", mCityName);
        params.put("page", mPage + "");
        mLl_loading_remind_progress_bar.setVisibility(View.VISIBLE);
        ApiClient.getStoresMarkersLatLng(StoresListActivity.this,  params, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                StoresMarkerInfo storesMarkerInfo = GsonUtils.parseJSON(s, StoresMarkerInfo.class);
                mTotalNum = storesMarkerInfo.getNum();
                if (storesMarkerInfo.getContains() != null && storesMarkerInfo.getContains().size() > 0)
                {
                    mPage++;
                    mMStoresMarkerInfoContains.addAll(storesMarkerInfo.getContains());
                    mStoresListAdapter.notifyDataSetChanged();
                    mTv_toshow_nothing.setVisibility(View.GONE);
                    mLl_loading_remind_progress_bar.setVisibility(View.GONE);
                } else
                {
                    mTv_toshow_nothing.setVisibility(View.VISIBLE);
                }


            }
        });
//        mCustom_view_refresh.stopLoadMore();//加载完数据后调用此方法
    }

    private void initUI()
    {
        mStoresListAdapter = new StoresListAdapter();
        mLv_stores_list.setAdapter(mStoresListAdapter);
        mLv_stores_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                data.putExtra(Constant.IntentKey.STORES_MAP_KEY, mMStoresMarkerInfoContains.get(position).getName());
                data.putExtra(Constant.IntentKey.STORES_ID_KEY, mMStoresMarkerInfoContains.get(position).getId());
                setResult(Constant.ResultCode.CHOOSE_STORE, data);
                finish();
            }
        });
        initXRefreshView();
    }

    private void initXRefreshView()
    {
//        mCustom_view_refresh.setPullRefreshEnable(false);
//        mCustom_view_refresh.setPullLoadEnable(false);
//        mCustom_view_refresh.setPinnedTime(1000);
//        mCustom_view_refresh.setAutoLoadMore(true);
//        mCustom_view_refresh.setMoveForHorizontal(true);
//        mCustom_view_refresh.setCustomFooterView(new CustomerFooter(StoresListActivity.this));
//        mCustom_view_refresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//
//            }
//
//            @Override
//            public void onLoadMore(boolean isSlience)
//            {
//                if (mTotalNum > mPage)
//                {
//                    initData();
//                } else
//                {
//                    mCustom_view_refresh.stopLoadMore();
//                    Toast.makeText(StoresListActivity.this, "没有更多门店地址", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void setListener()
    {
        mIv_zuche_stores_map_back.setOnClickListener(this);
    }

    private void findViewID()
    {
        mIv_zuche_stores_map_back = (ImageView) findViewById(R.id.iv_zuche_stores_map_back);
        mLv_stores_list = (ListView) findViewById(R.id.lv_stores_list);
        mTv_toshow_nothing = (TextView) findViewById(R.id.tv_toshow_nothing);
//        mCustom_view_refresh = (XRefreshView) findViewById(R.id.custom_view_refresh);
        mLl_loading_remind_progress_bar = (LinearLayout) findViewById(R.id.ll_loading_remind_progress_bar);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_stores_map_back:
                finish();
                break;
        }
    }

    class StoresListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mMStoresMarkerInfoContains.size();
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
            View stores_list_layout = getLayoutInflater().inflate(R.layout.layout_stores_list_item, null);
            TextView tv_stores_name = (TextView) stores_list_layout.findViewById(R.id.tv_stores_name);
            TextView tv_stores_phone = (TextView) stores_list_layout.findViewById(R.id.tv_stores_phone);
            TextView tv_stores_addr = (TextView) stores_list_layout.findViewById(R.id.tv_stores_addr);
            if (mMStoresMarkerInfoContains != null && mMStoresMarkerInfoContains.size() > 0)
            {
                tv_stores_name.setText(mMStoresMarkerInfoContains.get(position).getName());
                tv_stores_phone.setText(mMStoresMarkerInfoContains.get(position).getPhone());
                tv_stores_addr.setText(mMStoresMarkerInfoContains.get(position).getAddress());
            }

            return stores_list_layout;
        }
    }
}
