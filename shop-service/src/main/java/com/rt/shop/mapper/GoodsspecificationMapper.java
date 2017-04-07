package com.rt.shop.mapper;

import java.util.List;

import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.GoodsTypeSpec;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Goodsspecification 表数据库控制层接口
 *
 */
public interface GoodsspecificationMapper extends AutoMapper<GoodsSpecification> {

	List<GoodsSpecification> selectGSFByGoodsType(Long type_id);

	List<GoodsSpecification> selectGSFByGoodsTypeId(GoodsTypeSpec gts);


}