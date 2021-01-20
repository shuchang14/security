package com.shu.security.service;

import com.shu.security.entity.UserEntity;

public interface UserService {

    UserEntity getUserByCode(String code);
}
