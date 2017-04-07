package com.rt.shop.mapper;

import java.util.List;

import com.rt.shop.entity.ChattingLog;
import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Chattinglog 表数据库控制层接口
 *
 */
public interface ChattinglogMapper extends AutoMapper<ChattingLog> {

	List<ChattingLog> selectListOr1(ChattingLog sChattingLog);


}