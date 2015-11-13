package com.qixing.bean;

import java.io.Serializable;

public class BeanTeam implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int teamid;//车队id
	private String userid;//创建人id(外键是用户id)?是不是String
	private String teamname;//车队名字
	private String teamimage;//车队头像
	private String teamtime;//创建时间
	private String teamaddress;//创建地点
	private double teamdistance;//车队总里程
	private int teamnumber;//车队人数
	private String teamslogan;//口号
	private String teamintroduce;//介绍
	private String teaminform;//公告
	private String username;
	
	public BeanTeam() {
		super();
	}

	

	public BeanTeam(int teamid, String userid, String teamname,
			String teamimage, String teamtime, String teamaddress,
			double teamdistance, int teamnumber, String teamslogan,
			String teamintroduce, String teaminform, String username) {
		super();
		this.teamid = teamid;
		this.userid = userid;
		this.teamname = teamname;
		this.teamimage = teamimage;
		this.teamtime = teamtime;
		this.teamaddress = teamaddress;
		this.teamdistance = teamdistance;
		this.teamnumber = teamnumber;
		this.teamslogan = teamslogan;
		this.teamintroduce = teamintroduce;
		this.teaminform = teaminform;
		this.username = username;
	}



	public int getTeamid() {
		return teamid;
	}

	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamimage() {
		return teamimage;
	}

	public void setTeamimage(String teamimage) {
		this.teamimage = teamimage;
	}

	public String getTeamtime() {
		return teamtime;
	}

	public void setTeamtime(String teamtime) {
		this.teamtime = teamtime;
	}

	public String getTeamaddress() {
		return teamaddress;
	}

	public void setTeamaddress(String teamaddress) {
		this.teamaddress = teamaddress;
	}

	public double getTeamdistance() {
		return teamdistance;
	}

	public void setTeamdistance(double teamdistance) {
		this.teamdistance = teamdistance;
	}

	public int getTeamnumber() {
		return teamnumber;
	}

	public void setTeamnumber(int teamnumber) {
		this.teamnumber = teamnumber;
	}

	public String getTeamslogan() {
		return teamslogan;
	}

	public void setTeamslogan(String teamslogan) {
		this.teamslogan = teamslogan;
	}

	public String getTeamintroduce() {
		return teamintroduce;
	}

	public void setTeamintroduce(String teamintroduce) {
		this.teamintroduce = teamintroduce;
	}

	public String getTeaminform() {
		return teaminform;
	}

	public void setTeaminform(String teaminform) {
		this.teaminform = teaminform;
	}

	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	@Override
	public String toString() {
		return "BeanTeam [teamid=" + teamid + ", userid=" + userid
				+ ", teamname=" + teamname + ", teamimage=" + teamimage
				+ ", teamtime=" + teamtime + ", teamaddress=" + teamaddress
				+ ", teamdistance=" + teamdistance + ", teamnumber="
				+ teamnumber + ", teamslogan=" + teamslogan
				+ ", teamintroduce=" + teamintroduce + ", teaminform="
				+ teaminform + ", username=" + username + "]";
	}



}
