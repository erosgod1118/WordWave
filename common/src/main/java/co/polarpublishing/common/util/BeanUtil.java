package co.polarpublishing.common.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Provides bean utilities.
 *
 * @author FMRGJ
 */
@Component
@RequiredArgsConstructor
public class BeanUtil {

	private final ApplicationContext applicationContext;

	public void changeBeanScope(String beanDefinitionName, String scope) {
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) this.applicationContext
				.getAutowireCapableBeanFactory();

		BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);

		beanDefinition.setScope(scope);
		beanDefinitionRegistry.registerBeanDefinition(beanDefinitionName, beanDefinition);
	}

}
