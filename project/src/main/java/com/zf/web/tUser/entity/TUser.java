package com.zf.web.tUser.entity;

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
 * 用户表
 *
 * @author yss
 * @since 2021-10-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TUser对象")
public class TUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("组织id")
    private Integer orgId;

    @ApiModelProperty("真实姓名")
    private String realname;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性`user``interact_action`别")
    private String sex;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("用户类型 ADMIN_USER管理员用户STUDENT_USER学生 SOCIAL_USER社会人员 TEACHER_USER教师")
    private String type;

    @ApiModelProperty("头像")
    private String headUrl;

    @ApiModelProperty("角色id")
    private String roleId;

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
