package com.zhiyou.auth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthRoleService;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.MD5;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/auth/user")
public class AuthUserController {

	private static final String PREFIX = "auth/user/"; // 返回jsp前缀
	private static final String PRETWO = "mobile/info/";
	private static final Logger log = Logger.getLogger(AuthUserController.class);
	@Autowired
	private IAuthUserService authUserService;
	@Autowired
	private IAuthRoleService authRoleService;

	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String doGetAuthUserList(HttpServletRequest request, HttpSession session, Model model) {
		int appid = -1;
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			appid = sessionUser.getAppid();
		}
		Map<String, Object> params = Utils.getParameters(request);
		String appidStr = request.getParameter("qappid");
		if (null == appidStr || "".equals(appidStr)) {
			params.put("appid", appid);
		} else {
			params.put("appid", appidStr);
			params.put("qappid", appidStr);
		}
		PagingDto paging = new PagingDto(request.getParameter("curPageNum"));
		List<AuthUser> userList = new ArrayList<AuthUser>();
		userList = authUserService.doGetAuthUserList(params, paging);
		model.addAttribute("params", params);
		model.addAttribute("userList", userList);
		model.addAttribute("appid", appid);
		model.addAttribute("paging", paging);
		return PREFIX + "list";
	}

	/**
	 * 新增user操作,同时考虑对应的角色
	 * 
	 * @param roleIDs
	 * @param authUser
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/insert", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void doInsertAuthUser(@RequestParam(name = "roleIDs", required = false) int[] roleIDs, AuthUser authUser, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			boolean ret = authUserService.doInsertAuthUser(authUser);
			if (ret) {
				authUserService.insertUserRole(authUser.getUserId(), roleIDs);
				log.info("新增authUser成功");
				response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
			} else {
				log.info("新增authUser失败");
				response.getWriter().write("<h3 align=\"center\">UserName重复!</h3>");
			}
		} catch (Exception e) {
			log.error("新增authUser异常:{}", e);
			response.getWriter().write("<h3 align=\"center\">操作异常!</h3>");
		}
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String doAddAuthUser(Model model) {
		List<AuthRole> roleList = new ArrayList<AuthRole>();
		try {
			roleList = authRoleService.doGetRoleList();
		} catch (Exception e) {
			log.error("authRoleService.doGetRoleList()异常");
		}
		model.addAttribute("roleList", roleList);
		return PREFIX + "/add";
	}

	@RequestMapping("/edit/{userId}")
	public String editAuthUser(@PathVariable("userId") int userId, HttpServletRequest req, Model model) {
		List<AuthRole> roleList = new ArrayList<AuthRole>();
		AuthUser authUser = new AuthUser();
		try {
			authUser = authUserService.doGetAuthUserByUserID(userId);
			roleList = authRoleService.doGetRoleList();
		} catch (Exception e) {
			log.error("编辑AuthUser异常:{}", e);
		}
		model.addAttribute("authUser", authUser);
		model.addAttribute("roleList", roleList);
		return PREFIX + "edit";
	}

	// 修改密码
	@RequestMapping("/editPage/{userId}")
	public String editPage(@PathVariable("userId") int userId, Model model) {
		model.addAttribute("userId", userId);
		return PRETWO + "editpwd";
	}

	// 修改Authuser登录密码
	@RequestMapping("/resetPWD")
	public String resetPWD(@RequestParam("userId") int userId, @RequestParam(name = "username", defaultValue = "", required = true) String username,
			@RequestParam(name = "password", defaultValue = "", required = true) String password, @RequestParam(name = "newPWD", defaultValue = "", required = true) String newPWD,
			@RequestParam(name = "renewPWD", defaultValue = "", required = true) String renewPWD, Model model) {
		AuthUser authuser = authUserService.doGetAuthUserByUserID(userId);
		String dbPWD = authuser.getPassword();
		String dbusername = authuser.getUsername();
		if (null == password || "".equals(password)) {
			model.addAttribute("passwordErro", "密码不能为空");
			return PRETWO + "editpwd";
		}
		if (null == newPWD || "".equals(newPWD)) {
			model.addAttribute("newPWDErro", "新密码不能为空");
			return PRETWO + "editpwd";
		}
		if (!newPWD.equals(renewPWD)) {
			model.addAttribute("newPWDErro", "输入的新密码不一致");
			return PRETWO + "editpwd";
		}

		if (dbPWD.equalsIgnoreCase(MD5.md5Password(password))) {
			// 允许修改密码
			if (newPWD.equalsIgnoreCase(renewPWD)) {
				authUserService.doUpdatePWD(authuser.getUserId(), newPWD);
				return "mobile/mobileIndex";
			}
			model.addAttribute("newPWDErro", "两次密码不一致");
			return PRETWO + "editpwd";
		} else {
			// 禁止修改
			model.addAttribute("userId", userId);
			model.addAttribute("passwordErro", "原始密码输入有误");
			return PRETWO + "editpwd";
		}
	}

	@RequestMapping("/update")
	@ResponseBody
	public void updateAuthUser(@RequestParam(name = "roleIDs", required = false) int[] roleIDs, AuthUser authUser, HttpServletRequest req, HttpServletResponse response) throws IOException {

		response.setContentType("text/html;charset=utf-8");
		try {
			boolean ret = authUserService.doUpdateAuthUser(authUser);
			if (ret) {
				authUserService.deleteUserRoleByUserID(authUser.getUserId());
				authUserService.insertUserRole(authUser.getUserId(), roleIDs);
			} else {
				response.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
			}
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("更新authUser异常:{}", e);
			e.printStackTrace();
			response.getWriter().write("<h3 align=\"center\">操作异常!</h3>");
		}
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String deleteAuthUser(@PathVariable(name = "userId", required = true) int userId, HttpServletRequest req, HttpServletResponse res) {
		try {
			authUserService.doDeleteAuthUser(userId);
		} catch (Exception e) {
			log.error("删除authUser异常:{}", e);
		}
		return ViewUrlUtils.doGetRedirectUrl(PREFIX, "list");
	}

}
