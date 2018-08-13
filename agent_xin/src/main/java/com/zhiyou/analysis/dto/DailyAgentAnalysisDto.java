package com.zhiyou.analysis.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class DailyAgentAnalysisDto extends DTO {

	private static final long serialVersionUID = 6994383034730595878L;
	private String day; // 数据所处日期
	private int appid; // 游戏id
	private int guid; // 玩家id
	private int tableNum; // 大局数
	private int bindNum; // 绑定人数
	private int income1; // 代理一级收入
	private int income2; // 代理二级收入
	private int income3; // 代理三级收入
	private int useDiamond; // 耗钻数
	private Date updateTime;// 修改日期

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public int getTableNum() {
		return tableNum;
	}

	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}

	public int getBindNum() {
		return bindNum;
	}

	public void setBindNum(int bindNum) {
		this.bindNum = bindNum;
	}

	public int getIncome1() {
		return income1;
	}

	public void setIncome1(int income1) {
		this.income1 = income1;
	}

	public int getIncome2() {
		return income2;
	}

	public void setIncome2(int income2) {
		this.income2 = income2;
	}

	public int getIncome3() {
		return income3;
	}

	public void setIncome3(int income3) {
		this.income3 = income3;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public DailyAgentAnalysisDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DailyAgentAnalysisDto [day=" + day + ", appid=" + appid + ", guid=" + guid + ", tableNum=" + tableNum + ", bindNum=" + bindNum + ", income1=" + income1 + ", income2=" + income2
				+ ", income3=" + income3 + ", useDiamond=" + useDiamond + ", updateTime=" + updateTime + "]";
	}

	public int getUseDiamond() {
		return useDiamond;
	}

	public void setUseDiamond(int useDiamond) {
		this.useDiamond = useDiamond;
	}

}