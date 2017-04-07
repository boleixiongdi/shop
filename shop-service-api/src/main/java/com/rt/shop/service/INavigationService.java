package com.rt.shop.service;

import java.util.List;

import com.rt.shop.entity.Navigation;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Navigation 表数据服务层接口
 *
 */
public interface INavigationService extends ISuperService<Navigation> {

	/**
	 *   .query("select obj from Navigation obj where obj.display=:display and obj.location=:location and obj.type!=:type order by obj.sequence asc", params, 0, count);
	 * @param sNavigation
	 * @return
	 */
	List<Navigation> selectNavationOr(Navigation sNavigation);


}