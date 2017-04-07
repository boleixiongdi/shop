package com.rt.shop.mapper;

import java.util.List;

import com.rt.shop.entity.Partner;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Partner 表数据库控制层接口
 *
 */
public interface PartnerMapper extends AutoMapper<Partner> {

	List<Partner> selectParterNoImg(Partner sPartner);


}