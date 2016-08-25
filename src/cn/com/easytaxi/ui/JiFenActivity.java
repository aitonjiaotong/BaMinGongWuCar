package cn.com.easytaxi.ui;

import com.aiton.yc.client.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.platform.common.Util;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.Utils;
import cn.com.easytaxi.util.XTCPUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * 
 * @author LXL
 * 积分界面
 *
 */
public class JiFenActivity extends YdLocaionBaseActivity{
	private TextView mAll_jifen;
	private TextView mYiyong_jifen;
	private TextView mYu_jifen;
	private View mLayout_shangc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jifen_activity);
		initTitle();
		initView();
		getjifen(Long.valueOf(getPassengerId()));
	}


	private void initTitle(){
		TitleBar bar = new TitleBar(this);
		bar.setTitleName("我的积分");
	}

	private void initView(){
		mAll_jifen = (TextView) findViewById(R.id.all_jifen);
		mYiyong_jifen = (TextView) findViewById(R.id.yiyong_jifen);
		mYu_jifen = (TextView) findViewById(R.id.yu_jifen);
		mLayout_shangc = findViewById(R.id.layout_shangc);
		mAll_jifen.setText("");
		mYiyong_jifen.setText("");
		mYu_jifen.setText("");
		mLayout_shangc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(JiFenActivity.this, JiFenSCActivity.class));
			}
		});
		

	}


	//获取提现金额
	private void getjifen(long passengerId){
		showLoadingDialog("");
		try {
			JSONObject param = new JSONObject();
			param.put("action", "bookAction");
			param.put("method", "getScoreByPassengerId");
			param.put("passengerId", passengerId);
			XTCPUtil.send(param, new XTCPUtil.SimpleTcpCallback() {
				@Override
				public void onSuc(String result) {
					// TODO Auto-generated method stub
					try {
						cancelLoadingDialog();
						JSONObject object = new JSONObject(result);
						int error = object.getInt("error");
						if (error == 0) {
							String totalmonery = object.getString("score");
							String  total = Utils.getDecimal("#.##", false, Integer.valueOf(totalmonery) / 100d);
							double allmonery = Double.parseDouble(total);
							mAll_jifen.setText((int)allmonery+"");
							mYiyong_jifen.setText("0");
							mYu_jifen.setText("0");
						}else {
							ToastUtil.show(JiFenActivity.this, object.getString("errormsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
						cancelLoadingDialog();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			cancelLoadingDialog();
		}
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
