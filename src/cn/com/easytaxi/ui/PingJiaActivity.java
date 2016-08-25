package cn.com.easytaxi.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.SocketUtil;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.platform.YdLocaionBaseActivity;
import cn.com.easytaxi.ui.adapter.PingJiaAdapter;
import cn.com.easytaxi.ui.bean.PinglunBean;
import cn.com.easytaxi.util.PullTool;
import cn.com.easytaxi.util.ToastUtil;
import cn.com.easytaxi.util.XGsonUtil;

import com.aiton.yc.client.R;
import com.google.gson.JsonArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PingJiaActivity extends YdLocaionBaseActivity implements OnRefreshListener2<ListView> {
	private PullToRefreshListView pullListview;
	private PingJiaAdapter adapter;
	private List<PinglunBean> date = new ArrayList<PinglunBean>();
	private Handler handler = new Handler();
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pingjia);
		initViews();
		showLoadingDialog("数据加载中...");
		getdate(0);
	}

	private View footerView;
	private TextView footTv;

	@Override
	protected void initViews() {
		TitleBar bar = new TitleBar(this);
		bar.setTitleName("我的评价");
		adapter = new PingJiaAdapter(PingJiaActivity.this, date);
		pullListview = (PullToRefreshListView) findViewById(R.id.pulllistview);
		pullListview.setMode(Mode.PULL_FROM_START);
		pullListview.getRefreshableView().setHeaderDividersEnabled(false);
		pullListview.getRefreshableView().setFooterDividersEnabled(false);
		footerView = LayoutInflater.from(this).inflate(R.layout.listview_footer_layout, null);
		footerView.setVisibility(View.GONE);
		footTv = (TextView) footerView.findViewById(R.id.listview_footer);
		pullListview.getRefreshableView().addFooterView(footerView);
		pullListview.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
		pullListview.setAdapter(adapter);
		cn.com.easytaxi.util.PullTool.PullListViewUtils.setPullListViewParams(pullListview);
		pullListview.setOnRefreshListener(this);

		view = findViewById(R.id.layout);
	}

	private void doRefresh() {
		getdate(0);
	}

	private void doNextPage() {
		getdate(date.size());
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

	private void setIsMore(boolean enable) {
		if (date.size() == 0) {
			footerView.setVisibility(View.GONE);
			pullListview.setMode(Mode.PULL_FROM_START);
		} else if (!enable) {
			footerView.setVisibility(View.VISIBLE);
			footTv.setText("没有更多评价了");
			pullListview.setMode(Mode.PULL_FROM_START);
		} else {
			footerView.setVisibility(View.GONE);
			pullListview.setMode(Mode.BOTH);
		}
	}

	private void getdate(final int page) {
		try {
			// showDialog(0);
			JSONObject json = new JSONObject();
			json.put("action", "proxyAction");
			json.put("method", "query");
			json.put("op", "getPassengerAssess");
			json.put("passengerId", getPassengerId());
			json.put("startId", page);
			AppLog.LogD("xyw", "cancelBook json-->" + json.toString());
			SocketUtil.getJSONObject(Long.valueOf(getPassengerId()), json, new Callback<JSONObject>() {
				int size = 0;

				@Override
				public void handle(JSONObject param) {
					try {
						if (param != null) {
							JSONObject result = (JSONObject) param;
							AppLog.LogD("xyw", "cancelBook--->" + result.toString());
							if (result.getInt("error") != 0) {
								view.setVisibility(View.VISIBLE);
							} else {
								JsonArray array = XGsonUtil.getJsonArray(result.getString("datas"));
								List<PinglunBean> beans = XGsonUtil.getListFromJson(false, array, PinglunBean.class);
								if (page == 0)
									date.clear();
								date.addAll(beans);
								if (beans != null)
									size = beans.size();
								adapter.notifyDataSetChanged();
								if (date.size() > 0) {
									view.setVisibility(View.GONE);

								} else {
									view.setVisibility(View.VISIBLE);
								}

							}
						}
					} catch (Exception e) {
						cancelLoadingDialog();
						e.printStackTrace();
						ToastUtil.show(PingJiaActivity.this, "获取失败");
					}
				}

				@Override
				public void error(Throwable e) {
					super.error(e);
					e.printStackTrace();
					ToastUtil.show(PingJiaActivity.this, "获取失败");
				}

				@Override
				public void complete() {
					cancelLoadingDialog();
					pullListview.onRefreshComplete();
					if (size == 0)
						setIsMore(false);
					else
						setIsMore(date.size() % 10 == 0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		PullTool.PullListViewUtils.setPullDownTime(getApplicationContext(), refreshView);
		doRefresh();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 让下拉刷新恢复原状
				pullListview.onRefreshComplete();
			}
		}, 1000);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (date.size() % 10 != 0)
			doNextPage();
		else {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// 让下拉刷新恢复原状
					pullListview.onRefreshComplete();
				}
			}, 1000);
		}
	}
}
