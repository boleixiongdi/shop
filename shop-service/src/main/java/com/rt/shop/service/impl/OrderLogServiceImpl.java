package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.OrderLog;
import com.rt.shop.mapper.OrderLogMapper;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * OrderLog 表数据服务层接口实现类
 *
 */
@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLogMapper, OrderLog> implements IOrderLogService {


}