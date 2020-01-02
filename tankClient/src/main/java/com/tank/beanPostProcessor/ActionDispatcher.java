package com.tank.beanPostProcessor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;

/**
 * 请求分发器
 * 
 * @author Administrator
 *
 */
@Component
public class ActionDispatcher {
	private Map<Class<?>, HandlerDefinition> handlerMap = new HashMap<>(); // 消息处理器注册在该map中

	/**
	 * 注册处理器
	 * @param clz
	 * @param defition
	 */
	public void registerHandlerDefinition(Class<?> clz,HandlerDefinition defition){
		HandlerDefinition pre=handlerMap.put(clz, defition);
		if(pre!=null){	//如果该类消息有多个处理器，抛出异常
//			throw new IllegalAccessException("");
		}
	}
	
	public void doHandle(Object packet){
		HandlerDefinition definition=handlerMap.get(packet.getClass());
		if(definition!=null){	//如果该类型消息没有对应的处理器，应该抛出异常
			definition.invoke(packet);
		}
		
	}
	
	public Map<Class<?>, HandlerDefinition> getHandlerMap() {
		return handlerMap;
	}

	public void setHandlerMap(Map<Class<?>, HandlerDefinition> handlerMap) {
		this.handlerMap = handlerMap;
	}

}
