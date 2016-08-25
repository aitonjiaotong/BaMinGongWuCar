package cn.com.easytaxi.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.aiton.yc.client.R;
import com.xc.lib.layout.LayoutUtils;
import com.xc.lib.layout.ScreenConfig;

public class CarSelectContoller{
	private RadioGroup group;
	private View layout;
	private Activity activity;
	private float width_height = 156 / 46f;
	private int space = 10;
	private int childWidth = 0;
	private int childSize;

	public CarSelectContoller(Activity activity) {
		this.activity = activity;
		this.group = (RadioGroup) activity.findViewById(R.id.carradiogroup);
		this.layout = activity.findViewById(R.id.carselectlayout);
		this.childSize = group.getChildCount();
		initView();
	}
	
	public CarSelectContoller(Activity activity,View view){
		this.activity = activity;
		this.group = (RadioGroup) view.findViewById(R.id.carradiogroup);
		this.layout = view;
		this.childSize = group.getChildCount();
		initView();
	}
	
	public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
		group.setOnCheckedChangeListener(listener);
	}
	
	public void setCheckId(int checkId){
		group.check(checkId);
	}
	
	public void setVisibility(int visibility){
		layout.setVisibility(visibility);
	}

	private void initView() {
		space = LayoutUtils.getRate4px(space);
		childWidth = (ScreenConfig.SCRREN_W - 5 * space) / 4;
		ViewGroup.LayoutParams params = group.getLayoutParams();
		params.height = (int) (childWidth / width_height);
		group.setLayoutParams(params);
		for (int i = 0; i < childSize; i++) {
			setChildParams(group.getChildAt(i));
		}
	}

	public int getCheckRbId() {
		return group.getCheckedRadioButtonId();
	}

	private void setChildParams(View child) {
		RadioGroup.LayoutParams params = (LayoutParams) child.getLayoutParams();
		params.leftMargin = space;
		child.setLayoutParams(params);

	}

}
