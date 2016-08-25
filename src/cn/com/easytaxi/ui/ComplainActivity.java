package cn.com.easytaxi.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.aiton.yc.client.R;
import com.google.gson.JsonObject;

import cn.com.easytaxi.book.BookBean;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.onetaxi.NewMainActivityNew;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.XTCPUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ComplainActivity extends Activity implements OnClickListener {
	private TitleBar bar;
	private TextView mEt_chexing;// 车型不符
	private TextView mEt_more_toll;// 多收费
	private TextView mEt_detour;// 绕路
	private TextView mEt_statemal;// 态度恶劣
	private EditText mEt_excuse;// 其他理由
	private Button submit; // 提交按钮

	private View layout_view1;
	private View layout_view2;
	private View layout_view3;
	private View layout_view4;

	private ImageView mImageView1;
	private ImageView mImageView2;
	private ImageView mImageView3;
	private ImageView mImageView4;

	/**
	 * 0代表车型不符，1代表多收费，2绕路，3态度恶劣
	 */
	private int type = -1;

	private String content = "";

	private BookBean mBean;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complain);
		mProgressDialog = new ProgressDialog(ComplainActivity.this);
		mProgressDialog.setMessage("提交中...");
		tetile();
		initview();
		mBean = (BookBean) getIntent().getSerializableExtra("bean");

	}

	private void tetile() {
		bar = new TitleBar(this);
		bar.setTitleName("投诉");
		bar.switchToCityButton();
		bar.getRightCityButton().setVisibility(View.GONE);
		bar.getRightHomeButton().setVisibility(View.GONE);
	}

	private void initview() {
		mEt_chexing = (TextView) findViewById(R.id.et_chexing);
		mEt_more_toll = (TextView) findViewById(R.id.et_more_toll);
		mEt_detour = (TextView) findViewById(R.id.et_detour);
		mEt_statemal = (TextView) findViewById(R.id.et_statemal);
		mEt_excuse = (EditText) findViewById(R.id.et_excuse);

		layout_view1 = findViewById(R.id.layout_view1);
		layout_view2 = findViewById(R.id.layout_view2);
		layout_view3 = findViewById(R.id.layout_view3);
		layout_view4 = findViewById(R.id.layout_view4);

		mImageView1 = (ImageView) findViewById(R.id.imageview1);
		mImageView2 = (ImageView) findViewById(R.id.imageview2);
		mImageView3 = (ImageView) findViewById(R.id.imageview3);
		mImageView4 = (ImageView) findViewById(R.id.imageview4);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		layout_view1.setOnClickListener(this);
		layout_view2.setOnClickListener(this);
		layout_view3.setOnClickListener(this);
		layout_view4.setOnClickListener(this);

		mEt_excuse.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mImageView1.setBackgroundResource(R.drawable.choose);
				mImageView2.setBackgroundResource(R.drawable.choose);
				mImageView3.setBackgroundResource(R.drawable.choose);
				mImageView4.setBackgroundResource(R.drawable.choose);
				type=-1;
				return false;
			}
		});

	}

	private boolean getchar() {
		String excuse = mEt_excuse.getText().toString().trim();
		if (TextUtils.isEmpty(excuse)) {
			if (type == 0) {
				content = mEt_chexing.getText().toString().trim();
			} else if (type == 1) {
				content = mEt_more_toll.getText().toString().trim();
			} else if (type == 2) {
				content = mEt_detour.getText().toString().trim();
			} else if (type == 3) {
				content = mEt_statemal.getText().toString().trim();
			} else {
				return false;
			}
		} else {
			 if (excuse.length()<8) {
				 return false;
			}else {
				content = excuse;
			}
		}
		return true;

	}

	/**
	 * 
	 * @param suggesterId
	 *            建议人id passengerId
	 * @param userId
	 *            投诉人id passengerId
	 * @param cityId
	 *            城市id cityId
	 * @param bookId
	 *            订单id id
	 * @param taxiId
	 *            司机id replyerId
	 */
	private void getdatas(long suggesterId, long userId, long cityId, long bookId, long taxiId) {
		mProgressDialog.show();
		try {
			JSONObject param = new JSONObject();
			param.put("action", "receivedAction");
			param.put("method", "setComplain");
			param.put("suggesterId", suggesterId);
			param.put("userId", userId);
			param.put("cityId", cityId);
			param.put("source", 1 + "");
			param.put("bookId", bookId);
			param.put("content", content);
			param.put("taxiId", taxiId);
			param.put("type", 3 + "");
			XTCPUtil.send(1L, param, new XTCPUtil.XNetCallback() {
				@Override
				public void onSuc(String result) {
					// TODO Auto-generated method stub
					try {
						JSONObject object = new JSONObject(result);
						int error = object.getInt("error");
						if (error == 0) {
							ToastUtil.show(ComplainActivity.this, "投诉成功");
							mProgressDialog.cancel();
							mImageView1.setBackgroundResource(R.drawable.choose);
							mImageView2.setBackgroundResource(R.drawable.choose);
							mImageView3.setBackgroundResource(R.drawable.choose);
							mImageView4.setBackgroundResource(R.drawable.choose);
							mEt_excuse.setText("");
							finish();

						} else if (error == 1) {
							ToastUtil.show(ComplainActivity.this, "网络不给力");
							mProgressDialog.cancel();
						} else {
							ToastUtil.show(ComplainActivity.this, "投诉失败");
							mProgressDialog.cancel();
						}
					} catch (JSONException e) {
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
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_view1:
			type = 0;
			mImageView1.setBackgroundResource(R.drawable.chooseon);
			mImageView2.setBackgroundResource(R.drawable.choose);
			mImageView3.setBackgroundResource(R.drawable.choose);
			mImageView4.setBackgroundResource(R.drawable.choose);
			mEt_excuse.setText("");
			break;
		case R.id.layout_view2:
			type = 1;
			mImageView1.setBackgroundResource(R.drawable.choose);
			mImageView2.setBackgroundResource(R.drawable.chooseon);
			mImageView3.setBackgroundResource(R.drawable.choose);
			mImageView4.setBackgroundResource(R.drawable.choose);
			mEt_excuse.setText("");
			break;
		case R.id.layout_view3:
			type = 2;
			mImageView1.setBackgroundResource(R.drawable.choose);
			mImageView2.setBackgroundResource(R.drawable.choose);
			mImageView3.setBackgroundResource(R.drawable.chooseon);
			mImageView4.setBackgroundResource(R.drawable.choose);
			mEt_excuse.setText("");
			break;
		case R.id.layout_view4:
			type = 3;
			mImageView1.setBackgroundResource(R.drawable.choose);
			mImageView2.setBackgroundResource(R.drawable.choose);
			mImageView3.setBackgroundResource(R.drawable.choose);
			mImageView4.setBackgroundResource(R.drawable.chooseon);
			mEt_excuse.setText("");
			break;
		case R.id.submit:
			
			if(!getchar()){
				ToastUtil.show(ComplainActivity.this, "请选择或输入8-200个字的建议");
				return;
			}
			if (mBean == null)
				return;
			try {
				getdatas(mBean.getPassengerId(), mBean.getPassengerId(), Config.default_city.cityId, mBean.getId(), mBean.getReplyerId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

}
