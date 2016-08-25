package cn.com.easytaxi.platform;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.NewNetworkRequest;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.common.User;
import cn.com.easytaxi.dialog.ListSelectDialog;
import cn.com.easytaxi.dialog.ListSelectDialog.SelectListener;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.service.EasyTaxiCmd;
import cn.com.easytaxi.platform.service.MainService;
import cn.com.easytaxi.service.AlarmClockBookService;
import cn.com.easytaxi.ui.MoreWebviewActivity;
import cn.com.easytaxi.util.BDLocationServer;
import cn.com.easytaxi.util.ToastUtil;
import passenger.view.activity.MainActivity;
import cn.com.easytaxi.util.BDLocationServer.LocReceive;

import com.baidu.location.BDLocation;
import com.aiton.yc.client.R;

public class AddUserInfoActivity extends Activity implements SelectListener, LocReceive {
	private SessionAdapter dao;
	private ProgressDialog progressDialog;
	public static final String ACTION_REGISTER = "cn.com.easytaxi.ACTION_REGISTER";

	protected static final int CLOSE_DLG = 250;

	private EditText recommendEditText;
	private EditText nickNameEditText;
	private String nickName;
	private TitleBar bar;
	private CheckBox mCbProtocol;
	// private Spinner sexSpinner;
	private EditText recommendNameEditText;
	private Button submitButton;
	private ListSelectDialog sexSelect;
	private TextView sexTv;
	private String mobile;
	private int sex = 0;
	private String cityName = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_register_more);

		BDLocationServer.getInstance(this).setReceiveListenern(this);
		BDLocationServer.getInstance(this).requestLocation();

		mobile = this.getIntent().getStringExtra("mobile");
		dao = new SessionAdapter(this);
		bar = new TitleBar(this);
		bar.setTitleName("完善信息");
		bar.switchToCityButton();
		bar.getRightCityButton().setVisibility(View.GONE);
		bar.getRightHomeButton().setVisibility(View.GONE);
		bar.setBackCallback(new Callback<Object>() {

			@Override
			public void handle(Object param) {
				// TODO Auto-generated method stub
				back(false);
			}
		});

		findViewById(R.id.register_tv_protocol).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doShowProtocol(v);
			}
		});

		findViewById(R.id.register_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back(false);
			}
		});

		findViewById(R.id.layout).setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					InputMethodManager imm = (InputMethodManager) AddUserInfoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return false;
			}
		});
		sexTv = (TextView) findViewById(R.id.spinner_register_sex);
		sexTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sexSelect.show(0);
			}
		});
		sexTv.setText("保密");

		try {
			sex = Integer.parseInt(dao.get(User._SEX));
			if (sex == 1) {
				sexTv.setText("男");
			} else if (sex == 2) {
				sexTv.setText("女");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		recommendEditText = (EditText) findViewById(R.id.register_recommend);
		nickNameEditText = (EditText) findViewById(R.id.register_nickname);
		mCbProtocol = (CheckBox) findViewById(R.id.register_cb_protocol);
		mCbProtocol.setChecked(false);
		recommendNameEditText = (EditText) findViewById(R.id.register_recommend_name);
		sexSelect = ListSelectDialog.newInstance(this);
		sexSelect.canDisEnable(false);
		sexSelect.setCanceledOnTouchOutside(true);
		// sexSpinner = (Spinner) findViewById(R.id.spinner_register_sex);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, sexValues);
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// sexSpinner.setAdapter(adapter);
		sexSelect.setTitle("选择性别");
		sexSelect.setSelectListener(this);
		sexSelect.setDatas(new String[] { "保密", "男", "女" }, sex);
		String name = dao.get("_NAME");
		if (!TextUtils.isEmpty(name)) {
			nickNameEditText.setText(name);
		}
		submitButton = (Button) findViewById(R.id.register_submit);
		submitButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
				// sex = sexSpinner.getSelectedItemPosition() - 1;
				nickName = nickNameEditText.getText().toString();
				String recommend = recommendEditText.getText().toString();
				String recName = recommendNameEditText.getText().toString();

				// if (!mCbProtocol.isChecked()) {
				// Window.info(AddUserInfoActivity.this, "请先阅读并同意服务条款!");
				// return;
				// }

				if (StringUtils.isEmpty(nickName)) {
					ToastUtil.show(AddUserInfoActivity.this, "昵称不能为空");
					return;
				}
				submitButton.setEnabled(false);
				progressDialog = ProgressDialog.show(AddUserInfoActivity.this, "请稍后", "处理中...", true, true);
				NewNetworkRequest.regNewUser(nickName, mobile, Long.valueOf(mobile), sex == 0 ? -1 : sex, "0", cityName, new Callback<Object>() {
					@Override
					public void handle(Object param) {
						if (param != null) {
							try {
								JSONObject jsonObject = (JSONObject) param;
								System.out.println("params - > " + param.toString());
								if (jsonObject.getInt("error") == 0) {
									ToastUtil.show(AddUserInfoActivity.this, "提交成功");
									submitButton.setEnabled(true);
									back(true);
								} else {
									ToastUtil.show(AddUserInfoActivity.this, jsonObject.getString("errormsg"));
								}
							} catch (Exception e) {
								e.printStackTrace();
								ToastUtil.show(AddUserInfoActivity.this, "提交失败，请重新提交");
							}
						} else {
							ToastUtil.show(AddUserInfoActivity.this, "提交失败，请重新提交");
						}

					}

					@Override
					public void error(Throwable e) {
						ToastUtil.show(AddUserInfoActivity.this, "提交失败，请重新提交");
					}

					@Override
					public void complete() {
						submitButton.setEnabled(true);
						progressDialog.cancel();
					}
				});

			}
		});
	}

	private void saveLocal(String mobile, String nickName, int sex) {
		dao.delete("TMP_MOBILE");
		dao.set("_MOBILE", mobile);
		dao.set("TMP_MOBILE", mobile);
		dao.set("_NAME", nickName);
		dao.set("_CITY_ID", Config.default_city.cityId + "");
		dao.set("_CITY_NAME", Config.default_city.cityName);
		dao.set(User._NICKNAME, nickName);
		dao.set(User._SEX, String.valueOf(sex));
		dao.set(User._ISLOGIN, User._LOGIN_LOGIN);
		dao.set(User._MOBILE_NEW, mobile);

		User user = ETApp.getInstance().getCurrentUser();
		try {
			user.setPassengerId(StringUtils.isEmpty(mobile) ? 0 : Long.valueOf(mobile));
			dao.set(User._PUID, StringUtils.isEmpty(mobile) ? "0" : mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setUserNickName(nickName);
		user.setPhoneNumber("_MOBILE", mobile);
		user.setLogin(true);
	}

	private void back(boolean isSuccess) {
		if (isSuccess) {
			saveLocal(mobile, nickName, sex);
			sendBroadcast(new Intent(ACTION_REGISTER));
			Intent serviceIntent = new Intent(this, MainService.class);
			serviceIntent.setAction(EasyTaxiCmd.START_MAINSERVICE);
			startService(serviceIntent);
			Intent alarmIntent = new Intent(this, AlarmClockBookService.class);
			startService(alarmIntent);
			startActivity(new Intent(this, MainActivity.class));
		}
		finish();
	}

	@Override
	protected void onDestroy() {
		bar.close();
		if (dao != null)
			dao.close();
		super.onDestroy();
	}

	public void doShowProtocol(View v) {
		Intent intent = new Intent(AddUserInfoActivity.this, MoreWebviewActivity.class);
		intent.putExtra("title", "服务条款");
		String uri = "file:///android_asset/tiaokuan_shanxi.html";
		intent.putExtra("uri", uri);
		intent.putExtra("type", 0);
		startActivity(intent);
	}

	private static final String[] sexValues = new String[] { "保密", "女", "男" };

	@Override
	public void onSelect(int flag, int tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSure(int flag, int tag, String value) {
		sexTv.setText(value);
		sex = tag;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location != null)
			this.cityName = location.getCity();
	}
}
