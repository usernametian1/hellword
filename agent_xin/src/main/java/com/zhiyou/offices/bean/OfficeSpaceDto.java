package com.zhiyou.offices.bean;

public class OfficeSpaceDto {

	/*
	 * division` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名字',
  `floor` int(50) DEFAULT NULL COMMENT '楼层',
  `room` int(50) DEFAULT NULL COMMENT '房间编号',
  `seatdetail` text CHARACTER
	 */
	private int userid;  //员工编号
	private String username; //员工姓名
	private String division; //所属部门
	private int floor;      // 楼层
	private int room;       //房间号
	private String seatdetail;  // 明细详情
	private String icon;    //图片路径
	
	
	public OfficeSpaceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OfficeSpaceDto(int userid, String username, String division, int floor, int room, String seatdetail,
			String icon) {
		super();
		this.userid = userid;
		this.username = username;
		this.division = division;
		this.floor = floor;
		this.room = room;
		this.seatdetail = seatdetail;
		this.icon = icon;
	}

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
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
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
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
	
	
	
}
