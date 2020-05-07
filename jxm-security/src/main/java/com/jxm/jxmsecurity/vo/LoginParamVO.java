package com.jxm.jxmsecurity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Accessors(chain = true)
@ApiModel("登录信息")
public class LoginParamVO implements UserDetails {

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "登录账号")
	private String loginName;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "工号")
	private String workNumber;

	@ApiModelProperty(value = "用户状态 0正常 1停用")
	private Integer status;

	@ApiModelProperty(value = "工号")
	private Collection<? extends GrantedAuthority> authorities;

	public LoginParamVO(Long userId, String loginName, String userName, String workNumber) {
		this.userId = userId;
		this.loginName = loginName;
		this.userName = userName;
		this.workNumber = workNumber;
	}

	public LoginParamVO(String loginName, String password, Integer status, Collection<? extends GrantedAuthority> authorities) {
		this.loginName = loginName;
		this.password = password;
		this.status = status;
		this.authorities = authorities;
	}

	public LoginParamVO() {
	}

	@Override
	public String getUsername() {
		return loginName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.status == 0 ? true : false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
