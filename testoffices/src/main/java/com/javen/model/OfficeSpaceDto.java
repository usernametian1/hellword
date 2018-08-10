package com.javen.model;


import java.sql.Date;

import com.zhiyou.duobao.utils.config.WebConfig;

public class OfficeSpaceDto {

	/*
	 * division` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名字',
  `floor` int(50) DEFAULT NULL COMMENT '楼层',
  `room` int(50) DEFAULT NULL COMMENT '房间编号',
  `seatdetail` text CHARACTER
  sonpartmentid departmentid
	 */
	private Integer userid;  //员工编号
	private String username; //员工姓名
	private String division; //所属部门
	private Integer floor;      // 楼层
	private Integer room;       //房间号
	private String seatdetail;  // 明细详情
	private String icon;    //图片路径
	private int buildid;   //办公楼编号
	private String buildname; //办公楼名字
	private String creatTime;
	private int departmentid;
	private int sonpartmentid;
	private String parentname;
	private String lowername;
	
	public OfficeSpaceDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public Integer getFloor() {
		return floor;
	}


	public void setFloor(Integer floor) {
		this.floor = floor;
	}


	public Integer getRoom() {
		return room;
	}


	public void setRoom(Integer room) {
		this.room = room;
	}


	public String getSeatdetail() {
		return seatdetail;
	}


	public void setSeatdetail(String seatdetail) {
		this.seatdetail = seatdetail;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	
	public int getBuildid() {
		return buildid;
	}


	public void setBuildid(int buildid) {
		this.buildid = buildid;
	}


	public String getBuildname() {
		return buildname;
	}


	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}

	








	public int getDepartmentid() {
		return departmentid;
	}


	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}


	public int getSonpartmentid() {
		return sonpartmentid;
	}


	public void setSonpartmentid(int sonpartmentid) {
		this.sonpartmentid = sonpartmentid;
	}


	public String getCreatTime() {
		return creatTime;
	}


	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

    








	public String getParentname() {
		return parentname;
	}


	public void setParentname(String parentname) {
		this.parentname = parentname;
	}


	public String getLowername() {
		return lowername;
	}


	public void setLowername(String lowername) {
		this.lowername = lowername;
	}










	private String iconUrl;

	public String getIconUrl() {
	       iconUrl ="";
		try{
			iconUrl =   WebConfig.getImageVisitPath()+icon;		
		}catch(Exception e){
			
		}
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
}
