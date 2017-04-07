package com.rt.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysRole;
import com.rt.sys.entity.SysUser;

/**
 *
 * SysRole 表数据服务层接口
 *
 */
public interface ISysRoleService extends ISuperService<SysRole> {

	/**
	 *新增或更新SysRole
	 */
	public int saveSysRole(SysRole sysRole);
	
	/**
	 * 删除角色
	* @param id
	 */
	public int deleteSysRole(Long id);
	
	/**
	 * 添加角色绑定的人员
	* @param sysRole
	* @return
	 */
	public int saveUserRole(SysRole sysRole);
	
	
	/**
	 * 根据条件分页查询SysRole列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public Page<SysRole> findPageInfo(Map<String,Object> params);
	
	/**
	 * 根据角色id查询拥有的资源id集合
	* @param roleId
	* @return
	 */
	public List<Long> findResourceIdsByRoleId(Long roleId);
	
	/**
	 * 根据角色id查询拥有的机构id集合
	* @param roleId
	* @return
	 */
	public List<Long> findOfficeIdsByRoleId(Long roleId);
	
	/**
	 * 根据角色id查询拥有的资源 
	* @param roleId
	* @return
	 */
	public List<SysResource> findResourceByRoleId(Long roleId);
	
	/**
	 * 根据角色id查询拥有此角色的用户
	* @param roleId
	* @return
	 */
	public List<SysUser> findUserByRoleId(Long roleId);
	
	/**
	 * 当前登录用户的可见的角色
	 */
	public List<SysRole> findCurUserRoleList();
	
	/**
	 * 当前登录用户的可见的角色map形式 
	 */
	public Map<Long, SysRole> findCurUserRoleMap();
	
	
	/**
	 * 用户的角色List列表
	* @param userId
	* @return userRoleList
	 */
	public List<SysRole> findUserRoleListByUserId(Long userId);
	/**
	 * 用户的角色Map
	* @param userId
	* @return userRoleMap
	 */
	public Map<Long, SysRole> findUserRoleMapByUserId(Long userId);
}