package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.rt.shop.entity.GoodsCart;

/**
 *
 * Goodscart 表数据服务层接口
 *
 */
public interface IGoodsCartService extends ISuperService<GoodsCart> {

	List<GoodsCart> selectByStoreCartId(Long store_id);

	
}