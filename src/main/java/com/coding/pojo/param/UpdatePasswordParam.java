package com.coding.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author felix
 */
@Data
public class UpdatePasswordParam {


    @NotBlank(message = "Password cannot empty")
    @ApiModelProperty("new password")
    private String newPassword;


}
