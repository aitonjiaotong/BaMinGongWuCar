package com.aiton.yc.client.wxapi;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sourceforge.simcpux.Constants;
import net.sourceforge.simcpux.MD5;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.book.NewBookDetail;
import cn.com.easytaxi.client.common.ConfigUtil;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.MyWebViewActivity;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.receiver.LocationBroadcastReceiver;
import cn.com.easytaxi.ui.MyHttpUtils;
import cn.com.easytaxi.ui.SimpleCallBack;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.XTCPUtil;

import com.alipay.sdk.pay.demo.PayDemo;

import com.aiton.yc.client.R;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PayActivity extends YdLocaionBaseActivity implements OnClickListener {
	public static final String PAY_RECEIVER = "com.eteasun.etpassengersx.book.pay";
	// http://120.24.231.9:80/EasytaxiMonitor/rule_Rule!getRule?cityName=
	/**
	 * 计费规则连接
	 */
	public static final String PRICE_RULE = "http://" + ConfigUtil.getString("PAY_IPPORT")
			+ "/EasytaxiMonitor/rule_Rule!getRule?cityName=";
	public static final int PAY_TYPE_WEIXIN = 5;
	public static final int PAY_TYPE_ZHIFUBAO = 1;
	public static final int PAY_TYPE_XIANJIN = 7;

	// public static final String url = "https://" +
	// ConfigUtil.getString("PAY_IPPORT") + ":8444/YdRecharge/do";
	public static final String url = "http://" + ConfigUtil.getString("PAY_IPPORT") + "/YdRecharge/do";
	private ProgressDialog mProgress;
	private long mBookId;
	private int price;
	String zhifu;// 支付方式区分
	PayReq req;
	private TextView jifei_guize;// 金额
	// private TextView jine;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	Map<String, String> resultunifiedorder;
	StringBuffer sb;

	private EditText jine2;// 现金支付0428

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meidipay);
		TitleBar bar = new TitleBar(this);
		bar.setTitleName("选择支付方式");
		// jine = (TextView) findViewById(R.id.money_number);
		jine2 = (EditText) findViewById(R.id.money_number2);
		jifei_guize = (TextView) findViewById(R.id.jifei_guize);
		jifei_guize.setText(Html.fromHtml("<u>计费规则查看</u>"));
		mBookId = this.getIntent().getLongExtra("bookId", 0l);
		price = getIntent().getIntExtra("price", 0);
		zhifu = getIntent().getStringExtra("zhifu");

		if (price <= 0) {
			jine2.setEnabled(true);
			zhifu = "2";
		} else {
			zhifu = "1";
			jine2.setEnabled(false);
			jine2.setText(getPriceInYuan(price) + "");
		}
		SysDeug.logD("bookid -> " + mBookId + " price -> " + price);
		findViewById(R.id.pay_wx).setOnClickListener(this);
		findViewById(R.id.pay_zfb).setOnClickListener(this);
		findViewById(R.id.pay_xj).setOnClickListener(this);
		jifei_guize.setOnClickListener(this);
		req = new PayReq();
		sb = new StringBuffer();

		msgApi.registerApp(Constants.APP_ID);
		registerReceiver();
		// 生成签名参数

		jine2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
						jine2.setText(s);
						jine2.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					jine2.setText(s);
					jine2.setSelection(2);
				}

				if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						jine2.setText(s.subSequence(0, 1));
						jine2.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private float getPriceInYuan(int price) {
		return price / 100f;
	}

	PayResultReceiver receiver = null;

	private void registerReceiver() {
		if (receiver == null)
			receiver = new PayResultReceiver();
		registerReceiver(receiver, new IntentFilter(PAY_RECEIVER));
	}

	@Override
	protected void onDestroy() {
		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
		super.onDestroy();
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private void genPayReq(String prepayId) {

		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = prepayId;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

		Log.e("orion", signParams.toString());

	}

	private boolean isWXAppInstalledAndSupported(Context context, IWXAPI api) {
		// LogOutput.d(TAG, "isWXAppInstalledAndSupported");
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled() && api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {
			// Log.w(TAG, "~~~~~~~~~~~~~~微信客户端未安装，请确认");
			MyDialog.comfirm(context, "温馨提示", "您还没有安装微信哦", new SureCallback(), true, false, true);
		}
		return sIsWXAppInstalledAndSupported;
	}

	// private void sendPayReq() {
	//
	// msgApi.registerApp(Constants.APP_ID);
	// msgApi.sendReq(req);
	// }

	private void sendPayReq(String appid, String partnerId, String prepayId, String nonceStr, String timeStamp,
			String packageValue, String sign) {
		req.appId = appid;
		req.partnerId = partnerId;
		req.prepayId = prepayId;
		req.nonceStr = nonceStr;
		req.timeStamp = timeStamp;
		req.packageValue = packageValue;
		req.sign = sign;
		req.extData = "app data";

		AppLog.LogD("xxb", "PayReq-->" + req.toString());
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
	}

	/**
	 * 调用预支付=微信支付
	 */
	private void requestPre(final int type) {
		if (type == PAY_TYPE_WEIXIN && !isWXAppInstalledAndSupported(this, msgApi)) {
			return;
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("action", "rechargeAction");
			jsonObject.put("method", "rechargeSubmit");
			jsonObject.put("platform", "chexingchuxing");

			jsonObject.put("userId", String.valueOf(getPassengerId()));
			jsonObject.put("money", price);
			// jsonObject.put("money", 1);
			jsonObject.put("payModeId", type);
			jsonObject.put("sn", "");
			jsonObject.put("password", "");
			jsonObject.put("bookId", mBookId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mProgress = BaseHelper.showProgress(PayActivity.this, null, "正在生成订单", false, true);
		MyHttpUtils.getInstance().send(url, jsonObject.toString(), new SimpleCallBack(PayActivity.this) {
			@Override
			public void onSuccess(com.xc.lib.xutils.http.ResponseInfo<String> responseInfo) {
				SysDeug.logD("content" + responseInfo.result);
				try {
					JSONObject result = new JSONObject(responseInfo.result);
					if (type == PAY_TYPE_WEIXIN) {// 微信支付
						JSONObject aliResult = new JSONObject(result.getString("datas"));
						String appid = aliResult.getString("appid");
						String partnerId = aliResult.getString("partnerid");
						String prepayId = aliResult.getString("prepayid");
						String nonceStr = aliResult.getString("noncestr");
						String timeStamp = aliResult.getString("timestamp");
						String packageValue = aliResult.getString("package");
						String sign = aliResult.getString("sign");
						sendPayReq(Constants.APP_ID, partnerId, prepayId, nonceStr, timeStamp, packageValue, sign);
					} else if (type == PAY_TYPE_ZHIFUBAO) {
						JSONObject aliResult = new JSONObject(result.getString("datas"));
						String requestStr = aliResult.getString("requestStr");
						PayDemo payDemo = new PayDemo(PayActivity.this);
						payDemo.pay(requestStr);
					} else if (type == PAY_TYPE_XIANJIN) {
						Intent intent = new Intent();
						intent.putExtra("error", 1);
						intent.putExtra("type", PayActivity.PAY_TYPE_XIANJIN);
						PayActivity.this.setResult(RESULT_OK, intent);
						PayActivity.this.finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
					closeProgress();
					ToastUtil.show(PayActivity.this, "生成订单失败");
				}

			};

			@Override
			public void onFinish() {
				super.onFinish();
				closeProgress();
				ToastUtil.show(PayActivity.this, "生成订单失败");
			}
		});

	}

	@Override
	public void onClick(View v) {
		final String money = jine2.getText().toString().trim();
		switch (v.getId()) {
		case R.id.pay_wx:
			// 微信支付
			if (money.equals("") || money == null) {
				ToastUtil.show(PayActivity.this, "请输入本次行程费用");
				return;
			} else {
				if (zhifu.equals("2") || zhifu == "2") {
					getxianjinzf(PAY_TYPE_WEIXIN);
				} else {
					price = (int) (Double.parseDouble(money) * 100);
					if (price <= 0) {
						ToastUtil.show(PayActivity.this, "价格不能小于1分");
						return;
					} else {
						requestPre(PAY_TYPE_WEIXIN);
					}
				}
			}
			break;
		case R.id.pay_zfb:
			// 支付宝支付
			if (money.equals("") || money == null) {
				ToastUtil.show(PayActivity.this, "请输入本次行程费用");
				return;
			} else {
				if (zhifu.equals("2") || zhifu == "2") {
					getxianjinzf(PAY_TYPE_ZHIFUBAO);
				} else {
					price = (int) (Double.parseDouble(money) * 100);
					if (price <= 0) {
						ToastUtil.show(PayActivity.this, "价格不能小于1分");
						return;
					} else {
						requestPre(PAY_TYPE_ZHIFUBAO);
					}
				}
			}
			break;
		case R.id.pay_xj:// 现金支付028
			if (money.equals("") || money == null) {
				ToastUtil.show(PayActivity.this, "请输入本次行程费用");
				return;
			} else {
				MyDialog.comfirm(PayActivity.this, "温馨提示", "您确定选择企业月结吗？", new SureCallback() {
					@Override
					public void onClick(int tag) {
						if (tag == LEFT) {

						} else {
							if (zhifu.equals("2") || zhifu == "2") {
								getxianjinzf(PAY_TYPE_XIANJIN);
							} else {
								price = (int) (Double.parseDouble(money) * 100);
								if (price <= 0) {
									ToastUtil.show(PayActivity.this, "价格不能小于1分");
									return;
								} else {
									requestPre(PAY_TYPE_XIANJIN);
								}
							}
						}
					}
				});

			}
			break;
		case R.id.jifei_guize:
			Intent intent = new Intent(this, MyWebViewActivity.class);
			intent.putExtra("title", "计费规则");
			intent.putExtra("url", PRICE_RULE + LocationBroadcastReceiver.getcity());
			startActivity(intent);
			break;
		}
	}

	// 关闭进度�?
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * the OnCancelListener for lephone platform. lephone系统使用到的取消dialog监听
	 */
	public static class AlixOnCancelListener implements DialogInterface.OnCancelListener {
		Activity mcontext;

		public AlixOnCancelListener(Activity context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}

	public class PayResultReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Intent intents = new Intent();
			intents.putExtra("error", intent.getIntExtra("error", -1));
			intents.putExtra("type", intent.getIntExtra("type", -1));
			PayActivity.this.setResult(RESULT_OK, intents);
			finish();
		}
	}

	@Override
	protected void initViews() {
	}

	@Override
	protected void initListeners() {
	}

	@Override
	protected void initUserData() {
	}

	@Override
	protected void regReceiver() {
	}

	@Override
	protected void unRegReceiver() {
	}

	private void getxianjinzf(final int zhifufangshi) {
		String money = jine2.getText().toString().trim();
		price = (int) (Double.parseDouble(money) * 100);
		if (price <= 0) {
			ToastUtil.show(PayActivity.this, "价格不能小于1分");
			return;
		}
		try {
			JSONObject param = new JSONObject();
			param.put("action", "receivedAction");
			param.put("method", "cashPaymentBypassenger");
			param.put("bookId", mBookId);
			param.put("amount", price);
			XTCPUtil.send(param, new XTCPUtil.XNetCallback() {
				@Override
				public void onSuc(String result) {
					try {
						JSONObject object = new JSONObject(result);
						int error = object.getInt("error");
						if (error == 0) {
							closeProgress();
							requestPre(zhifufangshi);
						} else {
							closeProgress();
							ToastUtil.show(PayActivity.this, object.getString("errormsg"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						closeProgress();
					}
				}

				@Override
				public void onStart() {

				}

				@Override
				public void onComplete() {

				}

				@Override
				public void error(Throwable e, int errorcode) {
					closeProgress();
				}
			});
		} catch (Exception e) {
			closeProgress();
			e.printStackTrace();
		}
	}
}
