package com.zhiyou.user.dto;

import java.util.Date;

import com.zhiyou.core.DTO;
import com.zhiyou.utils.DateUtils;

public class UserInfoDto extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8662821939830487454L;
	private int appid; // 游戏Id
	private int guid; // 玩家 ID
	private String nickName; // 游戏昵称
	private int superGuid; // 上级玩家 Id
	private Date bindTime; // 绑定时间
	private Date regTime; // 注册时间
	private int totalFee;// 总的充值金额

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSuperGuid() {
		return superGuid;
	}

	public void setSuperGuid(int superGuid) {
		this.superGuid = superGuid;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	@Override
	public String toString() {
		return "UserInfoDto [appid=" + appid + ", guid=" + guid + ", nickName=" + nickName + ", superGuid=" + superGuid + ", bindTime=" + bindTime + ", regTime=" + regTime + ", totalFee=" + totalFee
				+ "]";
	}

	public UserInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfoDto(int appid, int guid, String nickName, int superGuid, Date bindTime, Date regTime, int totalFee) {
		super();
		this.appid = appid;
		this.guid = guid;
		this.nickName = nickName;
		this.superGuid = superGuid;
		this.bindTime = bindTime;
		this.regTime = regTime;
		this.totalFee = totalFee;
	}

	public String getDispalyBindTime() {
		if (this.bindTime == null) {
			return "";
		}
		return DateUtils.formatFullDate(this.bindTime);
	}

}
