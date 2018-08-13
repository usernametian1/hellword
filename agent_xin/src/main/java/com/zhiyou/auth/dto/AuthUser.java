package com.zhiyou.auth.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class AuthUser extends DTO {

	private static final long serialVersionUID = 2821152326103863811L;
	private int userId;
	private String username;
	private String password;
	private int appid;
	private String realname;
	private String openid;
	private String guid;
	private String company;
	private String address;
	private String mobile;
	private String telephone;
	private String qq;
	private int status;
	private Date regTime;
	private Date loginTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "AuthUser [userId=" + userId + ", username=" + username + ", password=" + password + ", appid=" + appid + ", realname=" + realname + ", openid=" + openid + ", guid=" + guid
				+ ", company=" + company + ", address=" + address + ", mobile=" + mobile + ", telephone=" + telephone + ", qq=" + qq + ", status=" + status + ", regTime=" + regTime + ", loginTime="
				+ loginTime + "]";
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

}
