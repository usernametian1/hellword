package com.zhiyou.auth.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;

public interface IAuthUserService {
	public List<AuthUser> doGetAuthUserList(Map<String, Object> params, PagingDto paging);

	public AuthUser doGetAuthUserByUserID(int userId);

	public void doDeleteAuthUser(int userId);

	public AuthUser doGetAuthUserByName(String username);

	// public int doGetAuthUser(HttpServletRequest req);

	public boolean doInsertAuthUser(AuthUser authUser);

	public boolean doUpdateAuthUser(AuthUser authUser);

	int doUpdatePWD(int userId, String PWD);

	public List<AuthUser> doGetAllUserList();

	void doDeleteAuthUserRole(int userId);

	void updateLoginTime(String userName);

	int updateByCondition(Map<String, Object> params);

	void insertUserRole(int userid, int[] roleids);

	void deleteUserRoleByUserID(int userid);

	public AuthUser getAuthUser(int appid, String guid);

}
