package com.ldj.redis.service;

import com.ldj.redis.bean.User;

import java.util.Map;

public interface UserService {
    /**
     *
     * @param uname 用户名
     * @param upass 密码
     * @return
     */
    public User login(String uname,String upass);


    /**
     *
     * @param uname 用户名
     * @return 验证用户的信息
     */
    public String loginValidate(String uname);

    /**
     * 判断当前用户名是否限制登录
     * @param uname
     * @return
     */
    public Map<String, Object> loginUserLock(String uname);
}
