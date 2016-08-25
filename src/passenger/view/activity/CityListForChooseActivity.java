package passenger.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import passenger.constant.Constant;
import passenger.model.CityListInfo;
import passenger.utils.GetRawResource;
import passenger.view.customview.IndexListView;
import shane_library.shane.utils.GsonUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aiton.yc.client.R;


public class CityListForChooseActivity extends Activity implements View.OnClickListener
{

    private String mLocCityName;
    private ImageView mIv_choose_city_back;
    private TextView mCurr_loction_name;
    private ListView mLv_everyting_city_list;
    private View mListViewHeadView;
    private List<CityListInfo.HotcityBean> mHotcity = new ArrayList<CityListInfo.HotcityBean>();
    private List<CityListInfo.AllcityBean> mAllcity = new ArrayList<CityListInfo.AllcityBean>();
    private List<String> mSearchCity = new ArrayList<String>();
    private LinearLayout mLl_for_hot_city1;
    private LinearLayout mLl_for_hot_city2;
    private TextView mTv_hot_city_name1;
    private TextView mTv_hot_city_name2;
    private TextView mTv_hot_city_name3;
    private TextView mTv_hot_city_name4;
    private TextView mTv_hot_city_name5;
    private TextView mTv_hot_city_name6;
    private CommonCityListViewAdapter mCommonCityListViewAdapter;
    private IndexListView mLetter_index_view;
    private TextView mTv_letter;
    private int mHeaderViewsCount;
    private ListView mLv_everyting_search_city_list;
    private MySearchListVewAdapter mMySearchListVewAdapter;
    private EditText mEt_everything_search_city;
    private ImageView mIv_search_clear;
    private String mUser_input;
    private MyOnScrollListener mMyOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_for_choose);
        initIntent();
        findViewID();
        setListener();
        initData();
        initUI();
    }

    private void initIntent()
    {
        Intent intent = getIntent();
        mLocCityName = intent.getStringExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY);
    }

    private void findViewID()
    {
        mIv_choose_city_back = (ImageView) findViewById(R.id.iv_choose_city_back);
        mCurr_loction_name = (TextView) findViewById(R.id.curr_loction_name);
        mLv_everyting_city_list = (ListView) findViewById(R.id.lv_everyting_city_list);

        mListViewHeadView = getLayoutInflater().inflate(R.layout.layout_everything_choose_city_list_head_view, null);
        mLl_for_hot_city1 = (LinearLayout) mListViewHeadView.findViewById(R.id.ll_for_hot_city1);
        mLl_for_hot_city2 = (LinearLayout) mListViewHeadView.findViewById(R.id.ll_for_hot_city2);
        mTv_hot_city_name1 = (TextView) mListViewHeadView.findViewById(R.id.tv_hot_city_name1);
        mTv_hot_city_name2 = (TextView) mListViewHeadView.findViewById(R.id.tv_hot_city_name2);
        mTv_hot_city_name3 = (TextView) mListViewHeadView.findViewById(R.id.tv_hot_city_name3);
        mTv_hot_city_name4 = (TextView) mListViewHeadView.findViewById(R.id.tv_hot_city_name4);
        mTv_hot_city_name5 = (TextView) mListViewHeadView.findViewById(R.id.tv_hot_city_name5);
        mTv_hot_city_name6 = (TextView) mListViewHeadView.findViewById(R.id.tv_hot_city_name6);

        mTv_letter = (TextView) findViewById(R.id.tv_letter);
        mLetter_index_view = (IndexListView) findViewById(R.id.letter_index_view);

        mLv_everyting_search_city_list = (ListView) findViewById(R.id.lv_everyting_search_city_list);
        mEt_everything_search_city = (EditText) findViewById(R.id.et_everything_search_city);
        mIv_search_clear = (ImageView) findViewById(R.id.iv_search_clear);

    }

    private void setListener()
    {
        mIv_choose_city_back.setOnClickListener(this);
        mTv_hot_city_name1.setOnClickListener(this);
        mTv_hot_city_name2.setOnClickListener(this);
        mTv_hot_city_name3.setOnClickListener(this);
        mTv_hot_city_name4.setOnClickListener(this);
        mTv_hot_city_name5.setOnClickListener(this);
        mTv_hot_city_name6.setOnClickListener(this);
        mIv_search_clear.setOnClickListener(this);
    }

    private void initData()
    {
        //加载本地城市列表LIST
        InputStream inputStream = getResources().openRawResource(R.raw.citylist);
        String cityInfo = GetRawResource.JsonTransformaString(inputStream);
        CityListInfo cityListInfo = GsonUtils.parseJSON(cityInfo, CityListInfo.class);
        mHotcity = cityListInfo.getHotcity();
        mAllcity = cityListInfo.getAllcity();
    }

    private void initUI()
    {
        mMyOnScrollListener = new MyOnScrollListener();
        mCurr_loction_name.setText(mLocCityName);
        initCommonCityListView();
        mLetter_index_view.setOnGetLetterListener(new MyGetLetterListener());
        initSearchCityListView();
        initSearchEditText();
    }

    private void initSearchEditText()
    {
        mIv_search_clear.setVisibility(View.GONE);
        mEt_everything_search_city.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mUser_input = s.toString();
                if ("".equals(mUser_input))
                {
                    mIv_search_clear.setVisibility(View.GONE);
                    mLv_everyting_search_city_list.setVisibility(View.GONE);
                } else
                {
                    mSearchCity.clear();
                    mIv_search_clear.setVisibility(View.VISIBLE);
                    mLv_everyting_search_city_list.setVisibility(View.VISIBLE);
                    mMySearchListVewAdapter.notifyDataSetChanged();
                    for (int i = 0; i < mAllcity.size(); i++)
                    {
                        CityListInfo.AllcityBean allcityBean = mAllcity.get(i);
                        if (allcityBean.getName().startsWith(mUser_input.trim()) || allcityBean.getLetter().startsWith(mUser_input.trim()))
                        {
                            mSearchCity.add(allcityBean.getName());
                            mMySearchListVewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void initCommonCityListView()
    {
        mLv_everyting_city_list.setOnScrollListener(mMyOnScrollListener);
        mCommonCityListViewAdapter = new CommonCityListViewAdapter();
        mLv_everyting_city_list.addHeaderView(mListViewHeadView, null, false);
        //暂时将热门城市后三个隐藏不显示
        mLl_for_hot_city2.setVisibility(View.GONE);
        //设置热门城市名称
        if (mHotcity != null && mHotcity.size() > 0)
        {
            mTv_hot_city_name1.setText(mHotcity.get(0).getName());
            mTv_hot_city_name2.setText(mHotcity.get(1).getName());
            mTv_hot_city_name3.setText(mHotcity.get(2).getName());
        }
        mHeaderViewsCount = mLv_everyting_city_list.getHeaderViewsCount();
        mLv_everyting_city_list.setAdapter(mCommonCityListViewAdapter);
        mLv_everyting_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int index = position - mHeaderViewsCount;
                Intent data = new Intent();
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mAllcity.get(index).getName());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mAllcity.get(index).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
            }
        });
    }

    private void initSearchCityListView()
    {
        mLv_everyting_search_city_list.setOnScrollListener(mMyOnScrollListener);
        mMySearchListVewAdapter = new MySearchListVewAdapter();
        mLv_everyting_search_city_list.setAdapter(mMySearchListVewAdapter);

        mLv_everyting_search_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mSearchCity.get(position));
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mAllcity.get(position).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
            }
        });
    }

    //搜索用户点击自定义的IndexListView控件所返回的字母
    public int searchLetter(String letter)
    {
        for (int i = 0; i < mAllcity.size(); i++)
        {
            String string = mAllcity.get(i).getPinyin();
            if (string.toUpperCase().startsWith(letter))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onClick(View v)
    {
        Intent data = new Intent();
        switch (v.getId())
        {
            case R.id.iv_choose_city_back:
                finish();
                break;
            case R.id.tv_hot_city_name1:
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mTv_hot_city_name1.getText().toString());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mHotcity.get(0).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
                break;
            case R.id.tv_hot_city_name2:
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mTv_hot_city_name2.getText().toString());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mHotcity.get(1).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
                break;
            case R.id.tv_hot_city_name3:
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mTv_hot_city_name3.getText().toString());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mHotcity.get(2).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
                break;
            case R.id.tv_hot_city_name4:
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mTv_hot_city_name4.getText().toString());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mHotcity.get(3).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
                break;
            case R.id.tv_hot_city_name5:
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mTv_hot_city_name5.getText().toString());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mHotcity.get(4).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
                break;
            case R.id.tv_hot_city_name6:
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mTv_hot_city_name6.getText().toString());
                data.putExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY, mHotcity.get(5).getProvince());
                setResult(Constant.ResultCode.CHOOSE_CITY_RESULT_CODE, data);
                finish();
                break;
            case R.id.iv_search_clear:
                mEt_everything_search_city.setText("");
                mIv_search_clear.setVisibility(View.GONE);
                break;
        }
    }


    class CommonCityListViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mAllcity.size();
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
            View layout = getLayoutInflater().inflate(R.layout.layout_everything_city_list_item, null);
            TextView tv_fristLetter = (TextView) layout.findViewById(R.id.tv_fristletter);
            TextView tv_city_name = (TextView) layout.findViewById(R.id.tv_city_name);
            if (mAllcity != null && mAllcity.size() > 0)
            {
                tv_city_name.setText(mAllcity.get(position).getName());

                String fristLetter = mAllcity.get(position).getPinyin().substring(0, 1).toUpperCase();
                tv_fristLetter.setText(fristLetter);
                if (position > 0)
                {
                    String prevLetter = mAllcity.get(position - 1).getPinyin().substring(0, 1).toUpperCase();
                    if (fristLetter.equals(prevLetter))
                    {
                        tv_fristLetter.setVisibility(View.GONE);
                    }
                }
            }
            return layout;
        }
    }

    class MySearchListVewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mSearchCity.size();
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
            View layout = getLayoutInflater().inflate(R.layout.layout_everything_city_search_list_item, null);
            TextView tv_city_name = (TextView) layout.findViewById(R.id.tv_city_name);
            if (mSearchCity != null && mSearchCity.size() > 0)
            {
                tv_city_name.setText(mSearchCity.get(position));
            }
            return layout;
        }
    }

    class MyGetLetterListener implements IndexListView.GetLetterListener
    {

        @Override
        public void onLetterChanged(String letter)
        {
            mTv_letter.setVisibility(View.VISIBLE);
            mTv_letter.setText(letter);
            //更新ListView的行数显示
            int searchLetter_index = searchLetter(letter);
            mLv_everyting_city_list.setSelection(searchLetter_index + mHeaderViewsCount);
        }

        @Override
        public void onActionUp()
        {
            mTv_letter.setVisibility(View.GONE);
        }
    }

    class MyOnScrollListener implements AbsListView.OnScrollListener
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
    }
}

