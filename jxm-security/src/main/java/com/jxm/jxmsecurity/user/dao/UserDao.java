package com.jxm.jxmsecurity.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.user.domain.SysUser;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<SysUser> {

	@Select(value = "select max(work_number) from sys_user")
	String findMaxIdData();
}
