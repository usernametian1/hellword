package com.zhiyou.analysis.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
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
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.analysis.dto.DailyAgentAnalysisDto;
import com.zhiyou.analysis.service.IDailyAgentAnalysisService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.service.IOrderInfoService;
import com.zhiyou.order.service.IncomeLogService;
import com.zhiyou.user.service.IUserInfoService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/analysis/agent")
public class DailyAgentAnalysisController {

	private static final String prefix = "/analysis/agent";
	private static final Logger log = Logger.getLogger(DailyAgentAnalysisController.class);

	@Autowired
	private IDailyAgentAnalysisService dailyAgentlysisService;
	@Autowired
	private IAgentService agentService;
	@Autowired
	private IAuthUserService authUserService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IOrderInfoService orderInfoService;
	@Autowired
	private IncomeLogService incomeLogServiceImpl;

	/**
	 * List信息展示
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String ListDailyAgent(HttpServletRequest request, Model model) {
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
		List<DailyAgentAnalysisDto> dailyAgentAnalysisList = dailyAgentlysisService.getListDailyAgentAnalysisDto(params, paging);
		model.addAttribute("dailyAgentAnalysisList", dailyAgentAnalysisList);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		model.addAttribute("params", params);
		return prefix + "/list";
	}

	/**
	 * 代理的每日数据展示
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = ("/mlist/{appid}/{guid}"), method = { RequestMethod.POST, RequestMethod.GET })
	public String doMlist(@PathVariable("appid") int appid, @PathVariable("guid") int guid, HttpServletRequest req, Model model) {
		List<DailyAgentAnalysisDto> dailyAgentList = dailyAgentlysisService.getMlistDailyAgent(appid, guid);
		model.addAttribute("dailyAgentList", dailyAgentList);
		return "mobile";
	}

	/**
	 * 代理每日数据跳转页
	 * 
	 * @param appid
	 * @param guid
	 * @param model
	 * @return
	 */
	@RequestMapping("/mAgentDailyData/{appid}/{guid}")
	public String method(@PathVariable("appid") int appid, @PathVariable("guid") int guid, Model model) {
		model.addAttribute("appid", appid);
		model.addAttribute("guid", guid);
		return "/mobile/daily/agentDailyData";
	}

	/**
	 * 异步数据加载
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/mlist")
	public void mlist(HttpServletRequest req, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		AuthUser authUser = new AuthUser();
		Map<String, Object> params = Utils.getParameters(req);
		String queryDay = String.valueOf(params.get("queryDay"));
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(req, Constants.SESSION_USER);
		if (null != sessionUser) {
			jsonObject.put("retCode", 1);
			authUser = sessionUser;
			String nowStr = DateUtils.formatSimpleDate(new Date());
			if (nowStr.equals(queryDay) || null == queryDay || "".equals(queryDay)) {
				// 今日钻石消耗
				int todayUsedDiamond = dailyAgentlysisService.getTodayUsedDiamond(authUser);
				jsonObject.put("todayUsedDiamond", todayUsedDiamond);
				// 今日开桌数
				int todayCountTable = dailyAgentlysisService.getTodayCountTable(authUser);
				jsonObject.put("todayCountTable", todayCountTable);
				// 各级绑定信息
				int countTodayBind = userInfoService.getDayBindNum(authUser, 1);
				jsonObject.put("countTodayBind", countTodayBind);
				params.put("userLevel", 2);
				int TodayBindLevel2 = userInfoService.getDayBindNum(authUser, 2);
				jsonObject.put("TodayBindLevel2", TodayBindLevel2);
				params.put("userLevel", 3);
				int TodayBindLevel3 = userInfoService.getDayBindNum(authUser, 3);
				jsonObject.put("TodayBindLevel3", TodayBindLevel3);
				// 今日收益信息
				int income1 = incomeLogServiceImpl.countTodayIncome(authUser, 1);
				int income2 = incomeLogServiceImpl.countTodayIncome(authUser, 2);
				int income3 = incomeLogServiceImpl.countTodayIncome(authUser, 3);
				jsonObject.put("income1", income1);
				jsonObject.put("income2", income2);
				jsonObject.put("income3", income3);
				jsonObject.put("queryDay", nowStr);
			} else {
				// 历史数据
				int dailyAgentBindOrIncome = dailyAgentlysisService.getDailyAgentCountTableOrDiamonds(authUser, queryDay, "useDiamond");
				jsonObject.put("todayUsedDiamond", dailyAgentBindOrIncome);
				// 今日开桌数
				int todayCountTable = dailyAgentlysisService.getDailyAgentCountTableOrDiamonds(authUser, queryDay, "tableNum");
				jsonObject.put("todayCountTable", todayCountTable);
				// 各级绑定信息
				int countTodayBind = dailyAgentlysisService.getDailyAgentBindOrIncome(authUser, queryDay, "daily_agent_bind1_analysis", "bindNum1");
				jsonObject.put("countTodayBind", countTodayBind);
				int countTodayBind2 = dailyAgentlysisService.getDailyAgentBindOrIncome(authUser, queryDay, "daily_agent_bind2_analysis", "bindNum2");
				jsonObject.put("TodayBindLevel2", countTodayBind2);
				int countTodayBind3 = dailyAgentlysisService.getDailyAgentBindOrIncome(authUser, queryDay, "daily_agent_bind3_analysis", "bindNum3");
				jsonObject.put("TodayBindLevel3", countTodayBind3);
				// 日收益
				int income1 = dailyAgentlysisService.getDailyAgentBindOrIncome(authUser, queryDay, "daily_agent_income1_analysis", "income1");
				jsonObject.put("income1", income1);
				int income2 = dailyAgentlysisService.getDailyAgentBindOrIncome(authUser, queryDay, "daily_agent_income2_analysis", "income2");
				jsonObject.put("income2", income2);
				int income3 = dailyAgentlysisService.getDailyAgentBindOrIncome(authUser, queryDay, "daily_agent_income3_analysis", "income3");
				jsonObject.put("income3", income3);
				jsonObject.put("queryDay", queryDay);
			}
		} else {
			jsonObject.put("retCode", 0);
		}
		Writer writer = response.getWriter();
		writer.write(jsonObject.toString());
		writer.close();
		return;
	}
}
