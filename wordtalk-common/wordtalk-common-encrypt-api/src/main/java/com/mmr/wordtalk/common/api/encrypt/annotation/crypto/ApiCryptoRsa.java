package com.mmr.wordtalk.common.api.encrypt.annotation.crypto;

import com.mmr.wordtalk.common.api.encrypt.annotation.decrypt.ApiDecrypt;
import com.mmr.wordtalk.common.api.encrypt.annotation.encrypt.ApiEncrypt;
import com.mmr.wordtalk.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * RSA加密解密注解
 *
 * @author Chill
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiEncrypt(EncryptType.RSA)
@ApiDecrypt(EncryptType.RSA)
public @interface ApiCryptoRsa {

}
