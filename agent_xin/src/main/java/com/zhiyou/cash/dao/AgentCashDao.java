package com.zhiyou.cash.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.cash.dto.ApplyCashDto;

public interface AgentCashDao {

	int insertCashApply(ApplyCashDto applyCashDto);

	List<ApplyCashDto> getCashApplyDto(@Param("appid") int appid, @Param("guid") int guid);

	int count(Map<String, Object> params);

	List<ApplyCashDto> findForPage(Map<String, Object> params);

	int updateCashApplyState(ApplyCashDto applyCashDto);

	ApplyCashDto getApplyCashDtoByOrderID(String orderId);

}
