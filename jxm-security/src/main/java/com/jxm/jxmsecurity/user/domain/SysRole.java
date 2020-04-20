package com.jxm.jxmsecurity.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel("角色表")
public class SysRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一Id，后台生成", hidden = true)
	private Long id;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色关键字 唯一性")
	private String roleKey;

	@ApiModelProperty(value = "排序")
	private Integer roleSort;

	@ApiModelProperty(value = "数据范围 暂时没使用到")
	private String dataScope;

	@ApiModelProperty(value = "状态 0正常 1停用")
	private Integer status;

	@ApiModelProperty(value = "创建人", hidden = true)
	private String createBy;

	@ApiModelProperty(value = "创建时间", hidden = true)
	private Date createTime;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "是否被选中")
	@TableField(exist = false)
	private boolean checked;

	@ApiModelProperty(value = "菜单ID")
	@TableField(exist = false)
	private List<Long> menuIdList;

}