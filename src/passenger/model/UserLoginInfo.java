package passenger.model;


/**
 * Created by Administrator on 2016/5/20.
 */
public class UserLoginInfo
{
    String phoneNum;
    String userId;
    String DeviceId;
    String image;
    String idCardImage;
    String idCardImage_back;
    String drivingLicenseImage;
    String drivingLicenseImage_back;

    public UserLoginInfo()
    {

    }

    public UserLoginInfo(String phoneNum, String userId, String deviceId, String imageUrl, String idCardImage, String idCardImage_back, String drivingLicenseImage, String drivingLicenseImage_back)
    {
        this.phoneNum = phoneNum;
        this.userId = userId;
        this.DeviceId = deviceId;
        this.image = imageUrl;
        this.idCardImage = idCardImage;
        this.idCardImage_back = idCardImage_back;
        this.drivingLicenseImage = drivingLicenseImage;
        this.drivingLicenseImage_back = drivingLicenseImage_back;
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

    public String getIdCardImage_back()
    {
        return idCardImage_back;
    }

    public void setIdCardImage_back(String idCardImage_back)
    {
        this.idCardImage_back = idCardImage_back;
    }

    public String getDrivingLicenseImage()
    {
        return drivingLicenseImage;
    }

    public void setDrivingLicenseImage(String drivingLicenseImage)
    {
        this.drivingLicenseImage = drivingLicenseImage;
    }

    public String getDrivingLicenseImage_back()
    {
        return drivingLicenseImage_back;
    }

    public void setDrivingLicenseImage_back(String drivingLicenseImage_back)
    {
        this.drivingLicenseImage_back = drivingLicenseImage_back;
    }


    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getDeviceId()
    {
        return DeviceId;
    }

    public void setDeviceId(String deviceId)
    {
        DeviceId = deviceId;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLoginInfo that = (UserLoginInfo) o;

        if (phoneNum != null ? !phoneNum.equals(that.phoneNum) : that.phoneNum != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return DeviceId != null ? DeviceId.equals(that.DeviceId) : that.DeviceId == null;

    }

    @Override
    public int hashCode()
    {
        int result = phoneNum != null ? phoneNum.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (DeviceId != null ? DeviceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "UserLoginInfo{" +
                "phoneNum='" + phoneNum + '\'' +
                ", userId='" + userId + '\'' +
                ", DeviceId='" + DeviceId + '\'' +
                ", image='" + image + '\'' +
                ", idCardImage='" + idCardImage + '\'' +
                ", idCardImage_back='" + idCardImage_back + '\'' +
                ", drivingLicenseImage='" + drivingLicenseImage + '\'' +
                ", drivingLicenseImage_back='" + drivingLicenseImage_back + '\'' +
                '}';
    }
}
