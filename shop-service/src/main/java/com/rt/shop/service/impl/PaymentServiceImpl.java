package com.rt.shop.service.impl;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Payment;
import com.rt.shop.mapper.PaymentMapper;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Payment 表数据服务层接口实现类
 *
 */
@Service
public class PaymentServiceImpl extends BaseServiceImpl<PaymentMapper, Payment> implements IPaymentService {

}