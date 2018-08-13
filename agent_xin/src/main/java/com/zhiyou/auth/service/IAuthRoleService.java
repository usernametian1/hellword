package com.zhiyou.auth.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.core.tag.PagingDto;

public interface IAuthRoleService {

	public List<AuthRole> doGetRoleList();

	public List<AuthRole> doGetAuthRoleList(Map<String, Object> params, PagingDto paging);

	public AuthRole doGetAuthRoleDtoById(int roleId);

	public AuthRole doGetAuthRoleDtoByName(String roleName);

	public void doInsertAuthRoleDto(AuthRole authRoleDto);

	public void doUpdateAuthRoleDto(AuthRole authRoleDto);

	public void doDeleteAuthRoleDto(int roleId);

	boolean deleteRoleResourceByRoleid(int roleid);

	void insertRoleResource(int roleid, int[] resourceids);

	List<AuthRole> getRolesByUserID(int userID);
}
