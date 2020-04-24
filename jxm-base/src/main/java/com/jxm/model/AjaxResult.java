package com.jxm.model;

import java.util.HashMap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作消息提醒
 *
 * @author school
 */
@ApiModel(value = "返回信息", description = "返回信息")
@Data
public class AjaxResult<T> {

	@ApiModelProperty(value = "状态码")
	private Integer code;

	@ApiModelProperty(value = "消息")
	private String msg;

	@ApiModelProperty(value = "返回数据总数")
	private Integer total;

	@ApiModelProperty(value = "是否成功状态码", example = "true")
	private boolean success;

	@ApiModelProperty(value = "数据信息")
	private T data;

	public AjaxResult() {
	}

	public AjaxResult(boolean success, Integer code, String msg, Integer total, T data) {
		this.code = code;
		this.msg = msg;
		this.total = total;
		this.success = success;
		this.data = data;
	}

	public AjaxResult(boolean success, Integer code, String msg, Integer total) {
		this.code = code;
		this.msg = msg;
		this.total = total;
		this.success = success;
	}

	public AjaxResult(boolean success, Integer code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public AjaxResult(boolean success,String msg, Integer total,  T data) {
		this.msg = msg;
		this.total = total;
		this.success = success;
		this.data = data;
	}

	public AjaxResult(boolean success, String msg, T data) {
		this.msg = msg;
		this.success = success;
		this.data = data;
	}

	public AjaxResult(boolean success, String msg) {
		this.msg = msg;
		this.success = success;
		this.data = null;
	}
}
