package com.mmr.wordtalk.app.api.model.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 张恩睿
 * @date 2023/09/11
 */
@Data
@Schema(description = "邮箱注册模式")
public class EmailRegistration implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 注册邮箱
     */
    @Schema(description = "注册邮箱")
    private String email;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;
}
