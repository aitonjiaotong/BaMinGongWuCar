package passenger.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import passenger.constant.Constant;
import passenger.model.ChooseFristInfo;
import passenger.model.PrivilegeEvent;
import passenger.model.TypeCarListInfo;
import passenger.model.TypeCarListInfo.ContainsBean;
import passenger.utils.ApiClient;
import passenger.utils.DateCommonUtils;
import passenger.view.customview.AnimCheckBox;
import passenger.view.customview.MyCarDialog;
import passenger.view.customview.UISwitchButton;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.UILUtils;
import shane_library.shane.utils.VolleyListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

public class OrgHomeActivity extends FragmentActivity
		implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
	private ImageView mIv_dache_jg_back;
	private LinearLayout mLl_dache_jg_choose_city_get;
	private LinearLayout mLl_dache_jg_choose_time_get;
	private LinearLayout mLl_dache_jg_return_time;
	private RadioGroup mRg_dache_jg_months;
	private Button mBtn_dache_jg_next;
	private long mGetCarTimeMillis;
	private long mReturnCarTimeMillis;
	private long mGetCarTimeMillis4Compare;
	private long mReturnCarTimeMillis4Compare;
	private TextView mTextView_take_car_city;
	private String mUnitOfAccount;
	// private StartDateTimePickerListener mStartDateTimePickerListener = new
	// StartDateTimePickerListener();
	// private EndDateTimePickerListener mEndDateTimePickerListener = new
	// EndDateTimePickerListener();
	private EditText mEt_dachezuche_dialog_unit_of_account;
	private EditText mEt_dachezuche_dialog_unit_of_password;
	private LinearLayout mLl_dache_reminder_prog;
	private AlertDialog mAlertDialog;
	private TextView mTv_check_failure_reminder;
	private LinearLayout mLl_dache_jg_choose_type_gongwuche;
	private LinearLayout mLl_dache_jg_choose_type_shangwuche;
	private LinearLayout mLl_dache_jg_choose_type_zhifache;
	private LinearLayout mLl_dache_jg_choose_type_yueyeche;
	private LinearLayout mLl_dache_jg_choose_type_pika;
	private LinearLayout mLl_dache_jg_choose_type_keche;
	private int mCarType = -1;
	private int mHasDriver;
	private AnimCheckBox mCk_remember_account;
	private RadioButton mRb_dache_gongwuche;
	private RadioButton mRb_dache_shangwuche;
	private RadioButton mRb_dache_zhifache;
	private RadioButton mRb_dache_yueyeche;
	private RadioButton mRb_dache_pika;
	private RadioButton mRb_dache_keche;

	private int mPage = 0;
	private int mBasePageNum;
	private List<TypeCarListInfo.ContainsBean> mCarTypeContainsEntityList = new ArrayList<ContainsBean>();
	private PopupWindow mPopupWindow;
	private carTypeListAdapter mCarTypeListAdapter;
	private TextView mTv_btn_get_more;
	private LinearLayout mLl_car_type_list_pop_close;
	private TextView mTv_no_result;
	private UISwitchButton mSwb_has_driver;
	private ListView mLv_car_type_list_info;
	private LinearLayout mLl_user_choose_car_info;
	private ImageView mIv_car_img;
	private TextView mTv_car_name;
	private TextView mTv_carriage_count;
	private TextView mTv_displacement;
	private TextView mTv_car_seat_count;
	private TextView mTv_car_price;
	private int mCarID;
	private TypeCarListInfo.ContainsBean.CarBean mCarInfo;
	private TypeCarListInfo.ContainsBean.PlanBean mPlan;
	private RelativeLayout mRl_already_rent;
	private boolean mIsChoose;
	private TextView mTextView_startDate;
	private TextView mTextView_startTime;
	private TextView mTextView_endDate;
	private TextView mTextView_endTime;
	private TextView mTextView_dayCounts;
	private boolean mIsChooseGetCarCity;
	private LinearLayout mLl_dache_jg_choose_city_return;
	private TextView mTextView_return_car_city;
	private boolean mIsChooseReturnCarCity;
	private LinearLayout mLl_dache_jg_order_get_car;
	private String mGetCityName;
	private String mReturnCityName;

	private int mGetCarStoresId = -1;
	private int mReturnCarStoresId = -1;
	private LinearLayout mLl_dache_jg_order_return_car;
	private UISwitchButton mSwb_is_get;
	private UISwitchButton mSwb_is_send;
	private TextView mTv_org_get_car_stores_title;
	private TextView mTv_org_retrun_car_stores_title;
	private TextView mTv_dache_jg_store_name_get;
	private TextView mTv_dache_jg_store_name_return;
	private boolean mIsChooseGetCarStore;
	private boolean mIsChooseReturnCarStore;
	private int mIsSend;
	private int mIsGet;
	private String mSendCarAddress;
	private String mGetCarAddress;
	private String mGetCarStoreName;
	private String mReturnCarStoreName;
	private TextView mTv_what_discount;
	private PrivilegeEvent mPrivilegeEvent;
	// private UISwitchButton mSwb_is_naked_car_hire;
	private int mNakedCarHire;
	// private UISwitchButton mSwb_has_tax;
	private int mHasTax;
	private UISwitchButton mSwb_has_lease4other;
	private int mHasLeaseForOther;
	private LinearLayout mLl_phone_of_other;
	private EditText mEt_phone_of_other;
	private boolean inputOtherPhoneIsDone;
	private String mPhoneOfUser;
	private TextView mTv_lease4other_line;
	private TextView mTv_phone_other_editor;
	private TextView mTv_phone_other_confirm;
	private boolean hasFinishOhterPhone = true;
	private SlideDateTimePicker dateTimePicker;
	private SlideDateTimePicker dateTimePicker02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ji_gou_yong_che);

		// 弹出确认企业账号信息的对话框
		// showConfirmOrderDialog();
		findViewID();
		initDefaultTimeMillis();
		initUI();
		setListener();
	}

	private void initDefaultTimeMillis() {
		long currentTimeMillis = DateCommonUtils.getCurrentTimeMillis();
		// 默认取车时间为当前时间往后推迟一个小时
		mGetCarTimeMillis = currentTimeMillis + 1L * 3600L * 1000L;
		mGetCarTimeMillis4Compare = currentTimeMillis;

		// 默认还车时间为取车时间往后推迟2小时
		mReturnCarTimeMillis = mGetCarTimeMillis + 24L * 3600L * 1000L;
		mReturnCarTimeMillis4Compare = mGetCarTimeMillis + 2L * 3600L * 1000L;
		matchingDiscount();
	}

	// 弹出企业账号验证的对话框
	// public void showConfirmOrderDialog()
	// {
	// mConfirm_order_dialog =
	// getLayoutInflater().inflate(R.layout.dachezuche_order_detail_dailog_layout,
	// null);
	// mCk_remember_account = (AnimCheckBox)
	// mConfirm_order_dialog.findViewById(R.id.ck_remember_account);
	// mDialog = new AlertDialog.Builder(OrgHomeActivity.this);
	// mDialog.setView(mConfirm_order_dialog);
	// mAlertDialog = mDialog.create();
	// mAlertDialog.show();
	// mAlertDialog.setCanceledOnTouchOutside(false);
	// mEt_dachezuche_dialog_unit_of_account = (EditText)
	// mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_account);
	// mEt_dachezuche_dialog_unit_of_account.setText(getUnitAccontID());
	// mEt_dachezuche_dialog_unit_of_password = (EditText)
	// mConfirm_order_dialog.findViewById(R.id.et_dachezuche_dialog_unit_of_password);
	// mLl_dache_reminder_prog = (LinearLayout)
	// mConfirm_order_dialog.findViewById(R.id.ll_dache_reminder_prog);
	// mBtn_dachezuche_dialog_comfire = (Button)
	// mConfirm_order_dialog.findViewById(R.id.btn_dachezuche_dialog_comfire);
	// mTv_check_failure_reminder = (TextView)
	// mConfirm_order_dialog.findViewById(R.id.tv_check_failure_reminder);
	// mIv_dailog_close = (ImageView)
	// mConfirm_order_dialog.findViewById(R.id.iv_dailog_close);
	// mIv_dailog_close.setOnClickListener(this);
	//
	// mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
	// {
	// public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	// {
	// if (keyCode == KeyEvent.KEYCODE_BACK)
	// {
	// dialog.dismiss();
	// //此处把dialog dismiss掉，然后把本身的activity finish掉
	// finish();
	// return true;
	// } else
	// {
	// return false;
	// }
	// }
	// });
	// }

	// 验证企业账号信息
	private void verifyTheUnitOfAccount(final String unitOfAccount, String unitOfpassword) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", unitOfAccount);
		params.put("password", unitOfpassword);
		ApiClient.verifyTheUnitOfAccount(OrgHomeActivity.this, params, new VolleyListener() {
			@Override
			public void onResponse(String s) {
				if (s.equals("true")) {
					mUnitOfAccount = unitOfAccount;
					mLl_dache_reminder_prog.setVisibility(View.GONE);
					SetEditTextClickable(true);
					mAlertDialog.dismiss();
					if (mCk_remember_account.isChecked()) {
						saveUnitAccontID(mUnitOfAccount);
					} else {
						removeUnitAccontID();
					}
				} else {
					mLl_dache_reminder_prog.setVisibility(View.GONE);
					SetEditTextClickable(true);
					mTv_check_failure_reminder.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		});
	}

	// 保存企业账号到本地
	private void saveUnitAccontID(String unitOfAccount) {
		SharedPreferences sp = getSharedPreferences("unitAccontID", Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("unitAccontID", unitOfAccount);
		edit.commit();
	}

	// 获取本地保存的企业账号
	public String getUnitAccontID() {
		SharedPreferences sp = getSharedPreferences("unitAccontID", Context.MODE_PRIVATE);
		String unitAccontID = sp.getString("unitAccontID", "");
		return unitAccontID;
	}

	// 清除本地保存的企业账号
	public void removeUnitAccontID() {
		SharedPreferences sp = getSharedPreferences("unitAccontID", Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.clear();
		edit.commit();
	}

	private void findViewID() {
		mIv_dache_jg_back = (ImageView) findViewById(R.id.iv_dache_jg_back);
		mLl_dache_jg_choose_city_get = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_city_get);
		mLl_dache_jg_choose_city_return = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_city_return);

		mLl_dache_jg_choose_time_get = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_time_get);
		mLl_dache_jg_return_time = (LinearLayout) findViewById(R.id.ll_dache_jg_return_time);
		mRg_dache_jg_months = (RadioGroup) findViewById(R.id.rg_dache_jg_months);
		mBtn_dache_jg_next = (Button) findViewById(R.id.btn_dache_jg_next);
		mTextView_take_car_city = (TextView) findViewById(R.id.textView_take_car_city);
		mTextView_return_car_city = (TextView) findViewById(R.id.textView_return_car_city);

		mLl_dache_jg_choose_type_gongwuche = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_gongwuche);
		mLl_dache_jg_choose_type_shangwuche = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_shangwuche);
		mLl_dache_jg_choose_type_zhifache = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_zhifache);
		mLl_dache_jg_choose_type_yueyeche = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_yueyeche);
		mLl_dache_jg_choose_type_pika = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_pika);
		mLl_dache_jg_choose_type_keche = (LinearLayout) findViewById(R.id.ll_dache_jg_choose_type_keche);

		mRb_dache_gongwuche = (RadioButton) findViewById(R.id.rb_dache_gongwuche);
		mRb_dache_shangwuche = (RadioButton) findViewById(R.id.rb_dache_shangwuche);
		mRb_dache_zhifache = (RadioButton) findViewById(R.id.rb_dache_zhifache);
		mRb_dache_yueyeche = (RadioButton) findViewById(R.id.rb_dache_yueyeche);
		mRb_dache_pika = (RadioButton) findViewById(R.id.rb_dache_pika);
		mRb_dache_keche = (RadioButton) findViewById(R.id.rb_dache_keche);

		mSwb_has_driver = (UISwitchButton) findViewById(R.id.swb_has_driver);
		// mSwb_is_naked_car_hire = (UISwitchButton)
		// findViewById(R.id.swb_is_naked_car_hire);
		// mSwb_has_tax = (UISwitchButton) findViewById(R.id.swb_has_tax);
		mSwb_has_lease4other = (UISwitchButton) findViewById(R.id.swb_has_lease4other);
		mLl_user_choose_car_info = (LinearLayout) findViewById(R.id.ll_user_choose_car_info);// 显示用户选择的车辆信息布局
		mIv_car_img = (ImageView) findViewById(R.id.iv_car_img);
		mTv_car_name = (TextView) findViewById(R.id.tv_car_name);
		mTv_carriage_count = (TextView) findViewById(R.id.tv_carriage_count);
		mTv_displacement = (TextView) findViewById(R.id.tv_displacement);
		mTv_car_seat_count = (TextView) findViewById(R.id.tv_car_seat_count);
		mTv_car_price = (TextView) findViewById(R.id.tv_car_price);
		mTextView_startDate = (TextView) findViewById(R.id.textView_startDate);
		mTextView_startTime = (TextView) findViewById(R.id.textView_startTime);
		mTextView_endDate = (TextView) findViewById(R.id.textView_endDate);
		mTextView_endTime = (TextView) findViewById(R.id.textView_endTime);
		mTextView_dayCounts = (TextView) findViewById(R.id.textView_dayCounts);

		mLl_dache_jg_order_get_car = (LinearLayout) findViewById(R.id.ll_dache_jg_order_get_car);
		mLl_dache_jg_order_return_car = (LinearLayout) findViewById(R.id.ll_dache_jg_order_return_car);

		// 送车上门选择框
		mSwb_is_get = (UISwitchButton) findViewById(R.id.swb_org_is_get);
		mSwb_is_send = (UISwitchButton) findViewById(R.id.swb_org_is_send);
		mTv_org_get_car_stores_title = (TextView) findViewById(R.id.tv_org_get_car_stores_title);
		mTv_org_retrun_car_stores_title = (TextView) findViewById(R.id.tv_org_retrun_car_stores_title);
		mTv_dache_jg_store_name_get = (TextView) findViewById(R.id.tv_dache_jg_store_name_get);
		mTv_dache_jg_store_name_return = (TextView) findViewById(R.id.tv_dache_jg_store_name_return);

		mTv_what_discount = (TextView) findViewById(R.id.tv_what_discount);

		mLl_phone_of_other = (LinearLayout) findViewById(R.id.ll_phone_of_other);
		mEt_phone_of_other = (EditText) findViewById(R.id.et_phone_of_other);
		mTv_lease4other_line = (TextView) findViewById(R.id.tv_lease4other_line);
		mTv_phone_other_editor = (TextView) findViewById(R.id.tv_phone_other_editor);
		mTv_phone_other_confirm = (TextView) findViewById(R.id.tv_phone_other_confirm);
	}

	private void initUI() {
		// 设置取车时间 默认当前时间
		setDate4TextView(mTextView_startDate, mTextView_startTime, mGetCarTimeMillis);
		// 设置还车时间 默认当前时间往后推迟一天
		setDate4TextView(mTextView_endDate, mTextView_endTime, mReturnCarTimeMillis);
		// 设置默认选择的时间差
		Double howLongDay = DateCommonUtils.getHowLongDay(mGetCarTimeMillis, mReturnCarTimeMillis);
		mTextView_dayCounts.setText(howLongDay + "天");
		mRg_dache_jg_months.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_dache_jg_five_day:
					mGetCarTimeMillis = mGetCarTimeMillis4Compare + 3600L * 1000L;
					setDate4TextView(mTextView_startDate, mTextView_startTime, mGetCarTimeMillis);
					mReturnCarTimeMillis = mGetCarTimeMillis + 5L * 24L * 3600L * 1000L;
					setDate4TextView(mTextView_endDate, mTextView_endTime, mReturnCarTimeMillis);
					mTextView_dayCounts.setText("5.0天");
					matchingDiscount();
					break;
				case R.id.rb_dache_jg_ten_day:
					mGetCarTimeMillis = mGetCarTimeMillis4Compare + 3600L * 1000L;
					setDate4TextView(mTextView_startDate, mTextView_startTime, mGetCarTimeMillis);
					mReturnCarTimeMillis = mGetCarTimeMillis + 10L * 24L * 3600L * 1000L;
					setDate4TextView(mTextView_endDate, mTextView_endTime, mReturnCarTimeMillis);
					mTextView_dayCounts.setText("10.0天");
					matchingDiscount();
					break;
				case R.id.rb_dache_jg_fifteen_months:
					mGetCarTimeMillis = mGetCarTimeMillis4Compare + 3600L * 1000L;
					setDate4TextView(mTextView_startDate, mTextView_startTime, mGetCarTimeMillis);
					mReturnCarTimeMillis = mGetCarTimeMillis + 15L * 24L * 3600L * 1000L;
					setDate4TextView(mTextView_endDate, mTextView_endTime, mReturnCarTimeMillis);
					mTextView_dayCounts.setText("15.0天");
					matchingDiscount();
					break;
				default:
					break;
				}
			}
		});

		initSwitchButton();
		initEditText4OtherPhone();
	}

	private void initEditText4OtherPhone() {

		mEt_phone_of_other.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mTv_phone_other_confirm.setVisibility(View.VISIBLE);
				if (s.length() == 11) {
					inputOtherPhoneIsDone = true;
				} else {
					inputOtherPhoneIsDone = false;
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void initSwitchButton() {
		mSwb_is_get.setChecked(false);
		mSwb_is_send.setChecked(false);
		mSwb_is_get.setOnCheckedChangeListener(this);
		mSwb_is_send.setOnCheckedChangeListener(this);

		mSwb_has_driver.setChecked(false);
		// mSwb_is_naked_car_hire.setChecked(false);
		mSwb_has_driver.setOnCheckedChangeListener(this);
		// mSwb_is_naked_car_hire.setOnCheckedChangeListener(this);

		// mSwb_has_tax.setChecked(false);
		mSwb_has_lease4other.setChecked(false);
		mSwb_has_lease4other.setOnCheckedChangeListener(this);

	}

	private void setDate4TextView(TextView tvDateName, TextView tvTimeName, long timeMillis) {
		String dateStr = DateCommonUtils.getTheDateToString(timeMillis, DateCommonUtils.MM_DD);
		String weekStr = DateCommonUtils.getTheDateToString(timeMillis, DateCommonUtils.EE);
		String timeStr = DateCommonUtils.getTheDateToString(timeMillis, DateCommonUtils.HH_MM);
		tvDateName.setText(dateStr);
		tvTimeName.setText(weekStr + " " + timeStr);
	}

	private void setListener() {
		mIv_dache_jg_back.setOnClickListener(this);
		mLl_dache_jg_choose_city_get.setOnClickListener(this);
		mLl_dache_jg_choose_city_return.setOnClickListener(this);
		mLl_dache_jg_choose_time_get.setOnClickListener(this);
		mLl_dache_jg_return_time.setOnClickListener(this);
		mBtn_dache_jg_next.setOnClickListener(this);
		// mBtn_dachezuche_dialog_comfire.setOnClickListener(this);
		mLl_dache_jg_choose_type_gongwuche.setOnClickListener(this);
		mLl_dache_jg_choose_type_shangwuche.setOnClickListener(this);
		mLl_dache_jg_choose_type_zhifache.setOnClickListener(this);
		mLl_dache_jg_choose_type_yueyeche.setOnClickListener(this);
		mLl_dache_jg_choose_type_pika.setOnClickListener(this);
		mLl_dache_jg_choose_type_keche.setOnClickListener(this);
		mLl_dache_jg_order_get_car.setOnClickListener(this);
		mLl_dache_jg_order_return_car.setOnClickListener(this);
		mTv_phone_other_editor.setOnClickListener(this);
		mTv_phone_other_confirm.setOnClickListener(this);
	}

	/**
	 * 设置编辑框是否可点击
	 */
	public void SetEditTextClickable(boolean b) {
		if (b) {
			mEt_dachezuche_dialog_unit_of_account.setEnabled(true);
			mEt_dachezuche_dialog_unit_of_password.setEnabled(true);
		} else {
			mEt_dachezuche_dialog_unit_of_account.setEnabled(false);
			mEt_dachezuche_dialog_unit_of_password.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_dache_jg_back:
			finish();
			break;
		case R.id.ll_dache_jg_choose_city_get:
			// 跳转到城市选择列表界面
			intent.setClass(OrgHomeActivity.this, ChooseCityActivity.class);
			startActivityForResult(intent, Constant.RequestCode.JIGOUZUCHE_TAKE_CAR_CITY);
			break;
		case R.id.ll_dache_jg_choose_city_return:
			// 跳转到城市选择列表界面
			intent.setClass(OrgHomeActivity.this, ChooseCityActivity.class);
			startActivityForResult(intent, Constant.RequestCode.JIGOUZUCHE_RETURN_CAR_CITY);
			break;
		case R.id.ll_dache_jg_choose_time_get:
			dateTimePicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
					.setListener(new StartDateTimePickerListener()).setInitialDate(new Date(mGetCarTimeMillis))
					.setIs24HourTime(true).build();
			dateTimePicker.show();
			break;
		case R.id.ll_dache_jg_return_time:
			dateTimePicker02 = new SlideDateTimePicker.Builder(getSupportFragmentManager())
					.setListener(new EndDateTimePickerListener()).setInitialDate(new Date(mReturnCarTimeMillis))
					.setIs24HourTime(true).build();
			dateTimePicker02.show();
			break;
		case R.id.btn_dache_jg_next:
			if (mReturnCarTimeMillis < 0) {
				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			} else if (!mIsChooseGetCarStore) {
				Toast.makeText(OrgHomeActivity.this, "请选择取车门店或送车上门地址", Toast.LENGTH_SHORT).show();
			} else if (!mIsChooseReturnCarStore) {
				Toast.makeText(OrgHomeActivity.this, "请选择还车门店或上门取车地址", Toast.LENGTH_SHORT).show();
			} else if (!mIsChoose) {
				Toast.makeText(OrgHomeActivity.this, "请选择租用车型", Toast.LENGTH_SHORT).show();
			} else if (!hasFinishOhterPhone) {
				Toast.makeText(OrgHomeActivity.this, "请检查并确认输入的对方联系手机号", Toast.LENGTH_SHORT).show();
			} else {
				intent.setClass(OrgHomeActivity.this, ConfirmOrderActivity.class);
				mHasDriver = hasDriver();
				mNakedCarHire = isNakedCarHire();
				mHasTax = hasTax();
				mHasLeaseForOther = hasLeaseForOther();
				mPhoneOfUser = mEt_phone_of_other.getText().toString().trim();
				ChooseFristInfo chooseFristInfo = new ChooseFristInfo(mUnitOfAccount, mGetCityName, mReturnCityName,
						mGetCarTimeMillis, mReturnCarTimeMillis, mHasDriver, mNakedCarHire, mHasTax, mHasLeaseForOther,
						mPhoneOfUser, mCarType, mCarID, mIsSend, mIsGet, mGetCarStoresId, mReturnCarStoresId,
						mSendCarAddress, mGetCarAddress, mReturnCarStoreName, mGetCarStoreName, mCarInfo, mPlan);
				intent.putExtra(Constant.IntentKey.CHOOSE_FRIST_INFO, chooseFristInfo);
				if (mPrivilegeEvent != null) {
					intent.putExtra(Constant.IntentKey.PRIVILEGE_EVENT, mPrivilegeEvent);
				}
				startActivity(intent);
			}
			break;
		case R.id.btn_dachezuche_dialog_comfire:
			// 企业认证确认按钮
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			String unitOfAccount = mEt_dachezuche_dialog_unit_of_account.getText().toString();
			String unitOfpassword = mEt_dachezuche_dialog_unit_of_password.getText().toString();
			if ("".equals(unitOfAccount) || "".equals(unitOfpassword)) {
				Toast.makeText(OrgHomeActivity.this, "请填写完整的企业账号及密码！", Toast.LENGTH_SHORT).show();
			} else {
				mLl_dache_reminder_prog.setVisibility(View.VISIBLE);
				SetEditTextClickable(false);
				verifyTheUnitOfAccount(unitOfAccount, unitOfpassword);
			}
			break;
		case R.id.ll_dache_jg_choose_type_gongwuche:
			mCarType = 0;
			mPage = 0;
			mIsChoose = false;
			mLl_user_choose_car_info.setVisibility(View.GONE);
			mCarTypeContainsEntityList.clear();
			if (mGetCarTimeMillis > 0 && mReturnCarTimeMillis > 0) {
				showPopupwindowForCarTypeList();
				initTypeCarListData(mCarType + "", mPage + "");
			} else {

				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			}
			mRb_dache_gongwuche.setChecked(true);
			mRb_dache_shangwuche.setChecked(false);
			mRb_dache_zhifache.setChecked(false);
			mRb_dache_yueyeche.setChecked(false);
			mRb_dache_pika.setChecked(false);
			mRb_dache_keche.setChecked(false);

			break;
		case R.id.ll_dache_jg_choose_type_shangwuche:
			mCarType = 1;
			mPage = 0;
			mIsChoose = false;
			mLl_user_choose_car_info.setVisibility(View.GONE);
			mCarTypeContainsEntityList.clear();
			if (mGetCarTimeMillis > 0 && mReturnCarTimeMillis > 0) {
				showPopupwindowForCarTypeList();
				initTypeCarListData(mCarType + "", mPage + "");
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			}
			mRb_dache_gongwuche.setChecked(false);
			mRb_dache_shangwuche.setChecked(true);
			mRb_dache_zhifache.setChecked(false);
			mRb_dache_yueyeche.setChecked(false);
			mRb_dache_pika.setChecked(false);
			mRb_dache_keche.setChecked(false);
			break;
		case R.id.ll_dache_jg_choose_type_zhifache:
			mCarType = 2;
			mPage = 0;
			mIsChoose = false;
			mLl_user_choose_car_info.setVisibility(View.GONE);
			mCarTypeContainsEntityList.clear();
			if (mGetCarTimeMillis > 0 && mReturnCarTimeMillis > 0) {
				showPopupwindowForCarTypeList();
				initTypeCarListData(mCarType + "", mPage + "");
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			}
			mRb_dache_gongwuche.setChecked(false);
			mRb_dache_shangwuche.setChecked(false);
			mRb_dache_zhifache.setChecked(true);
			mRb_dache_yueyeche.setChecked(false);
			mRb_dache_pika.setChecked(false);
			mRb_dache_keche.setChecked(false);
			break;
		case R.id.ll_dache_jg_choose_type_yueyeche:
			mCarType = 3;
			mPage = 0;
			mIsChoose = false;
			mLl_user_choose_car_info.setVisibility(View.GONE);
			mCarTypeContainsEntityList.clear();
			if (mGetCarTimeMillis > 0 && mReturnCarTimeMillis > 0) {
				showPopupwindowForCarTypeList();
				initTypeCarListData(mCarType + "", mPage + "");
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			}
			mRb_dache_gongwuche.setChecked(false);
			mRb_dache_shangwuche.setChecked(false);
			mRb_dache_zhifache.setChecked(false);
			mRb_dache_yueyeche.setChecked(true);
			mRb_dache_pika.setChecked(false);
			mRb_dache_keche.setChecked(false);
			break;
		case R.id.ll_dache_jg_choose_type_pika:
			mCarType = 4;
			mPage = 0;
			mIsChoose = false;
			mLl_user_choose_car_info.setVisibility(View.GONE);
			mCarTypeContainsEntityList.clear();
			if (mGetCarTimeMillis > 0 && mReturnCarTimeMillis > 0) {
				showPopupwindowForCarTypeList();
				initTypeCarListData(mCarType + "", mPage + "");
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			}
			mRb_dache_gongwuche.setChecked(false);
			mRb_dache_shangwuche.setChecked(false);
			mRb_dache_zhifache.setChecked(false);
			mRb_dache_yueyeche.setChecked(false);
			mRb_dache_pika.setChecked(true);
			mRb_dache_keche.setChecked(false);
			break;
		case R.id.ll_dache_jg_choose_type_keche:
			mCarType = 5;
			mPage = 0;
			mIsChoose = false;
			mLl_user_choose_car_info.setVisibility(View.GONE);
			mCarTypeContainsEntityList.clear();
			if (mGetCarTimeMillis > 0 && mReturnCarTimeMillis > 0) {
				showPopupwindowForCarTypeList();
				initTypeCarListData(mCarType + "", mPage + "");
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择还车时间", Toast.LENGTH_SHORT).show();
			}
			mRb_dache_gongwuche.setChecked(false);
			mRb_dache_shangwuche.setChecked(false);
			mRb_dache_zhifache.setChecked(false);
			mRb_dache_yueyeche.setChecked(false);
			mRb_dache_pika.setChecked(false);
			mRb_dache_keche.setChecked(true);
			break;
		case R.id.iv_dailog_close:
			mAlertDialog.dismiss();
			finish();
			break;
		case R.id.ll_car_type_list_pop_close:
			mPopupWindow.dismiss();
			if (!mIsChoose) {
				mRb_dache_gongwuche.setChecked(false);
				mRb_dache_shangwuche.setChecked(false);
				mRb_dache_zhifache.setChecked(false);
				mRb_dache_yueyeche.setChecked(false);
				mRb_dache_pika.setChecked(false);
				mRb_dache_keche.setChecked(false);
			}
			break;
		case R.id.tv_btn_get_more:
			if (mBasePageNum > mPage) {
				mTv_btn_get_more.setEnabled(true);
				mTv_btn_get_more.setText("加载更多数据！");
				initTypeCarListData(mCarType + "", mPage + "");
			} else {
				mTv_btn_get_more.setText("无更多数据！");
				mTv_btn_get_more.setEnabled(false);
			}
			break;

		case R.id.ll_dache_jg_order_get_car:
			if (mIsChooseGetCarCity) {
				if (mSwb_is_send.isChecked()) {
					intent.setClass(OrgHomeActivity.this, MapForChooseAddrActivity.class);
					intent.putExtra(Constant.IntentKey.GET_MAP_LOC_KEY, Constant.IntentKey.GET_MAP_LOC_GET);// 取车:判断地图上显示为取
					intent.putExtra(Constant.IntentKey.CITY, mGetCityName);
					startActivityForResult(intent, Constant.RequestCode.JIGOUZUCHE_TAKE_CAR_MAP);
				} else {
					intent.setClass(OrgHomeActivity.this, StoresListActivity.class);
					intent.putExtra(Constant.IntentKey.GET_MAP_LOC_KEY, Constant.IntentKey.GET_MAP_LOC_GET);// 取车:判断地图上显示为取
					intent.putExtra(Constant.IntentKey.CITY, mGetCityName);
					startActivityForResult(intent, Constant.RequestCode.JIGOUZUCHE_TAKE_CAR_MAP);
				}
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择取车城市", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.ll_dache_jg_order_return_car:
			if (mIsChooseReturnCarCity) {
				if (mSwb_is_get.isChecked()) {
					intent.setClass(OrgHomeActivity.this, MapForChooseAddrActivity.class);
					intent.putExtra(Constant.IntentKey.GET_MAP_LOC_KEY, Constant.IntentKey.GET_MAP_LOC_RETURN);// 取车:判断地图上显示为取
					intent.putExtra(Constant.IntentKey.CITY, mReturnCityName);
					startActivityForResult(intent, Constant.RequestCode.JIGOUZUCHE_RETURN_CAR_MAP);

				} else {
					intent.setClass(OrgHomeActivity.this, StoresListActivity.class);
					intent.putExtra(Constant.IntentKey.GET_MAP_LOC_KEY, Constant.IntentKey.GET_MAP_LOC_RETURN);// 取车:判断地图上显示为还
					intent.putExtra(Constant.IntentKey.CITY, mReturnCityName);
					startActivityForResult(intent, Constant.RequestCode.JIGOUZUCHE_RETURN_CAR_MAP);
				}
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择还车城市", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.tv_phone_other_confirm:
			if (inputOtherPhoneIsDone) {
				mEt_phone_of_other.setEnabled(false);
				hasFinishOhterPhone = true;
				mTv_phone_other_editor.setVisibility(View.VISIBLE);
				mTv_phone_other_confirm.setVisibility(View.GONE);
			} else {
				Toast.makeText(OrgHomeActivity.this, "请正确填写对方联系手机号", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.tv_phone_other_editor:
			hasFinishOhterPhone = false;
			mEt_phone_of_other.setEnabled(true);
			mTv_phone_other_editor.setVisibility(View.GONE);
			mTv_phone_other_confirm.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	/**
	 * 弹出车型对应的车辆列表信息
	 */
	private void initTypeCarListData(String lei, String page) {

		final MyCarDialog myCarDialog = new MyCarDialog(OrgHomeActivity.this);
		myCarDialog.show();
		
//		ProgressDialog = android.app.ProgressDialog.show(OrgHomeActivity.this, null, "拼命加载中…", true, true);
//		ProgressDialog.show();
		Map<String, String> params = new HashMap<String, String>();
		params.put("lei", lei);
		params.put("page", page);
		params.put("zuchuDate", mGetCarTimeMillis + "");
		params.put("planReturnDate", mReturnCarTimeMillis + "");
		ApiClient.getCarListByLei(OrgHomeActivity.this, params, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
			}

			@Override
			public void onResponse(String s) {
				if (s != null && !"".equals(s)) {
					myCarDialog.dismiss();
					mPage++;
					TypeCarListInfo typeCarListInfo = GsonUtils.parseJSON(s, TypeCarListInfo.class);
					mBasePageNum = typeCarListInfo.getNum();
					if (typeCarListInfo.getContains() != null && typeCarListInfo.getContains().size() > 0) {
						mCarTypeContainsEntityList.addAll(typeCarListInfo.getContains());
					}
					if (mBasePageNum == mPage) {
						mTv_btn_get_more.setVisibility(View.GONE);
					} else {
						mTv_btn_get_more.setVisibility(View.VISIBLE);
					}
					if (mCarTypeContainsEntityList != null && mCarTypeContainsEntityList.size() > 0) {
						mTv_no_result.setVisibility(View.GONE);
						mTv_btn_get_more.setVisibility(View.VISIBLE);

					} else {
						mTv_no_result.setVisibility(View.VISIBLE);
						mTv_btn_get_more.setVisibility(View.GONE);
					}
					mCarTypeListAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	/**
	 * 弹出车辆类型对应的汽车列表
	 */
	public void showPopupwindowForCarTypeList() {
		View inflate = getLayoutInflater().inflate(R.layout.car_type_list_pop_layout, null);
		mLl_car_type_list_pop_close = (LinearLayout) inflate.findViewById(R.id.ll_car_type_list_pop_close);
		mTv_no_result = (TextView) inflate.findViewById(R.id.tv_no_result);
		mTv_btn_get_more = (TextView) inflate.findViewById(R.id.tv_btn_get_more);
		mLl_car_type_list_pop_close.setOnClickListener(this);
		mTv_btn_get_more.setOnClickListener(this);
		mLv_car_type_list_info = (ListView) inflate.findViewById(R.id.lv_car_type_list_info);
		mLv_car_type_list_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mIsChoose = true;
				mLl_user_choose_car_info.setVisibility(View.VISIBLE);
				if (1 == mCarTypeContainsEntityList.get(position).getCar().getStatus()) {
					Toast.makeText(OrgHomeActivity.this, "该车辆已出租!", Toast.LENGTH_SHORT).show();
				} else {
					mCarID = mCarTypeContainsEntityList.get(position).getCar().getId();
					mCarInfo = mCarTypeContainsEntityList.get(position).getCar();
					mPlan = mCarTypeContainsEntityList.get(position).getPlan();
					mLl_user_choose_car_info.setVisibility(View.VISIBLE);
					UILUtils.displayImageNoAnim(mCarTypeContainsEntityList.get(position).getCar().getImage(),
							mIv_car_img);
					mTv_car_name.setText(mCarTypeContainsEntityList.get(position).getCar().getModel()
							+ mCarTypeContainsEntityList.get(position).getCar().getType());
					mTv_carriage_count.setText(mCarTypeContainsEntityList.get(position).getCar().getBox());
					if (0 == mCarTypeContainsEntityList.get(position).getCar().getZidong()) {
						mTv_displacement
								.setText(mCarTypeContainsEntityList.get(position).getCar().getPailiang() + "自动");
					} else {
						mTv_displacement
								.setText(mCarTypeContainsEntityList.get(position).getCar().getPailiang() + "手动");

					}
					mTv_car_seat_count
							.setText("乘坐" + mCarTypeContainsEntityList.get(position).getCar().getSeat() + "人");
					mTv_car_price.setText(mCarTypeContainsEntityList.get(position).getPlan().getPrice() + "");
					mPopupWindow.dismiss();
				}
			}
		});
		mCarTypeListAdapter = new carTypeListAdapter();
		mLv_car_type_list_info.setAdapter(mCarTypeListAdapter);
		// 最后一个参数为true，点击PopupWindow消失,宽必须为match，不然肯呢个会导致布局显示不完全
		mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 设置外部点击无效
		mPopupWindow.setOutsideTouchable(false);
		// 设置背景变暗
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		BitmapDrawable bitmapDrawable = new BitmapDrawable();
		mPopupWindow.setBackgroundDrawable(bitmapDrawable);
		mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
	}

	private int hasDriver() {
		if (mSwb_has_driver.isChecked()) {
			return 0;
		} else {
			return 1;
		}
	}

	private int isNakedCarHire() {
		// if (mSwb_is_naked_car_hire.isChecked())
		// {
		// return 1;
		// } else
		// {
		// return 0;
		// }
		return 0;
	}

	private int hasTax() {
		// if (mSwb_has_tax.isChecked())
		// {
		// return 1;
		// } else
		// {
		// return 0;
		// }
		return 1;
	}

	private int hasLeaseForOther() {
		if (mSwb_has_lease4other.isChecked()) {

			return 1;
		} else {
			return 0;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("map", "mapfanhui");
		if (data != null) {
			if (requestCode == Constant.RequestCode.JIGOUZUCHE_TAKE_CAR_CITY
					&& resultCode == Constant.ResultCode.CHOOSE_CITY) {
				mGetCityName = data.getStringExtra(Constant.IntentKey.CHOOSE_CITY);
				mTextView_take_car_city.setText(mGetCityName);
				mIsChooseGetCarCity = true;
			}

			if (requestCode == Constant.RequestCode.JIGOUZUCHE_RETURN_CAR_CITY
					&& resultCode == Constant.ResultCode.CHOOSE_CITY) {
				mReturnCityName = data.getStringExtra(Constant.IntentKey.CHOOSE_CITY);
				mTextView_return_car_city.setText(mReturnCityName);
				mIsChooseReturnCarCity = true;
			}

			switch (requestCode) {
			// 取车门店
			case Constant.RequestCode.JIGOUZUCHE_TAKE_CAR_MAP:
				if (resultCode == Constant.ResultCode.CHOOSE_STORE) {
					Log.e("map", "map");
					mGetCarStoreName = data.getStringExtra(Constant.IntentKey.STORES_MAP_KEY);
					mGetCarStoresId = data.getIntExtra(Constant.IntentKey.STORES_ID_KEY, -1);
					mTv_dache_jg_store_name_get.setText(mGetCarStoreName);
					mIsChooseGetCarStore = true;
				} else {
					mSendCarAddress = data.getStringExtra(Constant.IntentKey.MAP_FOR_CHOOSE_ADDR);
					mTv_dache_jg_store_name_get.setText(mSendCarAddress);
					mIsChooseGetCarStore = true;
				}
				break;
			// 还车门店
			case Constant.RequestCode.JIGOUZUCHE_RETURN_CAR_MAP:
				if (resultCode == Constant.ResultCode.CHOOSE_STORE) {
					mReturnCarStoreName = data.getStringExtra(Constant.IntentKey.STORES_MAP_KEY);
					mReturnCarStoresId = data.getIntExtra(Constant.IntentKey.STORES_ID_KEY, -1);
					mTv_dache_jg_store_name_return.setText(mReturnCarStoreName);
					mIsChooseReturnCarStore = true;
				} else {
					mGetCarAddress = data.getStringExtra(Constant.IntentKey.MAP_FOR_CHOOSE_ADDR);
					mTv_dache_jg_store_name_return.setText(mGetCarAddress);
					mIsChooseReturnCarStore = true;
				}
				break;
			}
		}
	}

	class carTypeListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mCarTypeContainsEntityList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = getLayoutInflater().inflate(R.layout.dachezuche_choose_car_type_list_item, null);
			mRl_already_rent = (RelativeLayout) layout.findViewById(R.id.rl_already_rent);
			ImageView iv_car_img = (ImageView) layout.findViewById(R.id.iv_car_img);// 车型图片
			TextView tv_car_name = (TextView) layout.findViewById(R.id.tv_car_name);// 车型名称
			TextView tv_car_price = (TextView) layout.findViewById(R.id.tv_car_price);// 租金
			TextView tv_carriage_count = (TextView) layout.findViewById(R.id.tv_carriage_count);// 厢数(三厢)
			TextView tv_displacement = (TextView) layout.findViewById(R.id.tv_displacement);// 排量(1.4自动)
			TextView tv_car_seat_count = (TextView) layout.findViewById(R.id.tv_car_seat_count);// 可乘坐位数(乘坐5人)
			if (mCarTypeContainsEntityList != null && mCarTypeContainsEntityList.size() > 0) {
				if (1 == mCarTypeContainsEntityList.get(position).getCar().getStatus()) {
					mRl_already_rent.setVisibility(View.VISIBLE);
				} else {
					mRl_already_rent.setVisibility(View.GONE);
				}
				TypeCarListInfo.ContainsBean carInfo = mCarTypeContainsEntityList.get(position);
				if (iv_car_img != null) {
					UILUtils.displayImageNoAnim(carInfo.getCar().getImage(), iv_car_img);
				}
				tv_car_name.setText(carInfo.getCar().getModel() + carInfo.getCar().getType());
				tv_car_price.setText(carInfo.getPlan().getPrice() + "");
				tv_carriage_count.setText(carInfo.getCar().getBox());
				if (carInfo.getCar().getZidong() == 0) {
					tv_displacement.setText(carInfo.getCar().getPailiang() + "自动");
				} else {
					tv_displacement.setText(carInfo.getCar().getPailiang() + "手动");
				}
				tv_car_seat_count.setText("乘坐" + carInfo.getCar().getSeat() + "人");
			}
			return layout;
		}
	}

	/**
	 * 弹出时间选择器
	 */
	public void showSlideDateTimePicker(SlideDateTimeListener listener, Date initialDate) {
		new SlideDateTimePicker.Builder(getSupportFragmentManager()).setListener(listener).setInitialDate(initialDate)
				.setIs24HourTime(true).build().show();
	}

	/**
	 * 起始时间选择器的回调监听
	 */
	class StartDateTimePickerListener extends SlideDateTimeListener {
		@Override
		public void onDateTimeSet(Date date) {
			boolean before = date.before(new Date(mGetCarTimeMillis4Compare));
			if (!before) {
				String theDateToString = DateCommonUtils.getTheDateToString(date.getTime(),
						DateCommonUtils.YYYY_MM_DD_EE_HH_MM);
				try {
					mGetCarTimeMillis = new SimpleDateFormat("yyyy-MM-dd EE HH:mm").parse(theDateToString).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Double howLongDay = DateCommonUtils.getHowLongDay(mGetCarTimeMillis, mReturnCarTimeMillis);
				if (howLongDay <= 0.0) {
					mReturnCarTimeMillis = mGetCarTimeMillis + 24L * 3600L * 1000L;
					setDate4TextView(mTextView_endDate, mTextView_endTime, mReturnCarTimeMillis);
					howLongDay = DateCommonUtils.getHowLongDay(mGetCarTimeMillis, mReturnCarTimeMillis);
				}
				setDate4TextView(mTextView_startDate, mTextView_startTime, mGetCarTimeMillis);
				mTextView_dayCounts.setText(howLongDay + "天");
				matchingDiscount();
			} else {
				Toast.makeText(OrgHomeActivity.this, "请选择合理的取车时间", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class EndDateTimePickerListener extends SlideDateTimeListener {

		@Override
		public void onDateTimeSet(Date date) {
			boolean before = date.before(new Date(mReturnCarTimeMillis4Compare));
			if (!before) {
				mRg_dache_jg_months.clearCheck();
				String theDateToString = DateCommonUtils.getTheDateToString(date.getTime(),
						DateCommonUtils.YYYY_MM_DD_EE_HH_MM);
				try {
					mReturnCarTimeMillis = new SimpleDateFormat("yyyy-MM-dd EE HH:mm").parse(theDateToString).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Double howLongDay = DateCommonUtils.getHowLongDay(mGetCarTimeMillis, mReturnCarTimeMillis);
				if (howLongDay <= 0.0) {
					Toast.makeText(OrgHomeActivity.this, "至少租车2小时", Toast.LENGTH_SHORT).show();
				} else {

					setDate4TextView(mTextView_endDate, mTextView_endTime, mReturnCarTimeMillis);
					mTextView_dayCounts.setText(howLongDay + "天");
				}
				matchingDiscount();

			} else {
				Toast.makeText(OrgHomeActivity.this, "至少租车2小时", Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void matchingDiscount() {
		ApiClient.cancelAll(OrgHomeActivity.this);
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", DateCommonUtils.getTheDateToString(mGetCarTimeMillis, DateCommonUtils.YYYY_MM_DD_HH_MM));
		params.put("end", DateCommonUtils.getTheDateToString(mReturnCarTimeMillis, DateCommonUtils.YYYY_MM_DD_HH_MM));

		ApiClient.getMatchingDiscount(OrgHomeActivity.this, params, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}

			@Override
			public void onResponse(String s) {
				mPrivilegeEvent = GsonUtils.parseJSON(s, PrivilegeEvent.class);
				if (mPrivilegeEvent != null && mPrivilegeEvent.getPrivilege() != null
						&& mPrivilegeEvent.getDiscount() != null) {
					mTv_what_discount.setVisibility(View.VISIBLE);
					mTv_what_discount.setText(
							mPrivilegeEvent.getPrivilege().getName() + "--" + mPrivilegeEvent.getDiscount().getName());

				} else {
					mTv_what_discount.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.swb_org_is_send:
			mIsChooseGetCarStore = false;
			if (isChecked) {
				mTv_org_get_car_stores_title.setText("送车上门");
				mTv_dache_jg_store_name_get.setText("请选择送车上门地点");
				mIsSend = 1;
			} else {
				mTv_org_get_car_stores_title.setText("取车门店");
				mTv_dache_jg_store_name_get.setText("请选择取车门店");
				mIsSend = 0;
			}
			break;
		case R.id.swb_org_is_get:
			mIsChooseReturnCarStore = false;
			if (isChecked) {
				mTv_org_retrun_car_stores_title.setText("上门取车");
				mTv_dache_jg_store_name_return.setText("请选择上门取车地点");
				mIsGet = 1;
			} else {
				mTv_org_retrun_car_stores_title.setText("还车门店");
				mTv_dache_jg_store_name_return.setText("请选择还车门店");
				mIsGet = 0;
			}
			break;
		// case R.id.swb_is_naked_car_hire:
		//
		// if (mSwb_is_naked_car_hire.isChecked())
		// {
		// mSwb_has_driver.setChecked(false);
		// mSwb_has_driver.setEnabled(false);
		// } else
		// {
		// mSwb_has_driver.setChecked(false);
		// mSwb_has_driver.setEnabled(true);
		// }
		// break;
		case R.id.swb_has_lease4other:
			if (mSwb_has_lease4other.isChecked()) {
				mLl_phone_of_other.setVisibility(View.VISIBLE);
				mTv_lease4other_line.setVisibility(View.VISIBLE);
				hasFinishOhterPhone = false;
			} else {
				mLl_phone_of_other.setVisibility(View.GONE);
				mTv_lease4other_line.setVisibility(View.GONE);
				hasFinishOhterPhone = true;
			}
			break;
		}
	}
}
