package com.example.selfstudyroomsystem.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableKnife4j
@EnableSwagger2WebMvc
@Configuration
public class Knife4jConfiguration implements InitializingBean {

    @Bean(value = "defaultApi2") //配置docket以配置Swagger具体参数
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("All")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.selfstudyroomsystem.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("自习室 APIs")
                .description("学习演示如何配置Swagger")
                .contact(new Contact("李瑞文", "http://47.115.201.56:8080/", "1353122328@qq.com"))
                .version("1.0")
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
