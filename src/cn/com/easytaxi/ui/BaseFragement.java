package cn.com.easytaxi.ui;

import cn.com.easytaxi.dialog.MyLoadingDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragement extends Fragment {
	protected MyLoadingDialog loading;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	protected void showLoadingDialog(String text) {
		if (loading == null)
			loading = new MyLoadingDialog(getActivity());
		loading.showWithText(text);
	}

	protected void cancelLoadingDialog() {
		if (loading != null && loading.isShowing())
			loading.dismiss();
	}
}
