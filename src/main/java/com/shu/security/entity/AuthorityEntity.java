package com.shu.security.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 权限
 */
@Table(name = "shu_sys_authority")
@Entity
@Data
public class AuthorityEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,nullable = false)
    private String code;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 50,nullable = false)
    private String controller;
}
