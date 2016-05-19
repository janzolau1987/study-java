package com.yaoyaohao.study.spring;

import java.io.Serializable;

/**
 * 
 * @author liujianzhu
 * @date 2016年5月19日 下午4:35:51
 *
 */
public class JavaBean implements Serializable{
	private String username;
	private String password;
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
	private static final long serialVersionUID = 750941409426765639L;
}
