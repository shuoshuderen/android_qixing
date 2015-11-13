package com.qixing.bean;

import java.io.Serializable;

public class Beanequdetailphoto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int	equdetailphotoid;
	private int equdetailId;
	private String photo;
	
	
	public Beanequdetailphoto() {
		super();
	}

	public Beanequdetailphoto(String photo) {
		super();
		this.photo = photo;
	}

	public Beanequdetailphoto(int equdetailphotoid, int equdetailId,
			String photo) {
		super();
		this.equdetailphotoid = equdetailphotoid;
		this.equdetailId = equdetailId;
		this.photo = photo;
	}

	public int getEqudetailphotoid() {
		return equdetailphotoid;
	}

	public void setEqudetailphotoid(int equdetailphotoid) {
		this.equdetailphotoid = equdetailphotoid;
	}

	public int getEqudetailId() {
		return equdetailId;
	}

	public void setEqudetailId(int equdetailId) {
		this.equdetailId = equdetailId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Beanequdetailphoto [photo=" + photo + "]";
	}

	

}
