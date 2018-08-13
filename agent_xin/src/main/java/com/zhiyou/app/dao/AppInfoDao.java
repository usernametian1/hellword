package com.zhiyou.app.dao;

import java.util.List;
import java.util.Map;

import com.zhiyou.app.dto.AppInfoDto;


/**  
 * appInfo列表
 * @author tcr
 * @date 2017年4月27日  新建  
 */
public interface AppInfoDao {

	/**
	 * 根据主键查询对象
	 * @param id
	 */
	public AppInfoDto get(int id);
	
	/**
	 * 根据AppId查询对象
	 * @param userId
	 */
	public AppInfoDto getAppId(int appid);
	
	/**
	 * 保存对象
	 * @param e
	 * @return
	 */
	public int insert(AppInfoDto e);
	
	/**
	 * 更新对象
	 * @param e
	 */
	public int update(AppInfoDto e);
	
	/**
	 * 根据主键删除对象
	 * @param id
	 */
	public int delete(int appid);
	
	/**
	 * 查询列表(可条件可分页可排序)
	 * 分页参数:currentPage:当前的页码,pageSize:每页条数
	 * 排序参数:sortK,sortV
	 * @param params
	 * @return
	 */
	public List<AppInfoDto> findForPage(Map<String, Object> params);
	
	/**
	 * count表(可条件)
	 * @param params
	 * @return
	 */
	public int count(Map<String, Object> params);
	

}
