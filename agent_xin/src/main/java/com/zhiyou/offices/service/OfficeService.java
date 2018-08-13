package com.zhiyou.offices.service;
 
import java.util.List;
import java.util.Map;

import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.offices.bean.OfficeSpaceDto;

public interface OfficeService {

	public List<OfficeSpaceDto> FindOfficeSpace(Map<String ,Object> params,PagingDto pageing);

	public boolean insertOffice(Map<String, Object> params);
	
	
}
