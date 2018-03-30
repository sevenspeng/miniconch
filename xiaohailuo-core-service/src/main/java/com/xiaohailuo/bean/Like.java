package com.xiaohailuo.bean;

public class Like {
	private String recordId;
	private String uid;
	private String replyUid;
	private String like;

	@Override
	public String toString() {
		return "Like [recordId=" + recordId + ", uid=" + uid + ", replyUid=" + replyUid + ", like=" + like + "]";
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

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

}
