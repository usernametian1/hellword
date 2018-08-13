package com.zhiyou.auth.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.auth.dao.AuthRoleDao;
import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.auth.service.IAuthRoleService;
import com.zhiyou.core.tag.PagingDto;

@Service("authRoleService")
public class AuthRoleServiceImpl implements IAuthRoleService {

	@Autowired
	private AuthRoleDao authRoleDao;

	/**
	 * 角色列表
	 */
	@Override
	public List<AuthRole> doGetRoleList() {
		return authRoleDao.doGetRoleList();
	}

	/**
	 * Id查询角色
	 */
	@Override
	public AuthRole doGetAuthRoleDtoById(int roleId) {
		return authRoleDao.doGetAuthRoleDtoById(roleId);
	}

	/**
	 * 新增角色
	 */
	@Override
	public void doInsertAuthRoleDto(AuthRole authRole) {
		authRoleDao.doInsertAuthRoleDto(authRole);
	}

	/**
	 * 更新角色
	 */
	@Override
	public void doUpdateAuthRoleDto(AuthRole authRole) {
		authRoleDao.doUpdateAuthRoleDto(authRole);
	}

	/**
	 * 删除角色 ,同时删除关联的资源
	 */
	@Override
	public void doDeleteAuthRoleDto(int roleId) {
		if (roleId > 0) {
			authRoleDao.doDeleteAuthRoleDto(roleId);
			authRoleDao.doDeleteAuthRoleMenuDto(roleId);
		}
	}

	/**
	 * 名称查询角色
	 */
	@Override
	public AuthRole doGetAuthRoleDtoByName(String roleName) {
		return authRoleDao.doGetRoleByName(roleName);
	}

	@Override
	public void insertRoleResource(int roleid, int[] resourceids) {
		if (null == resourceids || resourceids.length < 1) {
			return;
		}
		for (int resourceid : resourceids) {
			authRoleDao.insertRoleResource(roleid, resourceid);
		}
	}

	@Override
	public boolean deleteRoleResourceByRoleid(int roleid) {
		return authRoleDao.deleteRoleResourceByRoleid(roleid);
	}

	@Override
	public List<AuthRole> getRolesByUserID(int userID) {
		return authRoleDao.getRolesByUserID(userID);
	}

	@Override
	public List<AuthRole> doGetAuthRoleList(Map<String, Object> params, PagingDto paging) {
		int totalRows = authRoleDao.doGetAuthRoleListCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return authRoleDao.doGetAuthRoleList(params);
	}
}
