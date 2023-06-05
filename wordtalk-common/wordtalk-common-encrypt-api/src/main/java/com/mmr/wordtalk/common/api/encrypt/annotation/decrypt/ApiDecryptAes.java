package com.mmr.wordtalk.common.api.encrypt.annotation.decrypt;

import com.mmr.wordtalk.common.api.encrypt.enums.EncryptType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * aes 界面
 *
 * @author licoy.cn
 * @author 张恩睿
 * @version 2018/9/7
 * @see ApiDecrypt
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(EncryptType.AES)
public @interface ApiDecryptAes {

	/**
	 * Alias for {@link ApiDecrypt#secretKey()}.
	 * @return {String}
	 */
	@AliasFor(annotation = ApiDecrypt.class)
	String secretKey() default "";

}
