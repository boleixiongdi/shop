package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Document;
import com.rt.shop.mapper.DocumentMapper;
import com.rt.shop.service.IDocumentService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Document 表数据服务层接口实现类
 *
 */
@Service
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, Document> implements IDocumentService {


}