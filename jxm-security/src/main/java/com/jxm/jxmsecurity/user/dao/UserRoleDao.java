package com.jxm.jxmsecurity.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.user.domain.SysMenu;
import com.jxm.jxmsecurity.user.domain.SysUserRole;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseMapper<SysUserRole> {
}
