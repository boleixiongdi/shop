package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.GoodsTypeSpec;

/**
 *
 * Goodsspecification 表数据服务层接口
 *
 */
public interface IGoodsSpecificationService extends ISuperService<GoodsSpecification> {

	/**
	 * String sql1="s LEFT JOIN  shopping_goodstype_spec a on s.id=a.spec_id where a.type_id="+goodsType.getId();
	 * @param id
	 * @return
	 */
	List<GoodsSpecification> selectGSFByGoodsType(Long id);

	List<GoodsSpecification> selectGSFByGoodsTypeId(GoodsTypeSpec gts);


}