package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Group;
import com.rt.shop.mapper.GroupMapper;
import com.rt.shop.service.IGroupService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Group 表数据服务层接口实现类
 *
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper, Group> implements IGroupService {


}