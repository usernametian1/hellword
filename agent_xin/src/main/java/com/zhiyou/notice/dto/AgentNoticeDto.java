package com.zhiyou.notice.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class AgentNoticeDto extends DTO {

	private static final long serialVersionUID = -8068627990056917689L;

	private int id; // 自增id
	private int appid; //
	private String name; // 标题
	private String content; // 公告内蓉
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间
	private int state; // 0 = 无效 1 = 有效

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public AgentNoticeDto(int id, String name, String content, Date createTime, Date updateTime, int state) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
	}

	public AgentNoticeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AgentNoticeDto [id=" + id + ", appid=" + appid + ", name=" + name + ", content=" + content + ", createTime=" + createTime + ", updateTime=" + updateTime + ", state=" + state + "]";
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

}
