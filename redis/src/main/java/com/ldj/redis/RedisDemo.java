package com.ldj.redis;

import redis.clients.jedis.Jedis;
import org.junit.Test;

public class RedisDemo {
    public static void main(String[] args) {
        String host = "192.168.43.29";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        jedis.auth("123456");
        jedis.set("strName", "字符串中的名称");
        String strName = jedis.get("strName");
        System.out.println("redis中的数据："+strName);
        jedis.close();
    }

    @Test
    public void testGet() {
        String host = "192.168.43.29";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        jedis.auth("123456");
        String key = "applicationName";
        if (jedis.exists(key)) {
            String result = jedis.get(key);
            System.out.println("Redis数据库中查询得到："+result);
        } else {
            String result = "微信开发";
            jedis.set(key,result);
            System.out.println("MYSQL数据库查询得到："+result);
        }
        jedis.close();
    }

    @Test
    public void test() {
        String host = "192.168.1.106";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        jedis.auth("123456");
        System.out.println(jedis.ping());
    }

}
