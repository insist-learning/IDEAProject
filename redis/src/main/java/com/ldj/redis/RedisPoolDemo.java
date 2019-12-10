package com.ldj.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldj.redis.bean.User;
import com.ldj.redis.util.RedisPoolUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 连接池的使用
 */
public class RedisPoolDemo {
    public static void main(String[] args) {
        //1.连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(5);
        //2.创建连接池
        String host = "192.168.1.108";
        int port = 6379;
        JedisPool pool = new JedisPool(poolConfig, host, port);
        Jedis jedis = pool.getResource();
        jedis.auth("123456");
        System.out.println(jedis.ping());
    }

    /**
     * 使用工具类优化连接池的使用
     */
    @Test
    public void testJedisPoolUtil() {
        Jedis jedis = RedisPoolUtil.getJedis();
        String key = "applicationName";
        if (jedis.exists(key)) {
            String result = jedis.get(key);
            System.out.println("Redis数据库中查询得到："+result);
        } else {
            String result = "微信小程序开发";
            jedis.set(key,result);
            System.out.println("Mysql数据库中查询得到:"+result);
        }
        RedisPoolUtil.close(jedis);
    }

    /**
     * 优化 redis 中 hash 的存储
     */
    @Test
    public void testHash() {
        Jedis jedis = RedisPoolUtil.getJedis();
        int id = 1;
        String key = User.getKeyName() + id;
        if (jedis.exists(key)) {
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(jedis.hgetAll(key)));
            User user = User.builder().id(object.getInteger("id")).name(object.getString("userName"))
                    .password(object.getString("name")).password(object.getString("password"))
                    .age(object.getInteger("age")).build();
            System.out.println("redis中查询得到"+JSON.toJSONString(user));
        } else {
            User user = User.builder().id(1).name("张三").age(11).username("userName").password("password").build();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",user.getId()+"");
            map.put("name",user.getName()+"");
            map.put("age",user.getAge()+"");
            map.put("password",user.getPassword()+"");
            map.put("userName",user.getUsername()+"");
            jedis.hmset(key,map);
            System.out.println("Mysql中查询得到："+JSON.toJSONString(user));
        }

    }
}
