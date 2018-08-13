package com.zhiyou.funcconfig.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.funcconfig.dto.FuncConfigDto;

public interface IFuncConfigService {
			public boolean insertFuncConfig(FuncConfigDto funcConfigDto);
			public boolean deleteFuncConfig(int appid ,  String func);
			public boolean updateFuncConfig(FuncConfigDto funcConfigDto);
			public FuncConfigDto selectFuncConfig(int appid, String funcName1);
			public List<FuncConfigDto> findForPage(Map<String, Object> params, PagingDto paging);
}
