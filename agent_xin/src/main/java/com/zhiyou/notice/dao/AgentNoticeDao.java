package com.zhiyou.notice.dao;

import java.util.List;
import java.util.Map;

import com.zhiyou.notice.dto.AgentNoticeDto;

public interface  AgentNoticeDao {


		/**
		 * 根据主键查询对象
		 * @param id
		 */
		public AgentNoticeDto  getid(int id);
		
		/**
		 * 根据标题查询对象
		 * @param userId
		 */
		public AgentNoticeDto getName(String name);
		
		/**
		 * 保存对象
		 * @param e
		 * @return
		 */
		public int insert(AgentNoticeDto agentNoticeDto);
		
		/**
		 * 更新对象
		 * @param e
		 */
		public boolean update(AgentNoticeDto agentNoticeDto);
		
		/**
		 * 根据主键删除对象
		 * @param id
		 */
		public boolean delete(int id);
		
		/**
		 * 查询列表(可条件可分页可排序)
		 * 分页参数:currentPage:当前的页码,pageSize:每页条数
		 * 排序参数:sortK,sortV
		 * @param params
		 * @return
		 */
		public List<AgentNoticeDto> findForPage(Map<String, Object> params);
		
		/**
		 * count表(可条件)
		 * @param params
		 * @return
		 */
		public int count(Map<String, Object> params);
		


}
