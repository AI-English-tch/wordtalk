package com.mmr.wordtalk.app.api.dto;

import com.mmr.wordtalk.app.api.model.registration.EmailRegistration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 张恩睿
 * @date 2023/09/11
 */
@Data
@Schema(description = "用户注册信息")
public class AppUserRegisterDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码,明文传递")
    private String password;
    /**
     * 注册方式
     */
    @Schema(description = "注册参数", oneOf = {EmailRegistration.class})
    private Map<String, String> registration;

}
