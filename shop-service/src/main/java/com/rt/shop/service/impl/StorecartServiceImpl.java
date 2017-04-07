package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.StoreCart;
import com.rt.shop.mapper.StorecartMapper;
import com.rt.shop.service.IStoreCartService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Storecart 表数据服务层接口实现类
 *
 */
@Service
public class StorecartServiceImpl extends BaseServiceImpl<StorecartMapper, StoreCart> implements IStoreCartService {

	@Override
	public List<StoreCart> selectStoreCart(StoreCart sStoreCart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreCart> selectStoreCartOr(StoreCart sStoreCart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreCart> selectCookieCart(StoreCart sStoreCart) {
		// TODO Auto-generated method stub
		return null;
	}


}