package passenger.utils;

import android.content.Context;
import passenger.constant.Constant;
import shane_library.shane.utils.HTTPUtils;
import shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;


/**
 * Created by Administrator on 2016/6/28.
 */
public class ApiClient
{
    //判断服务器是否存活
    public static void getServerIsLive(Context context, VolleyListener volleyListener)
    {
        get(context, Constant.URL.SERVER_IS_LIVE, volleyListener);
    }

    //加载今日油价
    public static void getOilPrice(Context context, VolleyListener volleyListener)
    {
        get(context, Constant.URL.OIL_PRICE, volleyListener);
    }

    //加载广告条数据
    public static void getBannerImg(Context context,VolleyListener volleyListener)
    {
        get(context,Constant.URL.GET_BANNER_IMG,volleyListener);
    }

    //通过手机号查找该号码下过订单的机构编号
    public static void getOrganizationCodeList(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.ORGANIZATION_CODE_LIST, params, volleyListener);
    }

    //本机账号下所有订单列表（不区分机构编号）
    public static void getOrderList4Organization(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.GET_ORDER_LIST_INSTITUTIONS, params, volleyListener);
    }

    //根据需求筛选订单列表
    public static void getScreeningOrderList(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.SCREENING_ORDER_LIST, params, volleyListener);
    }

    //加载有开放租车的城市列表
    public static void getCityList(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.CITY_LIST, params, volleyListener);
    }

    //检查账号是否在其它设备上登录
//    public static void checkIsLoginOnOtherDevice(Context context, Map<String, String> params, VolleyListener volleyListener)
//    {
//        post(context, Constant.URL.FIND_LONGIN_ID, params, volleyListener);
//    }


    //匹配用户选择的租车时间是否符合当前有的优惠折扣
    public static void getMatchingDiscount(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.MATCHING_DISCOUNT, params, volleyListener);
    }

    //用户上传头像
    public static void uploadUserAvatar(RequestParams params, AsyncHttpResponseHandler httpResponseHandler)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(Constant.URL.UPLOAD_USER_AVATAR, params, httpResponseHandler);
    }

    //向服务器提交订单
    public static void commitOder(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.COMMIT_ZHUCHE_ORDER, params, volleyListener);
    }

    //验证企业账号
    public static void verifyTheUnitOfAccount(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.DACHEZUCHE_COMFIRE_UNIT_INFO, params, volleyListener);
    }

    //加载用户选择类型后的相应车辆
    public static void getCarListByLei(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.GET_CAR_LIST, params, volleyListener);
    }

    //向服务器取消订单
    public static void cancelOrder(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.CANCEL_ORDER, params, volleyListener);
    }

    //加载订单详情
    public static void loadOrderDetail(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.QUERY_ORDER_DETAIL, params, volleyListener);
    }

    //注册账号
    public static void Registered(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.REGISTERED, params, volleyListener);
    }

    //获取短信验证码(向后台请求并触发短信发送的接口)
    public static void getSMS(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.GET_SMS, params, volleyListener);
    }

    //向后台服务推送用户短信验证成功
    public static void smsLogingSuccess(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.SMS_LOGIN_SUCCESS, params, volleyListener);
    }

    //向后台服务验证用户账号及密码登录是否成功
    public static void passWordLoginSuccess(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.PASSWORD_LOGIN_SUCCESS, params, volleyListener);
    }

    //获取门店的地图上经纬地理坐标（从后台服务端获取StoresMarkerInfo数据）
    public static void getStoresMarkersLatLng(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.GET_STORES_MARKERS_LATLNG, params, volleyListener);
    }

    //更改用户密码
    public static void changePassword(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.CHANGE_PASSWORD, params, volleyListener);
    }

  //更改用户密码
    public static void chuanDacheOrderJigouhao(Context context, Map<String, String> params, VolleyListener volleyListener)
    {
        post(context, Constant.URL.COMMIT_DACHE_ORDER_JIGOUHAO, params, volleyListener);
    }


    public static void cancelAll(Context context)
    {
        HTTPUtils.cancelAll(context);
    }

    private static void get(Context context, String url, final VolleyListener volleyListener)
    {
        HTTPUtils.get(context, url, new VolleyListener()
        {
            @Override
            public void onResponse(String s)
            {
                volleyListener.onResponse(s);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                volleyListener.onErrorResponse(volleyError);
            }
        });
    }

    private static void post(Context context, String url, Map<String, String> params, final VolleyListener volleyListener)
    {
        HTTPUtils.post(context, url, params, new VolleyListener()
        {
            @Override
            public void onResponse(String s)
            {
                volleyListener.onResponse(s);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                volleyListener.onErrorResponse(volleyError);
            }
        });
    }


}
