package com.rt.shop.service;

import com.rt.shop.entity.SysConfig;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Sysconfig 表数据服务层接口
 *
 */
public interface ISysConfigService extends ISuperService<SysConfig> {

	
	public abstract SysConfig getSysConfig();

}