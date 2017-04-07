package com.rt.sys.service;

import java.util.Map;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.sys.entity.SysUser;

/**
 *
 * SysUser 表数据服务层接口
 *
 */
public interface ISysUserService extends ISuperService<SysUser> {
	/**
	 * 添加或更新用户
	* @param sysUser
	* @return
	 */
	public int saveSysUser(SysUser sysUser);
	
	/**
	 * 删除用户
	* @param userId
	* @return
	 */
	public int deleteUser(Long userId);
	
	/**
	 * 用户列表
	* @param params
	* @return
	 */
	public Page<SysUser> findPageInfo(Map<String, Object> params) ;
	
	/**
	 * 验证用户
	* @param username 用户名
	* @param password 密码
	* @return user
	 */
	public SysUser checkUser(String username,String password);

}