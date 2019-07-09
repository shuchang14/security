package com.shu.securitydemo.service;

import com.shu.securitydemo.entity.UserDetailEntity;
import com.shu.securitydemo.entity.UserEntity;
import com.shu.securitydemo.util.SecurityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        for(UserEntity user:SecurityUtil.userList){
            if(s.equals(user.getUserCode())){
                Set<GrantedAuthority> authorities=new HashSet<>();
                UserDetailEntity userDetailEntity=new UserDetailEntity(user.getUserCode(),user.getPassword(),authorities);
                userDetailEntity.setUser(user);
                return userDetailEntity;
            }
        }
        return null;
    }
}
