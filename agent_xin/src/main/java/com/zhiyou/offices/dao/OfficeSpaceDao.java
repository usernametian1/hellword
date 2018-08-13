package com.zhiyou.offices.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhiyou.offices.bean.OfficeSpaceDto;

@Repository
public interface OfficeSpaceDao {

	public int insert(OfficeSpaceDto officeSpace);

	public int count(Map<String , Object> params);
	
	public List<OfficeSpaceDto> findOfficeSpace(Map<String , Object> params);
}
