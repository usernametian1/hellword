package com.zhiyou.auth.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.AuthMenuService;
import com.zhiyou.auth.service.IAuthRoleService;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.utils.Constants;

public class MyShiro extends AuthorizingRealm {
	@Autowired
	private IAuthUserService authUserService;
	@Autowired
	private IAuthRoleService authRoleService;
	@Autowired
	private AuthMenuService authMenuService;

	/**
	 * 权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		SimpleAuthorizationInfo simpleAuthorInfo = null;
		List<String> roleList = new ArrayList<String>();
		List<String> permissionList = new ArrayList<String>();
		String currentUsername = (String) super.getAvailablePrincipal(principal);
		AuthUser authUser = authUserService.doGetAuthUserByName(currentUsername);
		if (null != authUser) {
			List<Integer> list = new ArrayList<Integer>();
			List<AuthRole> roles = authRoleService.getRolesByUserID(authUser.getUserId());
			for (AuthRole authRole : roles) {
				roleList.add(authRole.getRoleName());
				list.add(authRole.getRoleId());
			}
			List<AuthMenuDto> resources = authMenuService.getResourcesByRoleIDs(list);
			for (AuthMenuDto authMenuDto : resources) {
				permissionList.add(authMenuDto.getHref());
			}
			// 为当前用户设置角色和权限
			simpleAuthorInfo = new SimpleAuthorizationInfo();
			simpleAuthorInfo.addRoles(roleList);
			simpleAuthorInfo.addStringPermissions(permissionList);
		}
		return simpleAuthorInfo;
	}

	/**
	 * shrio验证登录;
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		AuthUser authUser = authUserService.doGetAuthUserByName(token.getUsername());
		SimpleAuthenticationInfo authcInfo = null;
		if (null != authUser) {
			authcInfo = new SimpleAuthenticationInfo(authUser, authUser.getPassword(), getName());
			authcInfo.setCredentialsSalt(ByteSource.Util.bytes(Constants.MD5_SALT));
			this.setSession(Constants.SESSION_USER, authUser);
		}
		return authcInfo;
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}
}
