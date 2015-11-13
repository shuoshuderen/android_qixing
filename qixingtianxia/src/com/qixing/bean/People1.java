package com.qixing.bean;

import java.io.Serializable;

public class People1 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;
	private String username;
	private String gender;
	private int age;
	private String userimg;
	private double totaldistance;
	private String totaltime;
	private double latiudt;//纬度
	private double longitude;//经度
	private double distance;//两个用户之间的距离
	
	public People1() {
		super();
	}

	
	public People1(String userid, String username, String gender, int age,
			String userimg, double totaldistance, String totaltime,
			double latiudt, double longitude, double distance) {
		super();
		this.userid = userid;
		this.username = username;
		this.gender = gender;
		this.age = age;
		this.userimg = userimg;
		this.totaldistance = totaldistance;
		this.totaltime = totaltime;
		this.latiudt = latiudt;
		this.longitude = longitude;
		this.distance = distance;
	}


	public People1(String userid, String username, String gender, int age,
			String userimg, double totaldistance, String totaltime,
			double latiudt, double longitude) {
		super();
		this.userid = userid;
		this.username = username;
		this.gender = gender;
		this.age = age;
		this.userimg = userimg;
		this.totaldistance = totaldistance;
		this.totaltime = totaltime;
		this.latiudt = latiudt;
		this.longitude = longitude;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUserimg() {
		return userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	public double getTotaldistance() {
		return totaldistance;
	}

	public void setTotaldistance(double totaldistance) {
		this.totaldistance = totaldistance;
	}

	public String getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}

	public double getLatiudt() {
		return latiudt;
	}

	public void setLatiudt(double latiudt) {
		this.latiudt = latiudt;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		People1 other = (People1) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "People [userid=" + userid + ", username=" + username
				+ ", gender=" + gender + ", age=" + age + ", userimg="
				+ userimg + ", totaldistance=" + totaldistance + ", totaltime="
				+ totaltime + ", latiudt=" + latiudt + ", longitude="
				+ longitude + "]";
	}

}
