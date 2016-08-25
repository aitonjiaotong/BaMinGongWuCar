package cn.com.easytaxi.platform;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.NewNetworkRequest;
import cn.com.easytaxi.client.channel.TcpClient;
import cn.com.easytaxi.client.common.MsgConst;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.common.User;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.mine.MyMainActivity;
import cn.com.easytaxi.onetaxi.NewMainActivityNew;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.common.Util;
import cn.com.easytaxi.platform.service.EasyTaxiCmd;
import cn.com.easytaxi.platform.service.MainService;
import cn.com.easytaxi.service.AlarmClockBookService;
import cn.com.easytaxi.ui.MoreWebviewActivity;
import cn.com.easytaxi.ui.view.CommonDialog;
import cn.com.easytaxi.util.RandomUtils;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;
import com.aiton.yc.client.R;
import com.google.gson.JsonObject;

public class RegisterActivity extends Activity {
	private ProgressDialog progressDialog;
	public static final String ACTION_REGISTER = "cn.com.easytaxi.ACTION_REGISTER";

	protected static final int CLOSE_DLG = 250;
	public static final int SEND_SMS_OK = 900;

	private RegisterActivity self = this;

	private EditText mobileEditText;
	private EditText codeEditText;
	private EditText recommendEditText;
	private TitleBar bar;

	private Spinner sexSpinner;
	private CheckBox tiaokuan;

	private Button sendSmsButton; // 发送短信按钮
	private Button submitButton;
	private String mobile;

	// private Long code;

	// 注册成功后执行的代码类型：0-只广播，1-广播并按pkg.className跳转，并关闭设置界面
	private int type;

	private String pkg;
	private String className;

	private SessionAdapter dao;

	private String code = "1358";

	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_register1);
		// setContentView(R.layout.p_register);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				if (msg.what == SEND_SMS_OK) {
					String str = (String) msg.obj;
					if (!TextUtils.isEmpty(str)) {
						try {
							JSONObject json = new JSONObject(str);
							int error = json.getInt("error");
							if (error == 0) {
								ToastUtil.show(RegisterActivity.this, "正在为您发送注册码，请稍等！");
							} else {
								ToastUtil.show(RegisterActivity.this, "注册码获取失败，请稍后重试！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ToastUtil.show(RegisterActivity.this, "注册码获取失败，请稍后重试！");
					}
				}
			}
		};

		bar = new TitleBar(this);
		bar.setTitleName("登录/注册");
		bar.switchToCityButton();
		bar.getRightCityButton().setVisibility(View.GONE);
		bar.getRightHomeButton().setVisibility(View.GONE);
		bar.setBackCallback(new Callback<Object>() {

			@Override
			public void handle(Object param) {
				// TODO Auto-generated method stub
				// doBack();
				finish();
			}
		});

		findViewById(R.id.layout).setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					InputMethodManager imm = (InputMethodManager) RegisterActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return false;
			}
		});

		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		pkg = intent.getStringExtra("pkg");
		className = intent.getStringExtra("className");

		dao = new SessionAdapter(self);

		// recommendEditText = (EditText) findViewById(R.id.register_recommend);
		mobileEditText = (EditText) findViewById(R.id.register_mobile);
		findViewById(R.id.tiaokuan_lable).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				doShowProtocol(v);
			}
		});
		tiaokuan = (CheckBox) findViewById(R.id.tiaokuan);
		// sexSpinner = (Spinner) findViewById(R.id.spinner_register_sex);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, sexValues);
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// sexSpinner.setAdapter(adapter);

		String tmpMobile = dao.get("TMP_MOBILE");

		if (TextUtils.isEmpty(tmpMobile)) {

		} else {
			mobileEditText.setText(tmpMobile);
		}

		sendSmsButton = (Button) findViewById(R.id.register_sendmsg);
		sendSmsButton.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View button) {
				mobile = mobileEditText.getText().toString();
				if (mobile == null || mobile.equals("") || !mobile.matches(Util.REGEX_MOBILE)) {
					ToastUtil.show(self, "请输入手机号码");
					// Window.info(self, "请输入正确的手机号码");
					return;
				} else {
					dao.set("TMP_MOBILE", mobile);

					code = generageCode(mobile);
					SysDeug.logD("code - > " + code);
					sendSms(mobile);

					sendSmsButton.setEnabled(false);
					// sendSmsButton.setBackgroundResource(R.drawable.icon013);
					new AsyncTask<Object, Integer, Object>() {
						@Override
						protected void onProgressUpdate(Integer... values) {
							sendSmsButton.setText("重发 (" + values[0] + "s)");
						}

						@Override
						protected void onPostExecute(Object result) {
							sendSmsButton.setEnabled(true);
							sendSmsButton.setText("重新获取");
							// sendSmsButton.setBackgroundResource(R.drawable.icon011);
						}

						@Override
						protected Object doInBackground(Object... params) {
							try {
								for (int i = 90; i >= 0; i--) {
									publishProgress(i);
									Thread.sleep(1000);
								}
							} catch (Throwable e) {
								e.printStackTrace();
							}
							return null;
						}
					}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			}
		});

		codeEditText = (EditText) findViewById(R.id.register_code);

		submitButton = (Button) findViewById(R.id.register_submit);
		submitButton.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View v) {
				mobile = mobileEditText.getText().toString();
				String _code = codeEditText.getText().toString();
				// String recommend = recommendEditText.getText().toString();
				if (mobile == null || mobile.equals("") || !mobile.matches(Util.REGEX_MOBILE)) {
					// Window.info(self, "请输入正确的手机号码");
					ToastUtil.show(self, "请输入手机号码");
					return;
				}
				if (!tiaokuan.isChecked()) {
					// Window.info(self, "请勾选使用条款");
					ToastUtil.show(self, "请勾选服务条款");
					return;
				}
				// if (recommend.length() > 0 &&
				// !recommend.matches(Util.REGEX_MOBILE)) {
				// Toast.makeText(self, "请输入正确的推荐人号码",
				// Toast.LENGTH_SHORT).show();
				// //Window.info(self, "请输入正确的推荐人号码");
				// return;
				// }

				if (mobile == null || mobile.equals("")) {
					ToastUtil.show(self, "请输入手机号码");
					// Window.info(self, "请输入正确的本机号码");
					return;
				} else {
					String number = dao.get("TMP_MOBILE");
					if (number != null) {
						if (!mobile.equals(number)) {
							ToastUtil.show(self, "请重新获取验证码");
							// Window.info(self, "请重新获取验证码");
							submitButton.setEnabled(true);
							return;
						}
					}
				}

				submitButton.setEnabled(false);

				if (code.equals(_code)) {
					new LoadNickName().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mobile);
				} else {
					submitButton.setEnabled(true);
					codeEditText.setText("");
					ToastUtil.show(self, "请输入正确的验证码");
					// Window.info(self, "验证码输入不正确");
				}
			}
		});
	}

	private class LoadNickName extends AsyncTask<String, Integer, Boolean> {
		private boolean iserror = false;
		private String errorMsg = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			AppLog.LogD("LoadNickName onPreExecute");
			progressDialog = ProgressDialog.show(RegisterActivity.this, "提示", "请稍后...", true, true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			JsonObject json = new JsonObject();
			json.addProperty("action", "userAction");
			// json.addProperty("method", "getPassengerName");
			json.addProperty("phone", params[0]);
			json.addProperty("id", params[0]);
			json.addProperty("source", 1);
			json.addProperty("method", "regPassenger");
			try {
				SysDeug.logD("注册接口-> req : " + json.toString());
				byte[] response = TcpClient.send(1L, MsgConst.MSG_TCP_ACTION, json.toString().getBytes("UTF-8"));
				if (response != null && response.length > 0) {
					String rs = new String(response, "UTF-8");
					// AppLog.LogD("doInBackground response-->" + rs);
					SysDeug.logD("注册接口-> res : " + rs);
					JSONObject retJson = new JSONObject(rs);
					int error = retJson.getInt("error");
					if (error == 0) {
						String nickName = retJson.getString("name");
						if (TextUtils.isEmpty(nickName)) {
							return false;
						} else {
							int sex = 0;
							dao.set(User._NICKNAME, nickName);
							saveLocal(mobile, nickName, sex);
							return true;
						}
					} else {
						errorMsg = retJson.getString("errormsg");
						iserror = true;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.cancel();
			submitButton.setEnabled(true);
			if (iserror) {
				// Toast.makeText(self, errorMsg, Toast.LENGTH_SHORT).show();
				ToastUtil.show(self, "登录/注册失败");
				return;
			}

			registerSuccess();
			if (result) {
				startActivity(new Intent(self, NewMainActivityNew.class));
			} else {
				Intent i = new Intent(RegisterActivity.this, AddUserInfoActivity.class);
				i.putExtra("mobile", mobile);
				RegisterActivity.this.startActivity(i);
				finish();

			}
		}

	}

	/**
	 * 跳转到服务条款
	 * 
	 * @param v
	 */
	public void doShowProtocol(View v) {
		Intent intent = new Intent(this, MoreWebviewActivity.class);
		intent.putExtra("title", "八闽专车服务条款");
		String uri = "file:///android_asset/tiaokuan_shanxi.html";
		intent.putExtra("uri", uri);
		intent.putExtra("type", 0);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		submitButton.setEnabled(true);
		if (requestCode == 100) {
			if (resultCode == Activity.RESULT_OK) {
				String nickName = data.getStringExtra("nickName");
				int sex = data.getIntExtra("sex", 0);
				dao.set(User._NICKNAME, nickName);
				saveLocal(mobile, nickName, sex);
				registerSuccess();
			} else {
				ToastUtil.show(RegisterActivity.this, "取消注册");
			}
		}
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

	private void registerSuccess() {

		RegisterActivity.this.sendBroadcast(new Intent(ACTION_REGISTER));

		Intent serviceIntent = new Intent(self, MainService.class);
		serviceIntent.setAction(EasyTaxiCmd.START_MAINSERVICE);
		startService(serviceIntent);

		if (type == 1) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName cn = new ComponentName(pkg, pkg + "." + className);
			intent.setComponent(cn);
			self.startActivity(intent);
		}

		if (type == 2) {
			Intent intent = new Intent(self, MyMainActivity.class);
			self.startActivity(intent);
		}
		// ETApp.getInstance().setLogin(true);
		self.finish();
		submitButton.setEnabled(true);
		// finish();
		// 开启订单闹钟提醒服务
		Intent alarmIntent = new Intent(self, AlarmClockBookService.class);
		startService(alarmIntent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyDialog.comfirm(RegisterActivity.this, "温馨提示", "确定要退出注册吗？", new SureCallback() {
				@Override
				public void onClick(int tag) {
					if (tag == LEFT) {
					} else {
						RegisterActivity.this.finish();
					}
				}
			});
			// showDialog(CLOSE_DLG);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case CLOSE_DLG:
			return createLogoutDlg();
			// break;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	private Dialog createLogoutDlg() {
		Callback<Object> okBtnCallback = new Callback<Object>() {
			@Override
			public void handle(Object param) {
				CommonDialog dialog = (CommonDialog) param;
				dialog.dismiss();
				RegisterActivity.this.finish();
			}
		};

		Callback<Object> cancelBtnCallback = new Callback<Object>() {
			@Override
			public void handle(Object param) {
				CommonDialog dialog = (CommonDialog) param;
				dialog.dismiss();
				// dismissDialog(CLOSE_DLG);
			}
		};

		Dialog dialog = new CommonDialog(RegisterActivity.this, "注意", "确定要退出注册吗？", "确定", "取消", R.layout.dlg_close, okBtnCallback, cancelBtnCallback);
		// dialog.show();
		return dialog;
	}

	@Override
	protected void onDestroy() {
		if (dao != null) {
			dao.close();
			dao = null;
		}
		bar.close();
		super.onDestroy();
	}

	private void sendSms(String mobile) {
		NewNetworkRequest.sendMms(handler, mobile, getAuthCode());
	}

	private String getAuthCode() {
		return code;
	}

	private String generageCode(String mobile) {
		String code = RandomUtils.getRandomNumbers(4);
		if (code.length() != 4) {
			try {
				code = errorCodes[code.length() % 3];
			} catch (Exception e) {
				code = "8765";
				e.printStackTrace();
			}

		}
		return code;
	}

	private String[] errorCodes = new String[] { "3213", "2313", "8765" };

	private static final String[] sexValues = new String[] { "保密", "女", "男" };
}
