package com.javen.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.javen.model.OfficeBuildDto;
import com.zhiyou.utils.PagingDto;

public interface IOfficeBuildService {

	public List<OfficeBuildDto> findBuild(Map<String, Object> params,PagingDto paging);

	public void insertBuild(OfficeBuildDto build, MultipartFile iconFile);

	public OfficeBuildDto findBuildByid(int buildid);

	public void doUpdate(OfficeBuildDto build);

	public void deleteBuild(int buildid);

	public void doUpdateIcomFile(int buildid, MultipartFile iconFile);

	public List<OfficeBuildDto> findAll();

}
