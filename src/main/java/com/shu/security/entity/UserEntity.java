package com.shu.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "shu_sys_user")
@Entity
@Data
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,nullable = false,unique = true)
    private String userCode;
    @Column(nullable = false,length = 50)
    private String userName;
    @Column(length = 100,nullable = false)
    private String password;
    @Column(nullable = false)
    private int sex;
    @Column
    private Integer age;
    @Column
    private String tell;
    @ManyToMany
    @JoinTable(name = "shu_sys_user_role",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;
}
