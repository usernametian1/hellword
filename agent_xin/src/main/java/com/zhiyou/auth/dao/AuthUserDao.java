package com.zhiyou.auth.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.auth.dto.AuthUser;

public interface AuthUserDao {

	public List<AuthUser> doGetAuthUserList();

	public List<AuthUser> doGetAuthUserList(Map<String, Object> params);

	public int doGetAuthUserListCount(Map<String, Object> params);

	public AuthUser doGetAuthUserById(int userId);

	public AuthUser doGetAuthUserByOpenid(String openid);

	public AuthUser doGetAuthUserByPfUserId(int pfUserId);

	public void doDeleteAuthUser(int userId);

	public AuthUser doGetAuthUserByName(String username);

	public boolean doInsertAuthUser(AuthUser authUserDto);

	public boolean doUpdateAuthUser(AuthUser authUserDto);

	public int updateByCondition(Map<String, Object> params);

	public List<AuthUser> doGetAllUserList();

	void doDeleteAuthUserRole(int userId);

	void updateLoginTime(@Param("userName") String userName, @Param("loginTime") Date loginTime);

	void insertUserRole(@Param("userid") int userid, @Param("roleid") int roleid);

	void deleteUserRoleByUserID(@Param("userid") int userid);

	int doUpdatePWD(@Param("userId") int userId, @Param("newPWD") String newPWD);

	public AuthUser getAuthUser(@Param("appid") int appid, @Param("guid") String guid);

}
