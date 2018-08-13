package com.zhiyou.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.user.dto.UserBindLog;
import com.zhiyou.user.dto.UserInfoDto;

public interface UserInfoDao {

	/**
	 * 根据guId,appid查询对象
	 * 
	 * @param guId
	 */
	public UserInfoDto getUserInfo(@Param("guid") int guid, @Param("appid") int appid);

	public UserInfoDto getUserInfoBySuperGuid(@Param("appid") int appid, @Param("guid") int guid, @Param("superGuid") int superGuid);

	/**
	 * 保存对象
	 * 
	 * @param e
	 * @return
	 */
	public int insert(UserInfoDto e);

	/**
	 * 更新对象
	 * 
	 * @param e
	 */
	public int update(UserInfoDto e);

	/**
	 * guid,appid更新用户充值金额
	 * 
	 * @param guid
	 * @return
	 */
	public int updateTotalFee(@Param("guid") int guid, @Param("appid") int appid, @Param("totalFee") int totalFee);

	/**
	 * 根据用户Id删除对象
	 * 
	 * @param
	 */
	public int delete(int guid, int appid);

	/**
	 * 查询列表(可条件可分页可排序) 分页参数:currentPage:当前的页码,pageSize:每页条数 排序参数:sortK,sortV
	 * 
	 * @param params
	 * @return
	 */
	public List<UserInfoDto> findForPage(Map<String, Object> params);

	/**
	 * count表(可条件)
	 * 
	 * @param params
	 * @return
	 */
	public int count(Map<String, Object> params);

	/**
	 * 今日绑定数
	 * 
	 * @param superGuid
	 * @return
	 */
	int countTodayBind(Map<String, Object> params);

	int countMothBind(Map<String, Object> params);

	int countAllBind(Map<String, Object> params);

	public List<UserInfoDto> getUsersBySuperGuid(@Param("appid") int appid, @Param("superGuid") String guid);

	/**
	 * 代理下绑定数
	 * 
	 * @param params
	 * @return
	 */
	int getDayBindNum(Map<String, Object> params);

	/**
	 * 绑定日志
	 * 
	 * @param userBindLog
	 * @return
	 */
	int insertBindLog(UserBindLog userBindLog);

}
