package com.zhiyou.app.service;

import java.util.List;
import java.util.Map;

import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.core.tag.PagingDto;




public interface IAppInfoService {
	AppInfoDto findByID(int appid);

	int updateAppInfo(AppInfoDto appInfoDto);

	int insertAppInfo(String appname);

	int deleteByID(int appid);
	
    List<AppInfoDto> findForPage(Map<String, Object> params, PagingDto paging);

}
