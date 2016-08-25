package cn.com.easytaxi.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Utils {

	public static String getDecimal(String pattern,boolean is45,double value){
		DecimalFormat df2 = new DecimalFormat(pattern);
		df2.setRoundingMode(RoundingMode.DOWN);
		return df2.format(value);
	}
}
