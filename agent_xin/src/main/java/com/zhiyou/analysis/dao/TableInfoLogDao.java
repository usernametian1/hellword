package com.zhiyou.analysis.dao;

import java.util.List;
import java.util.Map;





import com.zhiyou.analysis.dto.TableInfoLogDto;


public interface TableInfoLogDao {		
		/**
		 * 插入数据
		 */
	public boolean insertTable(TableInfoLogDto tableInfoLogDto);
	
	/**
	 * 查询,可配条件|分页
	 * 
	 * @param map
	 * @return
	 */
	List<TableInfoLogDto> getTableByCondition(Map<String, Object> map);

	int findTableCount(Map<String, Object> params);

	List<TableInfoLogDto> getTableByDay(String day);
	
	List<TableInfoLogDto> getDisTableByDay(String day);
 

}
