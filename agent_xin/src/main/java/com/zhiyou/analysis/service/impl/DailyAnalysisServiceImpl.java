package com.zhiyou.analysis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.analysis.dao.DailyAnalysisDao;
import com.zhiyou.analysis.dto.DailyAnalysisDto;
import com.zhiyou.analysis.service.IDailyAnalysisService;
import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.common.enums.RetSatusAndMsg;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.service.IOrderInfoService;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.SignVerify;

@Service("dailyAnalysisServiceImpl")
public class DailyAnalysisServiceImpl implements IDailyAnalysisService {
	private static final Logger log = Logger.getLogger(DailyAnalysisServiceImpl.class);
	@Autowired
	DailyAnalysisDao dailyAnalysisDao;
	@Autowired
	IOrderInfoService orderInfoServiceImpl;
	@Autowired
	IAppInfoService appInfoServiceImpl;

	@Override
	public JSONObject insertDailyAnalysisDto(Map<String, String> params) {
		JSONObject jsonObject = new JSONObject();
		int appid = 0;
		int regNum = 0;
		int dau = 0;
		String day = "";
		int online = 0;
		Date onlineTime = null;
		try {
			day = params.get("day");
			regNum = Integer.valueOf(params.get("regNum"));
			appid = Integer.valueOf(params.get("appid"));
			dau = Integer.valueOf(params.get("dau"));
			online = Integer.valueOf(params.get("online"));
			onlineTime = DateUtils.parseFullDate(params.get("onlineTime"));
		} catch (Exception e) {
			jsonObject.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
			jsonObject.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
			return jsonObject;
		}
		
		AppInfoDto appInfoDto = appInfoServiceImpl.findByID(appid);
		if (appInfoDto == null || !SignVerify.verify(params, appInfoDto.getAppkey())) { // 签名校验错误返回
			jsonObject.put("code", RetSatusAndMsg.DATA_CHECK_FAIL.getValue());
			jsonObject.put("msg", RetSatusAndMsg.DATA_CHECK_FAIL.getName());
			return jsonObject;
		}
		DailyAnalysisDto analysisDto = dailyAnalysisDao.getDailyAnalysisDto(appid, day);
		if (null != analysisDto) {
			jsonObject.put("code", RetSatusAndMsg.DATA_DUPLICATIUON_ERROR.getValue());
			jsonObject.put("msg", RetSatusAndMsg.DATA_DUPLICATIUON_ERROR.getName());
			return jsonObject;
		}

		Map<String, Object> qparam = new HashMap<String, Object>();
		qparam.put("appid", appid);
		qparam.put("day", day);
		DailyAnalysisDto tmp = dailyAnalysisDao.getDailyAnalysisDto(appid, day);
		DailyAnalysisDto dailyAnalysisDto = new DailyAnalysisDto();
		dailyAnalysisDto.setAppid(appid);
		dailyAnalysisDto.setDay(day);
		dailyAnalysisDto.setRegNum(regNum);
		dailyAnalysisDto.setDau(dau);
		dailyAnalysisDto.setOnline(online);
		dailyAnalysisDto.setOnlineTime(onlineTime);
		dailyAnalysisDto.setUpdateTime(new Date());
		try {
			if (tmp == null) {
				dailyAnalysisDao.insertDailyAnalysis(dailyAnalysisDto);
			} else {
				dailyAnalysisDao.updateDailyAnalysis(dailyAnalysisDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("同步insertDailyAnalysisDto异常");
			jsonObject.put("code", RetSatusAndMsg.SYSTEM_ERROR.getValue());
			jsonObject.put("code", RetSatusAndMsg.SYSTEM_ERROR.getName());
			return jsonObject;
		}
		jsonObject.put("code", RetSatusAndMsg.OK.getValue());
		jsonObject.put("msg", RetSatusAndMsg.OK.getName());
		return jsonObject;
	}

	@Override
	public List<DailyAnalysisDto> getListDailyAnalysisDto(Map<String, Object> params, PagingDto paging) {
		int totalRows = dailyAnalysisDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return dailyAnalysisDao.findForPage(params);
	}
}
