package com.zhiyou.notice.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.notice.dto.AgentNoticeDto;

public interface AgentNoticeService {

	public int insertAgentNotice(AgentNoticeDto agentNoticeDto);
	
	public boolean deleteById(int id);
	
	public boolean updateAgentNotice(AgentNoticeDto agentNoticeDto);
	
	public AgentNoticeDto selectAgentNotice(String name);
	
	public List<AgentNoticeDto> findForpage(Map<String,Object> params,PagingDto paging);

	public AgentNoticeDto findById(int id);


}
