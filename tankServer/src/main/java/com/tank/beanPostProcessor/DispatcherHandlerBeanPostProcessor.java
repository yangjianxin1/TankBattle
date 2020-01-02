package com.tank.beanPostProcessor;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DispatcherHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String nameName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String nameName) throws BeansException {

		Class<?> clz = bean.getClass();
		Method[] methods = clz.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(HandlerAnno.class)) { // 如果是处理请求消息的方法
				// 获取处理请求的方法的封装类
				HandlerDefinition def = HandlerDefinition.valueOf(bean, method);
				// 以报文的Class对象为key，HandlerDefinition为value，放进一个map里面
				getActionDispatcher().registerHandlerDefinition(def.getClz(), def);
			}
		}
		return bean;
	}
	
	private ActionDispatcher getActionDispatcher(){
		return (ActionDispatcher)applicationContext.getBean("actionDispatcher");
	}

}
