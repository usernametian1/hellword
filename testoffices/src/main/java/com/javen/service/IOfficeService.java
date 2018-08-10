package com.javen.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.javen.model.OfficeSpaceDto;
import com.zhiyou.utils.PagingDto;

public interface IOfficeService {
	
	public List<OfficeSpaceDto> findForPage(Map<String, Object> params,PagingDto paging);

	public OfficeSpaceDto getOfficeSpace(Map<String , Object> params);

	public void insertSpace(OfficeSpaceDto office, MultipartFile iconFile);

	public OfficeSpaceDto findOfficeByid(int userid);

	public void doUpdate(OfficeSpaceDto office);

	public void deleteOffice(int userid);

	public void doEditPortrait(MultipartFile iconFile,int userid);
	
}
