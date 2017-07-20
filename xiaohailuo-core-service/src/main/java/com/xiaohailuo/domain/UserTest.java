package com.xiaohailuo.domain;

public class UserTest {

	private Long id;
	private String name;
	private Integer age;
	private String address;
	private String address2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Override
	public String toString() {
		return "UserTest {id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + ", address2="
				+ address2 + "}";
	}
	
}
