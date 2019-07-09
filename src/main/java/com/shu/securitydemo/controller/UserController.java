package com.shu.securitydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.shu.securitydemo.entity.UserEntity;
import com.shu.securitydemo.util.SecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("register")
    public UserEntity register(String user){
        UserEntity userEntity=JSONObject.parseObject(user,UserEntity.class);
        BCryptPasswordEncoder bcry=new BCryptPasswordEncoder();
        String ss= bcry.encode(userEntity.getPassword());
        userEntity.setPassword(ss);
        SecurityUtil.userList.add(userEntity);
        return userEntity;
    }
}
