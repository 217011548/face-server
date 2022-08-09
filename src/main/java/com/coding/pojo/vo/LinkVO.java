package com.coding.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LinkVO {

    @ApiModelProperty("website name")
    private String name;

    @ApiModelProperty("direct url")
    private String url;

    public static LinkVO of(String name, String url) {
        LinkVO linkVO = new LinkVO();
        linkVO.setName(name);
        linkVO.setUrl(url);
        return linkVO;
    }
}
