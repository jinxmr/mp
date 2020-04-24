package com.jxm.jxmsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxm.jxmsecurity.dao.RoleDao;
import com.jxm.jxmsecurity.dao.RoleMenuDao;
import com.jxm.jxmsecurity.domain.SysRole;
import com.jxm.jxmsecurity.domain.SysRoleMenu;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysRoleService extends ServiceImpl<RoleDao, SysRole> {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleMenuDao roleMenuDao;

	public Page<SysRole> selectListPage (String roleName, int pageNumber, int pageSize) {
		Page<SysRole> page = new Page<>(pageNumber, pageSize);
		QueryWrapper queryWrapper = new QueryWrapper();
		if(StringUtils.isNoneBlank(roleName)) {
			queryWrapper.like("role_name", roleName);
		}
		page = roleDao.selectPage(page, queryWrapper);
		return page;
	}

	@Override
	public boolean save(SysRole entity) {
		//保存角色基本信息以及绑定菜单信息
		int retRole = roleDao.insert(entity);
		List<Long> menuIdList = entity.getMenuIdList();
		if(null != menuIdList && menuIdList.size() > 0) {
			//首先删除menuRole表的关联数据
			Map<String, Object> conditionMap = new HashMap<>(1);
			conditionMap.put("roleId", entity.getId());
			roleMenuDao.deleteByMap(conditionMap);
			List<SysRoleMenu> roleMenuList = new ArrayList<>();
			for (Long menuId : menuIdList) {
				SysRoleMenu rm = new SysRoleMenu();
				rm.setMenuId(menuId)
						.setRoleId(entity.getId());
				roleMenuList.add(rm);
				roleMenuDao.insert(rm);
			}
		}
		return retRole > 0 ? true : false;
	}
}
