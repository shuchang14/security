package com.shu.security.service;

import com.shu.security.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUserByCode(String code);

    /**
     * 保存
     * @param user
     */
    void save(UserEntity user);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 设置角色
     * @param userId
     * @param roleIds
     */
    void saveRole(String userId, List<String> roleIds);
}
