package com.ldj.redis.service.impl;

import com.ldj.redis.bean.User;
import com.ldj.redis.service.UserService;
import com.ldj.redis.util.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;


    public User login(String uname, String upass) {

        return null;
    }

    public String loginValidate(String uname) {
        String key = User.getLoginCountFailKey(uname);
        int num = LoginConstant.limit;
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key,"1");
            redisTemplate.expire(key,LoginConstant.times,TimeUnit.MINUTES);
            return "登录失败,在"+LoginConstant.times+"分钟内还允许输入"+(num-1)+"次";
        } else {
            long loginFailCount = Long.parseLong(redisTemplate.opsForValue().get(key).toString());
            if (loginFailCount < (num-1)) {
                redisTemplate.opsForValue().increment(key,1);
                long seconds = redisTemplate.getExpire(key,TimeUnit.SECONDS);
                return "登录失败在["+seconds+"]秒内还允许输入错误["+(num-loginFailCount-1)+"]";
            } else {
                redisTemplate.opsForValue().set(User.getLoginTimeLockKey(uname),"1");
                redisTemplate.expire(User.getLoginCountFailKey(uname),LoginConstant.lockTime,TimeUnit.HOURS);
                return "因登录次数超过限制["+num+"]次，已经对其限制登录["+LoginConstant.lockTime+"]小时";
            }
        }
    }

    public Map<String, Object> loginUserLock(String uname) {
        String key = User.getLoginTimeLockKey(uname);
        Map<String,Object> map = new HashMap<String, Object>();
        if (redisTemplate.hasKey(key)) {
            long lockTime = redisTemplate.getExpire(key, TimeUnit.MINUTES);
            //如果存在
            map.put("flag",true);
            map.put("lockTime",lockTime);
        } else {
            map.put("flag",false);
        }
        return map;
    }
}
