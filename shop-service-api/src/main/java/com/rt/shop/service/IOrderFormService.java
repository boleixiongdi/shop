package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.rt.shop.entity.Address;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.Store;

/**
 *
 * Orderform 表数据服务层接口
 *
 */
public interface IOrderFormService extends ISuperService<OrderForm> {

	

	/**
	 * // "select obj.user.id,sum(obj.totalPrice) from OrderForm obj where obj.order_status>=:order_status group by obj.user.id", 
	 * @param object
	 * @return
	 */
	List selectSumPriceByUserId(OrderForm object);


}