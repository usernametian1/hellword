package com.zhiyou.analysis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiyou.analysis.dao.TableInfoLogDao;
import com.zhiyou.analysis.dto.TableInfoLogDto;
import com.zhiyou.analysis.service.ITableInfoLogService;
import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.app.service.IAppInfoService;
import com.zhiyou.common.enums.RetSatusAndMsg;
import com.zhiyou.utils.DateUtils;
import com.zhiyou.utils.SignVerify;
import com.zhiyou.utils.StringUtils;

@Service("tableInfoLogService")
public class TableInfoLogServiceImpl implements ITableInfoLogService{
		
	private static final Logger log = Logger.getLogger(TableInfoLogServiceImpl.class);

	@Autowired
	private TableInfoLogDao tableInfoLogDao;
	
	@Autowired
	private IAppInfoService appInfoService;
	@Override
	public boolean insertTable(TableInfoLogDto tableInfoLogDto) {	
		return tableInfoLogDao.insertTable(tableInfoLogDto);
	}

	@Override
	public JSONObject insertTableInfoLog(Map<String, String> params) {
		JSONObject jsonObject = new JSONObject();
		String appidStr = params.get("appid");
		int tableId = 0;
		int roundNum = 0;
		String data = params.get("data");
		if  (StringUtils.isEmpty(data)) {
			jsonObject.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
			jsonObject.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
			return jsonObject;
		}
		JSONArray jsonarray = JSON.parseArray(data);
		int appid = 0;
		try{
			tableId = Integer.parseInt(params.get("tableId"));
			roundNum = Integer.parseInt(params.get("roundNum"));
			appid = Integer.valueOf(appidStr);
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
			jsonObject.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
			return jsonObject;
		}
		
		AppInfoDto appInfoDto =  appInfoService.findByID(appid);
		if (appInfoDto == null || !SignVerify.verify(params, appInfoDto.getAppkey())) { // 签名校验错误返回
			jsonObject.put("code", RetSatusAndMsg.DATA_CHECK_FAIL.getValue());
			jsonObject.put("msg", RetSatusAndMsg.DATA_CHECK_FAIL.getName());
			return jsonObject;
		}
		
		
		List<Integer> tmp = new ArrayList<Integer>();
		for (int index = 0; index < jsonarray.size(); index ++) {
			JSONObject json = jsonarray.getJSONObject(index);
			int guid = json.getIntValue("guid");
			int superGuid = json.getIntValue("superGuid");
			int useDiamond =json.getIntValue("useDiamond");
			if (tmp.contains(guid)) {
				jsonObject.put("code", RetSatusAndMsg.PARAMETER_ERROR.getValue());
				jsonObject.put("msg", RetSatusAndMsg.PARAMETER_ERROR.getName());
				return jsonObject;
			}
			tmp.add(guid);
			TableInfoLogDto tableInfoLogDto = new TableInfoLogDto();
			tableInfoLogDto.setAppid(appid);
			tableInfoLogDto.setTableId(tableId);
			tableInfoLogDto.setRoundNum(roundNum);
			tableInfoLogDto.setGuid(guid);
			tableInfoLogDto.setSuperGuid(superGuid);
			tableInfoLogDto.setUseDiamond(useDiamond);
			tableInfoLogDto.setDay(DateUtils.formatSimpleDate(new Date()));
			tableInfoLogDao.insertTable(tableInfoLogDto);
		}
		
		jsonObject.put("code", RetSatusAndMsg.OK.getValue());
		jsonObject.put("msg", RetSatusAndMsg.OK.getName());
		return jsonObject;
	}

	@Override
	public List<TableInfoLogDto> selectTableInfoLog(String day) {
		
		return tableInfoLogDao.getTableByDay(day);
	}

	@Override
	public List<TableInfoLogDto> selectDistTableInfo(String day) {

		return tableInfoLogDao.getDisTableByDay(day);
	}


}
