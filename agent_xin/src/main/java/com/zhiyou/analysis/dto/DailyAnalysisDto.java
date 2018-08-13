package com.zhiyou.analysis.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class DailyAnalysisDto extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8200920890503777339L;
	private int appid;// 游戏appID
	private String day;// 数据所处日期
	private int regNum;// 注册数
	private int dau;// 日活跃数
	private int tableNum;// 开桌数
	private int roundNum;// 回合数
	private int useDiamond;// 钻石消耗数
	private int totalFee;// 充值总数
	private int online;// 在线峰值
	private Date onlineTime;// 峰值对应的时间点
	private Date updateTime;

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getRegNum() {
		return regNum;
	}

	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}

	public int getDau() {
		return dau;
	}

	public void setDau(int dau) {
		this.dau = dau;
	}

	public int getTableNum() {
		return tableNum;
	}

	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}

	public int getRoundNum() {
		return roundNum;
	}

	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	public int getUseDiamond() {
		return useDiamond;
	}

	public void setUseDiamond(int useDiamond) {
		this.useDiamond = useDiamond;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
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
		return "DailyAnalysisDto [appid=" + appid + ", day=" + day + ", regNum=" + regNum + ", dau=" + dau + ", tableNum=" + tableNum + ", roundNum=" + roundNum + ", useDiamond=" + useDiamond
				+ ", totalFee=" + totalFee + ", online=" + online + ", onlineTime=" + onlineTime + ", updateTime=" + updateTime + "]";
	}

}
