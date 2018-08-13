package com.zhiyou.auth.dto;

import com.zhiyou.core.DTO;

public class AuthRole extends DTO {
	private static final long serialVersionUID = -516658975510634547L;
	private int roleId;
	private String roleName;
	private String remark;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AuthRole [roleId=" + roleId + ", roleName=" + roleName + ", remark=" + remark + "]";
	}

}
