package com.aiton.yc.client.wxapi;

import net.sourceforge.simcpux.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	private void sendPayResult(int errCode) {
		Intent intent = new Intent(PayActivity.PAY_RECEIVER);
		intent.putExtra("error", errCode);
		intent.putExtra("type", PayActivity.PAY_TYPE_WEIXIN);
		sendBroadcast(intent);
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.i(TAG, "onPayFinish, errCode = " + resp.errCode);
		sendPayResult(resp.errCode);
		finish();
		// if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		// }
	}
}