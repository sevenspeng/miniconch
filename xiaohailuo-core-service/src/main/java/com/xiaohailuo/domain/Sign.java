package com.xiaohailuo.domain;

import java.util.Date;

public class Sign {
	private String id;
	private String rid;
	private String um;
	private Date date;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Sign [id=" + id + ", rid=" + rid + ", um=" + um + ", date=" + date + "]";
	}
}
