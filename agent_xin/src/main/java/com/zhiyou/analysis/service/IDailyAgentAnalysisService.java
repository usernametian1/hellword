package com.zhiyou.analysis.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.analysis.dto.DailyAgentAnalysisDto;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;

public interface IDailyAgentAnalysisService {

	/*
	 * 每日数据的插入
	 */
	public void getListDailyAgentlysisDto(String day);

	List<DailyAgentAnalysisDto> getListDailyAgentAnalysisDto(Map<String, Object> params, PagingDto paging);

	/**
	 * 代理的每日数据展示
	 */
	List<DailyAgentAnalysisDto> getMlistDailyAgent(int appid, int guid);

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
	int getDailyAgentBindOrIncome(AuthUser authUser, String day, String tableName, String filed);

	/**
	 * 今日代理下钻石消耗
	 * 
	 * @param authUser
	 * 
	 * @param params
	 * @return
	 */
	int getTodayUsedDiamond(AuthUser authUser);

	/**
	 * 今日代理下开桌总数
	 * 
	 * @param authUser
	 * 
	 * @param params
	 * @return
	 */
	int getTodayCountTable(AuthUser authUser);

	/*
	 * 历史钻石消耗 开桌数
	 */

	int getDailyAgentCountTableOrDiamonds(AuthUser authUser, String day, String field);

}
