package com.xiaohailuo.bean;

//import java.util.Date;

public class CommentInfo {

	private String uid;
	private String nickname;
	private String profilephoto;
	//private Date date;
	private String date;
	private String comment;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfilephoto() {
		return profilephoto;
	}
	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}
	/*public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}*/
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "CommentInfo [uid=" + uid + ", nickname=" + nickname + ", profilephoto=" + profilephoto + ", date="
				+ date + ", comment=" + comment + "]";
	}
}
