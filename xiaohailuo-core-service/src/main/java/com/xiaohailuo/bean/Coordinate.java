package com.xiaohailuo.bean;

public class Coordinate {
	private String lng;
	private String lat;

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "Coordinates [lng=" + lng + ", lat=" + lat + "]";
	}
}
