package com.shu.security.service;

import com.shu.security.entity.RoleEntity;
import com.shu.security.entity.UserEntity;
import com.shu.security.repository.RoleRepository;
import com.shu.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public UserEntity getUserByCode(String code) {
        return userRepository.findByUserCode(code);
    }

    /**
     * 保存
     *
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        userRepository.deleteById(Long.parseLong(id));
    }

    /**
     * 设置角色
     *
     * @param userId
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(String userId, List<String> roleIds) {
        List<Long> ids = new ArrayList<>();
        roleIds.forEach(id -> ids.add(Long.parseLong(id)));
        List<RoleEntity> roles = roleRepository.findAllById(ids);
        UserEntity user = userRepository.findById(Long.parseLong(userId)).get();
        user.setRoles(roles);
        userRepository.save(user);
    }
}
