package passenger.constant;

/**
 * Created by Administrator on 2016/3/14.
 */
public class Constant {
	 public static final String HOST ="http://120.24.46.15:8010";//艾通后台服务器HOST地址
	// public static final String HOST ="http://www.bmcxfj.com:8010";//八闽后台服务器HOST地址
//	 public static final String HOST = "http://192.168.1.163:8080";//本地服务器
//	public static final String HOST = "http://192.168.1.124:8080";// 本地服务器
	public static final int ABLEVERSION = 2;// 可用版本号

	public class URL {
		// 获取广告条图片地址
		public static final String GET_BANNER_IMG = HOST + "/aiton-app-webapp/picture/getPictures";
		// 获取短信
		public static final String GET_SMS = HOST + "/aiton-app-webapp/public/sendmessage";
		// 检测软件升级-八闽云服务地址
		public static final String UP_GRADE = "http://bmcx.oss-cn-shanghai.aliyuncs.com/bmzc/upgrade_bmzc.txt";
		// 取车城市列表接口 传入参数:page;默认值:0
		public static final String CITY_LIST = HOST + "/aiton-app-webapp/zc/store/loadcities";
		// 机构认证服务接口 传入参数:机构编号String code,密码String password;返回值:true和false
		public static final String DACHEZUCHE_COMFIRE_UNIT_INFO = HOST
				+ "/aiton-app-webapp/zc/institutions/checkinstitutions";
		// 根据车辆不同类型加载该类型下所有车辆的信息
		// 传入参数:租车时间long zuchuDate,计划还车时间:long planReturnDate,车辆类型:int
		// lei,加载页码:page=0；车辆类型: 0:公务车 1：商务车 2：执法车 3：越野车 4：皮卡 5：客车
		public static final String GET_CAR_LIST = HOST + "/aiton-app-webapp/zc/car/loadcarsbylei";
		// 获取门店地址Marker显示于地图上 传入String city，城市名
		public static final String GET_STORES_MARKERS_LATLNG = HOST + "/aiton-app-webapp/zc/store/loadstorebycity";
		// 机构租车提交订单 Double discount;//折扣（若无折扣默认传1.0） Integer
		// privilegeId;//优惠活动ID
		public static final String COMMIT_ZHUCHE_ORDER = HOST + "/aiton-app-webapp/zc/order/institutions/addorder";
		// 企业用车查询订单列表 Integer account_id,Integer page
		public static final String GET_ORDER_LIST_INSTITUTIONS = HOST
				+ "/aiton-app-webapp/zc/order/institutions/loadbyaccount";
		// 取消订单列表 传入order_id
		public static final String CANCEL_ORDER = HOST + "/aiton-app-webapp/zc/order/cancelorder";
		// 查询订单详情
		public static final String QUERY_ORDER_DETAIL = HOST + "/aiton-app-webapp/zc/order/details";
		// 作用：根据电话列出所有下过单的机构号 传入数据：String phone
		public static final String ORGANIZATION_CODE_LIST = HOST + "/aiton-app-webapp/zc/institutions/loadcodebyphone";
		// 新增接口：/zc/order/institutions/loadbycode 传入数据：String
		// institutionsCode,String start,String end 作用：根据机构号列出机构号下的订单列表
		public static final String SCREENING_ORDER_LIST = HOST + "/aiton-app-webapp/zc/order/institutions/loadbycode";
		// 用户指南
		public static final String USER_GUID = "http://www.bmcxfj.com/zc/guide.html";
		// 法律条款
		public static final String LAW_PROVISIONS = "http://www.bmcxfj.com/zc/law.html";
		// 加载二维码图片地址
		public static final String CLI_SCAN = "http://bmcx.oss-cn-shanghai.aliyuncs.com/cliscan/android/bmcx_call_car_passenger.png";
		// 加载油价
		public static final String OIL_PRICE = HOST + "/aiton-app-webapp/zc/oil/price/today";
		// 向后台服务推送用户短信验证成功，发送参数 phone 手机号 + login_id 设备号 + flag 应用标识
		public static final String SMS_LOGIN_SUCCESS = HOST + "/aiton-app-webapp/login";
		// 向后台服务验证用户账号及密码登录是否成功 参数 phone 手机号 + login_id 设备号 + password 密码
		public static final String PASSWORD_LOGIN_SUCCESS = HOST + "/aiton-app-webapp/login";
		// 向后台注册账号 参数 phone 手机号 + login_id 设备号 + password 密码
		public static final String REGISTERED = HOST + "/aiton-app-webapp/registeredbypassword";
		// 向后台服务验证更改密码 参数 phone 手机号 + login_id 设备号 + password 密码
		public static final String CHANGE_PASSWORD = HOST + "/aiton-app-webapp/updatepassword";
		// 检查当时登录账号是否在其它设备上登录 参数 account_id
		// public static final String FIND_LONGIN_ID = HOST +
		// "/aiton-app-webapp/loadloginid";
		// 检测服务端是否存活
		public static final String SERVER_IS_LIVE = HOST + "/aiton-app-webapp/check/live";
		// 上传用户头像
		public static final String UPLOAD_USER_AVATAR = HOST + "/aiton-app-webapp/account/updateIcon";
		// 上传用户身份证正面
		public static final String UPLOAD_SFZZM = HOST + "/aiton-app-webapp/account/updateidcardimage";
		// 上传用户身份证反面
		public static final String UPLOAD_SFZFM = HOST + "/aiton-app-webapp/account/updateidcardimage_back";
		// 上传用户驾驶证正面
		public static final String UPLOAD_JSZZM = HOST + "/aiton-app-webapp/account/updatedrivinglicenseimage";
		// 上传用户驾驶证反面
		public static final String UPLOAD_JSZFM = HOST + "/aiton-app-webapp/account/updatedrivinglicenseimage_back";
		// 优惠活动比对 传入参数：String start，String end 格式为yyyy-MM-dd
		public static final String MATCHING_DISCOUNT = HOST + "/aiton-app-webapp/zc/privilege/find";
		// 将打车订单号还有机构号传给艾通后台
		public static final String COMMIT_DACHE_ORDER_JIGOUHAO = HOST + "/aiton-app-webapp/taxi/addOrderInstitutions";
		// 获取支付宝签名
		public static final String GETSIGN = HOST + "/alipay/getsign";
	}

	public class RequestCode {
		// 机构租车选取车城市
		public static final int JIGOUZUCHE_TAKE_CAR_CITY = 1;
		// 机构租车选还车城市
		public static final int JIGOUZUCHE_RETURN_CAR_CITY = 8;
		// 取车门店地图
		public static final int JIGOUZUCHE_TAKE_CAR_MAP = 3;
		// 还车门店地图
		public static final int JIGOUZUCHE_RETURN_CAR_MAP = 4;
		// 用户自主选择当前所在城市的请求码
		public static final int CHOOSE_CITY_REQUEST_CODE = 0;
	}

	public class ResultCode {
		// 选城市
		public static final int CHOOSE_CITY = 0;
		// 选门店
		public static final int CHOOSE_STORE = 2;
		// 用户自主选择当前所在城市的返回码
		public static final int CHOOSE_CITY_RESULT_CODE = 4;
		// 选择上门取车及送车上门的返回码
		public static final int MAP_FOR_CHOOSE_ADDR_RESULT_CODE = 5;
	}

	public class PermissionRequestAndResultCode {
		public static final int PERMISSION_READ_SMS = 1;
	}

	public class IntentKey {

		// 机构用车提交订单后返回到订单列表的KEY
		public static final String BACK_TO_ORDER_LIST_KEY = "backToOrderList";
		// 机构用车提交订单后返回到订单列表的Intent值
		public static final int ORG_BACK_INT_VALUE = 11;
		// 跳转到门店地图_取车城市的KEY
		public static final String CITY = "city_name";
		// 为区分还车门店还是取车门店
		public static final String GET_MAP_LOC_KEY = "get_map_loc";
		// 跳转到门店地图_取车的KEY
		public static final int GET_MAP_LOC_GET = 1;
		// 跳转到门店地图_还车的KEY
		public static final int GET_MAP_LOC_RETURN = 2;
		// 选择城市地区的KEY
		public static final String CHOOSE_CITY = "choose_city";
		// 选择城市地区的pisotion
		public static final String CHOOSE_CITY_POSITION = "choose_city_position";
		// 取车门店返回值的KEY
		public static final String STORES_MAP_KEY = "stores_map_marker_title";
		// 第一次选择时要传递的对象值的KEY
		public static final String CHOOSE_FRIST_INFO = "choose_frist_info";
		// 门店ID的KEY
		public static final String STORES_ID_KEY = "storesId";
		// 用户自主选择当前所在城市的返回值的Intent所需的KEY
		public static final String CHOOSE_CITY_INTENT_KEY = "chooseCityNameKey";
		// 用户自主选择当前所在城市的返回值的Intent所需的KEY
		public static final String CHOOSE_CITY_PROVINCE_INTENT_KEY = "chooseCityProvinceName";
		// 选择上门取车或送车上门的KEY
		public static final String MAP_FOR_CHOOSE_ADDR = "mapForChooseAddr";
		// 优惠折扣
		public static final String PRIVILEGE_EVENT = "privilegeEvent";
	}

	public class AcacheKey {
		public static final String JIGOUMINGCHRNG = "jigoumingcheng";
		public static final String JIGOUHAO = "jigouhao";
		public static final String JIGOUMIMA = "jigoumima";
		public static final String PAIDANYUAN = "paidanyuan";
		public static final String USER_ZHUCHE = "userZhuChe";
		public static final String USER_DACHE = "userDaChe";
	}
}
