package com.jxm.jxmsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.domain.SysUserRole;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseMapper<SysUserRole> {
}
