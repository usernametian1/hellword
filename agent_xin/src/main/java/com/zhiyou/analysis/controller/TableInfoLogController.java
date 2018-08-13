package com.zhiyou.analysis.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.analysis.service.ITableInfoLogService;
import com.zhiyou.utils.Utils;

@Controller
@RequestMapping("/analysis/agent")
public class TableInfoLogController {
	private static final String prefix = "/order/income/";
	private static final Logger log = Logger.getLogger(TableInfoLogController.class);
	@Autowired
	private ITableInfoLogService tableInfoLogService;

	@RequestMapping(value = "/synTableInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void synDailyInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> parameters = Utils.getParameterMap(request);
		JSONObject jsonObject = tableInfoLogService.insertTableInfoLog(parameters);
		Writer writer = response.getWriter();
		writer.write(jsonObject.toString());
		writer.close();
		return;
	}
}
