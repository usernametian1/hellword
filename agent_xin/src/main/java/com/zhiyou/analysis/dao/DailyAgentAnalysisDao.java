package com.zhiyou.analysis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.analysis.dto.DailyAgentAnalysisDto;

public interface DailyAgentAnalysisDao {

	int insertDailyAgentAnalysis(DailyAgentAnalysisDto dailyAgentAnalysisDto);

	DailyAgentAnalysisDto getDailyAgentAnalysis(@Param("appid") int appid, @Param("day") String day, @Param("guid") int guid);

	int count(Map<String, Object> params);

	List<DailyAgentAnalysisDto> findForPage(Map<String, Object> params);

	List<DailyAgentAnalysisDto> getMlistDailyAgent(@Param("appid") int appid, @Param("guid") int guid);

	/**
	 * 统计代理的每日绑定数
	 * 
	 * @param day
	 */
	void insertDailyAgentBindNum1(String day);

	void insertDailyAgentBindNum2(String day);

	void insertDailyAgentBindNum3(String day);

	/**
	 * 统计代理的每日总收益
	 * 
	 * @param day
	 */
	void insertDailyAgentIncome1(String day);

	void insertDailyAgentIncome2(String day);

	void insertDailyAgentIncome3(String day);

	/**
	 * 统计代理的每日总收益
	 * 
	 * @param day
	 */
	void insertDailyAgentTableAnalysis(String day);

	/**
	 * 代理各级的日绑定或收益
	 * 
	 * @param params
	 * @return
	 */
	int getDailyAgentBindOrIncome(Map<String, Object> params);

	/**
	 * 今日代理下钻石消耗
	 * 
	 * @param params
	 * @return
	 */
	int getTodayUsedDiamond(Map<String, Object> params);

	/**
	 * 今日代理下开桌总数
	 * 
	 * @param params
	 * @return
	 */
	int getTodayCountTable(Map<String, Object> params);

	int getDailyAgentCountTableOrDiamonds(Map<String, Object> params);
}
