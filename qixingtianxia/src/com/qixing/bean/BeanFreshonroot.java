package com.qixing.bean;

import java.io.Serializable;
import java.util.List;

public class BeanFreshonroot implements Serializable {

	private static final long serialVersionUID = 1L;
	private int freshonrootid;
	private String freshtitle;
	private String freshmaincontent;
	private int freshzan;
	private String maininform;
	List<String> list;

	public BeanFreshonroot() {
		super();
	}

	public BeanFreshonroot(int freshonrootid, String freshtitle,
			String freshmaincontent, int freshzan, String maininform,
			List<String> list) {
		super();
		this.freshonrootid = freshonrootid;
		this.freshtitle = freshtitle;
		this.freshmaincontent = freshmaincontent;
		this.freshzan = freshzan;
		this.maininform = maininform;
		this.list = list;
	}

	public int getFreshonrootid() {
		return freshonrootid;
	}

	public void setFreshonrootid(int freshonrootid) {
		this.freshonrootid = freshonrootid;
	}

	public String getFreshtitle() {
		return freshtitle;
	}

	public void setFreshtitle(String freshtitle) {
		this.freshtitle = freshtitle;
	}

	public String getFreshmaincontent() {
		return freshmaincontent;
	}

	public void setFreshmaincontent(String freshmaincontent) {
		this.freshmaincontent = freshmaincontent;
	}

	public int getFreshzan() {
		return freshzan;
	}

	public void setFreshzan(int freshzan) {
		this.freshzan = freshzan;
	}

	public String getMaininform() {
		return maininform;
	}

	public void setMaininform(String maininform) {
		this.maininform = maininform;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + freshonrootid;
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
		BeanFreshonroot other = (BeanFreshonroot) obj;
		if (freshonrootid != other.freshonrootid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeanFreshonroot [freshonrootid=" + freshonrootid
				+ ", freshtitle=" + freshtitle + ", freshmaincontent="
				+ freshmaincontent + ", freshzan=" + freshzan + ", maininform="
				+ maininform + ", list=" + list + "]";
	}

}
