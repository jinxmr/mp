package com.jxm.jxmsecurity.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.user.domain.SysRoleMenu;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuDao extends BaseMapper<SysRoleMenu> {
}
