package com.zhiyou.agent.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.zhiyou.agent.dto.AgentRate;
import com.zhiyou.agent.service.IAgentRateService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/agent/rate/")
public class AgentRateController {
	private static final String prefix = "/agent/rate/";
	private static final Logger log = Logger.getLogger(AgentRateController.class);
	@Autowired
	IAgentRateService agentRateService;

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
		List<AgentRate> list = agentRateService.getList(params, paging);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		model.addAttribute("appid", appid);
		return prefix + "list";
	}

	@RequestMapping("insert")
	@ResponseBody
	public void save(AgentRate agentBaseRate, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			agentRateService.insert(agentBaseRate);
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增agentRate异常:{}", e);
			response.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
		// return prefix + "list";
	}
	
		

	@RequestMapping("update")
	@ResponseBody
	public void update(AgentRate agentBaseRate, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			agentRateService.update(agentBaseRate);
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			response.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
			log.error("更新agentRate异常:{}", e);
		}
		// return prefix + "list";
	}

	@RequestMapping("add")
	public String addPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return prefix + "add";
	}

	@RequestMapping("edit/{guid}/{appid}")
	public String editPage(@PathVariable("guid") int guid, @PathVariable("appid") int appid, HttpServletRequest request, HttpServletResponse response, Model model) {
		AgentRate Rate = agentRateService.get(guid, appid);
		model.addAttribute("rate", Rate);
		return prefix + "edit";
	}

	@RequestMapping("delete/{guid}/{appid}")
	public String delete(@PathVariable("guid") int guid, @PathVariable("appid") int appid, HttpServletRequest request, HttpServletResponse response, Model model) {
		agentRateService.delete(guid, appid);
		return ViewUrlUtils.doGetRedirectUrl(prefix, "list");
	}
}
