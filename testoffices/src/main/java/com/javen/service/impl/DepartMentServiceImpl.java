package com.javen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javen.dao.DepartMentDao;
import com.javen.model.DePartMentDto;
import com.javen.service.IDepartMentServcie;

@Service("dePartServcie")
public class DepartMentServiceImpl implements IDepartMentServcie {

	
	@Autowired
	private DepartMentDao departDao;
	
	@Override
	public List<DePartMentDto> findAllDepart() {
		return departDao.findAll();
	}
 
}
