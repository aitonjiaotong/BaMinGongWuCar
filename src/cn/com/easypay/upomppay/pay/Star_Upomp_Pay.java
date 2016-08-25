package cn.com.easypay.upomppay.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.com.easypay.upomppay.config.UpompConfig;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.pay.Callback;

import com.unionpay.UPPayAssistEx;

public class Star_Upomp_Pay {

	public static final int PLUGIN_VALID = 0;
	public static final int PLUGIN_NOT_INSTALLED = -1;
	public static final int PLUGIN_NEED_UPGRADE = 2;

	/*
	 * 启动插件，该方法中PluginHelper依赖于com_unionpay_upomp_lthj_lib.jar
	 */
	public void start_upomp_pay(Activity thisActivity, String LanchPay, final Callback<Object> callback) {

		byte[] to_upomp = LanchPay.getBytes();

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				byte[] data = (byte[]) msg.obj;
				if (data != null) {
					byte[] xml = (byte[]) msg.obj;
					// 自行解析并输出
					String sxml;
					try {
						sxml = new String(xml, "utf-8");
						AppLog.LogD("这是支付成功后，回调返回的报文，需自行解析-->" + sxml);
						if (TextUtils.isEmpty(sxml)) {
							callback.onFailure(new Throwable("data is null"), "data is null");
						} else {
							String startS = "<respCode>";
							int start = sxml.indexOf(startS);
							int offset = startS.length();
							int end = sxml.indexOf("</respCode>");
							String resultCode = sxml.substring(start + offset, end);
							if ("0000".equals(resultCode)) {// 充值成功
								callback.onSuccess(resultCode);
							} else {

								callback.onFailure(new Throwable("data is null"), "data is null");
							}
						}
					} catch (Exception e) {
						callback.onFailure(new Throwable(e), e.getMessage());
					}
				} else {
					callback.onFailure(new Throwable("data is null"), "data is null");
				}
			}

		};

		Bundle mbundle = new Bundle();
		// to_upomp为商户提交的XML
		mbundle.putByteArray("xml", to_upomp);

		mbundle.putString("action_cmd", UpompConfig.CMD_PAY_PLUGIN);
		// 更换参数调起测试与生产插件,value为true是测试插件 ，为false是生产插件
		mbundle.putBoolean("test", false);
//		PluginHelper.LaunchPlugin(thisActivity, handler, mbundle);
	}

	/**
	 * 
	 * @param activity
	 * @param tn
	 * @param mode
	 *            参数解释： 00 - 启动银联正式环境; 01 - 连接银联测试环境
	 */
	public void doStartUnionPayPlugin(final Activity activity, String tn, String mode) {
		int ret = UPPayAssistEx.startPay(activity, null, null, tn, mode);
		if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
			// 需要重新安装控件
			AppLog.LogE(" plugin not found or need upgrade!!!");

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("提示");
			builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

			builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					UPPayAssistEx.installUPPayPlugin(activity);
					dialog.dismiss();
				}
			});

			builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();

		}
		AppLog.LogE(" plugin ret"  + ret);
	}

}
