package com.jxm.jxmsecurity.user.rest;

import com.jxm.base.model.AjaxResult;
import com.jxm.jxmsecurity.user.domain.SysUser;
import com.jxm.jxmsecurity.user.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("malls/user/")
@Api(tags = "用户相关接口")
public class SysUserRest {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("findAll")
	@ApiOperation("查询全部用户")
	public AjaxResult<List<SysUser>> findAll() {
		List<SysUser> userAll = sysUserService.list();
		return new AjaxResult(true, "成功", userAll);
	}

	@PostMapping("insertUser")
	@ApiOperation("添加用户")
	public AjaxResult insertUser(@RequestBody @ApiParam SysUser user) {
		boolean ret = sysUserService.save(user);
		return new AjaxResult(ret, ret ? "成功": "失败");
	}

	@PostMapping("updateUser")
	@ApiOperation("修改用户")
	public AjaxResult updateUser(@RequestBody @ApiParam SysUser user) {
		boolean ret = sysUserService.updateById(user);
		return new AjaxResult(ret, ret ? "成功": "失败");
	}

	@PostMapping("findById")
	@ApiOperation("根据ID查询用户")
	public AjaxResult<SysUser> findById(Long id) {
		SysUser user = sysUserService.getById(id);
		return new AjaxResult(true, "成功", user);
	}

	@PostMapping("deleteById")
	@ApiOperation("根据ID删除用户")
	public AjaxResult<SysUser> deleteById(Long id) {
		boolean ret = sysUserService.removeById(id);
		return new AjaxResult(true, ret ? "成功": "失败");
	}
}
