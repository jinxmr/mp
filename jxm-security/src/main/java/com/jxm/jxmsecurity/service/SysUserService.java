package com.jxm.jxmsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxm.dto.LoginParam;
import com.jxm.jxmsecurity.dao.UserDao;
import com.jxm.jxmsecurity.domain.SysUser;
import com.jxm.model.AjaxResult;
import com.jxm.utils.TokenUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysUserService extends ServiceImpl<UserDao, SysUser> {

	static final String WORK_NUM = "M";

	@Autowired
	private UserDao userDao;

	public Page<SysUser> selectPage(String loginName, String workNumber, String mobile, Integer status, int pageNumber, int pageSize) {
		Page<SysUser> page = new Page<>(pageNumber, pageSize);
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
		if (StringUtils.isNotBlank(loginName)) {
			queryWrapper.like("login_name", loginName);
		}
		if (StringUtils.isNotBlank(workNumber)) {
			queryWrapper.like("work_number", workNumber);
		}
		if (StringUtils.isNotBlank(mobile)) {
			queryWrapper.like("mobile", mobile);
		}
		if (null != status) {
			queryWrapper.like("status", status);
		}

		page = userDao.selectPage(page, queryWrapper);
		return page;

	}

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

	/**
	 * 登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	public AjaxResult login(String loginName, String password) throws Exception {
		SysUser user = userDao.selectByLoginNameAndPassword(loginName, password);
		if(null != user) {
			if(user.getStatus() != 0) {
				return new AjaxResult(false, "该账号已停用");
			}
		} else {
			return new AjaxResult(false, "账号不存在");
		}
		//生成token
		LoginParam loginParam = new LoginParam(loginName, user.getUserName(), user.getWorkNumber());

		String token = TokenUtil.createJWT(loginParam);

		return new AjaxResult(false, "成功", token);
	}
}
