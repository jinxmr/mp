package com.jxm.jxmsecurity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel("角色菜单表")
public class SysMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID 自增", hidden = true)
	private Long id;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "父级菜单ID")
	private Long parentId;

	@ApiModelProperty(value = "排序")
	private Integer orderSort;

	@ApiModelProperty(value = "菜单链接")
	private String href;

	@ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
	private String menuType;

	@ApiModelProperty(value = "菜单状态 0显示 1隐藏")
	private Integer visible;

	@ApiModelProperty(value = "权限标识")
	private String perms;

	@ApiModelProperty(value = "菜单图标")
	private String icon;

	@ApiModelProperty(value = "创建人", hidden = true)
	private String createBy;

	@ApiModelProperty(value = "创建时间", hidden = true)
	private Date createTime;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "关联APPID 暂时没用到")
	private Long appId;

	@ApiModelProperty(value = "角色ID")
	@TableField(exist = false)
	private Long roleId;

	@ApiModelProperty(value = "子菜单集合")
	@TableField(exist = false)
	private List<SysMenu> children = new ArrayList<>();
}