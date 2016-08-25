package cn.com.easytaxi.platform;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import cn.com.easytaxi.common.NetChecker;
import cn.com.easytaxi.onetaxi.TitleBar;

import com.aiton.yc.client.R;

public class MyWebViewActivity extends WebBaseActivity {
	MyWebViewActivity self;
	TitleBar bar;
	private WebView webView;
	private Button btn;
	private String url;

	protected void initUserData() {
		webView = (WebView) findViewById(R.id.help);
		WebSettings webSettings = webView.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webView.setWebChromeClient(new WebChromeClient());
		setWebViewClient(webView, btn);
		loadUrl(url);
		System.out.println("url - > " + url);
	}

	private void loadUrl(String url) {

		if (NetChecker.getInstance(this.getApplicationContext()).isAvailableNetwork()) {

			btn.setVisibility(View.INVISIBLE);
			String cityId = getCityId();
			if (TextUtils.isEmpty(cityId)) {
				cityId = "1";
			}
			webView.loadUrl(url);

			webView.setVisibility(View.VISIBLE);

		} else {

			webView.setVisibility(View.INVISIBLE);
			btn.setVisibility(View.VISIBLE);
			btn.setText(R.string.network_notgood);
			// webView.loadUrl(url);
		}
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_help);
		self = this;
		btn = (Button) findViewById(R.id.textView_refresh_again);
		bar = new TitleBar(self);
		bar.setTitleName(getIntent().getStringExtra("title"));
		 url = getIntent().getStringExtra("url");
//		url = "http://10.143.132.52:8080/EasytaxiMonitor/rule/valuationRule.html";
		initUserData();
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadUrl(url);
			}
		});

	}

	@Override
	protected void onDestroy() {
		bar.close();
		super.onDestroy();
	}
}
