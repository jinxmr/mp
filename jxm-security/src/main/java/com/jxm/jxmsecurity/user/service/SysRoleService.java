package com.jxm.jxmsecurity.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxm.jxmsecurity.user.dao.RoleDao;
import com.jxm.jxmsecurity.user.dao.RoleMenuDao;
import com.jxm.jxmsecurity.user.dao.UserDao;
import com.jxm.jxmsecurity.user.domain.SysRole;
import com.jxm.jxmsecurity.user.domain.SysRoleMenu;
import com.jxm.jxmsecurity.user.domain.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysRoleService extends ServiceImpl<RoleDao, SysRole> {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleMenuDao roleMenuDao;

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
		return true;
	}
}
