package passenger.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/28.
 */
public class PrivilegeEvent implements Serializable
{

    /**
     * id : 3
     * name : 国庆黄金周酬宾
     * type : 0
     * startDate : 1467104460000
     * endDate : 1467363660000
     * startOfMonth : 1
     * endOfMonth : 1
     * weekdays :
     * discountId : 5
     * status : 0
     */

    private PrivilegeBean privilege;
    /**
     * id : 5
     * name : 十天七折优惠
     * type : 1
     * times : 10
     * discount : 0.7
     * status : 0
     */

    private DiscountBean discount;

    public PrivilegeBean getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege(PrivilegeBean privilege)
    {
        this.privilege = privilege;
    }

    public DiscountBean getDiscount()
    {
        return discount;
    }

    public void setDiscount(DiscountBean discount)
    {
        this.discount = discount;
    }

    public static class PrivilegeBean implements Serializable
    {
        private int id;
        private String name;
        private int type;
        private long startDate;
        private long endDate;
        private int startOfMonth;
        private int endOfMonth;
        private String weekdays;
        private int discountId;
        private int status;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public long getStartDate()
        {
            return startDate;
        }

        public void setStartDate(long startDate)
        {
            this.startDate = startDate;
        }

        public long getEndDate()
        {
            return endDate;
        }

        public void setEndDate(long endDate)
        {
            this.endDate = endDate;
        }

        public int getStartOfMonth()
        {
            return startOfMonth;
        }

        public void setStartOfMonth(int startOfMonth)
        {
            this.startOfMonth = startOfMonth;
        }

        public int getEndOfMonth()
        {
            return endOfMonth;
        }

        public void setEndOfMonth(int endOfMonth)
        {
            this.endOfMonth = endOfMonth;
        }

        public String getWeekdays()
        {
            return weekdays;
        }

        public void setWeekdays(String weekdays)
        {
            this.weekdays = weekdays;
        }

        public int getDiscountId()
        {
            return discountId;
        }

        public void setDiscountId(int discountId)
        {
            this.discountId = discountId;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }
    }

    public static class DiscountBean implements Serializable
    {
        private int id;
        private String name;
        private int type;
        private int times;
        private double discount;
        private int status;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public int getTimes()
        {
            return times;
        }

        public void setTimes(int times)
        {
            this.times = times;
        }

        public double getDiscount()
        {
            return discount;
        }

        public void setDiscount(double discount)
        {
            this.discount = discount;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }
    }
}
