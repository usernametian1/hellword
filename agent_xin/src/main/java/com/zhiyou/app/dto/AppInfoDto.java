package com.zhiyou.app.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class AppInfoDto extends DTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1237612996506351212L;
	private int appid; // appId
	private String appname; // app的名字
	private String appkey;
	private Date createTime; // 创建 时间
	private Date updateTime; // 改变时间

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public AppInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AppInfoDto [appid=" + appid + ", appname=" + appname + ", appkey=" + appkey + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
