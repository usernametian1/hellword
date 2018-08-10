package com.javen.dao;

import java.util.List;
import java.util.Map;

import com.javen.model.OfficeSpaceDto;

public interface OfficeSpaceDao {

	
	List<OfficeSpaceDto> findOfficeSpace(Map<String,Object> params);
	
	int count(Map<String, Object> params);

	OfficeSpaceDto findOfficeSapce(Map<String, Object> params);
	
	int insert(OfficeSpaceDto office);

	int update(OfficeSpaceDto office);

	OfficeSpaceDto findByUserid(int userid);

	void deleteOffice(int userid);

	void doEditProtrait(Map<String ,Object> param);
	
	
}
