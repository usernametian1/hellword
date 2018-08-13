package com.zhiyou.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.dao.IncomeLogDao;
import com.zhiyou.order.dto.IncomeLog;
import com.zhiyou.order.service.IncomeLogService;

@Service("incomeLogServiceImpl")
public class IncomeLogServiceImpl implements IncomeLogService {
	@Autowired
	IncomeLogDao incomeLogDao;

	@Override
	public boolean insert(IncomeLog incomeLog) {
		return incomeLogDao.insert(incomeLog);
	}

	@Override
	public List<IncomeLog> getIncomeByCondition(Map<String, Object> params, PagingDto paging) {
		int totalRows = incomeLogDao.findTotalCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return incomeLogDao.getIncomeByCondition(params);
	}

	@Override
	public List<IncomeLog> getIncomeBySuperGuid(AuthUser sessionUser, int level) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appid", sessionUser.getAppid());
		params.put("superGuid", sessionUser.getGuid());
		params.put("userLevel", level);
		return incomeLogDao.getIncomeByCondition(params);
	}

	@Override
	public int countTodayIncome(AuthUser sessionUser, int level) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appid", sessionUser.getAppid());
		params.put("superGuid", sessionUser.getGuid());
		params.put("userLevel", level);
		params.put("day", new Date());
		return incomeLogDao.countTodayIncome(params);
	}

	@Override
	public List<IncomeLog> findForPage(Map<String, Object> params, PagingDto paging) {
		int totalRows = incomeLogDao.findTotalCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return incomeLogDao.findForPage(params);
	}
}
