package com.jxm.jxmsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxm.jxmsecurity.dao.UserDao;
import com.jxm.jxmsecurity.domain.SysUser;
import com.jxm.jxmsecurity.security.WebSecurityConfig;
import com.jxm.jxmsecurity.vo.LoginParamVO;
import com.jxm.model.AjaxResult;
import com.jxm.jxmsecurity.utils.TokenUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SysUserService extends ServiceImpl<UserDao, SysUser> implements UserDetailsService {

	static final String WORK_NUM = "M";

	@Autowired
	private UserDao userDao;

	@Autowired
	private WebSecurityConfig webSecurityConfig;

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
	 * 自定义用户认证
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		String password = "000000";
		password = webSecurityConfig.bCryptPasswordEncoder().encode(password);
		return new LoginParamVO(userName, password, 0, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
