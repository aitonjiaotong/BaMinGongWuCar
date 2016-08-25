package cn.com.easytaxi.onetaxi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityBean {
	@Expose
	@SerializedName("name")
	private String City;
	@Expose
	@SerializedName("id")
	private String CityId;
	@Expose
	@SerializedName("carNumber")
	private String carNumber;

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCityId() {
		return CityId;
	}

	public void setCityId(String cityId) {
		CityId = cityId;
	}

}
