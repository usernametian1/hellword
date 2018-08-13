package com.zhiyou.notice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.notice.dao.AgentNoticeDao;
import com.zhiyou.notice.dto.AgentNoticeDto;
import com.zhiyou.notice.service.AgentNoticeService;

@Service("agentNoticeService")
public class AgentNoticeServiceImpl implements AgentNoticeService {
	
	@Autowired
   private AgentNoticeDao agentNoticeDao;
	
	@Override
	public int insertAgentNotice(AgentNoticeDto agentNoticeDto) {
		agentNoticeDto.setCreateTime(new Date());
      return agentNoticeDao.insert(agentNoticeDto);
	}

	@Override
	public boolean deleteById(int id) {
		
		return agentNoticeDao.delete(id);
	}

	@Override
	public boolean updateAgentNotice(AgentNoticeDto agentNoticeDto) {
		agentNoticeDto.setUpdateTime(new Date());
		return agentNoticeDao.update(agentNoticeDto);
	}

	@Override
	public AgentNoticeDto selectAgentNotice(String name) {	
		return agentNoticeDao.getName(name);
	}
	

	@Override
	public List<AgentNoticeDto> findForpage(Map<String ,Object> params , PagingDto paging){
		int totalRows = agentNoticeDao.count(params);
        paging.setTotalRows(totalRows);
        params.put("startIndex", paging.getStartRowIndex());
        params.put("pageSize", paging.getPageSize());
		return agentNoticeDao.findForPage(params);
		
	}

	@Override
	public AgentNoticeDto findById(int id) {
		
		return agentNoticeDao.getid(id);
	}


}
