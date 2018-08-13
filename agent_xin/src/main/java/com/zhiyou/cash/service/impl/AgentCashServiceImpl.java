package com.zhiyou.cash.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.auth.service.IAuthUserService;
import com.zhiyou.cash.dao.AgentCashDao;
import com.zhiyou.cash.dto.ApplyCashDto;
import com.zhiyou.cash.service.IAgentCashService;
import com.zhiyou.common.constance.AgentConstance;
import com.zhiyou.common.enums.RetSatusAndMsg;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.Constants;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.IPUtils;
import com.zhiyou.utils.RandStrUtils;
import com.zhiyou.utils.SendMsg_webchinese;
import com.zhiyou.weixin.MchPay;
import com.zhiyou.weixin.MchPayResp;

@Service("agentCashServiceImpl")
public class AgentCashServiceImpl implements IAgentCashService {
	private static final Logger log = Logger.getLogger(AgentCashServiceImpl.class);
	@Autowired
	AgentCashDao agentCashDao;
	@Autowired
	IAuthUserService authUserService;
	@Autowired
	private IAgentService agentService;

	@Override
	public List<ApplyCashDto> getListCashApplyDto(Map<String, Object> params, PagingDto paging) {
		int totalRows = agentCashDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return agentCashDao.findForPage(params);
	}

	@Override
	public JSONObject insertCashApply(HttpServletRequest request) {
		String session_code = (String) WebUtils.getSessionAttribute(request, Constants.SESSION_CODE);
		request.getSession().removeAttribute(Constants.SESSION_CODE);
		JSONObject result = new JSONObject();
		int appid = 0;
		int guid = 0;
		int totalFee = 0;
		String checkCode = "";
		try {
			appid = Integer.valueOf(request.getParameter("appid"));
			guid = Integer.valueOf(request.getParameter("guid"));
			totalFee = Integer.valueOf(request.getParameter("totalFee")) * 100;
			checkCode = request.getParameter("checkCode");
		} catch (Exception e) {
			result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
			result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
			return result;
		}
		AuthUser authUser = authUserService.getAuthUser(appid, request.getParameter("guid"));
		// 验证输入的密码
		if (null == authUser || !checkCode.equalsIgnoreCase(session_code)) {
			request.getSession().removeAttribute(Constants.SESSION_CODE);
			result.put("code", RetSatusAndMsg.INPUT_CHECK_CODE__ERROR.getValue());
			result.put("msg", RetSatusAndMsg.INPUT_CHECK_CODE__ERROR.getName());
			return result;
		}
		// 提现金额和余额校验
		AgentDto agent = agentService.getAgent(appid, guid);
		if (null == agent || agent.getOpenid() == null || "".equals(agent.getOpenid())) {
			result.put("code", RetSatusAndMsg.CASH_NOT_FOUND_OPENID.getValue());
			result.put("msg", RetSatusAndMsg.CASH_NOT_FOUND_OPENID.getName());
			return result;
		}
		if (totalFee <= 0 || agent.getBalance() < totalFee) {
			result.put("code", RetSatusAndMsg.CASH_TOTALFEE_ERROR.getValue());
			result.put("msg", RetSatusAndMsg.CASH_TOTALFEE_ERROR.getName());
			return result;
		}
		// 账户状态
		if (agent.getState() == AgentConstance.FROZEN_STATE) {
			result.put("code", RetSatusAndMsg.CASH_FREEZON.getValue());
			result.put("msg", RetSatusAndMsg.CASH_FREEZON.getName());
			return result;
		}
		String orderId = DateUtils.format(new Date(), "yyyyMMddHHmmss") + guid + RandStrUtils.randNumeric(5);
		ApplyCashDto applyCashDto = new ApplyCashDto();
		applyCashDto.setOrderId(orderId);
		applyCashDto.setAppid(appid);
		applyCashDto.setGuid(guid);
		applyCashDto.setTotalFee(totalFee);
		applyCashDto.setApplyTime(new Date());
		applyCashDto.setState(AgentConstance.CASH_APPLY_SUBMIT);// 设为申请状态
		int ret = agentCashDao.insertCashApply(applyCashDto);
		if (ret > 0) {// 插入记录成功,减余额
			Map<String, Object> updateParams = new HashMap<String, Object>();
			updateParams.put("appid", appid);
			updateParams.put("guid", guid);
			updateParams.put("balance", -totalFee);
			int back = agentService.updateByCondition(updateParams);
			if (back < 1) {
				result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
				return result;
			}
		} else {
			result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
			result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
			return result;
		}
		result.put("code", RetSatusAndMsg.CASH_APPLY_SUCCESS.getValue());
		result.put("msg", RetSatusAndMsg.CASH_APPLY_SUCCESS.getName());
		return result;

	}

	@Override
	public JSONObject updateCashApplyState(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		int state = AgentConstance.CASH_APPLY_SUBMIT;
		String orderId = "";
		String remark = "";
		try {
			orderId = request.getParameter("orderId");
			remark = request.getParameter("remark");
			state = Integer.valueOf(request.getParameter("state"));
		} catch (Exception e) {
			result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
			result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
			return result;
		}
		ApplyCashDto applyCashDto = agentCashDao.getApplyCashDtoByOrderID(orderId);
		if (null == applyCashDto || applyCashDto.getState() != AgentConstance.CASH_APPLY_SUBMIT) {
			result.put("code", RetSatusAndMsg.CASH_APPLY_ORDER_INVALID.getValue());
			result.put("msg", RetSatusAndMsg.CASH_APPLY_ORDER_INVALID.getName());
			return result;
		}

		
		// 被驳回
		if (state == AgentConstance.CASH_APPLY_REFUSE) {
			applyCashDto.setState(AgentConstance.CASH_APPLY_REFUSE);
			applyCashDto.setRemark(remark);
			applyCashDto.setUpdateTime(new Date());
			try {
				int ret = agentCashDao.updateCashApplyState(applyCashDto);
				if (ret > 0) {
					Map<String, Object> updateParams = new HashMap<String, Object>();
					updateParams.put("appid", applyCashDto.getAppid());
					updateParams.put("guid", applyCashDto.getGuid());
					updateParams.put("balance", applyCashDto.getTotalFee());
					agentService.updateByCondition(updateParams);
					result.put("code", RetSatusAndMsg.CASH_APPLY_CHECK_PASS.getValue());
					result.put("msg", RetSatusAndMsg.CASH_APPLY_CHECK_PASS.getName());
				} else {
					result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
					result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
				}
				return result;
			} catch (Exception e) {
				log.error("updateCashApplyState驳回更新异常:{}", e);
				result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
			}
			return result;
		}
		// 通过
		if (state == AgentConstance.CASH_APPLY_PASS) {
			AgentDto agentDto = agentService.getAgent(applyCashDto.getAppid(), applyCashDto.getGuid());
			try {
				MchPayResp resp = MchPay.getInstance().pay(orderId, agentDto.getOpenid(), agentDto.getName(), applyCashDto.getTotalFee(), remark, IPUtils.getIpAddress(request));
				if (resp.isOk()) {
					applyCashDto.setState(AgentConstance.CASH_APPLY_PASS);
					applyCashDto.setRemark(remark);
				} else {
					applyCashDto.setState(AgentConstance.CASH_APPLY_SUBMIT);
					applyCashDto.setRemark(resp.getErr_code()+";"+resp.getErr_code_des());
				}
				applyCashDto.setUpdateTime(new Date());
				int ret = agentCashDao.updateCashApplyState(applyCashDto);
				if (ret > 0) {
					result.put("code", RetSatusAndMsg.CASH_APPLY_CHECK_PASS.getValue());
					result.put("msg", RetSatusAndMsg.CASH_APPLY_CHECK_PASS.getName());
				} else {
					result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
					result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
				}
				return result;
			} catch (Exception e) {
				log.error("updateCashApplyState调用微信相关异常:{}", e);
				result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
				return result;
			}
		}
		return result;
	}

	@Override
	public ApplyCashDto getApplyCashDtoByOrderID(String orderId) {
		return agentCashDao.getApplyCashDtoByOrderID(orderId);
	}

	@Override
	public JSONObject sendCheckCode(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		int userId = 0;
		String mobile = "";
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
			mobile = request.getParameter("mobile");
		} catch (Exception e) {
			jsonObject.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
			jsonObject.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
			return jsonObject;
		}
		AuthUser auther = authUserService.doGetAuthUserByUserID(userId);
		if (!mobile.equalsIgnoreCase(auther.getMobile())) {
			jsonObject.put("code", RetSatusAndMsg.CASH_APPLY_MOBILE_ERROR.getValue());
			jsonObject.put("msg", RetSatusAndMsg.CASH_APPLY_MOBILE_ERROR.getName());
			return jsonObject;
		}
		String code = RandStrUtils.randNumeric(6);
		WebUtils.setSessionAttribute(request, Constants.SESSION_CODE, code);
		SendMsg_webchinese webchinese = new SendMsg_webchinese();
		webchinese.MobileVerify(mobile, code);
		jsonObject.put("code", RetSatusAndMsg.CHECK_CODE_SEND_SUCCESS.getValue());
		jsonObject.put("msg", RetSatusAndMsg.CHECK_CODE_SEND_SUCCESS.getName());
		return jsonObject;
	}

	@Override
	public List<ApplyCashDto> getAgentApplyRecords(AuthUser sessionUser) {
		return agentCashDao.getCashApplyDto(sessionUser.getAppid(), Integer.parseInt(sessionUser.getGuid()));
	}
}
