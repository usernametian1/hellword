package com.zhiyou.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.user.dto.UserInfoDto;
import com.zhiyou.user.service.IUserInfoService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {

	public static final String prefix = "user/info";
	@Autowired
	private IUserInfoService useInfoService;

	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String doUserInfoList(HttpServletRequest request, HttpServletResponse res, Model model) {
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
		List<UserInfoDto> userInfoList = new ArrayList<UserInfoDto>();
		userInfoList = useInfoService.findForPage(params, paging);
		model.addAttribute("userInfoList", userInfoList);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		model.addAttribute("params", params);
		return prefix + "/list";
	}

	/*
	 * 页面跳转
	 */
	@RequestMapping("mList")
	public String mList(HttpServletRequest request, HttpServletResponse response, Model model) {
		int appid = -1;
		String guid = "";
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			appid = sessionUser.getAppid();
			guid = sessionUser.getGuid();
		}
		model.addAttribute("appid", appid);
		model.addAttribute("guid", guid);
		return "mobile/player/list";
	}

	/**
	 * 异步获取数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("mPlayerData")
	public void method(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> params = Utils.getParameters(request);
		String queryUID = request.getParameter("queryUID");
		if (null != queryUID && !"".equals(queryUID)) {
			params.put("guid", queryUID);
		}
		PagingDto paging = new PagingDto(Integer.parseInt(request.getParameter("pageSize")), request.getParameter("curPageNum"));
		List<UserInfoDto> AgentAnalysis = useInfoService.findForPage(params, paging);
		jsonObject.put("dataList", AgentAnalysis);
		jsonObject.put("paging", paging);
		jsonObject.put("params", params);
		jsonObject.put("queryUID", queryUID);
		writer.write(jsonObject.toString());
		writer.close();
		return;

	}

	/**
	 * 异步查询
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("mPageSearch")
	public void mPageSearch(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		int appid = 0;
		int guid = 0;
		int superGuid = 0;
		UserInfoDto userInfoDto = new UserInfoDto();
		try {
			appid = Integer.parseInt(request.getParameter("appid"));
			guid = Integer.parseInt(request.getParameter("queryUID"));
			superGuid = Integer.parseInt(request.getParameter("superGuid"));
			userInfoDto = useInfoService.getUserInfoBySuperGuid(appid, guid, superGuid);
			jsonObject.put("code", 1);
		} catch (Exception e) {
			jsonObject.put("code", 0);
		}
		jsonObject.put("userInfoDto", userInfoDto);
		writer.write(jsonObject.toString());
		writer.close();
		return;

	}

	/**
	 * "" 同步玩家信息接口,写入操作
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/synGuser", method = RequestMethod.POST)
	public void synGuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Writer writer = response.getWriter();
		Map<String, String> params = Utils.getParameterMap(request);

		JSONObject jsonObject = useInfoService.insertSynGuser(params);
		writer.write(jsonObject.toString());
		writer.close();
		return;
	}

	/**
	 * 同步玩家绑定信息接口,更新操作
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	/*
	 * @RequestMapping(value = "/updateGuser", method = { RequestMethod.POST,
	 * RequestMethod.GET }) public void updateGuser(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException {
	 * response.setContentType("text/html;charset=utf-8"); Writer writer =
	 * response.getWriter(); // 组织接受的参数 Map<String, String> params =
	 * Utils.getParameterMap(request); // JSONObject jsonObject =
	 * useInfoService.updatesynGuser(params); //
	 * writer.write(jsonObject.toString()); writer.close(); return; }
	 */
}
