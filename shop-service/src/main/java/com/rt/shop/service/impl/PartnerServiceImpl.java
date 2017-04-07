package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.shop.entity.Partner;
import com.rt.shop.mapper.PartnerMapper;
import com.rt.shop.service.IPartnerService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Partner 表数据服务层接口实现类
 *
 */
@Service
public class PartnerServiceImpl extends BaseServiceImpl<PartnerMapper, Partner> implements IPartnerService {

	@Autowired
	PartnerMapper partnerMapper;
	
	@Override
	public List<Partner> selectParterNoImg(Partner sPartner) {
		return baseMapper.selectParterNoImg(sPartner);
	}


}