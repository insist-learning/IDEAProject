package com.ldj.redis.bean;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
@Builder
public class User implements Serializable {


    private static final long serialVersionUID = 623708224480724097L;
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    public static String getKeyName() {
        return "user:";
    }

    /**
     * 锁定限制登录key
     * @param username
     * @return
     */
    public static   String getLoginTimeLockKey(String username) {
        return "user:loginTime:lock:"+username;
    }

    /**
     * 登录失败次数key
     * @param username
     * @return
     */
    public static String getLoginCountFailKey(String username) {
        return "user:loginCount:fail"+username;
    }

}
