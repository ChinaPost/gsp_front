package com.framework.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.events.AbstractSessionEvent;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDestroyedEvent;

import com.framework.utils.SpringBeanManger;

/**
 * @author tang E-mail: killerover84@163.com
 * @version 2017年1月12日 下午2:36:30 类说明
 */
public class SpringSessionRedisListener
		implements org.springframework.context.ApplicationListener<AbstractSessionEvent> {

	private final static Logger logger = LoggerFactory.getLogger(SpringSessionRedisListener.class);

	/*
	 * session监控 (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(AbstractSessionEvent event) {
		// TODO Auto-generated method stub
		if (event instanceof SessionDestroyedEvent) {
			logger.debug("session销毁：" + event.getSessionId());
		}
	}

}
