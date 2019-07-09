package com.shu.securitydemo;

import com.shu.securitydemo.entity.UserEntity;
import com.shu.securitydemo.util.SecurityUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityDemoApplication {

    public static void main(String[] args) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserCode("admin");
        userEntity.setUserName("管理员");
        BCryptPasswordEncoder bcry=new BCryptPasswordEncoder();
        String ss= bcry.encode("111");
        userEntity.setPassword(ss);
        userEntity.setSex("男");
        userEntity.setAge(24);
        userEntity.setTell("13113111778");
        SecurityUtil.userList.add(userEntity);
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

}
