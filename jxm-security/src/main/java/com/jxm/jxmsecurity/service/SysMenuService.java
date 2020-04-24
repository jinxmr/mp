package com.jxm.jxmsecurity.service;

import com.google.common.base.Splitter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxm.jxmsecurity.dao.MenuDao;
import com.jxm.jxmsecurity.dao.RoleMenuDao;
import com.jxm.jxmsecurity.domain.SysMenu;
import com.jxm.jxmsecurity.domain.SysUser;
import com.jxm.jxmsecurity.utils.TreeUtils;
import com.jxm.jxmsecurity.vo.ZTreeVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.MenuBarUI;

@Service
public class SysMenuService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private RoleMenuDao roleMenuDao;

	/**
	 * 根据用户ID查询权限
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	public Set<String> selectPermsByUserId(Long userId) {
		List<String> perms = menuDao.selectPermsByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtils.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim()
						.split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 根据用户查询菜单
	 *
	 * @param user 用户信息
	 * @return 菜单列表
	 */
	public List<SysMenu> selectMenusByUser(SysUser user) {
		List<SysMenu> menus = new LinkedList<SysMenu>();
		// 管理员显示所有菜单信息
		if (user.isAdmin()) {
			menus = menuDao.selectMenuNormalAll();
		} else {
			menus = menuDao.selectMenusByUserId(user.getId());
		}
		return TreeUtils.getChildPerms(menus, 0);
	}

	/**
	 * 根据ID查询
	 *
	 * @param id 菜单ID
	 */
	public SysMenu selectMenuById(Long id) {
		SysMenu sysMenu = menuDao.selectById(id);
		return sysMenu;
	}

	/**
	 * 列表信息
	 *
	 * @param menuName 菜单信息
	 */
	public Page<SysMenu> selectMenuList(String menuName, int pageNum, int pageSize) {
		Page<SysMenu> page = new Page<>(pageNum, pageSize);
		QueryWrapper queryWrapper = new QueryWrapper();
		if(StringUtils.isNotBlank(menuName)) {
			queryWrapper.like("menu_name", menuName);
		}
		Page<SysMenu> sysMenuPage = menuDao.selectPage(page, queryWrapper);
		return sysMenuPage;
	}

	/**
	 * 插入
	 *
	 * @param menu 菜单信息
	 */
	public int insertMenu(SysMenu menu) {
		int res = menuDao.insert(menu);
		return res;
	}

	/**
	 * 修改
	 *
	 * @param menu 菜单信息
	 */
	public int updateMenu(SysMenu menu) {
		int res = menuDao.updateById(menu);
		return res;
	}

	/**
	 * 删除
	 *
	 * @param ids 需要删除的数据ID
	 */
	public int deleteMenuByIds(String ids) {
		int res = 0;
		if (StringUtils.isNotBlank(ids)) {
			List<String> idList = new ArrayList<>(Splitter.on(" ")
					.splitToList(ids));
			res = menuDao.deleteBatchIds(idList);
		}
		return res;
	}

	/**
	 * 查询菜单树
	 *
	 * @return 菜单集合
	 */
	public List<ZTreeVO> selectMenuZTreeList(Long roleId) {
		List<SysMenu> menuList = menuDao.selectByRoleId(roleId);

		//处理成zTree树
		List<ZTreeVO> zTreeList = new ArrayList<>();
		ZTreeVO zTree = null;
		for (SysMenu menu : menuList) {
			zTree = new ZTreeVO();
			zTree.setId(menu.getId());
			zTree.setPId(menu.getParentId());
			zTree.setName(menu.getMenuName());
			if (null != menu.getRoleId()) {
				zTree.setChecked(true);
			}
			zTreeList.add(zTree);
		}
		return zTreeList;
	}
}
