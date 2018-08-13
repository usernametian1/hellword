package com.zhiyou.analysis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.analysis.dto.DailyAnalysisDto;

public interface DailyAnalysisDao {

	int insertDailyAnalysis(DailyAnalysisDto dailyAnalysisDto);

	DailyAnalysisDto getDailyAnalysisDto(@Param("appid") int appid, @Param("day") String day);

	int count(Map<String, Object> params);

	/**
	 * 某app日注册数
	 * 
	 * @param params
	 * @return
	 */
	int getDayRegNum(Map<String, Object> params);

	/**
	 * 某app日开桌数
	 * 
	 * @param params
	 * @return
	 */
	int getDayTableNum(Map<String, Object> params);

	/**
	 * 某app日对局
	 * 
	 * @param params
	 * @return
	 */
	int getDayRoundNum(Map<String, Object> params);

	/**
	 * 某app日钻石消耗
	 * 
	 * @param params
	 * @return
	 */
	int getDayDiamondUsed(Map<String, Object> params);

	List<DailyAnalysisDto> findForPage(Map<String, Object> params);

	/**
	 * 统计每个app下每天开桌对局信息,钻石消耗
	 */
	void insertBatchDailyTableAnalysis(String day);

	/**
	 * 统计每个app每天充值金额
	 */
	void insertBatchDailyRechargeAnalysis(String day);

	void updateDailyAnalysis(DailyAnalysisDto dailyAnalysisDto);

}
