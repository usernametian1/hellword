package com.zhiyou.test.dao;

import com.zhiyou.test.entity.Test;

public interface TestDao {
	Test findByID(int testID);

	int updateTest(Test test);

	int insertTest(Test test);

	int deleteByID(int testID);

}
