package com.coding.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class AdminLoginParam {


    @NotBlank(message = "Login info not complete")
    @ApiModelProperty("Login ID")
    @Length(min = 1, max = 32, message = "Account/Password not match")
    private String username;

    @Length(min = 6, max = 32, message = "Password lenght 6~32")
    @NotBlank(message = "Please insert password")
    @ApiModelProperty("Please insert password")
    private String password;
}
