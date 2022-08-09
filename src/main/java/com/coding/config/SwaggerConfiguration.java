
package com.coding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author yunji
 */
@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
    @Value("${spring.application.name}")
    private String name;

    @Bean
    @Order(value = 1)
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.coding.controller.api"))
                .paths(PathSelectors.any())
                .build().groupName("api");
    }

    @Bean
    @Order(value = 1)
    public Docket admin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.coding.controller.admin"))
                .paths(PathSelectors.any())
                .build().groupName("admin");
    }


    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("code space")
                .description(name)
                .termsOfServiceUrl("http://www.coding-space.cn/")
                .contact(new Contact("felix", "https://www.guanweiming.com", "roma@guanweiming.com"))
                .version("1.0")
                .build();
    }


}
