package com.zf.web.sysLog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zf.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统操作日志
 *
 * @author yss
 * @since 2021-10-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysLog对象")
public class SysLog extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String loginName;

    @ApiModelProperty("用户操作")
    private String model;

    @ApiModelProperty("响应时间")
    private Integer time;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT) // 新增的时候填充数据
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE) // 新增或修改的时候填充数据
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT) // 新增的时候填充数据
    @ApiModelProperty("0删除 1正常")
    private String status;

}
