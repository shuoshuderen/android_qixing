package com.qixing.bean;

import java.io.Serializable;
import java.util.List;

public class BeanEqudetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private int equdetailId;
	private String equdetailName;
	private double equdetailPrice;
	private String equdetailInform;
	private String equdetailClass;
    List<Beanequdetailphoto> list;
	public BeanEqudetail() {
		super();
	}

	public BeanEqudetail(String equdetailName, double equdetailPrice,
			String equdetailInform, String equdetailClass) {
		super();
		this.equdetailName = equdetailName;
		this.equdetailPrice = equdetailPrice;
		this.equdetailInform = equdetailInform;
		this.equdetailClass = equdetailClass;
	}


	public BeanEqudetail(int equdetailId, String equdetailName,
			double equdetailPrice, String equdetailInform,
			String equdetailClass, List<Beanequdetailphoto> list) {
		super();
		this.equdetailId = equdetailId;
		this.equdetailName = equdetailName;
		this.equdetailPrice = equdetailPrice;
		this.equdetailInform = equdetailInform;
		this.equdetailClass = equdetailClass;
		this.list = list;
	}

	public int getEqudetailId() {
		return equdetailId;
	}

	public void setEqudetailId(int equdetailId) {
		this.equdetailId = equdetailId;
	}

	public String getEqudetailName() {
		return equdetailName;
	}

	public void setEqudetailName(String equdetailName) {
		this.equdetailName = equdetailName;
	}

	public double getEqudetailPrice() {
		return equdetailPrice;
	}

	public void setEqudetailPrice(double equdetailPrice) {
		this.equdetailPrice = equdetailPrice;
	}

	public String getEqudetailInform() {
		return equdetailInform;
	}

	public void setEqudetailInform(String equdetailInform) {
		this.equdetailInform = equdetailInform;
	}

	public String getEqudetailClass() {
		return equdetailClass;
	}

	public void setEqudetailClass(String equdetailClass) {
		this.equdetailClass = equdetailClass;
	}

	public List<Beanequdetailphoto> getList() {
		return list;
	}

	public void setList(List<Beanequdetailphoto> list) {
		this.list = list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + equdetailId;
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
		BeanEqudetail other = (BeanEqudetail) obj;
		if (equdetailId != other.equdetailId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeanEqudetail [equdetailId=" + equdetailId + ", equdetailName="
				+ equdetailName + ", equdetailPrice=" + equdetailPrice
				+ ", equdetailInform=" + equdetailInform + ", equdetailClass="
				+ equdetailClass + ", list=" + list + "]";
	}

}
