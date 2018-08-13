package com.zhiyou.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyou.test.dao.TestDao;
import com.zhiyou.test.entity.Test;
import com.zhiyou.test.service.ITestService;

@Service("testService")
public class TestServiceImpl implements ITestService {
	@Autowired
	TestDao testDao;

	@Override
	public Test findByID(int testID) {
		return testDao.findByID(testID);
	}

	@Override
	public int updateTest(Test test) {
		return testDao.updateTest(test);
	}

	@Override
	public int insertTest(Test test) {
		return testDao.insertTest(test);
	}

	@Override
	public int deleteByID(int testID) {
		return testDao.deleteByID(testID);
	}

}
