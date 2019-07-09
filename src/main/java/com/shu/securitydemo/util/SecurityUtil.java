package com.shu.securitydemo.util;

import com.shu.securitydemo.entity.OnlineEntity;
import com.shu.securitydemo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityUtil {
    /**
     * 存储用户信息（模拟数据库）
     */
    public static List<UserEntity> userList=new ArrayList<>();
    /**
     * 存储在线用户信息
     */
    public static Map<String,OnlineEntity> onlineMap=new ConcurrentHashMap<>();
}
