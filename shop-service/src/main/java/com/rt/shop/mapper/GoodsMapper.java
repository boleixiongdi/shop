package com.rt.shop.mapper;

import java.util.List;

import com.rt.shop.entity.Goods;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Goods 表数据库控制层接口
 *
 */
public interface GoodsMapper extends AutoMapper<Goods> {

	List<Goods> selectGoodsByStoreCartId(Long id);


}