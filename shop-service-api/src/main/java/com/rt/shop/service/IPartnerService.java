package com.rt.shop.service;

import java.util.List;

import com.rt.shop.entity.Partner;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Partner 表数据服务层接口
 *
 */
public interface IPartnerService extends ISuperService<Partner> {

	
	/**
	 * 查询没有图片的合作伙伴
	 * @param ssPartner
	 * @return
	 */
	List<Partner> selectParterNoImg(Partner ssPartner);


}