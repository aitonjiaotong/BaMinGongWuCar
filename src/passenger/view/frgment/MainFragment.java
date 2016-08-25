package passenger.view.frgment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.onetaxi.NewMainActivityNew;
import passenger.constant.Constant;
import passenger.model.BannerInfo;
import passenger.model.OilPriceInfo;
import passenger.utils.ApiClient;
import passenger.view.activity.CityListForChooseActivity;
import passenger.view.activity.OrgHomeActivity;
import passenger.view.customview.FixedSpeedScroller;
import passenger.view.customview.ViewPagerIndicator;
import shane_library.shane.upgrade.UpgradeUtils;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.TextViewUtils;
import shane_library.shane.utils.UILUtils;
import shane_library.shane.utils.VolleyListener;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements View.OnClickListener
{
    private View mInflate;
    private ImageView mIv_cliscan;
    private ImageView mIv_cliscan_close;
    private ImageView mIv_cli_scan_show;
    private PopupWindow mPopupWindow;
    private TextView mTv_loc_city_name;
    private GeoCoder mSearch;
    private String mLocCityName;
    private TextView mTv_oil_price_ninety_five;
    private TextView mTv_oil_price_ninety_two;
    private TextView mTv_oil_price_ninety;
    private TextView mTv_oil_price_zero;
    private List<OilPriceInfo> mOilPriceInfoList = new ArrayList<OilPriceInfo>();
    private String mProvince;
    private int mIndex;
    private boolean isFristLocation = true;
    private ImageView mAbc_spinner_mtrl_am_alpha;
    private RelativeLayout mRl_loc_city;
    private LocationManager mLocManager;

    private int mPagerCount = Integer.MAX_VALUE / 3;
    private ViewPager mViewPager_banner;
    private ViewPagerIndicator mViewPagerIndicator;
    private boolean mDragging;
    private MyPagerAdapter mPagerAdapter;
    private boolean isFrist = true;
    private List<BannerInfo> bannerData = new ArrayList<BannerInfo>();

    public MainFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mInflate == null)
        {
            mInflate = inflater.inflate(R.layout.fragment_main, null);
            initBannerData();
            findViewID();
            setListener();
            initUI();
        }
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null)
        {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    private void initBannerData()
    {
        ApiClient.getBannerImg(getActivity(), new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<BannerInfo>>()
                {
                }.getType();
                bannerData = GsonUtils.parseJSONArray(s, type);
                mPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void autoScroll()
    {
        mViewPager_banner.setCurrentItem(mPagerCount / 2);
        mViewPager_banner.postDelayed(new Runnable()
        {
            public void run()
            {
                int position = mViewPager_banner.getCurrentItem() + 1;
                if (!mDragging)
                {
                    isFrist = false;
                    mViewPager_banner.setCurrentItem(position);
                }
                mViewPager_banner.postDelayed(this, 3000);
            }
        }, 3000);
    }

    private void findViewID()
    {
        mIv_cliscan = (ImageView) mInflate.findViewById(R.id.iv_cliscan);
        mTv_loc_city_name = (TextView) mInflate.findViewById(R.id.tv_loc_city_name);
        mRl_loc_city = (RelativeLayout) mInflate.findViewById(R.id.rl_loc_city);
        mTv_oil_price_ninety_five = (TextView) mInflate.findViewById(R.id.tv_oil_price_ninety_five);
        mTv_oil_price_ninety_two = (TextView) mInflate.findViewById(R.id.tv_oil_price_ninety_two);
        mTv_oil_price_ninety = (TextView) mInflate.findViewById(R.id.tv_oil_price_ninety);
        mTv_oil_price_zero = (TextView) mInflate.findViewById(R.id.tv_oil_price_zero);
        mAbc_spinner_mtrl_am_alpha = (ImageView) mInflate.findViewById(R.id.abc_spinner_mtrl_am_alpha);

    }

    private void setListener()
    {
        mInflate.findViewById(R.id.ll_rela_zijiazuche).setOnClickListener(this);
        mInflate.findViewById(R.id.ll_rela_jigouyongche).setOnClickListener(this);
        mIv_cliscan.setOnClickListener(this);
        mRl_loc_city.setOnClickListener(this);
    }

    private void initUI()
    {
        if (isFristLocation)
        {
            initLocCityName();
        }

        initBanner();
    }

    private void initLocCityName()
    {
        mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//        } else
//        {
            String chooseBestProvider = chooseBestProvider(mLocManager);
            mLocManager.requestLocationUpdates(chooseBestProvider, 500, 100, new MyLocationListener());
//        }
    }

    private void initBanner()
    {
        mViewPager_banner = (ViewPager) mInflate.findViewById(R.id.vp_headerview_pager);
        setViewPagerScrollSpeed();
        mPagerAdapter = new MyPagerAdapter(getFragmentManager());
        mViewPager_banner.setOnPageChangeListener(new BannerOnPageChangeListener());
        mViewPagerIndicator = (ViewPagerIndicator) mInflate.findViewById(R.id.ViewPagerIndicator);
        mViewPager_banner.setAdapter(mPagerAdapter);
        if (isFrist)
        {
            autoScroll();
        }
    }

    /**
     * 减慢viewpager滑动动作
     */
    private void setViewPagerScrollSpeed()
    {
        try
        {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager_banner.getContext());
            mScroller.set(mViewPager_banner, scroller);
        } catch (NoSuchFieldException e)
        {

        } catch (IllegalArgumentException e)
        {
        } catch (IllegalAccessException e)
        {

        }
    }

    private String chooseBestProvider(LocationManager locmanager)
    {
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_COARSE); //大致精度
        c.setPowerRequirement(Criteria.POWER_LOW); //电量消耗低
        c.setAltitudeRequired(false); //不需要海拔
        c.setSpeedRequired(false); //不需要速度
        c.setCostAllowed(false); //不需要费用
        String provider = locmanager.getBestProvider(c, true); //false是指不管当前适配器是否可用
        return provider;
    }

    private void initOilPriceData()
    {
        ApiClient.getOilPrice(getActivity(), new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {

                String erroStr = "{\"message\":\"系统错误\",\"code\":\"-1\"}";
                if (erroStr.equals(s))
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.net_abnormal), Toast.LENGTH_SHORT).show();
                } else
                {
                    Type type = new TypeToken<ArrayList<OilPriceInfo>>()
                    {
                    }.getType();
                    mOilPriceInfoList = GsonUtils.parseJSONArray(s, type);
                }
                if (mOilPriceInfoList != null && mOilPriceInfoList.size() > 0)
                {
                    for (int i = 0; i < mOilPriceInfoList.size(); i++)
                    {
                        if (mProvince.equals(mOilPriceInfoList.get(i).getCity().trim()))
                        {
                            mIndex = i;
                            break;
                        }
                    }
                    setOilPriceToShow();
                }
            }
        });
    }

    private void setOilPriceToShow()
    {
        mTv_oil_price_ninety_five.setText(mOilPriceInfoList.get(mIndex).getB97());
        setOilPricUpDown(mTv_oil_price_ninety_five, mOilPriceInfoList.get(mIndex).getB97_up());
        mTv_oil_price_ninety_two.setText(mOilPriceInfoList.get(mIndex).getB93());
        setOilPricUpDown(mTv_oil_price_ninety_two, mOilPriceInfoList.get(mIndex).getB93_up());
        mTv_oil_price_ninety.setText(mOilPriceInfoList.get(mIndex).getB90());
        setOilPricUpDown(mTv_oil_price_ninety, mOilPriceInfoList.get(mIndex).getB90_up());
        mTv_oil_price_zero.setText(mOilPriceInfoList.get(mIndex).getB0());
        setOilPricUpDown(mTv_oil_price_zero, mOilPriceInfoList.get(mIndex).getB0_up());
    }

    public void setOilPricUpDown(TextView textView, int oilBxx)
    {
        switch (oilBxx)
        {
            case 0:
                //没变
                break;
            case 1:
                //上升
                TextViewUtils.setTextDrawable(getActivity(), R.drawable.oil_price_up, textView, TextViewUtils.DRAWABLE_RIGHT);
                break;
            case 2:
                //下降
                TextViewUtils.setTextDrawable(getActivity(), R.drawable.oil_price_down, textView, TextViewUtils.DRAWABLE_RIGHT);
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.ll_rela_zijiazuche:
//                PackageManager packageManager = getActivity().getPackageManager();
//                Intent intent_open_app = packageManager.getLaunchIntentForPackage(getResources().getString(R.string.launch_intent_package_name));
//                try
//                {
//                    startActivity(intent_open_app);
//                } catch (Exception e)
//                {
//                    showInstall();
//                }
            	intent.setClass(getActivity(), NewMainActivityNew.class);
            	startActivity(intent);
                break;
            case R.id.ll_rela_jigouyongche:
                intent.setClass(getActivity(), OrgHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_cliscan:
                showPopCliScan();
                break;
            case R.id.iv_cliscan_close:
                mPopupWindow.dismiss();
                break;
            case R.id.rl_loc_city:
                RotateAnimation animation = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(500);//设置动画持续时间
                mAbc_spinner_mtrl_am_alpha.startAnimation(animation);
                intent.setClass(getActivity(), CityListForChooseActivity.class);
                intent.putExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY, mLocCityName);
                startActivityForResult(intent, Constant.RequestCode.CHOOSE_CITY_REQUEST_CODE);
                break;
        }
    }

    public void showInstall()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.dialog_title));
        builder.setMessage(getResources().getString(R.string.uninstalled));
        builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                UpgradeUtils.upGradeApk(getResources().getString(R.string.launch_intent_apk_url));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        builder.create().show();
    }

    private void showPopCliScan()
    {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_pop_cli_scan_show, null);
        mIv_cliscan_close = (ImageView) inflate.findViewById(R.id.iv_cliscan_close);
        mIv_cliscan_close.setOnClickListener(this);
        mIv_cli_scan_show = (ImageView) inflate.findViewById(R.id.iv_cli_scan_show);
        UILUtils.displayImageNoAnim(Constant.URL.CLI_SCAN, mIv_cli_scan_show, false);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setAnimationStyle(R.style.AnimFromSmallToBig);
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
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        mPopupWindow.setBackgroundDrawable(bitmapDrawable);
        mPopupWindow.showAtLocation(inflate, Gravity.CENTER, 0, 0);
    }

    class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            //当位置发生改变时调用
            if (location != null)
            {
                mSearch = GeoCoder.newInstance();
                mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener()
                {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult)
                    {
                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult)
                    {
                        if (reverseGeoCodeResult != null)
                        {
                            mLocCityName = reverseGeoCodeResult.getAddressDetail().city;
                            if (reverseGeoCodeResult.getAddressDetail().province != null && !"".equals(reverseGeoCodeResult.getAddressDetail().province))
                            {
                                mProvince = reverseGeoCodeResult.getAddressDetail().province.substring(0, reverseGeoCodeResult.getAddressDetail().province.length() - 1);
                            }
                            mTv_loc_city_name.setText(mLocCityName);
                            if (mOilPriceInfoList != null && mOilPriceInfoList.size() > 0)
                            {

                            } else
                            {
                                isFristLocation = false;
                                initOilPriceData();
                            }
                        }
                    }
                });
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(location.getLatitude(), location.getLongitude())));
            } else
            {
                Toast.makeText(getActivity(), "暂时无法获取您的地理信息", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
//            //当状态改变时调用
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//            {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//            } else
//            {
                mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new MyLocationListener());
//            }
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            //当适配器有效时调用
        }


        @Override
        public void onProviderDisabled(String provider)
        {
            //当适配器禁用时调用
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            if (requestCode == Constant.RequestCode.CHOOSE_CITY_REQUEST_CODE && resultCode == Constant.ResultCode.CHOOSE_CITY_RESULT_CODE)
            {
                mTv_loc_city_name.setText(data.getStringExtra(Constant.IntentKey.CHOOSE_CITY_INTENT_KEY));
                mProvince = data.getStringExtra(Constant.IntentKey.CHOOSE_CITY_PROVINCE_INTENT_KEY);
                if (mOilPriceInfoList != null && mOilPriceInfoList.size() > 0)
                {
                    for (int i = 0; i < mOilPriceInfoList.size(); i++)
                    {
                        if (mProvince.equals(mOilPriceInfoList.get(i).getCity().toString().trim()))
                        {
                            mIndex = i;
                            break;
                        }
                    }
                    setOilPriceToShow();
                } else
                {
                    initOilPriceData();
                }
            }
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter
    {

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if (bannerData != null && bannerData.size() > 0)
            {
                int index = position % bannerData.size();
                return new BannerFragment(bannerData.get(index).getUrl());
            } else
            {
                return new BannerFragment("");
            }
        }

        @Override
        public int getCount()
        {
            return mPagerCount;
        }
    }

    /**
     * 广告条页数改变监听
     */
    class BannerOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        public void onPageScrollStateChanged(int state)
        {
            switch (state)
            {
                case ViewPager.SCROLL_STATE_IDLE:
                    mDragging = false;
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    mDragging = true;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    mDragging = false;
                    break;
                default:
                    break;
            }
        }

        public void onPageScrolled(int position, float arg1, int arg2)
        {
            position = position % 3;
            mViewPagerIndicator.move(arg1, position);
        }

        public void onPageSelected(int arg0)
        {
        }
    }
}
