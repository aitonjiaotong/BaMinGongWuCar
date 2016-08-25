package cn.com.easytaxi.platform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.common.Util;
import cn.com.easytaxi.util.ToastUtil;

import com.aiton.yc.client.R;

public class EditLinkActivity extends Activity implements OnClickListener {
	private TextView name, phone;
	public static final String default_name = "default_name";
	public static final String default_phone = "default_phone";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_linkman);
		TitleBar bar = new TitleBar(this);
		bar.setTitleName("乘车人");

		String defaultName = getIntent().getStringExtra(default_name);
		String defaultPhone = getIntent().getStringExtra(default_phone);

		name = (TextView) findViewById(R.id.nameEdit);
		phone = (TextView) findViewById(R.id.phoneEdit);

		if (!TextUtils.isEmpty(defaultName)) {
			name.setText(defaultName);
		}
		if (!TextUtils.isEmpty(defaultPhone)) {
			phone.setText(defaultPhone);
		}
		findViewById(R.id.complete).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String nameStr = name.getText().toString();
		String phoneStr = phone.getText().toString();
		if (TextUtils.isEmpty(nameStr)) {
			ToastUtil.show(this, "请填写联系人姓名");
			return;
		}
		if (TextUtils.isEmpty(phoneStr)) {
			ToastUtil.show(this, "请填写联系人电话");
			return;
		} else if (!phoneStr.matches(Util.REGEX_MOBILE)) {
			ToastUtil.show(this, "请输入正确的手机号码");
			// Window.info(self, "请输入正确的手机号码");
			return;
		}

		Intent intent = new Intent();
		intent.putExtra("name", nameStr);
		intent.putExtra("phone", phoneStr);
		setResult(RESULT_OK, intent);
		finish();
	}
}
