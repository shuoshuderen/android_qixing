package com.qixing.bean;

import java.io.Serializable;

public class BeanWayImage implements Serializable {

	private static final long serialVersionUID = 1L;
	private int wayimageid;
	private String wayimageroot;
	private int wayid;
	
	
	public BeanWayImage() {
		super();
	}


	public BeanWayImage(String wayimageroot) {
		super();
		this.wayimageroot = wayimageroot;
	}


	public BeanWayImage(int wayimageid, String wayimageroot, int wayid) {
		super();
		this.wayimageid = wayimageid;
		this.wayimageroot = wayimageroot;
		this.wayid = wayid;
	}


	public int getWayimageid() {
		return wayimageid;
	}


	public void setWayimageid(int wayimageid) {
		this.wayimageid = wayimageid;
	}


	public String getWayimageroot() {
		return wayimageroot;
	}


	public void setWayimageroot(String wayimageroot) {
		this.wayimageroot = wayimageroot;
	}


	public int getWayid() {
		return wayid;
	}


	public void setWayid(int wayid) {
		this.wayid = wayid;
	}


	@Override
	public String toString() {
		return "BeanWayImage [wayimageroot=" + wayimageroot + "]";
	}


	
	
}
