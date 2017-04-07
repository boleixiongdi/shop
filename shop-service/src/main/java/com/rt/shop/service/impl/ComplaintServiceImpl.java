package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Complaint;
import com.rt.shop.mapper.ComplaintMapper;
import com.rt.shop.service.IComplaintService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Complaint 表数据服务层接口实现类
 *
 */
@Service
public class ComplaintServiceImpl extends BaseServiceImpl<ComplaintMapper, Complaint> implements IComplaintService {


}