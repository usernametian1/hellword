package com.zhiyou.order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.dto.IncomeLog;
import com.zhiyou.order.service.IncomeLogService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/order/income/")
public class IncomeLogController {
	private static final String prefix = "/order/income/";
	private static final Logger log = Logger.getLogger(IncomeLogController.class);
	@Autowired
	IncomeLogService incomeLogServiceImpl;

	@RequestMapping("list")
	public String getList(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		List<IncomeLog> incomeList = incomeLogServiceImpl.findForPage(params, paging);
		model.addAttribute("incomeList", incomeList);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		model.addAttribute("params", params);
		return prefix + "/list";
	}

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
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
		return "/mobile/income/list";
	}

	/**
	 * 异步获取数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("mIncomeData")
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
		List<IncomeLog> agentAnalysis = incomeLogServiceImpl.findForPage(params, paging);
		jsonObject.put("dataList", agentAnalysis);
		jsonObject.put("paging", paging);
		jsonObject.put("params", params);
		jsonObject.put("queryUID", queryUID);
		writer.write(jsonObject.toString());
		writer.close();
		return;

	}

}
