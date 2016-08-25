package cn.com.easytaxi.ui;

import com.aiton.yc.client.R;
import android.os.Bundle;
import android.widget.ImageView;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;

public class JiFenSCActivity extends YdLocaionBaseActivity{

	private ImageView mIv_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jifensc_activity);
		inittitle();
		initview();
	}

	private void inittitle(){
		TitleBar bar = new TitleBar(this);
		bar.setTitleName("积分商城");
	}

	private void initview(){
		mIv_image = (ImageView) findViewById(R.id.iv_image);
		mIv_image.setBackgroundResource(R.drawable.myjifensc);
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

}
