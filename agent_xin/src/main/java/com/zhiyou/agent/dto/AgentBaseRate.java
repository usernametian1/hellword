package com.zhiyou.agent.dto;

import com.zhiyou.core.DTO;

/**
 * 
 * @author Administrator
 *
 */
public class AgentBaseRate extends DTO {
	private static final long serialVersionUID = 197845316507600878L;
	private int appid;// appid
	private int level;// 代理等级
	private String name;// 代理类别描述
	private int rate1;// 一级代理基础提成
	private int rate2;// 二级代理基础提成
	private int rate3;// 三级代理基础提成

	@Override
	public String toString() {
		return "AgentBaseRate [appid=" + appid + ", level=" + level + ", name=" + name + ", rate1=" + rate1 + ", rate2=" + rate2 + ", rate3=" + rate3 + "]";
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
