package com.takaki.recruit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Takaki
 * @date 2022/6/9
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.takaki.**.mapper"})
public class MybatisPlusConfig {

}
