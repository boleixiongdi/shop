package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.CartGsp;
import com.rt.shop.entity.GoodsSpec;
import com.rt.shop.entity.GoodsSpecProperty;
import com.rt.shop.mapper.GoodsspecpropertyMapper;
import com.rt.shop.service.IGoodsSpecPropertyService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Goodsspecproperty 表数据服务层接口实现类
 *
 */
@Service
public class GoodsspecpropertyServiceImpl extends BaseServiceImpl<GoodsspecpropertyMapper, GoodsSpecProperty> implements IGoodsSpecPropertyService {

	@Override
	public List<GoodsSpecProperty> selectGspByGoodsId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GoodsSpecProperty> selectGspByGcId(CartGsp gc) {
		return baseMapper.selectGspByGcId(gc);
	}

	@Override
	public List<GoodsSpecProperty> selectGSPByGoods(Long goodsId) {
		return baseMapper.selectGSPByGoods(goodsId);
	}

	@Override
	public List<GoodsSpecProperty> selectGSPByGoodsId(GoodsSpec sp) {
		return baseMapper.selectGSPByGoodsId(sp);
	}


}