package com.jjlb.service.redis;

import redis.clients.jedis.ShardedJedis;

/**
 * @author lslnx0307
 */

public interface RedisDataSource {
    /**
     * 获取redisClient
     * @return
     */
    public abstract ShardedJedis getRedisClient();
    public void returnResource(ShardedJedis shardedJedis);
    public void returnResource(ShardedJedis shardedJedis, boolean broken);
}

