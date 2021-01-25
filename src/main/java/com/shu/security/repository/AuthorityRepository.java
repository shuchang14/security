package com.shu.security.repository;

import com.shu.security.entity.AuthorityEntity;
import com.shu.security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity,Long> {

}
