package com.zhiyou.agent.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class AgentDto extends DTO {
	private static final long serialVersionUID = -6279256911542747307L;
	private int appid; // id
	private int guid; // 用户id
	private String name; // 真实姓名
	private int status; // 审核状态
	private String phone; // 手机号
	private String remark; // 备注
	private Date applyTime; // 申请时间
	private Date checkTime; // 通过时间
	private int userNum1; // 推广人数
	private int userNum2;
	private int userNum3;
	private int agentLevel; //
	private int income1; // 一级代理的收入
	private int income2;
	private int income3;
	private String openid;
	private int balance;// 余额
	private int state;// 是否冻结状态
	private int freeze;// 冻结状态下的余额
	private int syn;

	public int getSyn() {
		return syn;
	}

	public void setSyn(int syn) {
		this.syn = syn;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public int getUserNum1() {
		return userNum1;
	}

	public void setUserNum1(int userNum1) {
		this.userNum1 = userNum1;
	}

	public int getUserNum2() {
		return userNum2;
	}

	public void setUserNum2(int userNum2) {
		this.userNum2 = userNum2;
	}

	public int getUserNum3() {
		return userNum3;
	}

	public void setUserNum3(int userNum3) {
		this.userNum3 = userNum3;
	}

	public int getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(int agentLevel) {
		this.agentLevel = agentLevel;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getFreeze() {
		return freeze;
	}

	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}

	@Override
	public String toString() {
		return "AgentDto [appid=" + appid + ", guid=" + guid + ", name=" + name + ", status=" + status + ", phone=" + phone + ", remark=" + remark + ", applyTime=" + applyTime + ", checkTime="
				+ checkTime + ", userNum1=" + userNum1 + ", userNum2=" + userNum2 + ", userNum3=" + userNum3 + ", agentLevel=" + agentLevel + ", income1=" + income1 + ", income2=" + income2
				+ ", income3=" + income3 + ", openid=" + openid + ", balance=" + balance + ", state=" + state + ", freeze=" + freeze + "]";
	}

}
