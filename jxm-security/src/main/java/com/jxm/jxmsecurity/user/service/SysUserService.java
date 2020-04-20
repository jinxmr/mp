package com.jxm.jxmsecurity.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxm.base.utils.JpaUtil;
import com.jxm.jxmsecurity.user.dao.UserDao;
import com.jxm.jxmsecurity.user.domain.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class SysUserService extends ServiceImpl<UserDao, SysUser> {

	static final String WORK_NUM = "M";

	@Autowired
	private UserDao userDao;

	public boolean save(SysUser user) {
		//获取workNum
		String maxWorkNum = userDao.findMaxIdData();
		if (null != maxWorkNum) {
			String[] numbers = maxWorkNum.split("M");
			Long number = Long.valueOf(numbers[1]);
			user.setWorkNumber(WORK_NUM + String.format("%05d", (number + 1)));
		} else {
			user.setWorkNumber(WORK_NUM + String.format("%05d", 1));
		}
		int ret = userDao.insert(user);
		return ret > 0 ? true : false;
	}
}
