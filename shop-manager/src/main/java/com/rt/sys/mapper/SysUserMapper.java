package com.rt.sys.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.rt.sys.entity.SysUser;

/**
 *
 * SysUser 表数据库控制层接口
 *
 */
public interface SysUserMapper extends AutoMapper<SysUser> {

	public List<SysUser> findPageInfo(Map<String, Object> params);
}