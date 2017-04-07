package com.rt.shop.mapper;

import java.util.List;

import com.rt.shop.entity.OrderForm;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Orderform 表数据库控制层接口
 *
 */
public interface OrderformMapper extends AutoMapper<OrderForm> {

	List selectSumPriceByUserId(OrderForm object);


}