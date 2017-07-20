package com.xiaohailuo.bean;

public class CoordinateScope {
	private Coordinate northeast;
	private Coordinate southwest;
	public Coordinate getNortheast() {
		return northeast;
	}
	public void setNortheast(Coordinate northeast) {
		this.northeast = northeast;
	}
	public Coordinate getSouthwest() {
		return southwest;
	}
	public void setSouthwest(Coordinate southwest) {
		this.southwest = southwest;
	}
	@Override
	public String toString() {
		return "CoordinateScope [northeast=" + northeast + ", southwest=" + southwest + "]";
	}
}
