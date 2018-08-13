package com.zhiyou.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhiyou.order.dto.OrderInfoDTO;

public interface OrderInfoDao {

	/**
	 * 根据orderId查询对象
	 * 
	 * @param id
	 */
	public OrderInfoDTO getByOrderId(@Param("appid") int appid, @Param("orderId") String orderId);

	/**
	 * 保存对象
	 * 
	 * @param e
	 * @return
	 */
	public int insert(OrderInfoDTO e);

	/**
	 * 查询列表(可条件可分页可排序) 分页参数:currentPage:当前的页码,pageSize:每页条数 排序参数:sortK,sortV
	 * 
	 * @param params
	 * @return
	 */
	public List<OrderInfoDTO> findForPage(Map<String, Object> params);

	/**
	 * count表(可条件)
	 * 
	 * @param params
	 * @return
	 */
	public int count(Map<String, Object> params);

	/**
	 * 订单日统计
	 * 
	 * 
	 * @param params
	 * @return
	 */
	public List<OrderInfoDTO> findTotal(Map<String, Object> params);

	public int findTotalCount(Map<String, Object> params);

	/**
	 * 根据日期查询订单
	 * 
	 * 
	 * @param day
	 * @return
	 */
	public List<OrderInfoDTO> findOrderByDay(Map<String, Object> params);

	/**
	 * 根据月份查询某月的所有订单
	 * 
	 * 
	 * @param params
	 * @return
	 */
	public List<OrderInfoDTO> findOrderByMonth(Map<String, Object> params);

	/**
	 * appid某天总充值数
	 * 
	 * @param appid
	 * @param day
	 * @return
	 */
	public int countTotalFeeByDay(@Param("appid") int appid, @Param("day") Date day);

	/**
	 * 统计今日代理下充值订单
	 * 
	 * @param params
	 * @return
	 */
	int countTodayTotalFee(Map<String, Object> params);

	/**
	 * 统计今当前月代理下充值订单
	 * 
	 * @param params
	 * @return
	 */
	int countMothTotalFee(Map<String, Object> params);

	/**
	 * 代理下充值总额
	 * 
	 * @param appid
	 * @param guid
	 * @return
	 */
	int countAllTotalFee(Map<String, Object> params);

	public List<OrderInfoDTO> getOrdersBySuperGuid(@Param("appid") int appid, @Param("guid") String guid);

	public List<OrderInfoDTO> getOrdersByGuidAndSuper(@Param("appid") int appid, @Param("guid") int guid, @Param("superGuid") int superGuid);

}
