package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rt.shop.entity.ChattingLog;
import com.rt.shop.mapper.ChattinglogMapper;
import com.rt.shop.service.IChattingLogService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Chattinglog 表数据服务层接口实现类
 *
 */
@Service
public class ChattinglogServiceImpl extends BaseServiceImpl<ChattinglogMapper, ChattingLog> implements IChattingLogService {



	@Override
	public List<ChattingLog> selectListOr1(ChattingLog sChattingLog,
			String string) {
		return baseMapper.selectListOr1(sChattingLog);
	}




}