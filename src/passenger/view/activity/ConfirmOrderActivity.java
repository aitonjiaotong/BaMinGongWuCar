package passenger.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import passenger.constant.Constant;
import passenger.model.ChooseFristInfo;
import passenger.model.PrivilegeEvent;
import passenger.model.UserZhuChe;
import passenger.utils.ACache;
import passenger.utils.ApiClient;
import passenger.utils.DateCommonUtils;
import passenger.utils.LoginState;
import shane_library.shane.utils.HTTPUtils;
import shane_library.shane.utils.UILUtils;
import shane_library.shane.utils.VolleyListener;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;
import com.umeng.socialize.utils.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderActivity extends Activity implements View.OnClickListener {

	private ImageView mIv_back;
	private Button mBtn_dache_commit_order;
	private TextView mTv_dache_jg_store_name_return;
	private TextView mTv_dache_jg_store_name_get;
	private ChooseFristInfo mChooseFristInfo;
	private TextView mTv_dache_get_time_date;
	private TextView mTv_dache_get_time_time;
	private TextView mTv_dache_how_long;
	private TextView mTv_dache_return_time_date;
	private TextView mTv_dache_return_time_time;
	private TextView mTv_car_name;
	private TextView mTv_carriage_count;
	private TextView mTv_displacement;
	private TextView mTv_car_seat_count;
	private ImageView mIv_car_img;
	private TextView mTv_base_rent_price;
	private TextView mTv_base_insurance_price;
	private TextView mTv_order_cost_price;
	private TextView mTv_base_total_price;
	private View mDialogLayout;
	private LinearLayout mLl_commint_order_remind;
	private LinearLayout mLl_commint_order_failure;
	private LinearLayout mLl_commint_order_comfire;
	private boolean mIsSuccessful;
	private TextView mTv_show_commint_msg;
	// private AlertDialog.Builder mBuilder;
	private AlertDialog mAlertDialog;
	private TextView mTv_btn_failure_back;
	private TextView mTv_btn_comfire_look_list;
	private TextView mTv_poundage_price;
	private TextView mTv_driver_cost_explain;
	private TextView mTv_driver_cost_price;
	private TextView mTv_base_rent_explain;
	private TextView mTv_beyond_mileage_explain;
	private TextView mTv_beyond_mileage_price;
	private TextView mTv_send_car_to_addr_price;
	private TextView mTv_get_car_to_addr_price;
	private TextView mTv_rate_percentage_price;
	private TextView mTv_discountPercentage_price;
	private double mCarPrice;
	private String mCarPriceDetailStr;
	private double mAllCarPrice;
	private double mInsurancePrice;
	private double mPoundagePrice;
	private double mDriverPrice;
	private double mAllDriverPrice;
	private String mDriverPriceDetailStr;
	private double mAllMileagePrice;
	private double mPickUpCarPrice;
	private double mSendCarPrice;
	private double mDiscountPercentage;
	private String mOutMileagePriceDetailStr;
	private double mAllDiscountPercentagePrice;
	private double mAllBasePrice;
	private double mRatePercentage;
	private double mAllRatePercentagePrice;
	private double mTotalPrice;
	private double mOtherPrice;
	private TextView mTv_total_price;
	private TextView mTv_discount_percentage_explain;
	private String mAllDiscountPercentagePriceStr;
	private String mAllRatePercentagePriceStr;
	private TextView mTv_rate_percentage_explain;
	private TextView mTv_org_get_car_stores_title;
	private TextView mTv_org_retrun_car_stores_title;
	private Double mDayCounts;
	private PrivilegeEvent mPrivilegeEvent;
	private TextView mTv_what_discount;
	private int mIsNakedCarHire;
	private int mHasLease4Other;
	private Dialog dialog;
	private TextView tv_huiyuanzhekou_msg;
	private TextView tv_huiyuanzhekoujiaqian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zu_che_order_detail);
		initGetIntent();
		findViewID();
		setListener();
		initUI();
	}

	private void initGetIntent() {
		DecimalFormat df = new DecimalFormat("0.0");
		Intent data = getIntent();

		mPrivilegeEvent = (PrivilegeEvent) data.getSerializableExtra(Constant.IntentKey.PRIVILEGE_EVENT);
		mChooseFristInfo = (ChooseFristInfo) data.getSerializableExtra(Constant.IntentKey.CHOOSE_FRIST_INFO);

		mDayCounts = DateCommonUtils.getHowLongDay(mChooseFristInfo.getGetCarTime(),
				mChooseFristInfo.getReturnCarTime());
		// 基本车辆租赁费
		mCarPrice = mChooseFristInfo.getPlan().getPrice();
		mCarPriceDetailStr = "￥" + mCarPrice + "/天  X " + mDayCounts + "天";
		mAllCarPrice = Double.parseDouble(df.format((mCarPrice * mDayCounts)));
		// 基本保险费
		mInsurancePrice = mChooseFristInfo.getPlan().getInsurance();
		// 基本手续费
		mPoundagePrice = mChooseFristInfo.getPlan().getPoundage();
		// 司机费用
		mDriverPrice = mChooseFristInfo.getPlan().getHasDriver();
		if (mChooseFristInfo.getHasDriver() == 0) {
			mDriverPriceDetailStr = "￥" + mDriverPrice + "/天  X " + mDayCounts + "天";
			mAllDriverPrice = Double.parseDouble(df.format((mDriverPrice * mDayCounts)));
		} else {
			mDriverPriceDetailStr = "￥" + mDriverPrice + "/天";
			mAllDriverPrice = 0.0;
		}
		// 是否裸车
		mIsNakedCarHire = mChooseFristInfo.getIsNakedCarHire();
		// 里程费
		if (mIsNakedCarHire == 0) {
			mAllMileagePrice = 0 * mChooseFristInfo.getPlan().getFloatPrice();
			mOutMileagePriceDetailStr = "超里程数 0 X ￥" + mChooseFristInfo.getPlan().getOutMileage() + "/公里";
		} else {
			mAllMileagePrice = 0.0;
			mOutMileagePriceDetailStr = "";
		}
		// 是否为他人租车
		mHasLease4Other = mChooseFristInfo.getHasLease4Other();
		// 上门取车费用
		if (mChooseFristInfo.getIsGet() == 0) {
			mPickUpCarPrice = 0.0;
		} else {
			mPickUpCarPrice = mChooseFristInfo.getPlan().getGetCarFromHome();
		}
		// 送车上门费用
		if (mChooseFristInfo.getIsSend() == 0) {
			mSendCarPrice = 0.0;
		} else {
			mSendCarPrice = mChooseFristInfo.getPlan().getSendCarToHome();
		}
		// （车身价 X 天数）+ （司机费用 X 天数）+ （里程费用 X 浮动差价) + 送车上门费用 + 上门取车费用 = 基本总价
		mAllBasePrice = Double.parseDouble(
				df.format((mAllCarPrice + mAllDriverPrice + mAllMileagePrice + mPickUpCarPrice + mSendCarPrice)));
		// 优惠折扣率
		if (mPrivilegeEvent != null && mPrivilegeEvent.getDiscount() != null) {
			mDiscountPercentage = mPrivilegeEvent.getDiscount().getDiscount();
			mAllDiscountPercentagePriceStr = "小计总价￥" + mAllBasePrice + " X 优惠折扣率" + mDiscountPercentage + "("
					+ mPrivilegeEvent.getPrivilege().getName() + "--" + mPrivilegeEvent.getDiscount().getName() + ")";
		} else {
			mDiscountPercentage = 1.0;
			mAllDiscountPercentagePriceStr = "(无优惠活动)";

		}
		// 基本总价 X 优惠折扣率 = 优惠折扣总价
		mAllDiscountPercentagePrice = Double.parseDouble(df.format((multiply(mAllBasePrice, mDiscountPercentage))));
		// 税率
		if (mChooseFristInfo.getHasTax() == 0) {
			mRatePercentage = 1.0;
			mAllRatePercentagePriceStr = "(不含税)";
		} else {
			mRatePercentage = mChooseFristInfo.getPlan().getTax();
			mAllRatePercentagePriceStr = "优惠后总价￥" + mAllDiscountPercentagePrice + " X 税率" + mRatePercentage;
		}
		// 优惠折扣总价 X 税率 = 税率折扣总价
		mAllRatePercentagePrice = Double
				.parseDouble(df.format((multiply(mAllDiscountPercentagePrice, mRatePercentage))));
		// 其它费用
		mOtherPrice = mChooseFristInfo.getPlan().getOthers();
		// 总价
		mTotalPrice = Double
				.parseDouble(df.format((mAllRatePercentagePrice + mInsurancePrice + mPoundagePrice + mOtherPrice)));
	}

	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	private void findViewID() {
		mIv_back = (ImageView) findViewById(R.id.iv_zuche_choose_car_type_back);
		mBtn_dache_commit_order = (Button) findViewById(R.id.btn_dache_commit_order);
		mTv_org_get_car_stores_title = (TextView) findViewById(R.id.tv_org_get_car_stores_title);
		mTv_org_retrun_car_stores_title = (TextView) findViewById(R.id.tv_org_retrun_car_stores_title);
		mTv_dache_jg_store_name_return = (TextView) findViewById(R.id.tv_dache_jg_store_name_return);
		mTv_dache_jg_store_name_get = (TextView) findViewById(R.id.tv_dache_jg_store_name_get);
		mTv_dache_get_time_date = (TextView) findViewById(R.id.tv_dache_get_time_date);
		mTv_dache_get_time_time = (TextView) findViewById(R.id.tv_dache_get_time_time);
		mTv_dache_how_long = (TextView) findViewById(R.id.tv_dache_how_long);
		mTv_what_discount = (TextView) findViewById(R.id.tv_what_discount);
		mTv_dache_return_time_date = (TextView) findViewById(R.id.tv_dache_return_time_date);
		mTv_dache_return_time_time = (TextView) findViewById(R.id.tv_dache_return_time_time);
		mIv_car_img = (ImageView) findViewById(R.id.iv_car_img);
		mTv_car_name = (TextView) findViewById(R.id.tv_car_name);
		mTv_carriage_count = (TextView) findViewById(R.id.tv_carriage_count);
		mTv_displacement = (TextView) findViewById(R.id.tv_displacement);
		mTv_car_seat_count = (TextView) findViewById(R.id.tv_car_seat_count);

		// 基本租赁费
		mTv_base_rent_explain = (TextView) findViewById(R.id.tv_base_rent_explain);
		mTv_base_rent_price = (TextView) findViewById(R.id.tv_base_rent_price);
		// 基本保险费
		mTv_base_insurance_price = (TextView) findViewById(R.id.tv_base_insurance_price);
		// 手续费
		mTv_poundage_price = (TextView) findViewById(R.id.tv_poundage_price);
		// 司机费用
		mTv_driver_cost_explain = (TextView) findViewById(R.id.tv_driver_cost_explain);
		mTv_driver_cost_price = (TextView) findViewById(R.id.tv_driver_cost_price);
		// 里程费用
		mTv_beyond_mileage_explain = (TextView) findViewById(R.id.tv_beyond_mileage_explain);
		mTv_beyond_mileage_price = (TextView) findViewById(R.id.tv_beyond_mileage_price);
		// 送车上门费用
		mTv_send_car_to_addr_price = (TextView) findViewById(R.id.tv_send_car_to_addr_price);
		// 上门取车费用
		mTv_get_car_to_addr_price = (TextView) findViewById(R.id.tv_get_car_to_addr_price);
		// 其它费用
		mTv_order_cost_price = (TextView) findViewById(R.id.tv_ohter_cost_price);
		// 税率价
		mTv_rate_percentage_price = (TextView) findViewById(R.id.tv_rate_percentage_price);
		mTv_rate_percentage_explain = (TextView) findViewById(R.id.tv_rate_percentage_explain);

		// 优惠折扣价
		mTv_discountPercentage_price = (TextView) findViewById(R.id.tv_discountPercentage_price);
		mTv_discount_percentage_explain = (TextView) findViewById(R.id.tv_discount_percentage_explain);
		tv_huiyuanzhekou_msg = (TextView) findViewById(R.id.tv_huiyuanzhekou_msg);
		tv_huiyuanzhekoujiaqian = (TextView) findViewById(R.id.tv_huiyuanzhekoujiaqian);
		
		// 小计
		mTv_base_total_price = (TextView) findViewById(R.id.tv_base_total_price);
		// 合计
		mTv_total_price = (TextView) findViewById(R.id.tv_total_price);

		mDialogLayout = getLayoutInflater().inflate(R.layout.jigouzuche_commint_order_secuss_dialog, null);
		mTv_btn_failure_back = (TextView) mDialogLayout.findViewById(R.id.tv_btn_failure_back);
		mTv_btn_comfire_look_list = (TextView) mDialogLayout.findViewById(R.id.tv_btn_comfire_look_list);
		mLl_commint_order_remind = (LinearLayout) mDialogLayout.findViewById(R.id.ll_commint_order_remind);
		mLl_commint_order_failure = (LinearLayout) mDialogLayout.findViewById(R.id.ll_commint_order_failure);
		mLl_commint_order_comfire = (LinearLayout) mDialogLayout.findViewById(R.id.ll_commint_order_comfire);
		mTv_show_commint_msg = (TextView) mDialogLayout.findViewById(R.id.tv_show_commint_msg);
	}

	private void setListener() {
		mIv_back.setOnClickListener(this);
		mBtn_dache_commit_order.setOnClickListener(this);
		mTv_btn_failure_back.setOnClickListener(this);
		mTv_btn_comfire_look_list.setOnClickListener(this);
	}

	private void initUI() {
		if (mChooseFristInfo != null && mChooseFristInfo.getCar() != null) {
			initChoosedCarInfo();
			initChoosedRentCarTime();
			initChoosedPriceInfo();
			initAddrInfo();
		}
	}

	private void initAddrInfo() {
		if (mChooseFristInfo.getIsSend() == 0) {

			mTv_org_get_car_stores_title.setText("取车门店");
			mTv_dache_jg_store_name_get.setText(mChooseFristInfo.getGetCarStoreName());
		} else {
			mTv_org_get_car_stores_title.setText("送车上门");
			mTv_dache_jg_store_name_get.setText(mChooseFristInfo.getSendCarAddress());
		}
		if (mChooseFristInfo.getIsGet() == 0) {
			mTv_org_retrun_car_stores_title.setText("还车门店");
			mTv_dache_jg_store_name_return.setText(mChooseFristInfo.getReturnCarStoreName());
		} else {
			mTv_org_retrun_car_stores_title.setText("上门取车");
			mTv_dache_jg_store_name_return.setText(mChooseFristInfo.getGetCarAddress());
		}
	}

	private void initChoosedCarInfo() {
		mTv_car_name.setText(mChooseFristInfo.getCar().getModel() + mChooseFristInfo.getCar().getType());
		mTv_carriage_count.setText(mChooseFristInfo.getCar().getBox());

		if (mChooseFristInfo.getCar().getZidong() == 0) {
			mTv_displacement.setText(mChooseFristInfo.getCar().getPailiang() + "自动");
		} else {
			mTv_displacement.setText(mChooseFristInfo.getCar().getPailiang() + "手动");
		}
		mTv_car_seat_count.setText("乘坐" + mChooseFristInfo.getCar().getSeat() + "人");
		if (mChooseFristInfo.getCar().getImage() != null && !"".equals(mChooseFristInfo.getCar().getImage())) {
			UILUtils.displayImageNoAnim(mChooseFristInfo.getCar().getImage(), mIv_car_img);
		}
	}

	private void initChoosedRentCarTime() {
		mTv_dache_get_time_date.setText(getDateToString(mChooseFristInfo.getGetCarTime()));
		mTv_dache_get_time_time.setText(getTimeToString(mChooseFristInfo.getGetCarTime()));
		mTv_dache_how_long.setText(mDayCounts + "天");
		if (mPrivilegeEvent != null && mPrivilegeEvent.getDiscount() != null) {
			mTv_what_discount
					.setText(mPrivilegeEvent.getPrivilege().getName() + mPrivilegeEvent.getDiscount().getName());
		}
		mTv_dache_return_time_date.setText(getDateToString(mChooseFristInfo.getReturnCarTime()));
		mTv_dache_return_time_time.setText(getTimeToString(mChooseFristInfo.getReturnCarTime()));
	}

	private void initChoosedPriceInfo() {
		// 基本租赁费
		mTv_base_rent_explain.setText(mCarPriceDetailStr);
		mTv_base_rent_price.setText("￥" + mAllCarPrice);
		// 基本保险费
		mTv_base_insurance_price.setText("￥" + mInsurancePrice);
		// 手续费
		mTv_poundage_price.setText("￥" + mPoundagePrice);
		// 司机费用
		mTv_driver_cost_explain.setText(mDriverPriceDetailStr);
		mTv_driver_cost_price.setText("￥" + mAllDriverPrice);
		// 里程费用
		mTv_beyond_mileage_explain.setText(mOutMileagePriceDetailStr);
		mTv_beyond_mileage_price.setText("￥" + mAllMileagePrice);
		// 送车上门费用
		mTv_send_car_to_addr_price.setText("￥" + mSendCarPrice);
		// 上门取车费用
		mTv_get_car_to_addr_price.setText("￥" + mPickUpCarPrice);
		// 总计
		mTv_base_total_price.setText("￥" + mAllBasePrice);
		// 优惠折扣价
		mTv_discountPercentage_price.setText("￥" + mAllDiscountPercentagePrice);
		mTv_discount_percentage_explain.setText(mAllDiscountPercentagePriceStr);
		//会员折扣
		ACache aCache = ACache.get(ConfirmOrderActivity.this);
		UserZhuChe userZhuChe = (UserZhuChe) aCache.getAsObject(Constant.AcacheKey.USER_ZHUCHE);
		double discount = userZhuChe.getMemberModel().getDiscount();
		tv_huiyuanzhekou_msg.setText("会员折扣 ¥ " + mAllDiscountPercentagePrice + " X 折扣"
				+ discount);
		tv_huiyuanzhekoujiaqian.setText("¥" + mAllDiscountPercentagePrice*discount);
		// 税率价
		mTv_rate_percentage_price.setText("￥" + mAllRatePercentagePrice);
		mTv_rate_percentage_explain.setText(mAllRatePercentagePriceStr);
		// 其它费用
		mTv_order_cost_price.setText("￥" + mOtherPrice);
		// 合计总价
		mTv_total_price.setText("￥" + mTotalPrice);

	}

	private String getDateToString(long l) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MM-dd");
		String date_format = mSimpleDateFormat.format(l);
		return date_format;
	}

	private String getTimeToString(long l) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EE HH:mm");
		String time_format = mSimpleDateFormat.format(l);
		return time_format;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_zuche_choose_car_type_back:
			finish();
			break;
		case R.id.btn_dache_commit_order:
			// 提交订单
			if (LoginState.getInstance(ConfirmOrderActivity.this).isLogin()) {
				// 用户登录的状态
				Map map = constructionOrderParams();
				commitOrder();
			} else {
				// 用户未登录的状态
				// startActivity(new Intent(ConfirmOrderActivity.this,
				// SmsLoginActivity.class));
			}
			break;
		case R.id.tv_btn_comfire_look_list:
			// 订单提交成功后点击查看跳转到订单列表
			intentToOrderList();
			break;
		case R.id.tv_btn_failure_back:
			dialog.dismiss();
			break;
		}
	}

	/**
	 * 向服务端提交订单信息
	 */
	private void commitOrder() {
		commintOrderSecussDialog();

		ACache aCache = ACache.get(ConfirmOrderActivity.this);
		String jigouhao = aCache.getAsString(Constant.AcacheKey.JIGOUHAO);

		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("zuchuDate", mChooseFristInfo.getGetCarTime() + "");// 租出时间
		orderParams.put("planReturnDate", mChooseFristInfo.getReturnCarTime() + "");// 计划还车时间
		orderParams.put("price", mTotalPrice + "");// 总价
		orderParams.put("hasDriver", mChooseFristInfo.getHasDriver() + "");// 是否携带司机
																			// 0:带
																			// 1：不带
		orderParams.put("planId", mChooseFristInfo.getPlan().getId() + "");// 计划id
		orderParams.put("model", mChooseFristInfo.getCar().getModel());
		orderParams.put("type", mChooseFristInfo.getCar().getType());
		orderParams.put("institutionsCode", jigouhao);// 企业账号
		orderParams.put("accountId", LoginState.getInstance(ConfirmOrderActivity.this).getLoginInfo().getUserId());// 用户id
		orderParams.put("hasLuo", mIsNakedCarHire + "");// 是否裸车
		orderParams.put("hasTax", mChooseFristInfo.getHasTax() + "");// 是否含税

		orderParams.put("isSendCarToHome", mChooseFristInfo.getIsSend() + "");// 是否送车上门
																				// 0:否
																				// 1:是
		if (mChooseFristInfo.getIsSend() == 0) {
			orderParams.put("getCar", mChooseFristInfo.getGetCarStoresId() + "");// 取车地点（门店ID）
		} else {
			orderParams.put("sendCarAddress", mChooseFristInfo.getSendCarAddress());// 送车上门地址
		}
		orderParams.put("isGetCarFromHome", mChooseFristInfo.getIsGet() + "");// 是否上门取车
																				// 0:否
																				// 1:是
		if (mChooseFristInfo.getIsGet() == 0) {
			orderParams.put("returnCar", mChooseFristInfo.getReturnCarStoresId() + "");// 还车地点（门店ID）
		} else {
			orderParams.put("getCarAddress", mChooseFristInfo.getGetCarAddress());// 上门取车地址
		}
		if (mPrivilegeEvent != null && mPrivilegeEvent.getDiscount() != null) {
			orderParams.put("discount", mPrivilegeEvent.getDiscount().getDiscount() + "");
			orderParams.put("privilegeId", mPrivilegeEvent.getPrivilege().getId() + "");
		} else {
			orderParams.put("discount", 1 + "");
		}
		orderParams.put("leaseForOther", mHasLease4Other + "");
		orderParams.put("phoneOfUser", mChooseFristInfo.getPhoneOfUserStr() + "");

		// String url =
		// "http://192.168.1.113:8080/aiton-app-webapp/zc/order/institutions/addorder";
		//
		// HTTPUtils.post(ConfirmOrderActivity.this, url , orderParams, new
		// VolleyListener() {
		//
		// @Override
		// public void onResponse(String s) {
		// Log.e("order", "order" + s);
		// if ("0".equals(s))
		// {
		// //订单异常
		// mIsSuccessful = false;
		// mLl_commint_order_remind.setVisibility(View.GONE);
		// mLl_commint_order_failure.setVisibility(View.VISIBLE);
		// mLl_commint_order_comfire.setVisibility(View.GONE);
		// mTv_show_commint_msg.setText("订单提交异常\n请重新提交订单！");
		// } else if ("-2".equals(s))
		// {
		// //此车已被预定
		// mIsSuccessful = false;
		// mLl_commint_order_remind.setVisibility(View.GONE);
		// mLl_commint_order_failure.setVisibility(View.VISIBLE);
		// mLl_commint_order_comfire.setVisibility(View.GONE);
		// mTv_show_commint_msg.setText("订单提交异常\n您租用的车辆已被租用\n请重新选择车辆并提交！");
		// } else if ("-3".equals(s))
		// {
		// //此车在此时间段内有人预定
		// mIsSuccessful = false;
		// mLl_commint_order_remind.setVisibility(View.GONE);
		// mLl_commint_order_failure.setVisibility(View.VISIBLE);
		// mLl_commint_order_comfire.setVisibility(View.GONE);
		// mTv_show_commint_msg.setText("订单提交异常\n您租用的车辆在您租用期间已被预定\n请重新选择车辆并提交！");
		// } else
		// {
		// //订单提交成功
		// mIsSuccessful = true;
		// mLl_commint_order_remind.setVisibility(View.GONE);
		// mLl_commint_order_failure.setVisibility(View.GONE);
		// mLl_commint_order_comfire.setVisibility(View.VISIBLE);
		// Toast.makeText(ConfirmOrderActivity.this, "订单提交成功！",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		//
		// @Override
		// public void onErrorResponse(VolleyError s) {
		// mAlertDialog.dismiss();
		// Toast.makeText(ConfirmOrderActivity.this, "订单提交失败，请重新提交！",
		// Toast.LENGTH_SHORT).show();
		// Log.e("order", "提交订单失败");
		// }
		// });

		ApiClient.commitOder(ConfirmOrderActivity.this, orderParams, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				dialog.dismiss();
				Toast.makeText(ConfirmOrderActivity.this, "订单提交失败，请重新提交！", Toast.LENGTH_SHORT).show();
				Log.e("order", "提交订单失败");
			}

			@Override
			public void onResponse(String s) {
				Log.e("order", "order" + s);
				if ("0".equals(s)) {
					// 订单异常
					mIsSuccessful = false;
					mLl_commint_order_remind.setVisibility(View.GONE);
					mLl_commint_order_failure.setVisibility(View.VISIBLE);
					mLl_commint_order_comfire.setVisibility(View.GONE);
					mTv_show_commint_msg.setText("订单提交异常\n请重新提交订单！");
				} else if ("-2".equals(s)) {
					// 此车已被预定
					mIsSuccessful = false;
					mLl_commint_order_remind.setVisibility(View.GONE);
					mLl_commint_order_failure.setVisibility(View.VISIBLE);
					mLl_commint_order_comfire.setVisibility(View.GONE);
					mTv_show_commint_msg.setText("订单提交异常\n您租用的车辆已被租用\n请重新选择车辆并提交！");
				} else if ("-3".equals(s)) {
					// 此车在此时间段内有人预定
					mIsSuccessful = false;
					mLl_commint_order_remind.setVisibility(View.GONE);
					mLl_commint_order_failure.setVisibility(View.VISIBLE);
					mLl_commint_order_comfire.setVisibility(View.GONE);
					mTv_show_commint_msg.setText("订单提交异常\n您租用的车辆在您租用期间已被预定\n请重新选择车辆并提交！");
				} else {
					// 订单提交成功
					mIsSuccessful = true;
					mLl_commint_order_remind.setVisibility(View.GONE);
					mLl_commint_order_failure.setVisibility(View.GONE);
					mLl_commint_order_comfire.setVisibility(View.VISIBLE);
					Toast.makeText(ConfirmOrderActivity.this, "订单提交成功！", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	/**
	 * 订单提交后的对话框提示
	 */
	public void commintOrderSecussDialog() {
		dialog = new Dialog(ConfirmOrderActivity.this, R.style.MineDialog);
		dialog.setContentView(mDialogLayout);
		// if (mBuilder == null)
		// {
		// mBuilder = new
		// AlertDialog.Builder(ConfirmOrderActivity.this,R.style.MineDialog).setView(mDialogLayout);
		// mAlertDialog = mBuilder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (mIsSuccessful && keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.dismiss();
					// 此处把dialog dismiss掉，然后把本身的activity finish掉
					finish();
					intentToOrderList();
					return true;
				} else {
					return false;
				}
			}
		});
		dialog.show();
		// }
		// mAlertDialog.show();
	}

	/**
	 * 订单提交完成后跳转到主界面的订单列表界面
	 */
	private void intentToOrderList() {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(ConfirmOrderActivity.this, MainActivity.class);
		intent.putExtra(Constant.IntentKey.BACK_TO_ORDER_LIST_KEY, Constant.IntentKey.ORG_BACK_INT_VALUE);
		startActivity(intent);
	}

	/**
	 * 构造订单的参数
	 */
	private Map constructionOrderParams() {
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("zuchuDate", mChooseFristInfo.getGetCarTime() + "");// 租出时间
		orderParams.put("planReturnDate", mChooseFristInfo.getReturnCarTime() + "");// 计划还车时间
		orderParams.put("price", mTotalPrice + "");// 总价
		orderParams.put("hasDriver", mChooseFristInfo.getHasDriver() + "");// 是否携带司机
																			// 0:带
																			// 1：不带
		orderParams.put("planId", mChooseFristInfo.getPlan().getId() + "");// 计划id
		orderParams.put("model", mChooseFristInfo.getCar().getModel());
		orderParams.put("type", mChooseFristInfo.getCar().getType());
		orderParams.put("institutionsCode", mChooseFristInfo.getUnitOfAccount());// 企业账号
		orderParams.put("accountId", LoginState.getInstance(ConfirmOrderActivity.this).getLoginInfo().getUserId());// 用户id
		orderParams.put("hasLuo", mIsNakedCarHire + "");// 是否裸车
		orderParams.put("hasTax", mChooseFristInfo.getHasTax() + "");// 是否含税

		orderParams.put("isSendCarToHome", mChooseFristInfo.getIsSend() + "");// 是否送车上门
																				// 0:否
																				// 1:是
		if (mChooseFristInfo.getIsSend() == 0) {
			orderParams.put("getCar", mChooseFristInfo.getGetCarStoresId() + "");// 取车地点（门店ID）
		} else {
			orderParams.put("sendCarAddress", mChooseFristInfo.getSendCarAddress());// 送车上门地址
		}
		orderParams.put("isGetCarFromHome", mChooseFristInfo.getIsGet() + "");// 是否上门取车
																				// 0:否
																				// 1:是
		if (mChooseFristInfo.getIsGet() == 0) {
			orderParams.put("returnCar", mChooseFristInfo.getReturnCarStoresId() + "");// 还车地点（门店ID）
		} else {
			orderParams.put("getCarAddress", mChooseFristInfo.getGetCarAddress());// 上门取车地址
		}
		if (mPrivilegeEvent != null && mPrivilegeEvent.getDiscount() != null) {
			orderParams.put("discount", mPrivilegeEvent.getDiscount().getDiscount() + "");
			orderParams.put("privilegeId", mPrivilegeEvent.getPrivilege().getId() + "");
		} else {
			orderParams.put("discount", 1 + "");
		}
		orderParams.put("leaseForOther", mHasLease4Other + "");
		orderParams.put("phoneOfUser", mChooseFristInfo.getPhoneOfUserStr() + "");
		return orderParams;
	}

	@Override
	protected void onDestroy() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		super.onDestroy();
	}

}
