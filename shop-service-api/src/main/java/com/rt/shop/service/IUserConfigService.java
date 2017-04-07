package com.rt.shop.service;

import com.rt.shop.entity.UserConfig;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Userconfig 表数据服务层接口
 *
 */
public interface IUserConfigService extends ISuperService<UserConfig> {

	UserConfig getUserConfig();


}