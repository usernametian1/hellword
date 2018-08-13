package com.zhiyou.notice.controller;

import java.io.IOException;
import java.io.Writer;
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

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.notice.dto.AgentNoticeDto;
import com.zhiyou.notice.service.AgentNoticeService;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/agentNotice")
public class AgentNoticeController {

	private static final String prefix = "agentnotice";
	private static final Logger log = Logger.getLogger(AgentNoticeController.class);

	@Autowired
	private AgentNoticeService agentNoticeService;

	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String ListForagetNotice(HttpServletRequest req, HttpServletResponse res, Model model) {
		Map<String, Object> params = Utils.getParameters(req);
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<AgentNoticeDto> agentNoticeList = agentNoticeService.findForpage(params, paging);
		model.addAttribute("agentNoticeList", agentNoticeList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return prefix + "/list";
	}

	@RequestMapping(value = "/edit/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String editForagentNotice(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res, Model model) {
		AgentNoticeDto agentNoticeDto = agentNoticeService.findById(id);
		model.addAttribute("agentNoticeDto", agentNoticeDto);
		return prefix + "/edit";
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public void updateForagentNotice(AgentNoticeDto agentNoticeDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		try {
			agentNoticeService.updateAgentNotice(agentNoticeDto);
			res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改公告失败");
			res.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	@RequestMapping(value = "/insert", method = { RequestMethod.POST, RequestMethod.GET })
	public void insertForagentNotice(AgentNoticeDto agentNoticeDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		try {
			agentNoticeService.insertAgentNotice(agentNoticeDto);
			res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("修改公告失败");
			res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		}
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String addForagentNotice(HttpServletRequest req) {
		return prefix + "/add";

	}

	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteForagentNotice(@PathVariable("id") int id) {
		agentNoticeService.deleteById(id);
		return ViewUrlUtils.doGetRedirectUrl("agentNotice", "/list");
	}

	@RequestMapping("/mNotice/{appid}")
	public String mNotice(@PathVariable("appid") int appid, Model model) {
		model.addAttribute("appid", appid);
		return "/mobile/notice/mNotice";
	}

	@RequestMapping("/mNoticeList")
	public void mNoticeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> params = Utils.getParameters(request);
		params.put("state", 1);
		PagingDto paging = new PagingDto(request.getParameter("curPageNum"));
		List<AgentNoticeDto> agentNoticeList = agentNoticeService.findForpage(params, paging);
		Writer writer = response.getWriter();
		jsonObject.put("agentNoticeList", agentNoticeList);
		jsonObject.put("paging", paging);
		jsonObject.put("params", params);
		writer.write(jsonObject.toString());
		return;
	}
}
