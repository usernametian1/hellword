package com.zhiyou.funcconfig.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.funcconfig.dao.FuncConfigDao;
import com.zhiyou.funcconfig.dto.FuncConfigDto;
import com.zhiyou.funcconfig.service.IFuncConfigService;

@Service ("funcConfigService")
public class FuncConfigServiceImpl  implements IFuncConfigService{
	
	@Autowired
	private FuncConfigDao funcConfigDao;
	
	@Override
	public boolean insertFuncConfig(FuncConfigDto funcConfigDto) {
		return funcConfigDao.insertFuncConfig(funcConfigDto);
	}

	@Override
	public boolean deleteFuncConfig(int appid ,  String funcName1) {
		String funcName = funcName1;
		return funcConfigDao.deleteFuncConfig(appid , funcName);
	}

	@Override
	public boolean updateFuncConfig(FuncConfigDto funcConfigDto) {
		
		 return funcConfigDao.updateFuncConfig(funcConfigDto);
	}

	@Override
	public List<FuncConfigDto> findForPage(Map<String, Object> params, PagingDto paging) {
		int totalRows = funcConfigDao.count(params);
	    paging.setTotalRows(totalRows);
		 params.put("startIndex", paging.getStartRowIndex());
	     params.put("pageSize", paging.getPageSize());
	     return funcConfigDao.findForPage(params);
	}
		/**
		 * 根据联合主键
		 * 查询 出对象
		 */
	@Override
	public FuncConfigDto selectFuncConfig(int appid, String funcName) {
		return funcConfigDao.getSynUrl(appid, funcName);
	}
		
}
