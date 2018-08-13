package com.zhiyou.cash.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class ApplyCashDto extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3576282001344613281L;
	private String orderId;//
	private int appid;//
	private int guid;//
	private int totalFee;// 提现金额(分)
	private int state;// 申请提现=0，已提现=1，驳回=2
	private String remark;// 驳回说明
	private Date applyTime;// 申请时间
	private Date updateTime;// 成功提现时间或驳回时间

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String oderId) {
		this.orderId = oderId;
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

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ApplyCash [orderId=" + orderId + ", appid=" + appid + ", guid=" + guid + ", totalFee=" + totalFee + ", state=" + state + ", remark=" + remark + ", applyTime=" + applyTime
				+ ", updateTime=" + updateTime + "]";
	}

}
