package com.jxm.jxmsecurity.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sys_role_menu")
@ApiModel("角色菜单中间表")
public class SysRoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@ApiModelProperty(value = "菜单ID")
	private Long menuId;
}