package com.zhiyou.agent.dto;

import java.io.Serializable;

import com.zhiyou.core.DTO;

public class AgentRate extends DTO {

	private static final long serialVersionUID = 7016144157481504881L;
	private int appid;// appID
	private int guid;// 用户ID
	private int rate1;// 一级代理分成率
	private int rate2;// 二级代理分成率
	private int rate3;// 三级代理分成率

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

	public int getRate1() {
		return rate1;
	}

	public void setRate1(int rate1) {
		this.rate1 = rate1;
	}

	public int getRate2() {
		return rate2;
	}

	public void setRate2(int rate2) {
		this.rate2 = rate2;
	}

	public int getRate3() {
		return rate3;
	}

	public void setRate3(int rate3) {
		this.rate3 = rate3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AgentRate [appid=" + appid + ", guid=" + guid + ", rate1=" + rate1 + ", rate2=" + rate2 + ", rate3=" + rate3 + "]";
	}

}
