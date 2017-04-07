package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Favorite;
import com.rt.shop.mapper.FavoriteMapper;
import com.rt.shop.service.IFavoriteService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Favorite 表数据服务层接口实现类
 *
 */
@Service
public class FavoriteServiceImpl extends BaseServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {


}