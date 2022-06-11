package com.takaki.recruit.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.takaki.recruit.constant.JwtConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                .groupName("recruit-server")
                .apiInfo(configureApiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.takaki.recruit.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(this.configureParameters());
    }

    @Bean
    public ApiInfo configureApiInfo() {
        return new ApiInfoBuilder()
                .title("山石在线招聘平台")
                .termsOfServiceUrl("localhost:8088")
                .contact(new Contact("Takaki","https://github.com/Takak11", "87449034@qq.com"))
                .description("实训项目，旨在完成一个完整的、可实际使用的招聘平台")
                .version("1.0")
                .build();
    }

    /**
     * 生成一个Header参数，放在请求头里，用于填写token访问
     * @return
     */
    @Bean
    public List<Parameter> configureParameters() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        Parameter parameter = parameterBuilder
                .name(JwtConstant.AUTH_HEADER)
                .modelRef(new ModelRef("string"))
                .parameterType("Header")
                .description("JwtToken")
                .required(false)
                .build();

        return Collections.singletonList(parameter);
    }


}
