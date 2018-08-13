package com.zhiyou.funcconfig.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.util.WebUtils;

import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.funcconfig.dto.FuncConfigDto;
import com.zhiyou.funcconfig.service.IFuncConfigService;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/funcConfig")
public class FuncConfigController {

	public static final String prefix = "app/funcconfig";
	private static final Logger log = Logger.getLogger(FuncConfigController.class);

	@Autowired
	private IFuncConfigService funcConfigService;

	/**
	 * 展示游戏接口表的信息
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String dolistFuncConfig(HttpServletRequest req, HttpServletResponse res, Model model) {
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
		List<FuncConfigDto> funcConfigList = new ArrayList<FuncConfigDto>();
		funcConfigList = funcConfigService.findForPage(params, paging);
		model.addAttribute("funcConfigList", funcConfigList);
		model.addAttribute("paging", paging);
		model.addAttribute("appid", appid);
		model.addAttribute("params", params);
		return prefix + "/list";
	}

	/**
	 * 编辑游戏接口表的信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/edit/{appid}/{funcName}", method = { RequestMethod.POST, RequestMethod.GET })
	public String doeditFuncConfig(@PathVariable("appid") int appid, @PathVariable("funcName") String funcName, Model model) throws UnsupportedEncodingException {
		String funcName1 = new String(funcName.getBytes("iso8859-1"), "utf-8");
		log.info(funcName1);
		FuncConfigDto funcConfigDto = funcConfigService.selectFuncConfig(appid, funcName1);
		model.addAttribute("funcConfigDto", funcConfigDto);
		return prefix + "/edit";
	}

	/**
	 * 新增游戏接口表的信息
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(HttpServletRequest req, HttpServletResponse res, Model model) {
		return prefix + "/add";
	}

	/**
	 * 新增游戏接口表的信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/insert", method = { RequestMethod.POST, RequestMethod.GET })
	public void doinsertFuncConfig(FuncConfigDto funcConfigDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		try {
			funcConfigService.insertFuncConfig(funcConfigDto);
			res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增接口失败:{}", e);
			res.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	/**
	 * 修改游戏接口表的信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public void doupateFuncConfig(FuncConfigDto funcConfigDto, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=utf-8");
		try {
			funcConfigService.updateFuncConfig(funcConfigDto);
			res.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增接口失败:{}", e);
			res.getWriter().write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	/**
	 * 删除游戏接口
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete/{appid}/{funcName}", method = { RequestMethod.POST, RequestMethod.GET })
	public String dodeleteFuncConfig(@PathVariable("appid") int appid, @PathVariable("funcName") String funcName, HttpServletRequest req) throws UnsupportedEncodingException {
		String funcName1 = new String(funcName.getBytes("iso8859-1"), "utf-8");
		log.info(funcName1);
		funcConfigService.deleteFuncConfig(appid, funcName1);
		return ViewUrlUtils.doGetRedirectUrl("/funcConfig", "/list");
	}
}
