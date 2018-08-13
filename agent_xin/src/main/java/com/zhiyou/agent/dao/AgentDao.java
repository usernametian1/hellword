package com.zhiyou.agent.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.agent.dto.AgentDto;

public interface AgentDao {

	/**
	 * 保存对象
	 * 
	 * @param e
	 * @return
	 */
	public boolean insert(AgentDto agent);

	/**
	 * 修改对象
	 */
	public boolean update(AgentDto agent);

	/**
	 * 查询列表(可条件可分页可排序) 分页参数:currentPage:当前的页码,pageSize:每页条数 排序参数:sortK,sortV
	 * 
	 * @param params
	 * @return
	 */
	public List<AgentDto> findForPage(Map<String, Object> params);

	public int updateByCondition(Map<String, Object> params);

	/**
	 * count表(可条件)
	 * 
	 * @param params
	 * @return
	 */
	public int count(Map<String, Object> params);

	public AgentDto getAgent(@Param("appid") int appid, @Param("guid") int guid);

	/**
	 * 根据appid 和 guid 删除对象
	 * 
	 * @param params
	 * @return
	 */

	public boolean delete(@Param("guid") int guid, @Param("appid") int appid);

}
