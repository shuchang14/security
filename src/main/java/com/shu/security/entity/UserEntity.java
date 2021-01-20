package com.shu.security.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "shu_sys_user")
@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue
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
}
