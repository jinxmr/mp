package com.jxm.jxmsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.domain.SysMenu;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends BaseMapper<SysMenu> {

	List<String> selectPermsByUserId(Long userId);

	/**
	 * 查询系统正常显示菜单（不含按钮）
	 *
	 * @return 菜单列表
	 */
	List<SysMenu> selectMenuNormalAll();

	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<SysMenu> selectMenusByUserId(Long userId);

	/**
	 * 根据角色ID关联查询菜单
	 */
	List<SysMenu> selectByRoleId(Long roleId);
}
