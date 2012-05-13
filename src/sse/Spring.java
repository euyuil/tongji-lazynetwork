package sse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Spring implements ApplicationContextAware {

	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
}
