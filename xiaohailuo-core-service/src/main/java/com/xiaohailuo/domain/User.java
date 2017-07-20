package com.xiaohailuo.domain;

public class User {

	private String id;
	private String name;
	private String nickname;
	private String profilephoto;
	private String summary;
	private String gender;
	private String subscribetim;
	private String qrcode;
	private String address;
	private String whatisup;
	private String region;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSubscribetim() {
		return subscribetim;
	}
	public void setSubscribetim(String subscribetim) {
		this.subscribetim = subscribetim;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWhatisup() {
		return whatisup;
	}
	public void setWhatisup(String whatisup) {
		this.whatisup = whatisup;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", nickname=" + nickname + ", profilephoto=" + profilephoto
				+ ", summary=" + summary + ", gender=" + gender + ", subscribetim=" + subscribetim + ", qrcode="
				+ qrcode + ", address=" + address + ", whatisup=" + whatisup + ", region=" + region + "]";
	}
}
