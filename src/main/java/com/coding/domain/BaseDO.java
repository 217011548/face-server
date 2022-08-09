package com.coding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * home key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    /**
     * create time
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "create_time", nullable = false,insertable = false, columnDefinition = " timestamp NULL DEFAULT CURRENT_TIMESTAMP ")
    private LocalDateTime createTime;

    /**
     * update time
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "update_time", nullable = false,insertable = false, columnDefinition = " timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ")
    private LocalDateTime updateTime;

    /**
     *  version 
     */
    @JsonIgnore
    @Version
    @ApiModelProperty(hidden = true)
    @Column(name = "version", nullable = false,insertable = false, columnDefinition = "bigint default 0")
    private Long version;


}
