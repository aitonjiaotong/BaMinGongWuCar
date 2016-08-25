package cn.com.easytaxi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import com.xc.lib.imageloader.ImageLoader;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	public Context context;
	public List<T> datas;
	public LayoutInflater inflater;
	public ImageLoader imageloder;// 图片加载器，可以加载本地和网络图片
	public MyBaseAdapter(Context context) {
		this(context, null);
	}

	public MyBaseAdapter(Context context, List<T> datas) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		setDatas(datas);
	}
	
	public void initLoader(){
		if(imageloder == null)
			imageloder = new ImageLoader(ETApp.getmSdcardAppDir() + "/cache");
	}
	
	public void setDatas(List<T> datas) {
		if (datas == null)
			datas = new ArrayList<T>();
		this.datas = datas;
	}
	
	public void addItem(T item){
		this.datas.add(item);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View inflater(int layout) {
		return inflater.inflate(layout, null);
	}

}
