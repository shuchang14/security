package com.shu.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 角色
 */
@Table(name = "shu_sys_role")
@Entity
@Data
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String code;
    @Column(length = 50)
    private String name;
    private boolean enable;
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
