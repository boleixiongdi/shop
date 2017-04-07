package com.rt.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.mapper.GoodscartMapper;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Goodscart 表数据服务层接口实现类
 *
 */
@Service
public class GoodscartServiceImpl extends BaseServiceImpl<GoodscartMapper, GoodsCart> implements IGoodsCartService {

	@Override
	public List<GoodsCart> selectByStoreCartId(Long store_id) {
		GoodsCart ssGoodsCart=new GoodsCart();
		ssGoodsCart.setSc_id(store_id);
	  return baseMapper.selectList(new EntityWrapper(ssGoodsCart));
	}

	
}