package cn.com.easytaxi.mine.account;

import com.google.gson.annotations.SerializedName;

/**
 * 金额
 * 
 * @ClassName: EtMoneyBean
 * @Description: TODO
 * @author Brook xu
 * @date 2013-7-31 下午4:50:45
 * @version 1.0
 */
public class EtMoneyBean extends Account {

	public EtMoneyBean() {
		super(Account.TYPE_ET_MONEY);
		// TODO Auto-generated constructor stub
	}

	@SerializedName("jyMoney")
	private int etMoney;

	public int getEtMoney() {
		return etMoney;
	}

	public void setEtMoney(int etMoney) {
		this.etMoney = etMoney;
	}

	@Override
	public String toString() {
		return "EtMoneyBean [etMoney=" + etMoney + super.toString() + "]";
	}
}
