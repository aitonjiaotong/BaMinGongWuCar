package passenger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public class OrderListInfo
{


    /**
     * contains : [{"order":{"id":2,"carId":null,"planId":16,"zuchuDate":1467702900000,"huancheDate":null,"planReturnDate":1468134900000,"limitMileage":0,"accountId":5,"guarantorId":null,"beforeMileage":null,"afterMileage":null,"jijiatime":5,"timePrice":1200,"outMileagePrice":0,"outTimePrice":0,"realPrice":0,"shouyajin":0,"price":0,"note":null,"flag":4,"driverId":null,"hasDriver":1,"getCar":7,"returnCar":7,"advancePayment":null,"date":1467698909000,"status":0,"sale":null,"institutionsCode":"727464","hasFranchiseFees":null,"settledDate":null,"model":"东风悦达起亚","type":"K3","otherPrice":0,"yuPrice":1101.6,"monthSettle":null,"returnDeposit":0,"finalPrice":0,"floatCost":0,"isSendCarToHome":0,"isGetCarFromHome":0,"sendCarAddress":null,"getCarAddress":null,"hasTax":1,"hasLuo":1,"privilegeId":1,"discount":0.9,"tempPriceForDiscount":0,"tempPriceForTax":0,"leaseForOther":1,"phoneOfUser":"12345678910","driverCost":null},"car":{"id":42,"licensePlate":"闽GDB012","model":"东风悦达起亚","type":"K3","box":"4厢","pailiang":"1591ml","seat":5,"zidong":0,"color":"红色","engineCode":"E1629852","mileage":6700,"maintenanceMileage":5000,"status":0,"deposit":2000,"buyDate":"2016-04-02","inspection":"2016-04-02","image":"http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/f99f04bce141a1e531ac1c1af39b3221.jpg","note":"","planId":16,"lei":0,"storeId":1,"licensePlateColor":"蓝","lastMaintenanceMileage":200,"message":null,"vehicleLicenseImg":null,"vehicleLicenseInvalidDate":null},"getCarStore":{"id":7,"name":"永安市长途客运站","address":"永安市燕江东路419号","city":"永安市","phone":"3632851","head":"王","longitude":null,"latitude":null,"status":1},"returnStore":{"id":7,"name":"永安市长途客运站","address":"永安市燕江东路419号","city":"永安市","phone":"3632851","head":"王","longitude":null,"latitude":null,"status":1},"privilege":{"id":1,"name":"感恩七月回馈","type":0,"startDate":1467251100000,"endDate":1468201500000,"startOfMonth":1,"endOfMonth":1,"weekdays":"","discountId":2,"status":0},"discount":{"id":2,"name":"五天九折优惠","type":1,"times":5,"discount":0.9,"status":0}}]
     * num : 1
     */

    private int num;
    /**
     * order : {"id":2,"carId":null,"planId":16,"zuchuDate":1467702900000,"huancheDate":null,"planReturnDate":1468134900000,"limitMileage":0,"accountId":5,"guarantorId":null,"beforeMileage":null,"afterMileage":null,"jijiatime":5,"timePrice":1200,"outMileagePrice":0,"outTimePrice":0,"realPrice":0,"shouyajin":0,"price":0,"note":null,"flag":4,"driverId":null,"hasDriver":1,"getCar":7,"returnCar":7,"advancePayment":null,"date":1467698909000,"status":0,"sale":null,"institutionsCode":"727464","hasFranchiseFees":null,"settledDate":null,"model":"东风悦达起亚","type":"K3","otherPrice":0,"yuPrice":1101.6,"monthSettle":null,"returnDeposit":0,"finalPrice":0,"floatCost":0,"isSendCarToHome":0,"isGetCarFromHome":0,"sendCarAddress":null,"getCarAddress":null,"hasTax":1,"hasLuo":1,"privilegeId":1,"discount":0.9,"tempPriceForDiscount":0,"tempPriceForTax":0,"leaseForOther":1,"phoneOfUser":"12345678910","driverCost":null}
     * car : {"id":42,"licensePlate":"闽GDB012","model":"东风悦达起亚","type":"K3","box":"4厢","pailiang":"1591ml","seat":5,"zidong":0,"color":"红色","engineCode":"E1629852","mileage":6700,"maintenanceMileage":5000,"status":0,"deposit":2000,"buyDate":"2016-04-02","inspection":"2016-04-02","image":"http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/f99f04bce141a1e531ac1c1af39b3221.jpg","note":"","planId":16,"lei":0,"storeId":1,"licensePlateColor":"蓝","lastMaintenanceMileage":200,"message":null,"vehicleLicenseImg":null,"vehicleLicenseInvalidDate":null}
     * getCarStore : {"id":7,"name":"永安市长途客运站","address":"永安市燕江东路419号","city":"永安市","phone":"3632851","head":"王","longitude":null,"latitude":null,"status":1}
     * returnStore : {"id":7,"name":"永安市长途客运站","address":"永安市燕江东路419号","city":"永安市","phone":"3632851","head":"王","longitude":null,"latitude":null,"status":1}
     * privilege : {"id":1,"name":"感恩七月回馈","type":0,"startDate":1467251100000,"endDate":1468201500000,"startOfMonth":1,"endOfMonth":1,"weekdays":"","discountId":2,"status":0}
     * discount : {"id":2,"name":"五天九折优惠","type":1,"times":5,"discount":0.9,"status":0}
     */

    private List<ContainsBean> contains;

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public List<ContainsBean> getContains()
    {
        return contains;
    }

    public void setContains(List<ContainsBean> contains)
    {
        this.contains = contains;
    }

    public static class ContainsBean
    {
        /**
         * id : 2
         * carId : null
         * planId : 16
         * zuchuDate : 1467702900000
         * huancheDate : null
         * planReturnDate : 1468134900000
         * limitMileage : 0.0
         * accountId : 5
         * guarantorId : null
         * beforeMileage : null
         * afterMileage : null
         * jijiatime : 5.0
         * timePrice : 1200.0
         * outMileagePrice : 0.0
         * outTimePrice : 0.0
         * realPrice : 0.0
         * shouyajin : 0.0
         * price : 0.0
         * note : null
         * flag : 4
         * driverId : null
         * hasDriver : 1
         * getCar : 7
         * returnCar : 7
         * advancePayment : null
         * date : 1467698909000
         * status : 0
         * sale : null
         * institutionsCode : 727464
         * hasFranchiseFees : null
         * settledDate : null
         * model : 东风悦达起亚
         * type : K3
         * otherPrice : 0.0
         * yuPrice : 1101.6
         * monthSettle : null
         * returnDeposit : 0.0
         * finalPrice : 0.0
         * floatCost : 0.0
         * isSendCarToHome : 0
         * isGetCarFromHome : 0
         * sendCarAddress : null
         * getCarAddress : null
         * hasTax : 1
         * hasLuo : 1
         * privilegeId : 1
         * discount : 0.9
         * tempPriceForDiscount : 0.0
         * tempPriceForTax : 0.0
         * leaseForOther : 1
         * phoneOfUser : 12345678910
         * driverCost : null
         */

        private OrderBean order;
        /**
         * id : 42
         * licensePlate : 闽GDB012
         * model : 东风悦达起亚
         * type : K3
         * box : 4厢
         * pailiang : 1591ml
         * seat : 5
         * zidong : 0
         * color : 红色
         * engineCode : E1629852
         * mileage : 6700.0
         * maintenanceMileage : 5000.0
         * status : 0
         * deposit : 2000.0
         * buyDate : 2016-04-02
         * inspection : 2016-04-02
         * image : http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/f99f04bce141a1e531ac1c1af39b3221.jpg
         * note :
         * planId : 16
         * lei : 0
         * storeId : 1
         * licensePlateColor : 蓝
         * lastMaintenanceMileage : 200.0
         * message : null
         * vehicleLicenseImg : null
         * vehicleLicenseInvalidDate : null
         */

        private CarBean car;
        /**
         * id : 7
         * name : 永安市长途客运站
         * address : 永安市燕江东路419号
         * city : 永安市
         * phone : 3632851
         * head : 王
         * longitude : null
         * latitude : null
         * status : 1
         */

        private GetCarStoreBean getCarStore;
        /**
         * id : 7
         * name : 永安市长途客运站
         * address : 永安市燕江东路419号
         * city : 永安市
         * phone : 3632851
         * head : 王
         * longitude : null
         * latitude : null
         * status : 1
         */

        private ReturnStoreBean returnStore;
        /**
         * id : 1
         * name : 感恩七月回馈
         * type : 0
         * startDate : 1467251100000
         * endDate : 1468201500000
         * startOfMonth : 1
         * endOfMonth : 1
         * weekdays :
         * discountId : 2
         * status : 0
         */

        private PrivilegeBean privilege;
        /**
         * id : 2
         * name : 五天九折优惠
         * type : 1
         * times : 5
         * discount : 0.9
         * status : 0
         */

        private DiscountBean discount;

        public OrderBean getOrder()
        {
            return order;
        }

        public void setOrder(OrderBean order)
        {
            this.order = order;
        }

        public CarBean getCar()
        {
            return car;
        }

        public void setCar(CarBean car)
        {
            this.car = car;
        }

        public GetCarStoreBean getGetCarStore()
        {
            return getCarStore;
        }

        public void setGetCarStore(GetCarStoreBean getCarStore)
        {
            this.getCarStore = getCarStore;
        }

        public ReturnStoreBean getReturnStore()
        {
            return returnStore;
        }

        public void setReturnStore(ReturnStoreBean returnStore)
        {
            this.returnStore = returnStore;
        }

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

        public static class OrderBean
        {
            private int id;
            private int carId;
            private int planId;
            private long zuchuDate;
            private long huancheDate;
            private long planReturnDate;
            private double limitMileage;
            private int accountId;
            private int guarantorId;
            private double beforeMileage;
            private double afterMileage;
            private double jijiatime;
            private double timePrice;
            private double outMileagePrice;
            private double outTimePrice;
            private double realPrice;
            private double shouyajin;
            private double price;
            private String note;
            private int flag;
            private int driverId;
            private int hasDriver;
            private int getCar;
            private int returnCar;
            private double advancePayment;
            private long date;
            private int status;
            private String sale;
            private String institutionsCode;
            private int hasFranchiseFees;
            private long settledDate;
            private String model;
            private String type;
            private double otherPrice;
            private double yuPrice;
            private int monthSettle;
            private double returnDeposit;
            private double finalPrice;
            private double floatCost;
            private int isSendCarToHome;
            private int isGetCarFromHome;
            private String sendCarAddress;
            private String getCarAddress;
            private int hasTax;
            private int hasLuo;
            private int privilegeId;
            private double discount;
            private double tempPriceForDiscount;
            private double tempPriceForTax;
            private int leaseForOther;
            private String phoneOfUser;
            private double driverCost;

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public int getCarId()
            {
                return carId;
            }

            public void setCarId(int carId)
            {
                this.carId = carId;
            }

            public int getPlanId()
            {
                return planId;
            }

            public void setPlanId(int planId)
            {
                this.planId = planId;
            }

            public long getZuchuDate()
            {
                return zuchuDate;
            }

            public void setZuchuDate(long zuchuDate)
            {
                this.zuchuDate = zuchuDate;
            }

            public long getHuancheDate()
            {
                return huancheDate;
            }

            public void setHuancheDate(long huancheDate)
            {
                this.huancheDate = huancheDate;
            }

            public long getPlanReturnDate()
            {
                return planReturnDate;
            }

            public void setPlanReturnDate(long planReturnDate)
            {
                this.planReturnDate = planReturnDate;
            }

            public double getLimitMileage()
            {
                return limitMileage;
            }

            public void setLimitMileage(double limitMileage)
            {
                this.limitMileage = limitMileage;
            }

            public int getAccountId()
            {
                return accountId;
            }

            public void setAccountId(int accountId)
            {
                this.accountId = accountId;
            }

            public int getGuarantorId()
            {
                return guarantorId;
            }

            public void setGuarantorId(int guarantorId)
            {
                this.guarantorId = guarantorId;
            }

            public double getBeforeMileage()
            {
                return beforeMileage;
            }

            public void setBeforeMileage(double beforeMileage)
            {
                this.beforeMileage = beforeMileage;
            }

            public double getAfterMileage()
            {
                return afterMileage;
            }

            public void setAfterMileage(double afterMileage)
            {
                this.afterMileage = afterMileage;
            }

            public double getJijiatime()
            {
                return jijiatime;
            }

            public void setJijiatime(double jijiatime)
            {
                this.jijiatime = jijiatime;
            }

            public double getTimePrice()
            {
                return timePrice;
            }

            public void setTimePrice(double timePrice)
            {
                this.timePrice = timePrice;
            }

            public double getOutMileagePrice()
            {
                return outMileagePrice;
            }

            public void setOutMileagePrice(double outMileagePrice)
            {
                this.outMileagePrice = outMileagePrice;
            }

            public double getOutTimePrice()
            {
                return outTimePrice;
            }

            public void setOutTimePrice(double outTimePrice)
            {
                this.outTimePrice = outTimePrice;
            }

            public double getRealPrice()
            {
                return realPrice;
            }

            public void setRealPrice(double realPrice)
            {
                this.realPrice = realPrice;
            }

            public double getShouyajin()
            {
                return shouyajin;
            }

            public void setShouyajin(double shouyajin)
            {
                this.shouyajin = shouyajin;
            }

            public double getPrice()
            {
                return price;
            }

            public void setPrice(double price)
            {
                this.price = price;
            }

            public String getNote()
            {
                return note;
            }

            public void setNote(String note)
            {
                this.note = note;
            }

            public int getFlag()
            {
                return flag;
            }

            public void setFlag(int flag)
            {
                this.flag = flag;
            }

            public int getDriverId()
            {
                return driverId;
            }

            public void setDriverId(int driverId)
            {
                this.driverId = driverId;
            }

            public int getHasDriver()
            {
                return hasDriver;
            }

            public void setHasDriver(int hasDriver)
            {
                this.hasDriver = hasDriver;
            }

            public int getGetCar()
            {
                return getCar;
            }

            public void setGetCar(int getCar)
            {
                this.getCar = getCar;
            }

            public int getReturnCar()
            {
                return returnCar;
            }

            public void setReturnCar(int returnCar)
            {
                this.returnCar = returnCar;
            }

            public double getAdvancePayment()
            {
                return advancePayment;
            }

            public void setAdvancePayment(double advancePayment)
            {
                this.advancePayment = advancePayment;
            }

            public long getDate()
            {
                return date;
            }

            public void setDate(long date)
            {
                this.date = date;
            }

            public int getStatus()
            {
                return status;
            }

            public void setStatus(int status)
            {
                this.status = status;
            }

            public String getSale()
            {
                return sale;
            }

            public void setSale(String sale)
            {
                this.sale = sale;
            }

            public String getInstitutionsCode()
            {
                return institutionsCode;
            }

            public void setInstitutionsCode(String institutionsCode)
            {
                this.institutionsCode = institutionsCode;
            }

            public int getHasFranchiseFees()
            {
                return hasFranchiseFees;
            }

            public void setHasFranchiseFees(int hasFranchiseFees)
            {
                this.hasFranchiseFees = hasFranchiseFees;
            }

            public long getSettledDate()
            {
                return settledDate;
            }

            public void setSettledDate(long settledDate)
            {
                this.settledDate = settledDate;
            }

            public String getModel()
            {
                return model;
            }

            public void setModel(String model)
            {
                this.model = model;
            }

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public double getOtherPrice()
            {
                return otherPrice;
            }

            public void setOtherPrice(double otherPrice)
            {
                this.otherPrice = otherPrice;
            }

            public double getYuPrice()
            {
                return yuPrice;
            }

            public void setYuPrice(double yuPrice)
            {
                this.yuPrice = yuPrice;
            }

            public int getMonthSettle()
            {
                return monthSettle;
            }

            public void setMonthSettle(int monthSettle)
            {
                this.monthSettle = monthSettle;
            }

            public double getReturnDeposit()
            {
                return returnDeposit;
            }

            public void setReturnDeposit(double returnDeposit)
            {
                this.returnDeposit = returnDeposit;
            }

            public double getFinalPrice()
            {
                return finalPrice;
            }

            public void setFinalPrice(double finalPrice)
            {
                this.finalPrice = finalPrice;
            }

            public double getFloatCost()
            {
                return floatCost;
            }

            public void setFloatCost(double floatCost)
            {
                this.floatCost = floatCost;
            }

            public int getIsSendCarToHome()
            {
                return isSendCarToHome;
            }

            public void setIsSendCarToHome(int isSendCarToHome)
            {
                this.isSendCarToHome = isSendCarToHome;
            }

            public int getIsGetCarFromHome()
            {
                return isGetCarFromHome;
            }

            public void setIsGetCarFromHome(int isGetCarFromHome)
            {
                this.isGetCarFromHome = isGetCarFromHome;
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

            public int getHasTax()
            {
                return hasTax;
            }

            public void setHasTax(int hasTax)
            {
                this.hasTax = hasTax;
            }

            public int getHasLuo()
            {
                return hasLuo;
            }

            public void setHasLuo(int hasLuo)
            {
                this.hasLuo = hasLuo;
            }

            public int getPrivilegeId()
            {
                return privilegeId;
            }

            public void setPrivilegeId(int privilegeId)
            {
                this.privilegeId = privilegeId;
            }

            public double getDiscount()
            {
                return discount;
            }

            public void setDiscount(double discount)
            {
                this.discount = discount;
            }

            public double getTempPriceForDiscount()
            {
                return tempPriceForDiscount;
            }

            public void setTempPriceForDiscount(double tempPriceForDiscount)
            {
                this.tempPriceForDiscount = tempPriceForDiscount;
            }

            public double getTempPriceForTax()
            {
                return tempPriceForTax;
            }

            public void setTempPriceForTax(double tempPriceForTax)
            {
                this.tempPriceForTax = tempPriceForTax;
            }

            public int getLeaseForOther()
            {
                return leaseForOther;
            }

            public void setLeaseForOther(int leaseForOther)
            {
                this.leaseForOther = leaseForOther;
            }

            public String getPhoneOfUser()
            {
                return phoneOfUser;
            }

            public void setPhoneOfUser(String phoneOfUser)
            {
                this.phoneOfUser = phoneOfUser;
            }

            public double getDriverCost()
            {
                return driverCost;
            }

            public void setDriverCost(double driverCost)
            {
                this.driverCost = driverCost;
            }
        }

        public static class CarBean
        {
            private int id;
            private String licensePlate;
            private String model;
            private String type;
            private String box;
            private String pailiang;
            private int seat;
            private int zidong;
            private String color;
            private String engineCode;
            private double mileage;
            private double maintenanceMileage;
            private int status;
            private double deposit;
            private String buyDate;
            private String inspection;
            private String image;
            private String note;
            private int planId;
            private int lei;
            private int storeId;
            private String licensePlateColor;
            private double lastMaintenanceMileage;
            private Object message;
            private Object vehicleLicenseImg;
            private Object vehicleLicenseInvalidDate;

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getLicensePlate()
            {
                return licensePlate;
            }

            public void setLicensePlate(String licensePlate)
            {
                this.licensePlate = licensePlate;
            }

            public String getModel()
            {
                return model;
            }

            public void setModel(String model)
            {
                this.model = model;
            }

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public String getBox()
            {
                return box;
            }

            public void setBox(String box)
            {
                this.box = box;
            }

            public String getPailiang()
            {
                return pailiang;
            }

            public void setPailiang(String pailiang)
            {
                this.pailiang = pailiang;
            }

            public int getSeat()
            {
                return seat;
            }

            public void setSeat(int seat)
            {
                this.seat = seat;
            }

            public int getZidong()
            {
                return zidong;
            }

            public void setZidong(int zidong)
            {
                this.zidong = zidong;
            }

            public String getColor()
            {
                return color;
            }

            public void setColor(String color)
            {
                this.color = color;
            }

            public String getEngineCode()
            {
                return engineCode;
            }

            public void setEngineCode(String engineCode)
            {
                this.engineCode = engineCode;
            }

            public double getMileage()
            {
                return mileage;
            }

            public void setMileage(double mileage)
            {
                this.mileage = mileage;
            }

            public double getMaintenanceMileage()
            {
                return maintenanceMileage;
            }

            public void setMaintenanceMileage(double maintenanceMileage)
            {
                this.maintenanceMileage = maintenanceMileage;
            }

            public int getStatus()
            {
                return status;
            }

            public void setStatus(int status)
            {
                this.status = status;
            }

            public double getDeposit()
            {
                return deposit;
            }

            public void setDeposit(double deposit)
            {
                this.deposit = deposit;
            }

            public String getBuyDate()
            {
                return buyDate;
            }

            public void setBuyDate(String buyDate)
            {
                this.buyDate = buyDate;
            }

            public String getInspection()
            {
                return inspection;
            }

            public void setInspection(String inspection)
            {
                this.inspection = inspection;
            }

            public String getImage()
            {
                return image;
            }

            public void setImage(String image)
            {
                this.image = image;
            }

            public String getNote()
            {
                return note;
            }

            public void setNote(String note)
            {
                this.note = note;
            }

            public int getPlanId()
            {
                return planId;
            }

            public void setPlanId(int planId)
            {
                this.planId = planId;
            }

            public int getLei()
            {
                return lei;
            }

            public void setLei(int lei)
            {
                this.lei = lei;
            }

            public int getStoreId()
            {
                return storeId;
            }

            public void setStoreId(int storeId)
            {
                this.storeId = storeId;
            }

            public String getLicensePlateColor()
            {
                return licensePlateColor;
            }

            public void setLicensePlateColor(String licensePlateColor)
            {
                this.licensePlateColor = licensePlateColor;
            }

            public double getLastMaintenanceMileage()
            {
                return lastMaintenanceMileage;
            }

            public void setLastMaintenanceMileage(double lastMaintenanceMileage)
            {
                this.lastMaintenanceMileage = lastMaintenanceMileage;
            }

            public Object getMessage()
            {
                return message;
            }

            public void setMessage(Object message)
            {
                this.message = message;
            }

            public Object getVehicleLicenseImg()
            {
                return vehicleLicenseImg;
            }

            public void setVehicleLicenseImg(Object vehicleLicenseImg)
            {
                this.vehicleLicenseImg = vehicleLicenseImg;
            }

            public Object getVehicleLicenseInvalidDate()
            {
                return vehicleLicenseInvalidDate;
            }

            public void setVehicleLicenseInvalidDate(Object vehicleLicenseInvalidDate)
            {
                this.vehicleLicenseInvalidDate = vehicleLicenseInvalidDate;
            }
        }

        public static class GetCarStoreBean
        {
            private int id;
            private String name;
            private String address;
            private String city;
            private String phone;
            private String head;
            private Object longitude;
            private Object latitude;
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

            public Object getLongitude()
            {
                return longitude;
            }

            public void setLongitude(Object longitude)
            {
                this.longitude = longitude;
            }

            public Object getLatitude()
            {
                return latitude;
            }

            public void setLatitude(Object latitude)
            {
                this.latitude = latitude;
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

        public static class ReturnStoreBean
        {
            private int id;
            private String name;
            private String address;
            private String city;
            private String phone;
            private String head;
            private Object longitude;
            private Object latitude;
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

            public Object getLongitude()
            {
                return longitude;
            }

            public void setLongitude(Object longitude)
            {
                this.longitude = longitude;
            }

            public Object getLatitude()
            {
                return latitude;
            }

            public void setLatitude(Object latitude)
            {
                this.latitude = latitude;
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

        public static class PrivilegeBean
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

        public static class DiscountBean
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
}
