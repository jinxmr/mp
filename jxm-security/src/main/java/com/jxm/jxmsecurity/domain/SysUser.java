package com.jxm.jxmsecurity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel("用户表")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一Id，后台生成", hidden = true)
	private Long id;

	@ApiModelProperty(value = "登录账号")
	private String loginName;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "员工编号", hidden = true)
	private String workNumber;

	@ApiModelProperty(value = "用户真实姓名")
	private String userName;

	@ApiModelProperty(value = "用户邮箱")
	private String email;

	@ApiModelProperty(value = "用户手机号")
	private String mobile;

	@ApiModelProperty(value = "用户头像")
	private String photo;

	@ApiModelProperty(value = "用户登录IP", hidden = true)
	private String loginIp;

	@ApiModelProperty(value = "用户登录日期", hidden = true)
	private Date loginTime;

	@ApiModelProperty(value = "创建日期", hidden = true)
	private Date createTime;

	@ApiModelProperty(value = "修改日期", hidden = true)
	private Date updateTime;

	@ApiModelProperty(value = "备注")
	private String remarks;

	@ApiModelProperty(value = "是否删除 0是1否")
	private Integer delFlag;

	@ApiModelProperty(value = "微信openId", hidden = true)
	private String wxOpenId;

	@ApiModelProperty(value = "用户状态")
	private Integer status;

	@ApiModelProperty(value = "用户性别 0男1女空未知")
	private Integer sex;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "角色组ID", hidden = true)
	@TableField(exist = false)
	private Long[] roleIds;

	/**
	 * 岗位组
	 */
	@ApiModelProperty(value = "岗位组ID", hidden = true)
	@TableField(exist = false)
	private Long[] postIds;

	public boolean isAdmin() {
		return isAdmin(this.id);
	}

	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

}