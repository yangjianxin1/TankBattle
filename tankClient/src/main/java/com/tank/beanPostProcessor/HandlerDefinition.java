package com.tank.beanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.netty.channel.Channel;

/**
 * 处理请求消息的方法封装类
 * 
 * @author Administrator
 *
 */
public class HandlerDefinition {
	private final Object bean; // bean对象
	private final Method method; // 方法对象
	private final Class<?> clz; // 请求消息的Class对象

	private HandlerDefinition(Object bean, Method method, Class<?> clz) {
		this.bean = bean;
		this.method = method;
		this.clz = clz;
	}

	public void invoke(Object packet) {
		try {
			method.invoke(bean, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 实例化
	public static HandlerDefinition valueOf(Object bean, Method method) {
		// 还需要检查方法的参数
		Class<?> clz = null;
		Class<?>[] clzs = method.getParameterTypes(); // 获取方法参数列表
		clz = clzs[0]; // 获取方法消息类型
		return new HandlerDefinition(bean, method, clz);
	}

	public Object getBean() {
		return bean;
	}

	public Method getMethod() {
		return method;
	}

	public Class<?> getClz() {
		return clz;
	}

}
