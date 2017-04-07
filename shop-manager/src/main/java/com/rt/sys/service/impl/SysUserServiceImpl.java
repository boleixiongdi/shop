package com.rt.sys.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.common.constant.Constant;
import com.rt.common.utils.CacheUtils;
import com.rt.common.utils.PasswordEncoder;
import com.rt.sys.entity.SysOffice;
import com.rt.sys.entity.SysUser;
import com.rt.sys.mapper.SysOfficeMapper;
import com.rt.sys.mapper.SysRoleMapper;
import com.rt.sys.mapper.SysUserMapper;
import com.rt.sys.service.ISysUserService;
import com.rt.sys.service.impl.support.BaseServiceImpl;
import com.rt.sys.util.SysUserUtils;

/**
 *
 * SysUser 表数据服务层接口实现类
 *
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
	@Resource
	private SysOfficeMapper sysOfficeMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	/**
	 * 添加或更新用户
	* @param sysUser
	* @return
	 */
	public int saveSysUser(SysUser sysUser){
		boolean count = false;
		SysOffice sysOffice = sysOfficeMapper.findOfficeCompanyIdByDepId(sysUser.getOfficeId());
		Long companyId = sysUser.getOfficeId();
		if(sysOffice != null){
			companyId = sysOffice.getId();
		}
		sysUser.setCompanyId(companyId);
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			String encryptPwd = PasswordEncoder.encrypt(sysUser.getPassword(), sysUser.getUsername());
			sysUser.setPassword(encryptPwd);
		}else{
			sysUser.setPassword(null);
		}
		if(null == sysUser.getId()){
			count = this.insertSelective(sysUser);
		}else{
			sysRoleMapper.deleteUserRoleByUserId(sysUser.getId());
			count = this.updateSelectiveById(sysUser);
			//清除缓存
			SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(sysUser.getId()));
			if(CacheUtils.isCacheByKey(Constant.CACHE_SYS_USER, sysUser.getId().toString())){
				String userType = this.selectById(sysUser.getId()).getUser_type();
				sysUser.setUser_type(userType);
				SysUserUtils.cacheLoginUser(sysUser);
			}
		}
		if(sysUser.getRoleIds()!=null) sysRoleMapper.insertUserRoleByUserId(sysUser);
		return 1;
	}
	
	/**
	 * 删除用户
	* @param userId
	* @return
	 */
	public int deleteUser(Long userId){
		sysRoleMapper.deleteUserRoleByUserId(userId);
		SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(userId));
		SysUserUtils.clearCacheUser(userId);
		// this.updateDelFlagToDelStatusById(SysUser.class, userId);
		 return 1;
	}
	
	/**
	 * 用户列表
	* @param params
	* @return
	 */
	public Page<SysUser> findPageInfo(Map<String, Object> params) {
//		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
//		params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
//		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		Page p=null;
		p.setRecords(list);
		return p;
	}
	
	/**
	 * 验证用户
	* @param username 用户名
	* @param password 密码
	* @return user
	 */
	public SysUser checkUser(String username,String password){
		SysUser sysUser = new SysUser();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
		List<SysUser> users = this.selectList(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}
}