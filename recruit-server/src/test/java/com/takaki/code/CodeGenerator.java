package com.takaki.code;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.takaki.recruit.RecruitApplication;
import com.takaki.recruit.entity.BaseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author Takaki
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RecruitApplication.class)
public class CodeGenerator {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Test
    public void generateCode() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder
                        .author("Takaki")
                        .enableSwagger()
                        .disableOpenDir()
                        .commentDate("yyyy-MM-dd")
                        .outputDir("D:\\JavaProject\\recruit\\recruit-server\\src\\main\\java"))
                .packageConfig(builder -> {
                    builder.parent("com.takaki")
                            .moduleName("recruit")
                            .controller("controller")
                            .service("service")
                            .serviceImpl("service.serviceImpl")
                            .mapper("mapper")
                            .entity("entity.po")
                            .other("util")
                            .pathInfo(
                                    Collections.singletonMap(
                                            OutputFile.xml,
                                            "D:\\JavaProject\\recruit\\recruit-server\\src\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> builder
                        .addInclude("t_sys_role")
                        .addInclude("t_sys_user")
                        .addInclude("t_sys_dict")
                        .addInclude("t_sys_dict_item")
                        .addInclude("t_sys_resource")
                        .addInclude("t_user_role_relation")
                        .addInclude("t_job_seeker")
                        .addInclude("t_requirement")
                        .addInclude("t_position")
                        .addInclude("t_company")
                        .addTablePrefix("t_sys_", "t_")
                        .build()
                )
                .strategyConfig(builder -> builder.entityBuilder()
                        .disableSerialVersionUID()
                        .enableLombok()
                        .enableRemoveIsPrefix()
                        .enableTableFieldAnnotation()
                        .logicDeleteColumnName("is_deleted")
                        .superClass(BaseEntity.class)
                        .addSuperEntityColumns("create_time", "update_time", "created_by", "updated_by")
                        .addTableFills(new Column("create_time", FieldFill.INSERT_UPDATE))
                        .addTableFills(new Column("update_time", FieldFill.UPDATE))
                        .addTableFills(new Column("created_by", FieldFill.INSERT_UPDATE))
                        .addTableFills(new Column("updated_by", FieldFill.UPDATE))
                        .formatFileName("%sEntity")
                        .idType(IdType.AUTO)
                        .build()
                )
                .strategyConfig(builder -> builder.controllerBuilder()
                        .enableHyphenStyle()
                        .enableRestStyle()
                        .formatFileName("%sController")
                        .build()
                )
                .strategyConfig(builder -> builder.serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .build()
                )
                .strategyConfig(builder -> builder.mapperBuilder()
                        .superClass(BaseMapper.class)
                        .enableMapperAnnotation()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .build()
                )
                .templateEngine(new VelocityTemplateEngine())
                .execute();


    }

}
