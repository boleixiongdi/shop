package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.rt.shop.entity.Goods;

/**
 *
 * Goods 表数据服务层接口
 *
 */
public interface IGoodsService extends ISuperService<Goods> {

	List<Goods> selectGoodsByStore(Long id);

	List<Goods> selectGoodsByStoreCartId(Long id);


}