package com.shu.security.entity;

import javax.persistence.*;
import java.util.Date;

public class BaseEntity {

    @Column(name = "create_by",nullable = false)
    private Long createBy;
    @Column(name = "create_time",nullable = false)
    private Date createTime;
    @Column(name = "last_modify_by")
    private Long lastModifyBy;
    @Column(name = "last_modify_time")
    private Date lastModifyTime;
}
