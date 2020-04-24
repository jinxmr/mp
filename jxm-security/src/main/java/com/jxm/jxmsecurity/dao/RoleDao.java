package com.jxm.jxmsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.domain.SysRole;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends BaseMapper<SysRole> {
}
