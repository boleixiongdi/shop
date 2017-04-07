package com.rt.shop.mapper;

import java.util.List;

import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * User 表数据库控制层接口
 *
 */
public interface UserMapper extends AutoMapper<User> {
	public int insertBatchUserRole (List<UserRole> urList);

}