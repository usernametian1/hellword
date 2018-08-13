package com.zhiyou.agent.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.agent.dto.AgentBaseRate;
import com.zhiyou.core.tag.PagingDto;

public interface IAgentBaseRateService {
	/**
	 * 插入
	 * 
	 * @param agentBaseRate
	 * @return
	 */
	boolean insert(AgentBaseRate agentBaseRate);

	/**
	 * 查询列表|可分页
	 * 
	 * @param paging
	 * @param params
	 * 
	 * @param params
	 * @return
	 */
	List<AgentBaseRate> getList(Map<String, Object> params, PagingDto paging);

	/**
	 * 获取单个
	 * 
	 * @param id
	 * @return
	 */
	AgentBaseRate getByAppIDLevel(int appid, int level);

	/**
	 * 删除单个
	 * 
	 * @param id
	 * @return
	 */
	boolean deletByAppIDLevel(int appid, int level);

	/**
	 * 更新
	 * 
	 * @param id
	 * @return
	 */
	boolean updateByAppIDLevel(AgentBaseRate agentBaseRate);

	List<AgentBaseRate> getListBaseRateByAppid(int appid);

}
