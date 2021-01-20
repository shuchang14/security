package com.shu.security.service;

import com.shu.security.entity.UserEntity;
import com.shu.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserEntity getUserByCode(String code) {
        return userRepository.findByUserCode(code);
    }
}
