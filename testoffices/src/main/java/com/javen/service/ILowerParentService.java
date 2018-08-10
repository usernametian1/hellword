package com.javen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javen.model.LowerDePartentDto;

public interface ILowerParentService {
	
	 public List<LowerDePartentDto> findAllLower(int parentid);

}
