package passenger.view.frgment;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.common.User;
import passenger.model.OrderListInfo;
import passenger.model.OrderListInfo.ContainsBean;
import passenger.model.OrganizationCodeListInfo;
import passenger.model.UserChooseScreeningInfo;
import passenger.utils.ApiClient;
import passenger.utils.DateCommonUtils;
import passenger.utils.LoginState;
import passenger.view.activity.OrderDetailActivity;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.UILUtils;
import shane_library.shane.utils.VolleyListener;
import shane_library.shane.widget.GridView4ScrollView;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;


public class OrderFragment extends Fragment implements View.OnClickListener
{
    private View mInflate;
    private TextView mTv_btn_screening;
    private PopupWindow mPopupWindow;
    private List<String> mOrganizationContainsData = new ArrayList<String>();
    private View mPopViewLayout;
    private GridView4ScrollView mGv_organization_code_list;
    private MyGridViewAdapter mMyGridViewAdapter;
    private int mCheckedIndex = -1;
    private LinearLayout mLl_organization_pop_all;
    private LinearLayout mLl_organization_pop_week;
    private LinearLayout mLl_organization_pop_thismonth;
    private LinearLayout mLl_organization_pop_lastmonth;
    private CheckBox mCheckBox_all;
    private CheckBox mCheckBox_week;
    private CheckBox mCheckBox_thismonth;
    private CheckBox mCheckBox_lastmonth;
    private UserChooseScreeningInfo mUserChooseScreeningInfo = new UserChooseScreeningInfo();
    private TextView mTv_btn_cancel_disspop;
    private TextView mTv_btn_comfire_pop_choose;
    private boolean mIsChooseOrganization = false;
    private boolean mIsChooseTime;
    private TextView mTextView_what_order;
    private String mTimeStr;
    private LinearLayout mLl_organization_pop_y;
    private LinearLayout mLl_organization_pop_j;
    private LinearLayout mLl_organization_pop_done;
    private LinearLayout mLl_organization_pop_d;
    private LinearLayout mLl_organization_pop_c;
    private CheckBox mCheckBox_y;
    private CheckBox mCheckBox_j;
    private CheckBox mCheckBox_done;
    private CheckBox mCheckBox_d;
    private CheckBox mCheckBox_c;
    private LinearLayout mLl_organization_pop_order_all;
    private CheckBox mCheckBox_order_all;
    private boolean mIsChooseOrderStage;
    private String mOrderStageStr = "全部订单";
    private String mChooseOrganizationStr;
//    private XRefreshView mCustom_view_refresh;
    private ListView mListView_org_order_list;
    private TextView mTv_jg_order_list_remind;
    private TextView mTv_jg_order_list_unlogin;
    private LinearLayout mLl_loading_remind_progress_bar;
    private boolean isScreeningOrder = false;
    private int mPage = 0;
    private String mAccountId;
    private List<OrderListInfo.ContainsBean> mContains = new ArrayList<ContainsBean>();
    private MyAdapter mAdapter;
    private int mTotalNum;
    private LinearLayout mLl_organization_pop_wpay;
    private CheckBox mCheckBox_wpay;
    private TextView mTv_btn_organization_pop_order_account;
	private TextView text_zuche;
	private TextView text_zucheLine;
	private TextView text_dache;
	private TextView text_dacheLine;
	private FrameLayout frame_dache;
	private int aiton_basic_color;
	private int black;
	private int white;

    public OrderFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mInflate == null)
        {
            mInflate = inflater.inflate(R.layout.fragment_order, null);
            findID();
            initUI();
            setListener();
//            initOrderListData();
        }
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null)
        {
            parent.removeView(mInflate);
        }
        return mInflate;
    }


    private void findID()
    {
    	text_zuche = (TextView) mInflate.findViewById(R.id.Text_zuche);
    	text_zucheLine = (TextView) mInflate.findViewById(R.id.Text_zucheLine);
    	text_dache = (TextView) mInflate.findViewById(R.id.text_dache);
    	text_dacheLine = (TextView) mInflate.findViewById(R.id.text_dacheLine);
    	frame_dache = (FrameLayout) mInflate.findViewById(R.id.frame_dache);
    	aiton_basic_color = getResources().getColor(R.color.aiton_basic_color);
    	black = getResources().getColor(R.color.black);
    	white = getResources().getColor(R.color.white);
    	
        mTv_btn_screening = (TextView) mInflate.findViewById(R.id.tv_btn_screening);
        mTextView_what_order = (TextView) mInflate.findViewById(R.id.textView_what_order);
        findPopViewID();
        findOrderListViewID();
    }


    private void findPopViewID()
    {
        mPopViewLayout = getActivity().getLayoutInflater().inflate(R.layout.layout_screening_pop, null);
        mGv_organization_code_list = (GridView4ScrollView) mPopViewLayout.findViewById(R.id.gv_organization_code_list);

        //本机账号下所有订单
        mTv_btn_organization_pop_order_account = (TextView) mPopViewLayout.findViewById(R.id.tv_btn_organization_pop_order_account);

        //选择时间段
        mLl_organization_pop_all = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_all);
        mLl_organization_pop_week = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_week);
        mLl_organization_pop_thismonth = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_thismonth);
        mLl_organization_pop_lastmonth = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_lastmonth);
        mCheckBox_all = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_all);
        mCheckBox_week = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_week);
        mCheckBox_thismonth = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_thismonth);
        mCheckBox_lastmonth = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_lastmonth);

        //选择订单状态
        mLl_organization_pop_order_all = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_order_all);//全部订单状态
        mLl_organization_pop_y = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_y);//预约成功订单
        mLl_organization_pop_j = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_j);//进行中订单
        mLl_organization_pop_done = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_done);//已完成订单
        mLl_organization_pop_d = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_d);//等待结算订单
        mLl_organization_pop_c = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_c);//已取消订单
        mLl_organization_pop_wpay = (LinearLayout) mPopViewLayout.findViewById(R.id.ll_organization_pop_wpay);//已取消订单

        mCheckBox_order_all = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_order_all);
        mCheckBox_y = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_y);
        mCheckBox_j = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_j);
        mCheckBox_done = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_done);
        mCheckBox_d = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_d);
        mCheckBox_c = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_c);
        mCheckBox_wpay = (CheckBox) mPopViewLayout.findViewById(R.id.checkBox_wpay);

        mTv_btn_cancel_disspop = (TextView) mPopViewLayout.findViewById(R.id.tv_btn_cancel_disspop);
        mTv_btn_comfire_pop_choose = (TextView) mPopViewLayout.findViewById(R.id.tv_btn_comfire_pop_choose);
    }

    private void findOrderListViewID()
    {
//        mCustom_view_refresh = (XRefreshView) mInflate.findViewById(R.id.custom_view_refresh);
        mListView_org_order_list = (ListView) mInflate.findViewById(R.id.listView_org_order_list);
        mTv_jg_order_list_remind = (TextView) mInflate.findViewById(R.id.tv_jg_order_list_remind);
        mTv_jg_order_list_unlogin = (TextView) mInflate.findViewById(R.id.tv_jg_order_list_unlogin);
        mLl_loading_remind_progress_bar = (LinearLayout) mInflate.findViewById(R.id.ll_loading_remind_progress_bar);
    }

    private void initUI()
    {
        mAdapter = new MyAdapter();
        initProgressBar();
        initGridView();
        initXRefreshView();
        initOrderListView();
    }

    private void initOrderListView()
    {
    	View jiazaigengduo = getActivity().getLayoutInflater().inflate(R.layout.jiazaigengduo, null);
    	mListView_org_order_list.addFooterView(jiazaigengduo);
        mListView_org_order_list.setAdapter(mAdapter);
        mListView_org_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            	if(position<mContains.size()){
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("order_id", mContains.get(position).getOrder().getId());
                startActivity(intent);
            	}else if(position==mContains.size()){
            		if (mTotalNum > mPage)
                      {
                          if (isScreeningOrder)
                          {
                              refreshOrderList();
                          } else
                          {
                              initOrderListData();
                          }
                      } else
                      {
                          Toast.makeText(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT).show();
                      }
				}
            }
        });
    }

    private void initProgressBar()
    {
        mLl_loading_remind_progress_bar.setVisibility(View.VISIBLE);
    }


    private void initGridView()
    {
        mGv_organization_code_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (mOrganizationContainsData.size() != 0)
                {
                    mIsChooseOrganization = true;
                    mCheckedIndex = position;
                    mMyGridViewAdapter.notifyDataSetChanged();
                    mUserChooseScreeningInfo.setOrganizationCode(mOrganizationContainsData.get(position));
                    mChooseOrganizationStr = "机构编号:(" + mOrganizationContainsData.get(position) + ")";
                }
            }
        });

    }

    private void initXRefreshView()
    {
//        mCustom_view_refresh.setPullRefreshEnable(false);
//        mCustom_view_refresh.setPullLoadEnable(false);
//        mCustom_view_refresh.setPinnedTime(1000);
//        mCustom_view_refresh.setAutoLoadMore(true);
//        mCustom_view_refresh.setMoveForHorizontal(true);
//        mCustom_view_refresh.setCustomFooterView(new CustomerFooter(getActivity()));
//        mCustom_view_refresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//            }
//
//            @Override
//            public void onLoadMore(boolean isSlience)
//            {
//                if (mTotalNum > mPage)
//                {
//                    if (isScreeningOrder)
//                    {
//                        refreshOrderList();
//                    } else
//                    {
//                        initOrderListData();
//                    }
//                } else
//                {
//                    mCustom_view_refresh.stopLoadMore();
//                    Toast.makeText(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


    private void initOrganizationCodeData()
    {
        Map<String, String> params = new HashMap<String, String>();
        if (LoginState.getInstance(getActivity()).isLogin())
        {
            params.put("phone", LoginState.getInstance(getActivity()).getLoginInfo().getPhoneNum());
            ApiClient.getOrganizationCodeList(getActivity(), params, new VolleyListener()
            {
                @Override
                public void onErrorResponse(VolleyError volleyError)
                {
                }

                @Override
                public void onResponse(String s)
                {
                    OrganizationCodeListInfo organizationCodeListInfo = GsonUtils.parseJSON(s, OrganizationCodeListInfo.class);
                    if (organizationCodeListInfo.getContains() != null && organizationCodeListInfo.getContains().size() > 0)
                    {
                        mOrganizationContainsData.clear();
                        mOrganizationContainsData.addAll(organizationCodeListInfo.getContains());
                        mMyGridViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }


    private void setListener()
    {
        mTv_btn_screening.setOnClickListener(this);
        mTv_btn_organization_pop_order_account.setOnClickListener(this);
        mTv_btn_cancel_disspop.setOnClickListener(this);
        mTv_btn_comfire_pop_choose.setOnClickListener(this);
        mLl_organization_pop_all.setOnClickListener(this);
        mLl_organization_pop_week.setOnClickListener(this);
        mLl_organization_pop_thismonth.setOnClickListener(this);
        mLl_organization_pop_lastmonth.setOnClickListener(this);
        mLl_organization_pop_order_all.setOnClickListener(this);
        mLl_organization_pop_y.setOnClickListener(this);
        mLl_organization_pop_j.setOnClickListener(this);
        mLl_organization_pop_done.setOnClickListener(this);
        mLl_organization_pop_d.setOnClickListener(this);
        mLl_organization_pop_c.setOnClickListener(this);
        mLl_organization_pop_wpay.setOnClickListener(this);
        mInflate.findViewById(R.id.rela_zuche).setOnClickListener(this);
        mInflate.findViewById(R.id.rela_dache).setOnClickListener(this);
    }

    private void initOrderListData()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account_id", mAccountId);
        params.put("page", mPage + "");
        Log.e("account_id", "account_id"+mAccountId);
        ApiClient.getOrderList4Organization(getActivity(), params, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            	
            }

            @Override
            public void onResponse(String s)
            {
                Log.e("OrderFragment", "onResponse: --本机账号下所有订单列表->>" + s);
                mPage++;
                mLl_loading_remind_progress_bar.setVisibility(View.GONE);//数据加载完成后，加载框—不可见
                OrderListInfo orderListInfo = GsonUtils.parseJSON(s, OrderListInfo.class);
                mTotalNum = orderListInfo.getNum();
                if (orderListInfo.getContains() != null && orderListInfo.getContains().size() > 0)
                {
                    //后台请求到的数据
                    mContains.addAll(orderListInfo.getContains());
                }
                if (mContains != null && mContains.size() > 0)
                {
                    //有订单数据
                } else
                {
                    //无订单数据
                    mTv_jg_order_list_remind.setVisibility(View.VISIBLE);//未查到相关订单
                }
//                mCustom_view_refresh.stopLoadMore();
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
        case R.id.rela_zuche:
        	mTv_btn_screening.setVisibility(View.VISIBLE);
        	text_dache.setTextColor(black);
        	text_dacheLine.setBackgroundColor(white);
        	text_zuche.setTextColor(aiton_basic_color);
        	text_zucheLine.setBackgroundColor(aiton_basic_color);
        	mListView_org_order_list.setVisibility(View.VISIBLE);
        	frame_dache.setVisibility(View.GONE);
        	break;
        case R.id.rela_dache:
        	mTv_btn_screening.setVisibility(View.GONE);
        	text_dache.setTextColor(aiton_basic_color);
        	text_dacheLine.setBackgroundColor(aiton_basic_color);
        	text_zuche.setTextColor(black);
        	text_zucheLine.setBackgroundColor(white);
        	mListView_org_order_list.setVisibility(View.GONE);
        	frame_dache.setVisibility(View.VISIBLE);
        	break;
            case R.id.tv_btn_screening:
                showPopCliScan();
                break;
            //监听订单状态选择---------------Start
            case R.id.ll_organization_pop_order_all:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_order_all);
                break;
            case R.id.ll_organization_pop_y:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_y);
                break;
            case R.id.ll_organization_pop_j:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_j);
                break;
            case R.id.ll_organization_pop_done:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_done);
                break;
            case R.id.ll_organization_pop_d:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_d);
                break;
            case R.id.ll_organization_pop_c:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_c);
                break;
            case R.id.ll_organization_pop_wpay:
                setChooseOrderStageCheckBox(R.id.ll_organization_pop_wpay);
                break;
            //监听订单状态选择---------------Start
            case R.id.ll_organization_pop_all:
                setChooseTimeCheckBox(R.id.ll_organization_pop_all);
                break;
            case R.id.ll_organization_pop_week:
                setChooseTimeCheckBox(R.id.ll_organization_pop_week);
                break;
            case R.id.ll_organization_pop_thismonth:
                setChooseTimeCheckBox(R.id.ll_organization_pop_thismonth);
                break;
            case R.id.ll_organization_pop_lastmonth:
                setChooseTimeCheckBox(R.id.ll_organization_pop_lastmonth);
                break;
            //监听时间段选择---------------Start
            case R.id.tv_btn_organization_pop_order_account:
                mPopupWindow.dismiss();
                mContains.clear();
                mAdapter.notifyDataSetChanged();
                mLl_loading_remind_progress_bar.setVisibility(View.VISIBLE);
                initOrderListData();
                break;
            case R.id.tv_btn_cancel_disspop:
                setDefaultStateOfPopu();
                mPopupWindow.dismiss();
                break;
            case R.id.tv_btn_comfire_pop_choose:
                if (mIsChooseOrganization && mIsChooseTime && mIsChooseOrderStage)
                {
                    setDefaultStateOfPopu();
                    mPopupWindow.dismiss();
                    mTextView_what_order.setText(mChooseOrganizationStr + mTimeStr + mOrderStageStr);
                    isScreeningOrder = true;
                    mPage = 0;
                    mContains.clear();
                    mAdapter.notifyDataSetChanged();
                    refreshOrderList();
                } else
                {
                    if (!mIsChooseOrganization)
                    {
                        Toast.makeText(getActivity(), "请选择机构编号", Toast.LENGTH_SHORT).show();
                    }
                    if (!mIsChooseTime)
                    {
                        Toast.makeText(getActivity(), "请选择时间段", Toast.LENGTH_SHORT).show();
                    }
                    if (!mIsChooseOrderStage)
                    {
                        Toast.makeText(getActivity(), "请选择订单状态", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void setDefaultStateOfPopu()
    {
        mCheckedIndex = -1;
        mMyGridViewAdapter.notifyDataSetChanged();
        mIsChooseTime = false;
        mIsChooseOrganization = false;
        mIsChooseOrderStage = false;
        mCheckBox_all.setChecked(false);
        mCheckBox_week.setChecked(false);
        mCheckBox_thismonth.setChecked(false);
        mCheckBox_lastmonth.setChecked(false);
        mCheckBox_order_all.setChecked(false);
        mCheckBox_y.setChecked(false);
        mCheckBox_j.setChecked(false);
        mCheckBox_done.setChecked(false);
        mCheckBox_d.setChecked(false);
        mCheckBox_c.setChecked(false);
        mCheckBox_wpay.setChecked(false);
    }

    private void setChooseOrderStageCheckBox(int viewId)
    {
        mIsChooseOrderStage = true;
        switch (viewId)
        {
            case R.id.ll_organization_pop_order_all://全部订单
                mCheckBox_order_all.setChecked(true);
                mCheckBox_y.setChecked(false);
                mCheckBox_j.setChecked(false);
                mCheckBox_done.setChecked(false);
                mCheckBox_d.setChecked(false);
                mCheckBox_c.setChecked(false);
                mCheckBox_wpay.setChecked(false);
                mUserChooseScreeningInfo.setFlag("all");
                mOrderStageStr = "的全部订单";
                break;
            case R.id.ll_organization_pop_y://4:预约成功
                mCheckBox_order_all.setChecked(false);
                mCheckBox_y.setChecked(true);
                mCheckBox_j.setChecked(false);
                mCheckBox_done.setChecked(false);
                mCheckBox_d.setChecked(false);
                mCheckBox_c.setChecked(false);
                mCheckBox_wpay.setChecked(false);
                mUserChooseScreeningInfo.setFlag("4");
                mOrderStageStr = "预约成功的订单";
                break;
            case R.id.ll_organization_pop_j://0:进行中
                mCheckBox_order_all.setChecked(false);
                mCheckBox_y.setChecked(false);
                mCheckBox_j.setChecked(true);
                mCheckBox_done.setChecked(false);
                mCheckBox_d.setChecked(false);
                mCheckBox_c.setChecked(false);
                mCheckBox_wpay.setChecked(false);
                mUserChooseScreeningInfo.setFlag("0");
                mOrderStageStr = "进行中的订单";
                break;
            case R.id.ll_organization_pop_done://1:完成
                mCheckBox_order_all.setChecked(false);
                mCheckBox_y.setChecked(false);
                mCheckBox_j.setChecked(false);
                mCheckBox_done.setChecked(true);
                mCheckBox_d.setChecked(false);
                mCheckBox_c.setChecked(false);
                mCheckBox_wpay.setChecked(false);
                mUserChooseScreeningInfo.setFlag("1");
                mOrderStageStr = "已完成的订单";
                break;
            case R.id.ll_organization_pop_d://3:等待结算(已还车)
                mCheckBox_order_all.setChecked(false);
                mCheckBox_y.setChecked(false);
                mCheckBox_j.setChecked(false);
                mCheckBox_done.setChecked(false);
                mCheckBox_d.setChecked(true);
                mCheckBox_c.setChecked(false);
                mCheckBox_wpay.setChecked(false);
                mUserChooseScreeningInfo.setFlag("3");
                mOrderStageStr = "等待结算的订单";
                break;
            case R.id.ll_organization_pop_c://2:取消
                mCheckBox_order_all.setChecked(false);
                mCheckBox_y.setChecked(false);
                mCheckBox_j.setChecked(false);
                mCheckBox_done.setChecked(false);
                mCheckBox_d.setChecked(false);
                mCheckBox_c.setChecked(true);
                mCheckBox_wpay.setChecked(false);
                mUserChooseScreeningInfo.setFlag("2");
                mOrderStageStr = "已取消的订单";
                break;
            case R.id.ll_organization_pop_wpay:
                mCheckBox_order_all.setChecked(false);
                mCheckBox_y.setChecked(false);
                mCheckBox_j.setChecked(false);
                mCheckBox_done.setChecked(false);
                mCheckBox_d.setChecked(false);
                mCheckBox_c.setChecked(false);
                mCheckBox_wpay.setChecked(true);
                mUserChooseScreeningInfo.setFlag("6");
                mOrderStageStr = "等待付款";
                break;
        }
    }

    private void setChooseTimeCheckBox(int viewId)
    {
        mIsChooseTime = true;
        switch (viewId)
        {
            case R.id.ll_organization_pop_all:
                mCheckBox_all.setChecked(true);
                mCheckBox_week.setChecked(false);
                mCheckBox_thismonth.setChecked(false);
                mCheckBox_lastmonth.setChecked(false);
                mUserChooseScreeningInfo.setStartMillise("0");
                mUserChooseScreeningInfo.setEndMillise("0");
                mTimeStr = "";
                break;
            case R.id.ll_organization_pop_week:
                mCheckBox_all.setChecked(false);
                mCheckBox_week.setChecked(true);
                mCheckBox_thismonth.setChecked(false);
                mCheckBox_lastmonth.setChecked(false);
                Map weekDay = DateCommonUtils.getWeekDay(DateCommonUtils.YYYY_MM_DD);
                mUserChooseScreeningInfo.setStartMillise((String) weekDay.get("mon"));
                mUserChooseScreeningInfo.setEndMillise((String) weekDay.get("sun"));
                mTimeStr = "本周(" + weekDay.get("mon") + "到" + weekDay.get("sun") + ")";
                break;
            case R.id.ll_organization_pop_thismonth:
                mCheckBox_all.setChecked(false);
                mCheckBox_week.setChecked(false);
                mCheckBox_thismonth.setChecked(true);
                mCheckBox_lastmonth.setChecked(false);
                Map monthDate = DateCommonUtils.getMonthDate(DateCommonUtils.YYYY_MM_DD);
                mUserChooseScreeningInfo.setStartMillise((String) monthDate.get("monthF"));
                mUserChooseScreeningInfo.setEndMillise((String) monthDate.get("monthL"));
                mTimeStr = "本月(" + monthDate.get("monthF") + "到" + monthDate.get("monthL") + ")";
                break;
            case R.id.ll_organization_pop_lastmonth:
                mCheckBox_all.setChecked(false);
                mCheckBox_week.setChecked(false);
                mCheckBox_thismonth.setChecked(false);
                mCheckBox_lastmonth.setChecked(true);
                Map preMonthDay = DateCommonUtils.getPreMonthDay(DateCommonUtils.YYYY_MM_DD);
                mUserChooseScreeningInfo.setStartMillise((String) preMonthDay.get("preMonthF"));
                mUserChooseScreeningInfo.setEndMillise((String) preMonthDay.get("preMonthL"));
                mTimeStr = "上月(" + preMonthDay.get("preMonthF") + "到" + preMonthDay.get("preMonthL") + ")";
                break;
        }
    }


    private void showPopCliScan()
    {
        mMyGridViewAdapter = new MyGridViewAdapter();
        mGv_organization_code_list.setAdapter(mMyGridViewAdapter);
        initOrganizationCodeData();
        CreatePopupWindow(mPopViewLayout, mTv_btn_screening);

    }

    private void CreatePopupWindow(View popViewLayout, View dropDownView)
    {
        //最后一个参数为true，点击PopupWindow消失,宽必须为match，不然肯呢个会导致布局显示不完全
        mPopupWindow = new PopupWindow(popViewLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置外部点击是否有效
        mPopupWindow.setOutsideTouchable(true);
        //设置背景变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        mPopupWindow.setBackgroundDrawable(bitmapDrawable);
        mPopupWindow.showAsDropDown(dropDownView);
    }

    @Override
    public void onStart()
    {
        if (LoginState.getInstance(getActivity()).isLogin())
        {
            mTv_btn_screening.setVisibility(View.VISIBLE);
            mAccountId = LoginState.getInstance(getActivity()).getLoginInfo().getUserId();
            mTv_jg_order_list_unlogin.setVisibility(View.GONE);//登录状态文字提示—不可见
            mLl_loading_remind_progress_bar.setVisibility(View.VISIBLE);//登录状态加载框—可见
            //登录状态
            mContains.clear();
            mAdapter.notifyDataSetChanged();
            mPage = 0;
            if (isScreeningOrder)
            {
                refreshOrderList();
            } else
            {
                initOrderListData();
            }
        } else
        {
            mTv_btn_screening.setVisibility(View.GONE);
            mContains.clear();
            mAdapter.notifyDataSetChanged();
            mTv_jg_order_list_unlogin.setVisibility(View.VISIBLE);//未登录状态文字提示—可见
            mLl_loading_remind_progress_bar.setVisibility(View.GONE);//未登录状态加载框—不可见
        }
        super.onStart();
    }

    public void refreshOrderList()
    {
        mLl_loading_remind_progress_bar.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<String, String>();
        params.put("institutionsCode", mUserChooseScreeningInfo.getOrganizationCode());
        params.put("start", mUserChooseScreeningInfo.getStartMillise());
        params.put("end", mUserChooseScreeningInfo.getEndMillise());
        params.put("page", mPage + "");

        if (!"all".equals(mUserChooseScreeningInfo.getFlag()))
        {
            params.put("flag", mUserChooseScreeningInfo.getFlag());
        }
        ApiClient.getScreeningOrderList(getActivity(), params, new VolleyListener()
        {
            @Override
            public void onResponse(String s)
            {
                Log.e("OrderFragment", "onResponse: --筛选订单列表->>" + s);
                mPage++;
                mLl_loading_remind_progress_bar.setVisibility(View.GONE);//数据加载完成后，加载框—不可见
                OrderListInfo orderListInfo = GsonUtils.parseJSON(s, OrderListInfo.class);
                mTotalNum = orderListInfo.getNum();
                mContains.clear();
                mAdapter.notifyDataSetChanged();
                if (orderListInfo.getContains() != null && orderListInfo.getContains().size() > 0)
                {
                    //后台请求到的数据
                    mContains.addAll(orderListInfo.getContains());
                    //有订单数据
                    mTv_jg_order_list_remind.setVisibility(View.GONE);//未查到相关订单
                } else
                {
                    //无订单数据
                    mTv_jg_order_list_remind.setVisibility(View.VISIBLE);//未查到相关订单
                    Log.e("OrderFragment", "onResponse: --没查询到数据 ->>");

                }
//                mCustom_view_refresh.stopLoadMore();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }
        });
    }

    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return mContains.size();
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
            View layout = getLayoutInflater(getArguments()).inflate(R.layout.jigouyongche_order_listitem, null);
            ImageView iv_car_img = (ImageView) layout.findViewById(R.id.iv_car_img);//显示汽车的图片
            TextView tv_car_name = (TextView) layout.findViewById(R.id.tv_car_name);//显示汽车的名称
            TextView tv_carriage_count = (TextView) layout.findViewById(R.id.tv_carriage_count);//显示汽车的厢数
            TextView tv_displacement = (TextView) layout.findViewById(R.id.tv_displacement);//显示汽车是否为自动档及排量
            TextView tv_car_seat_count = (TextView) layout.findViewById(R.id.tv_car_seat_count);//显示汽车可乘坐人数
            TextView tv_get_car_date = (TextView) layout.findViewById(R.id.tv_get_car_date);//显示取车时间的日期
            TextView tv_get_car_time = (TextView) layout.findViewById(R.id.tv_get_car_time);//显示取车时间的时间
            TextView tv_how_long = (TextView) layout.findViewById(R.id.tv_how_long);//显示租期
            TextView tv_return_car_date = (TextView) layout.findViewById(R.id.tv_return_car_date);//显示还车时间的日期
            TextView tv_return_car_time = (TextView) layout.findViewById(R.id.tv_return_car_time);//显示还车时间的时间
            TextView tv_order_num = (TextView) layout.findViewById(R.id.tv_order_list_num);
            TextView tv_order_stage = (TextView) layout.findViewById(R.id.tv_order_list_stage);
            TextView tv_what_discount = (TextView) layout.findViewById(R.id.tv_what_discount);
            if (mContains != null && mContains.size() > 0)
            {
                tv_car_name.setText(mContains.get(position).getCar().getModel());
                tv_carriage_count.setText(mContains.get(position).getCar().getBox());
                switch (mContains.get(position).getCar().getZidong())
                {
                    case 0:
                        tv_displacement.setText(mContains.get(position).getCar().getPailiang() + "自动");
                        break;
                    case 1:
                        tv_displacement.setText(mContains.get(position).getCar().getPailiang() + "手动");
                        break;
                }
                tv_car_seat_count.setText("可乘坐" + mContains.get(position).getCar().getSeat() + "人");
                tv_order_num.setText(mContains.get(position).getOrder().getId() + "");
                tv_get_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getZuchuDate(), DateCommonUtils.MM_DD));
                tv_get_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getZuchuDate(), DateCommonUtils.EE_HH_MM));
                if (mContains.get(position).getPrivilege() != null && mContains.get(position).getDiscount() != null)
                {
                    tv_what_discount.setText(mContains.get(position).getPrivilege().getName() + "--" + mContains.get(position).getDiscount().getName());
                }
                switch (mContains.get(position).getOrder().getFlag())
                {
//                    flag:订单状态 0:进行中 1：完成 2:取消 3：等待结算(已还车) 4:预约成功
                    case 0:
                        tv_order_stage.setText("订单进行中");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getPlanReturnDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getPlanReturnDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getPlanReturnDate(), DateCommonUtils.EE_HH_MM));
                        break;
                    case 1:
                        tv_order_stage.setText("订单已完成");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getHuancheDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.EE_HH_MM));
                        break;
                    case 2:
                        tv_order_stage.setText("订单已取消");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getPlanReturnDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getPlanReturnDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getPlanReturnDate(), DateCommonUtils.EE_HH_MM));
                        break;
                    case 3:
                        tv_order_stage.setText("订单等待结算(已还车)");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getHuancheDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.EE_HH_MM));
                        break;
                    case 4:
                        tv_order_stage.setText("预约成功");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getPlanReturnDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getPlanReturnDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getPlanReturnDate(), DateCommonUtils.EE_HH_MM));
                        break;
                    case 5:
                        tv_order_stage.setText("已结算未退压金");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getHuancheDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.EE_HH_MM));
                        break;
                    case 6:
                        tv_order_stage.setText("等待付款");
                        tv_how_long.setText(DateCommonUtils.getHowLongDay(mContains.get(position).getOrder().getZuchuDate(), mContains.get(position).getOrder().getHuancheDate()) + "天");
                        tv_return_car_date.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.MM_DD));
                        tv_return_car_time.setText(DateCommonUtils.getTheDateToString(mContains.get(position).getOrder().getHuancheDate(), DateCommonUtils.EE_HH_MM));
                        break;

                }
                UILUtils.displayImageNoAnim(mContains.get(position).getCar().getImage(), iv_car_img);
            }
            return layout;
        }
    }

    class MyGridViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            if (mOrganizationContainsData != null && mOrganizationContainsData.size() > 0)
            {

                return mOrganizationContainsData.size();
            } else
            {
                return 1;
            }
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
            View layout = View.inflate(getActivity(), R.layout.layout_screening_pop_gridview_item, null);
            TextView tv_screening_organization_num = (TextView) layout.findViewById(R.id.tv_screening_organization_num);
            if (mOrganizationContainsData != null && mOrganizationContainsData.size() > 0)
            {
                tv_screening_organization_num.setText(mOrganizationContainsData.get(position));
                if (position == mCheckedIndex)
                {
                    tv_screening_organization_num.setBackgroundResource(R.drawable.bg_organization_code_checked);
                } else
                {
                    tv_screening_organization_num.setBackgroundResource(R.drawable.bg_organization_code_normal);
                }
            } else
            {
                if (LoginState.getInstance(getActivity()).isLogin())
                {
                    tv_screening_organization_num.setText("加载中…");
                } else
                {
                    tv_screening_organization_num.setText("未登录无法加载…");
                }
            }
            return layout;
        }
    }
    
}
