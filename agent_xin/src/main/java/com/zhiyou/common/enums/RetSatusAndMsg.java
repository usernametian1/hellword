package com.zhiyou.common.enums;

/**
 * 校验数据
 * 
 * @author cY
 * @date 2017年2月23日
 */
public enum RetSatusAndMsg {

	OK(200, "OK"),
	DATA_CHECK_FAIL(400, "数据校验失败"),
	USER_NULL_ERROR(401, "玩家不存在"), 
	DATA_DUPLICATIUON_ERROR(402, "重复提交数据"),
	DATA_CHECK_FAIL_IS_TOKEN(403, "数据校验失败，is_token"),
	DATA_CHECK_FAIL_IS_AGENT(404, "该玩家已是区域代理，不可绑定邀请码"), 
	PARAMETER_ERROR(405, "参数不正确"), 
	AGENT_NOT_FOUND(406, "绑定的代理不存在"),
	FORBIDDEN_BIND_SELF(407, "不能自绑"), 
	FORBIDDEN_BIND_RELATED(408, "不能互绑"), 
	DUPLICATIUON_BIND_ERROR(409, "不能重复绑定"),
	APPID_ERROR(410, "APPID错误"), 
	UID_ERROR(411, "UID错误"), 
	ORDER_ERROR(412, "订单已同步"), 
	CASH_PWD_ERROR(413, "提现密码错误"), 
	CASH_TOTALFEE_ERROR(414,"提现金额错误"),
	CASH_FREEZON(415,"账户已冻结"),
	CASH_APPLY_SUCCESS(416,"申请提现成功"),
	CASH_APPLY_ORDER_INVALID(417,"申请提现订单无效"),
	CASH_APPLY_CHECK_PASS(418,"审核成功"),
	CASH_APPLY_MOBILE_ERROR(419,"手机号码不一致"),

	CHECK_CODE_SEND_SUCCESS(420,"验证码发送成功"),
	INPUT_CHECK_CODE__ERROR(421,"输入验证码错误"),
	CASH_NOT_FOUND_OPENID(422,"未绑定微信,请在<我的-个人信息 >绑定"),
	SYSTEM_ERROR(500, "系统异常"), ;
    
	private String name;
	private int value;

	private RetSatusAndMsg(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
