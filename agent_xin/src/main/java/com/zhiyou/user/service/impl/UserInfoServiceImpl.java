package com.zhiyou.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.common.constance.AgentConstance;
import com.zhiyou.common.enums.RetSatusAndMsg;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.user.dao.UserInfoDao;
import com.zhiyou.user.dto.UserBindLog;
import com.zhiyou.user.dto.UserInfoDto;
import com.zhiyou.user.service.IUserInfoService;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.SignVerify;
import com.zhiyou.utils.StringUtils;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

	private static final Logger log = Logger.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private IAgentService agentService;
	@Autowired
	private IAppInfoService appInfoService;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	@Override
	public UserInfoDto getUserInfo(int guid, int appid) {

		return userInfoDao.getUserInfo(guid, appid);
	}

	@Override
	public int updateUserInfo(UserInfoDto userInfoDto) {

		return userInfoDao.update(userInfoDto);
	}

	@Override
	public int insertUserInfo(UserInfoDto userInfoDto) {

		return userInfoDao.insert(userInfoDto);
	}

	@Override
	public int deleteUserInfo(int guid, int appid) {
		return userInfoDao.delete(guid, appid);
	}

	@Override
	public List<UserInfoDto> findForPage(Map<String, Object> params, PagingDto paging) {
		int totalRows = userInfoDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return userInfoDao.findForPage(params);
	}

	/**
	 * 保存同步玩家信息并绑定代理
	 */
	@Override
	public JSONObject insertSynGuser(Map<String, String> params) {
		JSONObject result = new JSONObject();
		try {

			String nickName = params.get("nickName");
			String superGuidStr = (String) params.get("superGuid");
			String bindTimeStr = params.get("bindTime");
			Date regTime = null;
			int appid = 0;
			int guid = 0;
			int superGuid = 0;
			Date bindTime = null;
			try {
				appid = Integer.parseInt(params.get("appid"));
				guid = Integer.parseInt(params.get("guid"));
				String regTimeStr = params.get("regTime");
				if (StringUtils.isEmpty(regTimeStr)) {
					result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
					result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
					return result;
				}
				regTime = DateUtils.parseFullDate(regTimeStr);
				
				if (!StringUtils.isEmpty(superGuidStr) && !StringUtils.isEmpty(bindTimeStr)) {
					superGuid = Integer.parseInt(superGuidStr);
					bindTime = DateUtils.parseFullDate(bindTimeStr);
				}
			} catch (Exception e) {
				result.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				result.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return result;
			}
			
			

			AppInfoDto appInfoDto = appInfoService.findByID(appid);
			if (null == appInfoDto || !SignVerify.verify(params, appInfoDto.getAppkey())) { // 签名校验错误返回
				result.put("code", RetSatusAndMsg.DATA_CHECK_FAIL.getValue());
				result.put("msg", RetSatusAndMsg.DATA_CHECK_FAIL.getName());
				return result;
			}

			UserInfoDto userInfo = userInfoDao.getUserInfo(guid, appid);
			if (userInfo == null) {
				// 先insert,无其他相关操作
				userInfo = new UserInfoDto();
				userInfo.setAppid(appid);
				userInfo.setGuid(guid);
				userInfo.setNickName(nickName);
				userInfo.setRegTime(regTime);
				userInfoDao.insert(userInfo);
//				if (!StringUtils.isEmpty(superGuidStr) && !StringUtils.isEmpty(bindTimeStr)) {
//					result.put("code", RetSatusAndMsg.OK.getValue());
//					result.put("msg", RetSatusAndMsg.OK.getName());
//					return result;
//				}
			}

			// 不存在,则判断是否有superGuid信息,有则执行绑定,否则不再执行
			// 已存在,执行绑定操作
			// 校验superGuid是否正确
			
			if (superGuid > 0) {
				AgentDto bindAgent = agentService.getAgent(userInfo.getAppid(), superGuid);
				if (bindAgent == null || bindAgent.getStatus() != AgentConstance.APPLY_PASS_STATUS) {
					result.put("code", RetSatusAndMsg.AGENT_NOT_FOUND.getValue());// 代理不存在或者未审核通过
					result.put("msg", RetSatusAndMsg.AGENT_NOT_FOUND.getName());
					return result;
				}

				UserInfoDto bindUseInfo = userInfoDao.getUserInfo(bindAgent.getGuid(), bindAgent.getAppid());
				// 绑定父级对象不存在|父级对象不是代理,返回错误
				if (bindUseInfo.getSuperGuid() == guid) {// 不能互绑
					result.put("code", RetSatusAndMsg.FORBIDDEN_BIND_RELATED.getValue());
					result.put("msg", RetSatusAndMsg.FORBIDDEN_BIND_RELATED.getName());
					return result;
				}
				if (userInfo.getGuid() == superGuid) {// 不能自己绑定自己
					result.put("code", RetSatusAndMsg.FORBIDDEN_BIND_SELF.getValue());
					result.put("msg", RetSatusAndMsg.FORBIDDEN_BIND_SELF.getName());
					return result;
				}
				if (userInfo.getSuperGuid() > 0) {// 不能重复绑定
					result.put("code", RetSatusAndMsg.DUPLICATIUON_BIND_ERROR.getValue());
					result.put("msg", RetSatusAndMsg.DUPLICATIUON_BIND_ERROR.getName());
					return result;
				}
				// 执行更新
				userInfo.setSuperGuid(superGuid);
				userInfo.setBindTime(bindTime);
				int updateRet = userInfoDao.update(userInfo);
				if (updateRet > 0) {
					try {
						// 用户绑定成功,更新代理信息agent表
						// 直属上级代理
						UserInfoDto supuserInfoDto = bindUseInfo;
						HashMap<String, Object> map = new HashMap<String, Object>();
						if (null != supuserInfoDto) {
							map.put("guid", supuserInfoDto.getGuid());
							map.put("appid", supuserInfoDto.getAppid());
							map.put("userNum1", 1);
							agentService.updateByCondition(map);
							UserBindLog userBindLog = new UserBindLog();
							userBindLog.setAppid(userInfo.getAppid());
							userBindLog.setGuid(userInfo.getGuid());
							userBindLog.setSuperGuid(superGuid);
							userBindLog.setSuperLevel(1);
							userBindLog.setCreateTime(new Date());
							userInfoDao.insertBindLog(userBindLog);
							// 上上级
							UserInfoDto userInfoDto2 = userInfoDao.getUserInfo(supuserInfoDto.getSuperGuid(), supuserInfoDto.getAppid());// 上级获取上上级信息
							if (null != userInfoDto2) {
								map.clear();
								map.put("guid", userInfoDto2.getGuid());
								map.put("appid", userInfoDto2.getAppid());
								map.put("userNum2", 1);
								agentService.updateByCondition(map);
								userBindLog.setSuperGuid(supuserInfoDto.getSuperGuid());
								userBindLog.setSuperLevel(2);
								userBindLog.setCreateTime(new Date());
								userInfoDao.insertBindLog(userBindLog);
								// 上上上级
								UserInfoDto userInfoDto3 = userInfoDao.getUserInfo(userInfoDto2.getSuperGuid(), userInfoDto2.getAppid());
								if (null != userInfoDto3) {
									map.clear();
									map.put("guid", userInfoDto3.getGuid());
									map.put("appid", userInfoDto3.getAppid());
									map.put("userNum3", 1);
									agentService.updateByCondition(map);
									userBindLog.setSuperGuid(userInfoDto2.getSuperGuid());
									userBindLog.setSuperLevel(3);
									userBindLog.setCreateTime(new Date());
									userInfoDao.insertBindLog(userBindLog);
								}
							}
						}
					} catch (Exception e) {
						log.error("insertsynGuser更新Agent表信息异常:{}", e);
						result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
						result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
						return result;
					}

				}
			}

		} catch (Exception e) {
			log.error("insertsynGuser异常:{}", e);
			result.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
			result.put("msg", RetSatusAndMsg.SYSTEM_ERROR.getName());
		}
		result.put("code", RetSatusAndMsg.OK.getValue());
		result.put("msg", RetSatusAndMsg.OK.getName());
		return result;
	}

	@Override
	public int updateTotalFee(int guid, int appid, int totalFee) {
		return userInfoDao.updateTotalFee(guid, appid, totalFee);
	}

	@Override
	public List<UserInfoDto> getUsersBySuperGuid(AuthUser sessionUser) {
		return userInfoDao.getUsersBySuperGuid(sessionUser.getAppid(), sessionUser.getGuid());
	}

	@Override
	public int countTodayBind(Map<String, Object> params) {
		return userInfoDao.countTodayBind(params);
	}

	@Override
	public int countMothBind(Map<String, Object> params) {

		return userInfoDao.countMothBind(params);
	}

	@Override
	public int countAllBind(Map<String, Object> params) {
		return userInfoDao.countAllBind(params);
	}

	/**
	 * 各级代理今日绑定
	 */
	@Override
	public int getDayBindNum(AuthUser sessionUser, int level) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", sessionUser.getAppid());
		params.put("superGuid", sessionUser.getGuid());
		params.put("superLevel", level);
		params.put("day", new Date());
		return userInfoDao.getDayBindNum(params);
	}

	/**
	 * 绑定日志插入
	 */
	@Override
	public int insertBindLog(UserBindLog userBindLog) {
		return userInfoDao.insertBindLog(userBindLog);
	}

	@Override
	public UserInfoDto getUserInfoBySuperGuid(int appid, int guid, int superGuid) {
		return userInfoDao.getUserInfoBySuperGuid(appid, guid, superGuid);
	}

}
