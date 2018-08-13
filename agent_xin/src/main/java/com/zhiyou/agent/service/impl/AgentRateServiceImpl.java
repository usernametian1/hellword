package com.zhiyou.agent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.agent.dao.AgentRateDao;
import com.zhiyou.agent.dto.AgentRate;
import com.zhiyou.agent.service.IAgentRateService;
import com.zhiyou.core.tag.PagingDto;

@Service("agentRateServiceImpl")
public class AgentRateServiceImpl implements IAgentRateService {
	@Autowired
	AgentRateDao agentRateDao;

	@Override
	public boolean insert(AgentRate agentRate) {
		if (agentRate.getGuid() == 0) {
			return false;
		}
		return agentRateDao.insert(agentRate);
	}

	@Override
	public List<AgentRate> getList(Map<String, Object> params, PagingDto paging) {
		int totalRows = agentRateDao.findTotalCount(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return agentRateDao.getList(params);
	}

	@Override
	public AgentRate get(int guid, int appid) {
		return agentRateDao.get(guid, appid);
	}

	@Override
	public boolean delete(int guid, int appid) {
		return agentRateDao.delete(guid, appid);
	}

	@Override
	public boolean update(AgentRate agentRate) {
		return agentRateDao.update(agentRate);
	}

}
