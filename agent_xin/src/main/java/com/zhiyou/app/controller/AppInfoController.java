package com.zhiyou.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/appinfo")
public class AppInfoController {

	public static final String prefix = "app/info";
	private static final Logger log = Logger.getLogger(AppInfoController.class);
	@Autowired
	IAppInfoService appInfoService;
	

	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryListForAppinfo(HttpServletRequest req, HttpServletResponse res, Model model) throws IOException {
		int appid = -1;
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(req, Constants.SESSION_USER);
		if (null != sessionUser) {
			appid = sessionUser.getAppid();
		}
		Map<String, Object> params = Utils.getParameters(req);
		String appidStr = req.getParameter("qappid");
		if (null == appidStr || "".equals(appidStr)) {
			params.put("appid", appid);
		} else {
			params.put("appid", appidStr);
			params.put("qappid", appidStr);
		}
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<AppInfoDto> appInfoList = appInfoService.findForPage(params, paging);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		model.addAttribute("params", params);
		return prefix + "/list";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET })
	public void save(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		try {
			String appname = req.getParameter("appname");
			appInfoService.insertAppInfo(appname);
			res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增游戏列表失败:{}", e);
			res.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(HttpServletRequest req, HttpServletResponse res, Model model) {
		return prefix + "/add";
	}

	@RequestMapping(value = "/delete/{appid}")
	public String delete(@PathVariable("appid") int appid, HttpServletRequest req, HttpServletResponse res) {
		appInfoService.deleteByID(appid);
		return ViewUrlUtils.doGetRedirectUrl("/appinfo", "/list");
	}

	@RequestMapping(value = "/edit/{appid}")
	public String edit(@PathVariable("appid") int appid, HttpServletRequest req, HttpServletResponse res, Model model) {
		AppInfoDto appInfoDto = appInfoService.findByID(appid);
		model.addAttribute("appInfoDto", appInfoDto);
		return prefix + "/edit";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public void update(AppInfoDto appinfo, HttpServletResponse res, Model model) throws IOException {
		res.setContentType("text/html ; charset=utf-8 ");
		appInfoService.updateAppInfo(appinfo);
		res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
	}
}
