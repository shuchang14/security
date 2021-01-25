package com.shu.security.repository;

import com.shu.security.entity.RoleEntity;
import com.shu.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

}
