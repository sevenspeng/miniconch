package com.xiaohailuo.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	private int id;
	private String rid;
	private String uid;
	private String comment;
	private Date date;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", rid=" + rid + ", uid=" + uid + ", comment=" + comment + ", date=" + date + "]";
	}

}
