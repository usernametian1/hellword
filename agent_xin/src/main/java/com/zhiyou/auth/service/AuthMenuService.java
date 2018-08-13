package com.zhiyou.auth.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.core.tag.PagingDto;

public interface AuthMenuService {

	public List<AuthMenuDto> doGetAuthMenuDtoList(Map<String, Object> params, PagingDto paging);

	/**
	 * type=1 仅获取菜单,0获取全部
	 * 
	 * @param type
	 * @return
	 */
	public List<AuthMenuDto> doGetMenuList(int type);

	public AuthMenuDto doGetAuthMenuDtoById(int resourceId);

	public void doInsertAuthMenuDto(AuthMenuDto authMenuDto);

	public void doDeleteAuthMenuDto(int resourceId);

	void doDeleteByParentId(int parentId);

	public void doUpdateAuthMenuDto(AuthMenuDto authMenuDto);

	List<AuthMenuDto> getResourcesByRoleIDs(List<Integer> roleIDs);

	public int totalCount();

	List<Integer> getResourceIDByRoleID(int roleID);
}
