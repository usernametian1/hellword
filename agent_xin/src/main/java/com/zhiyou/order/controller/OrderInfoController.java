package com.zhiyou.order.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.controller.AuthMenuController;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.offices.bean.OfficeSpaceDto;
import com.zhiyou.offices.service.OfficeService;
import com.zhiyou.order.dto.OrderInfoDTO;
import com.zhiyou.order.service.IOrderInfoService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/order/")
public class OrderInfoController {

	private static final String prefix = "/order/";
	private static final Logger log = Logger.getLogger(AuthMenuController.class);
	@Autowired
	IOrderInfoService orderInfoServiceImpl;
	@Autowired
	private OfficeService officeService;
	

	@RequestMapping("list")
	public String getList(HttpServletRequest request, Model model) {
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
		List<OrderInfoDTO> orderList = orderInfoServiceImpl.findForPage(params, paging);
		model.addAttribute("orderList", orderList);
		model.addAttribute("params", params);
		model.addAttribute("appid", appid);
		model.addAttribute("paging", paging);
		return prefix + "/list";
	}

	/**
	 * 跳转页面
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
		return "/mobile/order/list";
	}

	/**
	 * 异步获取数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("mOrderData")
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
		List<OrderInfoDTO> AgentAnalysis = orderInfoServiceImpl.findForPage(params, paging);
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
		List<OrderInfoDTO> AgentAnalysis = new ArrayList<OrderInfoDTO>();
		try {
			appid = Integer.parseInt(request.getParameter("appid"));
			guid = Integer.parseInt(request.getParameter("queryUID"));
			superGuid = Integer.parseInt(request.getParameter("superGuid"));
			AgentAnalysis = orderInfoServiceImpl.getOrdersByGuidAndSuper(appid, guid, superGuid);
			jsonObject.put("code", 1);
		} catch (Exception e) {
			jsonObject.put("code", 0);
		}
		jsonObject.put("dataList", AgentAnalysis);
		writer.write(jsonObject.toString());
		writer.close();
		return;

	}

	/**
	 * 订单同步
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("synOrder")
	public String synOrderInfo(HttpServletRequest req, HttpServletResponse response,Model model) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Writer writer = response.getWriter();
		Map<String, String> params = Utils.getParameterMap(request);
		JSONObject jsonObject = orderInfoServiceImpl.insertSynOrderInfo(params);
		writer.write(jsonObject.toString());
		writer.close();
		return;
		return  "/office/list";
	}
}
