package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.rt.shop.entity.CartGsp;
import com.rt.shop.entity.GoodsSpec;
import com.rt.shop.entity.GoodsSpecProperty;

/**
 *
 * Goodsspecproperty 表数据服务层接口
 *
 */
public interface IGoodsSpecPropertyService extends ISuperService<GoodsSpecProperty> {

	List<GoodsSpecProperty> selectGspByGoodsId(Long id);

	//select * from shopping_goodsspecproperty p LEFT JOIN shopping_cart_gsp cg  on p.id =cg.gsp_id
	List<GoodsSpecProperty> selectGspByGcId(CartGsp gc);
	/**
	 * String sql1="s LEFT JOIN  shopping_goods_spec t on  s.id=t.spec_id where t.goods_id="+obj.getId();
	 * @param id
	 * @return
	 */
	List<GoodsSpecProperty> selectGSPByGoods(Long id);

	List<GoodsSpecProperty> selectGSPByGoodsId(GoodsSpec sp);


}