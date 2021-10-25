package com.zf.web.sysLog.param;

import com.zf.common.pagination.BasePageOrderParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统操作日志 分页参数对象
 * </pre>
 *
 * @author yss
 * @date 2021-10-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统操作日志分页参数")
public class SysLogPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
