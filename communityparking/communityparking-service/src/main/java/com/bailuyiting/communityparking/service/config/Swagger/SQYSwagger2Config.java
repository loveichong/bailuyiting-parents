package com.bailuyiting.communityparking.service.config.Swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SQYSwagger2Config {
    /**
     * 旧功能扩展配置
     * @return
     */
    @Bean
    public Docket oldfunction() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("白鹭易停-小区停车接口文档：所有路径前面加localhost:communityParking/")
                .description("白鹭易停")
                .termsOfServiceUrl("http://www.bailuyiting.com/")
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bailuyiting.communityparking.service.controller"))
                .paths(PathSelectors.any())
                .build();
    }


}
