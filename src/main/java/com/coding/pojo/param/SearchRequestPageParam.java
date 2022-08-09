package com.coding.pojo.param;

import com.coding.utils.RequestPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequestPageParam extends RequestPage {

    @ApiModelProperty("Search keyword")
    private String keyword;
}
