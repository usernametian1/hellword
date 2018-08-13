package com.zhiyou.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.dto.AgentRate;
import com.zhiyou.agent.service.IAgentRateService;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.common.constance.AgentConstance;
import com.zhiyou.common.enums.RetSatusAndMsg;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.dao.OrderInfoDao;
import com.zhiyou.order.dto.IncomeLog;
import com.zhiyou.order.dto.OrderInfoDTO;
import com.zhiyou.order.service.IOrderInfoService;
import com.zhiyou.order.service.IncomeLogService;
import com.zhiyou.user.dto.UserInfoDto;
import com.zhiyou.user.service.IUserInfoService;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.SignVerify;
import com.zhiyou.utils.StringUtils;

@Service("orderInfoServiceImpl")
public class OrderInfoServiceImpl implements IOrderInfoService {

	private static final Logger log = Logger.getLogger(OrderInfoServiceImpl.class);
	@Autowired
	private OrderInfoDao orderInfoDao;
	@Autowired
	private IAppInfoService appInfoService;
	@Autowired
	private IAgentService agentService;
	@Autowired
	private IncomeLogService incomeLogService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAgentRateService agentRateService;

	@Override
	public OrderInfoDTO getOrderId(int appid, String orderId) {
		return orderInfoDao.getByOrderId(appid, orderId);
	}

	@Override
	public int insert(OrderInfoDTO e) {
		return orderInfoDao.insert(e);
	}

	@Override
	public List<OrderInfoDTO> findForPage(Map<String, Object> params, PagingDto paging) {
		int totalRows = orderInfoDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return orderInfoDao.findForPage(params);
	}

	@Override
	public List<OrderInfoDTO> findTotal(Map<String, Object> params, PagingDto paging) {
		int totalRows = orderInfoDao.findTotalCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return orderInfoDao.findTotal(params);
	}

	/***
	 * 更新一级代理信息
	 * 
	 * @param orderInfo
	 * @return
	 */
	private AgentDto updateAgent1(OrderInfoDTO orderInfo) {
		UserInfoDto userInfo = userInfoService.getUserInfo(orderInfo.getGuid(), orderInfo.getAppid());
		if (userInfo == null) {
			log.warn("未同步用户数据"+orderInfo.getAppid()+"="+orderInfo.getGuid());
		}
		AgentDto superAgent1 = agentService.getAgent(orderInfo.getAppid(), userInfo.getSuperGuid());
		if (null != superAgent1 && null != userInfo) {// 直属上级代理
			AgentRate agentRate1 = agentRateService.get(superAgent1.getGuid(), superAgent1.getAppid());
			HashMap<String, Object> map = new HashMap<String, Object>();
			IncomeLog incomeLog = new IncomeLog();
			incomeLog.setTotalFee(orderInfo.getTotalFee());
			incomeLog.setAppid(orderInfo.getAppid());
			incomeLog.setGuid(orderInfo.getGuid());
			incomeLog.setOrderid(orderInfo.getOrderId());
			incomeLog.setUserLevel(1);
			incomeLog.setSuperGuid(userInfo.getSuperGuid());
			incomeLog.setIncome(calculateIncome(orderInfo.getTotalFee(), agentRate1.getRate1()));// 设置收入
			incomeLog.setCreateTime(new Date());
			if (superAgent1.getState() == AgentConstance.NORMAL_STATE) {// 代理处于正常状态
				incomeLog.setState(AgentConstance.NORMAL_STATE);
				map.put("income1", incomeLog.getIncome());// 加作为上级代理收入
				map.put("balance", incomeLog.getIncome());// 加余额

			} else {// 冻结状态
				incomeLog.setState(AgentConstance.FROZEN_STATE);
				map.put("freeze", incomeLog.getIncome());// 加在冻结余额
			}
			map.put("guid", superAgent1.getGuid());
			map.put("appid", superAgent1.getAppid());
			incomeLogService.insert(incomeLog);
			agentService.updateByCondition(map);
		}
		return superAgent1;
	}

	/***
	 * 更新二级代理信息
	 * 
	 * @param orderInfo
	 * @return
	 */
	private AgentDto updateAgent2(OrderInfoDTO orderInfo, AgentDto superAgent1) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		UserInfoDto userInfo1 = userInfoService.getUserInfo(superAgent1.getGuid(), superAgent1.getAppid());
		AgentDto superAgent2 = agentService.getAgent(userInfo1.getAppid(), userInfo1.getSuperGuid());
		if (null != superAgent2 && null != userInfo1) {
			AgentRate agentRate2 = agentRateService.get(superAgent2.getGuid(), superAgent2.getAppid());
			IncomeLog incomeLog = new IncomeLog();
			incomeLog.setTotalFee(orderInfo.getTotalFee());
			incomeLog.setAppid(orderInfo.getAppid());
			incomeLog.setGuid(orderInfo.getGuid());
			incomeLog.setOrderid(orderInfo.getOrderId());
			incomeLog.setUserLevel(2);
			incomeLog.setSuperGuid(userInfo1.getSuperGuid());
			incomeLog.setIncome(calculateIncome(orderInfo.getTotalFee(), agentRate2.getRate2()));// 设置收入
			incomeLog.setCreateTime(new Date());
			if (superAgent2.getState() == AgentConstance.NORMAL_STATE) {// 代理处于正常状态
				incomeLog.setState(AgentConstance.NORMAL_STATE);
				map.put("income2", incomeLog.getIncome());// 加作为上级代理收入
				map.put("balance", incomeLog.getIncome());// 加余额
			} else {// 冻结状态
				incomeLog.setState(AgentConstance.FROZEN_STATE);
				map.put("freeze", incomeLog.getIncome());// 加在冻结余额
			}
			map.put("guid", superAgent2.getGuid());
			map.put("appid", superAgent2.getAppid());
			incomeLogService.insert(incomeLog);
			agentService.updateByCondition(map);
		}
		return superAgent2;
	}

	/***
	 * 更新三级代理信息
	 * 
	 * @param orderInfo
	 * @return
	 */
	private AgentDto updateAgent3(OrderInfoDTO orderInfo, AgentDto superAgent2) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		UserInfoDto userInfo2 = userInfoService.getUserInfo(superAgent2.getGuid(), superAgent2.getAppid());
		AgentDto superAgent3 = agentService.getAgent(userInfo2.getAppid(), userInfo2.getSuperGuid());
		if (null != superAgent3 && null != userInfo2) {
			AgentRate agentRate3 = agentRateService.get(superAgent3.getGuid(), superAgent3.getAppid());
			IncomeLog incomeLog = new IncomeLog();
			incomeLog.setTotalFee(orderInfo.getTotalFee());
			incomeLog.setAppid(orderInfo.getAppid());
			incomeLog.setGuid(orderInfo.getGuid());
			incomeLog.setOrderid(orderInfo.getOrderId());
			incomeLog.setUserLevel(3);
			incomeLog.setSuperGuid(userInfo2.getSuperGuid());
			incomeLog.setIncome(calculateIncome(orderInfo.getTotalFee(), agentRate3.getRate3()));// 设置收入
			incomeLog.setCreateTime(new Date());
			if (superAgent3.getState() == AgentConstance.NORMAL_STATE) {// 代理处于正常状态
				incomeLog.setState(AgentConstance.NORMAL_STATE);
				map.put("income3", incomeLog.getIncome());// 加作为上级代理收入
				map.put("balance", incomeLog.getIncome());// 加余额
			} else {// 冻结状态
				incomeLog.setState(AgentConstance.FROZEN_STATE);
				map.put("freeze", incomeLog.getIncome());// 加冻结余额
			}
			map.put("guid", superAgent3.getGuid());
			map.put("appid", superAgent3.getAppid());
			incomeLogService.insert(incomeLog);
			agentService.updateByCondition(map);
		}
		return superAgent3;
	}

	@Override
	public JSONObject insertSynOrderInfo(Map<String, String> params) {
		JSONObject result = new JSONObject();
		try {
			String appidStr = params.get("appid");
			if (StringUtils.isEmpty(appidStr)) {
				result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return result;
			}
			int appid = Integer.parseInt(appidStr);
			String guid = params.get("guid");
			if (StringUtils.isEmpty(guid)) {
				result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return result;
			}
			
			String superGuidStr = params.get("superGuid");
			int superGuid = 0;
			if (!StringUtils.isEmpty(superGuidStr) && StringUtils.isDigit(superGuidStr)) {
				superGuid = Integer.parseInt(superGuidStr);
			}
			String payTime = params.get("payTime");
			if (StringUtils.isEmpty(payTime)) {
				result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return result;
			}
			
			String orderId = params.get("orderId");
			if (StringUtils.isEmpty(orderId)) {
				result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return result;
			}
			String wares = params.get("wares");
			String totalFee = params.get("totalFee");
			if (StringUtils.isEmpty(totalFee) || !StringUtils.isDigit(totalFee)) {
				result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return result;
			}
			
			AppInfoDto appInfoDto = appInfoService.findByID(appid);
			if (appInfoDto == null || !SignVerify.verify(params, appInfoDto.getAppkey())) { // 签名校验错误返回
				result.put("code", RetSatusAndMsg.DATA_CHECK_FAIL.getValue());
				result.put("msg", RetSatusAndMsg.DATA_CHECK_FAIL.getName());
				return result;
			}

			OrderInfoDTO orderInfo = orderInfoDao.getByOrderId(appid, orderId);
			if (orderInfo == null) {
				orderInfo = new OrderInfoDTO();
				orderInfo.setAppid(appid);
				orderInfo.setGuid(Integer.parseInt(guid));
				orderInfo.setSuperGuid(superGuid);
				orderInfo.setOrderId(orderId);
				orderInfo.setWares(wares);
				orderInfo.setTotalFee(Integer.parseInt(totalFee));
				orderInfo.setPayTime(DateUtils.parseFullDate(payTime));
				int flag = orderInfoDao.insert(orderInfo);
				if (flag > 0) {
					// 更新用户的充值总金额
					userInfoService.updateTotalFee(orderInfo.getGuid(), orderInfo.getAppid(), orderInfo.getTotalFee());
					AgentDto superAgent1 = updateAgent1(orderInfo);
					if (null != superAgent1) {
						AgentDto superAgent2 = updateAgent2(orderInfo, superAgent1);
						if (null != superAgent2) {
							updateAgent3(orderInfo, superAgent2);
						}
					}
				} else {
					result.put("code", RetSatusAndMsg.DATA_DUPLICATIUON_ERROR.getValue());
					result.put("msg", RetSatusAndMsg.DATA_DUPLICATIUON_ERROR.getName());
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
			result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
			return result;
		}
		result.put("code", RetSatusAndMsg.OK.getValue());
		result.put("msg", RetSatusAndMsg.OK.getName());
		return result;
	}

	/**
	 * 计算各级代理应得收入(Income,Rate) income单位 分
	 * 
	 * @return
	 */
	private static int calculateIncome(int income, int rate) {
		return income * rate / 100;
	}

	@Override
	public int countTotalFeeByDay(int appid, Date day) {
		return orderInfoDao.countTotalFeeByDay(appid, day);

	}

	@Override
	public List<OrderInfoDTO> getOrdersBySuperGuid(AuthUser sessionUser) {
		return orderInfoDao.getOrdersBySuperGuid(sessionUser.getAppid(), sessionUser.getGuid());
	}

	@Override
	public int countTodayTotalFee(Map<String, Object> params) {
		return orderInfoDao.countTodayTotalFee(params);
	}

	@Override
	public int countMothTotalFee(Map<String, Object> params) {
		return orderInfoDao.countMothTotalFee(params);
	}

	@Override
	public int countAllTotalFee(Map<String, Object> params) {
		return orderInfoDao.countAllTotalFee(params);
	}

	@Override
	public List<OrderInfoDTO> getOrdersByGuidAndSuper(int appid, int guid, int superGuid) {
		return orderInfoDao.getOrdersByGuidAndSuper(appid, guid, superGuid);
	}
}
