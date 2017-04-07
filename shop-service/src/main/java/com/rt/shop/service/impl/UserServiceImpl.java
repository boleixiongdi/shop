package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.mapper.UserMapper;
import com.rt.shop.service.IUserService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

	

	@Override
	public User selectUserByUsername(String username) {
		User user=new User();
		user.setUserName(username);
		return baseMapper.selectOne(user);
	}
	public int insertBatchUserRole(List<UserRole> ur){
		return baseMapper.insertBatchUserRole(ur);
	}

}