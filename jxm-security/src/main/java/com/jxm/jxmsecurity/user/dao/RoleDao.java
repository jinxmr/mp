package com.jxm.jxmsecurity.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.user.domain.SysRole;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends BaseMapper<SysRole> {
}
