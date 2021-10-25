package com.zf.web.tUser.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录参数说明",description = "登录参数说明")
public class LoginParam  {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "pwd")
    private String password;
}
