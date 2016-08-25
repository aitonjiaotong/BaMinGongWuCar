package cn.com.easytaxi.util;

public class ImTools {
	public static String getValueRemoveNull(String value) {
		if (value == null || "null".equals(value) || "NULL".equals(value)) {
			return "";
		}
		return value;
	}
}
