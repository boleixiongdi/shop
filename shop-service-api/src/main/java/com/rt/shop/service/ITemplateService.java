package com.rt.shop.service;

import com.rt.shop.entity.Template;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Template 表数据服务层接口
 *
 */
public interface ITemplateService extends ISuperService<Template> {

	Template selectByMark(String mark);


}