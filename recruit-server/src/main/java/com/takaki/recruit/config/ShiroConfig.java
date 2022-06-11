package com.takaki.recruit.config;

import com.takaki.recruit.filter.LoginFilter;
import com.takaki.recruit.realm.RecruitRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;

/**
 * @author Takaki
 * @date 2022/6/11
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置SecurityManager，关闭Session验证，设置自定义校验规则
     * @param realm
     * @return
     */
    @Bean
    public SecurityManager createSecurityManager(RecruitRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        DefaultSubjectDAO dao = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        dao.setSessionStorageEvaluator(evaluator);

        return securityManager;
    }

    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean registerFilterFactory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();

        HashMap<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("loginFilter", new LoginFilter());
        factory.setFilters(filterMap);

        HashMap<String, String> rules = new HashMap<>(16);
        rules.put("/api/recruit/login", "anon");
        rules.put("/api/recruit/register", "anon");
        rules.put("/swagger-resources/**", "anon");
        rules.put("/webjars/**", "anon");
        rules.put("/v2/**", "anon");
        rules.put("/swagger-ui.html/**", "anon");
        rules.put("/doc.html/**", "anon");

        rules.put("/**", "loginFilter");

        factory.setFilterChainDefinitionMap(rules);

        factory.setSecurityManager(securityManager);

        return factory;
    }

}
