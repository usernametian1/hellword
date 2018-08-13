package com.zhiyou.common.Enum;

/**
 * 校验数据
 * 
 * @author cY
 * @date 2017年2月23日
 */
public enum RetSatusAndMsg {

	OK(200, "OK"), DATA_CHECK_FAIL(400, "数据校验失败"), USER_NULL_ERROR(401, "玩家不存在"), DATA_DUPLICATIUON_ERROR(402, "重复提交数据"), DATA_CHECK_FAIL_IS_TOKEN(403, "数据校验失败，is_token"), DATA_CHECK_FAIL_IS_AGENT(
			404, "该玩家已是区域代理，不可绑定邀请码"), SYSTEM_ERROR(500, "系统异常"), ;
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
