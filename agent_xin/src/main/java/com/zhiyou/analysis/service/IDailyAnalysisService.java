package com.zhiyou.analysis.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.analysis.dto.DailyAnalysisDto;
import com.zhiyou.core.tag.PagingDto;

public interface IDailyAnalysisService {

	JSONObject insertDailyAnalysisDto(Map<String, String> params);

	List<DailyAnalysisDto> getListDailyAnalysisDto(Map<String, Object> params, PagingDto paging);

}
