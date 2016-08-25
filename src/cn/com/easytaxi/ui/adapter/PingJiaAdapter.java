package cn.com.easytaxi.ui.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.easytaxi.ETApp;
import cn.com.easytaxi.ui.bean.PinglunBean;

import com.aiton.yc.client.R;
import com.xc.lib.xutils.BitmapUtils;

public class PingJiaAdapter extends BaseAdapter {
	List<PinglunBean> datas;;
	Context context;
	private BitmapUtils bitmap;

	public PingJiaAdapter(Context context, List<PinglunBean> datas) {
		this.context = context;
		this.datas = datas;
		bitmap = new BitmapUtils(context, ETApp.getmSdcardAppDir() + "/cache").configDefaultLoadFailedImage(R.drawable.siji_head).configDefaultLoadingImage(R.drawable.siji_head);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.pingjia_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.fen = (TextView) convertView.findViewById(R.id.fenzhi);
			holder.head = (ImageView) convertView.findViewById(R.id.head);
			holder.star1 = (ImageView) convertView.findViewById(R.id.star1);
			holder.star2 = (ImageView) convertView.findViewById(R.id.star2);
			holder.star3 = (ImageView) convertView.findViewById(R.id.star3);
			holder.star4 = (ImageView) convertView.findViewById(R.id.star4);
			holder.star5 = (ImageView) convertView.findViewById(R.id.star5);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PinglunBean bean = datas.get(position);
		if (TextUtils.isEmpty(bean.getAvatar_image())) {
			holder.head.setImageResource(R.drawable.siji_head);
		} else {
			bitmap.display(holder.head, bean.getAvatar_image());
		}
		holder.name.setText(bean.getDriver_name());
		holder.time.setText(bean.getAssess_time());
		holder.fen.setText(bean.getPlResult() + ".0");
		switch (Integer.parseInt((bean.getPlResult() + "").substring(0, 1))) {
		case 0:
			holder.star1.setImageResource(R.drawable.star_up);
			holder.star2.setImageResource(R.drawable.star_up);
			holder.star3.setImageResource(R.drawable.star_up);
			holder.star4.setImageResource(R.drawable.star_up);
			holder.star5.setImageResource(R.drawable.star_up);
			break;
		case 1:
			holder.star1.setImageResource(R.drawable.star_down);
			holder.star2.setImageResource(R.drawable.star_up);
			holder.star3.setImageResource(R.drawable.star_up);
			holder.star4.setImageResource(R.drawable.star_up);
			holder.star5.setImageResource(R.drawable.star_up);
			break;
		case 2:
			holder.star1.setImageResource(R.drawable.star_down);
			holder.star2.setImageResource(R.drawable.star_down);
			holder.star3.setImageResource(R.drawable.star_up);
			holder.star4.setImageResource(R.drawable.star_up);
			holder.star5.setImageResource(R.drawable.star_up);
			break;
		case 3:
			holder.star1.setImageResource(R.drawable.star_down);
			holder.star2.setImageResource(R.drawable.star_down);
			holder.star3.setImageResource(R.drawable.star_down);
			holder.star4.setImageResource(R.drawable.star_up);
			holder.star5.setImageResource(R.drawable.star_up);
			break;
		case 4:
			holder.star1.setImageResource(R.drawable.star_down);
			holder.star2.setImageResource(R.drawable.star_down);
			holder.star3.setImageResource(R.drawable.star_down);
			holder.star4.setImageResource(R.drawable.star_down);
			holder.star5.setImageResource(R.drawable.star_up);
			break;
		case 5:
			holder.star1.setImageResource(R.drawable.star_down);
			holder.star2.setImageResource(R.drawable.star_down);
			holder.star3.setImageResource(R.drawable.star_down);
			holder.star4.setImageResource(R.drawable.star_down);
			holder.star5.setImageResource(R.drawable.star_down);
			break;

		default:
			break;
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView name;
		private TextView time;
		private TextView fen;
		private ImageView head;
		private ImageView star1;
		private ImageView star2;
		private ImageView star3;
		private ImageView star4;
		private ImageView star5;

	}
}
