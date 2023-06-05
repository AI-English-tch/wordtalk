/**
 * @author 张恩睿
 * @date 2022/3/29
 */
package io.seata.spring.util;

import io.seata.common.util.CollectionUtils;
import io.seata.rm.tcc.remoting.parser.DubboUtil;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Proxy tools base on spring 主要解决 v1.4.2 兼容性问题 https://github.com/seata/seata/issues/3709
 *
 * @author zhangsen
 */
public class SpringProxyUtils {

	private SpringProxyUtils() {
	}

	/**
	 * Find target class class.
	 * @param proxy the proxy
	 * @return the class
	 * @throws Exception the exception
	 */
	public static Class<?> findTargetClass(Object proxy) throws Exception {
		if (proxy == null) {
			return null;
		}
		if (AopUtils.isAopProxy(proxy) && proxy instanceof Advised) {
			// #issue 3709
			final TargetSource targetSource = ((Advised) proxy).getTargetSource();
			if (!targetSource.isStatic()) {
				return targetSource.getTargetClass();
			}
			return findTargetClass(targetSource.getTarget());
		}
		return proxy.getClass();
	}

	public static Class<?>[] findInterfaces(Object proxy) throws Exception {
		if (AopUtils.isJdkDynamicProxy(proxy)) {
			AdvisedSupport advised = getAdvisedSupport(proxy);
			return getInterfacesByAdvised(advised);
		}
		else {
			return new Class<?>[] {};
		}
	}

	private static Class<?>[] getInterfacesByAdvised(AdvisedSupport advised) {
		Class<?>[] interfaces = advised.getProxiedInterfaces();
		if (interfaces.length > 0) {
			return interfaces;
		}
		else {
			throw new IllegalStateException("Find the jdk dynamic proxy class that does not implement the interface");
		}
	}

	/**
	 * Gets advised support.
	 * @param proxy the proxy
	 * @return the advised support
	 * @throws Exception the exception
	 */
	public static AdvisedSupport getAdvisedSupport(Object proxy) throws Exception {
		Field h;
		if (AopUtils.isJdkDynamicProxy(proxy)) {
			h = proxy.getClass().getSuperclass().getDeclaredField("h");
		}
		else {
			h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		}
		h.setAccessible(true);
		Object dynamicAdvisedInterceptor = h.get(proxy);
		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		return (AdvisedSupport) advised.get(dynamicAdvisedInterceptor);
	}

	/**
	 * Is proxy boolean.
	 * @param bean the bean
	 * @return the boolean
	 */
	public static boolean isProxy(Object bean) {
		if (bean == null) {
			return false;
		}
		// check dubbo proxy ?
		return DubboUtil.isDubboProxyName(bean.getClass().getName())
				|| (Proxy.class.isAssignableFrom(bean.getClass()) || AopUtils.isAopProxy(bean));
	}

	/**
	 * Get the target class , get the interface of its agent if it is a Proxy
	 * @param proxy the proxy
	 * @return target interface
	 * @throws Exception the exception
	 */
	public static Class<?> getTargetInterface(Object proxy) throws Exception {
		if (proxy == null) {
			throw new IllegalArgumentException("proxy can not be null");
		}

		// jdk proxy
		if (Proxy.class.isAssignableFrom(proxy.getClass())) {
			Proxy p = (Proxy) proxy;
			return p.getClass().getInterfaces()[0];
		}

		return getTargetClass(proxy);
	}

	/**
	 * Get the class type of the proxy target object, if hadn't a target object, return
	 * the interface of the proxy
	 * @param proxy the proxy
	 * @return target interface
	 * @throws Exception the exception
	 */
	protected static Class<?> getTargetClass(Object proxy) throws Exception {
		if (proxy == null) {
			throw new IllegalArgumentException("proxy can not be null");
		}
		// not proxy
		if (!AopUtils.isAopProxy(proxy)) {
			return proxy.getClass();
		}
		AdvisedSupport advisedSupport = getAdvisedSupport(proxy);
		Object target = advisedSupport.getTargetSource().getTarget();
		/*
		 * the Proxy of sofa:reference has no target
		 */
		if (target == null) {
			if (CollectionUtils.isNotEmpty(advisedSupport.getProxiedInterfaces())) {
				return advisedSupport.getProxiedInterfaces()[0];
			}
			else {
				return proxy.getClass();
			}
		}
		else {
			return getTargetClass(target);
		}
	}

	/**
	 * get the all interfaces of bean, if the bean is null, then return empty array
	 * @param bean the bean
	 * @return target interface
	 */
	public static Class<?>[] getAllInterfaces(Object bean) {
		Set<Class<?>> interfaces = new HashSet<>();
		if (bean != null) {
			Class<?> clazz = bean.getClass();
			while (!Object.class.getName().equalsIgnoreCase(clazz.getName())) {
				Class<?>[] clazzInterfaces = clazz.getInterfaces();
				interfaces.addAll(Arrays.asList(clazzInterfaces));
				clazz = clazz.getSuperclass();
			}
		}
		return interfaces.toArray(new Class[0]);
	}

}
