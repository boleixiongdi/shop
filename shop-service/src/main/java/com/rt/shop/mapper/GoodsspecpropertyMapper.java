package com.rt.shop.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.rt.shop.entity.CartGsp;
import com.rt.shop.entity.GoodsSpec;
import com.rt.shop.entity.GoodsSpecProperty;

/**
 *
 * Goodsspecproperty 表数据库控制层接口
 *
 */
public interface GoodsspecpropertyMapper extends AutoMapper<GoodsSpecProperty> {

	List<GoodsSpecProperty> selectGSPByGoods(Long goods_id);

	List<GoodsSpecProperty> selectGSPByGoodsId(GoodsSpec sp);

	List<GoodsSpecProperty> selectGspByGcId(CartGsp gc);


}