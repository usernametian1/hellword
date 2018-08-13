package com.zhiyou.test.controller;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthMenuDto;
import com.zhiyou.auth.dto.AuthRole;
import com.zhiyou.auth.service.AuthMenuService;
import com.zhiyou.auth.service.IAuthRoleService;
import com.zhiyou.test.entity.Test;
import com.zhiyou.test.service.ITestService;
import com.zhiyou.utils.Utils;

@Controller
public class TestController {

	@Autowired
	ITestService testService;
	@Autowired
	private IAuthRoleService authRoleService;
	@Autowired
	private AuthMenuService authMenuService;

	@RequestMapping(value = "/test/synAgent", method = RequestMethod.POST)
	public void synAgent(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		System.out.println("---------");
		response.setContentType("text/html; charset=utf-8");
		Map<String, String> parameters = Utils.getParameterMap(request);
		Set<String> keySet = parameters.keySet();
		for (String element : keySet) {
			System.out.println(parameters.get(element));
		}
		response.getWriter().write("ok");
		return;
	}

	@RequestMapping("/test/shiro")
	public void method(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AuthRole> roles = authRoleService.getRolesByUserID(197);
		System.out.println(roles.size());
		List<Integer> list = new ArrayList<Integer>();
		for (AuthRole role : roles) {
			list.add(role.getRoleId());
		}
		List<AuthMenuDto> resourcesByRoleIDs = authMenuService.getResourcesByRoleIDs(list);
		System.out.println(resourcesByRoleIDs.size());
		return;
	}

	@RequestMapping("/test/get")
	public void get(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html; charset=utf-8");
		Test test = testService.findByID(2);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("test", test);
		Writer writer = res.getWriter();
		writer.write(jsonObject.toString());
		writer.close();
		return;
	}

	@RequestMapping("/test/save")
	public void save(Test test) throws IOException {
		System.out.println(test);
		testService.insertTest(test);
		return;
	}
	
	
	
	public int  sum(int a , int b) {
		System.out.println(a+b);
		return a+b;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
           
		//通过文件来反射 这个类 
	  Class<TestController> test = (Class<TestController>)Class.forName("com.zhiyou.test.controller.TestController");
	    //通过 test。getConstructor 反射出构造函数 
	  Constructor<TestController> ton = test.getConstructor();
	     //对构造函数 进行实列化
	  TestController ci  = ton.newInstance();
	  //反射出 java中的 方法
	   Method me = test.getMethod("sum", int.class,int.class);
	   //执行
	   me.invoke(ci, 2,2);
	}



}
