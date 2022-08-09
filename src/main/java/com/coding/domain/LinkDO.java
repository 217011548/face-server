package com.coding.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tab_link")
public class LinkDO extends BaseDO {


    @ApiModelProperty("image link")
    private String url;


    @ApiModelProperty("remark")
    private String remark;

}
