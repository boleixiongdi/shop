package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Message;
import com.rt.shop.mapper.MessageMapper;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Message 表数据服务层接口实现类
 *
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message> implements IMessageService {

	@Override
	public List<Message> selectMessAgeOr(Message sMessage) {
		// TODO Auto-generated method stub
		return null;
	}


}