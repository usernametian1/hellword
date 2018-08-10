package com.javen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javen.dao.LowerDeparentDao;
import com.javen.model.LowerDePartentDto;
import com.javen.service.ILowerParentService;

@Service("lowerPSvice")
public class LowerPServiceImpl implements ILowerParentService {

	@Autowired
	private  LowerDeparentDao lowerDepartDao;

	public List<LowerDePartentDto> findAllLower(int parentid) {
		// TODO Auto-generated method stub
		return lowerDepartDao.selectLowerDeparent(parentid);
	}
	

}
