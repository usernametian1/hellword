package com.zhiyou.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.auth.dto.AuthUser;
import com.zhiyou.core.tag.PagingDto;
import com.zhiyou.order.dto.OrderInfoDTO;

public interface IOrderInfoService {

	/**
	 * 根据orderId查询对象
	 * 
	 * @param id
	 */
	public OrderInfoDTO getOrderId(int appid, String orderId);

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
	public List<OrderInfoDTO> findForPage(Map<String, Object> params, PagingDto paging);

	int countTotalFeeByDay(int appid, Date day);

	/**
	 * 订单日统计
	 * 
	 * @author cY
	 * @param params
	 * @param paging
	 * @return
	 */
	public List<OrderInfoDTO> findTotal(Map<String, Object> params, PagingDto paging);

	/**
	 * 对接游戏充值订单
	 * 
	 * @author cY
	 * @param orderInfo
	 * @param request
	 * @return
	 */
	public JSONObject insertSynOrderInfo(Map<String, String> params);

	public List<OrderInfoDTO> getOrdersBySuperGuid(AuthUser sessionUser);

	int countTodayTotalFee(Map<String, Object> params);

	int countMothTotalFee(Map<String, Object> params);

	int countAllTotalFee(Map<String, Object> params);

	public List<OrderInfoDTO> getOrdersByGuidAndSuper(int appid, int guid, int superGuid);
}
