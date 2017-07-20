package com.xiaohailuo.bean;

public class Like {
	private String recordId;
	private String uId;
	private String like;

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	@Override
	public String toString() {
		return "Like [recordId=" + recordId + ", uId=" + uId + ", like=" + like + "]";
	}
	
}
