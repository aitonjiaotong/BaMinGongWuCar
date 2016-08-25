package passenger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class DriverInfo
{

    /**
     * contains : [{"id":1,"name":"John","phone":"12345678910","idcard":"nfjnskjmlkm","sex":"男","drivingYear":7,"image":null,"star":5,"status":1}]
     * num : 1
     */

    private int num;
    /**
     * id : 1
     * name : John
     * phone : 12345678910
     * idcard : nfjnskjmlkm
     * sex : 男
     * drivingYear : 7
     * image : null
     * star : 5.0
     * status : 1
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
        private String name;
        private String phone;
        private String idcard;
        private String sex;
        private int drivingYear;
        private String image;
        private double star;
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

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getIdcard()
        {
            return idcard;
        }

        public void setIdcard(String idcard)
        {
            this.idcard = idcard;
        }

        public String getSex()
        {
            return sex;
        }

        public void setSex(String sex)
        {
            this.sex = sex;
        }

        public int getDrivingYear()
        {
            return drivingYear;
        }

        public void setDrivingYear(int drivingYear)
        {
            this.drivingYear = drivingYear;
        }

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public double getStar()
        {
            return star;
        }

        public void setStar(double star)
        {
            this.star = star;
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
