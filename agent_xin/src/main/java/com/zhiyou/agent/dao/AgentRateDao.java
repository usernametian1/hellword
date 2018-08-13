package com.zhiyou.agent.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.agent.dto.AgentRate;

public interface AgentRateDao {
	/**
	 * 插入
	 * 
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
	List<AgentRate> getList(Map<String, Object> params);

	/**
	 * 获取单个
	 * 
	 * @param id
	 * @return
	 */
	AgentRate get(@Param("guid") int guid, @Param("appid") int appid);

	/**
	 * 删除单个
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(@Param("guid") int guid, @Param("appid") int appid);

	/**
	 * 更新
	 * 
	 * @param id
	 * @return
	 */
	boolean update(AgentRate agentRate);

	int findTotalCount(Map<String, Object> params);
}
