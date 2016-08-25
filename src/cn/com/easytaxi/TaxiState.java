package cn.com.easytaxi;

import cn.com.easytaxi.common.Config;

public class TaxiState {

	public static class Driver {
		/**
		 * 注册城市id
		 */
		public static Integer cityId = Config.default_city.cityId;

		public static String cityName = Config.default_city.cityName;
		/**
		 * 司机id 也就是手机号码
		 */
		public static Long id;
		/**
		 * 司机出车状态 1,出车赚钱，2收车休息
		 */
		public static int state = 2;

	};

}
