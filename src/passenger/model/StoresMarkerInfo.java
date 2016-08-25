
package passenger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class StoresMarkerInfo
{

    /**
     * contains : [{"id":3,"name":null,"address":null,"city":"a","phone":null,"head":null,"longitude":null,"latitude":null}]
     * num : 1
     */

    private int num;
    /**
     * id : 3
     * name : null
     * address : null
     * city : a
     * phone : null
     * head : null
     * longitude : null
     * latitude : null
     */

    private List<ContainsEntity> contains;

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public List<ContainsEntity> getContains()
    {
        return contains;
    }

    public void setContains(List<ContainsEntity> contains)
    {
        this.contains = contains;
    }

    public static class ContainsEntity
    {
        private int id;
        private String name;//门店的名称
        private String address;//门店的地址
        private String city;//门店所在的城市
        private String phone;//门店的联系电话
        private String head;//门店的联第人
        private double  longitude;//门店的经度
        private double  latitude;//门店的纬度

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

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getHead()
        {
            return head;
        }

        public void setHead(String head)
        {
            this.head = head;
        }

        public double getLongitude()
        {
            return longitude;
        }

        public void setLongitude(double longitude)
        {
            this.longitude = longitude;
        }

        public double getLatitude()
        {
            return latitude;
        }

        public void setLatitude(double latitude)
        {
            this.latitude = latitude;
        }
    }
}
