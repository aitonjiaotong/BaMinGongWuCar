package cn.com.easytaxi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.NewNetworkRequest;
import cn.com.easytaxi.TaxiState;

import com.aiton.yc.client.R;

import cn.com.easytaxi.common.ActionLogUtil;
import cn.com.easytaxi.common.NetChecker;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.YdBaseActivity;
import cn.com.easytaxi.util.ToastUtil;

public class SuggestionActivity extends YdBaseActivity {

	private TitleBar bar;
	private TextView suggest_app_info_version;
	private EditText suggest_info;
	private Button suggest_info_subbmit;
	private View cover_loading;
	private TextView change_num;
	private Handler handler;
	public static final int FEEDBACK_OK = 1000;

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		// suggest_app_info_version =(TextView)
		// findViewById(R.id.suggest_app_info_version);
		suggest_info = (EditText) findViewById(R.id.suggest_info);
		suggest_info_subbmit = (Button) findViewById(R.id.suggest_info_subbmit);
		cover_loading = findViewById(R.id.cover_loading);
		change_num = (TextView) findViewById(R.id.change_num);
	}

	@Override
	protected void initListeners() {
		bar = new TitleBar(this);
		bar.setTitleName("意见反馈");
		// TODO Auto-generated method stub
		suggest_info_subbmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String info = suggest_info.getText().toString();

				if (info.length() < 8 || info.length() > 200 || "".equals(info.trim()) || info == null) {
					ToastUtil.show(SuggestionActivity.this, "请输入8~200个字符的建议和意见！");

				} else {
					subbmitFeedBack(info);
					// 写入日志
					ActionLogUtil.writeActionLog(SuggestionActivity.this, R.array.more_fankui_jianyi_submit, "");
				}
			}
		});

		suggest_info.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				change_num.setHint("还可以输入" + (200 - suggest_info.getText().length()) + "字");
			}
		});
		suggest_info_subbmit.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				count++;
				if (count % 2 == 0) {
					Intent intent = new Intent(SuggestionActivity.this, SetIpActivity.class);
					startActivity(intent);
				}

				return false;
			}
		});

	}

	private static int count = 0;

	protected void subbmitFeedBack(String info) {

		if (NetChecker.getInstance(getApplicationContext()).isAvailableNetwork()) {
			cover_loading.setVisibility(View.VISIBLE);
			String cityId = getCityId();
			String passengerId = getPassengerId();
			NewNetworkRequest.feedBack(handler, cityId, passengerId, info, 5);
		} else {

			ToastUtil.show(SuggestionActivity.this, "网络不给力,请检查网络");
		}

	}

	@Override
	protected void initUserData() {

		// suggest_app_info_version.setText(ETApp.getInstance().getMobileInfo().getVerisonName());

		// TODO Auto-generated method stub
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == FEEDBACK_OK) {

					cover_loading.setVisibility(View.GONE);
//					ToastUtil.show(SuggestionActivity.this, "已提交服务器处理");
					ToastUtil.show(SuggestionActivity.this, "提交成功，感谢你对我们提供意见");
					finish();
				}

			}
		};
	}

	@Override
	protected void regReceiver() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unRegReceiver() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_suggest_activity);
		initViews();
		initUserData();
		initListeners();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (bar != null) {
			bar.close();
			bar = null;
		}
		super.onDestroy();
	}
}
