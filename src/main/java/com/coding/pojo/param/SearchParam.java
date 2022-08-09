package com.coding.pojo.param;

import com.coding.utils.RequestPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


@Data
public class SearchParam {

    @NotNull
    @Range(max = 1000, min = 1)
    @ApiModelProperty("Search number")
    private Integer limit;

    @ApiModelProperty("Search keyword")
    private String keyword;
}
