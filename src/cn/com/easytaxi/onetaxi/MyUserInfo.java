package cn.com.easytaxi.onetaxi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.NewNetworkRequest;
import cn.com.easytaxi.book.BookHistoryFragementActivity;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.Config;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.message.MyMessage;
import cn.com.easytaxi.mine.bean.MyInfo;
import cn.com.easytaxi.platform.AboutActivity;
import cn.com.easytaxi.platform.AddUserInfoActivity;
import cn.com.easytaxi.platform.RegisterActivity;
import cn.com.easytaxi.platform.service.OneBookService;
import cn.com.easytaxi.ui.JiFenActivity;
import cn.com.easytaxi.ui.MoreWebviewActivity;
import cn.com.easytaxi.ui.PingJiaActivity;
import cn.com.easytaxi.ui.SuggestionActivity;
import cn.com.easytaxi.util.ShareUtils;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.YdLoginUtil;
import com.aiton.yc.client.R;

/**
 * 这是首页，左边侧栏
 * 
 * @author 62568_000
 * 
 */
public class MyUserInfo implements OnClickListener {
	private ViewGroup backView;
	private Context context;
	private Animation left_right, right_left;
	private boolean isOpen = false;

	public MyUserInfo(Context contxt, ViewGroup backView) {
		this.context = contxt;
		this.backView = backView;
		backView.setVisibility(View.VISIBLE);
		initAnim();
		createView();
		chack();
	}

	private void chack() {
		SessionAdapter session = new SessionAdapter(context);
		String phone = session.get("_MOBILE");
		if (!TextUtils.isEmpty(phone)) {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = null;
			try {
				pi = pm.getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			String cityId = session.get("_CITY_ID");
			if (TextUtils.isEmpty(cityId)) {
				cityId = "1";
			}
			String passengerId = getPassengerId();
			if (pi == null || passengerId == null)
				return;
			NewNetworkRequest.checkUpdate(context, String.valueOf(Config.default_city.cityId), pi.versionCode, false, passengerId, newVersion);
		}
	}

	/**
	 * 打开
	 */
	public void open() {
		currentView.setVisibility(View.VISIBLE);
		isOpen = true;
		left_content.clearAnimation();
		left_content.startAnimation(left_right);
	}

	public boolean onBack() {
		if (isOpen) {
			close();
			return false;
		}
		return true;
	}

	/**
	 * 关闭
	 */
	public void close() {
		isOpen = false;
		left_content.clearAnimation();
		left_content.startAnimation(right_left);
	}

	private void initAnim() {
		left_right = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, -1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f);
		right_left = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 0.0f, TranslateAnimation.RELATIVE_TO_PARENT, -1.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.0f);
		left_right.setDuration(300);
		right_left.setDuration(300);

		left_right.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

			}
		});
		right_left.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				currentView.setVisibility(View.INVISIBLE);
			}
		});
	}

	private View currentView;
	private View right_space;
	private View left_content;

	/**
	 * 昵称
	 */
	private TextView mTv_name;
	/**
	 * 电话号码
	 */
	private TextView mTVPhone;
	/**
	 * 订单数
	 */
	private TextView mOrderNumber;

	/**
	 * 评价数
	 */
	private TextView mPingjiaNumber;
	/**
	 * 消息数量
	 */
	private TextView mMsgNunber;
//	private LinearLayout tuichuLayout;
//	private Button tuichu;

	private void createView() {
		currentView = LayoutInflater.from(context).inflate(R.layout.menu_frame, null);
		currentView.setVisibility(View.INVISIBLE);
		backView.addView(currentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		left_content = findViewById(R.id.left_content);
		right_space = findViewById(R.id.right_space);
		right_space.setOnClickListener(this);
		mTv_name = (TextView) findViewById(R.id.tv_name);
		mTVPhone = (TextView) findViewById(R.id.tv_phone);
		mOrderNumber = (TextView) findViewById(R.id.tv_ordernum);
		mPingjiaNumber = (TextView) findViewById(R.id.weiyue_num);
		mMsgNunber = (TextView) findViewById(R.id.message_num);
//		tuichu = (Button) findViewById(R.id.btn_exit);
//		tuichuLayout = (LinearLayout) findViewById(R.id.btn_change);
		findViewById(R.id.mytakepart_view).setOnClickListener(this);
		findViewById(R.id.myshort_view).setOnClickListener(this);
		findViewById(R.id.myidea_view).setOnClickListener(this);
		findViewById(R.id.update_view).setOnClickListener(this);
		findViewById(R.id.use_view).setOnClickListener(this);
		findViewById(R.id.about_view).setOnClickListener(this);
		findViewById(R.id.share_view).setOnClickListener(this);
		// findViewById(R.id.btn_exit).setOnClickListener(this);
//		findViewById(R.id.btn_change).setOnClickListener(this);
//		findViewById(R.id.btn_exit).setOnClickListener(this);

		findViewById(R.id.person_info_layout).setOnClickListener(this);

		findViewById(R.id.mybreak_view).setOnClickListener(this);//
		// 我的评价，临时跳转到投诉界面

		findViewById(R.id.my_jifen).setOnClickListener(this);//我的积分



		session = new SessionAdapter(context);
		// 从网络获取数据
		NewNetworkRequest.getMyInfo(getPassengerId(), getInfoCallBack);
		String phone = getPassengerId();
		String name = getName();
		if (TextUtils.isEmpty(name)) {
			mTv_name.setText("八闽专车");
		} else {
			mTv_name.setText(getName() + "");
		}

		if (TextUtils.isEmpty(phone)) {
			mTVPhone.setText("");
		} else {
			mTVPhone.setText(getPassengerId() + "");

		}
		SessionAdapter session = new SessionAdapter(context);
		String islogin = session.get("_MOBILE");
		if (TextUtils.isEmpty(islogin)) {
//			tuichuLayout.setVisibility(View.GONE);
//			tuichu.setVisibility(View.GONE);
		} else {
//			tuichuLayout.setVisibility(View.VISIBLE);
//			tuichu.setVisibility(View.VISIBLE);
		}

	}

	private String getPassengerId() {
		return session.get("_MOBILE");
	}

	private String getName() {
		return session.get("_NAME");

	}

	private SessionAdapter session;

	public void onDestory() {
		if (session == null)
			session.close();
	}

	public void onResume() {
		NewNetworkRequest.getMyInfo(getPassengerId(), getInfoCallBack);
		String phone = getPassengerId();
		String name = getName();
		if (TextUtils.isEmpty(name)) {
			mTv_name.setText("八闽专车");
		} else {
			mTv_name.setText(getName() + "");
		}

		if (TextUtils.isEmpty(phone)) {
//			tuichuLayout.setVisibility(View.GONE);
//			tuichu.setVisibility(View.GONE);
			mTVPhone.setText("");
		} else {
//			tuichuLayout.setVisibility(View.VISIBLE);
//			tuichu.setVisibility(View.VISIBLE);
			mTVPhone.setText(getPassengerId() + "");
		}
	}

	private View findViewById(int id) {
		return currentView.findViewById(id);
	}

	/**
	 * 获取个人信息成功
	 */
	Callback<MyInfo> getInfoCallBack = new Callback<MyInfo>() {
		@Override
		public void handle(MyInfo myInfo) {
			try {
				mOrderNumber.setText(myInfo.get_CALL_NUMBER() + "");
				mMsgNunber.setText(myInfo.get_MSG_NUMBER() + "");
				mPingjiaNumber.setText(String.valueOf(myInfo.get_LEVEL()));

				// mMsgNunber.setText(myInfo.);
				// tvMoney.setVisibility(View.GONE);
				// tvMoney.setText(myInfo.get_RMB() + "");
				// tvScore.setText(myInfo.get_SCORE() + "");
				// tvBook.setText(myInfo.get_CALL_NUMBER() + "");
				// tvMsg.setText(myInfo.get_MSG_NUMBER() + "");
				// tvBreach.setText(myInfo.get_WEIYUE_NUMBER() + "");
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	};

	Callback<String> newVersion = new Callback<String>() {
		@Override
		public void handle(String param) {
			// TODO Auto-generated method stub
			if (param.equals("current")) {
				((Activity) context).runOnUiThread(new Runnable() {
					public void run() {
						ToastUtil.show(context, "当前版本已经最新");
					}
				});
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right_space:
			close();
			break;
		case R.id.mytakepart_view:// 我的订单
			if (YdLoginUtil.isLogin(context))
				context.startActivity(new Intent(context, BookHistoryFragementActivity.class));
			break;
		case R.id.myshort_view: // 我的消息
			if (YdLoginUtil.isLogin(context))
				context.startActivity(new Intent(context, MyMessage.class));
			break;
		case R.id.mybreak_view: // 我的评价 临时写在这 跳转到投诉界面
			if (YdLoginUtil.isLogin(context))
				context.startActivity(new Intent(context, PingJiaActivity.class));
			break;

		case R.id.myidea_view: // 意见反馈
			if (YdLoginUtil.isLogin(context))
				context.startActivity(new Intent(context, SuggestionActivity.class));
			break;
		case R.id.update_view:// 在线升级
			if (YdLoginUtil.isLogin(context)) {
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = null;
				try {
					pi = pm.getPackageInfo(context.getPackageName(), 0);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				String cityId = session.get("_CITY_ID");
				if (TextUtils.isEmpty(cityId)) {
					cityId = "1";
				}
				String passengerId = getPassengerId();
				if (pi == null || passengerId == null)
					return;
				NewNetworkRequest.checkUpdate(context, String.valueOf(Config.default_city.cityId), pi.versionCode, true, passengerId, newVersion);
			}
			break;
		case R.id.use_view: // 使用指南
			useView();
			break;
		case R.id.about_view:// 关于
			context.startActivity(new Intent(context, AboutActivity.class));
			break;
		case R.id.share_view:// 分享
			// DiaoduCancelDialog.newInstance(context).show();
			ShareUtils.showShare(context);
			break;
			// case R.id.btn_exit: // 退出
			// exit();
			// break;
		case R.id.person_info_layout:
			if (YdLoginUtil.isLogin(context)) {
				Intent intent = new Intent(context, AddUserInfoActivity.class);
				intent.putExtra("mobile", getPassengerId());
				context.startActivity(intent);
			}
			break;
//		case R.id.btn_change: // 注销
//			Dialog dialog = createLogoutDlg();
//			dialog.show();
//			break;
//		case R.id.btn_exit: // 注销
//			Dialog dialog1 = createLogoutDlg();
//			dialog1.show();
//			break;

		case R.id.my_jifen: // 积分
			if (YdLoginUtil.isLogin(context)) {
				context.startActivity(new Intent(context, JiFenActivity.class));
			}
			break;
		}
	}

	protected void logOut() {
		// TODO Auto-generated method stub

		ETApp.getInstance().logOut();
		Intent intent = new Intent(context, RegisterActivity.class);
		context.startActivity(intent);
		((Activity) context).finish();
	}

	public Dialog createLogoutDlg() {
		// Callback<Object> okBtnCallback = new Callback<Object>() {
		// @Override
		// public void handle(Object param) {
		// CommonDialog dialog = (CommonDialog) param;
		// dialog.dismiss();
		// logOut();
		// }
		// };
		//
		// Callback<Object> cancelBtnCallback = new Callback<Object>() {
		// @Override
		// public void handle(Object param) {
		// CommonDialog dialog = (CommonDialog) param;
		// dialog.dismiss();
		// }
		// };
		//
		// // Dialog dialog = new CommonDialog(context, "提示",
		// // "您确认要注销现在手机号码，重新注册新的手机吗？", "确定", "取消", R.layout.dlg_close,
		// // okBtnCallback, cancelBtnCallback);
		// Dialog dialog = new CommonDialog(context, "提示", "确定退出吗？", "确定", "取消",
		// R.layout.dlg_close, okBtnCallback, cancelBtnCallback);
		Dialog dialog = MyDialog.comfirm(context, "温馨提示", "确定退出吗？", new SureCallback() {
			@Override
			public void onClick(int tag) {
				if (tag == LEFT) {
				} else {
					logOut();
				}
			}
		});

		return dialog;
	}

	// 退出
	private void exit() {
		Intent service = new Intent(context, OneBookService.class);
		context.stopService(service);
		// Intent name = new Intent(MoreActivity.this,
		// MainService.class);
		// stopService(name);

		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			am.restartPackage(context.getPackageName());
		}
	}

	// 使用指南
	private void useView() {
		Intent intent = new Intent(context, MoreWebviewActivity.class);
		intent.putExtra("title", "使用指南");
		intent.putExtra("type", 0);
		String uri = "file:///android_asset/helpuser_shanxi.html";
		intent.putExtra("uri", uri);
		context.startActivity(intent);
	}
}
