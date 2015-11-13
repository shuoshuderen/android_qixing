package com.qixing.gc.beans;

import java.io.Serializable;

public class BeanPostPhoto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int postphotoid;
	private int postId;
	private String photo;

	public BeanPostPhoto() {
		super();
	}

	public BeanPostPhoto(String photo) {
		super();
		this.photo = photo;
	}

	public BeanPostPhoto(int postphotoid, int postId, String photo) {
		super();
		this.postphotoid = postphotoid;
		this.postId = postId;
		this.photo = photo;
	}

	public int getPostphotoid() {
		return postphotoid;
	}

	public void setPostphotoid(int postphotoid) {
		this.postphotoid = postphotoid;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "BeanPostPhoto [photo=" + photo + "]";
	}


}
