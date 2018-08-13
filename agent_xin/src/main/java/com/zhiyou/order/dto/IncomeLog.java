package com.zhiyou.order.dto;

import java.util.Date;

import com.zhiyou.core.DTO;
import com.zhiyou.utils.DateUtils;

public class IncomeLog extends DTO {

	private static final long serialVersionUID = 9067027332551105735L;
	private int id;
	private String orderid;
	private int appid;
	private int guid;
	private int superGuid;
	private int userLevel;
	private int income;
	private int state;
	private int totalFee;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	@Override
	public String toString() {
		return "IncomeLog [id=" + id + ", orderid=" + orderid + ", appid=" + appid + ", guid=" + guid + ", superGuid=" + superGuid + ", userLevel=" + userLevel + ", income=" + income + ", state="
				+ state + ", totalFee=" + totalFee + ", createTime=" + createTime + "]";
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDispalyCreateTime() {
		if (this.createTime == null) {
			return "";
		}
		return DateUtils.formatFullDate(this.createTime);
	}

}
