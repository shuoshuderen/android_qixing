package com.qixing.bean;

import java.io.Serializable;
import java.util.List;

public class BeanWay implements Serializable {

	private static final long serialVersionUID = 1L;
	private int wayId;
	private int start1;
	private int start2;
	private int start3;
	private String wayDatatime;
	private String wayName;
	private String wayDecription;
	private int wayZambia;
	List<BeanWayImage> list;

	public BeanWay() {
		super();
	}

	public BeanWay(int wayId, int start1, int start2, int start3,
			String wayDatatime, String wayName, String wayDecription,
			int wayZambia, List<BeanWayImage> list) {
		super();
		this.wayId = wayId;
		this.start1 = start1;
		this.start2 = start2;
		this.start3 = start3;
		this.wayDatatime = wayDatatime;
		this.wayName = wayName;
		this.wayDecription = wayDecription;
		this.wayZambia = wayZambia;
		this.list = list;
	}

	public int getWayId() {
		return wayId;
	}

	public void setWayId(int wayId) {
		this.wayId = wayId;
	}

	public int getStart1() {
		return start1;
	}

	public void setStart1(int start1) {
		this.start1 = start1;
	}

	public int getStart2() {
		return start2;
	}

	public void setStart2(int start2) {
		this.start2 = start2;
	}

	public int getStart3() {
		return start3;
	}

	public void setStart3(int start3) {
		this.start3 = start3;
	}

	public String getWayDatatime() {
		return wayDatatime;
	}

	public void setWayDatatime(String wayDatatime) {
		this.wayDatatime = wayDatatime;
	}

	public String getWayName() {
		return wayName;
	}

	public void setWayName(String wayName) {
		this.wayName = wayName;
	}

	public String getWayDecription() {
		return wayDecription;
	}

	public void setWayDecription(String wayDecription) {
		this.wayDecription = wayDecription;
	}

	public int getWayZambia() {
		return wayZambia;
	}

	public void setWayZambia(int wayZambia) {
		this.wayZambia = wayZambia;
	}

	public List<BeanWayImage> getList() {
		return list;
	}

	public void setList(List<BeanWayImage> list) {
		this.list = list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + wayId;
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
		BeanWay other = (BeanWay) obj;
		if (wayId != other.wayId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeanWay [wayId=" + wayId + ", start1=" + start1 + ", start2="
				+ start2 + ", start3=" + start3 + ", wayDatatime="
				+ wayDatatime + ", wayName=" + wayName + ", wayDecription="
				+ wayDecription + ", wayZambia=" + wayZambia + ", list=" + list
				+ "]";
	}

}
