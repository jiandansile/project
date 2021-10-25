
package com.yss.common.exception;

import com.yss.common.api.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * spring-boot-plus配置异常
 *
 * @author geekidea
 * @date 2020/3/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SpringBootPlusConfigException extends SpringBootPlusException {

    private static final long serialVersionUID = 8952028631871769425L;

    private Integer errorCode;
    private String message;

    public SpringBootPlusConfigException() {
        super();
    }

    public SpringBootPlusConfigException(String message) {
        super(message);
        this.message = message;
    }

    public SpringBootPlusConfigException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public SpringBootPlusConfigException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public SpringBootPlusConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringBootPlusConfigException(Throwable cause) {
        super(cause);
    }

}
