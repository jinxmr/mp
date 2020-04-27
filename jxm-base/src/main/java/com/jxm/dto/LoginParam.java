package com.jxm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("登录信息")
public class LoginParam {

	@ApiModelProperty(value = "用户ID")
	private String userId;

	@ApiModelProperty(value = "登录账号")
	private String loginName;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "工号")
	private String workNumber;

	public LoginParam(String loginName, String userName, String workNumber) {
		this.loginName = loginName;
		this.userName = userName;
		this.workNumber = workNumber;
	}

	public LoginParam() {
	}
}
