package com.zhiyou.offices.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.offices.bean.OfficeSpaceDto;
import com.zhiyou.offices.dao.OfficeSpaceDao;
import com.zhiyou.offices.service.OfficeService;


@Service("officeService")
public class officeServiceImpl implements OfficeService {

	@Autowired
	private OfficeSpaceDao officeSpaceDao;
	 
	@Override
	public List<OfficeSpaceDto> FindOfficeSpace(Map<String, Object> params, PagingDto paging){
		int totalRows = officeSpaceDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return officeSpaceDao.findOfficeSpace(params);
	}

	@Override
	public boolean insertOffice(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return false;
	}

}
