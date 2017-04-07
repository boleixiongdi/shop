package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.User;
import com.rt.shop.entity.UserConfig;
import com.rt.shop.mapper.UserconfigMapper;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Userconfig 表数据服务层接口实现类
 *
 */
@Service
public class UserconfigServiceImpl extends BaseServiceImpl<UserconfigMapper, UserConfig> implements IUserConfigService {

	@Override
	public UserConfig getUserConfig() {
		return null;
//		User u = SecurityUserHolder.getCurrentUser();
//	     UserConfig config = null;
//	     if (u != null) {
//	       User user = (User)this.userDAO.get(u.getId());
//	       if (user != null)
//	         config = user.getConfig();
//	     }
//	     else {
//	       config = new UserConfig();
//	     }
//	     return config;
	}


}