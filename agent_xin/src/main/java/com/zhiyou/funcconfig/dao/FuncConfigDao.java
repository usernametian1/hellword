package com.zhiyou.funcconfig.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.app.dto.AppInfoDto;
import com.zhiyou.funcconfig.dto.FuncConfigDto;

public interface FuncConfigDao {
	
		/**
		 * 保存对象
		 * 
		 */
		public 	boolean insertFuncConfig(FuncConfigDto funcConfigDto);
		/**
		 * 更新对象
		 * 
		 */
		public boolean updateFuncConfig(FuncConfigDto funcConfigDto);
		/**
		 * 删除对象
		 * 
		 */
		public boolean deleteFuncConfig(@Param ("appid")int  appid , @Param("funcName") String funcNmae);
		
		/**
		 * 查询列表(可条件可分页可排序)
		 * 分页参数:currentPage:当前的页码,pageSize:每页条数
		 * 排序参数:sortK,sortV
		 * @param params
		 * @return
		 */
		public List<FuncConfigDto> findForPage(Map<String, Object> params);
		
		/**
		 * count(可条件)
		 * @param params
		 * @return
		 */
		public int count(Map<String, Object> params);
		
		/**
		 *联合查询
		 *得到接口地址
		 * @param appid , func
		 * @return
		 */
		public FuncConfigDto getSynUrl(@Param("appid") int appid,@Param("funcName") String funcName);
}
