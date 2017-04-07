package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rt.shop.entity.Goods;
import com.rt.shop.mapper.GoodsMapper;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Goods 表数据服务层接口实现类
 *
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl<GoodsMapper, Goods> implements IGoodsService {


		@Autowired
	private	GoodsMapper  goodsMapper;
	@Override
	public List<Goods> selectGoodsByStore(Long id) {
		Goods goods=new Goods();
		goods.setGoods_store_id(id);
		return baseMapper.selectList(new EntityWrapper<Goods>(goods));
	}

	@Override
	public List<Goods> selectGoodsByStoreCartId(Long id) {
		return goodsMapper.selectGoodsByStoreCartId(id);
	}


}