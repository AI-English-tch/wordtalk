package com.mmr.wordtalk.common.api.encrypt.annotation.encrypt;

import com.mmr.wordtalk.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * <p>
 * 加密{@link org.springframework.web.bind.annotation.ResponseBody}响应数据，可用于整个控制类或者某个控制器上
 * </p>
 *
 * @author licoy.cn
 * @author 张恩睿
 * @version 2018/9/4
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiEncrypt {

	/**
	 * 加密类型
	 * @return 类型
	 */
	EncryptType value();

	/**
	 * 私钥，用于某些需要单独配置私钥的方法，没有时读取全局配置的私钥
	 * @return 私钥
	 */
	String secretKey() default "";

}
