package com.zhiyou.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.order.dto.IncomeLog;

public interface IncomeLogDao {
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
	List<IncomeLog> getIncomeByCondition(Map<String, Object> map);

	int findTotalCount(Map<String, Object> params);

	List<IncomeLog> findForPage(Map<String, Object> params);

	/**
	 * 代理今日分成收入
	 * 
	 * @param superGuid
	 * @return
	 */
	int countTodayIncome(Map<String, Object> params);


}
