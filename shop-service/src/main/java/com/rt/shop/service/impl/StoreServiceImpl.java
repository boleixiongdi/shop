package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Store;
import com.rt.shop.mapper.StoreMapper;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Store 表数据服务层接口实现类
 *
 */
@Service
public class StoreServiceImpl extends BaseServiceImpl<StoreMapper, Store> implements IStoreService {


}