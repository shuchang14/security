package com.shu.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "shu_sys_menu")
@Entity
@Data
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 菜单名称
     */
    @Column(length = 50,nullable = false)
    private String name;
    /**
     * 请求url
     */
    @Column(length = 100)
    private String url;
    /**
     * 菜单类型：1（目录）、2（菜单）
     */
    private int type;
    /**
     * 关联权限
     */
    @Column(name="authority_ids")
    private String authorityIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuEntity parent;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "parent")
    private List<MenuEntity> children;
}
