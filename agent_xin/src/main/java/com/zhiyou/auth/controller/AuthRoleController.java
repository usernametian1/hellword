package com.zhiyou.auth.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.auth.service.AuthMenuService;
import com.zhiyou.auth.service.IAuthRoleService;
import com.zhiyou.common.TreeList;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Utils;
import com.zhiyou.utils.ViewUrlUtils;

@Controller
@RequestMapping("/auth/role")
public class AuthRoleController {
	private static final String PREFIX = "auth/role/"; // 返回对应的jsp前缀

	private static final Logger log = Logger.getLogger(AuthRoleController.class);

	@Autowired
	private IAuthRoleService authRoleService;
	@Autowired
	private AuthMenuService authMenuService;

	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String doGetRoleList(HttpServletRequest request, Model model) {
		Map<String, Object> params = Utils.getParameters(request);
		PagingDto paging = new PagingDto(request.getParameter("curPageNum"));
		List<AuthRole> roleList = authRoleService.doGetAuthRoleList(params, paging);
		model.addAttribute("roleList", roleList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return PREFIX + "list";
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public String doAddAuthRoleDto() {
		return PREFIX + "add";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public void doInsertAuthRoleDto(AuthRole authRoleDto, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Writer writer = response.getWriter();
		try {
			authRoleService.doInsertAuthRoleDto(authRoleDto);
			writer.write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("新增角色异常:{}", e);
			writer.write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	@RequestMapping(value = "/edit/{roleId}", method = { RequestMethod.POST, RequestMethod.GET })
	public String doEditAuthRoleDto(@PathVariable("roleId") int roleId, Model model) {
		AuthRole authRoleDto = authRoleService.doGetAuthRoleDtoById(roleId);
		model.addAttribute("role", authRoleDto);
		return PREFIX + "edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void doUpdateAuthRoleDto(AuthRole authRole, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Writer writer = response.getWriter();
		try {
			authRoleService.doUpdateAuthRoleDto(authRole);
			log.info("更新角色成功");
			writer.write("<h3 align=\"center\">操作成功!</h3>");
		} catch (Exception e) {
			log.error("更新角色异常:{}", e);
			writer.write("<h3 align=\"center\">操作失败!</h3>");
		}
	}

	@RequestMapping(value = "/delete/{roleId}", method = { RequestMethod.POST, RequestMethod.GET })
	public String doDeleteAuthRoleDto(@PathVariable("roleId") int roleId) {
		try {
			authRoleService.doDeleteAuthRoleDto(roleId);
			log.info("删除角色成功");
		} catch (Exception e) {
			log.error("删除角色异常:{}", e);
		}
		return ViewUrlUtils.doGetRedirectUrl(PREFIX, "list");
	}

	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	@ResponseBody
	public List<AuthRole> doRoleList() {
		List<AuthRole> roleList = authRoleService.doGetRoleList();
		return roleList;
	}

	@RequestMapping("roleAndResource")
	public String roleAndResource(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			List<AuthRole> roleList = authRoleService.doGetRoleList();
			List<AuthMenuDto> menuList = authMenuService.doGetMenuList(0);
			TreeList treeList = new TreeList(menuList);
			List<AuthMenuDto> buildTree = treeList.buildTree();
			model.addAttribute("roleList", roleList);
			model.addAttribute("menuList", buildTree);
		} catch (Exception e) {
			log.error("角色授权异常:{}", e);
		}
		return PREFIX + "roleAndResource";
	}

	@RequestMapping("getAjaxRoleRe/{roleID}")
	@ResponseBody
	public void getAjaxRoleRe(@PathVariable("roleID") int roleID, Writer writer, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		List<Integer> resourceIDs = authMenuService.getResourceIDByRoleID(roleID);
		jsonObject.put("IDList", resourceIDs);
		writer.write(jsonObject.toString());
		writer.close();
		return;
	}

	@RequestMapping("doRoleAndResource")
	public String method(@RequestParam(name = "roleid", required = true) int roleid, @RequestParam(name = "resourceIds", required = true) int[] resourceIds, HttpServletResponse response, Model model) {
		try {// 先删除后更新插入
			authRoleService.deleteRoleResourceByRoleid(roleid);
			authRoleService.insertRoleResource(roleid, resourceIds);
		} catch (Exception e) {
		}
		return ViewUrlUtils.doGetRedirectUrl(PREFIX, "roleAndResource");
	}
}
