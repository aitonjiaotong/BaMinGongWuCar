package passenger.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import passenger.model.UserZhuChe;
import passenger.model.UserLoginInfo;
import passenger.utils.ApiClient;
import passenger.utils.Installation;
import passenger.utils.IsMobileNOorPassword;
import passenger.utils.LoginState;
import passenger.utils.SmsContent;
import passenger.utils.SubJsonStr;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.VolleyListener;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;


public class SmsLoginActivity extends Activity implements View.OnClickListener
{

    private ImageView mPhone_num_cancle;
    private ImageView mSms_cancle;
    private EditText mPhone_num;
    private EditText mSms;
    private Button mSendSms;
    private Button mLogin;
    private Runnable mR;
    private int[] mI;
    private boolean isSend = false;
    private String mPhoneNum;
    private UserZhuChe mUser;
    private TextView mTextView_quickLogin;
    private TextView mTextView_accountLogin;
    private ImageView mImageView_tiao01;
    private int basicColor;
    private int offset;
    private int mSystem_gray;
    private LinearLayout mLinear_quickLogin;
    private LinearLayout mLinear_accountLogin;
    private TextView mTextView_register;
    private EditText mEditText_phoneNum;
    private EditText mEditText_password;
    private boolean isQuickLogin = true;
    private String mSuijiMath;
    private LoginState mLoginState;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        mLoginState = LoginState.getInstance(SmsLoginActivity.this);
        findID();
        initUI();
        setListener();
        initAnim();
    }

    private void initUI()
    {
        basicColor = getResources().getColor(R.color.title_bar);
        mSystem_gray = getResources().getColor(R.color.black);
        // 注册短信变化监听
//        SmsContent smsContent = new SmsContent(SmsLoginActivity.this, new Handler(), mSms);
//        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContent);

    }

    private void toast(final String str)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(SmsLoginActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void findID()
    {
        mPhone_num_cancle = (ImageView) findViewById(R.id.phone_num_cancle);
        mSms_cancle = (ImageView) findViewById(R.id.sms_cancle);
        mPhone_num = (EditText) findViewById(R.id.phone_num);
        mSms = (EditText) findViewById(R.id.sms);
        mSendSms = (Button) findViewById(R.id.sendSms);
        mLogin = (Button) findViewById(R.id.login);
        mTextView_quickLogin = (TextView) findViewById(R.id.textView_quickLogin);
        mTextView_accountLogin = (TextView) findViewById(R.id.textView_accountLogin);
        mImageView_tiao01 = (ImageView) findViewById(R.id.imageView_tiao01);
        mLinear_quickLogin = (LinearLayout) findViewById(R.id.linear_quickLogin);
        mLinear_accountLogin = (LinearLayout) findViewById(R.id.linear_accountLogin);
        mTextView_register = (TextView) findViewById(R.id.textView_register);
        mEditText_phoneNum = (EditText) findViewById(R.id.editText_phoneNum);
        mEditText_password = (EditText) findViewById(R.id.editText_password);
    }

    private void setListener()
    {
        mLogin.setOnClickListener(this);
        findViewById(R.id.iv_org_detailback).setOnClickListener(this);
        mPhone_num.addTextChangedListener(new MyPhoneNumTextWatcher());
        mSms.addTextChangedListener(new MySmsTextWatcher());
        mPhone_num_cancle.setOnClickListener(this);
        mSms_cancle.setOnClickListener(this);
        mSendSms.setOnClickListener(this);
        mTextView_quickLogin.setOnClickListener(this);
        mTextView_accountLogin.setOnClickListener(this);
        mTextView_register.setOnClickListener(this);
        findViewById(R.id.textView_frogetPassword).setOnClickListener(this);
    }

    private void initAnim()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = screenW / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        mImageView_tiao01.setImageMatrix(matrix);// 设置动画初始位置
    }

    class MyPhoneNumTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (s.length() > 0)
            {
                mPhone_num_cancle.setVisibility(View.VISIBLE);

            } else
            {
                mPhone_num_cancle.setVisibility(View.INVISIBLE);
            }
            if (s.length() == 11)
            {
                mSms.requestFocus();
                if (!isSend)
                {
                    mSendSms.setEnabled(true);
                }
            } else
            {
                mSendSms.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    class MySmsTextWatcher implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (s.length() > 0)
            {
                mSms_cancle.setVisibility(View.VISIBLE);
            } else
            {
                mSms_cancle.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.textView_frogetPassword:
                intent.setClass(SmsLoginActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.textView_register:
                intent.setClass(SmsLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.textView_quickLogin:
                isQuickLogin = true;
                mTextView_quickLogin.setTextColor(basicColor);
                mTextView_accountLogin.setTextColor(mSystem_gray);
                Animation animation = new TranslateAnimation(offset, 0, 0, 0);
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                mImageView_tiao01.startAnimation(animation);
                mLogin.setVisibility(View.GONE);
                mLinear_quickLogin.setVisibility(View.VISIBLE);
                mLinear_accountLogin.setVisibility(View.INVISIBLE);
                break;
            case R.id.textView_accountLogin:
                isQuickLogin = false;
                mTextView_accountLogin.setTextColor(basicColor);
                mTextView_quickLogin.setTextColor(mSystem_gray);
                Animation animation02 = new TranslateAnimation(0, offset, 0, 0);
                animation02.setFillAfter(true);// True:图片停在动画结束位置
                animation02.setDuration(300);
                mImageView_tiao01.startAnimation(animation02);
                mLogin.setVisibility(View.VISIBLE);
                mLinear_quickLogin.setVisibility(View.INVISIBLE);
                mLinear_accountLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.login:
                if (isQuickLogin)
                {
                    if (mSuijiMath.equals(mSms.getText().toString().trim()))
                    {
                        //每次存储唯一标识
                        final String DeviceId = Installation.id(SmsLoginActivity.this);
                        //向后台服务推送用户短信验证成功，发送手机号----start----//
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", mPhoneNum);
                        params.put("login_id", DeviceId);
                        ApiClient.smsLogingSuccess(SmsLoginActivity.this, params, new VolleyListener()
                        {
                            public void onErrorResponse(VolleyError volleyError)
                            {
                            }

                            public void onResponse(String s)
                            {
                                Log.e("SmsLoginActivity", "onResponse: --向后台发送登陆信息->>" + s);

                                mUser = GsonUtils.parseJSON(s, UserZhuChe.class);
                                if (mUser.isSuccess())
                                {
                                    Log.e("SmsLoginActivity", "onResponse: --短信快速登录时短信验证成功后的登陆状态->>" + mLoginState.isLogin());
                                    toast("短信验证成功");
                                    //用户账号及密码验证完成后，在本地保存登录状态
                                    mLoginState.login(SmsLoginActivity.this);
                                    Log.e("SmsLoginActivity", "onResponse: --短信快速登录时短信验证成功后的保存到本地登陆状态->>" + mLoginState.isLogin());
                                    //在本地保存用户的手机号、用户id、设备号
                                    UserLoginInfo userLoginInfo = new UserLoginInfo(mPhoneNum, "" + mUser.getContains().getId(), DeviceId, mUser.getContains().getImage(),
                                            mUser.getContains().getIdCardImage(), mUser.getContains().getIdCardImage_back(),
                                            mUser.getContains().getDrivingLicenseImage(), mUser.getContains().getDrivingLicenseImage_back());
                                    mLoginState.setLoginInfo(userLoginInfo);
                                    Log.e("SmsLoginActivity", "onResponse: --登陆信息->>" + mLoginState.getLoginInfo().toString());
                                    //友盟统计
                                    MobclickAgent.onProfileSignIn(mPhoneNum);
                                    finish();
                                } else
                                {
                                    toast(mUser.getMessage());
                                }
                            }
                        });
                        //向后台服务推送用户短信验证成功，发送手机号----end----//
                    } else
                    {
                        toast("短信验证失败");
                    }
                } else
                {
                    mPhoneNum = mEditText_phoneNum.getText().toString().trim();
                    String password = mEditText_password.getText().toString().trim();
                    //每次存储唯一标识
                    final String DeviceId = Installation.id(SmsLoginActivity.this);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("phone", mPhoneNum);
                    map.put("login_id", DeviceId);
                    map.put("password", password);
                    ApiClient.passWordLoginSuccess(SmsLoginActivity.this,  map, new VolleyListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError volleyError)
                        {

                        }

                        @Override
                        public void onResponse(String s)
                        {
                            mUser = GsonUtils.parseJSON(s, UserZhuChe.class);
                            if (mUser.isSuccess())
                            {
                                Log.e("SmsLoginActivity", "onResponse: --密码登录时成功后的登陆状态->>" + mLoginState.isLogin());
                                //用户账号及密码验证完成后，在本地保存登录状态
                                mLoginState.login(SmsLoginActivity.this);
                                Log.e("SmsLoginActivity", "onResponse: --密码登录时成功后保存到本地的登陆状态->>" + mLoginState.isLogin());
                                //在本地保存用户的手机号、用户id、设备号
                                UserLoginInfo userLoginInfo = new UserLoginInfo(mPhoneNum, "" + mUser.getContains().getId(), DeviceId,
                                        mUser.getContains().getImage(),
                                        mUser.getContains().getIdCardImage(), mUser.getContains().getIdCardImage_back(),
                                        mUser.getContains().getDrivingLicenseImage(), mUser.getContains().getDrivingLicenseImage_back());
                                mLoginState.setLoginInfo(userLoginInfo);
                                toast("登录成功");
                                //友盟统计
                                MobclickAgent.onProfileSignIn(mPhoneNum);
                                finish();
//                                Intent intent = new Intent();
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.setClass(SmsLoginActivity.this, MainActivity.class);
//                                startActivity(intent);
                            } else
                            {
                                toast("用户名或密码错误");
                            }

                        }
                    });
                }
                break;
            case R.id.iv_org_detailback:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.phone_num_cancle:
                mPhone_num.setText("");
                break;
            case R.id.sms_cancle:
                mSms.setText("");
                break;
            case R.id.sendSms:
//                if (ContextCompat.checkSelfPermission(SmsLoginActivity.this, Manifest.permission.READ_SMS)
//                        != PackageManager.PERMISSION_GRANTED)
//                {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(SmsLoginActivity.this, new String[]{Manifest.permission.READ_SMS}, Constant.PermissionRequestAndResultCode.PERMISSION_READ_SMS);
//                } else
//                {
                    sendSMS();
//                }

                break;
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == Constant.PermissionRequestAndResultCode.PERMISSION_READ_SMS)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                sendSMS();
//            }
//        }
//    }

    private void sendSMS()
    {
        mPhoneNum = mPhone_num.getText().toString().trim();
        boolean mobileNO = IsMobileNOorPassword.isMobileNO(mPhoneNum);
        if (mobileNO)
        {
            mSendSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable()
            {
                @Override
                public void run()
                {
                    mSendSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0)
                    {
                        mSendSms.setEnabled(true);
                        mSendSms.setText("获取验证码");
                        isSend = false;
                        return;
                    } else
                    {
                        isSend = true;
                    }
                    mSendSms.postDelayed(mR, 1000);
                }
            };
            mSendSms.postDelayed(mR, 0);
            mLogin.setVisibility(View.VISIBLE);
            getSms();
        } else
        {
            Toast.makeText(SmsLoginActivity.this, "输入的手机格式有误", Toast.LENGTH_SHORT).show();
            mPhone_num.setText("");
        }
    }

    private void getSms()
    {
        mSuijiMath = (int) (Math.random() * 9000 + 1000) + "";
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", mPhoneNum);
        map.put("code", mSuijiMath + "");
        ApiClient.getSMS(SmsLoginActivity.this, map, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                String dislodgeHeadTag = SubJsonStr.dislodgeHeadTag(s);
                String dislodgeHeadTag1 = SubJsonStr.dislodgeHeadTag(dislodgeHeadTag);
                if ("03".equals(dislodgeHeadTag1) || "0".equals(dislodgeHeadTag1))
                {
                    toast("获取验证码成功");
                } else if ("02".equals(dislodgeHeadTag1))
                {
                    toast("IP限制");
                } else if ("04".equals(dislodgeHeadTag1))
                {
                    toast("用户名错误");
                } else if ("05".equals(dislodgeHeadTag1))
                {
                    toast("密码错误");
                } else if ("07".equals(dislodgeHeadTag1))
                {
                    toast("发送时间错误");
                } else if ("08".equals(dislodgeHeadTag1))
                {
                    toast("信息内容为黑内容");
                } else if ("09".equals(dislodgeHeadTag1))
                {
                    toast("该用户的该内容 受同天内，内容不能重复发 限制");
                } else if ("10".equals(dislodgeHeadTag1))
                {
                    toast("扩展号错误");
                } else if ("97".equals(dislodgeHeadTag1))
                {
                    toast("短信参数有误");
                } else if ("11".equals(dislodgeHeadTag1))
                {
                    toast("余额不足");
                } else if ("-1".equals(dislodgeHeadTag1))
                {
                    toast("程序异常");
                }
            }
        });
    }


    private void AnimFromRightToLeft()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume()
    {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
