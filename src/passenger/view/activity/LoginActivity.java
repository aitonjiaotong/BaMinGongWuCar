package passenger.view.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.client.channel.TcpClient;
import cn.com.easytaxi.client.common.MsgConst;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.common.User;
import cn.com.easytaxi.mine.MyMainActivity;
import cn.com.easytaxi.platform.AddUserInfoActivity;
import cn.com.easytaxi.platform.service.EasyTaxiCmd;
import cn.com.easytaxi.platform.service.MainService;
import cn.com.easytaxi.service.AlarmClockBookService;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;
import passenger.constant.Constant;
import passenger.model.UserZhuChe;
import passenger.model.JiGouInfo;
import passenger.model.UserLoginInfo;
import passenger.utils.ACache;
import passenger.utils.ApiClient;
import passenger.utils.Installation;
import passenger.utils.IsMobileNOorPassword;
import passenger.utils.LoginState;
import passenger.utils.VersionAndServerIsCanUse;
import passenger.view.customview.AnimCheckBox;
import passenger.view.customview.MyCarDialog;
import passenger.view.customview.SingleBtnDialog;
import passenger.view.customview.SingleBtnDialog.ClickListenerInterface;
import shane_library.shane.upgrade.UpgradeUtils;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.VolleyListener;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText edit_jigouhao;
	private EditText edit_jigoumima;
	private EditText edit_paidanyuan;
	private AnimCheckBox ck_remember_account;
	private String jigoumimaACache;
	private String paidanyuanACache;
	private LoginActivity self = this;
	private SessionAdapter dao;
	private String unitOfAccount;
	private String unitOfpassword;
	private String paidanyuan;
	public static final String ACTION_REGISTER = "cn.com.easytaxi.ACTION_REGISTER";
	// 注册成功后执行的代码类型：0-只广播，1-广播并按pkg.className跳转，并关闭设置界面
	private int type;
	private String pkg;
	private String className;
	private LoginState mLoginState;
	private MyCarDialog myProgressDialog;
	private String jiGouCode;//机构号
	private String jigoumingchengACache;
	private String isConnection;
	private String mConfirm;
	private UserZhuChe user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_login);
		mLoginState = LoginState.getInstance(LoginActivity.this);
		getAcache();
		isConnection = getResources().getString(R.string.no_connection);
		mConfirm = getResources().getString(R.string.confirm);
		checkVersionAndServerIsCanUse();
		initLogin();
		findID();
		initUI();
	}

	private void initLogin() {
		dao = new SessionAdapter(self);
	}

	private void getAcache() {
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		pkg = intent.getStringExtra("pkg");
		className = intent.getStringExtra("className");
		ACache aCache = ACache.get(LoginActivity.this);
		jigoumingchengACache = aCache.getAsString(Constant.AcacheKey.JIGOUMINGCHRNG);
		jigoumimaACache = aCache.getAsString(Constant.AcacheKey.JIGOUMIMA);
		//两天后机构密码被清除
		if(jigoumimaACache==null){
			jigoumimaACache="";
		}
		paidanyuanACache = aCache.getAsString(Constant.AcacheKey.PAIDANYUAN);
	}

	private void initUI() {
		if(jigoumingchengACache!=null){
			edit_jigouhao.setText(jigoumingchengACache);
		}
		if(jigoumimaACache!=null){
			edit_jigoumima.setText(jigoumimaACache);
		}
		if(paidanyuanACache!=null){
			edit_paidanyuan.setText(paidanyuanACache);
		}
	}

	private void findID() {
		edit_jigouhao = (EditText) findViewById(R.id.edit_jigouhao);
		edit_jigoumima = (EditText) findViewById(R.id.edit_jigoumima);
		edit_paidanyuan = (EditText) findViewById(R.id.edit_paidanyuan);
		ck_remember_account = (AnimCheckBox) findViewById(R.id.ck_remember_account);
		setListener();
	}

	private void setListener() {
		findViewById(R.id.button_login).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_login:
			unitOfAccount = edit_jigouhao.getText().toString().trim();
			unitOfpassword = edit_jigoumima.getText().toString().trim();
			paidanyuan = edit_paidanyuan.getText().toString().trim();
			if(unitOfAccount.endsWith("")&&"".equals(unitOfpassword)){
				toast("请输入机构名称和机构密码");
			}else{
				if(IsMobileNOorPassword.isMobileNO(paidanyuan)){
					verifyTheUnitOfAccount();
				}else{
					toast("请输入正确的手机号");
				}
			}
			break;
		default:
			break;
		}
	}
	
	// 验证企业账号信息
    private void verifyTheUnitOfAccount()
    {
    	showLoadingDialog("验证机构号……");
        Map<String, String> params = new HashMap<String, String>();
        params.put("loginName", unitOfAccount);
        params.put("password", unitOfpassword);
        ApiClient.verifyTheUnitOfAccount(LoginActivity.this, params, new VolleyListener()
        {

			@Override
            public void onResponse(String s)
            {
            	Log.e("jigou", "jigou"+s);
            	JiGouInfo jiGouInfo = GsonUtils.parseJSON(s, JiGouInfo.class);
                if (jiGouInfo.isSuccess())
                {
                	jiGouCode = jiGouInfo.getInstitutions().getCode();
                	loginAiton();
                } else
                {
                	toast("验证失败");
                	myProgressDialog.dismiss();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            	myProgressDialog.dismiss();
            	toast("网络请求失败");
            }
        });
    }
    
    protected void loginAiton() {
    	//每次存储唯一标识
        final String DeviceId = Installation.id(LoginActivity.this);
        //向后台服务推送用户短信验证成功，发送手机号----start----//
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", paidanyuan);
        params.put("login_id", DeviceId);
        ApiClient.smsLogingSuccess(LoginActivity.this, params, new VolleyListener()
        {

			public void onErrorResponse(VolleyError volleyError)
            {
            }

            public void onResponse(String s)
            {
                Log.e("SmsLoginActivity", "onResponse: --向后台发送登陆信息->>" + s);

                user = GsonUtils.parseJSON(s, UserZhuChe.class);
                if (user.isSuccess())
                {
                    //用户账号及密码验证完成后，在本地保存登录状态
                    mLoginState.login(LoginActivity.this);
                    Log.e("SmsLoginActivity", "onResponse: --短信快速登录时短信验证成功后的保存到本地登陆状态->>" + mLoginState.isLogin());
                    //在本地保存用户的手机号、用户id、设备号
                    UserLoginInfo userLoginInfo = new UserLoginInfo(paidanyuan, "" + user.getContains().getId(), DeviceId, user.getContains().getImage(),
                    		user.getContains().getIdCardImage(), user.getContains().getIdCardImage_back(),
                    		user.getContains().getDrivingLicenseImage(), user.getContains().getDrivingLicenseImage_back());
                    mLoginState.setLoginInfo(userLoginInfo);
                	loginDaChe();
                } else
                {
                	myProgressDialog.dismiss();
                    toast(user.getMessage());
                }
            }
        });
	}

	protected void loginDaChe() {
		new LoadNickName().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paidanyuan);
	}

	private class LoadNickName extends AsyncTask<String, Integer, Boolean> {
		private boolean iserror = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			AppLog.LogD("LoadNickName onPreExecute");
		}

		@Override
		protected Boolean doInBackground(String... params) {
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
							saveLocal(params[0], nickName, sex);
							return true;
						}
					} else {
						retJson.getString("errormsg");
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
			super.onPostExecute(result);
			
			registerSuccess();
			myProgressDialog.dismiss();
	        if (ck_remember_account.isChecked())
	        {
	        	removeUnitAccontID();
	            saveUnitAccontID(jiGouCode,unitOfpassword,paidanyuan);
	        } else
	        {
	            removeUnitAccontID();
	        }
	      //友盟统计
	        MobclickAgent.onProfileSignIn(paidanyuan);
			if (result) {
		        Intent intent = new Intent();
		        intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent );
				finish();
				if (iserror) {
					// Toast.makeText(self, errorMsg, Toast.LENGTH_SHORT).show();
					ToastUtil.show(self, "登录/注册失败");
					return;
				}
//				startActivity(new Intent(self, NewMainActivityNew.class));
			} else {
				Intent i = new Intent(LoginActivity.this, AddUserInfoActivity.class);
				i.putExtra("mobile", paidanyuan);
				LoginActivity.this.startActivity(i);
				finish();
			}
		}

	}
	
	private void registerSuccess() {

		LoginActivity.this.sendBroadcast(new Intent(ACTION_REGISTER));

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
		// finish();
		// 开启订单闹钟提醒服务
		Intent alarmIntent = new Intent(self, AlarmClockBookService.class);
		startService(alarmIntent);
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
	
	private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    protected void removeUnitAccontID() {
		ACache aCache = ACache.get(LoginActivity.this);
		aCache.clear();
	}

	protected void saveUnitAccontID(String jiGouCode, String unitOfpassword,String paidanyuan) {
		ACache aCache = ACache.get(LoginActivity.this);
        aCache.put(Constant.AcacheKey.USER_ZHUCHE, user);
		aCache.put(Constant.AcacheKey.JIGOUMINGCHRNG,unitOfAccount);
		aCache.put(Constant.AcacheKey.JIGOUHAO, jiGouCode);
		aCache.put(Constant.AcacheKey.JIGOUMIMA, unitOfpassword,ACache.TIME_DAY*2);
		aCache.put(Constant.AcacheKey.PAIDANYUAN, paidanyuan);
	}

	private void checkVersionAndServerIsCanUse()
    {
		final MyCarDialog myCarDialog = new MyCarDialog(LoginActivity.this);
		myCarDialog.show();
		myCarDialog.setCancelable(false);
        ApiClient.getServerIsLive(LoginActivity.this, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            	myCarDialog.dismiss();
                if (isConnection.equals(volleyError.getMessage()))
                {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.not_connect_net), Toast.LENGTH_SHORT).show();
                } else
                {
                    setDialogCheck(getResources().getString(R.string.server_is_upgrading), mConfirm);
                }
            }

            @Override
            public void onResponse(String s)
            {
            	myCarDialog.dismiss();
                VersionAndServerIsCanUse versionAndHouTaiIsCanUse = GsonUtils.parseJSON(s, VersionAndServerIsCanUse.class);
                int ableVersion = versionAndHouTaiIsCanUse.getAbleVersion();
                if (versionAndHouTaiIsCanUse.isLive())
                {
                    if (Constant.ABLEVERSION < ableVersion)
                    {
                        setDialogCheck(getResources().getString(R.string.able_version_unused), mConfirm);
                    } else
                    {
                        checkUpGrade();
                        //检查是否在其他设备上登录
//                        checkIsLoginOnOtherDevice();
                    }
                } else
                {
                    setDialogCheck(versionAndHouTaiIsCanUse.getMessage(), mConfirm);
                }
            }
        });
    }
	
	private void checkUpGrade()
    {
        UpgradeUtils.checkUpgrade(LoginActivity.this, Constant.URL.UP_GRADE);
    }
	
	 //dialog提示
    private void setDialogCheck(String messageTxt, String iSeeTxt)
    {
    	final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(LoginActivity.this, messageTxt, iSeeTxt);
    	singleBtnDialog.show();
    	singleBtnDialog.setCancelable(false);
    	singleBtnDialog.setClicklistener(new ClickListenerInterface() {
			
			@Override
			public void doWhat() {
				singleBtnDialog.dismiss();
				finish();
			}
		});
    }
	
	public void showLoadingDialog(String text) {
		myProgressDialog = new MyCarDialog(LoginActivity.this);
		myProgressDialog.show();
		myProgressDialog.setCancelable(false);
		myProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    	myProgressDialog.dismiss();
                        finish();
                    }
                    return false;
                }
            });
    }
	
	@Override
	protected void onDestroy() {
		if (dao != null) {
			dao.close();
			dao = null;
		}
		super.onDestroy();
	}
}
