package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.Chatting;
import com.rt.shop.mapper.ChattingMapper;
import com.rt.shop.service.IChattingService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Chatting 表数据服务层接口实现类
 *
 */
@Service
public class ChattingServiceImpl extends BaseServiceImpl<ChattingMapper, Chatting> implements IChattingService {

	@Override
	public List selectListOr(Chatting sChatting) {
		// TODO Auto-generated method stub
		return null;
	}


}