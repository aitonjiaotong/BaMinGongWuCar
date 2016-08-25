package passenger.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zjb on 2016/3/19.
 */
public class CarInfoList implements Serializable
{


    /**
     * contains : [{"car":{"id":null,"licensePlate":null,"model":"北京现代","type":"途胜","box":"3厢","pailiang":"1975ml","seat":5,"zidong":0,"color":null,"engineCode":null,"mileage":null,"maintenanceMileage":null,"status":0,"deposit":null,"buyDate":null,"inspection":null,"image":"http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/e2dc07690a11d34a73d606b8342c06a8.png","note":null,"planId":1,"lei":null,"storeId":null,"licensePlateColor":null,"lastMaintenanceMileage":null,"message":null},"plan":{"id":1,"name":"公务一型","price":200,"unitMileage":100,"outMileage":10,"outTime":3,"flag":0,"jijia":0,"insurance":50,"hasDriver":40,"others":0,"poundage":0,"franchiseFees":20,"floatPrice":2,"sendCarToHome":40,"getCarFromHome":40,"discount":0.9,"tax":1.03}},{"car":{"id":null,"licensePlate":null,"model":"东风悦达起亚","type":"K2","box":"3厢","pailiang":"1396ml","seat":5,"zidong":0,"color":null,"engineCode":null,"mileage":null,"maintenanceMileage":null,"status":0,"deposit":null,"buyDate":null,"inspection":null,"image":"http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/4ad72b604e0cfa4a9b1621d81f09f43e.jpg","note":null,"planId":2,"lei":null,"storeId":null,"licensePlateColor":null,"lastMaintenanceMileage":null,"message":null},"plan":{"id":2,"name":"公务二型","price":150,"unitMileage":3000,"outMileage":9.5,"outTime":2.5,"flag":0,"jijia":0,"insurance":50,"hasDriver":60,"others":0,"poundage":0,"franchiseFees":30,"floatPrice":2.5,"sendCarToHome":50,"getCarFromHome":50,"discount":0.85,"tax":1.05}}]
     * num : 1
     */

    private int num;
    /**
     * car : {"id":null,"licensePlate":null,"model":"北京现代","type":"途胜","box":"3厢","pailiang":"1975ml","seat":5,"zidong":0,"color":null,"engineCode":null,"mileage":null,"maintenanceMileage":null,"status":0,"deposit":null,"buyDate":null,"inspection":null,"image":"http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/e2dc07690a11d34a73d606b8342c06a8.png","note":null,"planId":1,"lei":null,"storeId":null,"licensePlateColor":null,"lastMaintenanceMileage":null,"message":null}
     * plan : {"id":1,"name":"公务一型","price":200,"unitMileage":100,"outMileage":10,"outTime":3,"flag":0,"jijia":0,"insurance":50,"hasDriver":40,"others":0,"poundage":0,"franchiseFees":20,"floatPrice":2,"sendCarToHome":40,"getCarFromHome":40,"discount":0.9,"tax":1.03}
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

    public static class ContainsBean implements Serializable
    {
        /**
         * id : null
         * licensePlate : null
         * model : 北京现代
         * type : 途胜
         * box : 3厢
         * pailiang : 1975ml
         * seat : 5
         * zidong : 0
         * color : null
         * engineCode : null
         * mileage : null
         * maintenanceMileage : null
         * status : 0
         * deposit : null
         * buyDate : null
         * inspection : null
         * image : http://bmcx.oss-cn-shanghai.aliyuncs.com/cars/e2dc07690a11d34a73d606b8342c06a8.png
         * note : null
         * planId : 1
         * lei : null
         * storeId : null
         * licensePlateColor : null
         * lastMaintenanceMileage : null
         * message : null
         */

        private CarBean car;
        /**
         * id : 1
         * name : 公务一型
         * price : 200.0
         * unitMileage : 100.0
         * outMileage : 10.0
         * outTime : 3.0
         * flag : 0
         * jijia : 0
         * insurance : 50.0
         * hasDriver : 40.0
         * others : 0.0
         * poundage : 0.0
         * franchiseFees : 20.0
         * floatPrice : 2.0
         * sendCarToHome : 40.0
         * getCarFromHome : 40.0
         * discount : 0.9
         * tax : 1.03
         */

        private PlanBean plan;

        public CarBean getCar()
        {
            return car;
        }

        public void setCar(CarBean car)
        {
            this.car = car;
        }

        public PlanBean getPlan()
        {
            return plan;
        }

        public void setPlan(PlanBean plan)
        {
            this.plan = plan;
        }

        public static class CarBean implements Serializable
        {
            private Object id;
            private Object licensePlate;
            private String model;
            private String type;
            private String box;
            private String pailiang;
            private int seat;
            private int zidong;
            private Object color;
            private Object engineCode;
            private Object mileage;
            private Object maintenanceMileage;
            private int status;
            private Object deposit;
            private Object buyDate;
            private Object inspection;
            private String image;
            private Object note;
            private int planId;
            private Object lei;
            private Object storeId;
            private Object licensePlateColor;
            private Object lastMaintenanceMileage;
            private Object message;

            public Object getId()
            {
                return id;
            }

            public void setId(Object id)
            {
                this.id = id;
            }

            public Object getLicensePlate()
            {
                return licensePlate;
            }

            public void setLicensePlate(Object licensePlate)
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

            public Object getColor()
            {
                return color;
            }

            public void setColor(Object color)
            {
                this.color = color;
            }

            public Object getEngineCode()
            {
                return engineCode;
            }

            public void setEngineCode(Object engineCode)
            {
                this.engineCode = engineCode;
            }

            public Object getMileage()
            {
                return mileage;
            }

            public void setMileage(Object mileage)
            {
                this.mileage = mileage;
            }

            public Object getMaintenanceMileage()
            {
                return maintenanceMileage;
            }

            public void setMaintenanceMileage(Object maintenanceMileage)
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

            public Object getDeposit()
            {
                return deposit;
            }

            public void setDeposit(Object deposit)
            {
                this.deposit = deposit;
            }

            public Object getBuyDate()
            {
                return buyDate;
            }

            public void setBuyDate(Object buyDate)
            {
                this.buyDate = buyDate;
            }

            public Object getInspection()
            {
                return inspection;
            }

            public void setInspection(Object inspection)
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

            public Object getNote()
            {
                return note;
            }

            public void setNote(Object note)
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

            public Object getLei()
            {
                return lei;
            }

            public void setLei(Object lei)
            {
                this.lei = lei;
            }

            public Object getStoreId()
            {
                return storeId;
            }

            public void setStoreId(Object storeId)
            {
                this.storeId = storeId;
            }

            public Object getLicensePlateColor()
            {
                return licensePlateColor;
            }

            public void setLicensePlateColor(Object licensePlateColor)
            {
                this.licensePlateColor = licensePlateColor;
            }

            public Object getLastMaintenanceMileage()
            {
                return lastMaintenanceMileage;
            }

            public void setLastMaintenanceMileage(Object lastMaintenanceMileage)
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
        }

        public static class PlanBean implements Serializable
        {
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

            public double getPrice()
            {
                return price;
            }

            public void setPrice(double price)
            {
                this.price = price;
            }

            public double getUnitMileage()
            {
                return unitMileage;
            }

            public void setUnitMileage(double unitMileage)
            {
                this.unitMileage = unitMileage;
            }

            public double getOutMileage()
            {
                return outMileage;
            }

            public void setOutMileage(double outMileage)
            {
                this.outMileage = outMileage;
            }

            public double getOutTime()
            {
                return outTime;
            }

            public void setOutTime(double outTime)
            {
                this.outTime = outTime;
            }

            public int getFlag()
            {
                return flag;
            }

            public void setFlag(int flag)
            {
                this.flag = flag;
            }

            public int getJijia()
            {
                return jijia;
            }

            public void setJijia(int jijia)
            {
                this.jijia = jijia;
            }

            public double getInsurance()
            {
                return insurance;
            }

            public void setInsurance(double insurance)
            {
                this.insurance = insurance;
            }

            public double getHasDriver()
            {
                return hasDriver;
            }

            public void setHasDriver(double hasDriver)
            {
                this.hasDriver = hasDriver;
            }

            public double getOthers()
            {
                return others;
            }

            public void setOthers(double others)
            {
                this.others = others;
            }

            public double getPoundage()
            {
                return poundage;
            }

            public void setPoundage(double poundage)
            {
                this.poundage = poundage;
            }

            public double getFranchiseFees()
            {
                return franchiseFees;
            }

            public void setFranchiseFees(double franchiseFees)
            {
                this.franchiseFees = franchiseFees;
            }

            public double getFloatPrice()
            {
                return floatPrice;
            }

            public void setFloatPrice(double floatPrice)
            {
                this.floatPrice = floatPrice;
            }

            public double getSendCarToHome()
            {
                return sendCarToHome;
            }

            public void setSendCarToHome(double sendCarToHome)
            {
                this.sendCarToHome = sendCarToHome;
            }

            public double getGetCarFromHome()
            {
                return getCarFromHome;
            }

            public void setGetCarFromHome(double getCarFromHome)
            {
                this.getCarFromHome = getCarFromHome;
            }

            public double getDiscount()
            {
                return discount;
            }

            public void setDiscount(double discount)
            {
                this.discount = discount;
            }

            public double getTax()
            {
                return tax;
            }

            public void setTax(double tax)
            {
                this.tax = tax;
            }
        }
    }
}
