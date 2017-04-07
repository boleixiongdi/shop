package com.rt.shop.service;

import java.util.List;

import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService extends ISuperService<User> {


	User selectUserByUsername(String username);

	public int insertBatchUserRole(List<UserRole> urList);

}