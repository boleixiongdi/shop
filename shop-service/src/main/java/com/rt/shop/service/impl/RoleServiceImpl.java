package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Role;
import com.rt.shop.mapper.RoleMapper;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {


}