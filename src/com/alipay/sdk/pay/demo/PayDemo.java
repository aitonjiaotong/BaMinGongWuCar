package com.alipay.sdk.pay.demo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.com.easytaxi.client.common.ConfigUtil;
import cn.com.easytaxi.ui.MyHttpUtils;
import cn.com.easytaxi.util.SysDeug;
import cn.com.easytaxi.util.ToastUtil;

import com.aiton.yc.client.wxapi.PayActivity;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.xc.lib.xutils.exception.HttpException;
import com.xc.lib.xutils.http.ResponseInfo;
import com.xc.lib.xutils.http.callback.RequestCallBack;
import com.xc.lib.xutils.http.client.HttpRequest.HttpMethod;

public class PayDemo {
	private PayActivity activity;
//	public static final String url = "http://" + ConfigUtil.getString("PAY_IPPORT") + "/YdRecharge/Sync/alipay/recharge/";
	public static final String url = "http://" + ConfigUtil.getString("PAY_IPPORT") + "/YdRecharge/Sync/alipay/recharge/";

	public PayDemo(PayActivity activity) {
		this.activity = activity;
	}

	// 商户PID
	public static final String PARTNER = "2088121765668893";
	// 商户收款账号
	public static final String SELLER = "fj_bmcx@163.com";
	// 商户私钥，pkcs8格式
//	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALTXuVpOCYsOxXBUxmXjlQ3z0e6RCyQNjOe2zLQcPW0obYR9c5VjvkvqxkfCUvB4lpAT7Wsh20pwYro/9dvq28t6D8PmPgnAQ4VH3oKy/tP85zzfMffXPMF52zJWYCR72BwHadLwpbSAB+UTlnuQK9Zs3vCAmPxuQUBItQbcYWONAgMBAAECgYBUFDAjIkEhm/6D2YZcfNEiKYgjSOBLJNmsKbtGyhJtG+y/Oqxwem1RUphZaKmzD/3AguNRee5Az7u3KGGgm7MQQUIlOBrgcDzaND+yNOy9TZdDxTztWSrf/YmFt9p3CpzpH3kg+Qps3RiR8B4nhTXhU8jd7poWs8FC/+GGPo5e6QJBAN548g1UDpDmDnD4Bu/Lsp3R+mS6qdGgIbQ0Ibn+k+C9SkIgIztWV9YrJjT9Yhg1kqddG7hhSAayGJe6hwXT1NcCQQDQGLB08x4gcxpx7fC50MLiD5Xi5LT1djxCQQYlx/UUy6TaFp32YLzMxIo8RrtxKWBOeFs/Wob4K2/vt9KG6po7AkEAy2epZ6ZC6xhse71KGlUTqlghp5dDTPYr2qcPbw7kBccL7gmULNx00swA0tIYrinNPFhUz++h+v06nv9cFGfpYQJAFnfT9YyaYB6NaGesaiOkJwxPvqO69hXCA9VJeKLST2B9eB6hB40CVYmS81cfveQdxdsw3vJDSGBIQbWzQWXrKQJBALGGT+wUX34xZaJd3jMcn1YgduhcN6XDI3jhDifPJ4jPQQD0V7lTtW9zUMkgRiyXVhjSFgImAZUgPQfMJQiQq78=";
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCKLf3VXqSQX3ABi8eAar58zVgQ6AjI7egEWaXLiJ+Dp2ZBGWom1d0LS3Ybdo3mgsCAQS/duufPzWPoPDVllTFk+2B+zfeTuXj7kDo3fVYt6laB07E0iijzMPFHSuMCFuqlS06dSSnzwzUpQ6fohVdyftoXzZyidFWAhJgrInUvAgMBAAECgYEAgtRkcRCHKv2zPJZouFh6wuBKbVFaI+iEJsXSigPkJBK1JBpqcziElWOhcM25dj+19mMV7BsuQEzHsZqRxCCcBtvu5+DztX1l8KmYpqdyccQ4m/oEVcVneWRDmt419+Tm2I7YEyoXERC6Gh1Dypw0A5SJdLibEfFeKoHEnoHzzkECQQDU4nQqjBu4uXbpfrjXKfUZN8+k5u/+4Yhy3VZOLUvmTQjqZVKeirGImjrddiRp56sFrmsZhanqK1UKieayI5rrAkEA1EtV6vIV9OpCgVeLaWLOYvK0eX3loiDlQc7IIAgt18dddlSUYLATFzrMt1dnEteyiVXNphwJwx8mqkWU4/Z1zQJAe1QhtlBq8u5HDGhEjyoYex6RJdhAKynfUaQWjr3BHc99HcXLQlvZE+k9tvTtjYkP0//Cvgtob2fhIXTYeFUWNwJAFOtfiglU9I9pAknYKQhdgg6cjiRDzpgjPzrbKZzkt5CjuxdWj7iKCQ34QlDQjWDH7RSRcT7uD1YwfzLgGx6cOQJAQ1tOCyvjcnetnCTNDOeCwaZF2LHtUWAnp6MyPE1Yq12RbjG0q6ai23a5tSWoHfCqHuA/77XXAShhj5dnQMHmRw==";
	// 支付宝公钥
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC017laTgmLDsVwVMZl45UN89HukQskDYzntsy0HD1tKG2EfXOVY75L6sZHwlLweJaQE+1rIdtKcGK6P/Xb6tvLeg/D5j4JwEOFR96Csv7T/Oc83zH31zzBedsyVmAke9gcB2nS8KW0gAflE5Z7kCvWbN7wgJj8bkFASLUG3GFjjQIDAQAB";
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private int errCode;

	private void sendPayResult() {
		Intent intent = new Intent(PayActivity.PAY_RECEIVER);
		intent.putExtra("error", errCode);
		intent.putExtra("type", PayActivity.PAY_TYPE_ZHIFUBAO);
		activity.sendBroadcast(intent);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				try {
					paySuc(msg);
				} catch (Exception e) {
				}
				
				break;
			}
			case SDK_CHECK_FLAG: {
				ToastUtil.show(activity, "检查结果为：" + msg.obj);
				break;
			}
			default:
				break;
			}
		};
	};
	
	
	private void paySuc(Message msg){
		PayResult payResult = new PayResult((String) msg.obj);

		// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
		String resultInfo = payResult.getResult();

		String resultStatus = payResult.getResultStatus();
		Log.d("=========>", resultInfo.toString()+"resultStatus--->"+resultStatus.toString());
		Log.d("url-------->", url + "?" + resultInfo);
		// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
		if (TextUtils.equals(resultStatus, "9000")) {
			errCode = 1;
			send(resultInfo);
			ToastUtil.show(activity, "支付成功");
		} else {
			// 判断resultStatus 为非“9000”则代表可能支付失败
			// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
			if (TextUtils.equals(resultStatus, "8000")) {
				ToastUtil.show(activity, "支付结果确认中");

			} else {
				errCode = 0;
				// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
				ToastUtil.show(activity, "支付失败");
				sendPayResult();
			}
		}
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(final String payInfo) {
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

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

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check() {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(activity);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(activity);
		String version = payTask.getVersion();
		ToastUtil.show(activity, version);
	}
	/**
	 * 告诉服务器支付成功
	 * @param str
	 */
	private void send(String str) {
		activity.showLoadingDialog("");
		new MyHttpUtils().send(HttpMethod.GET, url + "?" + str, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				sendPayResult();
				activity.cancelLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				sendPayResult();
				activity.cancelLoadingDialog();
			}

			@Override
			public void onSuccessPre(ResponseInfo<String> arg0) {

			}
		});
	}
}
