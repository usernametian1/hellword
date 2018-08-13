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

import com.zhiyou.agent.dto.AgentBaseRate;
import com.zhiyou.agent.service.IAgentBaseRateService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/agent/baseRate/")
public class AgentBaseRateController {
	private static final String prefix = "/agent/baseRate/";
	private static final Logger log = Logger.getLogger(AgentBaseRateController.class);
	@Autowired
	IAgentBaseRateService agentBaseRateService;

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
		List<AgentBaseRate> list = agentBaseRateService.getList(params, paging);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		return prefix + "list";
	}

	@RequestMapping("insert")
	@ResponseBody
	public void save(AgentBaseRate agentBaseRate, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			agentBaseRateService.insert(agentBaseRate);
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增agentBaseRate异常:{}", e);
			response.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public void update(AgentBaseRate agentBaseRate, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			agentBaseRateService.updateByAppIDLevel(agentBaseRate);
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("更新agentBaseRate异常:{}", e);
			response.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	@RequestMapping("add")
	public String addPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return prefix + "add";
	}

	@RequestMapping("edit/{appid}/{level}")
	public String editPage(@PathVariable("appid") int appid, @PathVariable("level") int level, HttpServletRequest request, HttpServletResponse response, Model model) {
		AgentBaseRate baseRate = agentBaseRateService.getByAppIDLevel(appid, level);
		model.addAttribute("baseRate", baseRate);
		return prefix + "edit";
	}

	@RequestMapping("delete/{appid}/{level}")
	public String delete(@PathVariable("appid") int appid, @PathVariable("level") int level) {
		agentBaseRateService.deletByAppIDLevel(appid, level);
		return ViewUrlUtils.doGetRedirectUrl(prefix, "list");
	}
}
