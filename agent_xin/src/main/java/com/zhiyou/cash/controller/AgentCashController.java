package com.zhiyou.cash.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.cash.dto.ApplyCashDto;
import com.zhiyou.cash.service.IAgentCashService;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/cash/")
public class AgentCashController {
	private static final String prefix = "/cash/";
	private static final Logger log = Logger.getLogger(AgentCashController.class);
	@Autowired
	private IAgentCashService agentCashServiceImpl;
	@Autowired
	private IAgentService agentService;
	@Autowired
	IAuthUserService authUserService;

	/**
	 * 提现List信息展示
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String cashApplyList(HttpServletRequest request, Model model) {
		int appid = -1;
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			appid = sessionUser.getAppid();
		}
		Map<String, Object> params = Utils.getParameters(request);
		String appidStr = request.getParameter("appid");
		if (null == appidStr || "".equals(appidStr)) {
			params.put("appid", appid);
		}
		PagingDto paging = new PagingDto(request.getParameter("curPageNum"));
		List<ApplyCashDto> cashApplyList = agentCashServiceImpl.getListCashApplyDto(params, paging);
		model.addAttribute("cashApplyList", cashApplyList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return prefix + "list";
	}

	@RequestMapping("mMyCashRecord")
	public String cashRecords(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ApplyCashDto> records = new ArrayList<ApplyCashDto>();
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			try {
				records = agentCashServiceImpl.getAgentApplyRecords(sessionUser);
			} catch (Exception e) {
			}
		}
		model.addAttribute("records", records);
		return "/mobile/cash/cashRecords";
	}

	/**
	 * 跳转到申请提现页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("applyCash/{appid}/{guid}")
	public String applyCash(@PathVariable("appid") int appid, @PathVariable("guid") String guid,
			HttpServletRequest request, Model model) {
		AuthUser authUser = authUserService.getAuthUser(appid, guid);
		model.addAttribute("authUser", authUser);
		return prefix + "add";
	}

	/**
	 * 跳转到申请提现页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("mApplyCash/{appid}/{guid}")
	public String mApplyCash(@PathVariable("appid") int appid, @PathVariable("guid") String guid, Model model) {
		int balance = 0;
		AuthUser authUser = new AuthUser();
		int isCashAble = 0;
		try {
			authUser = authUserService.getAuthUser(appid, guid);
			AgentDto agent = agentService.getAgent(appid, Integer.parseInt(guid));
			balance = agent.getBalance();
			String openid = agent.getOpenid();
			if (openid != null && !"".equals(openid)) {
				isCashAble = 1;
			}
		} catch (Exception e) {
		}
		model.addAttribute("authUser", authUser);
		model.addAttribute("balance", balance);
		model.addAttribute("isCashAble", isCashAble);
		return "/mobile/cash/add";
	}

	@RequestMapping("sendCheckCode")
	public void method(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject sendCheckCode = agentCashServiceImpl.sendCheckCode(request);
		Writer writer = response.getWriter();
		writer.write(sendCheckCode.toString());
		writer.close();
		return;
	}

	/**
	 * 
	 * 插入记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/mInserApply", method = { RequestMethod.POST })
	public void mInserApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonObject = agentCashServiceImpl.insertCashApply(request);
		Writer writer = response.getWriter();
		writer.write("<h3 align=\"center\">" + jsonObject.getString("msg") + "</h3>");
		writer.close();
		return;
	}

	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	@ResponseBody
	public void insertCashApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonObject = agentCashServiceImpl.insertCashApply(request);
		Writer writer = response.getWriter();
		writer.write("<h3 align=\"center\">" + jsonObject.getString("msg") + "</h3>");
		writer.close();
		return;
	}

	@RequestMapping("/edit/{orderId}")
	public String editPage(@PathVariable("orderId") String orderId, Model model) {
		ApplyCashDto applyCashDto = new ApplyCashDto();
		if (null != orderId && !"".equals(orderId)) {
			try {
				applyCashDto = agentCashServiceImpl.getApplyCashDtoByOrderID(orderId);
			} catch (Exception e) {
				log.error("orderID查询ApplyCashDto异常");
			}
		}
		model.addAttribute("applyCash", applyCashDto);
		return prefix + "edit";
	}

	/**
	 * 更新状态
	 * 
	 * @param ApplyCashDto
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public void updateCashApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonObject = agentCashServiceImpl.updateCashApplyState(request);
		Writer writer = response.getWriter();
		writer.write("<h3 align=\"center\">" + jsonObject.getString("msg") + "</h3>");
		writer.close();
		return;
	}
}
