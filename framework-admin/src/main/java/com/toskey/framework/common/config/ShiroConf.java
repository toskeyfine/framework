package com.toskey.framework.common.config;

import com.toskey.framework.common.shiro.ShiroFilterFactory;
import com.toskey.framework.common.shiro.ShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class ShiroConf {

    private final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean
    public EhCacheManager getEhCacheManager(){
        EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehcacheManager;
    }

    @Bean(name = "myShiroRealm")
    public ShiroRealm myShiroRealm(EhCacheManager ehCacheManager){
        ShiroRealm realm = new ShiroRealm();
        realm.setCacheManager(ehCacheManager);
        return realm;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(ShiroRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(realm);
        securityManager.setCacheManager(getEhCacheManager());
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactory();
        factoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        factoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("/403");
        loadShiroFilterChain(factoryBean);
        logger.info("shiro拦截器工厂类注入成功");
        return factoryBean;
    }

    /**
     * 加载ShiroFilter权限控制规则
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
        filterChainMap.put("/permission/userInsert", "anon");
        filterChainMap.put("/error", "anon");
        filterChainMap.put("/tUser/insert","anon");
        filterChainMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
    }

}
