package cn.com.easytaxi.workpool.bean;

import java.io.Serializable;

public class GeoPointLable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2257227318326959484L;
	private long lat;
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	private long log;
	private String name;
	private String cityName;
	
	private boolean isDelete = true;

	public GeoPointLable disDelate() {
		this.isDelete = false;
		return this;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public long getLat() {
		return lat;
	}

	public void setLat(long lat) {
		this.lat = lat;
	}

	public long getLog() {
		return log;
	}

	public void setLog(long log) {
		this.log = log;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeoPointLable(long lat, long log, String name,String cityName) {
		super();
		this.lat = lat;
		this.log = log;
		this.name = name;
		this.cityName=cityName;
	}

	@Override
	public String toString() {
		return name;
	}
}
