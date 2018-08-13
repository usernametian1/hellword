package com.zhiyou.auth.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.auth.dto.AuthMenuDto;

public interface AuthMenuDao {
	public List<AuthMenuDto> doGetMenuList(@Param("type") int type);

	public AuthMenuDto doGetAuthMenuDtoById(int resourceId);

	public void doInsertAuthMenuDto(AuthMenuDto authMenuDto);

	public void doUpdateAuthMenuDto(AuthMenuDto authMenuDto);

	public void doDeleteAuthMenuDto(int resourceId);

	public void doDeleteByParentId(int parentId);

	public int totalCount();

	public List<AuthMenuDto> doGetAuthMenuDtoList(Map<String, Object> params);

	public List<Integer> getResourceIDByRoleID(int roleID);

	/**
	 * 由roleID集合查询对应的resources
	 * 
	 * @param roleIDs
	 * @return
	 */
	public List<AuthMenuDto> getResourcesByRoleIDs(@Param("roleIDs") List<Integer> roleIDs);
}
