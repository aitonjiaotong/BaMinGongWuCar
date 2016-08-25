package cn.com.easytaxi.dialog;

import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.easytaxi.MyBaseAdapter;
import cn.com.easytaxi.util.BitmapRadiusUtil;

import com.aiton.yc.client.R;
import com.xc.lib.layout.ViewHolder;
import com.xc.lib.utils.Tools;

public class ListSelectDialog extends Dialog implements OnItemClickListener {
	private ListView listview;
	private TextView title;
	private Button sure;
	private Button cancel;
	private MyAdapter adapter;
	private SelectListener listener;

	public ListSelectDialog(Context context, int theme) {
		super(context, theme);
		initview();
		setCanceledOnTouchOutside(false);
	}

	public void canDisEnable(boolean enable) {
		if (!enable)
			cancel.setVisibility(View.GONE);
		else
			cancel.setVisibility(View.VISIBLE);
	}

	public static ListSelectDialog newInstance(Context context) {
		return new ListSelectDialog(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
	}

	private void initview() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.dialog_list_view, null);
		title = (TextView) view.findViewById(R.id.title);
		listview = (ListView) view.findViewById(R.id.listview);
		sure = (Button) view.findViewById(R.id.sure);
		cancel = (Button) view.findViewById(R.id.cancel);
		adapter = new MyAdapter(getContext());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);
		makeNotFullScreen(this);
		setContentView(view);

		Tools.getViewSize(sure, new Tools.ViewSizeListener() {

			@Override
			public void onSize(int arg0, int arg1) {
//				sure.setBackground(BitmapRadiusUtil.createBg(1, arg1 / 2, Color.TRANSPARENT, Color.parseColor("#FFB935"), null));
//				cancel.setBackground(BitmapRadiusUtil.createBg(1, arg1 / 2, Color.TRANSPARENT, Color.parseColor("#FFB935"), null));
				sure.setBackground(BitmapRadiusUtil.createBg(1, arg1 / 2, Color.TRANSPARENT, Color.parseColor("#ff9704"), null));
				cancel.setBackground(BitmapRadiusUtil.createBg(1, arg1 / 2, Color.TRANSPARENT, Color.parseColor("#DDDDDD"), null));
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (listener != null)
					listener.onSure(flag, adapter.getTag(), adapter.getValue());
			}
		});
	}

	public static void makeNotFullScreen(Dialog dialog) {
		android.view.Window window = dialog.getWindow();
		WindowManager.LayoutParams wmLayoutParams = window.getAttributes();
		// 获取屏幕宽、高用
		Display d = ((WindowManager) (dialog.getContext().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
		if (dialog.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			wmLayoutParams.width = (int) (d.getWidth() * 0.92); // 竖屏时，宽度设置为屏幕的0.92
		} else {
			wmLayoutParams.width = (int) (d.getWidth() * 0.6); // 横屏时，宽度设置为屏幕的0.6
		}
		// wmLayoutParams.gravity = Gravity.BOTTOM | Gravity.FILL_HORIZONTAL;
		window.setAttributes(wmLayoutParams);
	}

	public void setSelectListener(SelectListener listener) {
		this.listener = listener;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setDatas(List<String> datas, int tag) {
		adapter.setDatas(datas);
		adapter.setTag(tag);
		adapter.notifyDataSetChanged();
	}

	public void setDatas(String[] params, int tag) {
		setDatas(Arrays.asList(params), tag);
	}

	public int flag;

	public void show(int flag) {
		super.show();
		this.flag = flag;
	}

	public interface SelectListener {
		void onSelect(int flag, int tag);

		void onSure(int flag, int tag, String value);
	}

	class MyAdapter extends MyBaseAdapter<String> {

		public int tag = 0;

		public MyAdapter(Context context) {
			super(context);
		}

		public void setTag(int tag) {
			this.tag = tag;
		}

		public int getTag() {
			return tag;
		}

		public String getValue() {
			return getItem(tag);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			TextView content;
			if (convertView == null)
				convertView = inflater(R.layout.normal_list_item);
			view = ViewHolder.getView(convertView, R.id.right_icon);
			content = ViewHolder.getView(convertView, R.id.content);
			if (tag == position)
				view.setBackgroundResource(R.drawable.checkon_c);
			else
				view.setBackgroundResource(R.drawable.check_c);
			content.setText(getItem(position));
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		adapter.setTag(arg2);
		adapter.notifyDataSetChanged();
		if (listener != null)
			listener.onSelect(flag, arg2);
	}

}
