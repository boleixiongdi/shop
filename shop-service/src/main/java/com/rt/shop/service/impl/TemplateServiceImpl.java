package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Template;
import com.rt.shop.mapper.TemplateMapper;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Template 表数据服务层接口实现类
 *
 */
@Service
public class TemplateServiceImpl extends BaseServiceImpl<TemplateMapper, Template> implements ITemplateService {

	@Override
	public Template selectByMark(String mark) {
		Template Template=new Template();
		Template.setMark(mark);
		return baseMapper.selectOne(Template);
	}


}