package com.zhiyou.funcconfig.dto;

import com.zhiyou.core.DTO;

public class FuncConfigDto extends DTO{
    
	private static final long serialVersionUID = 9195571496202816826L;
	
	private int appid;             //游戏id
	private String funcName;      //功能名称
	private String notfiyUrl;     //同步地址
	
	
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getNotfiyUrl() {
		return notfiyUrl;
	}
	public void setNotfiyUrl(String notfiyUrl) {
		this.notfiyUrl = notfiyUrl;
	}
	@Override
	public String toString() {
		return "FuncConfigDto [appid=" + appid + ", funcName=" + funcName
				+ ", notfiyUrl=" + notfiyUrl + "]";
	}
	public FuncConfigDto(int appid, String funcName, String notfiyUrl) {
		super();
		this.appid = appid;
		this.funcName = funcName;
		this.notfiyUrl = notfiyUrl;
	}
	public FuncConfigDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
