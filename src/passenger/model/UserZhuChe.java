package passenger.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/4.
 */
public class UserZhuChe implements Serializable
{
	/**
     * success : true
     * message : 登陆成功
     * contains : {"id":5,"name":"13799283714","password":"PuQFBPpe6wIFm2b87b/yKw==","sex":null,"login_id":"78c97a0d-bda3-4696-8fbc-fd5925ff337f","phone":"13799283714","image":null,"idCardImage":null,"drivingLicenseImage":null,"idCardImage_back":null,"drivingLicenseImage_back":null}
     */

    private boolean success;
    private String message;
    /**
     * id : 5
     * name : 13799283714
     * password : PuQFBPpe6wIFm2b87b/yKw==
     * sex : null
     * login_id : 78c97a0d-bda3-4696-8fbc-fd5925ff337f
     * phone : 13799283714
     * image : null
     * idCardImage : null
     * drivingLicenseImage : null
     * idCardImage_back : null
     * drivingLicenseImage_back : null
     */

    private ContainsBean contains;
    private MemberModelEntity memberModel;
    
    public MemberModelEntity getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModelEntity memberModel) {
        this.memberModel = memberModel;
    }
    
    public static class MemberModelEntity implements Serializable{
        private int id;
        private String name;
        private double discount;
        private int status;
        private int level;
        private double ceiling;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public double getCeiling() {
            return ceiling;
        }

        public void setCeiling(double ceiling) {
            this.ceiling = ceiling;
        }
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ContainsBean getContains()
    {
        return contains;
    }

    public void setContains(ContainsBean contains)
    {
        this.contains = contains;
    }

    public static class ContainsBean implements Serializable
    {
        private int id;
        private String name;
        private String password;
        private int sex;
        private String login_id;
        private String phone;
        private String image;
        private String idCardImage;
        private String drivingLicenseImage;
        private String idCardImage_back;
        private String drivingLicenseImage_back;

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

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public int getSex()
        {
            return sex;
        }

        public void setSex(int sex)
        {
            this.sex = sex;
        }

        public String getLogin_id()
        {
            return login_id;
        }

        public void setLogin_id(String login_id)
        {
            this.login_id = login_id;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public String getIdCardImage()
        {
            return idCardImage;
        }

        public void setIdCardImage(String idCardImage)
        {
            this.idCardImage = idCardImage;
        }

        public String getDrivingLicenseImage()
        {
            return drivingLicenseImage;
        }

        public void setDrivingLicenseImage(String drivingLicenseImage)
        {
            this.drivingLicenseImage = drivingLicenseImage;
        }

        public String getIdCardImage_back()
        {
            return idCardImage_back;
        }

        public void setIdCardImage_back(String idCardImage_back)
        {
            this.idCardImage_back = idCardImage_back;
        }

        public String getDrivingLicenseImage_back()
        {
            return drivingLicenseImage_back;
        }

        public void setDrivingLicenseImage_back(String drivingLicenseImage_back)
        {
            this.drivingLicenseImage_back = drivingLicenseImage_back;
        }
    }
}
