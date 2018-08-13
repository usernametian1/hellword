package com.zhiyou.order.dto;

import java.util.Date;

import com.zhiyou.core.DTO;
import com.zhiyou.utils.DateUtils;

public class OrderInfoDTO extends DTO {

	private static final long serialVersionUID = 7838688722830921516L;

	private int appid;// 订单所属游戏产品ID
	private String orderId;// 订单号
	private int guid;// 用户游戏ID
	private int superGuid;// 上级代理Uid
	private String wares;// 订单描述
	private int totalFee;// 支付金额(分为单位)
	private Date payTime;// 支付时间

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public String getWares() {
		return wares;
	}

	public void setWares(String wares) {
		this.wares = wares;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "OrderInfoDTO [appid=" + appid + ", orderId=" + orderId + ", guid=" + guid + ", superGuid=" + superGuid + ", wares=" + wares + ", totalFee=" + totalFee + ", payTime=" + payTime + "]";
	}

	public int getSuperGuid() {
		return superGuid;
	}

	public void setSuperGuid(int superGuid) {
		this.superGuid = superGuid;
	}

	public String getDispalyPayTime() {
		if (this.payTime == null) {
			return "";
		}
		return DateUtils.formatFullDate(payTime);
	}
}
