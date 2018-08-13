package com.zhiyou.auth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.agent.dao.AgentDao;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.auth.dao.AuthUserDao;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.common.constance.AgentConstance;
import com.zhiyou.common.constance.AuthUserConstance;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.MD5;
import com.zhiyou.utils.StringUtils;

@Service("authUserService")
public class AuthUserServiceImpl implements IAuthUserService {

	@Autowired
	private AuthUserDao authUserDao;

	@Autowired
	private AgentDao agentDao;

	// @Autowired
	// private UserInfoDao userInfoDao;

	/**
	 * 获取 AuthUser列表-分页
	 */
	@Override
	public List<AuthUser> doGetAuthUserList(Map<String, Object> params, PagingDto paging) {
		int totalRows = authUserDao.doGetAuthUserListCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return authUserDao.doGetAuthUserList(params);
	}

	/**
	 * userId获取AuthUser
	 */
	@Override
	public AuthUser doGetAuthUserByUserID(int userId) {
		return authUserDao.doGetAuthUserById(userId);
	}

	/**
	 * userId删除AuthUser,同时删除关联角色
	 */
	@Override
	public void doDeleteAuthUser(int userid) {
		if (userid > 0) {
			authUserDao.doDeleteAuthUser(userid);
			authUserDao.doDeleteAuthUserRole(userid);
		}
	}

	/**
	 * 名称获取AuthUser
	 */
	@Override
	public AuthUser doGetAuthUserByName(String username) {

		return authUserDao.doGetAuthUserByName(username);
	}

	/**
	 * 申请代理的 authUser表的插入
	 * 
	 */

	public int doGetAuthUser(HttpServletRequest req) {
		int retValue = 0;
		String appidStr = req.getParameter("appid");
		String guidStr = req.getParameter("guid");
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String password1 = req.getParameter("password1");
		String phone = req.getParameter("phone");
		String address = req.getParameter("adress");
		String qq = req.getParameter("qq");
		String openid = req.getParameter("openid");

		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(password1)) {
			retValue = 1;// 密码不能为空
			return retValue;
		}

		if (!password.equals(password1)) {
			retValue = 2;// 密码不一致
			return retValue;
		}

		int appid = 0;
		try {
			appid = Integer.parseInt(appidStr);
		} catch (Exception e) {
			retValue = 3;// 参数错误
			return retValue;
		}

		int guid = 0;
		try {
			guid = Integer.parseInt(guidStr);
		} catch (Exception e) {
			retValue = 4;// UID错误
			return retValue;
		}

		// 判断用户是否存在
		// UserInfoDto userInfo = userInfoDao.getUserInfo(guid, appid);
		// if (userInfo == null) {
		// retValue = 5;// 用户不存在
		// return retValue;
		// }

		// 判断是否已经是代理
		AgentDto agent = agentDao.getAgent(appid, guid);
		if (agent != null) {
			if (agent.getStatus() == AgentConstance.APPLY_PASS_STATUS) {
				retValue = 6;// 已经是代理，请登录代理系统
				return retValue;
			} else if (agent.getStatus() == AgentConstance.SUBMIT_STATUS) {
				retValue = 7;// 已经提交申请，请耐心等待
				return retValue;
			} else if (agent.getStatus() == AgentConstance.REFUSE_STATUS) {
				retValue = 8;// 已经拒绝，请联系管理员
				return retValue;
			}
		}

		agent = new AgentDto();
		agent.setAppid(appid);
		agent.setGuid(guid);
		agent.setName(name);
		agent.setPhone(phone);
		agent.setStatus(AgentConstance.SUBMIT_STATUS);
		agent.setApplyTime(new Date());
		agent.setSyn(AgentConstance.NOT_SYN);
		agent.setState(AgentConstance.NORMAL_STATE);
		agent.setOpenid(openid);
		agentDao.insert(agent);

		AuthUser authUser = new AuthUser();
		authUser.setAppid(appid);
		authUser.setUsername(guid + "@@" + appid);
		authUser.setPassword(password);
		authUser.setRealname(name);
		authUser.setGuid(String.valueOf(guid));
		authUser.setAddress(address);
		authUser.setMobile(phone);
		authUser.setQq(qq);
		authUser.setRegTime(new Date());
		authUser.setStatus(AuthUserConstance.FORBIDDEN);
		authUserDao.doInsertAuthUser(authUser);
		return retValue;
	}

	/**
	 * 新增AuthUser
	 */
	@Override
	public boolean doInsertAuthUser(AuthUser authUser) {
		AuthUser user = authUserDao.doGetAuthUserByName(authUser.getUsername());
		// 已存在该userName
		if (null != user) {
			return false;
		}
		authUser.setPassword(MD5.md5Password(authUser.getPassword()));
		authUser.setRegTime(new Date());
		return authUserDao.doInsertAuthUser(authUser);
	}

	/**
	 * 更新AuthUser信息
	 */
	@Override
	public boolean doUpdateAuthUser(AuthUser authUser) {
		return authUserDao.doUpdateAuthUser(authUser);
	}

	/**
	 * 查询所有AuthUser
	 */
	@Override
	public List<AuthUser> doGetAllUserList() {
		return authUserDao.doGetAllUserList();
	}

	@Override
	public void doDeleteAuthUserRole(int userId) {
		authUserDao.doDeleteAuthUserRole(userId);

	}

	@Override
	public void updateLoginTime(String userName) {
		authUserDao.updateLoginTime(userName, new Date());

	}

	@Override
	public void insertUserRole(int userid, int[] roleids) {
		if (null == roleids || roleids.length < 1) {
			return;
		}
		authUserDao.deleteUserRoleByUserID(userid);
		for (int roleid : roleids) {
			authUserDao.insertUserRole(userid, roleid);
		}
	}

	@Override
	public void deleteUserRoleByUserID(int userid) {
		authUserDao.deleteUserRoleByUserID(userid);
	}

	@Override
	public int doUpdatePWD(int userId, String PWD) {
		String newPWD = MD5.md5Password(PWD);
		return authUserDao.doUpdatePWD(userId, newPWD);

	}

	@Override
	public AuthUser getAuthUser(int appid, String guid) {
		return authUserDao.getAuthUser(appid, guid);

	}

	@Override
	public int updateByCondition(Map<String, Object> params) {
		return authUserDao.updateByCondition(params);
	}
}
