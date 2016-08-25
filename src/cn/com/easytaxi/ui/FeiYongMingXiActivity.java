package cn.com.easytaxi.ui;

import org.json.JSONObject;

import cn.com.easytaxi.onetaxi.NewMainActivityNew;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.util.Utils;
import cn.com.easytaxi.util.XTCPUtil;

import com.aiton.yc.client.R;
import com.xc.lib.activity.BaseActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeiYongMingXiActivity extends BaseActivity{
	private TitleBar bar;
	private LinearLayout mChetype_layout;
    private TextView mFeiyongmingxi;
    private TextView mQbjname;
    private TextView mQibujia;
    private TextView mLcfname;
    private TextView mLiqifei;
    private TextView mDsfname;
    private TextView mDisufei;
    private TextView mYtfname;
    private TextView mYuantufei;
    private TextView mYjfname;
    private TextView mYejianfei;
    private TextView mGsfname;
    private TextView mGaosufei;
    private TextView mLqfname;
    private TextView mLuqiaofei;
    private TextView mTcfname;
    private TextView mTingxhefei;
    private TextView mQtfname;
    private TextView mOther;
    private LinearLayout mChuzuche_layout;
    private TextView mChuzumingxi;
    private TextView mCfname;
    private TextView mCefei;
    private TextView mCzcqtname;
    private TextView mQitafei;

	private long bookid;
	private View mLayout1;
	private View mLayout2;
	private View mLayout3;
	private View mLayout4;
	private View mLayout5;
	private View mLayout6;
	private View mLayout7;
	private View mLayout8;
	private View mLayout9;
	private View mLayout10;
	private View mLayout11;
	private ProgressDialog mProgressDialog;
	private TextView mTv_yuan;
	private TextView mTv_chuyuan;
	int qtf = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mingxi);
		bookid = getIntent().getLongExtra("bookid", 0);
		mProgressDialog = new ProgressDialog(FeiYongMingXiActivity.this);
		mProgressDialog.setMessage("请稍后...");
		initHead();
		initview();
		getjiage();
	}


	private void initHead() {
		bar = new TitleBar(this);
		bar.setTitleName("费用明细");
	}



	private void initview(){
		mChetype_layout = (LinearLayout) findViewById(R.id.chetype_layout);
        mFeiyongmingxi = (TextView) findViewById(R.id.feiyongmingxi);
        mQbjname = (TextView) findViewById(R.id.qbjname);
        mQibujia = (TextView) findViewById(R.id.qibujia);
        mLcfname = (TextView) findViewById(R.id.lcfname);
        mLiqifei = (TextView) findViewById(R.id.liqifei);
        mDsfname = (TextView) findViewById(R.id.dsfname);
        mDisufei = (TextView) findViewById(R.id.disufei);
        mYtfname = (TextView) findViewById(R.id.ytfname);
        mYuantufei = (TextView) findViewById(R.id.yuantufei);
        mYjfname = (TextView) findViewById(R.id.yjfname);
        mYejianfei = (TextView) findViewById(R.id.yejianfei);
        mGsfname = (TextView) findViewById(R.id.gsfname);
        mGaosufei = (TextView) findViewById(R.id.gaosufei);
        mLqfname = (TextView) findViewById(R.id.lqfname);
        mLuqiaofei = (TextView) findViewById(R.id.luqiaofei);
        mTcfname = (TextView) findViewById(R.id.tcfname);
        mTingxhefei = (TextView) findViewById(R.id.tingxhefei);
        mQtfname = (TextView) findViewById(R.id.qtfname);
        mOther = (TextView) findViewById(R.id.other);
        mChuzuche_layout = (LinearLayout) findViewById(R.id.chuzuche_layout);
        mChuzumingxi = (TextView) findViewById(R.id.chuzumingxi);
        mCfname = (TextView) findViewById(R.id.cfname);
        mCefei = (TextView) findViewById(R.id.cefei);
        mCzcqtname = (TextView) findViewById(R.id.czcqtname);
        mQitafei = (TextView) findViewById(R.id.qitafei);
        
        mTv_yuan = (TextView) findViewById(R.id.tv_yuan);
        

		mLayout1 = findViewById(R.id.layout1);
		mLayout2 = findViewById(R.id.layout2);
		mLayout3 = findViewById(R.id.layout3);
		mLayout4 = findViewById(R.id.layout4);
		mLayout5 = findViewById(R.id.layout5);
		mLayout6 = findViewById(R.id.layout6);
		mLayout7 = findViewById(R.id.layout7);
		mLayout8 = findViewById(R.id.layout8);
		mLayout9 = findViewById(R.id.layout9);
		mLayout10 = findViewById(R.id.layout10);
		mLayout11 = findViewById(R.id.layout11);

		//出租车
		mChuzuche_layout = (LinearLayout) findViewById(R.id.chuzuche_layout);
		mChuzumingxi = (TextView) findViewById(R.id.chuzumingxi);
		mCefei = (TextView) findViewById(R.id.cefei);
		mQitafei = (TextView) findViewById(R.id.qitafei);
		mTv_chuyuan = (TextView) findViewById(R.id.tv_yuan1);
	}

	private void getjiage(){
		mProgressDialog.show();
		try {
			JSONObject param = new JSONObject();
			param.put("action", "bookAction");
			param.put("method", "getprice");
			param.put("id", bookid);
			XTCPUtil.send(param, new XTCPUtil.XNetCallback() {
				@Override
				public void onSuc(String result) {
					try {
						JSONObject object = new JSONObject(result);
						int error = object.getInt("error");
						if (error == 0) {
							mProgressDialog.cancel();
							int fee = object.getInt("fee");
							String fee1 = Utils.getDecimal("#.##", false, fee / 100d);
							double zongfeiyong = getNumber(fee1);
							int qibujia = object.getInt("qbj");
							String qibujia1 = Utils.getDecimal("#.##", false, qibujia / 100d);
							int xcf = object.getInt("xcf");
							String xcf1 = Utils.getDecimal("#.##", false, xcf / 100d);
							if (object.has("qtf")) {
								qtf = object.getInt("qtf");
							}else {
								qtf = 0;
							}
							String qtf1 = Utils.getDecimal("#.##", false, qtf / 100d);
							if (qibujia == 0) {
								mChuzuche_layout.setVisibility(View.VISIBLE);
								mChetype_layout.setVisibility(View.GONE);
								mChuzumingxi.setText(String.format("%.2f", zongfeiyong)+"");
								//mChuzumingxi.setText(fee1+"");
								mTv_chuyuan.setText("元");
								if (xcf == 0) {
									mLayout10.setVisibility(View.GONE);
								}else {
									mCfname.setText("车费");
									//mCefei.setText(xcf1+"元");
									double chefei = getNumber(xcf1);
									mCefei.setText(String.format("%.2f", chefei)+"元");
								}
								if (qtf == 0) {
									mLayout11.setVisibility(View.GONE);
								}else {
									mCzcqtname.setText("其他费");
									//mQitafei.setText(qtf1+"元");
									double chuqitafei = getNumber(qtf1);
									mQitafei.setText(String.format("%.2f", chuqitafei)+"元");
								}
							}else {
								mChuzuche_layout.setVisibility(View.GONE);
								mChetype_layout.setVisibility(View.VISIBLE);
								int lcf = object.getInt("lcf");
								String lcf1 = Utils.getDecimal("#.##", false, lcf / 100d);
								int dsf = object.getInt("dsf");
								String dsf1 = Utils.getDecimal("#.##", false, dsf / 100d);
								int ytf = object.getInt("ytf");
								String ytf1 = Utils.getDecimal("#.##", false, ytf / 100d);
								int yjf = object.getInt("yjf");
								String yjf1 = Utils.getDecimal("#.##", false, yjf / 100d);
								int gsf = object.getInt("gsf");
								String gsf1 = Utils.getDecimal("#.##", false, gsf / 100d);
								int lqf = object.getInt("glf");
								String lqf1 = Utils.getDecimal("#.##", false, lqf / 100d);
								int tcf = object.getInt("tcf");
								String tcf1 = Utils.getDecimal("#.##", false, tcf / 100d);
								mFeiyongmingxi.setText(fee1+"");
								mTv_yuan.setText("元");
								if (qibujia == 0) {
									mLayout1.setVisibility(View.GONE);
								}else {
									mQbjname.setText("起步价");
									//mQibujia.setText(qibujia1+"元");
									double qbujia = getNumber(qibujia1);
									mQibujia.setText(String.format("%.2f", qbujia)+"元");
								}
								if (lcf == 0) {
									mLayout2.setVisibility(View.GONE);
								}else {
									mLcfname.setText("里程费");
									//mLiqifei.setText(lcf1+"元");
									double lichengfei = getNumber(lcf1);
									mLiqifei.setText(String.format("%.2f", lichengfei)+"元");
								}
								if (dsf == 0) {
									mLayout3.setVisibility(View.GONE);
								}else {
									mDsfname.setText("低速费");
									//mDisufei.setText(dsf1+"元");
									double disufei = getNumber(dsf1);
									mDisufei.setText(String.format("%.2f", disufei)+"元");
								}
								if (ytf == 0) {
									mLayout4.setVisibility(View.GONE);
								}else {
									mYtfname.setText("远途费");
									double yuantufei = getNumber(ytf1);
									mYuantufei.setText(String.format("%.2f", yuantufei)+"元");
									//mYuantufei.setText(ytf1+"元");
								}
								if (yjf == 0) {
									mLayout5.setVisibility(View.GONE);
								}else {
									mYjfname.setText("夜间费");
									double yejianfei = getNumber(yjf1);
									mYejianfei.setText(String.format("%.2f", yejianfei)+"元");
									//mYejianfei.setText(yjf1+"元");
								}

								if (gsf == 0) {
									mLayout6.setVisibility(View.GONE);
								}else {
									mGsfname.setText("高速费");
									double gaosufei = getNumber(gsf1);
									mGaosufei.setText(String.format("%.2f", gaosufei)+"元");
									//mGaosufei.setText(gsf1+"元");
								}
								if (lqf == 0) {
									mLayout7.setVisibility(View.GONE);
								}else {
									mLqfname.setText("路桥费");
									//mLuqiaofei.setText(lqf1+"元");
									double luqiaofei = getNumber(lqf1);
									mLuqiaofei.setText(String.format("%.2f", luqiaofei)+"元");
								}

								if (tcf == 0) {
									mLayout8.setVisibility(View.GONE);
								}else {
									mTcfname.setText("停车费");
									//mTingxhefei.setText(tcf1+"元");
									double tingchefei = getNumber(tcf1);
									mTingxhefei.setText(String.format("%.2f", tingchefei)+"元");
								}

								if (qtf == 0) {
									mLayout9.setVisibility(View.GONE);
								}else {
									mQtfname.setText("其他费");
								//	mOther.setText(qtf1+"元");
									double qitafei = getNumber(qtf1);
									mOther.setText(String.format("%.2f", qitafei)+"元");
								}
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
						mProgressDialog.cancel();
					}
				}
				@Override
				public void onStart() {
				}
				@Override
				public void onComplete() {
				}
				@Override
				public void error(Throwable e, int errorcode) {
					mProgressDialog.cancel();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			mProgressDialog.cancel();
		}

	}

	
	
	private double getNumber(String et) {
		try {
			return Double.parseDouble(change(et));
		} catch (Exception e) {
		}
		return 0;
	}


	private String change(String str) {
		if (str.equals("")) {
			return "0";
		} else if (str.startsWith(".")) {
			return "0" + str;
		} else {
			return str;
		}

	}
	
	
	
	
	
}
