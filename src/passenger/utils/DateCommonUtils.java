package passenger.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
public class DateCommonUtils
{

    public static final int YYYY_MM_DD = 1;
    public static final int YYYY_MM_DD_EE_HH_MM = 2;
    public static final int YYYY_MM_DD_EE_HH_MM_SS = 3;
    public static final int MM_DD = 4;
    public static final int EE = 5;
    public static final int HH_MM = 6;
    public static final int EE_HH_MM = 7;
    public static final int YYYY_MM_DD_HH_MM = 8;


    public static long getCurrentTimeMillis()
    {
        long time = System.currentTimeMillis();
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(time);
        long timeMill = 0;
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH) + 1;
        int dayOfMonth = calender.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calender.get(Calendar.HOUR_OF_DAY);
        int minute = calender.get(Calendar.MINUTE);
        if (minute > 0 && minute <= 15)
        {
            minute = 15;
        } else if (minute > 15 && minute <= 30)
        {
            minute = 30;
        } else if (minute > 30 && minute <= 45)
        {
            minute = 45;
        } else
        {
            minute = 0;
            hourOfDay++;
        }
        String currTimeStr = year + "-" + month + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;
        try
        {
            timeMill = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(currTimeStr).getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return timeMill;

    }

    /**
     * 获取当前毫秒数
     *
     * @return
     */
//    public static long getCurrentTimeMillis()
//    {
//        long currentTimeMillis = System.currentTimeMillis();
//        String theDateToString = getTheDateToString(currentTimeMillis, YYYY_MM_DD_EE_HH_MM);
//        long millionSeconds = 0;//毫秒
//        try
//        {
//            millionSeconds = new SimpleDateFormat("yyyy-MM-dd EE HH:mm").parse(theDateToString).getTime();
//
//        } catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//        return millionSeconds;
//    }

    /**
     * 获取今天的日期
     * 返回值"yyyy-MM-dd 00:00:00"字符串  转换时间格式 "yy-MM-dd EE HH:mm:ss"
     * yy-MM-dd EE HH:mm:ss
     */
    public static String getTheDateToString(long timeMillis, int whatStr)
    {
        String dateStr = "";
        SimpleDateFormat sd;
        switch (whatStr)
        {
            case YYYY_MM_DD_HH_MM:
                sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateStr = sd.format(timeMillis);
                break;
            case YYYY_MM_DD:
                sd = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = sd.format(timeMillis);
                break;
            case YYYY_MM_DD_EE_HH_MM:
                sd = new SimpleDateFormat("yyyy-MM-dd EE HH:mm");
                dateStr = sd.format(timeMillis);
                break;
            case YYYY_MM_DD_EE_HH_MM_SS:
                sd = new SimpleDateFormat("yyyy-MM-dd EE HH:mm:ss");
                dateStr = sd.format(timeMillis);
                break;
            case MM_DD:
                sd = new SimpleDateFormat("MM-dd");
                dateStr = sd.format(timeMillis);
                break;
            case EE:
                sd = new SimpleDateFormat("EE");
                dateStr = sd.format(timeMillis);
                break;
            case HH_MM:
                sd = new SimpleDateFormat("HH:mm");
                dateStr = sd.format(timeMillis);
                break;
            case EE_HH_MM:
                sd = new SimpleDateFormat("EE HH:mm");
                dateStr = sd.format(timeMillis);
                break;
            default:
                sd = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                dateStr = sd.format(timeMillis);
                break;
        }
        return dateStr;
    }

    /**
     * 获取本星期的日期段
     */
    public static Map getWeekDay(int whatStr)
    {
        Map<String, String> map = new HashMap<String, String>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df;
        if (whatStr == YYYY_MM_DD)
        {
            df = new SimpleDateFormat("yyyy-MM-dd");
        } else
        {
            df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        String weekOfMonDay = df.format(cal.getTime());//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        map.put("mon", weekOfMonDay);

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        String weekOfSunDay = df.format(cal.getTime());
        map.put("sun", weekOfSunDay);//获取本周日的日期
        return map;
    }

    /**
     * 获取本月的日期段
     */
    public static Map getMonthDate(int whatStr)
    {
        Map<String, String> map = new HashMap<String, String>();
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format;
        if (whatStr == YYYY_MM_DD)
        {
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else
        {
            format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        }

        // 设置时间,当前时间不用设置
        // calendar.setTime(new Date());

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        String minMonthDate = format.format(calendar.getTime());//本月的最小日期
        map.put("monthF", minMonthDate);


        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        String maxMonthDate = format.format(calendar.getTime());//本月的最大日期
        map.put("monthL", maxMonthDate);
        return map;
    }


    /**
     * 获取上月的日期段
     */
    public static Map getPreMonthDay(int whatStr)
    {
        Map<String, String> map = new HashMap<String, String>();
        // 获取Calendar
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format;
        if (whatStr == YYYY_MM_DD)
        {
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else
        {
            format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        }
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String preMonthF = format.format(calendar.getTime());
        map.put("preMonthF", preMonthF);


        // 设置日期为本月最大日期
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        String preMonthL = format.format(calendar.getTime());//本月的最大日期
        map.put("preMonthL", preMonthL);
        return map;
    }


    public static Double getHowLongDay(long millisS, long millisE)
    {
        Double hours = (millisE - millisS) / (1000 * 60 * 60.0);
        hours = Math.ceil(hours);
        double days = Math.floor(hours / 24.0);
        if (hours.intValue() % 24 != 0)
        {
            if (hours.intValue() % 24 <= 4)
            {
                days += 0.5;
            } else
            {
                days += 1.0;
            }

        }
        return days;
    }


    //默认除法运算精度
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算。
     *
     * @param v1
     * @param v2
     * @return 两个参数的和
     */

    public static double add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static long add(long v1, long v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).longValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1
     * @param v2
     * @return 两个参数数学加和，以字符串格式返回
     */
    public static String add(String v1, String v2)
    {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1
     * @param v2
     * @return 两个参数的差
     */
    public static double subtract(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static long subtract(long v1, long v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).longValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1
     * @param v2
     * @return 两个参数数学差，以字符串格式返回
     */
    public static String subtract(String v1, String v2)
    {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }


    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     * @param v2
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static long multiply(long v1, long v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).longValue();
    }


    /**
     * 提供精确的乘法运算
     *
     * @param v1
     * @param v2
     * @return 两个参数的数学积，以字符串格式返回
     */
    public static String multiply(String v1, String v2)
    {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     *
     * @param v1
     * @param v2
     * @return 两个参数的商
     */
    public static double divide(long v1, long v2)
    {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     *
     * @param v1
     * @param v2
     * @param scale 表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(long v1, long v2, int scale)
    {
        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param v1
     * @param v2
     * @param scale      表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商
     */
    public static long divide(long v1, long v2, int scale, int round_mode)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, round_mode).longValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     *
     * @param v1
     * @param v2
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2)
    {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     *
     * @param v1
     * @param v2
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2, int scale)
    {
        return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param v1
     * @param v2
     * @param scale      表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商，以字符串格式返回
     */
    public static String divide(String v1, String v2, int scale, int round_mode)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, round_mode).toString();
    }

    /**
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale)
    {
        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v          需要四舍五入的数字
     * @param scale      小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale, int round_mode)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.setScale(scale, round_mode).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale)
    {
        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v          需要四舍五入的数字
     * @param scale      小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale, int round_mode)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, round_mode).toString();
    }

}
