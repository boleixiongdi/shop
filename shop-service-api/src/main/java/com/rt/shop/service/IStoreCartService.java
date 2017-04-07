package com.rt.shop.service;

import java.util.List;

import com.rt.shop.entity.StoreCart;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Storecart 表数据服务层接口
 *
 */
public interface IStoreCartService extends ISuperService<StoreCart> {

	
	List<StoreCart> selectStoreCart(StoreCart sStoreCart);

	/**
	 * 		//"select obj from StoreCart obj where (obj.cart_session_id=:cart_session_id or obj.user.id=:user_id) and obj.sc_status=:sc_status and obj.store.id=:store_id",
		
	 * @param sStoreCart
	 * @return
	 */
	List<StoreCart> selectStoreCartOr(StoreCart sStoreCart);

	List<StoreCart> selectCookieCart(StoreCart sStoreCart);


}