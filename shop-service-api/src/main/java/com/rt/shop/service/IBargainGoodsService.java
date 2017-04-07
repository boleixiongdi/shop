package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.entity.Goods;

/**
 *
 * BargainGoods 表数据服务层接口
 *
 */
public interface IBargainGoodsService extends ISuperService<BargainGoods> {

	List<Goods> selectBargainGoodsPage(Page<BargainGoods> page,
			BargainGoods sBargainGoods);


}