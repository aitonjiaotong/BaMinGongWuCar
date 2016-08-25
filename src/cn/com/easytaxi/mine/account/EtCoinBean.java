package cn.com.easytaxi.mine.account;

import com.google.gson.annotations.SerializedName;

/**
 * 易达币
 * 
 * @ClassName: EtCoinBean
 * @Description: TODO
 * @author Brook xu
 * @date 2013-7-31 下午4:50:29
 * @version 1.0
 */
public class EtCoinBean extends Account {

	public EtCoinBean() {
		super(Account.TYPE_ET_COIN);
		// TODO Auto-generated constructor stub
	}

	@SerializedName("jyMoney")
	private int etCoin;

	public int getEtCoin() {
		return etCoin;
	}

	public void setEtCoin(int etCoin) {
		this.etCoin = etCoin;
	}

	@Override
	public String toString() {
		return "EtCoinBean [etCoin=" + etCoin + super.toString() + "]";
	}
}
