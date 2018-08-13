package com.zhiyou.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.app.dao.AppInfoDao;
import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.utils.MD5;
import com.zhiyou.utils.RandStrUtils;


@Service("appInfoService")
public class AppInfoServiceImpl implements IAppInfoService {
	@Autowired
	AppInfoDao appInfoDao;

	@Override
	public AppInfoDto findByID(int appid) {
		
		return appInfoDao.getAppId(appid);
	}

	@Override
	public int updateAppInfo(AppInfoDto appInfoDto) {
	    appInfoDto.setUpdateTime(new Date());
		return appInfoDao.update(appInfoDto);
	}

	@Override
	public int insertAppInfo(String appname) {
	    
        AppInfoDto appInfoDto = new AppInfoDto();
        appInfoDto.setAppkey(MD5.encodeUTF8(RandStrUtils.randAlphanumberic(32)).toLowerCase());
        appInfoDto.setAppname(appname);
        appInfoDto.setCreateTime(new Date());
        appInfoDto.setUpdateTime(new Date());
		return appInfoDao.insert(appInfoDto);
	}

	@Override
	public int deleteByID(int appid) {
		return appInfoDao.delete(appid);
	}

	@Override
	public List<AppInfoDto> findForPage(Map<String, Object> params,
			PagingDto paging) {
		int totalRows = appInfoDao.count(params);
        paging.setTotalRows(totalRows);
        params.put("startIndex", paging.getStartRowIndex());
        params.put("pageSize", paging.getPageSize());
		return appInfoDao.findForPage(params);
		
	}
}
