package cn.com.easytaxi.util;

import java.util.concurrent.Callable;

import org.json.JSONObject;

import cn.com.easytaxi.client.channel.TcpClient;
import cn.com.easytaxi.client.common.MsgConst;
import cn.com.easytaxi.ui.adapter.LoadCallback;


/**
 * tcp请求
 * 
 * @author 62568_000
 * 
 */
public class XTCPUtil {
	public static void send(final JSONObject param, final XNetCallback callback) {
		send(1L, param, callback);
	}

	/**
	 * 发送网络请求
	 * 
	 * @param param
	 * @param callback
	 */
	public static void send(final long id, final JSONObject param, final XNetCallback callback) {
	
		AsyncUtil.goAsync(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return new String(TcpClient.send(id,MsgConst.MSG_TCP_ACTION ,param.toString().getBytes("utf-8")));
			}
		}, new LoadCallback<String>() {
			@Override
			public void onStart() {
				if (callback != null)
					callback.onStart();
			}

			@Override
			public void handle(String param) {
				SysDeug.logD("");
				if (callback != null)
					callback.onSuc(param);
			}

			@Override
			public void error(Throwable e, int errorcode) {
				super.error(e, errorcode);
				if (callback != null)
					callback.error(e, errorcode);
			}

			@Override
			public void complete() {
				super.complete();
				if (callback != null)
					callback.onComplete();
			}
		});
	}

	public interface XNetCallback {
		void onStart();

		void onSuc(String result);

		void onComplete();

		void error(Throwable e, int errorcode);
	}

	public static abstract class SimpleTcpCallback implements XNetCallback {

		@Override
		public void onStart() {

		}

		@Override
		public void onComplete() {

		}

		@Override
		public void error(Throwable e, int errorcode) {

		}

	}

}
