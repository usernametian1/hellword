package com.javen.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.javen.dao.OfficeSpaceDao;
import com.javen.model.OfficeSpaceDto;
import com.javen.service.IOfficeService;
import com.zhiyou.ContentEm;
import com.zhiyou.duobao.utils.config.WebConfig;
import com.zhiyou.duobao.utils.service.BService;
import com.zhiyou.utils.PagingDto;


@Service("officeService")
public class OfficeServiceImpl extends BService  implements IOfficeService{

	
	@Autowired
	private OfficeSpaceDao officeSpaceDao;
	
	@Override
	public List<OfficeSpaceDto> findForPage(Map<String, Object> params,PagingDto paging) {
		// TODO Auto-generated method stub
		int totalRows = officeSpaceDao.count(params);
		paging.setTotalRows(totalRows);
		params.put("startIndex", paging.getStartRowIndex());
		params.put("pageSize", paging.getPageSize());
		return officeSpaceDao.findOfficeSpace(params);
	}

	@Override
	public OfficeSpaceDto getOfficeSpace(Map<String, Object> params) {
		
		return officeSpaceDao.findOfficeSapce(params);
	}

	@Override
	public void insertSpace(OfficeSpaceDto office, MultipartFile iconFile) {
		officeSpaceDao.insert(office);
		String fileName = iconFile.getOriginalFilename();
		if (!StringUtils.isEmpty(fileName)) {
			String icon = ContentEm.STAFF_I0N + "/"+ office.getUserid() + fileName.substring(fileName.indexOf("."));
			this.doFileUpload(iconFile, icon);
			office.setIcon(icon);	
			int b = officeSpaceDao.update(office);
		}
		
	}

	@Override
	public OfficeSpaceDto findOfficeByid(int userid) {
		return officeSpaceDao.findByUserid(userid);
	}

	@Override
	public void doUpdate(OfficeSpaceDto office) {
		try {
			officeSpaceDao.update(office);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 protected void doDeleteOldFile(String oldFileUrl){
	    	try {
				if (!StringUtils.isEmpty(oldFileUrl)) {
					File file = new File(WebConfig.getImageUploadDir(), oldFileUrl);
					file.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

	@Override
	public void deleteOffice(int userid) {
          officeSpaceDao.deleteOffice(userid);		
	}

		
	@Override
	public void doEditPortrait(MultipartFile iconFile ,int userid) {
		Map<String ,Object> param = new HashMap<String ,Object>();
		param.put("userid", userid);
		String fileName = iconFile.getOriginalFilename();
		if(!StringUtils.isEmpty(fileName)){
			String icon = ContentEm.STAFF_I0N+"/"+userid+fileName.substring(fileName.indexOf("."));
//			this.doFileUpload(iconFile, icon);
//			office.setIcon(icon);	
//			int b = officeSpaceDao.update(office);
            this.doFileUpload(iconFile, icon);
            param.put("icon", icon);
            officeSpaceDao.doEditProtrait(param);
		}
	}


}
