package com.javen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.javen.dao.OfficeBuildDao;
import com.javen.model.OfficeBuildDto;
import com.javen.service.IOfficeBuildService;
import com.zhiyou.ContentEm;
import com.zhiyou.duobao.utils.service.BService;
import com.zhiyou.utils.PagingDto;

@Service("buildService")
public class OfficeBuildServiceImpl extends BService implements IOfficeBuildService{

	@Autowired
	private OfficeBuildDao buildDao;
	
	@Override
	public List<OfficeBuildDto> findBuild(Map<String, Object> params, PagingDto paging) {
		// TODO Auto-generated method stub
				int totalRows = buildDao.count(params);
				paging.setTotalRows(totalRows);
				params.put("startIndex", paging.getStartRowIndex());
				params.put("pageSize", paging.getPageSize());
				return buildDao.findListBuild(params);
	}

	@Override
	public void insertBuild(OfficeBuildDto build, MultipartFile iconFile) {
		buildDao.insertBuild(build);
		String fileName = iconFile.getOriginalFilename();
		if (!StringUtils.isEmpty(fileName)) {
			String icon = ContentEm.STAFF_I0N + "/"+ build.getBuildid() + fileName.substring(fileName.indexOf("."));
			this.doFileUpload(iconFile, icon);
			build.setPositionicon(icon);	
			int b = buildDao.update(build);
		}
	}

	

	@Override
	public OfficeBuildDto findBuildByid(int buildid) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<OfficeBuildDto> buildList =  buildDao.findListBuild(params);
		if (buildList != null) {
			for(OfficeBuildDto build : buildList){
				return build;
			}
		}
		return null;
	}

	@Override
	public void doUpdate(OfficeBuildDto build) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBuild(int buildid) {
		buildDao.deleteBuild(buildid);
	}

	@Override
	public void doUpdateIcomFile(int buildid, MultipartFile iconFile) {
          OfficeBuildDto build = this.findBuildByid(buildid);
          String fileName = iconFile.getOriginalFilename();
  		if (!StringUtils.isEmpty(fileName)) {
  			String icon = ContentEm.STAFF_I0N + "/"+ build.getBuildid() + fileName.substring(fileName.indexOf("."));
  			this.doFileUpload(iconFile, icon);
  			build.setPositionicon(icon);	
  			int b = buildDao.update(build);
	}

  }

	@Override
	public List<OfficeBuildDto> findAll() {
		return buildDao.findAll() ;
	}

	
}
