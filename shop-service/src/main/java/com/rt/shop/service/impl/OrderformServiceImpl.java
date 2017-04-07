package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Address;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.Store;
import com.rt.shop.mapper.OrderformMapper;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Orderform 表数据服务层接口实现类
 *
 */
@Service
public class OrderformServiceImpl extends BaseServiceImpl<OrderformMapper, OrderForm> implements IOrderFormService {


	
	

	@Override
	public List selectSumPriceByUserId(OrderForm object) {
		 return baseMapper.selectSumPriceByUserId(object);
	}


}