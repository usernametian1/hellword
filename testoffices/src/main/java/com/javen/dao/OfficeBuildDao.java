package com.javen.dao;

import java.util.List;
import java.util.Map;

import com.javen.model.OfficeBuildDto;

public interface OfficeBuildDao {
      
	void insertBuild(OfficeBuildDto build);
	
	List<OfficeBuildDto> findListBuild(Map<String,Object> params); 
	
	int count(Map<String,Object> params);
	
	int deleteBuild(int buildid);

	int update(OfficeBuildDto build);

	List<OfficeBuildDto> findAll();

	OfficeBuildDto findBuildById(int buildid);
}
