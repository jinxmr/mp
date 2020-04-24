package com.jxm.jxmsecurity.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxm.model.AjaxResult;
import com.jxm.jxmsecurity.domain.SysMenu;
import com.jxm.jxmsecurity.service.SysMenuService;
import com.jxm.jxmsecurity.vo.ZTreeVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 菜单 信息操作处理
 *
 * @author drm
 * @date 2019-09-02
 */
@RestController
@RequestMapping("malls/menu")
@Api(tags = "菜单相关接口")
public class MenuRest {
	private String prefix = "system/menu";

	@Autowired
	private SysMenuService menuService;

	@GetMapping()
	public String menu() {
		return prefix + "/menu";
	}

	/**
	 * 查询菜单列表
	 */
	@PostMapping("/list")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNumber", value = "页数", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "条数", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "menuName", value = "菜单名称", dataType = "String",paramType = "query")})
	public Page<SysMenu> list(@RequestParam(required = false) String menuName, @RequestParam int pageNumber,
							  @RequestParam int pageSize) {

		Page<SysMenu> page = menuService.selectMenuList(menuName, pageNumber, pageSize);
		return page;
	}

	/**
	 * 新增保存菜单
	 */
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SysMenu menu) {
		int ret = menuService.insertMenu(menu);
		return new AjaxResult(ret > 0 ? true : false, ret > 0 ? "成功" : "失败");
	}

	/**
	 * 修改保存菜单
	 */
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SysMenu menu) {
		int ret = menuService.updateMenu(menu);
		return new AjaxResult(ret > 0 ? true : false, ret > 0 ? "成功" : "失败");
	}

	/**
	 * 删除菜单
	 */
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		int ret = menuService.deleteMenuByIds(ids);
		return new AjaxResult(ret > 0 ? true : false, ret > 0 ? "成功" : "失败");
	}


	/**
	 * 查询菜单树结构
	 *
	 * @return 返回zTree
	 */
	@GetMapping("zTreeList")
	@ResponseBody
	public AjaxResult zTreeList(Long roleId) {

		List<ZTreeVO> zTreeList = menuService.selectMenuZTreeList(roleId);

		return new AjaxResult(true, "成功", zTreeList);
	}
}
