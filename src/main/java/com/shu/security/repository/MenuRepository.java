package com.shu.security.repository;

import com.shu.security.entity.MenuEntity;
import com.shu.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity,Long> {

}
