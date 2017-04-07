package com.rt.common.beetl.function;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rt.common.beetl.utils.BeetlUtils;
import com.rt.common.constant.Constant;
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysUser;
import com.rt.sys.util.SysUserUtils;

@Component
public class AuthUserFunctions {

	/**
	 * 判断用户是否具有指定权限
	 */
	public boolean hasPermission(String url) {
		Map<String, SysResource> allRes = BeetlUtils
				.getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
		SysResource sysResource = allRes.get(url);
		if (sysResource == null
				|| Constant.RESOURCE_COMMON.equals(sysResource.getCommon())) {
			return true;
		}

	
		 Map<Long,LinkedHashMap<String, SysResource>> userRes = SysUserUtils.getUserResources();
         for(Map<String, SysResource> res1 :userRes.values()){
        	 if (res1.containsKey(url)) return true;
         }
		
		return false;
	}
	
	/**
	 * 登录用户
	* @return
	 */
	public SysUser getLoginUser(){
		return SysUserUtils.getCacheLoginUser();
	}
	
	/**
	 * 是否为超级管理员
	* @return
	 */
	public boolean isSuper(){
		return getLoginUser().getUser_type().equals(Constant.SUPER_ADMIN)?true:false;
	}
	
	/**
	 * 是否持有所有数据(数据范围，可认为是总管理)
	 */
	public boolean hasAllDataScope(){
		return SysUserUtils.getUserDataScope().contains(Constant.DATA_SCOPE_ALL);
	}

	
}
