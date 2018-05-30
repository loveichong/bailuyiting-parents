package com.bailuyiting.sso.service.config.web;

import com.bailuyiting.sso.service.Interceptor.TokenInterceptor;
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
                .excludePathPatterns("/oauth/**")//登入接口不进行拦截;
                .excludePathPatterns("/api/login/**")//登入接口不进行拦截;
                .excludePathPatterns("/api/sms/**")//登入接口不进行拦截;
                .excludePathPatterns("weChat/**")//登入接口不进行拦截;
                .excludePathPatterns("notify/**")//回调接口不进行拦截;
                .excludePathPatterns("/api/order/camera/**");//自动识别不进行拦截；

    }

}
