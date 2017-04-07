package com.rt.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.constructs.web.PageInfo;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rt.common.constant.Constant;
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysRole;
import com.rt.sys.entity.SysUser;
import com.rt.sys.mapper.SysRoleMapper;
import com.rt.sys.service.ISysRoleService;
import com.rt.sys.service.impl.support.BaseServiceImpl;
import com.rt.sys.util.SysUserUtils;

/**
 *
 * SysRole 表数据服务层接口实现类
 *
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
	@Resource
	private SysRoleMapper sysRoleMapper;
	/**
	 *新增或更新SysRole
	 */
	public int saveSysRole(SysRole sysRole){
		boolean count = false;
		if(null == sysRole.getId()){
			count = this.insertSelective(sysRole);
		}else{
			sysRoleMapper.deleteRoleResourceByRoleId(sysRole.getId());
			sysRoleMapper.deleteRoleOfficeByRoleId(sysRole.getId());
			count = this.updateSelectiveById(sysRole);
			//清除缓存
			List<Long> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
			SysUserUtils.clearAllCachedAuthorizationInfo(userIds);
		}
		if(sysRole.getResourceIds().length>0){
			sysRoleMapper.insertRoleResource(sysRole);
		}
//		if(sysRole.getOfficeIds().length>0 && ("9").equals(sysRole.getDataScope())){
//			sysRoleMapper.insertRoleOffice(sysRole);
//		}
	    return 1;
	}
	
	/**
	 * 删除角色
	* @param id
	 */
	public int deleteSysRole(Long id){
		List<Long> userIds = sysRoleMapper.findUserIdsByRoleId(id);
		sysRoleMapper.deleteUserRoleByRoleId(id);
		sysRoleMapper.deleteRoleOfficeByRoleId(id);
		sysRoleMapper.deleteRoleResourceByRoleId(id);
		 this.deleteById(id);
		//清除缓存
		SysUserUtils.clearAllCachedAuthorizationInfo(userIds);
		return 1;
	}
	
	/**
	 * 添加角色绑定的人员
	* @param sysRole
	* @return
	 */
	public int saveUserRole(SysRole sysRole){
		//旧的绑定的人员id
		List<Long> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
		//当前的要绑定的人员id
		List<Long> curUserIds = Lists.newArrayList(sysRole.getUserIds());
		userIds.addAll(curUserIds);
		ImmutableList<Long> mergeList = ImmutableSet.copyOf(userIds).asList();
		
		sysRoleMapper.deleteUserRoleByRoleId(sysRole.getId());
		if(sysRole.getUserIds().length>0) {
			sysRoleMapper.insertUserRoleByRoleId(sysRole);
		}
		//清除缓存
		SysUserUtils.clearAllCachedAuthorizationInfo(mergeList);
		return 1;
	}
	
	
	/**
	 * 根据条件分页查询SysRole列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public Page<SysRole> findPageInfo(Map<String,Object> params) {
//		params.put(Constant.CACHE_USER_DATASCOPE, 
//				SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
//        PageHelper.startPage(params);
        List<SysRole> list = sysRoleMapper.findPageInfo(params); 
        Page p=null;
        p.setRecords(list);
        return p;
	}
	
	/**
	 * 根据角色id查询拥有的资源id集合
	* @param roleId
	* @return
	 */
	public List<Long> findResourceIdsByRoleId(Long roleId){
		return sysRoleMapper.findResourceIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的机构id集合
	* @param roleId
	* @return
	 */
	public List<Long> findOfficeIdsByRoleId(Long roleId){
		return sysRoleMapper.findOfficeIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的资源 
	* @param roleId
	* @return
	 */
	public List<SysResource> findResourceByRoleId(Long roleId){
		return sysRoleMapper.findResourceByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有此角色的用户
	* @param roleId
	* @return
	 */
	public List<SysUser> findUserByRoleId(Long roleId){
		return sysRoleMapper.findUserByRoleId(roleId);
	}
	
	/**
	 * 当前登录用户的可见的角色
	 */
	public List<SysRole> findCurUserRoleList(){
		Map<String, Object> params = Maps.newHashMap();
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
		return sysRoleMapper.findPageInfo(params);
	}
	
	/**
	 * 当前登录用户的可见的角色map形式 
	 */
	public Map<Long, SysRole> findCurUserRoleMap(){
		List<SysRole> list = this.findCurUserRoleList();
		Map<Long, SysRole> map = Maps.uniqueIndex(list, new Function<SysRole, Long>() {
			@Override
			public Long apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return map;
	}
	
	
	/**
	 * 用户的角色List列表
	* @param userId
	* @return userRoleList
	 */
	public List<SysRole> findUserRoleListByUserId(Long userId){
		List<SysRole> userRoles = sysRoleMapper.findUserRoleListByUserId(userId);
		return userRoles;
	}
	
	/**
	 * 用户的角色Map
	* @param userId
	* @return userRoleMap
	 */
	public Map<Long, SysRole> findUserRoleMapByUserId(Long userId){
		List<SysRole> roleList = this.findUserRoleListByUserId(userId);
		Map<Long, SysRole> userRoleMap = Maps.uniqueIndex(roleList, new Function<SysRole, Long>() {
			@Override
			public Long apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return userRoleMap;
	}
}