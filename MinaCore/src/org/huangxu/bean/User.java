package org.huangxu.bean;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -3831065199062813287L;
	
	private int uid ;
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String username ;
	
	private String password ;
	
}
