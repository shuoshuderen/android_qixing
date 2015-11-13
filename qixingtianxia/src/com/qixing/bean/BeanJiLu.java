package com.qixing.bean;

import java.io.Serializable;

public class BeanJiLu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int recodeid;
	private String userid;
	private double  mileage;//里程
	private String alltime;
	private String time;
	public BeanJiLu(int recodeid, String userid, double mileage,
			String alltime, String time) {
		super();
		this.recodeid = recodeid;
		this.userid = userid;
		this.mileage = mileage;
		this.alltime = alltime;
		this.time = time;
	}
	public int getRecodeid() {
		return recodeid;
	}
	public void setRecodeid(int recodeid) {
		this.recodeid = recodeid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public String getAlltime() {
		return alltime;
	}
	public void setAlltime(String alltime) {
		this.alltime = alltime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "BeanJiLu [recodeid=" + recodeid + ", userid=" + userid
				+ ", mileage=" + mileage + ", alltime=" + alltime + ", time="
				+ time + "]";
	}
	

	
}
