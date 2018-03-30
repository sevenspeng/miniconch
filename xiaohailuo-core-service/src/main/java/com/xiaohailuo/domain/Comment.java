package com.xiaohailuo.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private int id;
	private String recordId;
	private String uid;
	private String replyUid;
	private String comment;

	@Override
	public String toString() {
		return "Comment [dateFormater=" + dateFormater + ", id=" + id + ", recordId=" + recordId + ", uid=" + uid
				+ ", replyUid=" + replyUid + ", comment=" + comment + ", date=" + date + "]";
	}

	private Date date;

	public SimpleDateFormat getDateFormater() {
		return dateFormater;
	}

	public void setDateFormater(SimpleDateFormat dateFormater) {
		this.dateFormater = dateFormater;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getReplyUid() {
		return replyUid;
	}

	public void setReplyUid(String replyUid) {
		this.replyUid = replyUid;
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

}
