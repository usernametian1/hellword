package com.zhiyou.auth.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.AuthMenuService;
import com.zhiyou.auth.service.IAuthRoleService;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.common.constance.AuthUserConstance;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.offices.bean.OfficeSpaceDto;
import com.zhiyou.offices.service.OfficeService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.MD5;
import com.zhiyou.utils.RandStrUtils;
import com.zhiyou.utils.SendMsg_webchinese;
import com.zhiyou.utils.StringUtils;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/")
public class HomeController {

	private static final Logger log = Logger.getLogger(HomeController.class);
	@Autowired
	AuthMenuService authMenuService;
	@Autowired
	IAuthUserService authUserService;
	@Autowired
	IAgentService agentService;
	@Autowired
	IAuthRoleService authRoleService;
	@Autowired
	private OfficeService officeService;

	/**
	 * 主页
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(HttpServletRequest req, HttpServletResponse res, Model model) {
		Map<String, Object> params = Utils.getParameters(req);
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<OfficeSpaceDto> officeList = new ArrayList<OfficeSpaceDto>();
		officeList = officeService.FindOfficeSpace(params, paging);
		model.addAttribute("officeList", officeList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return  "office/list";
	}

	
	
	@RequestMapping(value = "/officelist", method = { RequestMethod.POST, RequestMethod.GET })
	public String findForPage(HttpServletRequest req , Model model) {
		Map<String, Object> params = Utils.getParameters(req);
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<OfficeSpaceDto> officeList = new ArrayList<OfficeSpaceDto>();
		officeList = officeService.FindOfficeSpace(params, paging);
		model.addAttribute("officeList", officeList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return  "office/list";
	}
	
	
	/**
	 * 后台用户登录页面
	 * 
	 * @return
	 */
	@RequestMapping("login")
	public String authUserLogin(HttpServletRequest req ,Model model) {
		Map<String, Object> params = Utils.getParameters(req);
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<OfficeSpaceDto> officeList = new ArrayList<OfficeSpaceDto>();
		officeList = officeService.FindOfficeSpace(params, paging);
		model.addAttribute("officeList", officeList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return  "/office/list";
	}

	/**
	 * 代理登录页面
	 * 
	 * @return
	 */
	@RequestMapping("agentLogin/{appid}")
	public String agentLoginPage(@PathVariable("appid") int appid, HttpServletRequest request, Model model) {
		try {
			request.getSession().removeAttribute(Constants.SESSION_USER);
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
		}
		model.addAttribute("appid", appid);
		return "agentLogin";
	}

	/**
	 * 手机首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("mobileIndex/{appid}")
	public String method(@PathVariable("appid") int appid, HttpServletRequest request, Model model) {
		AuthUser loginUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null == loginUser) {
			return "redirect:/agentLogin/" + appid;
		}
		model.addAttribute("loginUser", loginUser);
		return "/mobile/mobileIndex";
	}

	/**
	 * 代理登录动作
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "doAgentLogin", method = { RequestMethod.POST })
	public String doagentLogin(HttpServletRequest req, HttpServletResponse res, Model model) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String appid = req.getParameter("appid");
		username = username + "@@" + appid;
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(false);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			log.error("登录用户验证异常");
			return "redirect:/agentLogin/" + appid;
		}
		// 验证是否登录成功,更新登录时间
		AuthUser user = (AuthUser) subject.getPrincipal();
		if (subject.isAuthenticated()) {
			if (user.getStatus() == AuthUserConstance.FORBIDDEN) {
				req.getSession().removeAttribute(Constants.SESSION_USER);
				SecurityUtils.getSubject().logout();
				model.addAttribute("msg", "<font color=\"#FF0000\">代理申请尚未通过审核</font>");
				return "forward:/agentLogin/" + appid;
			}
			authUserService.updateLoginTime(username);
			return "redirect:/mobileIndex/" + appid;
		} else {
			token.clear();
		}
		return "redirect:/agentLogin/" + appid;
	}

	/**
	 * 404错误页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("404")
	public String errorPage() {
		return "404";
	}

	/**
	 * 403权限不足页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("403")
	public String noAuthorizationPage() {
		return "403";
	}

	/**
	 * 登录验证
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "doLogin", method = { RequestMethod.POST })
	public String doLogin(HttpServletRequest req, HttpServletResponse res, Model model) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(false);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			log.error("登录用户验证异常");
			return "/login";
		}
		AuthUser user = (AuthUser) subject.getPrincipal();
		// 验证是否登录成功,更新登录时间
		if (subject.isAuthenticated()) {
			if (user.getStatus() == AuthUserConstance.FORBIDDEN) {
				req.getSession().removeAttribute(Constants.SESSION_MENU);
				req.getSession().removeAttribute(Constants.SESSION_USER);
				SecurityUtils.getSubject().logout();
				model.addAttribute("msg", "<font color=\"#FF0000\">代理申请尚未通过审核</font>");
				return "/login";
			}
			authUserService.updateLoginTime(username);
			return "redirect:/index";
		} else {
			token.clear();
		}
		return "/login";
	}

	@RequestMapping("/agentLogout/{appid}")
	public String agentLogout(@PathVariable("appid") int appid, HttpServletRequest request) {
		try {
			request.getSession().removeAttribute(Constants.SESSION_MENU);
			request.getSession().removeAttribute(Constants.SESSION_USER);
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
		}
		return "redirect:/agentLogin/" + appid;
	}

	/**
	 * 用户登出
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute(Constants.SESSION_MENU);
			request.getSession().removeAttribute(Constants.SESSION_USER);
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
		}
		return "login";
	}

	/**
	 * 用户忘记密码跳转页面
	 */
	@RequestMapping("forgetPWD/{appid}")
	public String forGetPwd(@PathVariable("appid") int appid, Model model) {
		model.addAttribute("appid", appid);
		return "mobile/info/forgetpwd";
	}

	/**
	 * 发送手机号
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = ("phoneSms"), method = { RequestMethod.POST })
	@ResponseBody
	public void dophonSms(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		Writer writer = res.getWriter();
		JSONObject json = new JSONObject();
		String username = req.getParameter("username") + "@@" + req.getParameter("appid");
		String mobile = req.getParameter("mobile");
		AuthUser authuser = authUserService.doGetAuthUserByName(username);
		if (authuser == null) {
			json.put("code", "2");
			json.put("msg", "用户名输入不正确");
			writer.write(json.toString());
			return;
		}
		String phone = authuser.getMobile();
		if (!mobile.equals(phone)) {
			json.put("code", "1");
			json.put("msg", "手机号不匹配");
			writer.write(json.toString());
			return;
		}
		HttpSession session = req.getSession();
		String checkcode = (String) session.getAttribute("check_code");
		Date check_code_time = (Date) session.getAttribute("check_code_time");
		if (!StringUtils.isEmpty(checkcode) && DateUtils.betweenSecond(new Date(), check_code_time) < 90) {
			json.put("code", "3");
			json.put("msg", "正在发送");
			writer.write(json.toString());
			return;
		}
		try {
			session.removeAttribute("checkcode");
			session.removeAttribute("check_code_time");
		} catch (Exception e) {

		}
		SendMsg_webchinese sms = new com.zhiyou.utils.SendMsg_webchinese();
		String verifyCode = RandStrUtils.randNumeric(6);
		sms.MobileVerify(mobile, verifyCode);
		session.setAttribute("check_code", verifyCode);
		session.setAttribute("check_code_time", new Date());
		json.put("code", "0");
		json.put("msg", "发送成功");
		writer.write(json.toString());
		return;
	}

	/**
	 * 忘记密码修改
	 */
	@RequestMapping(value = ("editforpwd"), method = { RequestMethod.POST, RequestMethod.GET })
	public String doEditPwd(HttpServletRequest req, HttpServletResponse res, Model model) {
		HttpSession session = req.getSession();
		String checkcode = (String) session.getAttribute("check_code");
		String code = req.getParameter("code");
		String appid = req.getParameter("appid");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String newpwd = req.getParameter("newpwd");
		String mobile = req.getParameter("mobile");
		username = username + "@@" + appid;

		AuthUser authuser = authUserService.doGetAuthUserByName(username);
		if (!code.equals(null) && !checkcode.equals(null)) {
			if (code.equalsIgnoreCase(checkcode)) {
				if (null != authuser) {
					if (authuser.getMobile().equals(mobile)) {
						int userId = authuser.getUserId();
						authUserService.doUpdatePWD(userId, password);
						model.addAttribute("appid", appid);
						return "agentLogin";
					} else {
						model.addAttribute("phoneErro", "输入的手机号不匹配！");
						return "mobile/info/forgetpwd";
					}
				} else {
					model.addAttribute("usernameErro", "用户名输入有误");
					return "mobile/info/forgetpwd";
				}
			} else {
				model.addAttribute("codeErro", "验证码输入有误");
				return "mobile/info/forgetpwd";
			}
		}
		return "mobile/info/forgetpwd";
	}

	@RequestMapping("Modify/{userId}")
	public String doModifypwd(@PathVariable("userId") int userId, Model model) {
		AuthUser authuser = authUserService.doGetAuthUserByUserID(userId);
		model.addAttribute("authuser", authuser);
		return "auth/editpwd/edit";
	}

	@RequestMapping("/updateModifyPWD")
	public String doUpdateModeify(HttpServletRequest req, HttpServletResponse res, Model model) {
		String userIdtr = req.getParameter("userId");
		String password = req.getParameter("password");
		String newpassword = req.getParameter("newpassword");
		String renewpwd = req.getParameter("renewpwd");
		int userId = Integer.parseInt(userIdtr);
		AuthUser authuser = authUserService.doGetAuthUserByUserID(userId);
		if (MD5.md5Password(password).equalsIgnoreCase(authuser.getPassword())) {
			if (newpassword.equalsIgnoreCase(renewpwd)) {
				authUserService.doUpdatePWD(authuser.getUserId(), newpassword);
				return "login";
			}
			model.addAttribute("newpasswordErro", "俩次密码输入不对");
			return "auth/editpwd/edit";
		} else {
			model.addAttribute("passwordErro", "输入的原始 密码不对");
			return "auth/editpwd/edit";
		}

	}
}
