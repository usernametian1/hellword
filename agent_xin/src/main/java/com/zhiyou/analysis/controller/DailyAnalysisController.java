package com.zhiyou.analysis.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.analysis.dto.DailyAnalysisDto;
import com.zhiyou.analysis.service.IDailyAnalysisService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/analysis")
public class DailyAnalysisController {
	private static final String prefix = "/analysis/";
	private static final Logger log = Logger.getLogger(DailyAnalysisController.class);
	@Autowired
	private IDailyAnalysisService dailyAnalysisServiceImpl;

	/**
	 * 同步接口 接受数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/synDailyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void synDailyInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> parameters = Utils.getParameterMap(request);
		JSONObject jsonObject = dailyAnalysisServiceImpl.insertDailyAnalysisDto(parameters);
		Writer writer = response.getWriter();
		writer.write(jsonObject.toString());
		writer.close();
		return;
	}

	/**
	 * List信息展示
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/dailyInfoList")
	public String DailyInfoList(HttpServletRequest request, Model model) {
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
		List<DailyAnalysisDto> dailyAnalysisList = dailyAnalysisServiceImpl.getListDailyAnalysisDto(params, paging);
		model.addAttribute("dailyAnalysisList", dailyAnalysisList);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		model.addAttribute("params", params);
		return prefix + "list";
	}
}
