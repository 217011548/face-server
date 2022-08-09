package com.coding.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteParam {

    @ApiModelProperty("Delete id")
    private Long id;
}
