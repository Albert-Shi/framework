package com.shishuheng.framework.common.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author shishuheng
 * @date 2020/3/18 9:52 上午
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value(value = "${swagger.basePackage}")
    private String basePackage;

    @Value(value = "${swagger.title}")
    private String title;

    @Value(value = "${swagger.description}")
    private String description;

    @Value(value = "${swagger.version}")
    private String version;

    @Value(value = "${swagger.contact.name}")
    private String contactName;

    @Value(value = "${swagger.contact.url}")
    private String contactUrl;

    @Value(value = "${swagger.contact.email}")
    private String contactEmail;

    @Value(value = "${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder builder = new ApiInfoBuilder();
        Contact contact = new Contact(contactName, contactUrl, contactEmail);
        builder.title(title)
                .contact(contact)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .version(version);
        return builder.build();
    }
}
