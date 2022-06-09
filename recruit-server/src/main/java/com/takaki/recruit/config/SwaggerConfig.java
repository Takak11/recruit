package com.takaki.recruit.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Takaki
 * @date 2022/6/9
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(configureApiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.takaki.recruit.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo configureApiInfo() {
        return new ApiInfoBuilder()
                .title("山石在线招聘平台")
                .description("实训项目，旨在完成一个完整的、可实际使用的招聘平台")
                .version("1.0")
                .build();
    }


}
