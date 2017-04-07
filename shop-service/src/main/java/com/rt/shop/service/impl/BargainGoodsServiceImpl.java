package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.entity.Goods;
import com.rt.shop.mapper.BargainGoodsMapper;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * BargainGoods 表数据服务层接口实现类
 *
 */
@Service
public class BargainGoodsServiceImpl extends BaseServiceImpl<BargainGoodsMapper, BargainGoods> implements IBargainGoodsService {

	@Autowired
	private BargainGoodsMapper  bargainGoodsMapper;
	@Override
	public List<Goods>  selectBargainGoodsPage(Page<BargainGoods> page,
			BargainGoods sBargainGoods) {
		return bargainGoodsMapper.selectBargainGoodsPage(sBargainGoods);
	}


}