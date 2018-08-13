package com.zhiyou.test.service;

import com.zhiyou.test.entity.Test;

public interface ITestService {
	Test findByID(int testID);

	int updateTest(Test test);

	int insertTest(Test test);

	int deleteByID(int testID);
}
