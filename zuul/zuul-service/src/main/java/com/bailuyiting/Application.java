package com.bailuyiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Application   implements EmbeddedServletContainerCustomizer{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
				container.setPort(80);
	}

}
 