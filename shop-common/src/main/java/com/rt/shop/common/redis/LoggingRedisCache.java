package com.rt.shop.common.redis;
 
import org.apache.ibatis.cache.decorators.LoggingCache;
 
public class LoggingRedisCache extends LoggingCache{
 
    public LoggingRedisCache(String id) {
        super(new RedisCache(id));
        // TODO Auto-generated constructor stub
    }
 
}