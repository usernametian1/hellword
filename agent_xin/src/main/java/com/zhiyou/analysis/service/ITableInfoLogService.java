package com.zhiyou.analysis.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.analysis.dto.TableInfoLogDto;

public interface ITableInfoLogService {
		
	/**
	 * 插入桌牌信息
	 */
	boolean insertTable(TableInfoLogDto tableInfoLogDto);
	
	JSONObject insertTableInfoLog(Map<String, String> params);
	
	List<TableInfoLogDto> selectTableInfoLog(String day);
	
	List<TableInfoLogDto> selectDistTableInfo(String day);
}
