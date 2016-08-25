package passenger.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ChooseFristInfo implements Serializable
{
    private String unitOfAccount;
    private String getCityName;
    private String returnCityName;
    private long getCarTime;
    private long returnCarTime;
    private int hasDriver;
    private int isNakedCarHire;
    private int hasTax;
    private int hasLease4Other;
    private String phoneOfUserStr;
    private int carType;
    private int carID;
    private int isSend;
    private int isGet;
    private int mGetCarStoresId;
    private int mReturnCarStoresId;
    private String sendCarAddress;
    private String getCarAddress;
    private String mReturnCarStoreName;
    private String mGetCarStoreName;
    private TypeCarListInfo.ContainsBean.CarBean car;
    private TypeCarListInfo.ContainsBean.PlanBean plan;

    public ChooseFristInfo()
    {
    }

    public ChooseFristInfo(String unitOfAccount, String getCityName, String returnCityName, long getCarTime, long returnCarTime, int hasDriver, int isNakedCarHire, int hasTax, int hasLease4Other, String phoneOfUser,int carType, int carID, int isSend, int isGet, int getCarStoresId, int returnCarStoresId, String sendCarAddress, String getCarAddress, String returnCarStoreName, String getCarStoreName, TypeCarListInfo.ContainsBean.CarBean car, TypeCarListInfo.ContainsBean.PlanBean plan)
    {
        this.unitOfAccount = unitOfAccount;
        this.getCityName = getCityName;
        this.returnCityName = returnCityName;
        this.getCarTime = getCarTime;
        this.returnCarTime = returnCarTime;
        this.hasDriver = hasDriver;
        this.isNakedCarHire = isNakedCarHire;
        this.hasTax = hasTax;
        this.hasLease4Other = hasLease4Other;
        this.phoneOfUserStr = phoneOfUser;
        this.carType = carType;
        this.carID = carID;
        this.isSend = isSend;
        this.isGet = isGet;
        this.mGetCarStoresId = getCarStoresId;
        this.mReturnCarStoresId = returnCarStoresId;
        this.sendCarAddress = sendCarAddress;
        this.getCarAddress = getCarAddress;
        this.mReturnCarStoreName = returnCarStoreName;
        this.mGetCarStoreName = getCarStoreName;
        this.car = car;
        this.plan = plan;
    }

    public String getUnitOfAccount()
    {
        return unitOfAccount;
    }

    public void setUnitOfAccount(String unitOfAccount)
    {
        this.unitOfAccount = unitOfAccount;
    }

    public String getGetCityName()
    {
        return getCityName;
    }

    public void setGetCityName(String getCityName)
    {
        this.getCityName = getCityName;
    }

    public String getReturnCityName()
    {
        return returnCityName;
    }

    public void setReturnCityName(String returnCityName)
    {
        this.returnCityName = returnCityName;
    }

    public long getGetCarTime()
    {
        return getCarTime;
    }

    public void setGetCarTime(long getCarTime)
    {
        this.getCarTime = getCarTime;
    }

    public long getReturnCarTime()
    {
        return returnCarTime;
    }

    public void setReturnCarTime(long returnCarTime)
    {
        this.returnCarTime = returnCarTime;
    }

    public int getHasDriver()
    {
        return hasDriver;
    }

    public void setHasDriver(int hasDriver)
    {
        this.hasDriver = hasDriver;
    }

    public int getIsNakedCarHire()
    {
        return isNakedCarHire;
    }

    public void setIsNakedCarHire(int isNakedCarHire)
    {
        this.isNakedCarHire = isNakedCarHire;
    }

    public int getHasTax()
    {
        return hasTax;
    }

    public void setHasTax(int hasTax)
    {
        this.hasTax = hasTax;
    }

    public int getHasLease4Other()
    {
        return hasLease4Other;
    }

    public void setHasLease4Other(int hasLease4Other)
    {
        this.hasLease4Other = hasLease4Other;
    }

    public String getPhoneOfUserStr()
    {
        return phoneOfUserStr;
    }

    public void setPhoneOfUserStr(String phoneOfUserStr)
    {
        this.phoneOfUserStr = phoneOfUserStr;
    }

    public int getCarType()
    {
        return carType;
    }

    public void setCarType(int carType)
    {
        this.carType = carType;
    }

    public int getCarID()
    {
        return carID;
    }

    public void setCarID(int carID)
    {
        this.carID = carID;
    }

    public int getIsSend()
    {
        return isSend;
    }

    public void setIsSend(int isSend)
    {
        this.isSend = isSend;
    }

    public int getIsGet()
    {
        return isGet;
    }

    public void setIsGet(int isGet)
    {
        this.isGet = isGet;
    }

    public int getGetCarStoresId()
    {
        return mGetCarStoresId;
    }

    public void setGetCarStoresId(int getCarStoresId)
    {
        mGetCarStoresId = getCarStoresId;
    }

    public int getReturnCarStoresId()
    {
        return mReturnCarStoresId;
    }

    public void setReturnCarStoresId(int returnCarStoresId)
    {
        mReturnCarStoresId = returnCarStoresId;
    }

    public String getSendCarAddress()
    {
        return sendCarAddress;
    }

    public void setSendCarAddress(String sendCarAddress)
    {
        this.sendCarAddress = sendCarAddress;
    }

    public String getGetCarAddress()
    {
        return getCarAddress;
    }

    public void setGetCarAddress(String getCarAddress)
    {
        this.getCarAddress = getCarAddress;
    }

    public String getReturnCarStoreName()
    {
        return mReturnCarStoreName;
    }

    public void setReturnCarStoreName(String returnCarStoreName)
    {
        mReturnCarStoreName = returnCarStoreName;
    }

    public String getGetCarStoreName()
    {
        return mGetCarStoreName;
    }

    public void setGetCarStoreName(String getCarStoreName)
    {
        mGetCarStoreName = getCarStoreName;
    }

    public TypeCarListInfo.ContainsBean.CarBean getCar()
    {
        return car;
    }

    public void setCar(TypeCarListInfo.ContainsBean.CarBean car)
    {
        this.car = car;
    }

    public TypeCarListInfo.ContainsBean.PlanBean getPlan()
    {
        return plan;
    }

    public void setPlan(TypeCarListInfo.ContainsBean.PlanBean plan)
    {
        this.plan = plan;
    }
}
