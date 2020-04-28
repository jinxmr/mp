package com.jxm.jxmsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxm.jxmsecurity.domain.SysUser;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<SysUser> {

	@Select(value = "select max(work_number) from sys_user")
	String findMaxIdData();

	@Select(value = "select id,create_time,del_flag,email,login_time,login_ip," +
			"login_name,mobile,photo,remarks,sex,status,update_time,user_name," +
			"work_number,wx_open_id from sys_user where login_name=#{loginName}" +
			"and password=#{password}")
	SysUser selectByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);
}
