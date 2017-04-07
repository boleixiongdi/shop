package com.rt.shop;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import ch.qos.logback.classic.Logger;

import com.baomidou.framework.spring.SpringContextHolder;

/** 
 * 创建时间：2016年1月7日 上午11:40:00 
 *  http://blog.csdn.net/fengshizty/article/details/50581887#
 * Mybatis二级缓存实现类 
 *  
 * @author andy 
 * @version 2.2 
 */  
  
public class MybatisRedisCache implements Cache {  
  
	
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);  
      
    private RedisTemplate<Serializable, Serializable> redisTemplate =  (RedisTemplate<Serializable, Serializable>) SpringContextHolder.getBean(RedisTemplate.class);   
      
    private String id;  
      
    private JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();  
      
    public MybatisRedisCache(final String id){  
        if(id == null){  
            throw new IllegalArgumentException("Cache instances require an ID");  
        }  
        System.out.println("Redis Cache id " + id);  
        this.id = id;  
    }  
      
    @Override  
    public String getId() {  
        return this.id;  
    }  
  
    @Override  
    public void putObject(Object key, Object value) {  
        if(value != null){  
            redisTemplate.opsForValue().set(key.toString(), jdkSerializer.serialize(value), 2, TimeUnit.DAYS);  
        }  
    }  
  
    @Override  
    public Object getObject(Object key) {  
        try {  
            if(key != null){  
                Object obj = redisTemplate.opsForValue().get(key.toString());  
                return jdkSerializer.deserialize((byte[])obj);   
            }  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
    @Override  
    public Object removeObject(Object key) {  
        try {  
            if(key != null){  
                redisTemplate.expire(key.toString(), 1, TimeUnit.SECONDS);  
            }  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
    @Override  
    public void clear() {  
        //jedis nonsupport  
    }  
  
    //TODO
    @Override  
    public int getSize() {
		return 0;  
//        Long size = redisTemplate.getMasterRedisTemplate().execute(new RedisCallback<Long>(){  
//            @Override  
//            public Long doInRedis(RedisConnection connection)  
//                    throws DataAccessException {  
//                return connection.dbSize();  
//            }  
//        });  
//        return size.intValue();  
    }  
  
    @Override  
    public ReadWriteLock getReadWriteLock() {  
        return this.readWriteLock;  
    }  
      
}  