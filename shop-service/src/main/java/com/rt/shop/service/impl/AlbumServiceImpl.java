package com.rt.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.User;
import com.rt.shop.mapper.AlbumMapper;
import com.rt.shop.mapper.UserMapper;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Album 表数据服务层接口实现类
 *
 */
@Service
public class AlbumServiceImpl extends BaseServiceImpl<AlbumMapper, Album> implements IAlbumService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	AlbumMapper albumMapper;
	@Override
	public Album getDefaultAlbum(Long id) {

	     User user = userMapper.selectById(id);
	     if (user.getParent_id() == null) {
	       
	       Album sAlbum=new Album();
	       sAlbum.setUser_id(id);
	       sAlbum.setAlbum_default(Boolean.valueOf(true));
	       List<Album> list = this.albumMapper.selectList(new EntityWrapper(sAlbum));
	       
	       //  "select obj from Album obj where obj.user.id=:user_id and obj.album_default=:album_default", 
	       if (list.size() > 0) {
	         return (Album)list.get(0);
	       }
	       return null;
	     }
	     Album sAlbum=new Album();
	       sAlbum.setUser_id(user.getParent_id());
	       sAlbum.setAlbum_default(Boolean.valueOf(true));
	       List<Album> list = this.albumMapper.selectList(new EntityWrapper(sAlbum));
	     if (list.size() > 0) {
	       return (Album)list.get(0);
	     }
	     return null;
	   
	}


}