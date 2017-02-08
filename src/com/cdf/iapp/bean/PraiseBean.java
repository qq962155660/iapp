package com.cdf.iapp.bean;

public class PraiseBean {
	private String username;
	private String name;
	private String time;
	
	public PraiseBean() {
		super();
	}

	public PraiseBean(String username, String name, String time) {
		super();
		this.username = username;
		this.name = name;
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "PraiseBean [username=" + username + ", name=" + name + ", time=" + time + "]";
	}

	
	
}
