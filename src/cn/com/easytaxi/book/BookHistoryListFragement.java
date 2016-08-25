package cn.com.easytaxi.book;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.AppLog;
import cn.com.easytaxi.BookConfig;
import cn.com.easytaxi.book.adapter.BookListAdapter;
import cn.com.easytaxi.common.Callback;
import cn.com.easytaxi.common.SocketUtil;
import cn.com.easytaxi.common.Window;
import cn.com.easytaxi.platform.MainActivityNew;
import cn.com.easytaxi.util.PullTool;
import cn.com.easytaxi.util.ToastUtil;

import com.aiton.yc.client.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 订单列表
 */
public class BookHistoryListFragement extends BookHistoryBaseFragement
		implements OnClickListener, OnRefreshListener2<ListView> {

	// View countLayout;
	private TextView bookCount;
	// TextView cityBookCount;
	private PullToRefreshListView bookList;
	private BookListAdapter adapter;
	private View pubishBut;
	// private TitleBar bar;
	// 0 = init , 1 = refresh , 2 = loadmore
	private int action = 0;
	private String passengerId;
	private View unRegister;
	private View footerView;
	private TextView footTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.book_list, container, false);
		initViews(view);
		initDatas();
		initListner();
		// 注册刷新广播
		registReceiver();
		// startLoopTime();
		AppLog.LogD(" BookListFragement onCreateView --------------");
		return view;
	}

	public void loadData() {

	}

	@Override
	public void onResume() {
		super.onResume();
		// if (adapter != null) {
		// adapter.notifyDataSetChanged();
		// }
		// getAPage(0);
		onRefresh();
	}

	public void callDriver(final String phones) {
		try {
			Window.callTaxi(bookParent, phones);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initViews(View view) {
		// countLayout = findViewById(R.id.book_count_layout);
		bookCount = (TextView) view.findViewById(R.id.book_count);
		String subString = "0";
		String txt = bookParent.getString(R.string.book_count, subString);
		bookCount.setText(
				BookUtil.getSpecialText(txt, subString, bookParent.getResources().getColor(R.color.yellow_state)));
		// cityBookCount = (TextView) findViewById(R.id.book_city_count);
		footerView = LayoutInflater.from(bookParent).inflate(R.layout.listview_footer_layout, null);
		footerView.setVisibility(View.GONE);
		footTv = (TextView) footerView.findViewById(R.id.listview_footer);
		bookList = (PullToRefreshListView) view.findViewById(R.id.book_list);
		bookList.getRefreshableView().addFooterView(footerView);
		bookList.getRefreshableView().setHeaderDividersEnabled(false);
		bookList.getRefreshableView().setFooterDividersEnabled(false);
		bookList.setMode(Mode.PULL_FROM_START);
		PullTool.PullListViewUtils.setPullListViewParams(bookList);
		bookList.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
		bookList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				System.out.println("index -> " + arg2);
				Log.e("index", "index -> " + arg2 + "size---"+datas.size());
				if (arg2 == datas.size()+1) {
					Log.e("order", "------------------no");
					ToastUtil.show(bookParent, "没有更多订单了");
				} else {
					BookBean book = (BookBean) datas.get(arg2 - 1);
					Log.e("order", "------------------yes");
					if (book.getBookFlags() > 0) {
						Intent intent = new Intent(BookHistoryListFragement.this.getActivity(), NewBookDetail.class);
						intent.putExtra("book", book);
						BookHistoryListFragement.this.getActivity().startActivity(intent);
					} else {
						ToastUtil.show(bookParent, "该订单没有详情");
					}
				}
			}
		});

		pubishBut = view.findViewById(R.id.book_list_publish);
		unRegister = view.findViewById(R.id.un_reg);
	}

	private void initDatas() {
		adapter = new BookListAdapter(bookParent, datas);
		adapter.setAlertView(unRegister);
		bookList.setAdapter(adapter);

		if (!StringUtils.isEmpty(getPassengerId())) {
			// count = START_ID;
			// bookIndex = 0;
			// bookList.setVisibility(View.GONE);
			getMyCount(0);
			// getCityCount(0);
			getAPage(0);
		} else {

		}
	}

	// 获取订单列表
	private void getMyCount(final long delay) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(delay);
					JSONObject json = new JSONObject();
					json.put("action", "bookAction");
					json.put("method", "getBookSizeByPassenger");

					json.put("cityId", MainActivityNew.cityId);
					json.put("cityName", MainActivityNew.currentCityName);
					json.put("clientType", BookConfig.ClientType.CLIENT_TYPE_PASSENGER);
					Long id = Long.valueOf(getPassengerId());
					SocketUtil.getJSONObject(id, json, new Callback<JSONObject>() {
						@Override
						public void handle(JSONObject param) {
							try {
								// bookList.setVisibility(View.VISIBLE);
								// unRegister.setVisibility(View.GONE);
								String subString = param.getString("size");
								if (subString.length() >= 0) {
									adapter.notifyDataSetChanged();
									unRegister.setVisibility(View.GONE);
									// bookList.setVisibility(View.VISIBLE);
								} else {
									unRegister.setVisibility(View.VISIBLE);
									// bookList.setVisibility(View.GONE);
									// bookList.getmHeaderViewContent().setVisibility(View.GONE);
								}
								String txt = bookParent.getString(R.string.book_count, subString);
								bookCount.setText(BookUtil.getSpecialText(txt, subString,
										bookParent.getResources().getColor(R.color.yellow_state)));

							} catch (Exception e) {
								e.printStackTrace();
								String subString = "0";
								String txt = bookParent.getString(R.string.book_count, subString);
								bookCount.setText(BookUtil.getSpecialText(txt, subString,
										bookParent.getResources().getColor(R.color.yellow_state)));
							}
						}

						@Override
						public void error(Throwable e) {
							// TODO Auto-generated method stub
							super.error(e);
							String subString = "0";
							String txt = bookParent.getString(R.string.book_count, subString);
							bookCount.setText(BookUtil.getSpecialText(txt, subString,
									bookParent.getResources().getColor(R.color.yellow_state)));
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					String subString = "0";
					String txt = bookParent.getString(R.string.book_count, subString);
					bookCount.setText(BookUtil.getSpecialText(txt, subString,
							bookParent.getResources().getColor(R.color.yellow_state)));
					// LayoutParams params = (LayoutParams)
					// countLayout.getLayoutParams();
					// params.gravity=Gravity.CENTER;
					// countLayout.setLayoutParams(params);
				}
			};
		}.start();
	}

	private void initListner() {
		pubishBut.setOnClickListener(this);
		bookList.setOnRefreshListener(this);
	}

	Handler ui = new Handler();

	public void onRefresh() {

		if (!StringUtils.isEmpty(getPassengerId())) {
			// count = START_ID;
			bookIndex = 0;
			getMyCount(0);
			// getCityCount(0);
			getAPage(1);
		} else {

		}

	}

	public void onLoadMore() {
		getAPage(2);
	}

	private void setIsMore(boolean enable) {
		if (datas.size() == 0) {
			footerView.setVisibility(View.GONE);
			bookList.setMode(Mode.PULL_FROM_START);
		} else if (!enable) {
			footerView.setVisibility(View.VISIBLE);
			footTv.setText("没有更多订单了");
			bookList.setMode(Mode.PULL_FROM_START);
		} else {
			footerView.setVisibility(View.GONE);
			bookList.setMode(Mode.BOTH);
		}
	}

	public void getAPage(final int action) {
		loadPage(false, new Callback<Integer>() {
			int size = 0;

			@Override
			public void handle(Integer result) {
				this.size = result;
				adapter.notifyDataSetChanged();
			}

			@Override
			public void complete() {
				super.complete();
				bookList.onRefreshComplete();
				if (size == 0)
					setIsMore(false);
				else
					setIsMore(datas.size() % 10 == 0);
			}

			@Override
			public void error(Throwable e) {
				super.error(e);
			}
		});
	}

	// 查询出租车
	// private class SelectCityButtonListener implements View.OnClickListener {
	// public void onClick(View v) {
	// Window.selectCity(self, session, new Callback<JSONObject>() {
	// @Override
	// public void handle(JSONObject json) {
	// try {
	// session.set("_CITY_NAME", json.getString("_NAME"));
	// session.set("_CITY_ID", json.getString("_ID"));
	// Intent intent = new Intent("cn.com.easytaxi.cityswitch");
	// self.sendBroadcast(intent);
	// } catch (Throwable e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }
	// }

	public void onClick(View v) {
		// String startAddress =
		// this.getIntent().getStringExtra("startAddress");
		// Intent i = new Intent(bookParent, BookPublish.class);
		// i.putExtra("startAddress", startAddress);
		// startActivity(i);
	}

	private ReloadReceiver refreshReceiver;

	private void registReceiver() {
		refreshReceiver = new ReloadReceiver();
		IntentFilter filter = new IntentFilter("cn.com.easytaxi.book.state.changed");
		filter.addAction(Intent.ACTION_SCREEN_ON);
		bookParent.registerReceiver(refreshReceiver, filter);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		stopLoopTime();
		AppLog.LogD(" BookListFragement onDestroy --------------");
		if (refreshReceiver != null) {
			bookParent.unregisterReceiver(refreshReceiver);
			refreshReceiver = null;
		}

		enableLoadMore = true;
		// count = 0;
		bookIndex = 0;
		datas.clear();// 这里如果清空了datas,则对列表的缓存就没有用了
		super.onDestroy();
	}

	@Override
	public void onTimeChange() {
		adapter.notifyDataSetChanged();
	}

	private class ReloadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			onRefresh();
		}

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		onRefresh();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		PullTool.PullListViewUtils.setPullDownTime(getActivity(), refreshView);
		onLoadMore();
	}
}
