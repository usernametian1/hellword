package com.zhiyou.auth.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.auth.dto.AuthRole;

public interface AuthRoleDao {

	public List<AuthRole> doGetRoleList();

	public List<AuthRole> doGetAuthRoleList(Map<String, Object> params);

	public int doGetAuthRoleListCount(Map<String, Object> params);

	public AuthRole doGetAuthRoleDtoById(int roleId);

	public void doInsertAuthRoleDto(AuthRole authRoleDto);

	public void doUpdateAuthRoleDto(AuthRole authRoleDto);

	public void doDeleteAuthRoleDto(int roleId);

	public void doDeleteAuthRoleMenuDto(int roleId);

	public AuthRole doGetRoleByName(String roleName);

	public void insertRoleResource(@Param("roleid") int roleid, @Param("resourceid") int resourceid);

	public boolean deleteRoleResourceByRoleid(int roleid);

	public List<AuthRole> getRolesByUserID(int userID);

}
