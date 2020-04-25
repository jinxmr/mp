package com.jxm.jxmsecurity.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxm.model.AjaxResult;
import com.jxm.jxmsecurity.domain.SysUser;
import com.jxm.jxmsecurity.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("malls/user/")
@Api(tags = "用户相关接口")
public class SysUserRest {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("list")
	@ApiOperation("用户列表")
	@ApiImplicitParams({@ApiImplicitParam(name = "pageNumber", value = "页数", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "pageSize", value = "条数", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "loginName", value = "登录账号", dataType = "String", paramType = "query"), @ApiImplicitParam(name = "workNumber", value = "工号", dataType = "String", paramType = "query"), @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", paramType = "query"), @ApiImplicitParam(name = "status", value = "用户状态0启用 1停用", dataType = "int", paramType = "query")})
	public Page<SysUser> list(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam(required = false) String loginName, @RequestParam(required = false) String workNumber, @RequestParam(required = false) String mobile, @RequestParam(required = false) Integer status) {
		Page<SysUser> page = sysUserService.selectPage(loginName, workNumber, mobile, status, pageNumber, pageSize);
		return page;
	}

	@PostMapping("insertUser")
	@ApiOperation("添加用户")
	public AjaxResult insertUser(@RequestBody @ApiParam SysUser user) {
		boolean ret = sysUserService.save(user);
		return new AjaxResult(ret, ret ? "成功" : "失败");
	}

	@PostMapping("updateUser")
	@ApiOperation("修改用户")
	public AjaxResult updateUser(@RequestBody @ApiParam SysUser user) {
		boolean ret = sysUserService.updateById(user);
		return new AjaxResult(ret, ret ? "成功" : "失败");
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
		return new AjaxResult(true, ret ? "成功" : "失败");
	}

	@PostMapping("login")
	@ApiOperation("用户登录")
	public AjaxResult login(@RequestParam String loginName, @RequestParam String password) {
		AjaxResult ret = sysUserService.login(loginName, password);
		return ret;
	}

}
