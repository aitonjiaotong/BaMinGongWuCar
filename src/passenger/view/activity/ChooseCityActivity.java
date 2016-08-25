package passenger.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import passenger.constant.Constant;
import passenger.model.CityInfo;
import passenger.utils.ApiClient;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.VolleyListener;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChooseCityActivity extends Activity implements View.OnClickListener
{
    private Map<String, String> mParams = new HashMap<String, String>();
    private int mPage = 0;
    private ListView mLv_dachezuche_city_list;
    private List<String> mCity_list_data = new ArrayList<String>();
    private MyCityListAdapter mMyCityListAdapter;
    private ImageView mIv_zuche_choose_city_back;
    private TextView mTv_choose_city_more;
    private LinearLayout mLl_loading_remind_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_choose_city);
        findViewID();
        setListener();
        initUI();
        initData();
    }

    private void setListener()
    {
        mIv_zuche_choose_city_back.setOnClickListener(this);
        mTv_choose_city_more.setOnClickListener(this);
    }

    private void initData()
    {
        mLl_loading_remind_progress_bar.setVisibility(View.VISIBLE);
        mParams.put("page", mPage + "");
        ApiClient.getCityList(ChooseCityActivity.this, mParams, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                mLl_loading_remind_progress_bar.setVisibility(View.GONE);
                CityInfo cityInfo = GsonUtils.parseJSON(s, CityInfo.class);
                if (cityInfo.getNum() == (mPage + 1))
                {
                    mTv_choose_city_more.setVisibility(View.GONE);
                } else
                {
                    mTv_choose_city_more.setVisibility(View.VISIBLE);
                }
                mCity_list_data.addAll(cityInfo.getContains());
                mMyCityListAdapter.notifyDataSetChanged();
            }
        });

    }

    private void initUI()
    {
        mMyCityListAdapter = new MyCityListAdapter();
        mLv_dachezuche_city_list.setAdapter(mMyCityListAdapter);
        mLv_dachezuche_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                data.putExtra(Constant.IntentKey.CHOOSE_CITY, mCity_list_data.get(position));
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_POSITION, position);
                setResult(Constant.ResultCode.CHOOSE_CITY, data);
                finish();
            }
        });
    }

    private void findViewID()
    {
        mLv_dachezuche_city_list = (ListView) findViewById(R.id.lv_dachezuche_city_list);
        mIv_zuche_choose_city_back = (ImageView) findViewById(R.id.iv_zuche_choose_city_back);
        mTv_choose_city_more = (TextView) findViewById(R.id.tv_choose_city_more);
        mLl_loading_remind_progress_bar = (LinearLayout) findViewById(R.id.ll_loading_remind_progress_bar);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_city_back:
                finish();
                break;
            case R.id.tv_choose_city_more:
                mPage++;
                initData();
                break;
        }
    }

    class MyCityListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mCity_list_data.size();
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
            View layout = getLayoutInflater().inflate(R.layout.dachezuche_city_list_item, null);
            TextView tv_dache_city_name = (TextView) layout.findViewById(R.id.tv_dache_city_name);
            if (mCity_list_data != null && mCity_list_data.size() > 0)
            {
                tv_dache_city_name.setText(mCity_list_data.get(position));
            }
            return layout;
        }
    }

}
