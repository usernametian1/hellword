package com.zhiyou.auth.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class AuthMenuDto extends DTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8856979873480885795L;
	private int resourceId;
	private String name;
	private String href;
	private String icon;
	private int parentId;
	private int orderby;
	private int state;
	private String description;
	private Date createTime;
	private Date updateTime;
	private int type;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AuthMenuDto [resourceId=" + resourceId + ", name=" + name + ", href=" + href + ", icon=" + icon + ", parentId=" + parentId + ", orderby=" + orderby + ", state=" + state
				+ ", description=" + description + ", createTime=" + createTime + ", updateTime=" + updateTime + ", type=" + type + "]";
	}

}
