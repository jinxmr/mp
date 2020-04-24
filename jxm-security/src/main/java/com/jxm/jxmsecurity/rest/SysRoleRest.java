package com.jxm.jxmsecurity.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxm.jxmsecurity.domain.SysRoleMenu;
import com.jxm.model.AjaxResult;
import com.jxm.jxmsecurity.domain.SysRole;
import com.jxm.jxmsecurity.domain.SysUser;
import com.jxm.jxmsecurity.service.SysRoleService;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("malls/role/")
@Api(tags = "角色相关接口")
public class SysRoleRest {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("list")
	@ApiOperation("查询角色列表")
	public Page<SysRole> list(@RequestParam(required = false) String roleName,
										  @RequestParam int pageNumber,
										  @RequestParam int pageSize) {
		Page<SysRole> rolePage = sysRoleService.selectListPage(roleName, pageNumber, pageSize);
		return rolePage;
	}

	@PostMapping("insert")
	@ApiOperation("添加角色")
	public AjaxResult insertUser(@RequestBody @ApiParam SysRole role) {
		boolean ret = sysRoleService.save(role);
		return new AjaxResult(ret, ret ? "成功": "失败");
	}

	@PostMapping("updateById")
	@ApiOperation("修改角色")
	public AjaxResult updateUser(@RequestBody @ApiParam SysRole role) {
		boolean ret = sysRoleService.updateById(role);
		return new AjaxResult(ret, ret ? "成功": "失败");
	}

	@PostMapping("findById")
	@ApiOperation("根据ID查询角色")
	public AjaxResult<SysUser> findById(Long id) {
		SysRole role = sysRoleService.getById(id);
		return new AjaxResult(true, "成功", role);
	}

	@PostMapping("deleteById")
	@ApiOperation("根据ID删除角色")
	public AjaxResult<SysUser> deleteById(Long id) {
		boolean ret = sysRoleService.removeById(id);
		return new AjaxResult(true, ret ? "成功": "失败");
	}
}
