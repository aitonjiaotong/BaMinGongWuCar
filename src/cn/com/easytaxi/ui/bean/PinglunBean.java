package cn.com.easytaxi.ui.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinglunBean {
	/**
	 * 评分
	 */
	@Expose
	@SerializedName("plResult")
	private int plResult;
	/**
	 * 司机头像
	 */
	@Expose
	@SerializedName("avatar_image")
	private String avatar_image;
	/**
	 * 司机名称
	 */
	@Expose
	@SerializedName("driver_name")
	private String driver_name;
	/**
	 * 评价时间
	 */
	@Expose
	@SerializedName("assess_time")
	private String assess_time;

	public int getPlResult() {
		return plResult;
	}

	public void setPlResult(int plResult) {
		this.plResult = plResult;
	}

	public String getAvatar_image() {
		return avatar_image;
	}

	public void setAvatar_image(String avatar_image) {
		this.avatar_image = avatar_image;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getAssess_time() {
		return assess_time;
	}

	public void setAssess_time(String assess_time) {
		this.assess_time = assess_time;
	}

}
