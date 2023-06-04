package com.mmr.wordtalk.common.api.encrypt.annotation.crypto;

import com.mmr.wordtalk.common.api.encrypt.annotation.decrypt.ApiDecrypt;
import com.mmr.wordtalk.common.api.encrypt.annotation.encrypt.ApiEncrypt;
import com.mmr.wordtalk.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * DES加密解密注解
 *
 * @author Chill
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiEncrypt(EncryptType.DES)
@ApiDecrypt(EncryptType.DES)
public @interface ApiCryptoDes {

}
