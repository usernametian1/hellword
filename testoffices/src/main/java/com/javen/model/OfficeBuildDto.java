package com.javen.model;

import com.zhiyou.duobao.utils.config.WebConfig;

public class OfficeBuildDto {

	
	private int buildid;//办公楼id
	private String positionicon;
	private String buildname;
	public int getBuildid() {
		return buildid;
	}
	public void setBuildid(int buildid) {
		this.buildid = buildid;
	}
	public String getPositionicon() {
		return positionicon;
	}
	public void setPositionicon(String positionicon) {
		this.positionicon = positionicon;
	}
	public String getBuildname() {
		return buildname;
	}
	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}
	
	
	private String buildUrl;
	public String getBuildUrl() {
		buildUrl ="";
			try {
				buildUrl=WebConfig.getImageVisitPath()+positionicon;	
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		return buildUrl;
	}
	public void setBuildUrl(String buildUrl) {
		this.buildUrl = buildUrl;
	}
	
	
}
