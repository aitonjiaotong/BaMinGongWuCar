package cn.com.easytaxi.onetaxi;

import java.util.ArrayList;
import java.util.List;

import com.aiton.yc.client.R;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CityAdapter extends BaseAdapter {
	private List<CityBean> dataList;
	private Context context;

	public CityAdapter(List<CityBean> dataList, Context context) {
		this.dataList = dataList;
		this.context = context;
	}

	class ViewHolder {
		TextView textview;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.base_item, null);
			holder = new ViewHolder();
			holder.textview = (TextView) convertView.findViewById(R.id.base_item_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		bindViewData(position, holder);
		return convertView;
	}

	private void bindViewData(int position, ViewHolder holder) {
		holder.textview.setText(dataList.get(position).getCity().replace("市", ""));
	}
}
