package com.zhiyou.agent.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.agent.dto.AgentDto;
import com.zhiyou.core.tag.PagingDto;

public interface IAgentService {
	public boolean insertAgent(AgentDto e);

	public boolean deleteAgent(int appid, int guid);

	public int updateAgent(AgentDto e);

	public List<AgentDto> findForPage(Map<String, Object> params, PagingDto paging);

	int updateByCondition(Map<String, Object> params);

	public void doAudit(AgentDto agentDto);

	JSONObject applyAgent(HttpServletRequest req);

	/***
	 * 同步代理信息到游戏
	 * 
	 * @author cY
	 * @param appid
	 * @param agentId
	 */
	public void synAgent(int appid, int agentId);

	public AgentDto getAgent(int appid, int guid);
}
