package com.qixing.bean;

import java.io.Serializable;

public class BeanSceneryPhoto implements Serializable{
	private static final long serialVersionUID = 1L;
	private int	sceneryphotoid;
	private int sceneryid;
	private String photo;
	
	public BeanSceneryPhoto() {
		super();
	}
	
	public BeanSceneryPhoto(String photo) {
		super();
		this.photo = photo;
	}

	/*public BeanSceneryPhoto(int sceneryid, String photo) {
		super();
		this.sceneryid = sceneryid;
		this.photo = photo;
	}
	public BeanSceneryPhoto(int sceneryphotoid, int sceneryid, String photo) {
		super();
		this.sceneryphotoid = sceneryphotoid;
		this.sceneryid = sceneryid;
		this.photo = photo;
	}*/
	public int getSceneryphotoid() {
		return sceneryphotoid;
	}
	public void setSceneryphotoid(int sceneryphotoid) {
		this.sceneryphotoid = sceneryphotoid;
	}
	public int getSceneryid() {
		return sceneryid;
	}
	public void setSceneryid(int sceneryid) {
		this.sceneryid = sceneryid;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "BeanSceneryPhoto [sceneryphotoid=" + sceneryphotoid
				+ ", sceneryid=" + sceneryid + ", photo=" + photo + "]";
	}

}
