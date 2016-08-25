package passenger.model;

/**
 * Created by Administrator on 2016/3/30.
 */
public class OrderDetailsInfo {
	private boolean success;
    private String message;
    private ObjectEntity object;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ObjectEntity getObject() {
        return object;
    }

    public void setObject(ObjectEntity object) {
        this.object = object;
    }
	public static class ObjectEntity {
	/**
	 * id : 593 carId : null planId : 2 zuchuDate : 1471320000000 huancheDate :
	 * null planReturnDate : 1471406400000 limitMileage : 3000.0 accountId : 4
	 * guarantorId : null beforeMileage : null afterMileage : null jijiatime :
	 * 1.0 timePrice : 150.0 outMileagePrice : 0.0 outTimePrice : 0.0 realPrice
	 * : 0.0 shouyajin : 0.0 price : 191.8 note : null flag : 4 driverId : null
	 * hasDriver : 1 getCar : 3 returnCar : 3 advancePayment : null date :
	 * 1471316160000 status : 0 sale : null institutionsCode : 8000
	 * hasFranchiseFees : null settledDate : null model : 东风悦达起亚 type : K2
	 * otherPrice : 0.0 yuPrice : 207.5 monthSettle : null returnDeposit : 0.0
	 * finalPrice : 0.0 floatCost : 0.0 isSendCarToHome : 0 isGetCarFromHome : 0
	 * sendCarAddress : null getCarAddress : null hasTax : 1 hasLuo : 0
	 * privilegeId : null discount : 1.0 tempPriceForDiscount : 150.0
	 * tempPriceForTax : 141.8 leaseForOther : 0 phoneOfUser : driverCost : 0.0
	 * tempPriceForMemberDiscount : 135.0
	 */
	
	private OrderBean order;
	/**
	 * id : 73 licensePlate : 闽GDB751 model : 东风悦达起亚 type : K2 box : 3厢 pailiang
	 * : 1396ml seat : 5 zidong : 0 color : 白色 engineCode : E1260878 mileage :
	 * 4500.0 maintenanceMileage : 12000.0 status : 0 deposit : 2000.0 buyDate :
	 * 2015-02-09 inspection : 2017-08-23 image :
	 * http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/4ad72b604e0cfa4a9b1621d81f09f43e.jpg
	 * note : planId : 1 lei : 0 storeId : 1 licensePlateColor : 蓝
	 * lastMaintenanceMileage : 2000.0 message : null vehicleLicenseImg : null
	 * vehicleLicenseInvalidDate : null
	 */

	private CarEntity car;
	/**
	 * id : 3 name : 厦门河山店 address : 厦门何山路联谊大厦 city : 厦门 phone : 15963312012
	 * head : 鲁仁华 longitude : 20.5 latitude : 20.5 status : 1
	 */

	private GetCarStoreEntity getCarStore;
	/**
	 * id : 3 name : 厦门河山店 address : 厦门何山路联谊大厦 city : 厦门 phone : 15963312012
	 * head : 鲁仁华 longitude : 20.5 latitude : 20.5 status : 1
	 */

	private ReturnStoreEntity returnStore;
	/**
	 * order :
	 * {"id":593,"carId":null,"planId":2,"zuchuDate":1471320000000,"huancheDate":null,"planReturnDate":1471406400000,"limitMileage":3000,"accountId":4,"guarantorId":null,"beforeMileage":null,"afterMileage":null,"jijiatime":1,"timePrice":150,"outMileagePrice":0,"outTimePrice":0,"realPrice":0,"shouyajin":0,"price":191.8,"note":null,"flag":4,"driverId":null,"hasDriver":1,"getCar":3,"returnCar":3,"advancePayment":null,"date":1471316160000,"status":0,"sale":null,"institutionsCode":"8000","hasFranchiseFees":null,"settledDate":null,"model":"东风悦达起亚","type":"K2","otherPrice":0,"yuPrice":207.5,"monthSettle":null,"returnDeposit":0,"finalPrice":0,"floatCost":0,"isSendCarToHome":0,"isGetCarFromHome":0,"sendCarAddress":null,"getCarAddress":null,"hasTax":1,"hasLuo":0,"privilegeId":null,"discount":1,"tempPriceForDiscount":150,"tempPriceForTax":141.8,"leaseForOther":0,"phoneOfUser":"","driverCost":0,"tempPriceForMemberDiscount":135}
	 * car :
	 * {"id":73,"licensePlate":"闽GDB751","model":"东风悦达起亚","type":"K2","box":"3厢","pailiang":"1396ml","seat":5,"zidong":0,"color":"白色","engineCode":"E1260878","mileage":4500,"maintenanceMileage":12000,"status":0,"deposit":2000,"buyDate":"2015-02-09","inspection":"2017-08-23","image":"http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/4ad72b604e0cfa4a9b1621d81f09f43e.jpg","note":"","planId":1,"lei":0,"storeId":1,"licensePlateColor":"蓝","lastMaintenanceMileage":2000,"message":null,"vehicleLicenseImg":null,"vehicleLicenseInvalidDate":null}
	 * getCarStore :
	 * {"id":3,"name":"厦门河山店","address":"厦门何山路联谊大厦","city":"厦门","phone":"15963312012","head":"鲁仁华","longitude":20.5,"latitude":20.5,"status":1}
	 * returnStore :
	 * {"id":3,"name":"厦门河山店","address":"厦门何山路联谊大厦","city":"厦门","phone":"15963312012","head":"鲁仁华","longitude":20.5,"latitude":20.5,"status":1}
	 * driver : null plan :
	 * {"id":2,"name":"公务二型","price":150,"unitMileage":3000,"outMileage":9.5,"outTime":2.5,"flag":1,"jijia":0,"insurance":50,"hasDriver":60,"others":0,"poundage":0,"franchiseFees":30,"floatPrice":2.5,"sendCarToHome":50,"getCarFromHome":50,"discount":0.85,"tax":1.05}
	 * privilege : null discount : null account :
	 * {"id":4,"name":"15871105320","password":"PuQFBPpe6wIFm2b87b/yKw==","sex":null,"loginId":"9e22f5b4-44da-4b98-8f69-cf0eb4a6ca2a","realName":null,"cardStatus":null,"bankCard":null,"phone":"15871105320","address":null,"image":"","bankCardStatus":null,"note":null,"login_id":"5535bbba-073b-492c-9c99-6b7f734d2934","flag":0,"idCardImage":null,"idCardImage_back":null,"drivingLicenseImage":null,"drivingLicenseImage_back":null,"idCardInvalidDate":null,"drivingLicenseInvalidDate":null,"memberId":2,"idcard":null}
	 * member :
	 * {"id":2,"name":"一星会员","discount":0.9,"level":2,"ceiling":30000,"status":0}
	 */

	private DriverBean driver;
	/**
	 * id : 2 name : 公务二型 price : 150.0 unitMileage : 3000.0 outMileage : 9.5
	 * outTime : 2.5 flag : 1 jijia : 0 insurance : 50.0 hasDriver : 60.0 others
	 * : 0.0 poundage : 0.0 franchiseFees : 30.0 floatPrice : 2.5 sendCarToHome
	 * : 50.0 getCarFromHome : 50.0 discount : 0.85 tax : 1.05
	 */

	private PlanEntity plan;
	private PrivilegeBean privilege;
	private DiscountBean discount;
	/**
	 * id : 4 name : 15871105320 password : PuQFBPpe6wIFm2b87b/yKw== sex : null
	 * loginId : 9e22f5b4-44da-4b98-8f69-cf0eb4a6ca2a realName : null cardStatus
	 * : null bankCard : null phone : 15871105320 address : null image :
	 * bankCardStatus : null note : null login_id :
	 * 5535bbba-073b-492c-9c99-6b7f734d2934 flag : 0 idCardImage : null
	 * idCardImage_back : null drivingLicenseImage : null
	 * drivingLicenseImage_back : null idCardInvalidDate : null
	 * drivingLicenseInvalidDate : null memberId : 2 idcard : null
	 */

	private AccountEntity account;
	/**
	 * id : 2 name : 一星会员 discount : 0.9 level : 2 ceiling : 30000.0 status : 0
	 */

	private MemberEntity member;

	public OrderBean getOrder() {
		return order;
	}

	public void setOrder(OrderBean order) {
		this.order = order;
	}

	public CarEntity getCar() {
		return car;
	}

	public void setCar(CarEntity car) {
		this.car = car;
	}

	public GetCarStoreEntity getGetCarStore() {
		return getCarStore;
	}

	public void setGetCarStore(GetCarStoreEntity getCarStore) {
		this.getCarStore = getCarStore;
	}

	public ReturnStoreEntity getReturnStore() {
		return returnStore;
	}

	public void setReturnStore(ReturnStoreEntity returnStore) {
		this.returnStore = returnStore;
	}

	public DriverBean getDriver() {
		return driver;
	}

	public void setDriver(DriverBean driver) {
		this.driver = driver;
	}

	public static class DriverBean {
		private int id;
		private String name;
		private String phone;
		private String sex;
		private int drivingYear;
		private String image;
		private double star;
		private int status;
		private String realName;
		private String firstName;
		private String lastName;
		private String idCardImage;
		private String drivingLicenseImage;
		private long idCardInvalidDate;
		private long drivingLicenseInvalidDate;
		private String idcard;

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

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public int getDrivingYear() {
			return drivingYear;
		}

		public void setDrivingYear(int drivingYear) {
			this.drivingYear = drivingYear;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public double getStar() {
			return star;
		}

		public void setStar(double star) {
			this.star = star;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getIdCardImage() {
			return idCardImage;
		}

		public void setIdCardImage(String idCardImage) {
			this.idCardImage = idCardImage;
		}

		public String getDrivingLicenseImage() {
			return drivingLicenseImage;
		}

		public void setDrivingLicenseImage(String drivingLicenseImage) {
			this.drivingLicenseImage = drivingLicenseImage;
		}

		public long getIdCardInvalidDate() {
			return idCardInvalidDate;
		}

		public void setIdCardInvalidDate(long idCardInvalidDate) {
			this.idCardInvalidDate = idCardInvalidDate;
		}

		public long getDrivingLicenseInvalidDate() {
			return drivingLicenseInvalidDate;
		}

		public void setDrivingLicenseInvalidDate(long drivingLicenseInvalidDate) {
			this.drivingLicenseInvalidDate = drivingLicenseInvalidDate;
		}

		public String getIdcard() {
			return idcard;
		}

		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}
	}

	public PlanEntity getPlan() {
		return plan;
	}

	public void setPlan(PlanEntity plan) {
		this.plan = plan;
	}

	public PrivilegeBean getPrivilege() {
		return privilege;
	}

	public void setPrivilege(PrivilegeBean privilege) {
		this.privilege = privilege;
	}

	public static class PrivilegeBean {
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

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public long getStartDate() {
			return startDate;
		}

		public void setStartDate(long startDate) {
			this.startDate = startDate;
		}

		public long getEndDate() {
			return endDate;
		}

		public void setEndDate(long endDate) {
			this.endDate = endDate;
		}

		public int getStartOfMonth() {
			return startOfMonth;
		}

		public void setStartOfMonth(int startOfMonth) {
			this.startOfMonth = startOfMonth;
		}

		public int getEndOfMonth() {
			return endOfMonth;
		}

		public void setEndOfMonth(int endOfMonth) {
			this.endOfMonth = endOfMonth;
		}

		public String getWeekdays() {
			return weekdays;
		}

		public void setWeekdays(String weekdays) {
			this.weekdays = weekdays;
		}

		public int getDiscountId() {
			return discountId;
		}

		public void setDiscountId(int discountId) {
			this.discountId = discountId;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public DiscountBean getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountBean discount) {
		this.discount = discount;
	}

	public static class DiscountBean {
		private int id;
		private String name;
		private int type;
		private int times;
		private double discount;
		private int status;

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

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getTimes() {
			return times;
		}

		public void setTimes(int times) {
			this.times = times;
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
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public static class OrderBean {
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
		private double tempPriceForMemberDiscount;

		public double getTempPriceForMemberDiscount() {
			return tempPriceForMemberDiscount;
		}

		public void setTempPriceForMemberDiscount(double tempPriceForMemberDiscount) {
			this.tempPriceForMemberDiscount = tempPriceForMemberDiscount;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCarId() {
			return carId;
		}

		public void setCarId(int carId) {
			this.carId = carId;
		}

		public int getPlanId() {
			return planId;
		}

		public void setPlanId(int planId) {
			this.planId = planId;
		}

		public long getZuchuDate() {
			return zuchuDate;
		}

		public void setZuchuDate(long zuchuDate) {
			this.zuchuDate = zuchuDate;
		}

		public long getHuancheDate() {
			return huancheDate;
		}

		public void setHuancheDate(long huancheDate) {
			this.huancheDate = huancheDate;
		}

		public long getPlanReturnDate() {
			return planReturnDate;
		}

		public void setPlanReturnDate(long planReturnDate) {
			this.planReturnDate = planReturnDate;
		}

		public double getLimitMileage() {
			return limitMileage;
		}

		public void setLimitMileage(double limitMileage) {
			this.limitMileage = limitMileage;
		}

		public int getAccountId() {
			return accountId;
		}

		public void setAccountId(int accountId) {
			this.accountId = accountId;
		}

		public int getGuarantorId() {
			return guarantorId;
		}

		public void setGuarantorId(int guarantorId) {
			this.guarantorId = guarantorId;
		}

		public double getBeforeMileage() {
			return beforeMileage;
		}

		public void setBeforeMileage(double beforeMileage) {
			this.beforeMileage = beforeMileage;
		}

		public double getAfterMileage() {
			return afterMileage;
		}

		public void setAfterMileage(double afterMileage) {
			this.afterMileage = afterMileage;
		}

		public double getJijiatime() {
			return jijiatime;
		}

		public void setJijiatime(double jijiatime) {
			this.jijiatime = jijiatime;
		}

		public double getTimePrice() {
			return timePrice;
		}

		public void setTimePrice(double timePrice) {
			this.timePrice = timePrice;
		}

		public double getOutMileagePrice() {
			return outMileagePrice;
		}

		public void setOutMileagePrice(double outMileagePrice) {
			this.outMileagePrice = outMileagePrice;
		}

		public double getOutTimePrice() {
			return outTimePrice;
		}

		public void setOutTimePrice(double outTimePrice) {
			this.outTimePrice = outTimePrice;
		}

		public double getRealPrice() {
			return realPrice;
		}

		public void setRealPrice(double realPrice) {
			this.realPrice = realPrice;
		}

		public double getShouyajin() {
			return shouyajin;
		}

		public void setShouyajin(double shouyajin) {
			this.shouyajin = shouyajin;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public int getDriverId() {
			return driverId;
		}

		public void setDriverId(int driverId) {
			this.driverId = driverId;
		}

		public int getHasDriver() {
			return hasDriver;
		}

		public void setHasDriver(int hasDriver) {
			this.hasDriver = hasDriver;
		}

		public int getGetCar() {
			return getCar;
		}

		public void setGetCar(int getCar) {
			this.getCar = getCar;
		}

		public int getReturnCar() {
			return returnCar;
		}

		public void setReturnCar(int returnCar) {
			this.returnCar = returnCar;
		}

		public double getAdvancePayment() {
			return advancePayment;
		}

		public void setAdvancePayment(double advancePayment) {
			this.advancePayment = advancePayment;
		}

		public long getDate() {
			return date;
		}

		public void setDate(long date) {
			this.date = date;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getSale() {
			return sale;
		}

		public void setSale(String sale) {
			this.sale = sale;
		}

		public String getInstitutionsCode() {
			return institutionsCode;
		}

		public void setInstitutionsCode(String institutionsCode) {
			this.institutionsCode = institutionsCode;
		}

		public int getHasFranchiseFees() {
			return hasFranchiseFees;
		}

		public void setHasFranchiseFees(int hasFranchiseFees) {
			this.hasFranchiseFees = hasFranchiseFees;
		}

		public long getSettledDate() {
			return settledDate;
		}

		public void setSettledDate(long settledDate) {
			this.settledDate = settledDate;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public double getOtherPrice() {
			return otherPrice;
		}

		public void setOtherPrice(double otherPrice) {
			this.otherPrice = otherPrice;
		}

		public double getYuPrice() {
			return yuPrice;
		}

		public void setYuPrice(double yuPrice) {
			this.yuPrice = yuPrice;
		}

		public int getMonthSettle() {
			return monthSettle;
		}

		public void setMonthSettle(int monthSettle) {
			this.monthSettle = monthSettle;
		}

		public double getReturnDeposit() {
			return returnDeposit;
		}

		public void setReturnDeposit(double returnDeposit) {
			this.returnDeposit = returnDeposit;
		}

		public double getFinalPrice() {
			return finalPrice;
		}

		public void setFinalPrice(double finalPrice) {
			this.finalPrice = finalPrice;
		}

		public double getFloatCost() {
			return floatCost;
		}

		public void setFloatCost(double floatCost) {
			this.floatCost = floatCost;
		}

		public int getIsSendCarToHome() {
			return isSendCarToHome;
		}

		public void setIsSendCarToHome(int isSendCarToHome) {
			this.isSendCarToHome = isSendCarToHome;
		}

		public int getIsGetCarFromHome() {
			return isGetCarFromHome;
		}

		public void setIsGetCarFromHome(int isGetCarFromHome) {
			this.isGetCarFromHome = isGetCarFromHome;
		}

		public String getSendCarAddress() {
			return sendCarAddress;
		}

		public void setSendCarAddress(String sendCarAddress) {
			this.sendCarAddress = sendCarAddress;
		}

		public String getGetCarAddress() {
			return getCarAddress;
		}

		public void setGetCarAddress(String getCarAddress) {
			this.getCarAddress = getCarAddress;
		}

		public int getHasTax() {
			return hasTax;
		}

		public void setHasTax(int hasTax) {
			this.hasTax = hasTax;
		}

		public int getHasLuo() {
			return hasLuo;
		}

		public void setHasLuo(int hasLuo) {
			this.hasLuo = hasLuo;
		}

		public int getPrivilegeId() {
			return privilegeId;
		}

		public void setPrivilegeId(int privilegeId) {
			this.privilegeId = privilegeId;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
		}

		public double getTempPriceForDiscount() {
			return tempPriceForDiscount;
		}

		public void setTempPriceForDiscount(double tempPriceForDiscount) {
			this.tempPriceForDiscount = tempPriceForDiscount;
		}

		public double getTempPriceForTax() {
			return tempPriceForTax;
		}

		public void setTempPriceForTax(double tempPriceForTax) {
			this.tempPriceForTax = tempPriceForTax;
		}

		public int getLeaseForOther() {
			return leaseForOther;
		}

		public void setLeaseForOther(int leaseForOther) {
			this.leaseForOther = leaseForOther;
		}

		public String getPhoneOfUser() {
			return phoneOfUser;
		}

		public void setPhoneOfUser(String phoneOfUser) {
			this.phoneOfUser = phoneOfUser;
		}

		public double getDriverCost() {
			return driverCost;
		}

		public void setDriverCost(double driverCost) {
			this.driverCost = driverCost;
		}
	}

	public static class CarEntity {
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

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLicensePlate() {
			return licensePlate;
		}

		public void setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getBox() {
			return box;
		}

		public void setBox(String box) {
			this.box = box;
		}

		public String getPailiang() {
			return pailiang;
		}

		public void setPailiang(String pailiang) {
			this.pailiang = pailiang;
		}

		public int getSeat() {
			return seat;
		}

		public void setSeat(int seat) {
			this.seat = seat;
		}

		public int getZidong() {
			return zidong;
		}

		public void setZidong(int zidong) {
			this.zidong = zidong;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getEngineCode() {
			return engineCode;
		}

		public void setEngineCode(String engineCode) {
			this.engineCode = engineCode;
		}

		public double getMileage() {
			return mileage;
		}

		public void setMileage(double mileage) {
			this.mileage = mileage;
		}

		public double getMaintenanceMileage() {
			return maintenanceMileage;
		}

		public void setMaintenanceMileage(double maintenanceMileage) {
			this.maintenanceMileage = maintenanceMileage;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public double getDeposit() {
			return deposit;
		}

		public void setDeposit(double deposit) {
			this.deposit = deposit;
		}

		public String getBuyDate() {
			return buyDate;
		}

		public void setBuyDate(String buyDate) {
			this.buyDate = buyDate;
		}

		public String getInspection() {
			return inspection;
		}

		public void setInspection(String inspection) {
			this.inspection = inspection;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public int getPlanId() {
			return planId;
		}

		public void setPlanId(int planId) {
			this.planId = planId;
		}

		public int getLei() {
			return lei;
		}

		public void setLei(int lei) {
			this.lei = lei;
		}

		public int getStoreId() {
			return storeId;
		}

		public void setStoreId(int storeId) {
			this.storeId = storeId;
		}

		public String getLicensePlateColor() {
			return licensePlateColor;
		}

		public void setLicensePlateColor(String licensePlateColor) {
			this.licensePlateColor = licensePlateColor;
		}

		public double getLastMaintenanceMileage() {
			return lastMaintenanceMileage;
		}

		public void setLastMaintenanceMileage(double lastMaintenanceMileage) {
			this.lastMaintenanceMileage = lastMaintenanceMileage;
		}

		public Object getMessage() {
			return message;
		}

		public void setMessage(Object message) {
			this.message = message;
		}

		public Object getVehicleLicenseImg() {
			return vehicleLicenseImg;
		}

		public void setVehicleLicenseImg(Object vehicleLicenseImg) {
			this.vehicleLicenseImg = vehicleLicenseImg;
		}

		public Object getVehicleLicenseInvalidDate() {
			return vehicleLicenseInvalidDate;
		}

		public void setVehicleLicenseInvalidDate(Object vehicleLicenseInvalidDate) {
			this.vehicleLicenseInvalidDate = vehicleLicenseInvalidDate;
		}
	}

	public static class GetCarStoreEntity {
		private int id;
		private String name;
		private String address;
		private String city;
		private String phone;
		private String head;
		private double longitude;
		private double latitude;
		private int status;

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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getHead() {
			return head;
		}

		public void setHead(String head) {
			this.head = head;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class ReturnStoreEntity {
		private int id;
		private String name;
		private String address;
		private String city;
		private String phone;
		private String head;
		private double longitude;
		private double latitude;
		private int status;

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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getHead() {
			return head;
		}

		public void setHead(String head) {
			this.head = head;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}

	public static class PlanEntity {
		private int id;
		private String name;
		private double price;
		private double unitMileage;
		private double outMileage;
		private double outTime;
		private int flag;
		private int jijia;
		private double insurance;
		private double hasDriver;
		private double others;
		private double poundage;
		private double franchiseFees;
		private double floatPrice;
		private double sendCarToHome;
		private double getCarFromHome;
		private double discount;
		private double tax;

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

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getUnitMileage() {
			return unitMileage;
		}

		public void setUnitMileage(double unitMileage) {
			this.unitMileage = unitMileage;
		}

		public double getOutMileage() {
			return outMileage;
		}

		public void setOutMileage(double outMileage) {
			this.outMileage = outMileage;
		}

		public double getOutTime() {
			return outTime;
		}

		public void setOutTime(double outTime) {
			this.outTime = outTime;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public int getJijia() {
			return jijia;
		}

		public void setJijia(int jijia) {
			this.jijia = jijia;
		}

		public double getInsurance() {
			return insurance;
		}

		public void setInsurance(double insurance) {
			this.insurance = insurance;
		}

		public double getHasDriver() {
			return hasDriver;
		}

		public void setHasDriver(double hasDriver) {
			this.hasDriver = hasDriver;
		}

		public double getOthers() {
			return others;
		}

		public void setOthers(double others) {
			this.others = others;
		}

		public double getPoundage() {
			return poundage;
		}

		public void setPoundage(double poundage) {
			this.poundage = poundage;
		}

		public double getFranchiseFees() {
			return franchiseFees;
		}

		public void setFranchiseFees(double franchiseFees) {
			this.franchiseFees = franchiseFees;
		}

		public double getFloatPrice() {
			return floatPrice;
		}

		public void setFloatPrice(double floatPrice) {
			this.floatPrice = floatPrice;
		}

		public double getSendCarToHome() {
			return sendCarToHome;
		}

		public void setSendCarToHome(double sendCarToHome) {
			this.sendCarToHome = sendCarToHome;
		}

		public double getGetCarFromHome() {
			return getCarFromHome;
		}

		public void setGetCarFromHome(double getCarFromHome) {
			this.getCarFromHome = getCarFromHome;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
		}

		public double getTax() {
			return tax;
		}

		public void setTax(double tax) {
			this.tax = tax;
		}
	}

	public static class AccountEntity {
		private int id;
		private String name;
		private String password;
		private Object sex;
		private String loginId;
		private Object realName;
		private Object cardStatus;
		private Object bankCard;
		private String phone;
		private Object address;
		private String image;
		private Object bankCardStatus;
		private Object note;
		private String login_id;
		private int flag;
		private Object idCardImage;
		private Object idCardImage_back;
		private Object drivingLicenseImage;
		private Object drivingLicenseImage_back;
		private Object idCardInvalidDate;
		private Object drivingLicenseInvalidDate;
		private int memberId;
		private Object idcard;

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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Object getSex() {
			return sex;
		}

		public void setSex(Object sex) {
			this.sex = sex;
		}

		public String getLoginId() {
			return loginId;
		}

		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}

		public Object getRealName() {
			return realName;
		}

		public void setRealName(Object realName) {
			this.realName = realName;
		}

		public Object getCardStatus() {
			return cardStatus;
		}

		public void setCardStatus(Object cardStatus) {
			this.cardStatus = cardStatus;
		}

		public Object getBankCard() {
			return bankCard;
		}

		public void setBankCard(Object bankCard) {
			this.bankCard = bankCard;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Object getAddress() {
			return address;
		}

		public void setAddress(Object address) {
			this.address = address;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Object getBankCardStatus() {
			return bankCardStatus;
		}

		public void setBankCardStatus(Object bankCardStatus) {
			this.bankCardStatus = bankCardStatus;
		}

		public Object getNote() {
			return note;
		}

		public void setNote(Object note) {
			this.note = note;
		}

		public String getLogin_id() {
			return login_id;
		}

		public void setLogin_id(String login_id) {
			this.login_id = login_id;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public Object getIdCardImage() {
			return idCardImage;
		}

		public void setIdCardImage(Object idCardImage) {
			this.idCardImage = idCardImage;
		}

		public Object getIdCardImage_back() {
			return idCardImage_back;
		}

		public void setIdCardImage_back(Object idCardImage_back) {
			this.idCardImage_back = idCardImage_back;
		}

		public Object getDrivingLicenseImage() {
			return drivingLicenseImage;
		}

		public void setDrivingLicenseImage(Object drivingLicenseImage) {
			this.drivingLicenseImage = drivingLicenseImage;
		}

		public Object getDrivingLicenseImage_back() {
			return drivingLicenseImage_back;
		}

		public void setDrivingLicenseImage_back(Object drivingLicenseImage_back) {
			this.drivingLicenseImage_back = drivingLicenseImage_back;
		}

		public Object getIdCardInvalidDate() {
			return idCardInvalidDate;
		}

		public void setIdCardInvalidDate(Object idCardInvalidDate) {
			this.idCardInvalidDate = idCardInvalidDate;
		}

		public Object getDrivingLicenseInvalidDate() {
			return drivingLicenseInvalidDate;
		}

		public void setDrivingLicenseInvalidDate(Object drivingLicenseInvalidDate) {
			this.drivingLicenseInvalidDate = drivingLicenseInvalidDate;
		}

		public int getMemberId() {
			return memberId;
		}

		public void setMemberId(int memberId) {
			this.memberId = memberId;
		}

		public Object getIdcard() {
			return idcard;
		}

		public void setIdcard(Object idcard) {
			this.idcard = idcard;
		}
	}

	public static class MemberEntity {
		private int id;
		private String name;
		private double discount;
		private int level;
		private double ceiling;
		private int status;

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

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}
	}
}
