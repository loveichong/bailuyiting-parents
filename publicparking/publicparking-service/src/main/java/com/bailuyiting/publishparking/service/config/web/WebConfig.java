package com.bailuyiting.publishparking.service.config.web;

import com.bailuyiting.publishparking.service.Interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web配置
 * 注册拦截器
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry
                .addInterceptor(this.tokenInterceptor)
                .addPathPatterns("/api/**")// blyt/api/ 为扩展接口
                .excludePathPatterns("/feign/**")// 这个为原接口 不进行拦截
                .excludePathPatterns("/oauth/**")
                .excludePathPatterns("/text/**");//登入接口不进行拦截;

    }

}
