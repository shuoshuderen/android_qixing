package com.qixing.gc.beans;

import java.io.Serializable;

public class BeanComments implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int commentsid;
	private int postid;
	private String userid;
	private String TIME;
	private String content;
	private String touxiang;
	private String username;
	public BeanComments() {
		super();
	}

	public BeanComments(int commentsid, int postid, String userid, String tIME,
			String content, String touxiang, String username) {
		super();
		this.commentsid = commentsid;
		this.postid = postid;
		this.userid = userid;
		TIME = tIME;
		this.content = content;
		this.touxiang = touxiang;
		this.username = username;
	}

	 

	public int getCommentsid() {
		return commentsid;
	}

	public void setCommentsid(int commentsid) {
		this.commentsid = commentsid;
	}

	public int getPostid() {
		return postid;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTouxiang() {
		return touxiang;
	}

	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commentsid;
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
		BeanComments other = (BeanComments) obj;
		if (commentsid != other.commentsid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeanComments [commentsid=" + commentsid + ", postid=" + postid
				+ ", userid=" + userid + ", TIME=" + TIME + ", content="
				+ content + ", touxiang=" + touxiang + ", username=" + username
				+ "]";
	}
 
}
