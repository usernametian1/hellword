package com.zhiyou.agent.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.zhiyou.agent.dto.AgentBaseRate;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.dto.AgentRate;
import com.zhiyou.agent.service.IAgentBaseRateService;
import com.zhiyou.agent.service.IAgentRateService;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.common.constance.AgentConstance;
import com.zhiyou.common.constance.AuthUserConstance;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.service.IOrderInfoService;
import com.zhiyou.order.service.IncomeLogService;
import com.zhiyou.user.dto.UserInfoDto;
import com.zhiyou.user.service.IUserInfoService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.StringUtils;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;
import com.zhiyou.weixin.WxOauth;

@Controller
@RequestMapping("/agent")
public class AgentContorller {
	private static final Logger log = Logger.getLogger(AgentContorller.class);
	private static final String prefix = "agent";
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
	@Autowired
	IAgentBaseRateService agentBaseRateService;
	@Autowired
	IAgentRateService agentRateService;

	/** 展示 代理列表 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String getList(HttpServletRequest req, HttpServletResponse res, Model model) {
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
		List<AgentDto> AgentList = new ArrayList<AgentDto>();
		AgentList = agentService.findForPage(params, paging);
		model.addAttribute("AgentList", AgentList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		model.addAttribute("appid", appid);
		return prefix + "/list";
	}

	/** 编辑 代理信息 */
	@RequestMapping(value = "/edit/{appid}/{guid}", method = { RequestMethod.POST, RequestMethod.GET })
	public String getEdilt(@PathVariable("appid") int appid, @PathVariable("guid") int guid, Model model) {
		AgentDto agentDto = agentService.getAgent(appid, guid);
		model.addAttribute("agentDto", agentDto);
		List<AgentBaseRate> AgentBaseRate = agentBaseRateService.getListBaseRateByAppid(appid);
		model.addAttribute("baseRateList", AgentBaseRate);
		return prefix + "/edit";
	}

	/** 提交审核 通过代理申请审核 */
	@RequestMapping("/update")
	@ResponseBody
	public void editPage(AgentDto agentDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		try {
			if (agentDto.getStatus() == AgentConstance.APPLY_PASS_STATUS) {
				agentService.doAudit(agentDto);
				AgentRate agentRate = new AgentRate();
				agentRate.setAppid(agentDto.getAppid());
				agentRate.setGuid(agentDto.getGuid());
				AgentBaseRate baseRate = agentBaseRateService.getByAppIDLevel(agentDto.getAppid(), agentDto.getAgentLevel());
				agentRate.setRate1(baseRate.getRate1());
				agentRate.setRate2(baseRate.getRate2());
				agentRate.setRate3(baseRate.getRate3());
				agentRateService.insert(agentRate);
				res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
			} else {
				agentService.updateAgent(agentDto);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("appid", agentDto.getAppid());
				params.put("guid", agentDto.getGuid());
				params.put("status", AuthUserConstance.FORBIDDEN);
				authUserService.updateByCondition(params);
				res.getWriter().write("<h3 align=\"center\">成功驳回!</h3>");
			}

		} catch (Exception e) {
			log.error("更新agent异常:{}", e);
			res.getWriter().write("<h3 align=\"center\">操作异常!</h3>");
		}
		return;
	}

	@RequestMapping("/delete/{guid}/{appid}")
	public String deleteAgent(@PathVariable("guid") int guid, @PathVariable("appid") int appid, HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean flag = agentService.deleteAgent(guid, appid);
		if (flag) {
			log.info("删除成功");
		} else {
			log.error("删除失败");
		}
		return ViewUrlUtils.doGetRedirectUrl(prefix, "/list");
	}

	@RequestMapping("/applyAgent/{appid}/{guid}")
	public String addAgent(@PathVariable("appid") int appid, @PathVariable("guid") int guid, Model model) {
		model.addAttribute("appid", appid);
		model.addAttribute("guid", guid);
		return prefix + "/add";
	}

	@RequestMapping("/mApplyAgent/{appid}")
	public String MAddAgent(@PathVariable("appid") int appid, Model model) {
		model.addAttribute("appid", appid);
		return prefix + "/madd";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insertAgent(HttpServletRequest req, HttpServletResponse res, Model model) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		Writer writer = res.getWriter();
		try {
			JSONObject jsonObject = agentService.applyAgent(req);
			writer.write("<h3 align=\"center\">" + jsonObject.getString("msg") + "</h3>");
		} catch (Exception e) {
			log.error("提交代理申请异常:{}", e);
			writer.write("<h3 align=\"center\">操作异常!</h3>");
		}
		writer.close();
		return;
	}

	@RequestMapping("/personalInfo")
	public String personalInfo(HttpServletRequest request, Model model) {
		AgentDto agentDto = new AgentDto();
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser && !StringUtils.isEmpty(sessionUser.getGuid())) {
			agentDto = agentService.getAgent(sessionUser.getAppid(), Integer.valueOf(sessionUser.getGuid()));
		}
		model.addAttribute("sessionUser", sessionUser);
		model.addAttribute("agentInfo", agentDto);
		return prefix + "/personalInfo";
	}

	@RequestMapping("mSpread")
	public String mSpread(HttpServletRequest request, HttpServletResponse response, Model model) {
		AuthUser authUser = new AuthUser();
		AgentDto agentDto = new AgentDto();
		UserInfoDto userInfo = new UserInfoDto();
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			Map<String, Object> params = new HashMap<String, Object>();
			authUser = sessionUser;
			agentDto = agentService.getAgent(sessionUser.getAppid(), Integer.valueOf(sessionUser.getGuid()));
			userInfo = userInfoService.getUserInfo(Integer.valueOf(sessionUser.getGuid()), sessionUser.getAppid());
			params.put("appid", sessionUser.getAppid());
			params.put("superGuid", sessionUser.getGuid());
			// 用绑定信息
			int countTodayBind = userInfoService.getDayBindNum(sessionUser, 1);
			int countMothBind = userInfoService.countMothBind(params);
			model.addAttribute("countTodayBind", countTodayBind);
			model.addAttribute("countMothBind", countMothBind);
			// 各级绑定信息
			params.put("userLevel", 2);
			int TodayBindLevel2 = userInfoService.getDayBindNum(sessionUser, 2);
			model.addAttribute("TodayBindLevel2", TodayBindLevel2);
			params.put("userLevel", 3);
			int TodayBindLevel3 = userInfoService.getDayBindNum(sessionUser, 3);
			model.addAttribute("TodayBindLevel3", TodayBindLevel3);
			// 充值信息
			int countAllTotalFee = orderInfoService.countAllTotalFee(params);
			int countTodayTotalFee = orderInfoService.countTodayTotalFee(params);
			int countMothTotalFee = orderInfoService.countMothTotalFee(params);
			model.addAttribute("countAllTotalFee", countAllTotalFee);
			model.addAttribute("countTodayTotalFee", countTodayTotalFee);
			model.addAttribute("countMothTotalFee", countMothTotalFee);
			// 今日收益信息
			int income1 = incomeLogServiceImpl.countTodayIncome(sessionUser, 1);
			int income2 = incomeLogServiceImpl.countTodayIncome(sessionUser, 2);
			int income3 = incomeLogServiceImpl.countTodayIncome(sessionUser, 3);
			model.addAttribute("income1", income1);
			model.addAttribute("income2", income2);
			model.addAttribute("income3", income3);
		}
		model.addAttribute("sessionUser", authUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("agentInfo", agentDto);
		return "/mobile/spread/spread";
	}

	@RequestMapping("/mPersonalIndex")
	public String mPersonalIndex(HttpServletRequest request, Model model) {
		AuthUser authUser = new AuthUser();
		AgentDto agentDto = new AgentDto();
		UserInfoDto userInfo = new UserInfoDto();
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			authUser = sessionUser;
			agentDto = agentService.getAgent(sessionUser.getAppid(), Integer.valueOf(sessionUser.getGuid()));
			userInfo = userInfoService.getUserInfo(Integer.valueOf(sessionUser.getGuid()), sessionUser.getAppid());
		}
		model.addAttribute("sessionUser", authUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("agentInfo", agentDto);
		return "/mobile/info/mPersonalIndex";
	}

	@RequestMapping("/mPersonalInfo")
	public String mPersonalInfo(HttpServletRequest request, Model model) {
		AuthUser authUser = new AuthUser();
		AgentDto agentDto = new AgentDto();
		UserInfoDto userInfo = new UserInfoDto();
		AuthUser sessionUser = (AuthUser) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
		if (null != sessionUser) {
			authUser = sessionUser;
			agentDto = agentService.getAgent(sessionUser.getAppid(), Integer.valueOf(sessionUser.getGuid()));
			userInfo = userInfoService.getUserInfo(Integer.valueOf(sessionUser.getGuid()), sessionUser.getAppid());
		}
		model.addAttribute("sessionUser", authUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("agentInfo", agentDto);
		return "/mobile/info/mPersonalInfo";
	}
    @RequestMapping("/mPersonalInfoi")
    public String mPersonalInfo1(HttpServletRequest request , Model model) {
    	AuthUser authUser = new AuthUser();
          AgentDto agentDto = new AgentDto();
          UserInfoDto userInfo = new UserInfoDto();
          AuthUser sessionUser = (AuthUser)WebUtils.getSessionAttribute(request, null);
          if(null != sessionUser) {  
        	  authUser = sessionUser;
        	  agentDto = agentService.getAgent(sessionUser.getAppid(), Integer.valueOf(0));
          }
          return null;
     }
    
	
	/**
	 * 微信绑定回调
	 * 
	 * @param appid
	 * @param guid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getOpenid/{appid}/{guid}")
	public String getOpenid(@PathVariable("appid") int appid, @PathVariable("guid") int guid, HttpServletRequest request) {
		String code = request.getParameter("code");
		JSONObject result = WxOauth.getAccessToken(WxOauth.appid, WxOauth.secret, code);
		log.info("微信绑定返回:" + result.toString());
		if (result.containsKey("openid") && !"".equalsIgnoreCase(result.getString("openid"))) {// 微信返回openID成功
			String openid = result.getString("openid");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("appid", appid);
			params.put("guid", guid);
			params.put("openid", openid);
			agentService.updateByCondition(params);
			authUserService.updateByCondition(params);
			return "redirect:/agent/mPersonalIndex";
		} else {
			log.error("绑定失败");
			return "redirect:/404";
		}
	}
}
