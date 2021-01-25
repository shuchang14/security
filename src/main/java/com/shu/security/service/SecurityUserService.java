package com.shu.security.service;

import com.shu.security.dto.UserDetailEntity;
import com.shu.security.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       // for(UserEntity user:SecurityUtil.userList){
        UserEntity user=userService.getUserByCode(s);
            //if(s.equals(user.getUserCode())){
                Set<GrantedAuthority> authorities=new HashSet<>();
                UserDetailEntity userDetailEntity=new UserDetailEntity(user.getUserCode(),user.getPassword(),authorities);
                userDetailEntity.setUser(user);
                return userDetailEntity;
            //}
       // }
       // return null;
    }
}
