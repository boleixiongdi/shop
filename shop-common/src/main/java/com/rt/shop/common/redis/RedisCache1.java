/*package com.rt.shop.common.redis;
 
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.rt.shop.common.tools.spring.SpringContextUtil;
 
*//**
 * http://www.cnblogs.com/wcyBlog/p/4402567.html
 * @author zhuan
 *
 *//*
@Service
public class RedisCache1 implements Cache {
    private static Log logger = LogFactory.getLog(RedisCache1.class);
    private ShardedJedis redisClient = createClient();
    *//** The ReadWriteLock. *//*
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
 
    private String id;
 
    public RedisCache1(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }
  

    public String getId() {
        return this.id;
    }
   
    public int getSize() {
		return 0;
       // return Integer.valueOf(redisClient.dbSize().toString());
    }
 
    public void putObject(Object key, Object value) {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
    	redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil
                .serialize(value));
    }
 
    public Object getObject(Object key) {
        Object value = SerializeUtil.unserialize(redisClient.get(SerializeUtil
                .serialize(key.toString())));
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key + "=" + value);
        return value;
    }
 
    public Object removeObject(Object key) {
        return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
    }
 
    public void clear() {
      //  redisClient.flushDB();
    }
 
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
 
    
    @Resource//如果Spring容器中有，就注入，没有就忽略
    private ShardedJedisPool shardedJedisPool=SpringContextUtil.getBean(ShardedJedisPool.class);
    
    private  ShardedJedis createClient() {
    	ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
		return shardedJedis;
    }
}*/