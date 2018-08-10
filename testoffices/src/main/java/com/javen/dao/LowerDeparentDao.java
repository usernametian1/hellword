package com.javen.dao;

import java.util.List;

import com.javen.model.LowerDePartentDto;

public interface LowerDeparentDao {

	
	List<LowerDePartentDto> selectLowerDeparent(int parentid);
}
