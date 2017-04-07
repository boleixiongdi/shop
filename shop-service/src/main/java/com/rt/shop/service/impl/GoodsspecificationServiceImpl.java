package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.GoodsTypeSpec;
import com.rt.shop.mapper.GoodsspecificationMapper;
import com.rt.shop.service.IGoodsSpecificationService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Goodsspecification 表数据服务层接口实现类
 *
 */
@Service
public class GoodsspecificationServiceImpl extends BaseServiceImpl<GoodsspecificationMapper, GoodsSpecification> implements IGoodsSpecificationService {

	@Override
	public List<GoodsSpecification> selectGSFByGoodsType(Long goodsTypeId) {
		return baseMapper.selectGSFByGoodsType(goodsTypeId);
	}

	@Override
	public List<GoodsSpecification> selectGSFByGoodsTypeId(GoodsTypeSpec gts) {
		return baseMapper.selectGSFByGoodsTypeId(gts);
	}


}