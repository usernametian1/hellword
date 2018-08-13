package com.zhiyou.user.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class UserBindLog extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8017343367034249868L;
	private int id;
	private int appid;
	private int guid;
	private int superGuid;
	private int superLevel;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getSuperGuid() {
		return superGuid;
	}

	public void setSuperGuid(int superGuid) {
		this.superGuid = superGuid;
	}

	public int getSuperLevel() {
		return superLevel;
	}

	public void setSuperLevel(int superLevel) {
		this.superLevel = superLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserBindLog [id=" + id + ", appid=" + appid + ", guid=" + guid + ", superGuid=" + superGuid + ", superLevel=" + superLevel + ", createTime=" + createTime + "]";
	}

}
