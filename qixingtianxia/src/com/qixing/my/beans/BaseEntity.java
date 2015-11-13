package com.qixing.my.beans;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;


public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(column = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
