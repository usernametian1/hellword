package com.zhiyou.common.constance;

/**
 * Agent申请状态:1=提交审核；2=审核通过；3=驳回 ; State状态:0=正常;1=冻结
 * 
 * @author Administrator
 *
 */
public class AgentConstance {
	// Agent申请状态:1=提交审核；2=审核通过；3=驳回
	public static final int SUBMIT_STATUS = 1;
	//public static final int remark = 2;
	public static final int REFUSE_STATUS = 3;
	public static final int APPLY_PASS_STATUS =2;
	
	// State状态:0=正常;1=冻结
	public static final int NORMAL_STATE = 0;
	public static final int FROZEN_STATE = 1;

	// State状态:0=未同步;1=已同步
	public static final int NOT_SYN = 0;
	public static final int SYN_OK = 1;
	// 提现申请状态
	public static final int CASH_APPLY_SUBMIT = 0;
	public static final int CASH_APPLY_PASS = 1;
	public static final int CASH_APPLY_REFUSE = 2;
}
