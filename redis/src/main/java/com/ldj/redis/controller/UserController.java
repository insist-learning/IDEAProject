package com.ldj.redis.controller;

import com.ldj.redis.bean.User;
import com.ldj.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(
            @RequestParam("userName") final String userName,
            @RequestParam(required = false,value = "password") final String password,
            @RequestParam(required = false,value = "valcode") final String valcode) {
        if (StringUtils.isEmpty(userName)) {
            throw  new RuntimeException("用户名userNmae不能为空");
        }
        Map<String, Object> map = userService.loginUserLock(userName);
        if ("true".equals(map.get("flag"))) {
            String lockTime = map.get("lockTime").toString();
            return "当前账户已经被锁定，还剩下"+lockTime+"分钟解除";
        } else {
            User user = userService.login(userName,password);
            if (null == user) {
                return userService.loginValidate(userName);
            } else {
                return "登录成功";
            }
        }

    }
}
