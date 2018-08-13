package com.zhiyou.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.auth.dao.AuthMenuDao;
import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.auth.service.AuthMenuService;
import com.zhiyou.core.tag.PagingDto;

@Service("authMenuService")
public class AuthMenuServiceImpl implements AuthMenuService {

	@Autowired
	private AuthMenuDao authMenuDao;

	public AuthMenuDao getAuthMenuDao() {
		return authMenuDao;
	}

	public void setAuthMenuDao(AuthMenuDao authMenuDao) {
		this.authMenuDao = authMenuDao;
	}

	@Override
	public List<AuthMenuDto> doGetMenuList(int type) {
		return authMenuDao.doGetMenuList(type);
	}

	@Override
	public AuthMenuDto doGetAuthMenuDtoById(int resourceId) {
		return authMenuDao.doGetAuthMenuDtoById(resourceId);
	}

	@Override
	public void doInsertAuthMenuDto(AuthMenuDto authMenuDto) {
		authMenuDto.setCreateTime(new Date());
		authMenuDto.setUpdateTime(new Date());
		authMenuDto.setIcon("");
		authMenuDao.doInsertAuthMenuDto(authMenuDto);

	}

	// 删除没有父菜的菜单
	@Override
	public void doDeleteAuthMenuDto(int resourceId) {
		AuthMenuDto authMenuDto = authMenuDao.doGetAuthMenuDtoById(resourceId);
		if (authMenuDto != null) {
			int parentId = authMenuDto.getParentId();
			if (parentId != -1) {// 子菜单直接删除
				authMenuDao.doDeleteAuthMenuDto(resourceId);
			} else {
				// 本菜单及子菜单都删除
				authMenuDao.doDeleteAuthMenuDto(resourceId);
				authMenuDao.doDeleteByParentId(resourceId);
			}

		}
	}

	@Override
	public void doUpdateAuthMenuDto(AuthMenuDto authMenuDto) {
		authMenuDto.setUpdateTime(new Date());
		authMenuDao.doUpdateAuthMenuDto(authMenuDto);
	}

	@Override
	public int totalCount() {

		return authMenuDao.totalCount();
	}

	@Override
	public List<AuthMenuDto> doGetAuthMenuDtoList(Map<String, Object> params, PagingDto paging) {
		int totalRows = authMenuDao.totalCount();
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return authMenuDao.doGetAuthMenuDtoList(params);
	}

	@Override
	public void doDeleteByParentId(int parentId) {
		authMenuDao.doDeleteByParentId(parentId);
	}

	@Override
	public List<AuthMenuDto> getResourcesByRoleIDs(List<Integer> roleIDs) {
		if (roleIDs.size() < 1) {
			return new ArrayList<AuthMenuDto>();
		}
		return authMenuDao.getResourcesByRoleIDs(roleIDs);
	}

	@Override
	public List<Integer> getResourceIDByRoleID(int roleID) {
		return authMenuDao.getResourceIDByRoleID(roleID);
	}
}
