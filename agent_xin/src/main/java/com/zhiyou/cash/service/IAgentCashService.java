package com.zhiyou.cash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.cash.dto.ApplyCashDto;
import com.zhiyou.core.tag.PagingDto;

public interface IAgentCashService {

	JSONObject insertCashApply(HttpServletRequest request);

	JSONObject updateCashApplyState(HttpServletRequest request);

	List<ApplyCashDto> getListCashApplyDto(Map<String, Object> params, PagingDto paging);

	ApplyCashDto getApplyCashDtoByOrderID(String orderId);

	JSONObject sendCheckCode(HttpServletRequest request);

	List<ApplyCashDto> getAgentApplyRecords(AuthUser sessionUser);

}
