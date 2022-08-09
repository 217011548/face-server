package com.coding.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tab_user")
public class UserDO extends BaseDO {

    @Column(length = 32, nullable = false, unique = true)
    private String username;
    
    @Column(length = 64, nullable = false)
    private String password;

    private String remark;


}
