package com.shu.securitydemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.securitydemo.entity.DataGridEntity;
import com.shu.securitydemo.entity.OnlineEntity;
import com.shu.securitydemo.entity.UserEntity;
import com.shu.securitydemo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @RequestMapping("expired")
    public String expired(){

        return "已被退出登录";
    }
    @RequestMapping("userList")
    public DataGridEntity userList(String condition){
        DataGridEntity dataGrid=new DataGridEntity();
        dataGrid.setCount(SecurityUtil.userList.size());
        dataGrid.setData(SecurityUtil.userList);
        return dataGrid;
    }
    @RequestMapping("onlineUsers")
    public DataGridEntity onlineUsers(String condition,Integer page,Integer limit){
        JSONObject jsonObject=JSONObject.parseObject(condition);
        DataGridEntity dataGrid=new DataGridEntity();
        dataGrid.setCount(SecurityUtil.onlineMap.size());
        List<OnlineEntity> list =new ArrayList<>();
        Set<String> keys = SecurityUtil.onlineMap.keySet();
        for(String key:keys){
            if(jsonObject!=null){
                String userCode=jsonObject.get("userCode")==null?null:jsonObject.get("userCode").toString();
                String userName=jsonObject.get("userName")==null?null:jsonObject.get("userName").toString();
                if(userCode!=null&& !"".equals(userCode)&&!userCode.equals(SecurityUtil.onlineMap.get(key).getUserCode())){
                    continue;
                }
                if(userName!=null&& !"".equals(userName)&&!userName.equals(SecurityUtil.onlineMap.get(key).getUserName())){
                    continue;
                }
            }
            list.add(SecurityUtil.onlineMap.get(key));
        }
        if(list.size()<page*limit){
            dataGrid.setData(list.subList((page-1)*limit,list.size()));
        }else{
            dataGrid.setData(list.subList((page-1)*limit,page*limit));
        }

        return dataGrid;
    }
    @Autowired
    private SessionRegistry sessionRegistry;
    /**
     * 强退           liangsc 2019-07-08
     * @param users
     * @return
     */
    @RequestMapping(value = "kickout")
    public String kickout(String users){
        List<String> userCodes =  JSONObject.parseArray(users,String.class);
        for(String userCode:userCodes){
            OnlineEntity onlineUser=SecurityUtil.onlineMap.get(userCode);
            if(onlineUser==null)
                continue;
            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(onlineUser.getSessionId());
            if(sessionInformation!=null)
                sessionInformation.expireNow();
            SecurityUtil.onlineMap.remove(userCode);
        }
        return "操作成功！";
    }
}
