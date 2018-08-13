package com.zhiyou.analysis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.analysis.dao.DailyAgentAnalysisDao;
import com.zhiyou.analysis.dao.DailyAnalysisDao;
import com.zhiyou.utils.DateUtils;

@Service("anlysisDailyTaskService")
public class AnlysisDailyTaskService {
	private static final Logger log = Logger.getLogger(AnlysisDailyTaskService.class);
	@Autowired
	private DailyAgentAnalysisDao dilyAgentAnalysisDao;// 代理下
	@Autowired
	private DailyAnalysisDao dailyAnalysisDao;// 游戏appid下

	/**
	 * 按appid,superguid统计代理下信息
	 */
	public void agentAnlysisDailyBindNum() {
		String dateStr = DateUtils.getYesterday();
		try {
			log.info("agentAnlysisDailyBindNum()开始执行绑定统计>>>>");
			dilyAgentAnalysisDao.insertDailyAgentBindNum1(dateStr);
			dilyAgentAnalysisDao.insertDailyAgentBindNum2(dateStr);
			dilyAgentAnalysisDao.insertDailyAgentBindNum3(dateStr);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void agentAnlysisDailyIncome() {
		String dateStr = DateUtils.getYesterday();
		try {
			log.info("agentAnlysisDailyIncome()开始执行收益统计>>>>");
			dilyAgentAnalysisDao.insertDailyAgentIncome1(dateStr);
			dilyAgentAnalysisDao.insertDailyAgentIncome2(dateStr);
			dilyAgentAnalysisDao.insertDailyAgentIncome3(dateStr);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void agentAnlysisDailyTable() {
		String dateStr = DateUtils.getYesterday();
		try {
			log.info("agentAnlysisDailyTable()开始执行对局,钻石消耗统计>>>>");
			dilyAgentAnalysisDao.insertDailyAgentTableAnalysis(dateStr);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 按appid统计信息
	 */
	public void appAnlysisDaily() {
		String dateStr = DateUtils.getYesterday();
		log.info("appAnlysisDaily()开始执行app每日充值统计>>>>");
		dailyAnalysisDao.insertBatchDailyRechargeAnalysis(dateStr);
		log.info("appAnlysisDaily()开始执行app每日对局,钻石消耗统计统计>>>>");
		dailyAnalysisDao.insertBatchDailyTableAnalysis(dateStr);
	}
}
