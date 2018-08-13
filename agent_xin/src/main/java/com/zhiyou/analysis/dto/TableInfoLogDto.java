package com.zhiyou.analysis.dto;

import java.util.Date;

import com.zhiyou.core.DTO;

public class TableInfoLogDto extends DTO{

	private static final long serialVersionUID = -2481487062263543676L;
	
	private int id;
	private int appid;
	private int guid;
	private int superGuid;
	private int tableId;
	private int useDiamond;
	private int roundNum;
	private String day;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getUseDiamond() {
		return useDiamond;
	}
	public void setUseDiamond(int useDiamond) {
		this.useDiamond = useDiamond;
	}
	public int getRoundNum() {
		return roundNum;
	}
	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	public TableInfoLogDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@Override
	public String toString() {
		return "TableInfoLogDto [id=" + id + ", appid=" + appid + ", guid="
				+ guid + ", superGuid=" + superGuid + ", tableId=" + tableId
				+ ", useDiamond=" + useDiamond + ", roundNum=" + roundNum
				+ ", day=" + day + "]";
	}
	public TableInfoLogDto(int id, int appid, int guid, int superGuid,
			int tableId, int useDiamond, int roundNum, String day) {
		super();
		this.id = id;
		this.appid = appid;
		this.guid = guid;
		this.superGuid = superGuid;
		this.tableId = tableId;
		this.useDiamond = useDiamond;
		this.roundNum = roundNum;
		this.day = day;
	}
}
