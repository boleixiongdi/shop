package com.rt.shop.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.entity.Goods;

/**
 *
 * BargainGoods 表数据库控制层接口
 *
 */
public interface BargainGoodsMapper extends AutoMapper<BargainGoods> {

	List<Goods>  selectBargainGoodsPage(BargainGoods sBargainGoods);


}