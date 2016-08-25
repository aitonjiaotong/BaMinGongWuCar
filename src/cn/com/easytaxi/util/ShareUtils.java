package cn.com.easytaxi.util;

import com.aiton.yc.client.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ShareUtils {
	private Context context;

	public ShareUtils(Context context) {
		this.context = context;
	}

	public static void showShare(Context context) {

		final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.QQ);
		// 要分享的文字内容
		String mShareContent = "遇见八闽,风雨同行 " + "http://www.bmcxfj.com";
		mController.setShareContent(mShareContent);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		UMImage mUMImgBitmap = new UMImage(context, bitmap);
		mController.setShareImage(mUMImgBitmap);
		mController.setAppWebSite(""); // 设置应用地址

		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//		String appID = "wxe0bda30d7272956e";
//		String appSecret = "469c865e8a4ca931664c614bb54cce86";
		String appID = "wx349c8646bb5f1809";
		String appSecret = "4a95ee6a296dba2b2c27dbbce7b1ff68";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// 设置微信好友分享内容
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置分享文字
		weixinContent.setShareContent(mShareContent);
		// 设置title
		weixinContent.setTitle("八闽专车");
		// 设置分享内容跳转URL
		weixinContent.setTargetUrl("http://www.bmcxfj.com");
		// 设置分享图片
		weixinContent.setShareImage(mUMImgBitmap);
		mController.setShareMedia(weixinContent);

		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(mShareContent);
		// 设置朋友圈title
		circleMedia.setTitle("八闽专车");
		circleMedia.setShareImage(mUMImgBitmap);
		circleMedia.setTargetUrl("http://www.bmcxfj.com");
		mController.setShareMedia(circleMedia);

		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		SmsShareContent content = new SmsShareContent();
		content.setShareContent(mShareContent);
		smsHandler.addToSocialSDK();

		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1105371321", "m6ILTAKR40fqekHp");
		qZoneSsoHandler.addToSocialSDK();

		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent(mShareContent);
		// 设置点击消息的跳转URL
		qzone.setTargetUrl("http://www.bmcxfj.com");
		// 设置分享内容的标题
		qzone.setTitle("八闽专车");
		qzone.setShareContent("遇见八闽,风雨同行 " + "http://www.bmcxfj.com");
		// 设置分享图片
		qzone.setShareImage(mUMImgBitmap);
		mController.setShareMedia(qzone);
		mController.openShare((Activity) context, false);

	}

}
