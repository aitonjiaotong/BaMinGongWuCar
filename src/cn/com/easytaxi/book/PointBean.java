package cn.com.easytaxi.book;

import cn.com.easytaxi.util.ImTools;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointBean {
	@Expose
	@SerializedName("lng")
	private double lng;
	@Expose
	@SerializedName("lat")
	private double lat;
	@Expose
	@SerializedName("id")
	private long id;
	@Expose
	@SerializedName("address")
	private String address;
	@Expose
	@SerializedName("datetime")
	private String datetime;
	@Expose
	@SerializedName("book_id")
	private long book_id;

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getAddress() {
		return ImTools.getValueRemoveNull(address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBook_id() {
		return book_id;
	}

	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}

}
