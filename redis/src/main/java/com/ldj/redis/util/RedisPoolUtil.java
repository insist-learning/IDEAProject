package com.ldj.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolUtil {
    private static JedisPool pool;

    static {
        //1.连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(5);
        String host = "192.168.16.247";
        int port = 6379;
        pool = new JedisPool(poolConfig, host, port);
    }

    public static Jedis getJedis() {
        Jedis jedis = pool.getResource();
        jedis.auth("123456");
        return jedis;
    }
    public static void close(Jedis jedis) {
        jedis.close();
    }
}
