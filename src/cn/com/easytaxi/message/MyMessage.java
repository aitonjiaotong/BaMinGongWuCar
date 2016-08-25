package cn.com.easytaxi.message;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.com.easytaxi.common.ActionLogUtil;
import cn.com.easytaxi.common.SessionAdapter;
import cn.com.easytaxi.dialog.MyDialog;
import cn.com.easytaxi.dialog.MyDialog.SureCallback;
import cn.com.easytaxi.onetaxi.TitleBar;
import cn.com.easytaxi.ui.MoreWebviewActivity;
import cn.com.easytaxi.ui.adapter.LoadCallback;
import cn.com.easytaxi.ui.bean.MsgBean;
import cn.com.easytaxi.ui.bean.MsgData;
import cn.com.easytaxi.util.PullTool;
import cn.com.easytaxi.workpool.BaseActivity;

import com.aiton.yc.client.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xc.lib.layout.LayoutUtils;

public class MyMessage extends BaseActivity implements OnItemClickListener, OnCheckedChangeListener, OnRefreshListener2<ListView>, OnPageChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {
	private static final int LIST_PAGE_SIZE = 5;
	private TitleBar titleBar;
	private ViewPager mPager;
	private MsgViewPagerAdapter mAdapter;
	private PullToRefreshListView systemMsglistView;
	private PullToRefreshListView personalMsglistView;
	private MyMessage self;
	private RadioGroup radioGroup;

	private RadioButton systemRadio;
	private RadioButton personalRadio;

	private BadgeView systemBadge;
	private BadgeView personalBadge;

	private ArrayList<MsgBean> systemMessageList;
	private ArrayList<MsgBean> personalMessageList;

	private MyMessageAdapter systemMessageAdapter;
	private MyMessageAdapter personalMessageAdapter;

	private Context context;

	public static final String UPDATE_TIME = "msg_update_time ";

	private MsgData msgData;

	Handler handler = new Handler();

	// private static int anim_switch = 1;
	private View lineText1, lineText2;
	private View systemFoot;
	private View personalFoot;
	private TextView foot1, foot2;
	View fp1, fp2;
	/**
	 * 未读易达公告条数
	 */
	private int unreadSystemMessageSize = 0;
	/**
	 * 未读个人消息条数
	 */
	private int unreadPersonalMessageSize = 0;
	private SessionAdapter session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymsg_main);
		self = this;
		titleBar = new TitleBar(this);
		titleBar.setTitleName("我的消息");
		context = this;

		msgData = new MsgData();

		session = new SessionAdapter(context);

		// try {
		// anim_switch =
		// Integer.parseInt(session.get(Setting._ANIMATION_SWITCH));
		// } catch (Exception e) {
		// session.set(Setting._ANIMATION_SWITCH, "1");
		// }
		systemFoot = getLayoutInflater().inflate(R.layout.listview_footer_layout, null);
		personalFoot = getLayoutInflater().inflate(R.layout.listview_footer_layout, null);

		systemMsglistView = createListView();
		personalMsglistView = createListView();

		systemMsglistView.getRefreshableView().addFooterView(systemFoot, null, false);
		personalMsglistView.getRefreshableView().addFooterView(personalFoot, null, false);
		personalFoot.setVisibility(View.GONE);
		systemFoot.setVisibility(View.GONE);

		foot1 = (TextView) systemFoot.findViewById(R.id.listview_footer);

		foot2 = (TextView) personalFoot.findViewById(R.id.listview_footer);

		systemMessageList = new ArrayList<MsgBean>();
		systemMsglistView.setOnItemClickListener(this);
		personalMessageList = new ArrayList<MsgBean>();
		personalMsglistView.setOnItemClickListener(this);

		systemMessageAdapter = new MyMessageAdapter(self, systemMessageList);
		personalMessageAdapter = new MyMessageAdapter(self, personalMessageList);

		systemMsglistView.setAdapter(systemMessageAdapter);
		personalMsglistView.setAdapter(personalMessageAdapter);

		// ListView加载动画
		// if (anim_switch == 1) {
		// systemMsglistView.setLayoutAnimation(TabAdapter.getListAnim());
		// personalMsglistView.setLayoutAnimation(TabAdapter.getListAnim());
		// }

		mPager = (ViewPager) findViewById(R.id.message_pager);

		mAdapter = new MsgViewPagerAdapter(self, new View[] { systemMsglistView, personalMsglistView });

		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);

		radioGroup = (RadioGroup) findViewById(R.id.msg_group_type);
		radioGroup.setOnCheckedChangeListener(this);

		systemRadio = (RadioButton) findViewById(R.id.msg_type_system);
		personalRadio = (RadioButton) findViewById(R.id.msg_type_personal);

		systemBadge = new BadgeView(this, systemRadio);
		systemBadge.setBackgroundResource(R.drawable.red_oval_shap);
		systemBadge.setTextColor(Color.WHITE);
		systemBadge.setTextSize(16);

		personalBadge = new BadgeView(this, personalRadio);
		personalBadge.setBackgroundResource(R.drawable.red_oval_shap);
		personalBadge.setTextColor(Color.WHITE);
		personalBadge.setTextSize(16);

		systemBadge.setVisibility(View.GONE);
		personalBadge.setVisibility(View.GONE);
		systemMsglistView.setOnRefreshListener(this);
		personalMsglistView.setOnRefreshListener(this);

		radioGroup.check(R.id.msg_type_system);
		lineText1 = findViewById(R.id.line1);
		lineText2 = findViewById(R.id.line2);
		setTab(0);
		systemRadio.setOnCheckedChangeListener(this);
		personalRadio.setOnCheckedChangeListener(this);
		doRefresh(1);
		doRefresh(2);
		setRadioLayoutParams(personalBadge);
		setRadioLayoutParams(systemBadge);
		//
		// try {
		// doNextPageGg();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * 设置标点
	 * 
	 * @param badgeView
	 */
	private void setRadioLayoutParams(BadgeView badgeView) {
		badgeView.setStaticParams(LayoutUtils.getRate4px(40), LayoutUtils.getRate4px(40));
	}

	private void setTab(int tab) {
		if (lineText1 == null)
			return;
		if (tab == 0) {
			lineText1.setBackgroundColor(Color.parseColor("#FFB628"));
			systemRadio.setTextColor(Color.parseColor("#FFB628"));
			lineText2.setBackgroundColor(Color.parseColor("#E7E5E0"));
			personalRadio.setTextColor(Color.BLACK);
		} else {
			lineText2.setBackgroundColor(Color.parseColor("#FFB628"));
			personalRadio.setTextColor(Color.parseColor("#FFB628"));
			lineText1.setBackgroundColor(Color.parseColor("#E7E5E0"));
			systemRadio.setTextColor(Color.BLACK);
		}
	}

	private PullToRefreshListView createListView() {
		PullToRefreshListView list = new PullToRefreshListView(self);
		PullTool.PullListViewUtils.setPullListViewParams(list);
		list.getRefreshableView().setCacheColorHint(Color.parseColor("#00000000"));
		list.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
		list.getRefreshableView().setDivider(null);
		list.setMode(Mode.PULL_FROM_START);
		// list.getRefreshableView().setDivider(new ColorDrawable(colorGray));
		list.getRefreshableView().setFooterDividersEnabled(false);
		list.getRefreshableView().setHeaderDividersEnabled(false);
		// list.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT));
		return list;
	}

	private void setIsMore(boolean isMore, int type) {
		if (type == 1)
			systemMsglistView.onRefreshComplete();
		else
			personalMsglistView.onRefreshComplete();

		int size = (type == 1 ? systemMessageList.size() : personalMessageList.size());
		if (type == 1) {
			if (size == 0) {
				systemFoot.setVisibility(View.GONE);
				systemMsglistView.setMode(Mode.PULL_FROM_START);
			} else if (!isMore) {
				systemFoot.setVisibility(View.VISIBLE);
				foot1.setText("没有更多信息了");
				systemMsglistView.setMode(Mode.PULL_FROM_START);
			} else {
				systemFoot.setVisibility(View.GONE);
				systemMsglistView.setMode(Mode.BOTH);
			}
		} else {
			if (size == 0) {
				personalFoot.setVisibility(View.GONE);
				personalMsglistView.setMode(Mode.PULL_FROM_START);
			} else if (!isMore) {
				personalFoot.setVisibility(View.VISIBLE);
				foot2.setText("没有更多信息了");
				personalMsglistView.setMode(Mode.PULL_FROM_START);
			} else {
				personalFoot.setVisibility(View.GONE);
				personalMsglistView.setMode(Mode.BOTH);
			}
		}
	}

	protected void doRefresh(final int type) {
		msgData.getMsgList(0, getCityId(), getPassengerId(), type, new LoadCallback<List<MsgBean>>() {

			@Override
			public void handle(List<MsgBean> result) {
				if (result != null && result.size() != 0) {
					if (type == 1) {
						systemMessageList.clear();
						systemMessageList.addAll(result);
						systemMessageAdapter.notifySortDataSetChanged();
					} else {
						personalMessageList.clear();
						personalMessageList.addAll(result);
						personalMessageAdapter.notifySortDataSetChanged();
					}
					if (result.size() % 10 != 0) {
						setIsMore(false, type);
					} else {
						setIsMore(true, type);
					}

				} else {
					setIsMore(false, type);
				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void complete() {
			}
		});
	}

	protected void doNextPage(final int type) {
		int size = type == 1 ? systemMessageList.size() : personalMessageList.size();
		msgData.getMsgList(size, getCityId(), getPassengerId(), type, new LoadCallback<List<MsgBean>>() {

			@Override
			public void handle(List<MsgBean> result) {
				if (result != null && result.size() != 0) {
					if (type == 1) {
						systemMessageList.addAll(result);
						systemMessageAdapter.notifySortDataSetChanged();
					} else {
						personalMessageList.addAll(result);
						personalMessageAdapter.notifySortDataSetChanged();
					}
					if (result.size() % 10 != 0) {
						setIsMore(false, type);
					} else {
						setIsMore(true, type);
					}
				} else {
					setIsMore(false, type);
				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void complete() {
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		session.close();
		titleBar.close();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MsgBean msg = (MsgBean) parent.getItemAtPosition(position);
		if (msg == null)
			return;

		// 标记已读
		if (!msg.getRead()) {
			msg.setRead(true);
			msgData.markRead(msg.getCacheId(), null);
			if (msg.getMsgType() == 1) {// 系统
				unreadSystemMessageSize--;
			} else {
				unreadPersonalMessageSize--;
			}
		}

		systemBadge.setText("" + unreadSystemMessageSize);
		personalBadge.setText("" + unreadPersonalMessageSize);
		systemMessageAdapter.notifyDataSetChanged();
		personalMessageAdapter.notifyDataSetChanged();

		if (!TextUtils.isEmpty(msg.getUrl())) {
			Intent intent = new Intent(MyMessage.this, MoreWebviewActivity.class);
			intent.putExtra("title", "消息详情");
			intent.putExtra("uri", msg.getUrl());
			startActivity(intent);
		} else {
			MyDialog.comfirm(MyMessage.this, "信息详情", msg.getBody(), new SureCallback(), true, false, true);
			// Window.showMessageDialog(MyMessage.this, "消息详情", msg.getBody(),
			// "确定", null, null, null);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.msg_type_system) {
			mPager.setCurrentItem(0, true);
			setTab(0);
			// 写入日志
			ActionLogUtil.writeActionLog(self, R.array.more_my_sys_message, "");
		} else if (checkedId == R.id.msg_type_personal) {
			mPager.setCurrentItem(1, true);
			setTab(1);
			// 写入日志
			ActionLogUtil.writeActionLog(self, R.array.more_my_self_message, "");
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == 0) {
			radioGroup.check(R.id.msg_type_system);
		} else {
			radioGroup.check(R.id.msg_type_personal);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			radioGroup.check(buttonView.getId());
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		PullTool.PullListViewUtils.setPullDownTime(context, refreshView);
		doRefresh(refreshView == systemMsglistView ? 1 : 2);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		doNextPage(refreshView == systemMsglistView ? 1 : 2);
	}
}
