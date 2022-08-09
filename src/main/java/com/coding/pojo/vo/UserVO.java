package com.coding.pojo.vo;

import com.coding.domain.UserDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class UserVO {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("社区名称")
    private String communityName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("描述")
    private String communityDesc;

    @ApiModelProperty("分组名称")
    private String groupName;

    public static UserVO of(UserDO item, String groupName) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(item, userVO);
        userVO.setGroupName(groupName);
        return userVO;
    }
}
