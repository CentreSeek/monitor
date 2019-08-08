package com.yjjk.monitor;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MonitorApplication {

	public static class CustomGenerator implements BeanNameGenerator {

		@Override
		public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
			return definition.getBeanClassName();
		}
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(MonitorApplication.class)
				.beanNameGenerator(new CustomGenerator())
				.run(args);
//    	SpringApplication.run(MonitorApplication.class, args);
	}

}
