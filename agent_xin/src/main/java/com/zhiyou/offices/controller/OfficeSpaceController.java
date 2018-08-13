package com.zhiyou.offices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.offices.bean.OfficeSpaceDto;
import com.zhiyou.offices.service.OfficeService;
import com.zhiyou.utils.Utils;


@Controller
@RequestMapping("/offices")
public class OfficeSpaceController{
	
	private static final String prefix = "office";
	
	@Autowired
	private OfficeService officeService;
     
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public String findForPage(HttpServletRequest req , Model model) {
		Map<String, Object> params = Utils.getParameters(req);
		PagingDto paging = new PagingDto(req.getParameter("curPageNum"));
		List<OfficeSpaceDto> officeList = new ArrayList<OfficeSpaceDto>();
		officeList = officeService.FindOfficeSpace(params, paging);
		model.addAttribute("officeList", officeList);
		model.addAttribute("paging", paging);
		model.addAttribute("params", params);
		return  prefix +"/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = { RequestMethod.POST, RequestMethod.GET })
  	public JSONObject insertOffice(HttpServletRequest req , Model model) {
       JSONObject json = new JSONObject();
		Map<String , Object> params = Utils.getParameters(req);
        boolean flag = officeService.insertOffice(params);
        if(flag) {
        	json.put("code", "200");
        	return json;
        }else {
        	json.put("code", "201");
        	return json;
        }
	}
	
	
}
