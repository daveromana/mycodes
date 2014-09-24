package org.huangxu.data;

import java.io.Serializable;

public class UserData implements Serializable {

	private static final long serialVersionUID = 3426192183194929808L;
	private int uid ;
	private String userName ;
	private String password ;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
