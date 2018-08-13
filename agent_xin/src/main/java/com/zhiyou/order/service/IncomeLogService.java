package com.zhiyou.order.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.dto.IncomeLog;

public interface IncomeLogService {
	/**
	 * 插入日志
	 * 
	 * @param incomeLog
	 * @return
	 */
	boolean insert(IncomeLog incomeLog);

	/**
	 * 查询,可配条件|分页
	 * 
	 * @param map
	 * @return
	 */
	List<IncomeLog> getIncomeByCondition(Map<String, Object> params, PagingDto paging);

	List<IncomeLog> getIncomeBySuperGuid(AuthUser sessionUser, int level);

	int countTodayIncome(AuthUser sessionUser, int level);

	List<IncomeLog> findForPage(Map<String, Object> params, PagingDto paging);
}
