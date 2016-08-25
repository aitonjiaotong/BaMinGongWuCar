package passenger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/5/13.
 */
public class CityListInfo
{

    /**
     * city_id : 2419
     * centerx : 116.407413
     * district_online : 1
     * centery : 39.904214
     * name : 三明
     * province : 福建
     * zone_online : 1
     * pinyin : beijing
     */

    private List<HotcityBean> hotcity;
    /**
     * city_id : 152900
     * centerx : 105.73546
     * district_online : 1
     * centery : 38.857855
     * name : 阿拉善盟
     * province : 内蒙古
     * zone_online : 1
     * pinyin : alashanmeng
     */

    private List<AllcityBean> allcity;

    public List<HotcityBean> getHotcity()
    {
        return hotcity;
    }

    public void setHotcity(List<HotcityBean> hotcity)
    {
        this.hotcity = hotcity;
    }

    public List<AllcityBean> getAllcity()
    {
        return allcity;
    }

    public void setAllcity(List<AllcityBean> allcity)
    {
        this.allcity = allcity;
    }

    public static class HotcityBean
    {
        private String city_id;
        private String centerx;
        private String district_online;
        private String centery;
        private String name;
        private String province;
        private String zone_online;
        private String pinyin;

        public String getCity_id()
        {
            return city_id;
        }

        public void setCity_id(String city_id)
        {
            this.city_id = city_id;
        }

        public String getCenterx()
        {
            return centerx;
        }

        public void setCenterx(String centerx)
        {
            this.centerx = centerx;
        }

        public String getDistrict_online()
        {
            return district_online;
        }

        public void setDistrict_online(String district_online)
        {
            this.district_online = district_online;
        }

        public String getCentery()
        {
            return centery;
        }

        public void setCentery(String centery)
        {
            this.centery = centery;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getProvince()
        {
            return province;
        }

        public void setProvince(String province)
        {
            this.province = province;
        }

        public String getZone_online()
        {
            return zone_online;
        }

        public void setZone_online(String zone_online)
        {
            this.zone_online = zone_online;
        }

        public String getPinyin()
        {
            return pinyin;
        }

        public void setPinyin(String pinyin)
        {
            this.pinyin = pinyin;
        }
    }

    public static class AllcityBean
    {
        private String city_id;
        private double centerx;
        private String district_online;
        private double centery;
        private String name;
        private String province;
        private String zone_online;
        private String pinyin;
        private String letter;

        public String getLetter()
        {
            return letter;
        }

        public void setLetter(String letter)
        {
            this.letter = letter;
        }

        public String getCity_id()
        {
            return city_id;
        }

        public void setCity_id(String city_id)
        {
            this.city_id = city_id;
        }

        public double getCenterx()
        {
            return centerx;
        }

        public void setCenterx(double centerx)
        {
            this.centerx = centerx;
        }

        public String getDistrict_online()
        {
            return district_online;
        }

        public void setDistrict_online(String district_online)
        {
            this.district_online = district_online;
        }

        public double getCentery()
        {
            return centery;
        }

        public void setCentery(double centery)
        {
            this.centery = centery;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getProvince()
        {
            return province;
        }

        public void setProvince(String province)
        {
            this.province = province;
        }

        public String getZone_online()
        {
            return zone_online;
        }

        public void setZone_online(String zone_online)
        {
            this.zone_online = zone_online;
        }

        public String getPinyin()
        {
            return pinyin;
        }

        public void setPinyin(String pinyin)
        {
            this.pinyin = pinyin;
        }
    }
}
