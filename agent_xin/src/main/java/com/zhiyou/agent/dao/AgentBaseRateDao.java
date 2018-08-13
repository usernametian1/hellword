package com.zhiyou.agent.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.agent.dto.AgentBaseRate;

public interface AgentBaseRateDao {
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
	 * @return
	 */
	List<AgentBaseRate> getList(Map<String, Object> params);

	/**
	 * 获取单个
	 * 
	 * @param id
	 * @return
	 */
	AgentBaseRate getByAppIDLevel(@Param("appid") int appid, @Param("level") int level);

	/**
	 * 删除单个
	 * 
	 * @param id
	 * @return
	 */
	boolean deletByAppIDLevel(@Param("appid") int appid, @Param("level") int level);

	/**
	 * 更新
	 * 
	 * @param id
	 * @return
	 */
	boolean updateByAppIDLevel(AgentBaseRate agentBaseRate);

	int findTotalCount(Map<String, Object> params);

	List<AgentBaseRate> getListBaseRateByAppid(int appid);

}
