package com.zhiyou.analysis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.agent.service.IAgentService;
import com.zhiyou.analysis.dao.DailyAgentAnalysisDao;
import com.zhiyou.analysis.dao.DailyAnalysisDao;
import com.zhiyou.analysis.dto.DailyAgentAnalysisDto;
import com.zhiyou.analysis.dto.TableInfoLogDto;
import com.zhiyou.analysis.service.IDailyAgentAnalysisService;
import com.zhiyou.analysis.service.ITableInfoLogService;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.dao.IncomeLogDao;
import com.zhiyou.order.service.IncomeLogService;
import com.zhiyou.user.dao.UserInfoDao;
import com.zhiyou.utils.DateUtils;

@Service("dailyAgentsisService")
public class DailyAgentAnalysisServiceImpl implements IDailyAgentAnalysisService {

	private static final Logger log = Logger.getLogger(DailyAgentAnalysisServiceImpl.class);

	@Autowired
	private DailyAgentAnalysisDao dilyAgentAnalysisDao;

	@Autowired
	private IAgentService agentService;

	@Autowired
	private IAppInfoService appInfoService;

	@Autowired
	private IncomeLogDao incomeLogDao;

	@Autowired
	private ITableInfoLogService tableInfoLogService;

	@Autowired
	private DailyAnalysisDao adilyAnalysisiDao;

	@Autowired
	private IncomeLogService incomeLogService;

	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public List<DailyAgentAnalysisDto> getListDailyAgentAnalysisDto(Map<String, Object> params, PagingDto paging) {
		int totalRows = dilyAgentAnalysisDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return dilyAgentAnalysisDao.findForPage(params);
	}

	/**
	 * 代理的每日组局
	 */
	@Override
	public List<DailyAgentAnalysisDto> getMlistDailyAgent(int appid, int guid) {
		return dilyAgentAnalysisDao.getMlistDailyAgent(appid, guid);
	}

	/**
	 * 代理每日数据插入数据
	 */
	@Override
	public void getListDailyAgentlysisDto(String day) {
		List<TableInfoLogDto> tableInfoLogList = tableInfoLogService.selectDistTableInfo(day);
		DailyAgentAnalysisDto dailyAgentAnalysisDto = new DailyAgentAnalysisDto();
		Map<String, Object> params = new HashMap<String, Object>();
		for (TableInfoLogDto tableInfo : tableInfoLogList) {
			params.put("day", day);
			params.put("superGuid", tableInfo.getSuperGuid());
			dailyAgentAnalysisDto.setAppid(tableInfo.getAppid());
			dailyAgentAnalysisDto.setGuid(tableInfo.getSuperGuid());
			dailyAgentAnalysisDto.setDay(tableInfo.getDay());
			dailyAgentAnalysisDto.setTableNum(adilyAnalysisiDao.getDayTableNum(params));
			dailyAgentAnalysisDto.setBindNum(userInfoDao.getDayBindNum(params));
			dailyAgentAnalysisDto.setUseDiamond(adilyAnalysisiDao.getDayDiamondUsed(params));
			params.put("userLevel", 1);
			dailyAgentAnalysisDto.setIncome1(incomeLogDao.countTodayIncome(params));
			params.put("userLevel", 2);
			dailyAgentAnalysisDto.setIncome2(incomeLogDao.countTodayIncome(params));
			params.put("userLevel", 3);
			dailyAgentAnalysisDto.setIncome3(incomeLogDao.countTodayIncome(params));
			dailyAgentAnalysisDto.setUpdateTime(new Date());
			dilyAgentAnalysisDao.insertDailyAgentAnalysis(dailyAgentAnalysisDto);
		}
	}

	@Override
	public void insertDailyAgentBindNum1(String day) {
		dilyAgentAnalysisDao.insertDailyAgentBindNum1(day);

	}

	@Override
	public void insertDailyAgentBindNum2(String day) {
		dilyAgentAnalysisDao.insertDailyAgentBindNum2(day);

	}

	@Override
	public void insertDailyAgentBindNum3(String day) {
		dilyAgentAnalysisDao.insertDailyAgentBindNum3(day);

	}

	@Override
	public void insertDailyAgentIncome1(String day) {
		dilyAgentAnalysisDao.insertDailyAgentIncome1(day);

	}

	@Override
	public void insertDailyAgentIncome2(String day) {
		dilyAgentAnalysisDao.insertDailyAgentIncome2(day);

	}

	@Override
	public void insertDailyAgentIncome3(String day) {
		dilyAgentAnalysisDao.insertDailyAgentIncome3(day);

	}

	@Override
	public void insertDailyAgentTableAnalysis(String day) {
		dilyAgentAnalysisDao.insertDailyAgentTableAnalysis(day);

	}

	@Override
	public int getDailyAgentBindOrIncome(AuthUser authUser, String day, String tableName, String filed) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("filed", filed);
		params.put("queryTab", tableName);
		params.put("day", day);
		params.put("appid", authUser.getAppid());
		params.put("guid", authUser.getGuid());
		return dilyAgentAnalysisDao.getDailyAgentBindOrIncome(params);
	}

	@Override
	public int getTodayUsedDiamond(AuthUser authUser) {
		String nowStr = DateUtils.formatSimpleDate(new Date());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("day", nowStr);
		params.put("appid", authUser.getAppid());
		params.put("superGuid", authUser.getGuid());
		return dilyAgentAnalysisDao.getTodayUsedDiamond(params);
	}

	@Override
	public int getTodayCountTable(AuthUser authUser) {
		String nowStr = DateUtils.formatSimpleDate(new Date());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("day", nowStr);
		params.put("appid", authUser.getAppid());
		params.put("superGuid", authUser.getGuid());
		return dilyAgentAnalysisDao.getTodayCountTable(params);
	}

	@Override
	public int getDailyAgentCountTableOrDiamonds(AuthUser authUser, String day, String filed) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("day", day);
		params.put("filed", filed);
		params.put("appid", authUser.getAppid());
		params.put("guid", authUser.getGuid());
		return dilyAgentAnalysisDao.getDailyAgentCountTableOrDiamonds(params);
	}
}
