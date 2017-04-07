package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Article;
import com.rt.shop.mapper.ArticleMapper;
import com.rt.shop.service.IArticleService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Article 表数据服务层接口实现类
 *
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IArticleService {


}