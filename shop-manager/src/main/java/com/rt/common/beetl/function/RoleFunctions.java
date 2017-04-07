package com.rt.common.beetl.function;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.rt.sys.entity.SysRole;
import com.rt.sys.service.ISysRoleService;
import com.rt.sys.util.SysUserUtils;

@Component
public class RoleFunctions {
	
	@Resource
	private ISysRoleService sysRoleService;
	
	/**
	 * 用户的角色List形式(当前登录用户持有的角色)
	 */
	public List<SysRole> getUserRoleList(){
		return sysRoleService.findCurUserRoleList();
	}
	
	/**
	 * 用户角色map形式 key:角色id
	 */
	public Map<Long, SysRole> getUserRoleMap(){
		return SysUserUtils.getUserRolesMap();
	}
	
}
