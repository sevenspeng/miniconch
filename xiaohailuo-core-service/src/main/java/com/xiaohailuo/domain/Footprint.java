package com.xiaohailuo.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
//import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaohailuo.bean.CommentInfo;

public class Footprint {
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String id;
	private String coordinates;
	private BigDecimal lat;
	private BigDecimal lng;
	private String uid;
	private String city;
	private String province;
	private String country;
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date footprintDate;
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public Date getFootprintDate() {
		return footprintDate;
	}

	public void setFootprintDate(Date footprintDate) {
		this.footprintDate = footprintDate;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public SimpleDateFormat getDateFormater() {
		return dateFormater;
	}

	public void setDateFormater(SimpleDateFormat dateFormater) {
		this.dateFormater = dateFormater;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "Footprint [id=" + id + ", coordinates=" + coordinates + ", lat=" + lat + ", lng=" + lng + ", uid=" + uid
				+ ", city=" + city + ", province=" + province + ", country=" + country +",footprintDate" + footprintDate+ "]";
	}

}
