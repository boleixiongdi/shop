package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Navigation;
import com.rt.shop.mapper.NavigationMapper;
import com.rt.shop.service.INavigationService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Navigation 表数据服务层接口实现类
 *
 */
@Service
public class NavigationServiceImpl extends BaseServiceImpl<NavigationMapper, Navigation> implements INavigationService {

	@Override
	public List<Navigation> selectNavationOr(Navigation sNavigation) {
		// TODO Auto-generated method stub
		return null;
	}


}