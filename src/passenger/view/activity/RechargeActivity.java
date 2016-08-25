package passenger.view.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.aiton.yc.client.R;
import com.aiton.yc.client.R.layout;
import com.aiton.yc.client.wxapi.BaseHelper;
import com.aiton.yc.client.wxapi.PayActivity;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.PayDemo;
import com.alipay.sdk.pay.demo.PayResult;
import com.android.volley.VolleyError;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.client.common.ConfigUtil;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.ui.MyHttpUtils;
import cn.com.easytaxi.ui.SimpleCallBack;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;
import net.sourceforge.simcpux.Constants;
import android.widget.Toast;
import passenger.constant.Constant;
import passenger.model.Sign;
import passenger.view.customview.SingleBtnDialog;
import passenger.view.customview.SingleBtnDialog.ClickListenerInterface;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.HTTPUtils;
import shane_library.shane.utils.VolleyListener;

public class RechargeActivity extends YdLocaionBaseActivity implements OnClickListener {

	private RadioButton radioButton100;
	private RadioButton radioButton200;
	private RadioButton radioButton500;
	private RadioButton radio1000;
	private RadioButton radio2000;
	private RadioButton radio_other;
	private RadioGroup radioGroup01;
	private RadioGroup radioGroup02;
	private RadioGroup pay_mode;
	private RadioButton radioButton_weixin;
	private RadioButton radioButton_zhifubao;
	private RadioButton radioButton_yinlian;
	private String payMode = "微信";// 支付方式
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private String serial;// 支付宝返回的流水号
	public static final int PAY_TYPE_WEIXIN = 5;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	// private long mBookId;
	private ProgressDialog mProgress;
	public static final String url = "http://" + ConfigUtil.getString("PAY_IPPORT") + "/YdRecharge/do";
	PayReq req;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
//		mBookId = this.getIntent().getLongExtra("bookId", 0l);
		req = new PayReq();
		findID();
		setListener();
	}

	private void findID() {
		radioButton100 = (RadioButton) findViewById(R.id.RadioButton100);
		radioButton200 = (RadioButton) findViewById(R.id.RadioButton200);
		radioButton500 = (RadioButton) findViewById(R.id.RadioButton500);
		radio1000 = (RadioButton) findViewById(R.id.radio1000);
		radio2000 = (RadioButton) findViewById(R.id.radio2000);
		radio_other = (RadioButton) findViewById(R.id.radio_other);
		radioGroup01 = (RadioGroup) findViewById(R.id.RadioGroup01);
		radioGroup02 = (RadioGroup) findViewById(R.id.RadioGroup02);
		pay_mode = (RadioGroup) findViewById(R.id.pay_mode);
		radioButton_weixin = (RadioButton) findViewById(R.id.radioButton_weixin);
		radioButton_zhifubao = (RadioButton) findViewById(R.id.radioButton_zhifubao);
		radioButton_yinlian = (RadioButton) findViewById(R.id.radioButton_yinlian);
	}

	private void setListener() {
		findViewById(R.id.imageView_back).setOnClickListener(this);
		radioGroup01.setOnCheckedChangeListener(new CheckListener01());
		radioGroup02.setOnCheckedChangeListener(new CheckListener02());
		radio_other.setOnClickListener(this);
		pay_mode.setOnCheckedChangeListener(new CheckListener03());
		findViewById(R.id.pay).setOnClickListener(this);
	}

	class CheckListener01 implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (radioButton100.isChecked()) {
				radioGroup02.clearCheck();
				Toast.makeText(RechargeActivity.this, "100", Toast.LENGTH_SHORT).show();
			}
			if (radioButton200.isChecked()) {
				radioGroup02.clearCheck();
				Toast.makeText(RechargeActivity.this, "200", Toast.LENGTH_SHORT).show();
			}
			if (radioButton500.isChecked()) {
				radioGroup02.clearCheck();
				Toast.makeText(RechargeActivity.this, "500", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class CheckListener02 implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (radio1000.isChecked()) {
				radioGroup01.clearCheck();
				Toast.makeText(RechargeActivity.this, "1000", Toast.LENGTH_SHORT).show();
			}
			if (radio2000.isChecked()) {
				radioGroup01.clearCheck();
				Toast.makeText(RechargeActivity.this, "2000", Toast.LENGTH_SHORT).show();
			}
			if (radio_other.isChecked()) {
				radioGroup01.clearCheck();
			}
		}
	}

	class CheckListener03 implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (radioButton_zhifubao.isChecked()) {
				Log.e("onCheckedChanged", "支付宝");
				payMode = "支付宝";
			} else if (radioButton_weixin.isChecked()) {
				payMode = "微信";
			} else if (radioButton_yinlian.isChecked()) {
				payMode = "银联";
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.pay:
			if ("支付宝".equals(payMode)) {
				getSign();
			} else if ("微信".equals(payMode)) {
				wechatPay();
			} else if ("银联".equals(payMode)) {
				// intent.setClass(RechargeActivity.this,
				// YinLianWebActivity.class);
				// intent.putExtra("BookLogAID", mBookLogAID);
				// intent.putExtra(Constant.IntentKey.PAY_ORDERID, mOrderId);
				// intent.putExtra(Constant.IntentKey.PAY_PRICE, (int)
				// (realPayPrice * 100) + "");
				// startActivity(intent);
			}
			break;
		case R.id.radio_other:
			View reaharge_value = getLayoutInflater().inflate(R.layout.reaharge_value, null);
			final Dialog reaharge_value_dialog = new Dialog(RechargeActivity.this, R.style.MineDialog);
			LayoutParams layPar = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			reaharge_value_dialog.setContentView(reaharge_value, layPar);
			reaharge_value_dialog.show();
			final EditText editText_value = (EditText) reaharge_value.findViewById(R.id.editText_value);
			reaharge_value.findViewById(R.id.button_comfrim).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String value = editText_value.getText().toString().trim();
					if ("".equals(value)) {
						Toast.makeText(RechargeActivity.this, "金额不能为空", Toast.LENGTH_SHORT).show();
					} else {
						radio_other.setText(value);
						reaharge_value_dialog.dismiss();
					}
				}
			});
			Window dialogWindow = reaharge_value_dialog.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			DisplayMetrics d = RechargeActivity.this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
			lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
			dialogWindow.setAttributes(lp);
			reaharge_value_dialog.show();
			Toast.makeText(RechargeActivity.this, "其他", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_back:
			finish();
			break;

		default:
			break;
		}
	}

	private void wechatPay() {
		price = 1;
		if (price <= 0) {
			ToastUtil.show(RechargeActivity.this, "价格不能小于1分");
			return;
		} else {
			requestPre(PAY_TYPE_WEIXIN);
		}
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
			jsonObject.put("bookId", "chongzhi");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mProgress = BaseHelper.showProgress(RechargeActivity.this, null, "正在生成订单", false, true);
		MyHttpUtils.getInstance().send(url, jsonObject.toString(), new SimpleCallBack(RechargeActivity.this) {
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
					} 
				} catch (Exception e) {
					e.printStackTrace();
					closeProgress();
					ToastUtil.show(RechargeActivity.this, "生成订单失败");
				}

			};

			@Override
			public void onFinish() {
				super.onFinish();
				closeProgress();
				ToastUtil.show(RechargeActivity.this, "生成订单失败");
			}
		});

	}
	
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

	private boolean isWXAppInstalledAndSupported(Context context, IWXAPI api) {
		// LogOutput.d(TAG, "isWXAppInstalledAndSupported");
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled() && api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {
			// Log.w(TAG, "~~~~~~~~~~~~~~微信客户端未安装，请确认");
			MyDialog.comfirm(context, "温馨提示", "您还没有安装微信哦", new SureCallback(), true, false, true);
		}
		return sIsWXAppInstalledAndSupported;
	}

	private void getSign() {
		String url = Constant.URL.GETSIGN;
		Map<String, String> map = new HashMap<String, String>();
		// map.put("out_trade_no", getOutTradeNo());
		// map.put("subject", mQueryOrder.getStartSiteName() + "-" +
		// mQueryOrder.getEndSiteName());
		// map.put("total_fee", realPayPrice + "");
		// map.put("id", mOrderId);
		// if (mRedBag != null) {
		// map.put("redEnvelope_id", mRedBag.getId() + "");
		// }
		// map.put("real_pay", realPayPrice + "");
		HTTPUtils.post(RechargeActivity.this, url, map, new VolleyListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}

			@Override
			public void onResponse(String s) {
				Sign sign = GsonUtils.parseJSON(s, Sign.class);
				final String body = sign.getBody();
				Log.e("onResponse", "返回值" + body);
				Runnable payRunnable = new Runnable() {

					@Override
					public void run() {
						// 构造PayTask 对象
						PayTask alipay = new PayTask(RechargeActivity.this);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(body);

						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				};

				// 必须异步调用
				Thread payThread = new Thread(payRunnable);
				payThread.start();
			}
		});
	}

	/**
	 * 支付宝支付动作完成后的回调
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息\
				String[] split = resultInfo.split("&");
				String[] split1 = split[2].split("\"");
				serial = split1[1];
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					// 支付成功向金点通发送确认订单
					// queryOrderState();
					// setDialog01("支付成功", "确认");
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						setFailDialog("支付结果确认中", "确认");
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						// cancleOrder();
						setFailDialog("支付失败", "确认");
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(RechargeActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		}
	};
	private int price;

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

	// 支付失败dialog提示
	private void setFailDialog(String messageTxt, String iSeeTxt) {
		final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(RechargeActivity.this, iSeeTxt, messageTxt);
		singleBtnDialog.show();
		singleBtnDialog.setClicklistener(new ClickListenerInterface() {

			@Override
			public void doWhat() {
				singleBtnDialog.dismiss();
			}
		});
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initUserData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void regReceiver() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unRegReceiver() {
		// TODO Auto-generated method stub

	}
}
