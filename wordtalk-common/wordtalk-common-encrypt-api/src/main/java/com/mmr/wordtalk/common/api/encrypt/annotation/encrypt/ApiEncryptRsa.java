package com.mmr.wordtalk.common.api.encrypt.annotation.encrypt;

import com.mmr.wordtalk.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * rsa body 加密
 *
 * @author licoy.cn
 * @author 张恩睿
 * @version 2018/9/4
 * @see ApiEncrypt
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiEncrypt(EncryptType.RSA)
public @interface ApiEncryptRsa {

}
