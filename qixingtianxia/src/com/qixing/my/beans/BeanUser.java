package com.qixing.my.beans;

import java.io.Serializable;

public class BeanUser extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * userid 账号 int username 用户名 varchar password 登陆密码 varchar
	 * 
	 * gender 性别 char
	 * 
	 * age 年龄 int userimg 头像 varcher address 住址 varchar totaldistance 总里程 Double
	 * 总时间 latiudt 维度 double longitude 经度 double
	 */
	private String userid;
	private String username;
	private String password;
	private String gender;
	private int age;
	private String userimg;
	private String address;
	private double totaldistance;
	private String totaltime;
	private double latiudt;
	private double longitude;
	//无参构造方法
	public BeanUser() {
		super();
	}
	//参数只有可以手动修改的几个
	public BeanUser(String userid, String username, String gender, int age,
			String address) {
		super();
		this.userid = userid;
		this.username = username;
		this.gender = gender;
		this.age = age;
		this.address = address;
	}
	//查询的信息
	
	public String getUserid() {
		return userid;
	}
	public BeanUser(String userid, String username, String gender, int age,
			String userimg, String address, double totaldistance,
			String totaltime) {
		super();
		this.userid = userid;
		this.username = username;
		this.gender = gender;
		this.age = age;
		this.userimg = userimg;
		this.address = address;
		this.totaldistance = totaldistance;
		this.totaltime = totaltime;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	@Override
	public String toString() {
		return "BeanUser [userid=" + userid + ", username=" + username
				+ ", password=" + password + ", gender=" + gender + ", age="
				+ age + ", userimg=" + userimg + ", address=" + address
				+ ", totaldistance=" + totaldistance + ", totaltime="
				+ totaltime + ", latiudt=" + latiudt + ", longitude="
				+ longitude + "]";
	}
	

}
