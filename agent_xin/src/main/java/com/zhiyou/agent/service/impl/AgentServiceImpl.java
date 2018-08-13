package com.zhiyou.agent.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dao.AgentDao;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.app.dao.AppInfoDao;
import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.common.constance.AgentConstance;
import com.zhiyou.common.constance.AuthUserConstance;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.funcconfig.dao.FuncConfigDao;
import com.zhiyou.funcconfig.dto.FuncConfigDto;
import com.zhiyou.user.dto.UserInfoDto;
import com.zhiyou.user.service.IUserInfoService;
import com.zhiyou.utils.HttpUtils;
import com.zhiyou.utils.SignVerify;
import com.zhiyou.utils.StringUtils;

@Service("agentService")
public class AgentServiceImpl implements IAgentService {

	private static final Logger log = Logger.getLogger(AgentServiceImpl.class);

	@Autowired
	private FuncConfigDao funcConfigDao;
	@Autowired
	private AppInfoDao appInfoDao;
	@Autowired
	private AgentDao agentDao;
	@Autowired
	private IAuthUserService authUserService;
	@Autowired
	private IUserInfoService userInfoService;

	@Override
	public boolean insertAgent(AgentDto e) {

		return agentDao.insert(e);
	}

	@Override
	public boolean deleteAgent(int guid, int appid) {

		return agentDao.delete(guid, appid);
	}

	@Override
	public int updateByCondition(Map<String, Object> params) {
		return agentDao.updateByCondition(params);
	}

	@Override
	public int updateAgent(AgentDto agentDto) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", agentDto.getAppid());
		params.put("guid", agentDto.getGuid());
		params.put("status", agentDto.getStatus());
		return agentDao.updateByCondition(params);
	}

	@Override
	public List<AgentDto> findForPage(Map<String, Object> params, PagingDto paging) {
		int totalRows = agentDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return agentDao.findForPage(params);
	}

	@Override
	public void doAudit(AgentDto agentDto) {
		AgentDto queryAgent = agentDao.getAgent(agentDto.getAppid(), agentDto.getGuid());
		if (queryAgent.getStatus() != AgentConstance.APPLY_PASS_STATUS) {
			queryAgent.setRemark(agentDto.getRemark());
			queryAgent.setStatus(AgentConstance.APPLY_PASS_STATUS);
			queryAgent.setName(agentDto.getName());
			queryAgent.setPhone(agentDto.getPhone());
			queryAgent.setCheckTime(new Date());
			queryAgent.setAgentLevel(agentDto.getAgentLevel());
			agentDao.update(queryAgent);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", agentDto.getPhone());
			params.put("appid", agentDto.getAppid());
			params.put("guid", agentDto.getGuid());
			params.put("status", AuthUserConstance.EFFECTIVE);
			authUserService.updateByCondition(params);
			int syn = queryAgent.getSyn();
			if (syn == 0) {
				// 0需要同步,1不需要同步
				this.synAgent(queryAgent.getAppid(), queryAgent.getGuid());
			}

		}
	}

	/**
	 * 同步代理信息的 接口
	 */
	public void synAgent(int appid, int guid) {
		AgentDto agent = agentDao.getAgent(appid, guid);
		int status = agent.getStatus();
		String name = agent.getName();
		String phone = agent.getPhone();
		AppInfoDto appinfo = appInfoDao.getAppId(appid);
		FuncConfigDto func = funcConfigDao.getSynUrl(appid, "synAgent");
		String url = func.getNotfiyUrl();
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", String.valueOf(appid));
		params.put("guid", String.valueOf(guid));
		params.put("status", String.valueOf(status));
		params.put("phone", phone);
		params.put("name", name);
		params.put("sign", SignVerify.getSign(params, appinfo.getAppkey()));
		try {
			String returnValue = HttpUtils.post(url, null, params);
			log.info("dddd" + returnValue);
			if ("ok".equalsIgnoreCase(returnValue)) {
				agent.setSyn(1);
				agentDao.update(agent);
			} else {
				log.error("appid:" + appid + ";guid:" + guid + ";同步结果:" + returnValue);
			}
		} catch (Exception e) {
			log.error("====同步异常===");
		}
	}

	public AgentDto getAgent(int appid, int guid) {
		return agentDao.getAgent(appid, guid);
	}

	@Override
	public JSONObject applyAgent(HttpServletRequest req) {
		JSONObject jsonObject = new JSONObject();

		String appidStr = req.getParameter("appid");
		String guidStr = req.getParameter("guid");
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String password1 = req.getParameter("password1");
		String phone = req.getParameter("phone");
		String address = req.getParameter("adress");
		String qq = req.getParameter("qq");
		String openid = req.getParameter("openid");

		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(password1)) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "密码不能为空");
			return jsonObject;
		}

		if (!password.equals(password1)) {
			jsonObject.put("code", 2);
			jsonObject.put("msg", "密码不一致");
			return jsonObject;
		}

		int appid = 0;
		try {
			appid = Integer.parseInt(appidStr);
		} catch (Exception e) {
			jsonObject.put("code", 3);
			jsonObject.put("msg", "参数错误");
			return jsonObject;
		}
		int guid = 0;
		try {
			guid = Integer.parseInt(guidStr);
		} catch (Exception e) {
			jsonObject.put("code", 4);
			jsonObject.put("msg", "UID错误");
			return jsonObject;
		}

		// 判断用户是否存在
		UserInfoDto userInfo = userInfoService.getUserInfo(guid, appid);
		if (userInfo == null) {
			jsonObject.put("code", 5);
			jsonObject.put("msg", "游戏用户不存在");
			return jsonObject;
		}

		// 判断是否已经是代理
		AgentDto agent = agentDao.getAgent(appid, guid);
		if (agent != null) {
			if (agent.getStatus() == AgentConstance.APPLY_PASS_STATUS) {
				jsonObject.put("code", 6);
				jsonObject.put("msg", "已经是代理，请登录代理系统");
				return jsonObject;
			} else if (agent.getStatus() == AgentConstance.SUBMIT_STATUS) {
				jsonObject.put("code", 7);
				jsonObject.put("msg", "已经提交申请，请耐心等待");
				return jsonObject;
			} else if (agent.getStatus() == AgentConstance.REFUSE_STATUS) {
				jsonObject.put("code", 8);
				jsonObject.put("msg", " 申请被拒绝，请联系管理员");
				return jsonObject;
			}
		}
		agent = new AgentDto();
		agent.setAppid(appid);
		agent.setGuid(guid);
		agent.setName(name);
		agent.setPhone(phone);
		agent.setStatus(AgentConstance.SUBMIT_STATUS);
		agent.setApplyTime(new Date());
		agent.setSyn(AgentConstance.NOT_SYN);
		agent.setState(AgentConstance.NORMAL_STATE);
		agent.setOpenid(openid);
		agentDao.insert(agent);
		AuthUser authUser = new AuthUser();
		authUser.setAppid(appid);
		authUser.setUsername(guid + "@@" + appid);
		authUser.setPassword(password);
		authUser.setRealname(name);
		authUser.setGuid(String.valueOf(guid));
		authUser.setAddress(address);
		authUser.setMobile(phone);
		authUser.setQq(qq);
		authUser.setRegTime(new Date());
		authUser.setStatus(AuthUserConstance.FORBIDDEN);
		authUserService.doInsertAuthUser(authUser);
		jsonObject.put("code", 200);
		jsonObject.put("msg", "代理申请成功");
		return jsonObject;
	}

	
	public static void main(String[] args) {
//	  System.out.println("abc="+a+b+c);	
	  System.out.print("输入");
	  Scanner scan = new Scanner(System.in);
	  String read = scan.nextLine();
      char[] charArr = read.toCharArray();
      char r = charArr[0];
	  switch (r) {
	      case 3 :
		     System.out.println("xiaji");
		  break;
	      case 4:
	    	  System.out.println("二");
	       break;
	      case 5:
	    	  System.out.println("三");
	       break;
	      case 6:
	    	  System.out.println("四");
	       break;
	      case 7:
	    	  System.out.println("A");
	       break;
	      case 'b':
	    	  System.out.println("B");
	       break;
	      case 'c':
	    	  System.out.println("C");
	       break;
	      default:
	    	  System.out.println("OTHER");
		  break;
	}
	}	
	
	
	
}