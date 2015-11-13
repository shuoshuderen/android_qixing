package com.qixing.bean;

import java.io.Serializable;
public class BeanHelp implements Serializable {

	/**
	 * 发起求救的人的具体信息
	 */
	private static final long serialVersionUID = 1L;
	private int seekhelpid;
	private String userId;
	private String name;
	private String helpContent;
	private String helpAddress;
	private double helpLongitude;
	private double helpLatitude;
	private	String time;
	
	public BeanHelp() {
		super();
	}



	public BeanHelp(int seekhelpid, String userId, String name,
			String helpContent, String helpAddress, double helpLongitude,
			double helpLatitude, String time) {
		super();
		this.seekhelpid = seekhelpid;
		this.userId = userId;
		this.name = name;
		this.helpContent = helpContent;
		this.helpAddress = helpAddress;
		this.helpLongitude = helpLongitude;
		this.helpLatitude = helpLatitude;
		this.time = time;
	}



	public int getSeekhelpid() {
		return seekhelpid;
	}



	public void setSeekhelpid(int seekhelpid) {
		this.seekhelpid = seekhelpid;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getHelpContent() {
		return helpContent;
	}



	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}



	public String getHelpAddress() {
		return helpAddress;
	}



	public void setHelpAddress(String helpAddress) {
		this.helpAddress = helpAddress;
	}



	public double getHelpLongitude() {
		return helpLongitude;
	}



	public void setHelpLongitude(double helpLongitude) {
		this.helpLongitude = helpLongitude;
	}



	public double getHelpLatitude() {
		return helpLatitude;
	}



	public void setHelpLatitude(double helpLatitude) {
		this.helpLatitude = helpLatitude;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	@Override
	public String toString() {
		return "BeanHelp [seekhelpid=" + seekhelpid + ", userId=" + userId
				+ ", name=" + name + ", helpContent=" + helpContent
				+ ", helpAddress=" + helpAddress + ", helpLongitude="
				+ helpLongitude + ", helpLatitude=" + helpLatitude + ", time="
				+ time + "]";
	}




}
