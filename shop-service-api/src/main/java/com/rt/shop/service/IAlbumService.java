package com.rt.shop.service;

import com.rt.shop.entity.Album;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Album 表数据服务层接口
 *
 */
public interface IAlbumService extends ISuperService<Album> {

	Album getDefaultAlbum(Long null2Long);


}