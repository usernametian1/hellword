package com.zhiyou.auth.controller;

import java.io.IOException;
import java.util.List;

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

import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.auth.service.AuthMenuService;
import com.zhiyou.common.TreeList;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping(value = "/auth/resources")
public class AuthMenuController {
	public static final String prefix = "/auth/resources";
	static final Logger log = Logger.getLogger(AuthMenuController.class);
	@Autowired
	private AuthMenuService authMenuService;

	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String doGetMenuList(HttpServletRequest request, Model model) {
		List<AuthMenuDto> menuList = authMenuService.doGetMenuList(0);
		TreeList treeList = new TreeList(menuList);
		List<AuthMenuDto> buildTree = treeList.buildTree();
		model.addAttribute("treeList", buildTree);
		return prefix + "/list";
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String doAddAuthMenuDto(HttpServletRequest request, Model model) {
		int resourceId = 0;
		String resourceIdstr = request.getParameter("resourceId");
		if (null != resourceIdstr && !"".equals(resourceIdstr)) {
			resourceId = Integer.parseInt(resourceIdstr);
		}
		List<AuthMenuDto> menuList = authMenuService.doGetMenuList(0);
		model.addAttribute("menuList", menuList);
		model.addAttribute("resourceId", resourceId);
		return prefix + "/add";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void doInsertAuthMenuDto(HttpServletRequest request, AuthMenuDto authMenuDto, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			authMenuService.doInsertAuthMenuDto(authMenuDto);
			request.getSession().removeAttribute(Constants.SESSION_MENU);
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增菜单异常:{}", e);
			response.getWriter().write("<h3 align=\"center\">操作异常!</h3>");
		}
		// return prefix + "/add";
	}

	@RequestMapping(value = "/edit/{resourceId}", method = { RequestMethod.POST, RequestMethod.GET })
	public String doEditAuthMenuDto(@PathVariable("resourceId") int resourceId, Model model) {
		AuthMenuDto authMenuDto = authMenuService.doGetAuthMenuDtoById(resourceId);
		List<AuthMenuDto> menuList = authMenuService.doGetMenuList(0);
		model.addAttribute("authMenuDto", authMenuDto);
		model.addAttribute("menuList", menuList);
		return prefix + "/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void doUpdateAuthMenuDto(HttpServletRequest request, HttpServletResponse response, AuthMenuDto authMenuDto) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {

			authMenuService.doUpdateAuthMenuDto(authMenuDto);
			request.getSession().removeAttribute(Constants.SESSION_MENU);
			response.getWriter().write("<h3 align=\"center\">操作成功!</h3>");
			log.info("更新菜单成功");
			// return ViewUrlUtils.doGetRedirectUrl(prefix, "/list");
		} catch (Exception e) {
			log.error("更新菜单失败:{}", e);
			response.getWriter().write("<h3 align=\"center\">操作异常!</h3>");
			// return ViewUrlUtils.doGetRedirectUrl(prefix, "/edit/" +
			// authMenuDto.getResourceId());
		}

	}

	@RequestMapping(value = "/delete/{resourceId}", method = { RequestMethod.POST, RequestMethod.GET })
	public String doDeleteAuthMenuDto(@PathVariable("resourceId") int resourceId, HttpServletRequest request) {
		try {
			authMenuService.doDeleteAuthMenuDto(resourceId);
			request.getSession().removeAttribute(Constants.SESSION_MENU);
			log.info("删除菜单成功");
		} catch (Exception e) {
			log.error("删除菜单失败:{}", e);
		}
		return ViewUrlUtils.doGetRedirectUrl(prefix, "/list");
	}
}
