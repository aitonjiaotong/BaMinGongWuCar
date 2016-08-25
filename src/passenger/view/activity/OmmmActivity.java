package passenger.view.activity;

import com.aiton.yc.client.R;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import cn.com.easytaxi.ui.BaseFragementActivity;

public class OmmmActivity extends BaseFragementActivity implements ViewPager.OnPageChangeListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_history_list);

	}
	@Override
	protected void onDestroy() {
//		if(titleBar != null){
//			titleBar.close();
//			titleBar = null;
//		}
		super.onDestroy();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
	}
}
