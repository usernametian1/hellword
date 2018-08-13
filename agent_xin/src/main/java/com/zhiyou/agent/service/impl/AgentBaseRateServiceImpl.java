package com.zhiyou.agent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.agent.dao.AgentBaseRateDao;
import com.zhiyou.agent.dto.AgentBaseRate;
import com.zhiyou.agent.service.IAgentBaseRateService;
import com.zhiyou.core.tag.PagingDto;

@Service("agentBaseRateServiceImpl")
public class AgentBaseRateServiceImpl implements IAgentBaseRateService {
	@Autowired
	AgentBaseRateDao agentBaseRateDao;

	@Override
	public boolean insert(AgentBaseRate agentBaseRate) {
		return agentBaseRateDao.insert(agentBaseRate);
	}

	@Override
	public List<AgentBaseRate> getList(Map<String, Object> params, PagingDto paging) {
		int totalRows = agentBaseRateDao.findTotalCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return agentBaseRateDao.getList(params);
	}

	@Override
	public AgentBaseRate getByAppIDLevel(int appid, int level) {
		return agentBaseRateDao.getByAppIDLevel(appid, level);
	}

	@Override
	public boolean deletByAppIDLevel(int appid, int level) {
		return agentBaseRateDao.deletByAppIDLevel(appid, level);
	}

	@Override
	public boolean updateByAppIDLevel(AgentBaseRate agentBaseRate) {
		return agentBaseRateDao.updateByAppIDLevel(agentBaseRate);
	}

	@Override
	public List<AgentBaseRate> getListBaseRateByAppid(int appid) {
		return agentBaseRateDao.getListBaseRateByAppid(appid);
	}
}
