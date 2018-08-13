package com.zhiyou.agent.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.agent.dto.AgentRate;
import com.zhiyou.core.tag.PagingDto;

public interface IAgentRateService {
	/**
	 * 插入
	 * @param agentRate
	 * @return
	 */
	boolean insert(AgentRate agentRate);

	/**
	 * 查询列表|可分页
	 * 
	 * @param params
	 * @return
	 */
	List<AgentRate> getList(Map<String, Object> params, PagingDto paging);

	/**
	 * 获取单个
	 * 
	 * @param id
	 * @return
	 */
	AgentRate get(int guid, int appid);

	/**
	 * 删除单个
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(int guid, int appid);

	/**
	 * 更新
	 * 
	 * @param id
	 * @return
	 */
	boolean update(AgentRate agentRate);
}
