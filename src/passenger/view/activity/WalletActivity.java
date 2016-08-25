package passenger.view.activity;

import com.aiton.yc.client.R;
import com.aiton.yc.client.R.id;
import com.aiton.yc.client.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class WalletActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);
		findID();
		setListener();
	}

	private void setListener() {
		findViewById(R.id.rela_Recharge).setOnClickListener(this);
		findViewById(R.id.imageView_back).setOnClickListener(this);
	}

	private void findID() {
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.imageView_back:
			finish();
			break;
		case R.id.rela_Recharge:
			intent.setClass(WalletActivity.this, RechargeActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
