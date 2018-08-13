package com.zhiyou.user.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.user.dto.UserBindLog;
import com.zhiyou.user.dto.UserInfoDto;

public interface IUserInfoService {

	UserInfoDto getUserInfo(int guid, int appid);

	/**
	 * 修改列表(可条件可分页可排序)
	 * 
	 * @param guid
	 * @return
	 */
	int updateUserInfo(UserInfoDto userInfoDto);

	/**
	 * 插入列表(可条件可分页可排序)
	 * 
	 * @param guid
	 * @return
	 */
	int insertUserInfo(UserInfoDto userInfoDto);

	int updateTotalFee(int guid, int appid, int totalFee);

	/**
	 * 删除列表(可条件可分页可排序)
	 * 
	 * @param guid
	 * @return
	 */
	int deleteUserInfo(int guid, int appid);

	/**
	 * 查询列表(可条件可分页可排序) 分页参数:currentPage:当前的页码,pageSize:每页条数 排序参数:sortK,sortV
	 * 
	 * @param params
	 * @return
	 */
	List<UserInfoDto> findForPage(Map<String, Object> params, PagingDto paging);

	/**
	 * save同步来的玩家信息
	 * 
	 * @param params
	 */
	JSONObject insertSynGuser(Map<String, String> params);

	List<UserInfoDto> getUsersBySuperGuid(AuthUser sessionUser);

	int countTodayBind(Map<String, Object> params);

	int countMothBind(Map<String, Object> params);

	int countAllBind(Map<String, Object> params);

	/**
	 * 代理下绑定数
	 * 
	 * @param params
	 * @return
	 */
	int getDayBindNum(AuthUser sessionUser, int level);

	/**
	 * 绑定日志
	 * 
	 * @param userBindLog
	 * @return
	 */
	int insertBindLog(UserBindLog userBindLog);

	UserInfoDto getUserInfoBySuperGuid(int appid, int guid, int superGuid);

	/**
	 * 更新玩家绑定信息
	 * 
	 * @param params
	 * @return
	 */
	// JSONObject updatesynGuser(Map<String, Object> params);

}
